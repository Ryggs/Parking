package parking.parkingmeter.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parking.parkingmeter.Main;
import parking.parkingmeter.utils.FXMLUtils;

import java.util.Locale;

//
public class RootLayoutController {
    private final String ROOT_LAYOUT_FXML_PATH = "../view/RootLayout.fxml";

    public void buyTicket(ActionEvent actionEvent) {
    }

    public void buySub(ActionEvent actionEvent) {
    }

    public void setLocalePL(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("pl"));
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();

        startApp();


    }

    public void setLocaleEN(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("en"));


        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        startApp();

    }

    private void startApp() {
        Stage stage = new Stage();
        Parent root = FXMLUtils.fxmlLoad(ROOT_LAYOUT_FXML_PATH);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
