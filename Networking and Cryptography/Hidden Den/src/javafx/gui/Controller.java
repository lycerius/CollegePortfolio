package javafx.gui;

import client.SimpleClient;
import javafx.Main;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.LinkedList;

public class Controller {

    @FXML
    private TextArea chatOutput;

    @FXML
    private TextArea chatInput;

    @FXML
    private TextArea userArea;

    private SimpleClient client;

    private Main main;

    private Stage stage;

    /** Whenever the user presses a key in chatInput, this method checks if it was a enter key and if it was, it sends the message to the server
     * and clears the chatInput box.*/
    @FXML
    private void onEnter(KeyEvent e) {
        if (e.getCode().toString().equals("ENTER")) {
            String message = chatInput.getText();
            message = message.substring(0, message.length() - 1);
            client.sendMessage(message);
            chatInput.clear();
        }
    }

    /** Sets what happens when the X button is pressed. */
    private void onClose() {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("User pressed x button. Closing Client and JavaFX Application");
                main.closeApplication();
            }
        });
    }

    /** Sets a bunch of custom event handling for certain things. */
    public void setCustomEvents() {
        onClose();
    }

    /** Updates the chatOutput box by appending the message on a new line.
     * @param message the message to be appended to the chat box. */
    public void updateChatBox(String message) {
        message = message.concat("\n");
        chatOutput.appendText(message);
    }

    /** Updates the users currently in the room. */
    public void updateUserList(LinkedList<String> userList) {
        for (String name : userList) {
            userArea.appendText(name.concat("\n"));
        }
    }

    /** Gives the Controller a reference to Main.
     * @param main the Main application to be set. */
    public void setMain(Main main) {
        this.main = main;
    }

    /** Gives the Controller a reference to Client.
     * @param client the client to be set. */
    public void setClient(SimpleClient client) {
        this.client = client;
    }

    /** Gives the Controller a reference to the Stage.
     * @param stage the stage to be set. */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
