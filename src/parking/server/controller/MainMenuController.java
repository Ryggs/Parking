package parking.server.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import parking.server.utils.FXMLUtils;

import java.io.IOException;
import java.util.ResourceBundle;


public class MainMenuController {

    public static final String PRICES_PANE_FXML_PATH = "../view/PricesPane.fxml";
    public static final String ADMINS_PANE_FXML_PATH = "../view/AdminsPane.fxml";
    public static final String SUBSCRIPTIONS_PANE_FXML_PATH = "../view/SubscriptionsPane.fxml";
    public static final String LOG_PANE_FXML_PATH = "../view/LogPane.fxml";

    private RootLayoutController rootController;

    @FXML
    void editPrices(ActionEvent event) {

        Pane pane = FXMLUtils.fxmlLoad(PRICES_PANE_FXML_PATH);

        PricesPaneController pricesController = FXMLUtils.getLoader().getController();
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

        Pane pane = FXMLUtils.fxmlLoad(ADMINS_PANE_FXML_PATH);

        AdminsPaneController adminsPaneController = FXMLUtils.getLoader().getController();
        adminsPaneController.setRootController(rootController);
        rootController.setScreen(pane);
    }

    @FXML
    void manageSubscriptions(ActionEvent event) {

        Pane pane = FXMLUtils.fxmlLoad(SUBSCRIPTIONS_PANE_FXML_PATH);

        SubscriptionsPaneController subscriptionsPaneController = FXMLUtils.getLoader().getController();
        subscriptionsPaneController.setRootController(rootController);
        rootController.setScreen(pane);
    }

    @FXML
    void showParkingLog(ActionEvent event) {

        Pane pane = FXMLUtils.fxmlLoad(LOG_PANE_FXML_PATH);

        LogPaneController logController = FXMLUtils.getLoader().getController();
        logController.setRootController(rootController);
        rootController.setScreen(pane);
    }

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }

}
