# 安装ntp时间同步

## 1. 介绍
## 2. 步骤
### 2.1 安装ntp服务
```
yum install ntp
```

### 2.2 配置ntp服务

``` vi /etc/ntp.conf ```

- 替换时区部分
```
server 0.cn.pool.ntp.org
server 1.asia.pool.ntp.org
server 2.asia.pool.ntp.org
```

- 替换限制部分
```
restrict 0.cn.pool.ntp.org nomodify notrap noquery
restrict 1.asia.pool.ntp.org nomodify notrap noquery
restrict 2.asia.pool.ntp.org nomodify notrap noquery
```

- 在限制部分下面增加
```
server 127.0.0.1 # local clock
fudge 127.0.0.1 stratum 10
```

### 2.3 同步时间
```
ntpdate asia.pool.ntp.org
```

### 2.4 设置时区
```
timedatectl  set-timezone Asia/Shanghai
```

### 2.4 启动
```
systemctl start ntpd.service
systemctl enable ntpd.service
```

## 测试

```
date
```