import java.util.*;

class RunnableDemo implements Runnable{
    private Thread t;
    private String threadName;

    RunnableDemo(String name){
        this.threadName = name;
        System.out.println("Creating thread with name: " + this.threadName);
    }

    public void run(){

    }

    public void start(){
        System.out.println("Start thread with name: " + this.threadName);
        if (this.t == null){
            t = new Thread(this, this.threadName);
            t.start()
        }
    }
}


class ThreadTest1 {

}
