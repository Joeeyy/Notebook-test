import java.util.*;

class ThreadDemo extends Thread{
    private Thread t;
    private String name;

    ThreadDemo(String name){
        this.name = name;
        System.out.println("Creating thread with name: " + this.name);
    }

    public void run(){
        System.out.println("Running thread with name: " + this.name);
        try{
            for (int i=0;i<5;i++){
                System.out.println("Thread: " + this.name + ", " + i);
                Thread.sleep(50);
            }
        }
        catch (InterruptedException e){
            System.out.println("Thread " + this.name + " interrupted.");
        }
        System.out.println("Exiting thread with name: " + this.name);
    }

    public void start(){
        System.out.println("Start thread with name: " + this.name);
        if (this.t == null){
            t = new Thread(this, this.name);
            t.start();
        }
    }
}

class ThreadTest2{
    public static void main(String args[]){
        ThreadDemo tt1 = new ThreadDemo("aaa");
        ThreadDemo tt2 = new ThreadDemo("bbb");

        tt1.start();
        tt2.start();
    }
}