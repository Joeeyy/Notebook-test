import java.util.*;

class testClient {
    public static void main(String args[]){
        String serverName = args[0];
        int port = Integer.parseInt(args[1]);

        try{
            System.out.println("Connecting to Host " + serverName + " port " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Name of remote server: " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeUTF("hello to server from " + client.getLocalSocketAddress());
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            System.out.println("Response from server: " + in.readUTF());

            client.close()
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}

