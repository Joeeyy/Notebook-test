# Web基础

## 0x00 Cookie & Session

Cookie: 存储于客户端浏览器，单个Cookie大小不超过4KB，一个站点最多保存20个，只能保存ASCII字符串，对用户可见，可以设置有效期，需要浏览器的支持，且支持跨域访问。

Session: 存储于服务器，大小一般来说没有上限，可以存出任何类型的数据，对用户不可见，依赖于JSESSIONID，会随着窗口关闭而失效，需要用到URL重写保证Session会话跟踪，不支持跨域访问。

**Cookie策略**：Cookie一般分为两种：Session Cookie和Third-party Cookie，前者可以看做是临时Cookie，后者则是本地Cookie，可以保存。Third-party Cookie由服务器设定了过期时间，这种Cookie被保存在本地，当时间超过设定后才会过期失效；而Session Cookie则没有被指定过期时间，关掉浏览器Session Cookie就会被销毁。在有些浏览器中会禁止Third-party Cookie的跨域访问。

## 0x01 HTTPS原理

## 0x02 SOP

SOP，Same Origin Policy，即同源策略。浏览器的同源策略，限制了来自不同**源**的"document"或脚本，对当前"document"读取或设置某些属性。

何谓同源：域名、端口、协议相同。

对于当前页面来说，页面内存放的JavaScript的域不重要，重要的是加载JavaScript的页面所在的域。比如a.com通过`scr`标签加载了b.com的某个脚本，那么这个脚本的域就是a.com。

**可以跨域加载资源的标签**

`<script>`**、**`<img>`**、**`<iframe>`**、**`<link>`**，他们都通过**`src`**属性加载跨域资源，实际上就是一次GET请求。但是对于通过**`src`**属性加载的资源，浏览器限制了JavaScript的权限，使其不能够读、写返回的内容。**

## 0x03 Web目录信息获取



## 0x04



