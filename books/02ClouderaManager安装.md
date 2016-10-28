第二章 Cloudera Manager的安装

1.主节点解压安装（主节点）
--------------
> 解压安装文件至/data 目录（Cloudera Manager默认安装在/opt目录）

```
tar -xzvf cloudera-manager*.tar.gz
mv cm-5.8.2 /data
mv cloudera /data
```

> 安装JDBC驱动

```
tar -xzvf mysql-connector-java-5.1.40.tar.gz
mv mysql-connector-java-5.1.40-bin.jar /data/cm-5.8.2/share/cmf/common_jars
cd /data/cm-5.8.2/share/cmf/lib
ln -s mysql-connector-java-5.1.40-bin.jar ../common_jars/mysql-connector-java-5.1.40-bin.jar
```

> 配置mysql

```
cm-5.3.3/share/cmf/schema/scm_prepare_database.sh mysql cm -hlocalhost -uroot -pxxxx --scm-host localhost scm scm scm
```

2.其他节点安装（从节点）
-------------


3.制作本地parcel（主节点）
-----------------

4.创建cloudera-scm用户（所有节点）
------------------------
```
useradd --system --home=/opt/cm-5.3.3/run/cloudera-scm-server/ --no-create-home --shell=/bin/false --comment "Cloudera SCM User" cloudera-scm
```
