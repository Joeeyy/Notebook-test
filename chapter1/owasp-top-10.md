> Referrence: [OWASP TOP 10 2017](https://www.owasp.org/images/7/72/OWASP_Top_10-2017_%28en%29.pdf.pdf)

**A1: 注入漏洞\(Injection\)**。如SQL、NoSQL、OS以及LDAP\(Lightweight Directory Access Protocol\)的注入。注入攻击发生在不受信任的数据被发送到服务器上的解释器并被解释器当做指令或者查询执行时。攻击者的恶意代码可能会欺骗解释器执行非预期的指令或者越权访问数据。

**A2: 有缺陷的认证\(Broken Authentication\)**。应用程序有关认证和会话管理的功能常常被错误地实现，导致攻击者能够获取密码、秘钥或者会话token，亦或是继续利用其他实现上的缺陷永久或临时地获取其他用户的身份。

**A3: 敏感数据的泄露\(Sensitive Data Explosure\)**。许多web应用或者API并没有合适地保护包含金融、医疗健康、PII在内的敏感数据。攻击者或许能够通过窃取或者修改这些没有被保护好的数据以实施信用卡诈骗、身份信息窃取或者其他的犯罪行为。敏感信息需要额外的保护，如加密、对与浏览器交互时的格外注意等，以防止被攻击。

**A4: XXE\(XML External Entities\)**。许多老的或者配置的不好的XML处理器会处理XML文件中的外部实体引用。外部实体的引用可以被利用，通过文件URI句柄泄露内部文件、分享内部文件、内部端口扫描、远程代码执行以及拒绝服务攻击。

**A5: 错误的访问控制\(Broken Access Control\)**。根据认证信息的不同对用户所能获取资源的限制常常没有被正确的加强。攻击者可以利用这些漏洞获取非授权的功能或数据，如获取其他用户的账户、浏览敏感文件、修改其他用户的数据、修改访问权限等等。

**A6: 安全上的错误配置\(Security Misconfiguration\)**。安全上的错误配置是最常见的问题了。这一般是错误的默认配置、不完整或者对点配置、公开云存储、错误配置的HTTP头以及冗余的含有敏感内容的错误信息造成的。所有的操作系统、框架、库以及应用程序不光需要被正确的配置，他们也必须及时的打上最新的补丁。

**A7: XSS\(Cross-Site Scripting\)**。XSS漏洞发生在应用没有经过合适的验证或者转义就将不受信的数据引入web页面，或者以用户提供的数据通过能够创建HTML的浏览器API或者JavaScript更新已有的web页面时。XSS使得攻击者能够在受害者的浏览器上执行命令，以达到劫持用户会话、污染web网站或者跳转至恶意网站的目的。

**A8: 不安全的反序列化\(Insecure Deserialization\)**。不安全的反序列化一般会导致远程代码执行。即便反序列化不导致远程代码执行，也可以被用来实施包含重放攻击、注入攻击、提权攻击在内的一系列攻击。

**A9: 对已知有漏洞的组件的使用\(Using Components with Known Vulnerabilities\)**。组件们，如库、框架、或是其他软件的模块，是以和应用程序相同的权限运行的。如果一个有漏洞的组件被利用了，那么可能导致重大数据丢失或者整个服务器被拿下。使用有漏洞的组件的应用程序和API可能会使防御失效，使得更多攻击方式成为可能。

**A10: 不足的日志和监控\(Insufficient Logging & Monitoring\)**。不足的日志和监控，以及缺失的或者低效的事件响应的整合，使得攻击者能供延续其攻击，维持其攻击，扩展攻击至更多系统，篡改、提取、破坏更多的数据。多数研究表明，检测到违规\(breach\)需要至少200天，并多是由外部实体检测而非内部的处理或者监控。

