# 安装CDH
## 介绍

## 步骤

### 2.1 增加cm5.repo

```
vi /etc/yum.repo.d/cm5.repo
```

```
[cloudera-manager]
# Packages for Cloudera Manager, Version 5, on RedHat or CentOS 7 x86_64
name=Cloudera Manager
baseurl=https://archive.cloudera.com/cm5/redhat/7/x86_64/cm/5/
gpgkey =https://archive.cloudera.com/cm5/redhat/7/x86_64/cm/RPM-GPG-KEY-cloudera
gpgcheck = 1
```

### 2.2 安装Server
- 在管理节点安装
```
yum install cloudera-manager-server
```

### 2.3 修改Manager节点配置
```
vi /etc/cloudera-scm-server/db.properties
```

```
# The database type
# Currently 'mysql', 'postgresql' and 'oracle' are valid databases.
com.cloudera.cmf.db.type=mysql

# The database host
# If a non standard port is needed, use 'hostname:port'
com.cloudera.cmf.db.host=10.20.35.40:3306

# The database name
com.cloudera.cmf.db.name=cmf

# The database user
com.cloudera.cmf.db.user=cmf

# The database user's password
com.cloudera.cmf.db.password=Cmf123.0

# The db setup type
# By default, it is set to INIT
# If scm-server uses Embedded DB then it is set to EMBEDDED
# If scm-server uses External DB then it is set to EXTERNAL
com.cloudera.cmf.db.setupType=EXTERNAL
```

### 启动manager
```
systemctl start cloudera-scm-server
systemclt enable cloudera-scm-server
```