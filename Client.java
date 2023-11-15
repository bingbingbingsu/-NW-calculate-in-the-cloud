import java.io.BufferedReader;
import java.io.BufferedWriter;
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
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            address = br.readLine();
            port = Integer.parseInt(br.readLine());
            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Socket socket = null;
        try {
            socket = new Socket(address, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Command : numA + numB (+, -, *, /)\n ex:\n10 * 30");
            while (true) {
                String command = br.readLine();
                if (command.toLowerCase().equals("end")) {
                    out.write("STOP");
                    out.flush();
                    break;
                }
                String arr[] = command.split(" ");
                if (arr.length <1 ) {
                    System.out.println("Wrong Input");
                    continue;
                }
                switch (arr[1]) {
                    case "+":
                        out.write("ADD"+" "+arr[0]+" "+arr[2]+"\n");
                        break;
                    case "-":
                        out.write("MIN"+" "+arr[0]+" "+arr[2]+"\n");
                        break;
                    case "*":
                        out.write("MUL"+" "+arr[0]+" "+arr[2]+"\n");
                        break;
                    case "/":
                        out.write("DIV"+" "+arr[0]+" "+arr[2]+"\n");
                        break;
                
                    default:
                        System.out.println("Wrong input");
                        continue;
                }
                out.flush();

                String inputString = in.readLine();
                StringTokenizer st = new StringTokenizer(inputString, " ");
                String stateCode = st.nextToken();
                if (stateCode.equals("200")) {
                    String answer = st.nextToken();
                    System.out.println("Answer :" + answer);
                }
                else {
                    System.out.println("Incorrect :");
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