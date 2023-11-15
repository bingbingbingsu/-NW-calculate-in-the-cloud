import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class Client {
    public static void main(String[] args){
        final String fileName = "server_info.dat";
        String address = "localhost";
        int port = 7777;

        try {
            File file = new File(fileName);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                address = br.readLine();
                port = Integer.parseInt(br.readLine());
                br.close();
            }
            else {
                System.out.println("(Clinet) "+fileName+" is not exist");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Socket socket = null;
        try {
            socket = new Socket(address, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Command : numA + numB (+, -, *, /)\nex :10 * 30");
            while (true) {
                String command = br.readLine();
                if (command.toLowerCase().equals("end")) {
                    out.write("STOP\n");
                    out.flush();
                    break;
                }
                String arr[] = command.split(" ");
                if (arr.length < 2) {
                    System.out.println("(Client) Wrong Input");
                    continue;
                }
                String message = null;
                switch (arr[1]) {
                    case "+":
                        message = "ADD"+" "+arr[0]+" "+arr[2];
                        break;
                    case "-":
                        message = "MIN"+" "+arr[0]+" "+arr[2];
                        break;
                    case "*":
                        message = "MUL"+" "+arr[0]+" "+arr[2];
                        break;
                    case "/":
                        message = "DIV"+" "+arr[0]+" "+arr[2];
                        break;
                
                    default:
                        message = "NOT -1 -1";
                        break;
                }
                if (arr.length>3) {
                    for (int i = 3; i < arr.length; i++) {
                        message = message + " " + arr[i];
                    }
                }
                message = message +"\n";
                out.write(message);
                out.flush();

                String inputString = in.readLine();
                StringTokenizer st = new StringTokenizer(inputString, " ");
                String stateCode = st.nextToken();
                if (stateCode.equals("200")) {
                    String answer = st.nextToken();
                    System.out.println("Answer : " + answer);
                }
                else {
                    System.out.print("Incorrect : ");
                    switch (stateCode) {
                        case "404":
                            System.out.println("Wrong operation");
                            break;
                        case "999":
                            System.out.println("Too many arguments");
                            break;
                        case "600":
                            System.out.println("Divide by Zero");
                            break;
                    
                        default:
                            System.out.println("Other Errors");
                            break;
                    }
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