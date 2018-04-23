# 安装java环境

## 介绍

### 2.1 下载jdk

### 2.2 建立规范目录
- 解压文件至`/usr/java/目录下`
- 建立link `ln -s /usr/java/jdk1.8.0_162/jre /usr/java/default`

`cp /usr/share/java/mysql-connector-java.jar`

### 2.3 建立环境
在/etc/profile,末尾追加
```
export JAVA_HOME=/usr/java/default
export PATH=$PATH:$JAVA_HOME/bin
```