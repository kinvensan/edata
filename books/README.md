EFUN大数据平台建立过程指导手册
=================
一、简介
----------------
    CDH (Cloudera’s Distribution, including Apache Hadoop)，是Hadoop众多分支中的一种，由Cloudera维护，基于稳定版本的Apache Hadoop构建，并集成了很多补丁，可直接用于生产环境。 
    Cloudera Manager则是为了便于在集群中进行Hadoop等大数据处理相关的服务安装和监控管理的组件，对集群中主机、Hadoop、Hive、Spark等服务的安装配置管理做了极大简化。

二、基础环境
------

### 软件环境
```
1.操作系统：Centos6.5

2.CDH软件包: 版本5.8、Cloudra Manager版本5.8.2
   Cloudera Manager地址：http://archive.cloudera.com/cm5/cm/5/ 
   [ * ]cloudera-manager-el6-cm5.8.2_x86_64.tar.gz
   
   CDH离线包地址：http://archive.cloudera.com/cdh5/parcels/
   [ * ]CDH-5.8.2-1.cdh5.8.2.p0.3-el6.parcel 
   [ * ]CDH-5.8.2-1.cdh5.8.2.p0.3-el6.parcel.sha1
   [ * ]manifest.json
   
   CDH的kafka离线包地址：http://archive.cloudera.com/kafka/parcels/latest/
   [ * ]KAFKA-2.0.2-1.2.0.2.p0.5-el6.parcel	 
   [ * ]KAFKA-2.0.2-1.2.0.2.p0.5-el6.parcel.sha1	 

3.JDK版本oracle jdk-8u101-linux-x64.tar.gz
    http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
   [ * ] Linux x64	jdk-8u111-linux-x64.tar.gz
   
4.MYSQL数据库：
    MYSQL 数据库地址：http://download.softagency.net/MySQL/Downloads/MySQL-5.7/
    [ * ] mysql-5.7.15-linux-glibc2.5-x86_64.tar  
    
    MYSQL JDBC地址：  http://download.softagency.net/MySQL/Downloads/Connector-J/
    [ * ] mysql-connector-java-5.1.40.tar.gz
```

### 硬件环境
> 8台虚拟机节点硬件配置如下

|虚拟机名称 |别名 |IP |核心数 |磁盘容量 |内存 | 
|--- |--- |--- |--- |--- |--- |
|hadoop-1  |HDP-1,HDP-M |172.16.7.28 | 8 | 200G |16G |
|hadoop-2  |HDP-2       |172.16.7.30 | 8 | 200G |16G |
|hadoop-3  |HDP-3       |172.16.7.31 | 8 | 200G |16G |
|hadoop-4  |HDP-4       |172.16.7.32 | 8 | 200G |16G |
|hadoop-5  |ETL-1       |172.16.7.35 | 4 | 200G |16G |
|hadoop-6  |ETL-2       |172.16.7.37 | 4 | 200G |16G |
|hadoop-7  |ETL-3       |172.16.7.38 | 4 | 200G |16G |
|hadoop-8  |WEB-1       |172.16.7.39 | 4 | 200G |16G |

