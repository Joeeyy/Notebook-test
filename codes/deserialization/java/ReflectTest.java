import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * 反射机制Demo：
 * 1. 运行时可以加载、探知、使用编译期间完全未知的类。
 * 2. 程序在运行状态时，动态加载一个只有名称的类，对于任意一个已经加载的类，可以获知该类的名字、所有属性、方法、参数、返回值等，对于该类任意一个对象，都可以调用这个类的任意一个方法和属性。
 * 3. 加载类之后，堆内存中会产生一个该类的Class类型对象，Class类型对象对于每个类来说唯一，该对象保存了该类完整的结构信息，就像一面镜子，“反射”着这个类。
 * 每个类倍加载入内存后，系统为该类生成一个java.lang.Class对象，通过该对象可以访问JVM中的该类。
 */

class ReflectTest {
    public static void main(String args[]){
        Method[] methods = Test.class.getMethods();

        String name = Test.class.getName();
        int modifiers = Test.class.getModifiers();

        for (Method method: methods){
            System.out.println("method - " + method.getName());
            /*
                寻找int2string方法，并通过method对象调用invoke函数，传入类的对象和int2string方法的参数调用该方法。
            */
            if (method.getName().equals("int2string")){
                System.out.println("int2string method found!");
                Class[] parameterTypes = method.getParameterTypes();
                Class returnType = method.getReturnType();

                try{
                    Object x = method.invoke(new Test(1), 666);
                    System.out.println(x);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        /*
            通过以下方法可以利用函数名和函数参数类型来定位一个函数，并通过invoke方法进行该函数的调用。
            如果找不到会返回NoSuchMethodException。
        */
        try{
            Method method = Test.class.getMethod("int2string", int.class);
            Object x = method.invoke(new Test(2),6662);
            System.out.println(x);

            // 通过反射调用本地方法
            method = java.lang.Runtime.class.getMethod("exec", String.class);
            Object result = method.invoke(Runtime.getRuntime(),"/bin/sh");
            System.out.println(result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
