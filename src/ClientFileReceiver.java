import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientFileReceiver {
    public static void main(String[] args){
        Socket serverSocket=null;
        try {
            InetAddress serverAddress=InetAddress.getByName("192.168.1.221");
            serverSocket=new Socket(serverAddress,6060);
        } catch (IOException e) {throw new RuntimeException(e);}

        try {
            DataInputStream dataInputStream=new DataInputStream(serverSocket.getInputStream());
            BufferedInputStream bufferedInputStream=new BufferedInputStream(dataInputStream);
            byte[] buffer=bufferedInputStream.readAllBytes();
            int FileBytes=bufferedInputStream.available();



            int bytePresenti=buffer.length;
            System.out.println("Ricevuti "+ bytePresenti+ " bytes");


            Scanner scanner= new Scanner(System.in);
            System.out.println("Che nome diamo a questo file?");
            String nomeFile=scanner.nextLine();
            System.out.println("Che estensione ha?");
            String estensione=scanner.nextLine();

            File file=new File(nomeFile=nomeFile+"."+estensione);
            System.out.println("'"+nomeFile+"' creato \n");
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(buffer);
            fileOutputStream.close();
        } catch (IOException e) {throw new RuntimeException(e);}





    }
}
