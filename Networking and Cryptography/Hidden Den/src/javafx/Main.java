package javafx;

import client.SimpleClient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.gui.Controller;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CryptoUtil;

import java.io.IOException;

public class Main extends Application{

    private Stage stage;
    private FXMLLoader loader;
    private AnchorPane rootLayout;
    private Controller controller;
    private SimpleClient client;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setTitle("Hidden Den");

        CryptoUtil.init("bulbasaur");

        setupStructure();
        setupClient();
        setupController();
    }

    /** Sets up the structure of JavaFX, which is the loader that loads the GUI, the scene that contains the rootLayout and the stage.
     * */
    private void setupStructure() {
        try {
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/javafx/gui/Structure.fxml"));
            rootLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(rootLayout);

            stage.setTitle("Application");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage() + " in start()");
        }
    }

    /** Creates a instance of SimpleClient called client.
     *  Setups the socket, streams, and runs the input thread to get input from the server.
     * */
    private void setupClient() {
        client = new SimpleClient();
        client.setupSocket();
        client.setupStreams();
        client.runInputThread();
    }

    /** Sets up the controller to have access to Main, the SimpleClient class, and the Stage.
     *  Now that controller is set, client sets the controller as well.
     * */
    private void setupController() {
        controller = loader.getController();
        controller.setMain(this);
        controller.setClient(client);
        controller.setStage(stage);
        controller.setCustomEvents();
        client.setController(controller);
    }

    /** Closes the stage to end the JavaFX part of the program. */
    public void closeApplication() {
        client.closeClient();
        Platform.exit();
    }

    /** Launches the application and gives control to the start method. */
    public static void main(String[] args) {
        launch(args);
    }
}
