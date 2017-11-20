package parking.server.controller;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;


public class MainMenuController {

    //private MainMenuController mainMenuController;
    private RootLayoutController rootController;

    @FXML
    void editPrices(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/PricesPane.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("parking.server.bundles.messages");
        loader.setResources(bundle);
        System.out.println(this.getClass().getResource("../view/PricesPane.fxml"));
        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
       // this.rootLaoutController = rootLayoutController.getController();
        System.out.println(pane.getChildren());
        PricesPaneController pricesController = loader.getController();
        pricesController.setRootController(rootController);
        rootController.setScreen(pane);
    }

    @FXML
    void logout(ActionEvent event) {

        LoginPopupController loginInstance = new LoginPopupController();

        loginInstance.showLoginWindow(new Stage());
        // Close window
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void manageAdmins(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AdminsPane.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("parking.server.bundles.messages");
        loader.setResources(bundle);
        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // this.rootLaoutController = rootLayoutController.getController();
        System.out.println(pane.getChildren());
        AdminsPaneController adminsPaneController = loader.getController();
        adminsPaneController.setRootController(rootController);
        rootController.setScreen(pane);
    }

    @FXML
    void manageSubscriptions(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/SubscriptionsPane.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("parking.server.bundles.messages");
        loader.setResources(bundle);
        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // this.rootLaoutController = rootLayoutController.getController();
        System.out.println(pane.getChildren());
        SubscriptionsPaneController subscriptionsPaneController = loader.getController();
        subscriptionsPaneController.setRootController(rootController);
        rootController.setScreen(pane);
    }

    @FXML
    void showParkingLog(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/LogPane.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("parking.server.bundles.messages");
        loader.setResources(bundle);
        System.out.println(loader.getResources());
        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // this.rootLaoutController = rootLayoutController.getController();
        System.out.println(pane.getChildren());
        LogPaneController logController = loader.getController();
        logController.setRootController(rootController);
        rootController.setScreen(pane);
    }


    @FXML
    void initialize() throws IOException {

    }
    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }

}
