# 配置集群Agent
## 介绍

## 步骤
## 安装Agent
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

### 2.2 安装agent
```
yum install cloudera-manager-agent
```

### 2.3 配置agent节点

```vi /etc/cloudera-scm-agent/config.ini ```

```
[General]
# Hostname of the CM server.
server_host=ehdp-mgr

# Port that the CM server is listening on.
server_port=7182

## It should not normally be necessary to modify these.
# Port that the CM agent should listen on.
listening_port=9000

# IP Address that the CM agent should listen on.
listening_ip=10.20.35.41

# Hostname that the CM agent reports as its hostname. If unset, will be
# obtained in code through something like this:
#
#   python -c 'import socket; \
#              print socket.getfqdn(), \
#                    socket.gethostbyname(socket.getfqdn())'
#
listening_hostname=ehdp-node-1

# An alternate hostname to report as the hostname for this host in CM.
# Useful when this agent is behind a load balancer or proxy and all
# inbound communication must connect through that proxy.
# reported_hostname=

# Port that supervisord should listen on.
# NB: This only takes effect if supervisord is restarted.
supervisord_port=19001

# Log file.  The supervisord log file will be placed into
# the same directory.  Note that if the agent is being started via the
# init.d script, /var/log/cloudera-scm-agent/cloudera-scm-agent.out will
# also have a small amount of output (from before logging is initialized).
log_file=/var/log/cloudera-scm-agent/cloudera-scm-agent.log

# Persistent state directory.  Directory to store CM agent state that
# persists across instances of the agent process and system reboots.
# Particularly, the agent's UUID is stored here.
lib_dir=/var/lib/cloudera-scm-agent

# Parcel directory.  Unpacked parcels will be stored in this directory.
# Downloaded parcels will be stored in <parcel_dir>/../parcel-cache
parcel_dir=/opt/cloudera/parcels
```

### 启动Agent
```
systemctl start cloudera-scm-agent
systemctl enable cloudera-scm-agent
```