import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(7777);
        System.out.println("Server is running...");
        ExecutorService serverPool = Executors.newFixedThreadPool(10);
        while (true) {
            Socket socket = serverSocket.accept();
            serverPool.execute(new serverThread(socket));
        }
    }

    private static  class serverThread implements Runnable {
        private Socket socket;
        serverThread(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            System.out.println("Connected : " + socket);
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                
                while (true) {
                    String inputString = in.readLine();
                    StringTokenizer st = new StringTokenizer(inputString, " ");
                    String method = st.nextToken();
                    double A = Double.parseDouble(st.nextToken());
                    double B = Double.parseDouble(st.nextToken());
                    if (st.hasMoreTokens()) { // Too many aruments
                        out.write("999");
                        out.flush();
                        continue;
                    }

                    switch (method) {
                        case "ADD":
                            out.write("200" + " " + (A+B));
                            out.flush();
                            break;
                        case "MIN":
                            out.write("200" + " " + (A+B));
                            out.flush();
                            break;
                        case "MUL":
                            out.write("200" + " " + (A+B));
                            out.flush();
                            break;
                        case "DIV":
                            out.write("200" + " " + (A+B));
                            out.flush();
                            break;
                    
                        default: // Wrong

                            break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}