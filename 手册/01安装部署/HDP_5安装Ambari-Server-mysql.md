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

## 5.3 安装和配置MySql

### 5.3.1安装mysql数据库
        下载配置库文件
	wget -Nv https://dev.mysql.com/get/mysql57-community-release-el7-9.noarch.rpm

	# 安装repo文件
	yum localinstall mysql57-community-release-el7-9.noarch.rpm

	# 执行安装mysql数据库（之前已经通过依赖安装就不用再进行了）
	yum install mysql-community-server
	

### 5.3.2 配置Mysql数据库
#### 5.3.2.1使用用户登录mysql服务器

	mysql -h chdp-ambari -u root -p
        输入密码：
        
#### 5.3.2.2 创建数据用户角色和数据库

	CREATE USER '$AMBARIUSER'@'%' IDENTIFIED BY '$AMBARIPASSWORD';
        GRANT ALL PRIVILEGES ON *.* TO '$AMBARIUSER'@'%'; 
        CREATE USER '$AMBARIUSER'@'localhost' IDENTIFIED BY '$AMBARIPASSWORD';
        GRANT ALL PRIVILEGES ON *.* TO '$AMBARIUSER'@'localhost';
        CREATE USER'$AMBARIUSER'@'$AMBARISERVERFQDN' IDENTIFIED BY '$AMBARIPASSWORD';
        GRANT ALL PRIVILEGES ON *.* TO '$AMBARIUSER'@'$AMBARISERVERFQDN';
        FLUSH PRIVILEGES;
        
        CREATE USER '$HIVEUSER'@'%' IDENTIFIED BY '$HIVEPASSWORD';
        GRANT ALL PRIVILEGES ON *.* TO '$AMBARIUSER'@'%'; 
        CREATE USER '$HIVEUSER'@'localhost' IDENTIFIED BY '$HIVEPASSWORD';
        GRANT ALL PRIVILEGES ON *.* TO '$AMBARIUSER'@'localhost';
        CREATE USER'$HIVEUSER'@'$HIVESERVERFQDN' IDENTIFIED BY '$HIVEPASSWORD';
        GRANT ALL PRIVILEGES ON *.* TO '$AMBARIUSER'@'$HIVESERVERFQDN';
        FLUSH PRIVILEGES;

#### 5.3.2.3 配置默认schema以及初始化表结构

	create DATABASE ambari;
	SOURCE /var/lib/ambari-server/resources/Ambari-DDL-MySql-CREATE.sql

	create DATABASE hive;
	………………

#### 5.3.6 重新启动数据库服务
	systemctl restart mysql.service

至此，数据库的安装完毕。

### 5.4 拷贝正确的JDBC驱动至安装环境 （重要）
下载mysql驱动至目录 `/usr/share/java/`

        wget -Nv http://search.maven.org/remotecontent?filepath=mysql/mysql-connector-java/5.1.41/mysql-connector-java-5.1.41.jar

        
执行脚本完成数据库驱动的安装与配置

	ambari-server setup --jdbc-db=mysql --jdbc-driver=/usr/share/java/mysql-connector-java-5.1.41.jar


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
=============================================================================
Enter choice (3): 3
Hostname (ehdp-m1): ehdp-m1
Port (3306): 3306
Database name (ambari): ambari
Username (ambari): ambari
Enter Database Password (Efun-168): 
Configuring ambari database...
Copying JDBC drivers to server resources...
Configuring remote database connection properties...
WARNING: Before starting Ambari Server, you must run the following DDL against the database to create the schema: /var/lib/ambari-server/resources/Ambari-DDL-MySQL-CREATE.sql
Proceed with configuring remote database connection properties [y/n] (y)?
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
