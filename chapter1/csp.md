# CSP

## 简介

CSP，Content Security Pollicy，即内容安全策略。他是一个额外的安全层，用于检测并削弱特定类型的攻击。

## 使用

为了确保CSP可以使用，我们需要配置网络服务器返回`Content-Security-Policy`头（旧版本被称为`X-Content-Security-Policy`）。除此之外，也可以使用`meta`标签进行配置，如：

```js
<meta http-equiv="Content-Security-Policy" content="default-src 'self'; img-src https://*; child-src 'none;">
```

### 制定策略

可以使用`Content-Security-Policy`头部指定策略，如：

```
Content-Securyti-Policy: policy
```

其中，`policy`是一个包含了各种策略指令的字符串。

### 描述策略

default-src: 其他类型资源没有符合自己的策略时使用的资源。

示例：

限制所有内容均来自于同一源，且不包含同源子域名：

```
Content-Security-Policy: default-src 'self'
```

允许内容来自信任的域名及其子域名：

```
Content-Security-Policy: default-src 'self' *.trusted.com
```

允许用户在自己的内容中你包含来自任何源的图片，但是限制音频或视频需从信任的地址获取，所有的脚本必须从特定主机获取可信代码：

```
Content-Security-Policy: default-src 'self'; 
```

## 针对

### XSS

减少和报告XSS攻击是CSP的主要目标。XSS攻击利用浏览器对于从服务器获取内容的信任，在用户的浏览器中运行恶意脚本。

CSP通过指定有效域（即浏览器认可的可执行脚本的有效来源），使服务器管理者有能力减少或消除XSS攻击所依赖的载体。一个CSP兼容的浏览器将会仅执行从白名单域获取到的脚本文件而忽略来自其他所有域的脚本（包括内联脚本和HTML事件处理属性）。

### 数据包嗅探攻击

除了限制可以加载内容的域，CSP还可以指明那种协议允许使用。比如，服务器可以指定所有内容必须通过HTTPS加载。

一个完整的数据安全传输策略不仅强制使用HTTPS进行数据传输，也为所有的Cookie标记安全标志，边切提供自动的重定向使得HTTP页面导向HTTPS版本。网站也可以使用`Strict-Transport-Security`头确保连接它的浏览器只使用加密通道。

