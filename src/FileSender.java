import java.io.*;
import java.lang.management.GarbageCollectorMXBean;
import java.net.Socket;
import java.nio.ByteBuffer;

public class FileSender extends Thread{
    private String requestedFile;
    private byte[] ThreadBuffer;
    private ByteBuffer byteBuffer;
    private Socket clientSocket;
    private String esito="   ";

    private void sendStringtoSocket(Socket socket,String messaggio){
        if(clientSocket.isClosed()){
            System.out.println("SOCKET CHIUSA");
            return;
        }
        try {
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
            PrintWriter printWriter=new PrintWriter(bufferedWriter,true);

            printWriter.println(messaggio);
        } catch (IOException e) {System.out.println("errore sendStringtoSocket");
                                    e.printStackTrace();}
    }

    private void RequestedFileOpener(){
        FileInputStream fileInputStream;
        DataInputStream dataInputStream;
        try {
            fileInputStream=new FileInputStream(this.requestedFile);
            dataInputStream=new DataInputStream(fileInputStream);
            this.ThreadBuffer=new byte[dataInputStream.available()];
            dataInputStream.readFully(this.ThreadBuffer);
            System.out.println(this.ThreadBuffer);

            fileInputStream.close();

        }catch(FileNotFoundException e){
            System.out.println("file non trovato");
            this.esito="/nf";
        }catch (IOException e) {System.out.println("errore nella creazione del buffer");}
    }

    private void SendFile() {
        if(this.esito.compareTo("/nf")==0){
            this.sendStringtoSocket(this.clientSocket,"File non trovato");
            try {
                this.clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(this.clientSocket.getOutputStream());
                dataOutputStream.write(this.ThreadBuffer);
                dataOutputStream.flush();
            } catch (IOException e) {
                System.out.println("errore SendFile");
            }
        }
    }
    FileSender(String request,Socket client){
        System.out.println("richiesto file: "+request);
        this.requestedFile=request;
        this.clientSocket=client;
        if(this.clientSocket.isClosed()){
            System.out.println("SOCKET CHIUSA 1");
        }

    }



    @Override
    public void run(){
        this.RequestedFileOpener();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.SendFile();
        //this.closeAll();
        try {
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
