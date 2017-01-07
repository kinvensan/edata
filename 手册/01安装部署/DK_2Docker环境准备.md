# Docker安装部署环境准备
本节是环境章节的补充资料

## 使用docker-compose
docker的环境由于涉及比较多的环节，所以采用docker-compose来进行管理和启动
下载`参考`中已有的docker-compose.yml文件后，一般docker-compose的命令使用如下

    #使用后台命令启动容器
    docker-compose up -d

    #停止容器
    docker-compose stop

    #删除容器
    docker-compose rm

## 构建jdk卷（volumn）
使用一个通用的卷来包含JDK环境，在后续安装jdk后，把jdk内容拷贝到`/usr/java/default目录想`中

    docker volume create --name jdk


注意docker的环境变量应该设置在`container.env`中

## 构建bridge网络

构建一个bridge网络用于所有的容器

  docker network create hdp.net


## 构建Base容器
### 用Compose启动容器
用centos-base.yml来启动base容器

    docker-compose -f centos7-base.yml up -d

然后进入docker容器中

    docker exec -it centos-base /bin/bash

### 在容器中安装软件
针对docker中centos环境的不足，需要在环境中安装如下内容

    yum -y install perl wget curl
    yum -y install openssh-server
    yum -y install ntp
    yum -y install psmisc initscripts policycoreutils

设置root用户的密码

    echo "root:efun" | chpasswd

### 配置SSH服务器和ntp服务器

请参考章节[4CDH的环境配置](./4CDH的环境准备.md)


## 导出和导入镜像
导出镜像，（注意要先停止容器）

    docker-compose -f centos7-base.yml stop
    docker export -o centos7-base-1.0.tar centos-base
    docker-compose -f centos7-base.yml rm

导入镜像，如果之前的镜像是存在的，请先自行删除
    docker import centos7-base-1.0.tar centos/base:1.0


## 参考
### centos 7 的 `docker-compose.yml`文件
