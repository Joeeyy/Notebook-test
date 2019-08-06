import java.util.*;
import java.util.concurrent.*;

class CallableDemo implements Callable<Integer> {
    public Integer call() throws Exception {
        int i = 0;
        for (;i<100;i++){
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
        return i;
    }
}

class CallableTest {
    public static void main(String args[]){
        CallableDemo cd = new CallableDemo();
        FutureTask ft = new FutureTask<>(cd);

        for (int i=0;i<100;i++){
            System.out.println(Thread.currentThread().getName() + ": " + i);
            if (i == 20){
                new Thread(ft, "threadWithReturnedValue").start();
            }
        }

        try{
            System.out.println("subThread: " + ft.get());
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
    }
}
