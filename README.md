# -NW-calculate-in-the-cloud

# Client

## Overview
This Java program serves as a simple client socket application. It connects to a server, sends mathematical operations, and receives the results.

## Usage
1. The client reads server information from a file (`server_info.dat`) or uses default values.
2. It establishes a connection to the server.
3. The user inputs mathematical operations in the form of "numA operator numB" (e.g., `10 * 30`).
4. The client sends the operation to the server and receives the result.

## Code Explanation
- **Resource Management:** Utilizes `try-with-resources` to manage resources like `Socket`, `BufferedReader`, and `BufferedWriter`.
- **Exception Handling:** Catches exceptions, prints the stack trace, and continues execution.
- **Operation Handling:** Parses user input, sends the operation to the server, and prints the result or error messages.

## Example Commands
- `10 * 30`: Multiplies 10 by 30.
- `end`: Terminates the client-server connection.

## Notes
- The server information file (`server_info.dat`) should contain the server's address on the first line and the port on the second line.
- The client supports basic arithmetic operations: addition, subtraction, multiplication, and division.

# Server

## Overview
This Java program acts as a server for basic socket communication. It listens for client connections, receives mathematical operations, performs the calculations, and sends the results back to clients. Below is an explanation of the code:

## Usage
1. The server listens on port 7777.
2. It accepts client connections and delegates them to a thread from a thread pool.
3. The server handles basic arithmetic operations: addition, subtraction, multiplication, and division.
4. If the client sends the command "STOP," the server thread disconnects.

## Code Explanation
- **Server:** Main class for starting the server.
  - **serverPool:** Thread pool for handling multiple client connections.
  - **ServerThread:** Inner class implementing the `Runnable` interface for processing individual client connections.
- **isNumber:** Helper method to check if a string can be converted to a number.

## How to Run
1. Compile the Java file: `javac Server.java`
2. Run the compiled file: `java Server`

## Example Commands
- `10 * 30`: Multiplies 10 by 30.
- `end`: Terminates the client-server connection.

## Notes
- The server supports multi-threaded communication with clients.
- Mathematical operations are performed based on client requests.
