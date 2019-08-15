# XXE

## 简介

XXE，XML External Entities。当XML解释器允许引用外部实体时，可以通过构造恶意XML内容，导致任意文件读取、执行系统命令、探测内网、攻击内网网站等等。一般XXE攻击基于回显或者报错才能进行，但是也可以通过Blind XXE的方式实现攻击。

## 利用

端口扫描，通过DTD盗取文件，RCE，钓鱼。

```js
<!DOCTYPE GVI [<!ENTITY xxe SYSTEM "file:///etc/passwd">]>
```



