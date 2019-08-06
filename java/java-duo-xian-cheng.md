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

### 继承Thread类

通过继承Thread类，并重写run\(\)方法实现。代码与前面类似，`implements Runnable`替换为`extends Thread`。

Thread类的一些方法：

1. public void start\(\): 使线程开始运行，调用线程的run\(\)方法。
2. public void run\(\): 如果该县城市使用独立的Runnable运行对象构造的，则调用该Runnable对象的run方法，否则该方法不执行任何操作并返回。
3. public final void setName\(String name\): 设置线程名称。
4. public final void setPriority\(int prioirty\): 设置线程优先级。
5. public final void setDaemon\(boolean on\): 将该线程标记为守护线程或用户线程。
6. public final void join\(long millisec\): 等待该线程终止的时长为millisec毫秒。
7. public void interruput\(\): 中断该线程。
8. public final boolean isAlive\(\): 判断线程是否存活。
9. public static void yield\(\): 暂停当前正在执行的线程对象，并执行其他线程。
10. public static void sleep\(long millisec\): 休眠指定的毫秒数。
11. public static boolean holdsLock\(Object x\): 当且仅当当前线程在指定的对象上保持监视锁时返回true。
12. public static currentThread\(\): 返回当前正在执行的线程对象的引用。
13. public static void dumpStack\(\): 将当前线程的堆栈跟踪打印至标准错误流。

### Callable & Future 





