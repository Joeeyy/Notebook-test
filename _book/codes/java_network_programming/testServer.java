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
        while (true){
            try{
                System.out.println("Waiting for connection: " + serverSocket.getLocalPort());
                Socket server = serverSocket.accept();
                System.out.println("Address of remote client: " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());
                System.out.println(in.readUTF());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thanks for connection: " + server.getLocalSocketAddress() + "\nGoodbye!");
                server.close();
            }
            catch (SocketTimeoutException e){
                System.out.println("Server timeout.");
                break;
            }
            catch (IOException e){
                e.printStackTrace();
                break;
            }
        }
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
