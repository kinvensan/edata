# systemd配置

## 介绍

## 步骤
### 在“/usr/lib/systemd/system”建立mindocd.service文件
```
[Unit]
Description=Mindoc daemon
After=network.target mysql.service
Wants=mysql.service

[Service]
EnvironmentFile=/etc/sysconfig/mindoc
User=root
Group=root
Type=simple
ExecStart=$MINDDOC $CONF_OPTIONS $LOG_OPTIONS
Restart=on-failure
RestartSec=10s

[Install]
WantedBy=multi-user.target
```

### 在/etc/sysconfig/中建立mindoc配置文件
```
# Configuration file for the mindoc service.
MINDOC=/opt/mindoc/mindoc_linux_amd64
CONF_OPTIONS=-config="/opt/mindoc/conf/app.conf"
LOG_OPTIONS=-log="/var/log/mindoc"
```

### 测试
```
systemctl list-unit-files | grep mindocd
```
```
systemctl start mindocd.service
systemctl enable mindocd.service
```