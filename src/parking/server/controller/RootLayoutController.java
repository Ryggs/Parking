package parking.server.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
//import parking.server.bundles.*;


public class RootLayoutController {

    @FXML
    private MenuBar menuBar;

    private FXMLLoader rootLoader;
    private ResourceBundle bundle;

    public RootLayoutController() {
        rootLoader = new FXMLLoader();
        bundle = ResourceBundle.getBundle("parking.server.bundles.messages");
        rootLoader.setResources(bundle);
    }


    @FXML
    private BorderPane borderPane;


    public void startApp() throws IOException {


//        System.out.println("Res: " + getClass().getResource("../view/RootLayout.fxml"));
        rootLoader.setLocation(getClass().getResource("../view/RootLayout.fxml"));

        borderPane = rootLoader.load();

        Stage appStage = new Stage();
        Scene scene = new Scene(borderPane);
        appStage.setScene(scene);
        appStage.setTitle(bundle.getString("title.application"));
        appStage.show();
        loadMainMenuScreen();

    }





    public void loadMainMenuScreen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/MainMenuPane.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("parking.server.bundles.messages");
        loader.setResources(bundle);
        //System.out.println(this.getClass().getResource("../view/MainMenuPane.fxml"));
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

    @FXML
    void setLocalePolish(ActionEvent event) throws IOException {
        Locale.setDefault(new Locale("pl"));
        System.out.println(Locale.getDefault());
        // Close window
        ((Node) menuBar).getScene().getWindow().hide();
        startApp();
    }

    @FXML
    void setLocaleEnglish(ActionEvent event) throws IOException {
        Locale.setDefault(new Locale("en"));
        System.out.println(Locale.getDefault());
        // Close window


        ((Node) menuBar).getScene().getWindow().hide();
        startApp();

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
