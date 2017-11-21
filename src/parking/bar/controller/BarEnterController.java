package parking.bar.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import parking.bar.model.Ticket;
import parking.bar.model.TicketDAO;

import java.sql.SQLException;




public class BarEnterController {

    @FXML
    private TextArea resultArea;

    //Set information to Text Area
    @FXML
    private void setInfoToTextArea (String infoText) {
        resultArea.setText(infoText);
    }

    //get new ticket from DataBase
    @FXML
    private void getTicket(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        System.out.println("Pobieram nowy bilet");
        try {
            Ticket newTicket = TicketDAO.getNewTicket();
            String newInfo = "Twój nr biletu to " + newTicket.getTicketNo() + "\nMasz 30sek na wjazd";
            String newInfo2 = "\nCzas wjazdu " + newTicket.getEntryTime().toString().substring(0,19);

            System.out.println(newInfo);
            System.out.println(newInfo2);
            setInfoToTextArea(newInfo + newInfo2);

        } catch (SQLException e){
            System.out.println("Error occurred while getting new ticket from DB.\n" + e);
            throw e;
        }
    }


}
