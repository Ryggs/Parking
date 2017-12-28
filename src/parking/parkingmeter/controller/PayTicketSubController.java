package parking.parkingmeter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import parking.parkingmeter.model.TicketDAO;
import parking.parkingmeter.model.TicketPay;
import parking.parkingmeter.utils.FXMLUtils;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class PayTicketSubController {
    private RootLayoutController rootController;
    ResourceBundle bundle = FXMLUtils.getResourceBundle();
    private String ticketNo;
    private String subNo;
    private TicketPay ticketPay;

    @FXML
    private TextField ticketNoTextField;

    @FXML
    private TextField subNoTextField;

    @FXML
    private Label message;

    @FXML
    private TextField detailsTextField;

    @FXML
    void backToMenu(ActionEvent event) {
        Stage currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        rootController.loadRootLayout(currStage);
    }

    @FXML
    void confirm(ActionEvent event) throws SQLException {
        ticketNo = ticketNoTextField.getText();
        subNo = subNoTextField.getText();

        ticketPay = TicketDAO.payTicketSub(ticketNo, subNo);

        if(ticketPay.getControlCode() == -1) {
            message.setTextFill(Color.rgb(255, 0, 0));
            message.setText(bundle.getString("sub.err"));
            detailsTextField.setVisible(false);
        } else {
            message.setTextFill(Color.rgb(0, 255, 0));
            message.setText(bundle.getString("pay.ok"));

            detailsTextField.setVisible(true);
            // TODO: Wyświetlanie jakoś szczegółów płatności i drukowanie potwierdzenia

        }
    }

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }
}
