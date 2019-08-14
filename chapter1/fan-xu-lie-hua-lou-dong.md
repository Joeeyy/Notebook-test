# 反序列化漏洞

## 定义

序列化：将对象保存为字节。

反序列化：将对象序列化后产生的字节恢复为原来的对象。

## 应用场景

1. 想把内存中的一个对象保存到文件中或者数据库中。
2. 想用套接字在网络上传输对象。
3. 想通过RMI传输对象。

## PHP中的反序列化漏洞

以下是一个简单的`serialize()`和`unserialized()`的demo：

```php
<?php
    class test{
        var $a = "123";
        function print_info(){
            echo "class test\n";
        }
    }

    $test1 = new test();
    $test1->print_info();    //正常打印"class test"
    $serialized_string = serialize($test1);    //执行序列化操作
    echo $serialized_string. "\n";    //O:4:"test":1:{s:1:"a";s:3:"123";}
    $test2 = unserialize($serialized_string);
    $test2->print_info();    //正常打印"class test"，反序列化成功。
?>
```

在PHP的序列化和反序列化过程中，有一些魔术函数会被自动调用，举例如下：

```php
<?php
    class test{
        var $a = "123";
        function print_info(){
            echo "class test\n";
        }
        function __wakeup(){
            echo "wake up \n";
        }
        function __construct(){
            echo "construct \n";
        }
        function __destruct(){
            echo "destruct \n";
        }
    }

    $test1 = new test();    //对象创建，自动调用construct
    $test1->print_info();
    $serialized_string = serialize($test1);
    echo $serialized_string. "\n";
    $test2 = unserialize($serialized_string);    // unserialize时候自动调用wakeup
    $test2->print_info();
    // 程序结束，对象析构，调用destruct
?>
```

除此之外，还有sleep函数，在序列化之前会被调用，返回需要序列化的关键属性，toString函数会在尝试直接打印一个对象时被调用。

## Java中的反序列化漏洞

> 引自勾[陈安全实验室](http://www.polaris-lab.com/index.php/archives/450/)

### 实现

实现类的Serializable接口。但是，对于一个实现了Serializable接口的类来说，并不一定其中所有的字段或者属性都是可以序列化的，如果该类有父类，则有两种情况：

1. 该父类已经实现了序列化接口，则其父类相应字段或者属性的处理与该类相同。
2. 若该父类未实现序列化接口，则该类父类所有相应字段或者属性都不会被序列化。反序列化时，所有之前未经过序列化的字段或者属性会被按照父类默认的构造函数进行初始化。

另外，如果勒种某个属性被标志位static，则该类不能被序列化。如果该类某个属性被标志位transient，则该类也不能被序列化。如果类的父类实现了序列化接口，子类不必显式实现序列化接口就能够自动获得序列化。当一个对象实例变量引用了其他对象，序列化该对象时也会尝试将被引用的对象进行序列化。

我们可以通过重写`ObjectOutputStream`的`readObject()`和`writeObject()`函数实现对反序列化和序列化的定义。

### Java的反射机制（Reflection）

#### 什么是Java的反射机制？

1. 指的是可以与运行时加载，探知和使用编译期间完全未知的类。
2. 程序在运行状态中，可以动态加载一个只有名称的类，对于任意一个已经加载的类，都能够知道这个类的所有属性和方法，对于任意一个对象，都可以调用这个类的任意一个方法和属性。
3. 加载类之后，在堆内存中会产生一个Class类型的对象（一个类只有一个Class对象），这个对象包含了该类完整的结构信息，而且这个Class对象就像是一面镜子，透过这个镜子看到类的结构，所以被称为反射。
4. 每个类被加载进入内存之后，系统就会为该类生成一个`java.lang.Class`对象，通过该Class对象就可以访问到JVM中的这个类。

#### Class对象的获取方法

1. 实例对象的`getClass()`方法；
2. 类的`.class`属性（最安全，性能最好）；
3. 运用`Class.forName(String className)`动态加载类，`className`需要的是类的全限定名。
4. 注意，以上使用第二种方法通过`.class`创建Class对象的引用时，不会自动初始化该Class对象，而是用第三种方法通过`forName()`函数则会初始化该对象。![](http://www.polaris-lab.com/usr/uploads/2018/03/2090392135.png)

#### 通过反射方法调用函数

```java
package Step2;

import java.lang.reflect.Method;

public class reflectionTest {

    public static void main(String[] args){

        Method[] methods = test.class.getMethods();
        //获取类的方法二，有点类似python的getattr()。java中每个类型都有class 属性
        //通过类的class属性获取对应的Class类的对象，通过这个Class类的对象获取test类中的方法集合

        /* String name = test.class.getName();
         * int modifiers = test.class.getModifiers();
         * .....还有很多方法
         * 也就是说，对于一个任意的可以访问到的类，我们都能够通过以上这些方法来知道它的所有的方法和属性；
         * 知道了它的方法和属性，就可以调用这些方法和属性。
         */

        //调用test类中的方法
        for(Method method : methods){
            if(method.getName().equals("int2string")) {
                System.out.println("method = " + method.getName());

                Class[] parameterTypes = method.getParameterTypes();//获取方法的参数
                Class returnType = method.getReturnType();//获取方法的返回类型
                try {
                    //method.invoke(test.class.newInstance(), 666);
                    Object x = method.invoke(new test(1), 666);
                    System.out.println(x);
                    // new关键字能调用任何构造方法。 newInstance()只能调用无参构造方法。
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        try {
            Method method = test.class.getMethod("int2string", Integer.class);
            Object x = method.invoke(new test(2), 666);//第一个参数是类的对象。第二参数是函数的参数
            System.out.println(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class test{
    private Integer n;  

    public test(Integer n){ //构造函数，初始化时执行
        this.n = n;
    }
    public String int2string(Integer n) {
        System.out.println("here");
        return Integer.toString(n);
    }
}
```

### 总结

程序有两大基本元素：变量和函数。故而想要控制程序实现命令执行，大致上有以下两个方向：

1. 控制代码、函数：就像命名注入等注入类漏洞一样，数据都被当做了代码执行；或者通过重写`readObject()`，不过这种场景基本上不存在，任意文件上传和执行勉强算是。
2. 控制输入、数据、变量：利用代码中已有的函数和逻辑，通过改变输入内容的形态实现流程的控制。

对于Java的反序列化漏洞，属于控制数据输入，它有以下两个基本点需要满足：

1. 有一个可序列化的类，并且该类重写了`readObject()`方法，由于不存在代码注入，只能查找已有代码逻辑中是否有这样的类。
2. 在重写的`readObject()`方法中的逻辑中有`method.invoke`函数的出现，且参数可控。

### 动态代理

> 参考[勾陈安全实验室](http://www.polaris-lab.com/index.php/archives/453/)



