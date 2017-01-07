# 5.安装Ambari-Server服务
在准备好HDP的环境和HDP的本地离线库后，整个HDP的安装将会非常的容易。
1. 配置yum指向chdp-ambari库的url
2. 安装ambari-server
3. 安装和配置postgres数据库
4. 修正ambari-server的数据库连接驱动
5. 启动服务器

## 5.1 配置YUM源
### 5.1.1 新建配置库文件`ambari.repo`

一定要确保有这个`ambari.repo`文件在目录中`/etc/yum.repos.d/`

该文件的内容如下：

```
#VERSION_NUMBER=2.4.2.0
[AMBARI-2.4.2.0]
name=AMBARI Version - AMBARI-2.4.2.0
baseurl=http://chdp-ambari/AMBARI/centos7/2.4.2.0-136
gpgcheck=1
gpgkey=http://chdp-ambari/AMBARI/centos7/2.4.2.0-136/RPM-GPG-KEY/RPM-GPG-KEY-Jenkins
enabled=1
priority=1
```

### 5.1.2 修改hdp.repo
下载HDP文件内容，并修改正确

	cd /etc/yum.repos.d/
	wget -Nv http://chdp-ambari/HDP/centos7/hdp.repo


修改对应的baseurl和gpkkey的内容

```
#VERSION_NUMBER=2.5.3.0-37
[HDP-2.5.3.0]
name=HDP Version - HDP-2.5.3.0
baseurl=http://chdp-ambari/HDP/centos7
gpgcheck=1
gpgkey=http://chdp-ambari/HDP/centos7/RPM-GPG-KEY/RPM-GPG-KEY-Jenkins
enabled=1
priority=1

[HDP-UTILS-1.1.0.21]
name=HDP-UTILS Version - HDP-UTILS-1.1.0.21
baseurl=http://chdp-ambari/HDP-UTILS/repos/centos7
gpgcheck=1
gpgkey=http://chdp-ambari/HDP/centos7/RPM-GPG-KEY/RPM-GPG-KEY-Jenkins
enabled=1
priority=1
```

修改完毕后执行`yum repolist`,能够正确显示就代表正常了。

## 5.2 安装Ambari-Server服务器
### 5.1.1 安装server

	yum install ambari-server

## 5.3 安装和配置POSTGRES

### 5.3.1安装postgresql数据库
	export LANGUAGE=en_US.UTF-8
	export LANG=en_US.UTF-8
	export LC_ALL=en_US.UTF-8

	# 对于ubuntu还需要执行下面两句
	locale-gen en_US.UTF-8
	dpkg-reconfigure locales

	# 执行安装postgres数据库（之前已经通过依赖安装就不用再进行了）
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

	CREATE ROLE ambari LOGIN PASSWORD 'efun';
	CREATE DATABASE ambari OWNER ambari ENCODING 'UTF8';
	GRANT ALL PRIVILEGES ON DATABASE ambari to ambari;

	CREATE ROLE hive LOGIN PASSWORD 'efun';
	CREATE DATABASE hive OWNER hive ENCODING 'UTF8';
	GRANT ALL PRIVILEGES ON DATABASE hive to hive;

	CREATE ROLE ranger LOGIN PASSWORD 'efun';
	CREATE DATABASE ranger OWNER ranger ENCODING 'UTF8';
	GRANT ALL PRIVILEGES ON DATABASE ranger to ranger;

	CREATE ROLE rangerkms LOGIN PASSWORD 'efun';
	CREATE DATABASE rangerkms OWNER rangerkms ENCODING 'UTF8';
	GRANT ALL PRIVILEGES ON DATABASE rangerkms to rangerkms;

#### 5.3.5.2 配置默认schema以及初始化表结构

	psql -h chdp-ambari -U ambari
	输入密码：
	create SCHEMA ambari;
	\i /var/lib/ambari-server/resources/Ambari-DDL-Postgres-CREATE.sql

	psql -h chdp-ambari -U hive
	输入密码：
	create SCHEMA hive
	………………

#### 5.3.6 重新启动数据库服务
	systemctl restart postgresql.service

至此，数据库的安装完毕。

### 5.4 拷贝正确的JDBC驱动至安装环境 （重要）
执行脚本完成数据库驱动的安装与配置

	ambari-server setup --jdbc-db=postgres --jdbc-driver=/usr/lib/ambari-server/postgresql-9.3-1101-jdbc4.jar


### 5.5 配置Ambari-Server
使用命令来进行字符界面的ambari配置

	ambari-server setup

```
Setup ambari-server
Checking SELinux...
WARNING: Could not run /usr/sbin/sestatus: OK
Customize user account for ambari-server daemon [y/n] (n)? n
Adjusting ambari-server permissions and ownership...
Checking firewall status...
Checking JDK...
[1] Oracle JDK 1.8 + Java Cryptography Extension (JCE) Policy Files 8
[2] Oracle JDK 1.7 + Java Cryptography Extension (JCE) Policy Files 7
[3] Custom JDK
==============================================================================
Enter choice (1): 3
WARNING: JDK must be installed on all hosts and JAVA_HOME must be valid on all hosts.
WARNING: JCE Policy files are required for configuring Kerberos security. If you plan to use Kerberos,please make sure JCE Unlimited Strength Jurisdiction Policy Files are valid on all hosts.
Path to JAVA_HOME: /usr/java/default
Validating JDK on Ambari Server...done.
Completing setup...
Configuring database...
Enter advanced database configuration [y/n] (n)? y
Configuring database...
==============================================================================
Choose one of the following options:
[1] - PostgreSQL (Embedded)
[2] - Oracle
[3] - MySQL / MariaDB
[4] - PostgreSQL
[5] - Microsoft SQL Server (Tech Preview)
[6] - SQL Anywhere
[7] - BDB
==============================================================================
Enter choice (1): 4
Hostname (localhost): chdp-ambari
Port (5432): 5432
Database name (ambari): ambari
Postgres schema (ambari): ambari
Username (ambari): ambari
Enter Database Password (bigdata):
Re-enter password:
Configuring ambari database...
Configuring remote database connection properties...
WARNING: Before starting Ambari Server, you must run the following DDL against the database to create the schema: /var/lib/ambari-server/resources/Ambari-DDL-Postgres-CREATE.sql
Proceed with configuring remote database connection properties [y/n] (y)? y
Extracting system views...
ambari-admin-2.4.2.0.136.jar
............
Adjusting ambari-server permissions and ownership...
Ambari Server 'setup' completed successfully.

```

### 5.5 启动Ambari-Server
使用命令启动服务器，启动过程中会检查数据是否已经建表成功。

	ambari-server start

```
Using python  /usr/bin/python
Starting ambari-server
Ambari Server running with administrator privileges.
Organizing resource files at /var/lib/ambari-server/resources...
Ambari database consistency check started...
No errors were found.
Ambari database consistency check finished
Server PID at: /var/run/ambari-server/ambari-server.pid
Server out at: /var/log/ambari-server/ambari-server.out
Server log at: /var/log/ambari-server/ambari-server.log
Waiting for server start....................
Ambari Server 'start' completed successfully.
```

### 5.6 测试启动成功

	curl http://localhost:8080
	能够正确显示页面html信息代表成功
