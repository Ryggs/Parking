package parking.parkingmeter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import parking.parkingmeter.model.TicketCharge;
import parking.parkingmeter.model.TicketDAO;
import parking.parkingmeter.model.TicketPay;
import parking.parkingmeter.utils.FXMLUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PayTicketCashController {
    private int valueToPay;
    private int valuePaid;
    private RootLayoutController rootController;

    private ResourceBundle bundle = FXMLUtils.getResourceBundle();
    private TicketCharge ticketCharge;

    private final String PAY_CASH_DETAILS_PANE =  "../view/PayTicketCashDetails.fxml";
    @FXML
    private AnchorPane loggedPane;

    @FXML
    private Label valueToPayLabel;

    @FXML
    private Label valuePaidLabel;

    @FXML
    private GridPane money;

    @FXML
    private Label infoLabel;

    @FXML
    private TextField ticketNoTextField;

    @FXML
    private Label message;


    @FXML
    void abort(ActionEvent event) {
        valuePaid = 0;
        valuePaidLabel.setText(valuePaid/100.0+"");
    }

    @FXML
    void add10gr(ActionEvent event) {
        valuePaid += 10;
        valuePaidLabel.setText(valuePaid/100.0+"");
    }

    @FXML
    void add1gr(ActionEvent event) {
        valuePaid += 1;
        valuePaidLabel.setText(valuePaid/100.0+"");
    }

    @FXML
    void add1zl(ActionEvent event) {
        valuePaid += 100;
        valuePaidLabel.setText(valuePaid/100.0+"");
    }

    @FXML
    void add20gr(ActionEvent event) {
        valuePaid += 20;
        valuePaidLabel.setText(valuePaid/100.0+"");
    }

    @FXML
    void add2gr(ActionEvent event) {
        valuePaid += 2;
        valuePaidLabel.setText(valuePaid/100.0+"");
    }

    @FXML
    void add2zl(ActionEvent event) {
        valuePaid += 200;
        valuePaidLabel.setText(valuePaid/100.0+"");
    }

    @FXML
    void add50gr(ActionEvent event) {
        valuePaid += 50;
        valuePaidLabel.setText(valuePaid/100.0+"");
    }

    @FXML
    void add5gr(ActionEvent event) {
        valuePaid += 5;
        valuePaidLabel.setText(valuePaid/100.0+"");
    }

    @FXML
    void add5zl(ActionEvent event) {
        valuePaid += 500;
        valuePaidLabel.setText(valuePaid/100.0+"");
    }


    @FXML
    void confirm(ActionEvent event) throws SQLException {
        if(valuePaid < valueToPay) {
            infoLabel.setText(bundle.getString("buy.insertMoreMoney"));
        }
        else {
            if (valuePaid > valueToPay) {
                int change = valuePaid - valueToPay;
                infoLabel.setText(bundle.getString("buy.returnMoney") + change/100.0);
            }

            String ticketNo = ticketNoTextField.getText();
            TicketPay ticketPay = TicketDAO.payTicketCash(ticketNo);

            if(ticketPay.getControlCode() == -1) {
                infoLabel.setTextFill(Color.rgb(255, 0, 0));
                infoLabel.setText(bundle.getString("pay.err"));
            } else {
                infoLabel.setTextFill(Color.rgb(0, 255, 0));
                infoLabel.setText(bundle.getString("pay.ok"));
            }

            FXMLLoader loader = FXMLUtils.getLoader(PAY_CASH_DETAILS_PANE);

            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            backToMenu(event);



            PayTicketCashDetailsController controller = loader.getController();
            controller.setTicketNo(ticketNo);
            controller.fillTextField();


            Scene scene = new Scene(root);
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.show();
        }
    }

    @FXML
    void confirmTicketNo(ActionEvent event) throws SQLException {
        String ticketNo = ticketNoTextField.getText();
        ticketCharge = TicketDAO.getTicketCharge(ticketNo);
        valueToPayLabel.setText("0");

        if (ticketCharge.getCharge() == -1) {
            message.setText(bundle.getString("ticket.wrongNumber"));
            message.setTextFill(Color.rgb(255, 0, 0));
            message.setVisible(true);

        } else {
            valueToPay = ticketCharge.getCharge();
            valueToPayLabel.setText(valueToPay / 100.0 + "");
            message.setVisible(false);
        }
    }

    public void backToMenu(ActionEvent actionEvent) {
        Stage currStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        rootController.loadRootLayout(currStage);
    }
    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }
}
