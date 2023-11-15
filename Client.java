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
        final String fileName = "server_info.dat";// get server information from this file
        String address = "localhost"; //default address
        int port = 7777; // default port

        try {
            File file = new File(fileName);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                address = br.readLine();
                port = Integer.parseInt(br.readLine());
                br.close();
            }
            else { // when file is not exists, default ip, port are used
                System.out.println("(Clinet) "+fileName+" is not exist");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Socket socket = null;
        try {
            socket = new Socket(address, port); // connect server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Command : numA + numB (+, -, *, /)\nex :10 * 30");
            while (true) {
                String command = br.readLine(); // input by user 
                if (command.toLowerCase().equals("end")) { // to unconnect
                    out.write("STOP\n");
                    out.flush();
                    break;
                }
                String arr[] = command.split(" ");
                String sign = null;
                if (arr.length < 2) {
                    sign = "SMALL";
                }
                else {
                    sign = arr[1];
                }
                String message = null;
                switch (sign) {
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
                    case "SMALL":
                        message = "SMALL -1 -1"; // server must take at least 3 arguments
                        break;
                
                    default:
                        message = "NOT -1 -1"; // server must take at least 3 arguments
                        break;
                }
                if (arr.length>3) { // if user input arguments more than 3
                    for (int i = 3; i < arr.length; i++) {
                        message = message + " " + arr[i];
                    }
                }
                message = message +"\n";
                out.write(message);
                out.flush();// sending a message to server

                String inputString = in.readLine();// recieve a message from server
                StringTokenizer st = new StringTokenizer(inputString, " ");
                String stateCode = st.nextToken();
                if (stateCode.equals("200")) { // correct message
                    String answer = st.nextToken();
                    System.out.println("Answer : " + answer);
                }
                else {
                    System.out.print("Incorrect : "); // incorrect
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
                        case "444":
                            System.out.println("Small arguments");
                            break;
                        case "987":
                            System.out.println("Not a number");
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