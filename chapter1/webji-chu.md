# Web基础

## 0x00 Cookie & Session

Cookie: 存储于客户端浏览器，单个Cookie大小不超过4KB，一个站点最多保存20个，只能保存ASCII字符串，对用户可见，可以设置有效期，需要浏览器的支持，且支持跨域访问。

Session: 存储于服务器，大小一般来说没有上限，可以存出任何类型的数据，对用户不可见，依赖于JSESSIONID，会随着窗口关闭而失效，需要用到URL重写保证Session会话跟踪，不支持跨域访问。

**Cookie策略**：Cookie一般分为两种：Session Cookie和Third-party Cookie，前者可以看做是临时Cookie，后者则是本地Cookie，可以保存。Third-party Cookie由服务器设定了过期时间，这种Cookie被保存在本地，当时间超过设定后才会过期失效；而Session Cookie则没有被指定过期时间，关掉浏览器Session Cookie就会被销毁。在有些浏览器中会禁止Third-party Cookie的跨域访问。

## 0x01 HTTPS原理

参考[HTTPS连接过程以及中间人劫持](https://blog.csdn.net/hbdatouerzi/article/details/71440206)

## 0x02 SOP

SOP，Same Origin Policy，即同源策略。浏览器的同源策略，限制了来自不同**源**的"document"或脚本，对当前"document"读取或设置某些属性。

何谓同源：域名、端口、协议相同。

对于当前页面来说，页面内存放的JavaScript的域不重要，重要的是加载JavaScript的页面所在的域。比如a.com通过`scr`标签加载了b.com的某个脚本，那么这个脚本的域就是a.com。

**可以跨域加载资源的标签**

`<script>`**、**`<img>`**、**`<iframe>`**、**`<link>`**，他们都通过**`src`**属性加载跨域资源，实际上就是一次GET请求。但是对于通过**`src`**属性加载的资源，浏览器限制了JavaScript的权限，使其不能够读、写返回的内容。**

## 0x03 Web目录信息获取

## 0x04 PHP Session序列化的有关知识

> 参考 [https://xz.aliyun.com/t/6640](https://xz.aliyun.com/t/6640)

session意为会话，是客户端与服务器之间的通讯状态的一种保存机制，所谓通讯状态，其中更为关键的便是身份认证信息。其主要区别于cookie的特征便是**session会保存在服务器端**。session存储单一用户的信息，且该信息可以在该站点的所有页面使用。

### 1. session工作流程

php中一般使用语句`sessionStart`表示一次会话的开始，在会话开始后，PHP会尝试从请求中寻找session id，该id一般保存在请求的cookie中，偶尔可能出现在GET、POST参数中。如果PHP未能发现session id，则会调用函数`phpSessionCreateId`函数创建一个新的session，并通过HTTP响应头部的set-cookie告知客户端。但是浏览器允许设置禁止cookie，在这种情况下，php可以将session id设置在url参数中或者页面表格中设置为隐藏的参数中，这需要在`php.ini`文件中或者在运行过程中调用`iniSet`设置`session.useTransSid`为开启。

在会话开始后，可以通过PHP将会话的数据保存在`$SESSION`变量中，在PHP停止时，会读取$SESSION中的内容并在对其进行序列化后发送给会话保存管理器进行保存。至于序列化具体承担者是谁，可以通过配置`session.saveHandler`进行设置，一般会将会话数据保存在配置`session.savePath`指定的路径下。

### 2. PHP session 存储机制

PHP会话保存机制由`session.serializeHandler`定义，默认以文件形式进行存储，且文件多以`sessSessionid`命名，文件的内容为会话序列化之后的字符串。

`session.serializeHandler`可以定义的保存机制有三种：php、php\_binary和php\_serialize，下面详细介绍这三种存储机制。

#### php

键名+竖线+经过serialize\(\)函数序列化处理之后的值。

#### php\_binary

键名的长度对应的ASCII字符+键名+经过serialize\(\)函数序列化处理之后的值。

#### php\_serialize

经过serialize\(\)函数序列化处理之后的值。

前两个机制较旧，其会话索引既不能是数字也不能包含字符"\|"和"!"。

































