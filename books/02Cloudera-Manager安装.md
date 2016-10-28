请参考链接 http://blog.csdn.net/u010022051/article/details/51205765

1.下载rpm安装包

rpm安装包下载地址：其中jdk如果自己安装好了可以不用下载。
http://archive.cloudera.com/cm5/redhat/6/x86_64/cm/5.7/RPMS/x86_64/

包含软件：

 


2.master节点安装

将下载好的rpm包放到一个文件夹中，任意命名，进入到这个文件夹手动安装：
yum localinstall --nogpgcheck *.rpm
yum install --installroot=/usr/src/ 
使用yum安装会同时安装相关的依赖，非常方便
如果要卸载使用
yum --setopt=tsflags=noscripts remove xxxx

3.slave节点安装

slave中不需要安装server的包，只需要安装cloudera-manager-agent.rpm和cloudera-manager-daemons.rpm。先将两个rpm包拷贝到slave节点上，剩下安装方法和master一样。


4.安装cloduera manager二进制安装包


wget
 [url=http://archive.cloudera.com/cm5/installer/latest/cloudera-manager-installer.bin]http://archive.cloudera.com/cm5/

... nager-installer.bin[/url]

 

2)chmod

u+x cloudera-manager-installer.bin

 

3)./cloudera-manager-installer.bin


4)根据安装向导一路next。注意，如果之前master上没有手动安装rpm包此时就会联网下载，下载速度一般都较慢，太费时间。

5)安装结束以后会提示登录7180端口。