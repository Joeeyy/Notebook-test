# SSTI

## 简介

SSTI，Server-Side Template Injection，服务器端模板注入，

## POC 基础（Python）

对于已经找到的SSTI注入点，我们需要考虑注入POC，那么POC如何构造呢？

在Python中，object类是所有类的基类，如果定义一个类时没有定义该类继承于哪个类，那么该类会自动继承于object类。假设对于一个空字符串`""`，我们可以通过\`""._class_""





