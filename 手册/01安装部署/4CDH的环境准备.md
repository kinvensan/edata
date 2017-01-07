## 4. 建立CDH环境服务器（所有机器）
### 4.1 配置DNS和HOSTS
由于要使用yum的源，建议把DNS更改为114.114.114.114

	echo "nameserver 114.114.114.114" > /etc/resolv.conf

按照第一节中的IP和HOST，配置/etc/hosts文件

### 4.2 安装与配置软件ntp软件

	yum -y install ntp wget
	yum -y install psmisc initscripts policycoreutils

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
	ssh-keygen   #一路回车
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
