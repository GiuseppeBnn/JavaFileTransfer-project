import java.io.*;
import java.lang.management.GarbageCollectorMXBean;
import java.net.Socket;

public class FileSender extends Thread{
    private String requestedFile;
    private byte[] ThreadBuffer;
    private Socket clientSocket;
    private DataOutputStream dataOutputStream;
    private String esito;

    private void sendStringtoSocket(Socket socket,String messaggio){
        try {
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
            PrintWriter printWriter=new PrintWriter(bufferedWriter,true);

            printWriter.println(messaggio);

            dataOutputStream.close();
            bufferedWriter.close();
            printWriter.close();
        } catch (IOException e) {System.out.println("errore sendStringtoSocket");}
    }

    private void RequestedFileOpener(){
        FileInputStream fileInputStream;
        DataInputStream dataInputStream;
        try {
            fileInputStream=new FileInputStream(this.requestedFile);
            dataInputStream=new DataInputStream(fileInputStream);
            this.ThreadBuffer=new byte[dataInputStream.available()];
            dataInputStream.readFully(this.ThreadBuffer);

            fileInputStream.close();
            dataInputStream.close();
        }catch(FileNotFoundException e){
            System.out.println("file non trovato");
            this.esito="/nf";
        }catch (IOException e) {System.out.println("errore nella creazione del buffer");}
    }

    private void SendFile(){
        if(this.esito.compareTo("/nf")==0){
            this.sendStringtoSocket(this.clientSocket,"File non trovato");
        }else {
            try {
                this.dataOutputStream = new DataOutputStream(this.clientSocket.getOutputStream());
                this.dataOutputStream.write(this.ThreadBuffer);
                this.dataOutputStream.flush();
            } catch (IOException e) {
                System.out.println("errore SendFile");
            }
        }
    }
    FileSender(String request,Socket client){
        this.requestedFile=request;
        this.clientSocket=client;
    }
    private void closeAll(){
        try {
            this.dataOutputStream.close();
        } catch (IOException e) {System.out.println("errore chiusura dataOutputStream");}
        try {
            this.clientSocket.close();
        } catch (IOException e) {System.out.println("errore chiusura Socket");}
    }


    @Override
    public void run(){
        this.RequestedFileOpener();
        this.SendFile();
        this.closeAll();
    }

}
