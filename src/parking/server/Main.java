package parking.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    // Stage containing current view ??
    private Stage primaryStage;

    //
    private AnchorPane loginLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showLoginWindow();
        primaryStage.setTitle("Parking Server Login");

        primaryStage.show();
    }

    public void showLoginWindow() {
        try {
            // Init login layout
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("view/LoginPopup.fxml"));
            loginLayout = loader.load();

            // Show the scene
            Scene scene = new Scene(loginLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error while showing login window");
            e.printStackTrace();
        }
    }

}



























