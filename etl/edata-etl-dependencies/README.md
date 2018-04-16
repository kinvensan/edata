# ETL依赖管理工程
## 一、大致介绍
- 多个项目的maven管理，实现统一依赖类库的版本管理，使用`dependencyManagement`和`properties`统一管理类库。
- 消除多个parent引用，尤其是springboot应用，采用`dependencyManagement`-> `scope import` 实现其他项目的pom引入。
- 采用manven版本工具，统一管理父子项目的版本号更改

## 二、实现步骤
### 2.1 dependencies工程的 pom 文件
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>etl</artifactId>
        <groupId>com.efun.edata</groupId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.efun.edata</groupId>
    <artifactId>edata-etl-dependencies</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>pom</packaging>

    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <spring-boot.version>2.0.1.RELEASE</spring-boot.version>
        <beetlsql.version>2.8.28</beetlsql.version>
        <druid.version>1.1.3</druid.version>
        <mysql.version>5.1.43</mysql.version>
        <dexecutor.version>2.0.2</dexecutor.version>
        <quartz.version>2.3.0</quartz.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>${druid.version}</version>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
            </dependency>
            <!-- quartz 模块 -->
            <dependency>
                <groupId>org.quartz-scheduler</groupId>
                <artifactId>quartz</artifactId>
                <version>${quartz.version}</version>
            </dependency>
            <dependency>
                <groupId>org.quartz-scheduler</groupId>
                <artifactId>quartz-jobs</artifactId>
                <version>${quartz.version}</version>
            </dependency>
            <dependency>
                <groupId>com.github.dexecutor</groupId>
                <artifactId>dexecutor-core</artifactId>
                <version>${dexecutor.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <build>
        <plugins>
            <plugin>
                <artifactId>maven-help-plugin</artifactId>
                <inherited>false</inherited>
                <executions>
                    <execution>
                        <id>generate-effective-dependencies-pom</id>
                        <phase>generate-resources</phase>
                        <goals>
                            <goal>effective-pom</goal>
                        </goals>
                        <configuration>
                            <output>${project.build.directory}/effective-pom/spring-boot-dependencies.xml</output>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>build-helper-maven-plugin</artifactId>
                <inherited>false</inherited>
                <executions>
                    <execution>
                        <id>attach-artifacts</id>
                        <phase>package</phase>
                        <goals>
                            <goal>attach-artifact</goal>
                        </goals>
                        <configuration>
                            <artifacts>
                                <artifact>
                                    <file>${project.build.directory}/effective-pom/spring-boot-dependencies.xml</file>
                                    <type>effective-pom</type>
                                </artifact>
                            </artifacts>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### 2.2 其他项目添加 maven 引用包
其他项目的引用，加入`<dependencyManagement>`部分即可。
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>etl</artifactId>
        <groupId>com.efun.edata</groupId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.efun.edata</groupId>
    <artifactId>edata-dexecutor</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <dependencyManagement>
        <dependencies>
            <dependency>
            <!-- Import dependency management from edata Boot -->
                <groupId>com.efun.edata</groupId>
                <artifactId>edata-etl-dependencies</artifactId>
                <version>${project.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>com.github.dexecutor</groupId>
            <artifactId>dexecutor-core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>
</project>
```

### 父子项目版本号一次升级
- 为避免顶级pom和子pom版本变化时一个个修改，maven提供了下面的命令，以下命令必须在项目根目录执行（即etl项目的目录）
- 修改版本

```
mvn versions:set -DnewVersion=xxx
```

- 回滚版本，提交后不能回滚
```
mvn versions:revert
```
- 提交版本变更
```
mvn versions:commit
```

### 三、测试

- 增加依赖包
```
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz</artifactId>
</dependency>
```
- 更新版本
```
mvn versions:set -DnewVersion=1.0.1-SNAPSHOT
mvn versions:commit
```

    
- 执行编译和本地安装
```
mvn clean install
```
 

