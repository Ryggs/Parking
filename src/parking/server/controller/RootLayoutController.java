package parking.server.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
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
        loadMainMenuScreen();

    }





    public void loadMainMenuScreen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/MainMenuPane.fxml"));
        System.out.println(this.getClass().getResource("../view/MainMenuPane.fxml"));
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainMenuController menuController = loader.getController();
        menuController.setRootController(this);
        setScreen(pane);
    }




    /**
     * Sets a current view of application to center of RootLayout BorderPane
     * @param pane - pane to be set
     */
    public void setScreen(Pane pane) {

        borderPane.setCenter(pane);


    }

    public RootLayoutController getController(){
        return this;
    }



}
