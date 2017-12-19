package parking.parkingmeter.controller;


import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class PayTicketController {
    RootLayoutController rootController;


    public void backToMenu(ActionEvent actionEvent) {

        Stage currStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        rootController.loadRootLayout(currStage);
    }

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }
}
