package parking.bar.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import parking.bar.model.Ticket;
import parking.bar.model.TicketDAO;

import java.sql.SQLException;



public class BarExitController {

    private String code = "";   // max number is 11digit long


    @FXML
    private TextArea resultArea;

    @FXML
    private TextField codeArea;

    //OpenBar
    @FXML
    private void openBar(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        //TODO THIS FUNCTION

        setInfoToResultArea("OpenBar");
        System.out.println("OpenBar");

    }
    //KeyC erase last digit
    @FXML
    private void numC(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(getCode().length() > 0)
            setCode(getCode().substring(0, getCode().length() - 1));
        showCode();
    }
    //KeyCE clear everything
    @FXML
    private void numCE(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setCode("");
        showCode();
    }
    //Key0
    @FXML
    private void num0(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(getCode().length() > 0)      //Don't start code from 0
            setCode(getCode() + "0");
        showCode();
    }
    //Key1
    @FXML
    private void num1(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setCode(getCode() + "1");
        showCode();
    }
    //Key2
    @FXML
    private void num2(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setCode(getCode() + "2");
        showCode();
    }
    //Key3
    @FXML
    private void num3(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setCode(getCode() + "3");
        showCode();
    }
    //Key4
    @FXML
    private void num4(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setCode(getCode() + "4");
        showCode();
    }
    //Key5
    @FXML
    private void num5(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setCode(getCode() + "5");
        showCode();
    }
    //Key6
    @FXML
    private void num6(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setCode(getCode() + "6");
        showCode();
    }
    //Key7
    @FXML
    private void num7(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setCode(getCode() + "7");
        showCode();
    }
    //Key8
    @FXML
    private void num8(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setCode(getCode() + "8");
        showCode();
    }
    //Key9
    @FXML
    private void num9(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setCode(getCode() + "9");
        showCode();
    }
    void setCode(String newCode){
        if(newCode.length() < 12)
            this.code = newCode;
    }
    String getCode(){
        return this.code;
    }

    //Set information to resultArea
    @FXML
    private void setInfoToResultArea (String infoText) {
        resultArea.setText(infoText);
    }

    //Set information to codeArea
    @FXML
    private void setInfoToCodeArea (String infoText) {
        codeArea.setText(infoText);
    }
    //Show Code in codeArea
    @FXML
    private void showCode() {
        setInfoToCodeArea(getCode());
    }
}
