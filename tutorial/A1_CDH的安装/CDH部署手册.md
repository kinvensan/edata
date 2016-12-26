# CDH部署手册

## 1. 前言

## 2. 配置环境
### 2.1 硬件配置

| HOST   | CPU | 内存 | 说明 |
| ------------ | ------------- | ------------ | ------------ | 
| EHDP-CM  | 8核  | 16G | 集群管理器，安装CM ，postgresql sNameNode 等|
| EHDP-M   | 8核  | 16G | 集群Master主节点，安装NameNode，historyServer等|
| EHDP-S1  | 16核 | 24G | 集群Slave从节点，安装zk，hdfs，yarn，hive，hbase，imapla，spark等 |
| EHDP-S2  | 16核 | 24G | 集群Slave从节点，安装zk，hdfs，yarn，hive，hbase，imapla，spark等|
| EHDP-S3  | 16核 | 24G | 集群Slave从节点，安装zk，hdfs，yarn，hive，hbase，imapla，spark等|

*其中datanode建议至少16G内存*

### 2.2 磁盘空间配置
| HOST   | 总空间 | ／ | ／data |
| ------------ | ------------- | ------------ | ------------ | 
| EHDP-CM  | 160G | 60G  | 100G |
| EHDP-M   | 160G | 60G  | 100G | 
| EHDP-S1  | 600G | 40G | 540G | 
| EHDP-S2  | 600G | 40G | 540G | 
| EHDP-S3  | 600G | 40G | 540G | 

*其中／由于要写入日志文件和安装软件，通常放在／opt目录下，建议要分配足够的空间*

### 2.3 其他配置

| IP   | HOST | 用户1 | 用户2 | ssh端口 | 
| ------------ | ------------- | ------------ | ------------ | 
| 172.16.5.  | EHDP-CM  | root  | etl | 36000 |
| 172.16.5.  | EHDP-M   | root  | etl | 36000 |
| 172.16.5.  | EHDP-S1  | root  | etl | 36000 |
| 172.16.5.  | EHDP-S2  | root  | etl | 36000 |
| 172.16.5.  | EHDP-S3  | root  | etl | 36000 |

*注： ssh命令 ssh –p 36000 root@ip*

*注 scp 命令 scp –P 36000 xxxx.tar.gz root@ip:/data*

*其中 "IP HOSt" 列写入到/etc/hosts文件中*

## 3. 建立本地库

为了加速CDH的安装和使用，建立本地库，提供给CM等下载和分配，以加快整个安装配置的过程。

### 3.1 下载软件
| 文件名称  	| 版本 	| url	|
| -----  	| -----	| ---	|
| cloudera-manager-installer.bin |   5.9.0   | http://archive.cloudera.com/cm5/installer/latest/cloudera-manager-installer.bin |
| cm5-tarball |  5.9.0   | http://archive.cloudera.com/cm5/repo-as-tarball/5.9.0/cm5.9.0-centos7.tar.gz |
| cd5.parcel  | 5.9.0  | http://archive.cloudera.com/cdh5/parcels/5/CDH-5.9.0-1.cdh5.9.0.p0.23-el7.parcel |
| cd5.parcel.sha1  | 5.9.0  | http://archive.cloudera.com/cdh5/parcels/5/CDH-5.9.0-1.cdh5.9.0.p0.23-el7.parcel.sha1 |
| cd5.manifest  | 5.9.0  | http://archive.cloudera.com/cdh5/parcels/5/manifest.json |
| kafka.parcel | 0.9 | http://archive.cloudera.com/kafka/parcels/latest/KAFKA-2.0.2-1.2.0.2.p0.5-el7.parcel |
| kafka.parcel.sha1 | 0.9 | http://archive.cloudera.com/kafka/parcels/latest/KAFKA-2.0.2-1.2.0.2.p0.5-el7.parcel.sha1 |
| kafka.manifest | 0.9 | http://archive.cloudera.com/kafka/parcels/latest/manifest.json |

### 3.2 安装与配置Apache（httpd）服务器
安装httpd服务器后直接采用link的方式替换 html 目录。


	yum install -y httpd wget curl
	mv /var/www/html /var/www/html2
	ln -s /data/cdh-repo/ /var/www/html
	
*注意要启动服务器（centos7)*

 
    systemctl start httpd.service
    systemctl enable httpd.service


### 3.3 配置库目录索引

```
./cdh5
./cdh5/parcels
./cdh5/parcels/5
./cdh5/parcels/5/CDH-5.9.0-1.cdh5.9.0.p0.23-el7.parcel
./cdh5/parcels/5/CDH-5.9.0-1.cdh5.9.0.p0.23-el7.parcel.sha1
./cdh5/parcels/5/manifest.json
./index.html
./cm5
./cm5/5
./cm5/5.9
./cm5/RPM-GPG-KEY-cloudera
./cm5/cloudera-cm.repo
./cm5/5.9.0
./cm5/5.9.0/mirrors
./cm5/5.9.0/RPMS
./cm5/5.9.0/RPMS/noarch
./cm5/5.9.0/RPMS/x86_64
./cm5/5.9.0/RPMS/x86_64/cloudera-manager-agent-5.9.0-1.cm590.p0.249.el7.x86_64.rpm
./cm5/5.9.0/RPMS/x86_64/cloudera-manager-daemons-5.9.0-1.cm590.p0.249.el7.x86_64.rpm
./cm5/5.9.0/RPMS/x86_64/cloudera-manager-server-5.9.0-1.cm590.p0.249.el7.x86_64.rpm
./cm5/5.9.0/RPMS/x86_64/cloudera-manager-server-db-2-5.9.0-1.cm590.p0.249.el7.x86_64.rpm
./cm5/5.9.0/RPMS/x86_64/enterprise-debuginfo-5.9.0-1.cm590.p0.249.el7.x86_64.rpm
./cm5/5.9.0/RPMS/x86_64/jdk-6u31-linux-amd64.rpm
./cm5/5.9.0/RPMS/x86_64/oracle-j2sdk1.7-1.7.0+update67-1.x86_64.rpm
./cm5/5.9.0/repodata
./cm5/5.9.0/repodata/filelists.xml.gz
./cm5/5.9.0/repodata/filelists.xml.gz.asc
./cm5/5.9.0/repodata/other.xml.gz
./cm5/5.9.0/repodata/other.xml.gz.asc
./cm5/5.9.0/repodata/primary.xml.gz
./cm5/5.9.0/repodata/primary.xml.gz.asc
./cm5/5.9.0/repodata/repomd.xml
./cm5/5.9.0/repodata/repomd.xml.asc
./kafka
./kafka/parcels
./kafka/parcels/latest
./kafka/parcels/latest/KAFKA-2.0.2-1.2.0.2.p0.5-el7.parcel
./kafka/parcels/latest/KAFKA-2.0.2-1.2.0.2.p0.5-el7.parcel.sha1
./kafka/parcels/latest/manifest.json
./install
./install/jdk.rpm
./install/cloudera-manager-installer.bin
./install/postgresql-jdbc.jar
```

### 3.4 测试是否安装成功

	curl http://localhost

	<html>
	<body><p> welcome to cdh repo </p></body>
	</html>

## 4. 建立CDH环境服务器（所有机器）
### 4.1 配置DNS和HOSTS
由于要使用yum的源，建议把DNS更改为114.114.114.114
	
	echo "nameserver 114.114.114.114" > /etc/resolv.conf
	
按照第一节中的IP和HOST，配置/etc/hosts文件

### 4.2 安装与配置软件ntp软件

	yum -y install ntp wget
	
#### 4.2.1 主NTP服务器配置（ehdp-cm）
修改/etc/ntp.conf文件


	# Hosts on local network are less restricted.
	#restrict 192.168.1.0 mask 255.255.255.0 nomodify notrap
	restrict 172.16.5.0 mask 255.255.255.0 nomodify notrap

	# Use public servers from the pool.ntp.org project.
	# Please consider joining the pool (http://www.pool.ntp.org/join.html).
	server 2.cn.pool.ntp.org
	server 1.asia.pool.ntp.org
	server 2.asia.pool.ntp.org

	#broadcast 192.168.1.255 autokey	# broadcast server
	#broadcastclient			# broadcast client
	#broadcast 224.0.1.1 autokey		# multicast server
	#multicastclient 224.0.1.1		# multicast client
	#manycastserver 239.255.254.254		# manycast server
	#manycastclient 239.255.254.254 autokey # manycast client

	# 允许上层时间服务器主动修改本机时间
	restrict 2.cn.pool.ntp.org nomodify notrap noquery
	restrict 1.asia.pool.ntp.org nomodify notrap noquery
	restrict 2.asia.pool.ntp.org nomodify notrap noquery

	server 127.0.0.1 # local clock
	fudge 127.0.0.1 stratum 10


#### 4.2.2 从NTP服务器(ehdp-*)
修改/etc/ntp.conf文件

	# Use public servers from the pool.ntp.org project.
	# Please consider joining the pool (http://www.pool.ntp.org/join.html).
	server ehdp-cm
	server 127.0.0.1 # local clock
	fudge 127.0.0.1 stratum 10

#### 4.2.3 启动NTP服务
	ntpdate ehdp-cm
	systemctl start ntpd.service
	systemctl enable ntpd.service
	
#### 4.2.4 测试时间是否同步
	date
	
	2016年 12月 25日 星期日 22:20:29 CST

### 4.3 安装与配置jdk
安装JDK

	yum install http://ehdp-cm/install/jdk.rpm
	
配置JDK，在/etc/profile,末尾追加
	
	export JAVA_HOME=/usr/java/default
	export PATH=$PATH:$JAVA_HOME/bin

使环境变量马上生效，执行
	
	source /etc/profile
	
### 4.4 生成和配置SSH私钥
#### 4.4.1 生成SSH私钥（在ehdp-cm） 
	ssh-keygen -t RSA  #一路回车
	cd ~/.ssh
	cat id_rsa.pub >> authorized_keys

把文件拷贝到http服务器上进行分发

	tar -cvf /var/www/html/install/ssh-key.tar ~/.ssh
	
#### 4.4.2 把ssh拷贝到所有的机器（在ehdp-*）
	wget -Nv http://ehdp-cm/install/ssh-key.tar
	tar -xvf ssh-key.tar ~/
	rm -rf ssh-key.tar
	
### 4.5 配置transparent_hugepage

	echo never > /sys/kernel/mm/transparent_hugepage/enabled
	echo never > /sys/kernel/mm/transparent_hugepage/defrag
	
	echo "echo never > /sys/kernel/mm/transparent_hugepage/enabled" >> /etc/rc.local
	echo "echo never > /sys/kernel/mm/transparent_hugepage/defrag" >> /etc/rc.local
	
### 4.6 配置vm.swappiness

	echo "vm.swappiness = 0" >> /etc/sysctl.conf
	sysctl -p
	
## 5. 建立CM服务器
### 5.1 安装CM-SERVER
#### 5.1.1 配置repo库
下载配置库文件

	cd /etc/yum.repos.d/
	wget -Nv http://ehdp-cm/cm5/cloudera-cm.repo
	
编辑cloudera-cm.repo 修改内容为

	baseurl=http://ehdp-cm/cm5/5/
	gpgkey = http://ehdp-cm/cm5/RPM-GPG-KEY-cloudera
	
#### 5.1.2 下载并安装CM-server

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
		
## 6.安装集群
以下内容均在浏览器中进行，优先下载
	
	http://ehdp-cm/install/ssh.tar

以下内容均在浏览器中打开
###6.1 同意安装协议
	
###6.2 选择CDH版本为EXPRESS

###6.3 定位服务器

###6.4 修改parcel配置

###6.5 修改agent配置

###6.7 安装AGENT服务

###6.8 安装Alert等服务

## 7.增加CDH服务
以下服务均是按照顺序增加和配置的。

###7.1 增加和配置zookeeper

| 配置项	| 值 	|
| ---	| ---	|
| host 	| ehdp-s1,ehdp-s2,ehdp-s3|

###7.2 增加和配置HDFS
| 配置项	| 值 	|
| ---	| ---	|
| namenode 	| |	
| namenode 	| |	
	
	

###7.3 增加和配置YARN
| 配置项	| 值 	|
| ---	| ---	|
| namenode 	| |	
| namenode 	| |		

###7.4 增加和配置HIVE

#### metastore配置
	选择postgresql数据库
	填写url为 ehdp-cm
	测试通过即可
	
| 配置项	| 值 	|
| ---	| ---	|
| namenode 	| |	
| namenode 	| |	

###7.5 增加和配置HBASE
| 配置项	| 值 	|
| ---	| ---	|
| namenode 	| |	
| namenode 	| |	
#### 内存配置
	增加内存配置至2G

###7.6 增加和配置IMPALA并配置
| 配置项	| 值 	|
| ---	| ---	|
| namenode 	| |	
| namenode 	| |	
#### 内存配置
	增加内存配置至2G

###7.7 增加和配置SPARK
| 配置项	| 值 	|
| ---	| ---	|
| namenode 	| |	
| namenode 	| |	

###7.8 配置HIVE使用SPARK
#### HIVE打开SPARK
#### HIVE默认使用SPARK




