import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientFileReceiver {

    private static DataInputStream dataInputStream;
    private static OutputStreamWriter outputStreamWriter;
    private static void sendStringtoSocket(Socket socket, String messaggio){
        try {
            outputStreamWriter=new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
            PrintWriter printWriter=new PrintWriter(bufferedWriter,true);

            printWriter.println(messaggio);





        } catch (IOException e) {System.out.println("errore sendStringtoSocket");}
    }
    private String recvStringFromSocket(Socket socket){
        try {
            InputStreamReader inputStreamReader=new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String stringa=bufferedReader.readLine();
            return stringa;
        } catch (IOException e) {System.out.println("errore recvStringFromScoket");return null;}
    }
    public static void main(String[] args){
        Socket serverSocket=null;
        Scanner scanner= new Scanner(System.in);


        try {
            InetAddress serverAddress=InetAddress.getByName("192.168.1.221");
            serverSocket=new Socket(serverAddress,6060);
        } catch (IOException e) {throw new RuntimeException(e);}


        try {
            System.out.println("Scrivi il file che vuoi ricevere da remoto");
            String richiesta=scanner.nextLine();
            System.out.println("mando richiesta di: "+richiesta);

            sendStringtoSocket(serverSocket,richiesta);


            if(serverSocket.isClosed()) {
                System.out.println("CHIUSA SOCKET");
            }

            dataInputStream=new DataInputStream(serverSocket.getInputStream());
            BufferedInputStream bufferedInputStream=new BufferedInputStream(dataInputStream);
            byte[] buffer=bufferedInputStream.readAllBytes();
            int FileBytes=bufferedInputStream.available();



            int bytePresenti=buffer.length;
            System.out.println("Ricevuti "+ bytePresenti+ " bytes");
            if(buffer.toString()==" File non trovato"){
                System.out.println("File non presente, riavvia il programma");
            }




            File file=new File(richiesta);
            System.out.println("'"+richiesta+"' creato \n");
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(buffer);
            fileOutputStream.close();
        } catch (IOException e) {e.printStackTrace();}





    }
}
