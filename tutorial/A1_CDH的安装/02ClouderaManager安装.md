第二章 Cloudera Manager的安装

1.CM-Server节点安装（主节点）
----------------------
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

2.Agent节点安装（所有节点）
-----------------
> 修改Agent配置 
修改/data/cm-5.8.2/etc/cloudera-scm-agent/config.ini

```
[General]
# Hostname of the CM server.
server_host=hadoop-1
```

> 分发修改后的安装包至各个从节点
```
scp -r /data/cm-5.8.2 root@hadoop-2:/data/
scp -r /data/cm-5.8.2 root@hadoop-3:/data/
......
```

3.制作本地parcel（主节点）
-----------------
> 将CHD5相关的Parcel包放到主节点的/data/cloudera/parcel-repo/目录中

```
   [ * ]CDH-5.8.2-1.cdh5.8.2.p0.3-el6.parcel 
   [ * ]CDH-5.8.2-1.cdh5.8.2.p0.3-el6.parcel.sha1
   [ * ]manifest.json
   [ * ]KAFKA-2.0.2-1.2.0.2.p0.5-el6.parcel	 
   [ * ]KAFKA-2.0.2-1.2.0.2.p0.5-el6.parcel.sha1
```

> 制作sha文件,这点必须注意，否则，系统会重新下载CDH

```
    cp CDH-5.8.2-1.cdh5.8.2.p0.3-el6.parcel.sha1 CDH-5.8.2-1.cdh5.8.2.p0.3-el6.parcel.sha
    cp KAFKA-2.0.2-1.2.0.2.p0.5-el6.parcel.sha1 KAFKA-2.0.2-1.2.0.2.p0.5-el6.parcel.sha
```

4.创建cloudera-scm用户（所有节点）
------------------------
```
useradd --system --home=/opt/cm-5.3.3/run/cloudera-scm-server/ --no-create-home --shell=/bin/false --comment "Cloudera SCM User" cloudera-scm
```

5.启动服务(所有节点）
-----------------------------
> 主节点启动Server,启动服务端
```
/data/cm-5.3.3/etc/init.d/cloudera-scm-server start
```

> [ **所有节点** ] 启动Agent服务
```
/data/cm-5.3.3/etc/init.d/cloudera-scm-agent start
```

6.使用UI完成安装
----------
>
* 访问 http://hadoop-1:7180
* 使用 admin / admin进行登录界面
* 同意协议一路continue

> 为 CDH 群集安装指定主机

每行一个主机名称（为了确保安装过程顺利和成功，建议优先选择两台主机进行（后续可以通过向集群中添加主机的方式完成同样的安装）
```
hadoop-1
hadoop-2
.....
```


