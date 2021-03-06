# SSRF

> 参考《[了解SSRF，这一篇就足够了](https://xz.aliyun.com/t/2115)》、[CTF-Wiki](https://ctf-wiki.github.io/ctf-wiki/web/ssrf-zh/)、《[SSRF绕过方法总结](https://www.secpulse.com/archives/65832.html)》

## 定义

SSRF，服务端请求伪造（Server-Side Request Forgery），是一种由攻击者构造，形成由服务器发起请求的一个安全漏洞。一般情况下，SSRF攻击的目标是从外网无法访问的内网系统。

SSRF形成的原因大都是由于服务器端提供了从其他服务器应用获取数据的功能而没有对目标地址做过滤和限制。如从指定URL获取文本内容，加载指定地址的图片，下载等等。

## 场景

可能出现SSRF攻击的场景有以下几个：

1. 能够对外发起网络请求的地方：如在线识图、在线文档翻译等。
2. 能够请求远程服务器资源的地方：如对远程url的上传。
3. 数据的一些内置功能：如mongodb的copyDatabase。
4. 邮件系统。
5. 文件的在线处理：如ImageMagic、XML等。

## 利用

利用SSRF实现的攻击主要有以下5种：

1. 对外网、服务器所在内网、本地进行端口扫描，获取一些服务的Banner信息。
2. 攻击运行在内网的应用程序。
3. 对内网Web应用进行指纹识别（通过访问默认配置文件）。
4. 攻击内外网的Web应用，主要是通过GET参数就能实现的攻击（Structs2，sqli等）。
5. 利用`file`协议读取本地文件。

## 防范姿势

1. 服务器开启OpenSSL。
2. 服务器开启鉴权。
3. 限制请求常用TCP端口。
4. 禁用不必要的协议，如file://、gopher://等。
5. 统一错误信息，避免用户通过错误信息推断服务器状态。

## 绕过姿势

1. IP地址不同进制的写法。
2. 利用别名，比如本地可以用`127.0.0.1`、`localhost`、`[::]`等。
3. 利用短地址。
4. 利用DNS解析。
5. 利用Enclosed Alphanumerics。
6. 利用句号，会被自动替换为点号。
7. 利用特殊地址`http://0/`。
8. 利用如`dict`、`gopher`、`sftp`、`tftp`、`ldap`等协议。

## DNS Rebinding及其防范

传统SSRF过滤器大致思路如下：

1. 获取输入的url，从url中提取host。
2. 通过DNS解析获得host对应的IP。
3. 检测IP是否合法（是否含有内网地址）。
4. 若3通过则发送请求。
5. **如果请求过程中有跳转，则取出跳转链接从第一步开始执行**。
6. 判断完成后继续执行。

以上过程存在缺陷。上述过程有两次DNS解析的过程，一次在步骤2，一次在步骤3发送请求的时候，两次DNS  
请求之间有一段时间差，可以利用这段时间差进行绕过。

攻击者自己定义一个DNS服务器，通过将自己的子域名指向该服务器。当外部尝试解析该子域名是，第一次解析我们返回一个合法的外网地址，第二次返回内网地址。当然为了防止DNS缓存，我们设置DNS记录的TTL为0。

**DNS请求步骤如下**：

1. 查询本地DNS服务器`/etc/resolv.conf`
2. 如果有缓存则返回缓存的结果，若无则继续
3. 请求远程DNS服务器返回结果。

**关于DNS缓存**：

在MacOS和Windows上，为了加快HTTP访问速度，系统会对DNS记录缓存，但是在Linux系列上，除非运行了nscd等软件，否则默认不缓存DNS记录。

另外，IP为`8.8.8.8`的DNS地址不会进行缓存。

**防御**

1. 强行实现DNS缓存。
2. Host白名单。



