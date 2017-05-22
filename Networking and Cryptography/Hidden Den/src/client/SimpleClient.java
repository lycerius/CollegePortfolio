package client;

import javafx.gui.Controller;
import util.CryptoUtil;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class SimpleClient{

    private Socket socket;
    private String hostName;
    private int portNumber;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private Controller controller;

    private enum ClientStatus {RUNNING, SHUTTING_DOWN}
    private ClientStatus status;

    /** Sets up SimpleClient with a default hostname of "localhost", and a default port number of 50000.
     * */
    public SimpleClient() {
        hostName = "localhost";
        portNumber = 50001;
        status = ClientStatus.RUNNING;
    }

    /** Sets up the SimpleClient with the specified hostName and portNumber.
     * @param hostName the hostName or IP address to connect to.
     * @param portNumber the portNumber to connect to on the server.
     * */
    public SimpleClient(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        status = ClientStatus.RUNNING;
    }

    /** Sets up the client's socket to connect to the server.
     * */
    public void setupSocket() {
        try {
            this.socket = new Socket(hostName, portNumber);
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage() + " in setupClient()");
        }
    }

    /** Sets up the inputStream and outputStream.
     * */
    public void setupStreams() {
        try {
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage() + " in setupStreams()");
        }
    }

    /** Sends a message to the server.
     * @param message the message to be sent.
     * */
    public void sendMessage (String message) {
        if (status == ClientStatus.RUNNING) {
            try {
                byte[] byteMessage = CryptoUtil.encrypt(message);
                outputStream.write(byteMessage);
            } catch (IOException e) {
                System.out.println("Exception: " + e.getMessage() + " in sendMessage()");
            }
        }
    }

    /** Runs a input thread that constantly reads from the server and updates the controller's chat box.
     * */
    public void runInputThread() {
        new Thread() {
            public void run() {
                String input = null;

                while (status == ClientStatus.RUNNING) {
                    try {
                        while (inputStream.available() == 0) {
                            Thread.sleep(10);
                        }

                        byte[] byteMessage = new byte[inputStream.available()];
                        inputStream.read(byteMessage);

                        input = CryptoUtil.decrypt(byteMessage);

                        controller.updateChatBox(input);
                    } catch (IOException e) {
                        System.out.println("Exception: " + e.getMessage() + " in runInputThread()");
                    } catch (InterruptedException e) {
                        System.out.println("Exception: " + e.getMessage() + " in handleClient()");
                    }
                }
            }
        }.start();
    }

    /** Sets the controller being used in order to get access to some of its methods.
     * */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /** Closes the client by closing the streams and the socket that connects it to the server. */
    public void closeClient() {
        try {
            status = ClientStatus.SHUTTING_DOWN;
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage() + " in closeClient()");
        }
    }
}