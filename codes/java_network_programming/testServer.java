import java.util.*;
import java.net.*;
import java.io.*;

class testServer extends Thread{
    private ServerSocket serverSocket;

    public testServer(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run(){
        System.out.println("Waiting for connection: " + serverSocket.getLocalPort());

    }
    catch (SocketTimeoutException e){
        System.out.println("Server timeout.");
        break;
    }
    catch (IOException e){
        e.printStackTrace();
        break;
    }

    public static void main(String args[]){
        int port = Integer.parseInt(args[0]);
        try {
            Thread t = new testServer(port);
            t.run();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
