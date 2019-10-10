# Voter
:cn: Simple online vote system based on Spring Boot | 基于SpringBoot的简约投票系统 | :sunglasses: COOL & MODERN | ANONYMOUS | Bootstrap | :cupid: Good looking

:construction_worker: 部署中遇到问题？:heavy_plus_sign: 我的微信号：1101635162 :ok_hand: 无偿帮助你解决问题。

[中文说明](#中文说明)

[English Version](#english-version)

# 预览 Preview

[公开测试网站 | Public Test Website](https://voter.stackoverflow.wiki/)

![DEMO](/images/Demo-Full.gif)

# 中文说明

## 运行

本项目使用Intellij IDEA构建，你可以使用IDEA直接调试本项目。

### 配置MySQL数据库

Voter默认使用MySQL中的`Voter`库。

```$xslt
在开始使用之前，请在你的MySQL中先创建一个名为"Voter"的数据库。
```

#### 自动生成表

Voter集成了自动生成数据表的功能。编辑类`pers.adlered.voter.configuration.MySQL`类使项目在启动时自动部署数据表。

**注意：配置此类只是为了自动生成数据表，如果你想为Voter配置读写数据库连接，请配置application.properties文件中的参数。**

> 请注意：如果你想手动建表，可以执行下列语句：

```$xslt
USE Voter;

CREATE TABLE `Voter_Vote` (
  `VID` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Describe` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Selection` varchar(10000) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Type` int(11) DEFAULT '0',
  `Limit` int(11) DEFAULT '-1',
  PRIMARY KEY (`VID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

INSERT INTO `Voter_Vote` VALUES (1, 'This is a vote title :)', 'And here is a describe of the vote XD', '<%1<%92<%Vote for Xiaoli<%2<%42<%Vote for Zhanghua<%3<%41<%Vote for Ergou<%4<%37<%Vote for Guawazi<%5<%48<%Vote for Benwei<%6<%50<%Vote for Adler', 0, 2);
```

#### 配置数据库连接

当建立完成数据库后，配置`application.properties`文件来配置数据库连接。

## 工作原理

### 实现一串字符串存储所有投票选项统计

存储在数据库中的`SelectionText`字段如下：

```$xslt
<%1<%0<%Vote for Xiaoli
<%2<%10<%Vote for Zhanghua
<%3<%20<%Vote for Ergou
<%4<%30<%Vote for Guawazi
<%5<%40<%Vote for Benwei
<%6<%50<%Vote for Adler
```

规定的投票存储语法如下：

```$xslt
<%选项号<%获得票数<%选项描述
```

通过`pers.adlered.voter.analyzer`中的方法对此字段实现分析与修改。

# English Version

## Run

This project is built using Intellij IDEA, you can use IDEA to debug this project directly.

### Configuring the MySQL database

Voter defaults to the `Voter` library in MySQL.

```$xslt
Before you start using it, create a database called "Voter" in your MySQL.
```

#### Automatically generating tables

Voter integrates the ability to automatically generate data tables. Editing the class `pers.adlered.voter.configuration.MySQL` class enables the project to automatically deploy the data table at startup.

**Note: This class is configured only to automatically generate data tables. If you want to configure read and write database connections for Voter, configure the parameters in the application.properties file. **

> Please note: If you want to build a table manually, you can execute the following statement:

```$xslt
USE Voter;

CREATE TABLE `Voter_Vote` (
  `VID` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Describe` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Selection` varchar(10000) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Type` int(11) DEFAULT '0',
  `Limit` int(11) DEFAULT '-1',
  PRIMARY KEY (`VID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

INSERT INTO `Voter_Vote` VALUES (1, 'This is a vote title :)', 'And here is a describe of the vote XD', '<%1<%92<%Vote for Xiaoli<%2<%42<%Vote for Zhanghua<%3<%41<%Vote for Ergou<%4<%37<%Vote for Guawazi<%5<%48<%Vote for Benwei<%6<%50<%Vote for Adler', 0, 2);
```

#### Configuring database connections

After creating the database, configure the `application.properties` file to configure the database connection.

## working principle

### Implement a string of strings to store all voting option statistics

The `SelectionText` field stored in the database is as follows:

```$xslt
<%1<%0<%Vote for Xiaoli
<%2<%10<%Vote for Zhanghua
<%3<%20<%Vote for Ergou
<%4<%30<%Vote for Guawazi
<%5<%40<%Vote for Benwei
<%6<%50<%Vote for Adler
```

The prescribed voting storage syntax is as follows:

```$xslt
<% option number <% get votes <% option description
```

Analyze and modify this field by the method in `pers.adlered.voter.analyzer`.
