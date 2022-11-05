import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFileSender {
        public static void main(String[] args) throws IOException {
            //File file=new File("FileProva.txt");
            //String pathname= file.getAbsolutePath();
            //System.out.println("PATHNAME: "+pathname);
            FileInputStream fileInputStream=new FileInputStream("kubuntu.torrent");
            DataInputStream dataInputStream=new DataInputStream(fileInputStream);

            byte[] buffer=new byte[dataInputStream.available()];
            try {

                dataInputStream.readFully(buffer);

            }catch (IOException e) {throw new RuntimeException(e);}

            ServerSocket serverListener=null;
            try {
                serverListener=new ServerSocket(6060);
            } catch (IOException e) {throw new RuntimeException(e);}

            while(true) {
                Socket clientSocket=null;
                try {
                    clientSocket = serverListener.accept();
                } catch (IOException e) {e.printStackTrace();}

                DataOutputStream dataOutputStream=new DataOutputStream(clientSocket.getOutputStream());
                dataOutputStream.write(buffer);
                dataOutputStream.flush();

                clientSocket.close();
                dataOutputStream.close();
            }


        }
}