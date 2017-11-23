package parking.bar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainBarExit extends Application {

    //This is our PrimaryStage (It contains everything)
    private Stage primaryStage;

    //This is the BorderPane of RootLayout
    private BorderPane rootLayout;


    @Override
    public void start(Stage primaryStage) throws Exception{

        //1) Declare a primary stage (Everything will be on this stage)
        this.primaryStage = primaryStage;

        //Set a title for primary stage
        this.primaryStage.setTitle("ExitEnter");

        //2) Initialize RootLayout
        initRootLayout();

        //3) Display the EnterBar View
        showExitBarView();

    }

    //Initializes the root layout.
    public void initRootLayout() {
        try {
            //First, load root layout from RootLayout.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainBarExit.class.getResource("view/PrimaryLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            //Second, show the scene containing the root layout.
            Scene scene = new Scene(rootLayout); //We are sending rootLayout to the Scene.
            primaryStage.setScene(scene); //Set the scene in primary stage.

            //Third, show the primary stage
            primaryStage.setFullScreenExitHint("EXIT BAR");
            primaryStage.setFullScreen(true);
            primaryStage.setAlwaysOnTop(true);
            primaryStage.show(); //Display the primary stage
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Shows Enter Bar view in the middle of the screen
    public void showExitBarView() {
        try {
            //load BarEnterView
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainBarExit.class.getResource("view/BarExitLayout.fxml"));
            AnchorPane BarExitView = (AnchorPane) loader.load();

            // Set BarEnterView into the center of root layout.
            rootLayout.setCenter(BarExitView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}