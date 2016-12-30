CREATE TABLE IF NOT EXISTS `to_user_total_login` (
  `uid` bigint COMMENT '用户ID',
  `gameCode` varchar(20) COMMENT '游戏代码',
  `serverCode` varchar(32) COMMENT '序列ID',
  `gpid` int COMMENT '组ID',
  `appPlatform` varchar(50) COMMENT '游戏平台IOS或ANDROID',
  `thirdPlate` varchar(20) COMMENT '发行渠道',
  `firstLogin` timestamp COMMENT '每天首次登录时间',
  `lastLogin` timestamp COMMENT '每天最后登录时间',
  `total` int  COMMENT '每天登录次数',
  `address` varchar(50)  COMMENT '登录玩家所在区域',
  `ip` varchar(20)  COMMENT '登录玩家IP',
  `token` varchar(100)  COMMENT 'IOS玩家令牌(用于推送消息时查找近期未登入玩家)',
  `area` varchar(10)  COMMENT '来自于哪个地区(中东en,韩国kr,港台hk)',
  `mac` varchar(50)  COMMENT '设备标识',
  `imei` varchar(50)  COMMENT '设备标识',
  `countryCode` varchar(50)  COMMENT '国家简码',
  `cityCode` varchar(50)  COMMENT '省市简码'
)
COMMENT 'TO_用户登录明细表'
STORED AS PARQUET
LOCATION '/data/warehouse/ods/to_user_total_login';

CREATE TABLE IF NOT EXISTS  `to_ads_user_info` (
  `uid` bigint COMMENT 't_users表的uid',
  `registerIp` varchar(60)  COMMENT '注册ip',
  `gameCode` varchar(20)  COMMENT 'gameCode',
  `advertiser` varchar(50)  COMMENT '广告商',
  `incentives` int  COMMENT '是否为激励',
  `adType` varchar(20)  COMMENT '广告类型',
  `partner` varchar(50)  COMMENT '合作商',
  `campaignid` varchar(128)  COMMENT '广告id',
  `mac` varchar(50)  COMMENT 'mac地址',
  `imei` varchar(50)  COMMENT 'imei值',
  `androidid` varchar(50)  COMMENT 'androidid',
  `registerTime` timestamp  COMMENT '注册时间',
  `address` varchar(50)  COMMENT '注册地区',
  `gpid` int  COMMENT '合作商id',
  `area` varchar(10)  COMMENT '来自哪个地区的数据'
)
COMMENT 'TO_用户注册明细表'
STORED AS PARQUET
LOCATION '/data/warehouse/ods/to_ads_user_info';
;

CREATE TABLE IF NOT EXISTS  `tw_user_save_info` (
  `uid` bigint COMMENT '用户ID',
  `registerDate` timestamp  COMMENT '注册日期',
  `gameCode` varchar(20)  COMMENT 'gameCode',
  `advertiser` varchar(50)  COMMENT '广告商',
  `campaignid` varchar(128)  COMMENT '广告id',
  `saveCnt` double  COMMENT '连续登录游戏次数'
)
COMMENT 'TW_用户留存信息统计表'
STORED AS PARQUET
LOCATION '/data/warehouse/eds/tw_user_save_info';
;

//改进模型为字符串拼接
CREATE TABLE IF NOT EXISTS  `tw_user_save_info2` (
  `uid` bigint COMMENT '用户ID',
  `registerDate` timestamp  COMMENT '注册日期',
  `gameCode` varchar(20)  COMMENT 'gameCode',
  `advertiser` varchar(50)  COMMENT '广告商',
  `campaignid` varchar(128)  COMMENT '广告id',
  `loginIndex` string  COMMENT '第N天登录索引'
)
COMMENT 'TW_用户留存信息统计表'
STORED AS PARQUET
LOCATION '/data/warehouse/eds/tw_user_save_info2';
;

//创建临时清单表，消除表数据中的id序列

/** 第一次加载数据静茹TO的表中 **/
insert into to_ads_user_info
  select uid,registerip,gamecode,advertiser,incentives,adtype,partner,campaignid,mac,imei,androidid,cast(registertime as timestamp),address,gpid,area
  from t_ads_user_info;
insert into to_user_total_login
  select userid,gamecode,servercode,gpid,appplatform,thirdplate,
  cast(firstlogin as timestamp),cast(lastlogin as timestamp),
  total,address,ip,token,area,mac,imei,countrycode,citycode
  from t_user_total_login;

//对表进行汇总，形成UID留存用户表
insert into tw_user_save_info
  select t1.uid,trunc(t1.registertime,'J') as registerDate,t1.gamecode,t1.advertiser,t1.campaignid,sum(case when datediff(t2.firstLogin,t1.registertime)>=0 and datediff(t2.firstLogin,t1.registertime)<=30 then power(2,datediff(t2.firstLogin,t1.registertime)) else 0 end)
  from to_ads_user_info t1 inner join to_user_total_login t2 on t1.uid=t2.uid and t1.gameCode=t2.gameCode
  group by t1.uid,trunc(t1.registertime,'J'),t1.gamecode,t1.advertiser,t1.campaignid;

//改进方法，采用left join ，以注册数据为准
insert into tw_user_save_info2
  select t1.uid,trunc(t1.registertime,'J') as registerDate,t1.gamecode,t1.advertiser,t1.campaignid,group_concat(case when datediff(t2.firstLogin,t1.registertime)>=0 then cast(datediff(t2.firstLogin,t1.registertime) as string) else 'N' end)
  from to_ads_user_info t1 left join to_user_total_login t2 on t1.uid=t2.uid and t1.gameCode=t2.gameCode
  group by t1.uid,trunc(t1.registertime,'J'),t1.gamecode,t1.advertiser,t1.campaignid;

//形成次日留存的两个统计数字
insert into TM_USER_SAVE_INFO(ROWID,REGISTERDATE,GAMECODE,ADVERTISER,CAMPAIGNID,ADDCNT,DAY1SAVE,DAY3SAVE,DAY7SAVE,DAY15SAVE,DAY30SAVE)
  select cast(fnv_hash(registerdate)+fnv_hash(gamecode)+fnv_hash(advertiser)+fnv_hash(campaignid) as string),
  registerdate,gamecode,advertiser,campaignid,
  count(uid),
  sum(case when cast(saveCnt as bigint)&3=3 then 1 else 0 end) as day1save,
  sum(case when cast(saveCnt as bigint)&7=7 then 1 else 0 end) as day3save,
  sum(case when cast(saveCnt as bigint)&127=127 then 1 else 0 end) as day7ave,
  sum(case when cast(saveCnt as bigint)&32767=32767 then 1 else 0 end) as day15save,
  sum(case when cast(saveCnt as bigint)&1073741823=1073741823 then 1 else 0 end) as day1save
  from tw_user_save_info
  group by cast(fnv_hash(registerdate)+fnv_hash(gamecode)+fnv_hash(advertiser)+fnv_hash(campaignid) as string),registerdate,gamecode,advertiser,campaignid;

// in hbase
create 'TM_USER_SAVE_INFO', {NAME => 'INFO'}

// in hive
CREATE EXTERNAL TABLE IF NOT EXISTS  `TM_USER_SAVE_INFO` (
  `ROWID` string COMMENT '行ID',
  `REGISTERDATE` timestamp  COMMENT '注册日期',
  `GAMECODE` varchar(20)  COMMENT 'gameCode',
  `ADVERTISER` varchar(50)  COMMENT '广告商',
  `CAMPAIGNID` varchar(128)  COMMENT '广告id',
  `ADDCNT` bigint  COMMENT '新增用户数',
  `DAY1SAVE` bigint COMMENT '次日留存数',
  `DAY3SAVE` bigint COMMENT '3日留存数',
  `DAY7SAVE` bigint COMMENT '7日留存数',
  `DAY15SAVE` bigint COMMENT '15日留存数',
  `DAY30SAVE` bigint COMMENT '30日留存数'
)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ("hbase.columns.mapping" = ":key,\
  INFO:REGISTERDATE,\
  INFO:GAMCODE,\
  INFO:ADVERTISER,\
  INFO:CAMPAIGNID,\
  INFO:ADDCNT,\
  INFO:DAY1SAVE,\
  INFO:DAY3SAVE,\
  INFO:DAY7SAVE,\
  INFO:DAY15SAVE,\
  INFO:DAY30SAVE")
TBLPROPERTIES("hbase.table.name" = "TM_USER_SAVE_INFO");

// in impala
INVALIDATE METADATA;

//另外的方法比对,这种查找的速度明显要快很多。基本在1s内就可以出结果。
CREATE TABLE IF NOT EXISTS  `TM_USER_SAVE_INFO2` (
  `registerdate` timestamp  COMMENT '注册日期',
  `gamecode` varchar(20)  COMMENT 'gameCode',
  `advertiser` varchar(50)  COMMENT '广告商',
  `campaignid` varchar(128)  COMMENT '广告id',
  `addcnt` bigint  cOMMENT '新增用户数',
  `day1save` bigint COMMENT '次日留存数',
  `day3save` bigint COMMENT '3日留存数',
  `day7save` bigint COMMENT '7日留存数',
  `day15save` bigint COMMENT '15日留存数',
  `day30save` bigint COMMENT '30日留存数'
)COMMENT 'TM_用户留存信息统计表'
STORED AS PARQUET
LOCATION '/data/warehouse/dm/tm_user_save_info2';
;

insert into TM_USER_SAVE_INFO2
  select
  registerdate,gamecode,advertiser,campaignid,
  count(uid),
  sum(case when instr(concat(', ',loginindex,','),', 1,') > 0 then 1 else 0 end) as day1save,
  sum(case when instr(concat(', ',loginindex,','),', 3,') > 0 then 1 else 0 end) as day3save,
  sum(case when instr(concat(', ',loginindex,','),', 7,') > 0 then 1 else 0 end) as day7ave,
  sum(case when instr(concat(', ',loginindex,','),', 15,') > 0 then 1 else 0 end) as day15save,
  sum(case when instr(concat(', ',loginindex,','),', 30,') > 0 then 1 else 0 end) as day30save
  from tw_user_save_info2
  group by registerdate,gamecode,advertiser,campaignid;
