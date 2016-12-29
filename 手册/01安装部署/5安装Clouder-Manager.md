# 5.安装Clouder Manager服务器
在准备好CDH的环境和CDH的本地离线库后，整个Clouder Manager的安装将会非常的容易。
1. 配置yum指向ehdp-cm库的url
2. 安装clouder-manager-daemons和clouder-server
3. 安装postgres数据库，配置用户
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

## 5.2 安装CM服务器
### 5.1.2 下载并安装CM-server

	wget -Nv http://ehdp-cm/install/cloudera-manager-installer.bin
	chmod 775 cloudera-manager-installer.bin
	./cloudera-manager-installer.bin --skip_repo_package=1 #跳过远程库下载


至此，按提示页面进行对应选择即可成功在内网服务器上安装ClouderaManager，安装好后，会提示一个URL，通过浏览器地址即可访问，用户密码都是admin

### 5.2 配置POSTGRES
#### 5.2.1 初始化数据库
	su - postgres
	postgresql-setup initdb

#### 5.2.2 修改数据库配置文件
在目录中/var/lib/pgsql/data修改postgresql.conf(删除注释)

	listen_addresses = '*'		# what IP address(es) to listen on;
								# comma-separated list of addresses;
								# defaults to 'localhost'; use '*' for all
								# (change requires restart)
	port = 5432					# (change requires restart)


在目录中/var/lib/pgsql/data修改pg_hba.conf（修改trust为md5）

	# "local" is for Unix domain socket connections only
	local   all             all                                     peer
	# IPv4 local connections:
	host    all             all             0.0.0.0/0               md5
	# IPv6 local connections:
	host    all             all             ::1/128                 md5

#### 5.2.3 启动服务器

	systemctl start postgresql.service
	systemctl enabale postgresql.service

#### 5.2.4 配置POSTGRES数据库的用户与密码
使用用户登录postgres服务器

	su - postgres
	psql

第一步创建数据用户角色和数据库

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

第二步，分别使用各自的账号创建schema

	psql -h ehdp-cm -U scm
	create SCHEMA scm

	psql -h ehdp-cm -U hive
	create SCHEMA hive


#### 5.2.5 配置POSTGRES的SCHEMA

### 5.3 停止服务器
	/etc/init.d/cloudera-scm-server stop
	/etc/init.d/cloudera-scm-server-db stop

### 5.4 安装SCM库

	/usr/share/cmf/schema/scm_prepare_database.sh postgresql -h ehdp-cm scm scm efun

### 5.5 卸载JDK7和CM-SERVER-DB
	yum remove
	yum remove

### 5.6 启动服务器
	/etc/init.d/cloudera-scm-server start

### 5.7 测试启动成功

	curl http://localhost:7180
	<head><meta http-equiv="refresh" content="0;url=/cmf/"></head>

## 6.安装集群
以下内容均在浏览器中进行，优先下载ssh.tar,并自行解压以备后续使用。

	http://ehdp-cm/install/ssh.tar
