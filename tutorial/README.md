EFUN������ƽ̨��������ָ���ֲ�
=================
һ�����
----------------
    CDH (Cloudera��s Distribution, including Apache Hadoop)����Hadoop�ڶ��֧�е�һ�֣���Clouderaά���������ȶ��汾��Apache Hadoop�������������˺ܶಹ������ֱ���������������� 
    Cloudera Manager����Ϊ�˱����ڼ�Ⱥ�н���Hadoop�ȴ����ݴ�����صķ���װ�ͼ�ع����������Լ�Ⱥ��������Hadoop��Hive��Spark�ȷ���İ�װ���ù������˼���򻯡�

������������
------

### �������
```
1.����ϵͳ��Centos6.5

2.CDH�����: �汾5.8��Cloudra Manager�汾5.8.2
   Cloudera Manager��ַ��http://archive.cloudera.com/cm5/cm/5/ 
   [ * ]cloudera-manager-el6-cm5.8.2_x86_64.tar.gz
   
   CDH���߰���ַ��http://archive.cloudera.com/cdh5/parcels/
   [ * ]CDH-5.8.2-1.cdh5.8.2.p0.3-el6.parcel 
   [ * ]CDH-5.8.2-1.cdh5.8.2.p0.3-el6.parcel.sha1
   [ * ]manifest.json
   
   CDH��kafka���߰���ַ��http://archive.cloudera.com/kafka/parcels/latest/
   [ * ]KAFKA-2.0.2-1.2.0.2.p0.5-el6.parcel	 
   [ * ]KAFKA-2.0.2-1.2.0.2.p0.5-el6.parcel.sha1	 

3.JDK�汾oracle jdk-8u101-linux-x64.tar.gz
    http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
   [ * ] Linux x64	jdk-8u111-linux-x64.tar.gz
   
4.MYSQL���ݿ⣺
    MYSQL ���ݿ��ַ��http://download.softagency.net/MySQL/Downloads/MySQL-5.7/
    [ * ] mysql-5.7.15-linux-glibc2.5-x86_64.tar  
    
    MYSQL JDBC��ַ��  http://download.softagency.net/MySQL/Downloads/Connector-J/
    [ * ] mysql-connector-java-5.1.40.tar.gz
```

### Ӳ������
> 8̨������ڵ�Ӳ����������

|��������� |���� |IP |������ |�������� |�ڴ� | 
|--- |--- |--- |--- |--- |--- |
|hadoop-1  |HDP-1,HDP-M |172.16.7.28 | 8 | 200G |16G |
|hadoop-2  |HDP-2       |172.16.7.30 | 8 | 200G |16G |
|hadoop-3  |HDP-3       |172.16.7.31 | 8 | 200G |16G |
|hadoop-4  |HDP-4       |172.16.7.32 | 8 | 200G |16G |
|hadoop-5  |ETL-1       |172.16.7.35 | 4 | 200G |16G |
|hadoop-6  |ETL-2       |172.16.7.37 | 4 | 200G |16G |
|hadoop-7  |ETL-3       |172.16.7.38 | 4 | 200G |16G |
|hadoop-8  |WEB-1       |172.16.7.39 | 4 | 200G |16G |

