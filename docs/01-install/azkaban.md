# 调度程序azkaban安装
## 1.介绍

## 2.步骤
### 2.1 解压

/opt/azkaban-solo-server目录中

### 2.2 配置调整

```
# Azkaban Personalization Settings
azkaban.name=ETLJOB
azkaban.label=ETL Azkaban
azkaban.color=#FF3601
azkaban.default.servlet.path=/index
web.resource.dir=web/
default.timezone.id=Asia/Shanghai
# Azkaban UserManager class
user.manager.class=azkaban.user.XmlUserManager
user.manager.xml.file=conf/azkaban-users.xml
# Loader for projects
executor.global.properties=conf/global.properties
azkaban.project.dir=projects
database.type=h2
h2.path=./h2
h2.create.tables=true
# Velocity dev mode
velocity.dev.mode=false
# Azkaban Jetty server properties.
jetty.use.ssl=false
jetty.maxThreads=25
jetty.port=18082
# Azkaban Executor settings
executor.port=12321
# mail settings
mail.sender=
mail.host=
# User facing web server configurations used to construct the user facing server URLs. They are useful when there is a reverse proxy between Azkaban web servers and users.
# enduser -> myazkabanhost:443 -> proxy -> localhost:8081
# when this parameters set then these parameters are used to generate email links. 
# if these parameters are not set then jetty.hostname, and jetty.port(if ssl configured jetty.ssl.port) are used.
# azkaban.webserver.external_hostname=myazkabanhost.com
# azkaban.webserver.external_ssl_port=443
# azkaban.webserver.external_port=8081
job.failure.email=
job.success.email=
lockdown.create.projects=false
cache.directory=cache
# JMX stats
jetty.connector.stats=true
executor.connector.stats=true
# Azkaban plugin settings
azkaban.jobtype.plugin.dir=plugins/jobtypes
```

### 修改启动脚本
```
vi start-solo.sh
```

```
#!/bin/bash

script_dir=$(dirname $0)

${script_dir}/internal/internal-start-solo-server.sh "$@" > /var/log/azkaban/soloServerLog__`date +%F`.out 2>&1 &
```

### 启动服务和关闭服务
一定要在/opt/azkaban-solo-server目录中启动

```
./bin/start-solo.sh
```

```
./bin/shutdown-solo.sh
```