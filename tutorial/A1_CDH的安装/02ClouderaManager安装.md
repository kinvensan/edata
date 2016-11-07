�ڶ��� Cloudera Manager�İ�װ

1.CM-Server�ڵ㰲װ�����ڵ㣩
----------------------
> ��ѹ��װ�ļ���/data Ŀ¼��Cloudera ManagerĬ�ϰ�װ��/optĿ¼��

```
tar -xzvf cloudera-manager*.tar.gz
mv cm-5.8.2 /data
mv cloudera /data
```

> ��װJDBC����

```
tar -xzvf mysql-connector-java-5.1.40.tar.gz
mv mysql-connector-java-5.1.40-bin.jar /data/cm-5.8.2/share/cmf/common_jars
cd /data/cm-5.8.2/share/cmf/lib
ln -s mysql-connector-java-5.1.40-bin.jar ../common_jars/mysql-connector-java-5.1.40-bin.jar
```

> ����mysql

```
cm-5.3.3/share/cmf/schema/scm_prepare_database.sh mysql cm -hlocalhost -uroot -pxxxx --scm-host localhost scm scm scm
```

2.Agent�ڵ㰲װ�����нڵ㣩
-----------------
> �޸�Agent���� 
�޸�/data/cm-5.8.2/etc/cloudera-scm-agent/config.ini

```
[General]
# Hostname of the CM server.
server_host=hadoop-1
```

> �ַ��޸ĺ�İ�װ���������ӽڵ�
```
scp -r /data/cm-5.8.2 root@hadoop-2:/data/
scp -r /data/cm-5.8.2 root@hadoop-3:/data/
......
```

3.��������parcel�����ڵ㣩
-----------------
> ��CHD5��ص�Parcel���ŵ����ڵ��/data/cloudera/parcel-repo/Ŀ¼��

```
   [ * ]CDH-5.8.2-1.cdh5.8.2.p0.3-el6.parcel 
   [ * ]CDH-5.8.2-1.cdh5.8.2.p0.3-el6.parcel.sha1
   [ * ]manifest.json
   [ * ]KAFKA-2.0.2-1.2.0.2.p0.5-el6.parcel	 
   [ * ]KAFKA-2.0.2-1.2.0.2.p0.5-el6.parcel.sha1
```

> ����sha�ļ�,������ע�⣬����ϵͳ����������CDH

```
    cp CDH-5.8.2-1.cdh5.8.2.p0.3-el6.parcel.sha1 CDH-5.8.2-1.cdh5.8.2.p0.3-el6.parcel.sha
    cp KAFKA-2.0.2-1.2.0.2.p0.5-el6.parcel.sha1 KAFKA-2.0.2-1.2.0.2.p0.5-el6.parcel.sha
```

4.����cloudera-scm�û������нڵ㣩
------------------------
```
useradd --system --home=/opt/cm-5.3.3/run/cloudera-scm-server/ --no-create-home --shell=/bin/false --comment "Cloudera SCM User" cloudera-scm
```

5.��������(���нڵ㣩
-----------------------------
> ���ڵ�����Server,���������
```
/data/cm-5.3.3/etc/init.d/cloudera-scm-server start
```

> [ **���нڵ�** ] ����Agent����
```
/data/cm-5.3.3/etc/init.d/cloudera-scm-agent start
```

6.ʹ��UI��ɰ�װ
----------
>
* ���� http://hadoop-1:7180
* ʹ�� admin / admin���е�¼����
* ͬ��Э��һ·continue

> Ϊ CDH Ⱥ����װָ������

ÿ��һ���������ƣ�Ϊ��ȷ����װ����˳���ͳɹ�����������ѡ����̨�������У���������ͨ����Ⱥ����������ķ�ʽ���ͬ���İ�װ��
```
hadoop-1
hadoop-2
.....
```


