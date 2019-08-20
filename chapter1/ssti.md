# SSTI

## 简介

SSTI，Server-Side Template Injection，服务器端模板注入，

## POC 基础（Python）

对于已经找到的SSTI注入点，我们需要考虑注入POC，那么POC如何构造呢？

在Python中，object类是所有类的基类，如果定义一个类时没有定义该类继承于哪个类，那么该类会自动继承于object类。假设对于一个空字符串`""`，我们可以通过`"".__class__`获取`str`类，对于某一特定类，可以通过`__bases__`获得其所有基类，这里对于字符串，我们可以通过`"".__class__.__bases__[0]`获取到object类，同样对于字符串类，也可以通过`__mro__`\(method resolution order\)确认其类调用顺序。对于object类，通过`__subclasses__()`可以获取所有子类，即Python环境中所有存在的类，这里我们完全可以定位到`os`类，进而执行命令。

## 防御



