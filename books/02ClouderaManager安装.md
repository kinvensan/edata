�ڶ��� Cloudera Manager�İ�װ

1.���ڵ��ѹ��װ�����ڵ㣩
--------------
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

2.�����ڵ㰲װ���ӽڵ㣩
-------------


3.��������parcel�����ڵ㣩
-----------------

4.����cloudera-scm�û������нڵ㣩
------------------------
```
useradd --system --home=/opt/cm-5.3.3/run/cloudera-scm-server/ --no-create-home --shell=/bin/false --comment "Cloudera SCM User" cloudera-scm
```
