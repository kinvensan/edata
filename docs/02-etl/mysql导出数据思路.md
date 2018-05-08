1. 通过mysql dump语句导出数据

2. 拆分SQL语句 
split -l 5000 test.2012-08-16_17 tablename_

3. hive表倒入


通过sqoop进行操作

sqoop import --connect jdbc:mysql://server.foo.com/db --table bar \
    --direct -- --default-character-set=latin1


mysqldump -u root -p cmf AUDITS -t -T /data

mysqldump --skip-lock-tables -u root -p -t cmf AUDITS --fields-enclosed-by=\" --fields-terminated-by=, > /data/info.sql

mysqldump --skip-lock-tables -u root -p -t cmf AUDITS > /data/info.sql

mysqldump --skip-lock-tables --no-create-info --skip-comments --skip-add-locks -u root -p cmf AUDITS > info4.sql

//变成csv文件
sed -i '/\/\*!/'d info4.sql
sed -i 's/),(/\n/g' info4.sql
sed -i 's/(/\n/g;s/);//g' info4.sql
sed -i '/INSERT/d;/^[[:space:]]*$/d' info4.sql


CREATE TABLE AUDITS (
  AUDIT_ID bigint ,
  SERVICE_ID bigint ,
  ROLE_ID bigint ,
  CREATED_INSTANT bigint ,
  MESSAGE string,
  ACTING_USER_ID bigint ,
  COMMAND_ID bigint ,
  USER_ID bigint ,
  HOST_ID bigint ,
  AUDIT_TYPE string  ,
  CONFIG_CONTAINER_ID bigint ,
  CLUSTER_ID bigint ,
  OPTIMISTIC_LOCK_VERSION bigint ,
  HOST_TEMPLATE_ID bigint ,
  IP_ADDRESS string ,
  ALLOWED int  ,
  EXTERNAL_ACCOUNT_ID bigint 
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE;  


LOAD DATA LOCAL INPATH '/data/info5.sql' INTO TABLE AUDITS;