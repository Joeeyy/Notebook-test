
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/*
 * Java的代理机制：
 * 代理模式为其他对象提供了一种代理以便控制对这个对象的访问，这里所谓的控制就是可以在其调用行为前后加入一些别的操作。代理模式分为 静态代理（类的继承）和动态代理两种。
 */
class ProxyTest {
    public static void main(String args[]){
        System.out.println("1: ");
        Subject sub = new ProxySubject();
        sub.request();

        System.out.println("2: ");
        DynamicSubject ds = new RealDynamicSubject();
        Handler handler = new Handler(ds);
        DynamicSubject ds2 = (DynamicSubject)Proxy.newProxyInstance(DynamicSubject.class.getClassLoader(), new Class[]{DynamicSubject.class}, handler);
        // ClassLoader load: 指定动态代理类的类加载器
        // Class<?> interfaces: 指定动态代理类需要实现的所有接口
        // invocationHandler h: 指定与动态代理类关联的InvocationHandler对象
        DynamicSubject ds3 = (DynamicSubject)Proxy.newProxyInstance(DynamicSubject.class.getClassLoader(),ds.getClass().getInterfaces(), handler);
        DynamicSubject ds4 = (DynamicSubject)Proxy.newProxyInstance(DynamicSubject.class.getClassLoader(),RealDynamicSubject.class.getInterfaces(), handler);

        System.out.println("ds.getClass() = " + ds.getClass());
        System.out.println("DynamicSubject.class = " + DynamicSubject.class);
        System.out.println(new Class[]{DynamicSubject.class});
        System.out.println(RealDynamicSubject.class.getInterfaces());

        ds2.request();
        ds3.request();
        ds4.request();
    }
}

// 以下为动态代理
/* 
 * Java的动态代理机制中有两个重要的类或者接口：InvocationHandler(Interface)、Proxy(Class)，他们是实现动态代理所必须用到的。
 * 大致流程：
 * 1. 定义一个接口（抽象角色）
 * 2. 基于以上接口实现一个类（真实角色）
 * 3. 基于invocationHandler实现一个处理器类
 * 4. 调用流程下面的流程：
 * 4.1 实现一个真实对象角色
 * 4.2 实现一个处理器对象
 * 4.3 构建一个新的代理对象，这个对象基于已有的处理器类和接口类
 * 4.4 再将这个对象转化为接口的类型、抽象角色的类型，不能是真实角色的类型。
 */
interface DynamicSubject {
    // 动态代理只能是接口，否则代理转成该类型会报错。
    abstract void request();
}

class RealDynamicSubject implements DynamicSubject{
    public RealDynamicSubject() {

    }

    public void request() {
        System.out.println("From real dynamic Subject");
    }
}

class Handler implements InvocationHandler {
    private Object obj; // 被代理的对象

    public Handler(Object obj) {
        this.obj = obj;
    }

    /*
     * 所有的流程都控制在invoke方法中
     * proxy：代理类
     * method：正在使用的方法
     * args：方法的参数
     */ 
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable { // 接口必须实现的方法，核心逻辑
        System.out.println("Do sth. before requesting.");
        Object xxx = method.invoke(this.obj, args);
        System.out.println("Do sth. after requesting.");
        return xxx;
    }
}

// 以下为静态代理

abstract class Subject {
    // 抽象角色，通过抽象类声明真实角色实现的业务方法。
    // 类比网络代理，所有的http代理都支持http协议。
    abstract void request();
}

class RealSubject extends Subject{
    // 真实角色
    public RealSubject(){

    }

    public void request() {
        System.out.println("From RealSubject.");
    }
}

class ProxySubject extends Subject {
    // 代理角色

    private RealSubject realSubject;

    public ProxySubject(){

    }

    public void request() {
        preRequest(); // 控制的体现
        if (realSubject == null) {
            realSubject = new RealSubject();
        }
        realSubject.request();
        postRequest();
    }

    private void preRequest() {
        System.out.println("preRequest");
    }

    private void postRequest() {
        System.out.println("postRequest");
    }
}