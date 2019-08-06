# Java 多线程

## 线程生命周期

**Established**: 一个线程对象刚刚被创建（new）。

**Ready**: 创建好的线程对象调用了start\(\)方法。

**Running**: 线程对象在start\(\)方法调用后调用了run\(\)方法。

**Blocked**: 调用了run\(\)方法后的线程对象因为某些原因暂停运行。

**Exited**: 线程任务执行完毕，退出。



关于阻塞：

1. 等待阻塞：线程调用了wait\(\)方法，等待唤醒的信号。
2. 同步阻塞：线程获取synchronized同步锁失败后进入同步阻塞。
3. 其他阻塞：线程调用sleep\(\)或join\(\)或发起I/O请求。

## 线程创建

在Java中，我们可以通过三种方式创建一个线程：

1. 通过实现Runnable接口；
2. 通过集成Thread类本身；
3. 通过Callable和Future创建线程。

### 实现Runnable接口

最简单的方法就是实现Runnable接口的类，为了实现Runnable，一个类仅需执行一个方法调用run函数即可。

```java
import java.util.*;

class RunnableExample implements Runnable {
    private Thread t;
    private String name;
    
    RunnableExample(String name){
        this.name = name;
        System.out.println("Creating thread with name: " + this.name);
    }
    
    public void run(){
        System.out.println("Running thread with name: " + this.name);
        // do sth.
        System.out.println("Exiting thread with name: " + this.name);
    }
    
    public void start(){
        System.out.println("Starting thread with name: " + this.name);
        if (t == null){
            this.t = new Thread(this, this.name);
            t.start();
        }
    }
}

public class TestRunnableExample {
    public static void main(String args[]){
        RunnableExample re = new RunnableExample("Name-1");
        re.start()
    }
}
```





