# PHP安全

## 0x00 文件包含漏洞

PHP中常见导致文件包含漏洞的函数：`include()`、`include_once`_、_`require()`_、_`require_once()`、`fopen()`、`readfile()`。

当使用以上前四个函数时，被包含的文件会被作为PHP代码执行，PHP内核不会判断其实际上是何种文件。

> 1\) include与require的区别  
> 它们语句相同，错误处理方面有所不同。当被包含的文件不存在时，require会产生致命错误E\_COMPILE\_ERROR从而停止脚本的执行，而include只会生成警告E\_WARNING，脚本会继续执行。
>
> 2\) include\_once 与 include区别  
> 使用include\_once进行文件包含时会首先检查要导入的文件是否已经在该程序的其他地方导入过了，若有则不会继续导入。

以上方式完成攻击有以下两个条件：

1. include等函数以动态变量方式引入某文件。
2. 用户能够控制该动态变量。

按照包含文件是否在服务器本地可以将文件包含漏洞划分为`本地文件包含漏洞(LFI)`和`远程文件包含漏洞(RFI)`两种。

### 本地文件包含

```php
<?php
$file = $_GET['file']; // "../../etc/passwd\0"
if (file_exists('/home/wwwrun/'.$file.'.php')){
    include '/home/wwwrun'.$file.'.php';
}
?>
```

上述代码中，用户可以控制`$file`变量，且`include`函数接收了该变量进行文件包含，用户通过输入`../../etc/passwd\0`尝试包含`/etc/passwd`文件。

**注意！**

PHP内核是由C语言实现的，因此使用了C中的一些字符串处理函数。上例中，用户通过输入`\0`0字节截断了后面的`.php`变量，在web中也可以通过输入`%00`完成截断。web用户一般不需要输入0字节，故而可以对其进行过滤。

上面通过`../`访问上级目录的姿势称为目录遍历漏洞，我们可以通过`open_basedir()`限制，但该函数匹配前缀，满足前缀的目录都在允许访问的范围内。

**利用技巧**

1. 包含用户上传文件
2. 包含data://、php://input 等伪协议。
3. 包含Session文件。前提用户能够控制Session的一些内容，向其中写入代码。
4. 包含日志文件，如Web Server的Access log。通过报错向日志中插入代码。
5. 包含/proc/self/environ文件。其中含有web进程运行的环境变量。
6. 包含上传的临时文件\(RFC 1867\)。
7. 包含其他应用程序创建的文件，如数据库文件、缓存文件、应用日志等等。

截断姿势：

1. 0字节截断
2. 操作系统目录最大长度限制，win 256B，Linux 4096B。
3. url中`?`截断后面的内容当做参数处理。

### 远程文件包含

**前提**：

1. allow\_url\_open开启

```php
<?php
if ($route == "share"){
    require_once $basePath. '/action/m_share.php';
}
elseif ($route == "sharelink"){
    require_once $basePath. 'action/m_sharelink.php';
}
?>
```

## 0x01 变量覆盖漏洞

**1. 全局变量覆盖**

PHP允许变量未经初始化使用，访问一个未经初始化的变量并不会报错。当PHP配置register\_globals为ON时，PHP的变量来源可能是各个地方，如页面表单、Cookie、URL等。

PHP中`unset()`函数智能销毁局部变量，如果想要销毁全局变量则必须使用`$GLOBALS`。

**2. 函数引起的变量覆盖**

`extract()`、 `import_request_variables()`、`parse_str()`、以遍历方式释放代码的。

```php
// 1. extract能将变量从数组导入当前的符号表，比如$_GET数组，
// 用户以GET参数的形式提交auth=1，则经过函数处理后下面的代码就会出现问题。
// 避免问题：
//   首先确认register_globals关闭，
//   在调用该函数时使用EXTR_SKIP避免已有变量被覆盖。
//   注意变量获取的顺序。
<?php
    $auth = '0';
    extract($_GET);

    if ($auth == 1){
        echo "you're in";
    }
    else{
        echo "no way";
    }
?>

// 2. import_request_variables()可以将$_GET、$_POST、Cookie中的变量导入到全局。

<?php
    $auth = '0';
    import_request_variables('G');

    if ($auth == 1){
        echo "you're in";
    }
    else{
        echo "no way";
    }    
?>

// 3. parse_str()通常用户解析url的query string，
// 该函数第二个参数可以给定一个数组变量，解析后的结果会存入该数组变量。
<?php
    // var.php?var=new
    $var = 'init';
    parse_str($_SERVER['QUERY_STRING']);
    print $var;

?>

// 4. 遍历初始化变量
<?php
    $chs = '';
    if ($_POST && $charset != 'utf-8') {
        $chs = new Chinese('UTF-8', $charset);
        foreach ($_POST as $key => $value) {
            $$key = $chs->Convert($value);
        }
    }
?>
```

## 0x02 代码执行漏洞

PHP中可能造成非预期代码执行的危险函数有以下：

[system\(\)](https://www.php.net/manual/zh/function.system.php)、[exec\(\)](https://www.php.net/manual/zh/function.exec.php)、[passthru\(\)](https://www.php.net/manual/zh/function.passthru.php)、[escapeshellcmd\(\)](https://www.php.net/manual/zh/function.escapeshellcmd.php)、[shell\_exec\(\)](https://www.php.net/manual/zh/function.shell-exec.php)

eval、assert可以接收执行php代码。

ob\_start\(\)刷新缓存执行命令。

pcntl\_exec\(\)执行指定程序。

preg\_replace\(\)：第一个参数的正则式结尾有`/e`时可以执行

array\_map\(\)：遍历数组并执行指定函数名的函数。

反序列化

## 0x03 php伪协议

php://input，代表访问请求的原始数据。以POST请求为例，php://input可以获取POST请求的数据。但是不能处理`multipart/form-data`类型的请求。相比于``$HTTPRAWPOSTDATA来说对内存压力小，且无需配置。需要`allowurl_inclucde`。``

php://filter，一种元封装器，设计用于数据流打开时的筛选过滤应用，一般使用以下参数：

1. resource=要过滤的数据流，该参数必须
2. read=读链的筛选列表，该参数可选，可以设置一个或多个过滤器名称，以`|`分隔。
3. write=写链的筛选列表，该参数可选，可以设置一个或多个过滤器名称，以`|`分隔。
4. 任何没有以`2`或`3`的筛选器列表会视情况应用于读或写链。

举例，`php://filter/read=convert.base64-encode/resource=test.php`。

zip://archive.zip\#dir/file.txt，可以访问压缩文件中的某一特定子文件。

