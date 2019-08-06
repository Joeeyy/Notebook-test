# Java 网络编程

## 套接字建立TCP连接

通过套接字建立TCP连接过程如下：

1. 服务器实例化一个ServerSocket对象，监听服务器上某个端口。
2. 服务器调用ServerSocket类的accept\(\)方法，等待客户端的连接。
3. 客户端实例化一个Socket对象，指定服务器地址请求连接。
4. Socket类的构造函数试图将客户端连接到指定的服务器上，如果连接建立成功，则在客户端创立一个Socket对象与服务器进行通信。
5. 在服务器端，accpet方法返回服务器上一个新的Socket引用，该Socket连接到客户端的Socket。



