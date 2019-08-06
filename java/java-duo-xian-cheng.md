# Java 多线程

## 线程生命周期

**Established**: 一个线程对象刚刚被创建。

**Ready**: 创建好的线程对象调用了start\(\)方法。

**Running**: 线程对象在start\(\)方法调用后调用了run\(\)方法。

**Blocked**: 调用了run\(\)方法后的线程对象因为某些原因暂停运行。

**Exited**: 线程任务执行完毕，退出。

## 线程创建

在Java中，我们可以通过三种方式创建一个线程：

1. 通过实现Runnable接口；
2. 通过集成Thread类本身；
3. 通过Callable和Future创建线程。

### 实现Runnable接口

最简单的方法就是实现Runnable接口的类，为了实现Runnable，一个类仅需执行一个方法调用run函数即可。

