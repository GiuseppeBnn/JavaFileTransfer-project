import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFileSender {
        public static void main(String[] args) throws IOException {

            FileInputStream fileInputStream=new FileInputStream("kubuntu.torrent");
            DataInputStream dataInputStream=new DataInputStream(fileInputStream);

            byte[] buffer=new byte[dataInputStream.available()];
            try {

                dataInputStream.readFully(buffer);

            }catch (IOException e) {throw new RuntimeException(e);}

            ServerSocket serverListener;
            try {
                serverListener=new ServerSocket(6060);
            } catch (IOException e) {throw new RuntimeException(e);}
            boolean flag=true;
            while(flag) {
                Socket clientSocket=null;
                try {
                    clientSocket = serverListener.accept();
                } catch (IOException e) {e.printStackTrace();}

                assert clientSocket != null;
                DataOutputStream dataOutputStream=new DataOutputStream(clientSocket.getOutputStream());
                dataOutputStream.write(buffer);
                dataOutputStream.flush();

                clientSocket.close();
                dataOutputStream.close();
            }


        }
}