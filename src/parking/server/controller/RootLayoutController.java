package parking.server.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import parking.server.Main;

import java.awt.*;
import java.io.IOException;


public class RootLayoutController {

    @FXML
    private BorderPane borderPane;



    public void startApp() throws IOException {
        FXMLLoader loader = new FXMLLoader();
//        System.out.println("Res: " + getClass().getResource("../view/RootLayout.fxml"));
        loader.setLocation(getClass().getResource("../view/RootLayout.fxml"));

        borderPane = loader.load();

        Stage appStage = new Stage();
        Scene scene = new Scene(borderPane);
        appStage.setScene(scene);
        appStage.setTitle("Parking Server");
        appStage.show();


//        System.out.println("center test: "+borderPane.getChildren());


        StackPane test = new StackPane();
        test.setStyle("-fx-background-color: rgba(100, 0, 0, 1);");
        test.setPrefSize(1024,768);
        setScreen(test);


    }


    /**
     * Sets a current view of application to center of RootLayout BorderPane
     * @param pane - pane to be set
     */
    public void setScreen(Pane pane) {

        borderPane.setCenter(pane);


    }



}
