import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFileSender {

        private static int port=6060;
        private static ServerSocket serverListener;

        private static void ServerSocketInitializer(){
            try {
                serverListener=new ServerSocket(port);
            } catch (IOException e) {System.out.println("errore assegnazione socket");}
        }

        private static BufferedReader initializeReader(Socket socket){
            try {
                InputStreamReader inputStreamReader=new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                return bufferedReader;

            } catch (IOException e) {System.out.println("errore inputStreamReader"); return null;}
        }

        public static void main(String[] args) throws IOException {
            ServerSocketInitializer();
            boolean flag=true;
            while(flag){
                Socket clientSocket=serverListener.accept();
                BufferedReader bufferedReader=initializeReader(clientSocket);
                String requestedFile=bufferedReader.readLine();

                Thread RequestSolverThread=new Thread(new FileSender(requestedFile,clientSocket));
                RequestSolverThread.start();

            }
        }

}