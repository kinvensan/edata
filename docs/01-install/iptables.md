# 建立防火墙

## 介绍

## 步骤
### 2.1 安装

```
yum install iptables-services iptables-devel
```

### 2.1 配置防火墙规则

`vi /etc/sysconfig/iptables`

```
# sample configuration for iptables service
# you can edit this manually or use system-config-firewall
# please do not ask us to add additional ports/services to this default configuration
*filter
:INPUT ACCEPT [0:0]
:FORWARD ACCEPT [0:0]
:OUTPUT ACCEPT [0:0]

-A INPUT -s 103.227.130.49/32 -p tcp -j ACCEPT
-A INPUT -s 14.18.155.130/32  -p tcp -j ACCEPT
-A INPUT -s 14.23.161.66/32   -p tcp -j ACCEPT
-A INPUT -s 10.20.35.40/32    -p tcp -j ACCEPT
-A INPUT -s 10.20.35.41/32    -p tcp -j ACCEPT
-A INPUT -s 10.20.35.42/32    -p tcp -j ACCEPT
-A INPUT -s 10.20.35.43/32    -p tcp -j ACCEPT
-A INPUT -s 10.20.35.44/32    -p tcp -j ACCEPT
-A INPUT -s 10.20.35.45/32    -p tcp -j ACCEPT
-A INPUT -s 10.20.35.46/32    -p tcp -j ACCEPT
-A INPUT -s 50.18.119.0/24 -p tcp -m tcp --dport 10050 -j ACCEPT
-A INPUT -s 218.32.219.0/24 -p tcp -m tcp --dport 10050 -j ACCEPT
-A INPUT -s 58.229.180.0/24 -p tcp -m tcp --dport 10050 -j ACCEPT
-A INPUT -s 203.69.109.0/24 -p tcp -m tcp --dport 10050 -j ACCEPT
-A INPUT -s 103.227.128.0/24 -p tcp -m tcp --dport 10050 -j ACCEPT
-A INPUT -s 128.1.39.120/32 -p tcp -m tcp --dport 10050 -j ACCEPT
-A INPUT -s 54.238.241.18/32 -p tcp -m tcp --dport 10050 -j ACCEPT
-A INPUT -s 50.18.119.120/32 -p tcp -m tcp --dport 10050 -j ACCEPT
-A INPUT -s 54.207.73.140/32 -p tcp -m tcp --dport 10050 -j ACCEPT
-A INPUT -s 54.93.169.149/32 -p tcp -m tcp --dport 10050 -j ACCEPT
-A INPUT -s 175.41.130.249/32 -p tcp -m tcp --dport 10050 -j ACCEPT
#-A INPUT -s 103.227.130.49/32 -p tcp -m multiport --dports 36000 -j ACCEPT
#-A INPUT -s 14.18.155.130/32 -p tcp -m multiport --dports 36000 -j ACCEPT
#-A INPUT -s 14.23.161.66/32 -p tcp -m multiport --dports 36000 -j ACCEPT
-A INPUT -p tcp -m tcp --dport 9092 -j ACCEPT
-A INPUT -p tcp -m tcp --dport 10050 -j DROP
-A INPUT -m state --state RELATED,ESTABLISHED -j ACCEPT
-A INPUT -p icmp -j ACCEPT
-A INPUT -i lo -j ACCEPT
#-A INPUT -p tcp -m state --state NEW -m tcp --dport 22 -j ACCEPT
-A INPUT -j REJECT --reject-with icmp-host-prohibited
-A FORWARD -j REJECT --reject-with icmp-host-prohibited
COMMIT
```

### 2.2 启动iptables

```
systemctl restart iptables.service
systemctl enable iptables.service
systemctl stop iptables.service 
systemctl status iptables.service 
```