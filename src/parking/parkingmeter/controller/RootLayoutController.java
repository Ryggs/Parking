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
import java.util.Locale;

//
public class RootLayoutController {
    private final String ROOT_LAYOUT_FXML_PATH = "../view/RootLayout.fxml";
    private final String PAY_TICKET_FXML_PATH = "../view/PayTicketPane.fxml";
    private final String BUY_SUB_FXML_PATH = "../view/BuySubPane.fxml";
    RootLayoutController rootController;

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }
    @FXML
    void initialize() {
        setRootController(this);
    }

    public void payTicket(ActionEvent actionEvent) {
        FXMLLoader loader = FXMLUtils.getLoader(PAY_TICKET_FXML_PATH);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage currStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currStage.setScene(scene);

        PayTicketController payTicketController = loader.getController();
        payTicketController.setRootController(rootController);
    }

    public void buySub(ActionEvent actionEvent) {
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

        BuySubController buySubController = loader.getController();
        buySubController.setRootController(rootController);
    }

    public void setLocalePL(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("pl"));

        Stage currStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        loadRootLayout(currStage);


    }

    public void setLocaleEN(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("en"));

        Stage currStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        loadRootLayout(currStage);

    }



    public void loadRootLayout(Stage stage) {

        Parent root = FXMLUtils.fxmlLoad(ROOT_LAYOUT_FXML_PATH);
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }
}
