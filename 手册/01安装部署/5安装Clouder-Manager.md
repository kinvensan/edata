# 5.安装Clouder Manager服务器
在准备好CDH的环境和CDH的本地离线库后，整个Clouder Manager的安装将会非常的容易。
1. 配置yum指向ehdp-cm库的url
2. 安装clouder-manager-daemons和clouder-server
3. 安装和配置postgres数据库
4. 修正cm-server的数据库连接配置
5. 启动服务器

## 5.1 配置YUM源
### 5.1.1下载配置库文件

	cd /etc/yum.repos.d/
	wget -Nv http://ehdp-cm/cm5/cloudera-cm.repo

### 5.1.2 修改cloudera-cm.repo
修改对应的baseurl和gpkkey的内容

	baseurl=http://ehdp-cm/cm5/5/
	gpgkey=http://ehdp-cm/cm5/RPM-GPG-KEY-cloudera

修改完毕后执行`yum repolist`,能够正确显示就代表正常了。

## 5.2 安装CM服务器
### 5.1.1 自定义数据库的安装方法

	yum install cloudera-manager-daemons cloudera-manager-server

### 5.1.2 一键完成的安装方法（不建议选择）
该方法由于安装了内嵌数据库，不建议在生产环境使用，安装完成后有提示的。

	wget -Nv http://ehdp-cm/install/cloudera-manager-installer.bin
	chmod 775 cloudera-manager-installer.bin
	./cloudera-manager-installer.bin --skip_repo_package=1 #跳过远程库下载

安装完毕后，会提示一个URL，通过浏览器地址即可访问，用户密码都是admin

## 5.3 安装和配置mysql
### 5.3.1安装mysql数据库

下载rpm文件
参考 https://dev.mysql.com/downloads/repo/yum/

···
yum localinstall mysql57-community-release-el7-11.noarch.rpm
yum install mysql-server
systemctl start mysqld.service
systemctl enable mysqld.service
···

### 5.3.2 更改密码
在/var/log/mysqld.log中找到

```
A temporary password is generated for root@localhost
```
登录后修改密码
```
alter user 'root'@'localhost' identified by 'abcdefg#2017*MySQL';
```

修改密码强度
```
show variables like '%password%';

set global validate_password_policy=0;

set global validate_password_length=4;
```

### 5.3.３ 正确安装mysql.jar

```
$ mkdir -p /usr/share/java/
$ cp mysql-connector-java-5.1.31/mysql-connector-java-5.1.31-bin.jar /usr/share/java/mysql-connector-java.jar
```
** /usr/share/java/mysql-connector-java.jar 名字要变更正确**

### 5.3.4 数据库初始化
建立数据库
```
create database amon DEFAULT CHARACTER SET utf8;
create database rman DEFAULT CHARACTER SET utf8;
create database metastore DEFAULT CHARACTER SET utf8;
create database sentry DEFAULT CHARACTER SET utf8;
create database nav DEFAULT CHARACTER SET utf8;
create database navms DEFAULT CHARACTER SET utf8;
create database cmf DEFAULT CHARACTER SET utf8;
grant all on cmf.* TO 'cmf'@'%' IDENTIFIED BY 'cmf123';
grant all on amon.* TO 'cmf'@'%' IDENTIFIED BY 'cmf123';
grant all on rman.* TO 'cmf'@'%' IDENTIFIED BY 'cmf123';
grant all on metastore.* TO 'cmf'@'%' IDENTIFIED BY 'cmf123';
grant all on sentry.* TO 'cmf'@'%' IDENTIFIED BY 'cmf123';
grant all on nav.* TO 'cmf'@'%' IDENTIFIED BY 'cmf123';
grant all on navms.* TO 'cmf'@'%' IDENTIFIED BY 'cmf123';
```
初始化数据（新版本这步骤不正确）
```
/usr/share/cmf/schema/scm_prepare_database.sh mysql -h ehdp-manager cmf cmf cmf123
```


## 5.3 安装和配置POSTGRES

### 5.3.1安装postgresql数据库
	export LANGUAGE=en_US.UTF-8
	export LANG=en_US.UTF-8
	export LC_ALL=en_US.UTF-8

	# 对于ubuntu还需要执行下面两句
	locale-gen en_US.UTF-8
	dpkg-reconfigure locales

	# 执行安装postgres数据库
	yum install postgresql-server

### 5.3.2 初始化数据库
如果目录`/var/lib/pgsql/data`中存在数据库的文件，如base文件夹等，则证明数据库已经初始化了，
不用执行下面的指令

	postgresql-setup initdb

### 5.3.3 修改数据库配置文件作为外部数据库使用
在目录中`/var/lib/pgsql/data`修改`postgresql.conf`文件(删除注释)

	listen_addresses = '*'		# what IP address(es) to listen on;
								# comma-separated list of addresses;
								# defaults to 'localhost'; use '*' for all
								# (change requires restart)
	port = 5432					# (change requires restart)


在目录中`/var/lib/pgsql/data`修改pg_hba.conf（修改trust为md5）

	# "local" is for Unix domain socket connections only
	local   all             all                                     peer
	# IPv4 local connections:
	host    all             all             0.0.0.0/0               md5
	# IPv6 local connections:
	host    all             all             ::1/128                 md5

### 5.3.4 启动数据库服务器

	systemctl start postgresql.service
	systemctl enabale postgresql.service

### 5.3.5 配置POSTGRES数据库的用户与密码
#### 5.3.5.1使用用户登录postgres服务器

	su - postgres
	psql

#### 5.3.5.2 创建数据用户角色和数据库
如果系统中没有utf8 编码，可以利用UNICODE代替，该步骤**可选**

	UPDATE pg_database SET datistemplate = FALSE WHERE datname = 'template1';
	DROP DATABASE template1;
	CREATE DATABASE template1 WITH TEMPLATE = template0 ENCODING = 'UNICODE';
	UPDATE pg_database SET datistemplate = TRUE WHERE datname = 'template1';
	\c template1
	VACUUM FREEZE;
	\q

执行创建用户和数据库任务（以下均是在psql中执行）

	CREATE ROLE scm LOGIN PASSWORD 'efun';
	CREATE DATABASE scm OWNER scm ENCODING 'UTF8';
	GRANT ALL PRIVILEGES ON DATABASE scm to scm;

	CREATE ROLE amon LOGIN PASSWORD 'efun';
	CREATE DATABASE amon OWNER amon ENCODING 'UTF8';
	GRANT ALL PRIVILEGES ON DATABASE amon to amon;

	CREATE ROLE rman LOGIN PASSWORD 'efun';
	CREATE DATABASE rman OWNER rman ENCODING 'UTF8';
	GRANT ALL PRIVILEGES ON DATABASE rman to rman;

	CREATE ROLE hive LOGIN PASSWORD 'efun';
	CREATE DATABASE hive OWNER hive ENCODING 'UTF8';
	GRANT ALL PRIVILEGES ON DATABASE hive to hive;

	CREATE ROLE sentry LOGIN PASSWORD 'efun';
	CREATE DATABASE sentry OWNER sentry ENCODING 'UTF8';
	GRANT ALL PRIVILEGES ON DATABASE sentry to sentry;

	CREATE ROLE nav LOGIN PASSWORD 'efun';
	CREATE DATABASE nav OWNER nav ENCODING 'UTF8';
	GRANT ALL PRIVILEGES ON DATABASE nav to nav;
	CREATE ROLE navms LOGIN PASSWORD 'efun';

	CREATE DATABASE navms OWNER navms ENCODING 'UTF8';
	GRANT ALL PRIVILEGES ON DATABASE navms to navms;


#### 5.3.5.2 配置账号的默认schema

	psql -h ehdp-cm -U scm
	输入密码：
	create SCHEMA scm

	psql -h ehdp-cm -U hive
	输入密码：
	create SCHEMA hive
	………………

#### 5.3.6 重新启动数据库服务
	systemctl restart postgresql.service

至此，数据库的安装完毕。

### 5.4 修正cm-server的数据库连接配置 （重要）
执行脚本完成cloudera-scm-server的数据库安装与配置，执行完成后，

	/usr/share/cmf/schema/scm_prepare_database.sh postgresql -h ehdp-cm -P 5432 scm scm efun

请查看文件`/etc/cloudera-scm-server/db.properties`是否正确的值。

### 5.5 启动cm服务

自行修改 `/etc/cloudera-scm-server/db.properties`
内容如下：

```
# Auto-generated by scm_prepare_database.sh on Thu Jan 25 19:47:38 CST 2018
#
# For information describing how to configure the Cloudera Manager Server
# to connect to databases, see the "Cloudera Manager Installation Guide."
#
com.cloudera.cmf.db.type=mysql
com.cloudera.cmf.db.host=ehdp-manager:3306
com.cloudera.cmf.db.name=cmf
com.cloudera.cmf.db.user=cmf
com.cloudera.cmf.db.setupType=EXTERNAL
com.cloudera.cmf.db.password=cmf123
```

	/etc/init.d/cloudera-scm-server start
	systemctl start cloudera-scm-server
	systemctl enable cloudera-scm-server

### 5.6 测试启动成功

	curl http://localhost:7180
	<head><meta http-equiv="refresh" content="0;url=/cmf/"></head>
