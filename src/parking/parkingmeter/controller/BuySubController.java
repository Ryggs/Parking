package parking.parkingmeter.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parking.parkingmeter.utils.FXMLUtils;

import java.io.IOException;

public class BuySubController {
    RootLayoutController rootController;
    final String BUY_SUB_FXML_PATH = "../view/BuySubLoginPane.fxml";
    final String CREATE_ACC_FXML_PATH = "../view/CreateAccountPane.fxml";

    public void backToMenu(ActionEvent actionEvent) {
        Stage currStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        rootController.loadRootLayout(currStage);
    }

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }

    public void buySubLogin(ActionEvent actionEvent) {
        FXMLLoader loader = FXMLUtils.getLoader(BUY_SUB_FXML_PATH);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage currStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currStage.setScene(scene);

        BuySubLoginController buySubController = loader.getController();
        buySubController.setRootController(rootController);
    }
    public void createAccount(ActionEvent actionEvent) {
        FXMLLoader loader = FXMLUtils.getLoader(CREATE_ACC_FXML_PATH);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage currStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currStage.setScene(scene);

        CreateAccountController createAccountController = loader.getController();
        createAccountController.setRootController(rootController);
    }


}
