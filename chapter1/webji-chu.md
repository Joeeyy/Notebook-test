# Web基础

## 0x00 Cookie & Session

Cookie: 存储于客户端浏览器，单个Cookie大小不超过4KB，一个站点最多保存20个，只能保存ASCII字符串，对用户可见，可以设置有效期，需要浏览器的支持，且支持跨域访问。

Session: 存储于服务器，大小一般来说没有上限，可以存出任何类型的数据，对用户不可见，依赖于JSESSIONID，会随着窗口关闭而失效，需要用到URL重写保证Session会话跟踪，不支持跨域访问。

**Cookie策略**：Cookie一般分为两种：Session Cookie和Third-party Cookie，前者可以看做是临时Cookie，后者则是本地Cookie，可以保存。Third-party Cookie由服务器设定了过期时间，这种Cookie被保存在本地，当时间超过设定后才会过期失效；而Session Cookie则没有被指定过期时间，关掉浏览器Session Cookie就会被销毁。在有些浏览器中会禁止Third-party Cookie的跨域访问。

## HTTPS原理

## SOP

SOP，Same Origin Policy，即同源策略。

## Web目录信息获取



