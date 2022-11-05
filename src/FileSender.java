import java.io.*;
import java.net.Socket;

public class FileSender extends Thread{
    private String requestedFile;
    private byte[] ThreadBuffer;
    private Socket clientSocket;

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
        }catch(FileNotFoundException e){System.out.println("file non trovato");
        }catch (IOException e) {System.out.println("errore nella creazione del buffer");}
    }

    private void SendFile(){

    }


    Socket clientSocket;

    FileSender(String request,Socket client){
        this.requestedFile=request;
        this.clientSocket=client;

    }
    private void initializeThread(){

    }

}
