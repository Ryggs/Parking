package parking.parkingmeter.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parking.parkingmeter.utils.FXMLUtils;

import java.io.IOException;

public class PayTicketController {
    RootLayoutController rootController;

    private static final String PAY_CASH_FXML_PATH = "../view/PayTicketCashPane.fxml";
    private static final String PAY_SUB_FXML_PATH = "../view/PayTicketSubPane.fxml";

    @FXML
    void cashPayment(ActionEvent event) {
        FXMLLoader loader = FXMLUtils.getLoader(PAY_CASH_FXML_PATH);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currStage.setScene(scene);

        PayTicketCashController payTicketCashController = loader.getController();
        payTicketCashController.setRootController(rootController);
    }

    @FXML
    void subPayment(ActionEvent event) {
        FXMLLoader loader = FXMLUtils.getLoader(PAY_SUB_FXML_PATH);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currStage.setScene(scene);

        PayTicketSubController payTicketSubController = loader.getController();
        payTicketSubController.setRootController(rootController);
    }

    public void backToMenu(ActionEvent actionEvent) {

        Stage currStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        rootController.loadRootLayout(currStage);
    }

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }
}
