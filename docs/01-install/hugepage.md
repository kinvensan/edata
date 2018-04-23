# 配置transparent_hugepage
## 介绍

## 步骤
### 2.1 备份grub
```
cp /etc/default/grub /etc/default/grub.bak
```

### 2.2 修改grub
```
vi /etc/default/grub
```

```
GRUB_TIMEOUT=5  
GRUB_DISTRIBUTOR="$(sed 's, release .*$,,g' /etc/system-release)"  
GRUB_DEFAULT=saved  
GRUB_DISABLE_SUBMENU=true  
GRUB_TERMINAL_OUTPUT="console"  
GRUB_CMDLINE_LINUX="rhgb quiet transparent_hugepage=never" ---->>> transparent_hugepage=never是新加的  
GRUB_DISABLE_RECOVERY="true" 
```

### 2.3 重新配置grub
```
grub2-mkconfig -o /boot/grub2/grub.cfg
```

### 2.4 配置swappiness

```
echo "vm.swappiness = 0" >> /etc/sysctl.conf
sysctl -p
```


### 2.5 重新启动系统
```
reboot
```