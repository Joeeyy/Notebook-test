import java.util.*;

class RunnableDemo implements Runnable{
    private Thread t;
    private String threadName;

    RunnableDemo(String name){
        this.threadName = name;
        System.out.println("Creating thread with name: " + this.threadName);
    }

    public void run(){
        System.out.println("Running thread with name: " + this.threadName);
        try{
            for (int i=0;i<5;i++){
                System.out.println("Thread: " + this.threadName + ", " + i);
            }
        }
        catch (InterruptedException e){
            System.out.println("Thread " + this.threadName + " interrupted.");
        }
        System.out.println("Exiting thread with name: " + this.threadName);
    }

    public void start(){
        System.out.println("Start thread with name: " + this.threadName);
        if (this.t == null){
            t = new Thread(this, this.threadName);
            t.start();
        }
    }
}


class ThreadTest1 {
    public static void main(String args[]){
        RunnableDemo rd1 = new RunnableDemo("aaa");
        RunnableDemo rd2 = new RunnableDemo("bbb");

        rd1.start();
        rd2.start();
    }
}
