package parking.parkingmeter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import parking.parkingmeter.model.Sub;
import parking.parkingmeter.model.SubDAO;
import parking.parkingmeter.utils.FXMLUtils;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class BuySubDetailsController {
    private String username;
    private Sub sub;
    private ResourceBundle bundle = FXMLUtils.getResourceBundle();


    @FXML
    private TextArea resultTextArea;

    @FXML
    void close(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }




    public void fillTextField() {
        try {
            sub = SubDAO.getLatestSub(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // TODO: Ladniejsze wyswietlanie / drukowanie
        resultTextArea.appendText(bundle.getString("txt.username") + "\t\t\t\t\t" + username +"\n");
        resultTextArea.appendText(bundle.getString("txt.name") + "\t\t\t\t" +sub.getName() +"\n");
        resultTextArea.appendText(bundle.getString("txt.surname") + "\t\t\t\t" + sub.getSurname() +"\n");
        resultTextArea.appendText(bundle.getString("txt.start") + "\t\t\t\t" +sub.getStartTime() +"\n");
        resultTextArea.appendText(bundle.getString("txt.end") + "\t\t\t\t\t" +sub.getEndTime() +"\n");

    }


    public void setUser(String username) {
        this.username = username;
        System.out.println(this.username);
    }


}
