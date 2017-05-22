package server;

import util.CryptoUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import static util.CryptoUtil.encrypt;

/** Class that has the capabilities to act as a server, constantly accept multiple connections requests from clients, broadcast to those clients, and
 * listen to input from those clients.
 * */

public class MultiClient_Server {

    private ServerSocket serverSocket;
    private LinkedList<Socket> sockets;
    private LinkedList<DataOutputStream> toClientStreams;
    private LinkedList<DataInputStream> fromClientStreams;
    private LinkedList<String> clientNames;

    private ServerStatus status;
    private int numberConnections;
    private int connectionsListeningTo;

    /** Setups the server with a default port number of 50000.
     * */
    public MultiClient_Server() throws IOException{
        status = ServerStatus.RUNNING;
        numberConnections = 0;
        connectionsListeningTo = 0;

        serverSocket = new ServerSocket(50001);

        sockets = new LinkedList<>();
        toClientStreams = new LinkedList<>();
        fromClientStreams = new LinkedList<>();
        clientNames = new LinkedList<>();
    }

    /** Setups the server with a specified port number.
     * @param portNumber the port number that the server runs on.
     * */
    public MultiClient_Server(int portNumber) throws IOException{
        status = ServerStatus.RUNNING;

        numberConnections = 0;
        connectionsListeningTo = 0;

        serverSocket = new ServerSocket(portNumber);

        sockets = new LinkedList<>();
        toClientStreams = new LinkedList<>();
        fromClientStreams = new LinkedList<>();
        clientNames = new LinkedList<>();
    }

    private enum ServerStatus {RUNNING, SHUTTING_DOWN};

    public static void main(String[] args) throws IOException {

        CryptoUtil.init("bulbasaur");

        MultiClient_Server server = new MultiClient_Server(50001);

        server.acceptConnections();
        server.handleClients();
    }

    /** Constantly accepts all connection requests by starting a new thread that waits for requests and accepts them.
     * */
    public void acceptConnections() {

        new Thread() {
            public void run() {
                while (status == ServerStatus.RUNNING) {
                    try {
                        Socket clientSocket = null;

                        clientSocket = serverSocket.accept();

                        sockets.add(clientSocket);
                        toClientStreams.add(new DataOutputStream(clientSocket.getOutputStream()));
                        fromClientStreams.add(new DataInputStream(clientSocket.getInputStream()));

                        numberConnections++;
                    } catch (IOException e) {
                        System.out.println("Exception: " + e.getMessage() + " in acceptConnections().");
                    }
                }
            }
        }.start();
    }

    /** Constantly listens to clients, and broadcasts any messages from the client to all the clients connected to the server.
     * Each client receives their own thread on the server.
     * */
    public void handleClients() {

        new Thread() {
            public void run() {
                while (status == ServerStatus.RUNNING) {
                    try {
                        if (connectionsListeningTo < numberConnections) {

                            int clientNumber = connectionsListeningTo;

                            HandleClientThread thread = new HandleClientThread(
                                    clientNumber,
                                    "Anon",
                                    fromClientStreams.get(clientNumber),
                                    toClientStreams.get(clientNumber),
                                    sockets.get(clientNumber));

                            thread.startThread();

                            connectionsListeningTo++;
                        }

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Exception: " + e.getMessage() + " in handleClients().");
                    }
                }
            }
        }.start();
    }

    /** Inner class thread that is used by handleClients() to handle reading messages from clients and broadcasting those messages to all clients.
     * * */
    public class HandleClientThread implements Runnable {

        private int clientNumber;
        private String clientName;
        private DataInputStream inputStream;
        private DataOutputStream outputStream;
        private Socket socket;

        HandleClientThread(int clientNumber, String clientName, DataInputStream inputStream, DataOutputStream outputStream, Socket socket) {
            this.clientNumber = clientNumber;
            this.clientName = clientName;
            this.inputStream = inputStream;
            this.outputStream = outputStream;
            this.socket = socket;
        }

        public void startThread() {
            Thread aThread = new Thread(this);
            aThread.start();
        }

        @Override
        public void run() {
            setupClient();
            handleClient();
        }

        /**
         * Setups the client's username by asking the client, "What is your username?").
         * Once the user answers, it broadcasts it to the entire room.
         */
        private void setupClient() {
            try {
                String question = "What is your username?";
                byte[] byteMessage = encrypt(question);

                outputStream.write(byteMessage);

                while (inputStream.available() == 0) {
                    Thread.sleep(10);
                }

                byte[] clientNameInBytes = new byte[inputStream.available()];
                inputStream.read(clientNameInBytes);

                clientName = CryptoUtil.decrypt(clientNameInBytes);

                clientNames.add(clientName);
                broadcastServerMessage(clientName + " has joined the room!");
            } catch (IOException e) {
                System.out.println("Exception: " + e.getMessage() + " in setupUserName().");
            } catch (InterruptedException e) {
                System.out.println("Exception: " + e.getMessage() + " in handleClient()");
            }
        }

        private void handleClient() {
            String message;
            try {
                while (status == ServerStatus.RUNNING) {
                    while (inputStream.available() == 0) {
                        Thread.sleep(10);
                    }

                    byte[] byteMessage = new byte[inputStream.available()];

                    inputStream.read(byteMessage);

                    message = CryptoUtil.decrypt(byteMessage);

                    broadcastClientMessage(message);
                }
            } catch (IOException e) {
                closeClientConnection();
                return;
            } catch (InterruptedException e) {
                System.out.println("Exception: " + e.getMessage() + " in handleClient()");
            }
        }

        private void broadcastClientMessage(String message) {
            message = (clientName + ": " + message);

            for (int i = 0; i < numberConnections; i++) {
                try {
                    byte[] byteMessage = CryptoUtil.encrypt(message);
                    toClientStreams.get(i).write(byteMessage);
                } catch (IOException e) {
                    System.out.println("Exception: " + e.getMessage() + " in broadClientMessage().");
                }
            }
        }

        private void closeClientConnection() {
            try {
                outputStream.close();
                inputStream.close();
                socket.close();

                toClientStreams.remove(clientNumber);
                fromClientStreams.remove(clientNumber);
                sockets.remove(clientNumber);
                clientNames.remove(clientName);

                numberConnections--;
                connectionsListeningTo--;

                System.out.println("Client: " + clientNumber + ", also known as, " + clientName + ", successfully closed");
            } catch (IOException e) {
                System.out.println("Exception: " + e.getMessage() + "in closeClientConnection");
            }
        }
    }

    /** Shuts down the server, and alerts all clients before it does so.
     * */
    public void shutDown() {
        try {
            for (int i = 0; i < numberConnections; i++) {
                toClientStreams.get(i).writeUTF("Server is shutting down in 10 seconds.");
            }
            Thread.sleep(10000);
            /**** Not Finished ****/
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage() + " in broadcastToClients().");
        } catch (InterruptedException e) {
            System.out.println("Exception: " + e.getMessage() + " in broadcastToClients().");
        }
    }

    /** Broadcasts a single message from the server to all clients
     * @param message the message to be broadcast.
     * */
    public void broadcastServerMessage(String message) {
        try {
            message = ("Server: " + message);

            for (int i = 0; i < numberConnections; i++) {

                byte[] byteMessage = CryptoUtil.encrypt(message);

                toClientStreams.get(i).write(byteMessage);
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage() + " in broadcastToClients().");
        }
    }

    /** Sends a message once to a specific client.
     * @param clientNumber the specific client to broadcast to.
     * @param message the message to be sent to the client.
     * */
    public void sendToClient(String message, int clientNumber) {
        try {
            toClientStreams.get(clientNumber).writeUTF(message);
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage() + " in broadcastToClient().");
        }
    }
}
