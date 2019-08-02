# Java IO

Java IO分为两种，NIO和IO，其中NIO代表着New IO，他们有相同的作用和目的，但是实现方式不同。NIO主要用到的是块，所以他的效率会更高一点。Java分别针对标准输入输出和网络编程提供了两套NIO。

## NIO vs IO

### 数据处理方式

NIO是面向块的缓冲式处理，而IO是面向字节流的处理。Java IO面向流意味着每次从流中读取一个或多个字节，直至读取流中所有的字节，这些被读取的数据不会被缓冲在任何地方，因此也不能读取流中某个位置的数据。如果想要读取流中特定位置的数据，就需要把他们缓冲起来再进行读取。

### 阻塞与非阻塞

NIO属于非阻塞IO，而IO则是阻塞IO。Java IO阻塞就意味着，当一个线程调用read\(\)或write\(\)方法时，这个线程就会被阻塞，除非有一些数据被读取，或者所有的数据都写完，否则该线程不能再做任何事情。Java NIO的非阻塞IO使得其从某个通道发送请求数据，得到目前可用的数据，如果没有数据可用就不读取数据，而非阻塞线程。在线程写操作时也可以同事去做别的事情。一个线程可以管理多个输入输出通道。

### 选择器

Java NIO中实现了选择器，从而允许一个线程通过其监视多个输入输出通道。我们可以注册多个使用同一个选择器的通道，并设置一个单独的线程去管理选择器选择通道。这样使得一个线程可以管理多个输入输出的通道。

## 举例

假设我们有这样的文本数据流：

```
Name: Nana
Age: 20
Email: nana@example.com
Phone: 1234567
```

那么我们可以通过以下代码进行处理：

```java
InputStream input = ...; // get the InputStream from client socket
BufferedReader reader = new BufferedReader(new InputStreamReader(input));

String nameLine = reader.readLine();
String ageLine = reader.readLine();
String emailLine = reader.readLine();
String phoneLine = reader.readLine();
```

在IO中，每次执行read操作线程都会阻塞至读到数据。而对于NIO，则有如下例子：

```java
ByteBuffer buffer = ByteBuffer.allocate(48);
int bytesRead = inChannel.read(buffer);
```

第二行中，从inChannel通道中读取字节到ByteBuffer。当这个方法执行完并返回时，我们并不知道有多少数据被读取，我们需要的数据是否被读取，而仅仅知道有多少的字节被读取。这都需要我们自己去判断。举个例子：

```java
ByteBuffer buffer = ByteBuffer.allocate(48);

int bytesRead = inChannel.read(buffer);

while( !bufferFull(bytesRead)) {
    bytesRead = inChannel.read(buffer);
}
```

其中，bufferFull方法判断有多少字节被读取，并返回真值。但是这种方法只能判断缓冲器是不是满了，感觉很不好。

## Java 中的字节流和字符流

字符流Reader - InputStreamReader\(\): 字符通向字符流的桥梁，通常用于处理FileReader读取的字符。

字符流Reader - FileReader\(\): 读取字符文件。

字符流Reader - BufferedReader\(\): 缓冲流，特有方法readLine\(\)。

字符流Writter - BufferedWritter\(\): 缓冲流，特有方法newLine\(\)、write\(String str\)。

字符流Writter - OutputStreamWritter\(\): 转换流，是字符流通向字节流的桥梁。

字符流Writter - FileWriter\(\): 用于写入字符文件。



