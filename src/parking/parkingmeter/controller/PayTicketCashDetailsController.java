package parking.parkingmeter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import parking.parkingmeter.model.Sub;
import parking.parkingmeter.model.SubDAO;
import parking.parkingmeter.model.Ticket;
import parking.parkingmeter.model.TicketDAO;
import parking.parkingmeter.utils.FXMLUtils;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class PayTicketCashDetailsController {
    private String ticketNo;
    Ticket ticket;
    private ResourceBundle bundle = FXMLUtils.getResourceBundle();


    @FXML
    private TextArea resultTextArea;

    @FXML
    void close(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }




    public void fillTextField() {
        try {
            ticket = TicketDAO.getPaidTicket(ticketNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // TODO: Ladniejsze wyswietlanie / drukowanie
        resultTextArea.appendText(bundle.getString("txt.ticketNo") + "\t\t\t\t" + ticketNo +"\n");
        resultTextArea.appendText(bundle.getString("txt.entryTime") + "\t\t\t\t" +ticket.getEntryTime() +"\n");
        resultTextArea.appendText(bundle.getString("txt.paymentTime") + "\t\t\t\t" + ticket.getPaymentTime() +"\n");
        resultTextArea.appendText(bundle.getString("txt.paymentType") + "\t\t\t\t" + ticket.getPaymentType() +"\n");
        resultTextArea.appendText(bundle.getString("txt.charge") + "\t\t\t\t\t" +ticket.getCharge()/100.0 +"\n");
        resultTextArea.appendText(bundle.getString("txt.controlCode") + "\t\t\t\t" +ticket.getControlCode() +"\n");

    }


    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }
}
