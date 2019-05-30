# Voter
:hand: Simple online vote system based in Spring Boot | 基于Spring Boot的简易投票系统 | Bootstrap | :cupid: Good looking

# 中文说明

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