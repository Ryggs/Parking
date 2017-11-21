package parking.server.controller;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import parking.server.utils.DialogsUtils;
import parking.server.utils.FXMLUtils;

import java.io.IOException;
import java.util.Locale;
//import parking.server.bundles.*;


public class RootLayoutController {

    public static final String ROOT_LAYOUT_FXML_PATH = "../view/RootLayout.fxml";
    public static final String MAIN_MENU_PANE_FXML_PATH = "../view/MainMenuPane.fxml";
    @FXML
    private MenuBar menuBar;

    @FXML
    private BorderPane borderPane;


    public void startApp() throws IOException {

        borderPane = (BorderPane) FXMLUtils.fxmlLoad(ROOT_LAYOUT_FXML_PATH);

        System.out.println(borderPane.getChildren());
        Stage appStage = new Stage();
        Scene scene = new Scene(borderPane);

        appStage.setScene(scene);
        appStage.setTitle(FXMLUtils.getResourceBundle().getString("title.application"));
        appStage.show();
        loadMainMenuScreen();

    }





    public void loadMainMenuScreen() {

        AnchorPane pane = (AnchorPane) FXMLUtils.fxmlLoad(MAIN_MENU_PANE_FXML_PATH);

        MainMenuController menuController = FXMLUtils.getLoader().getController();
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

        System.out.println("Spr co zwraca rootlayout get controller");
        return this;
    }

    @FXML
    public void closeApplication() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void setModenaStyle() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
    }

    @FXML
    public void setCaspianStyle() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
    }

    @FXML
    public void setAlwaysOnTop(ActionEvent actionEvent) {
        boolean check = ((CheckMenuItem)actionEvent.getSource()).selectedProperty().get();
        Stage currStage = (Stage) borderPane.getScene().getWindow();
        currStage.setAlwaysOnTop(check);

    }

    public void about() {
        DialogsUtils.dialogAboutApplication();
    }
}