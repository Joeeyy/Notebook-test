import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * java中的序列化与反序列化
 * 实现了Serializabel接口的类可以被序列化。
 * writeObject和readObject函数定义了序列化和反序列化的过程。
 * 
 * Java中的反序列化漏洞属于输入参数可控的漏洞，主要有以下两种条件：
 * 1. 类可序列化，重写了readObject方法。
 * 2. 重写的readObject方法中调用了method.invoke这种可以执行调用任意方法的函数，且参数可控。
 */

class DeserializationTest implements Serializable{
    /**
     * 这里创建一个可以进行序列化操作的示例类。
     */ 

    private static final long serialVersionUID = 1L;

    private int n;

    public DeserializationTest(int n) {
        this.n = n;
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        System.out.println(Runtime.getRuntime().exec("/bin/date"));
        System.out.println("test");
    }

    public static void main(String args[]){
        //DeserializationTest dt = new DeserializationTest(7);
        //operation.serialize(dt);
        operation.deserilize();
    }
}

class operation {
    public static void serialize(Object obj){
        // 序列化操作
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.obj"));
            oos.writeObject(obj);
            oos.flush();
            oos.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void deserilize(){
        try{
            File file = new File("object.obj");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Object x = ois.readObject();
            System.out.println(x);
            ois.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}