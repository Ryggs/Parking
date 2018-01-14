package parking.bar.controller;
import parking.bar.model.Bar;
import parking.bar.model.TicketDAO;
//javaFX
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
//sql
import java.sql.SQLException;


public class BarExitController {

    private String code = "";   // max number is 10 digits long ticketNo + 2 ditigs controlCode
    Bar barChecker;

    @FXML
    private TextArea resultArea;

    @FXML
    private TextField codeArea;

    //OpenBar
    @FXML
    private void openBar(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setInfoToResultArea("Wait until the bar is closed");
        if(Bar.isBarClosed) {
            setInfoToResultArea("Enter correct code");
            if (code.length() > 2) // 1 digit ticketNo + 2 digits controlCode
                try {
                    // getCode().substring(0, getCode().length() - 2) ticketNo
                    // getCode().substring(getCode().length() - 2, getCode().length()) controlCode

                    boolean canOpenBar = TicketDAO.canTicketExit(Integer.parseInt(getCode().substring(0, getCode().length() - 2)),
                            Integer.parseInt(getCode().substring(getCode().length() - 2, getCode().length())));
                    System.out.println("ticketNo: " + Integer.parseInt(getCode().substring(0, getCode().length() - 2)));
                    System.out.println("controlCode: " + Integer.parseInt(getCode().substring(getCode().length() - 2, getCode().length())));

                    if (canOpenBar) {
                        setInfoToResultArea("Bar is opened for 60 sec\nHave a nice day!");
                        resultArea.appendText("\nSzlaban jest otwarty na 60sek.\nMiłego dnia!");
                        //open Bar
                        Bar.openBar();

                        //run thread to check for bar status
                        barChecker = new Bar();
                        barChecker.start();

                        setCode("");
                        showCode();
                    } else {
                        setInfoToResultArea("Error occured\nYou didn't pay your ticket\nor code you have entered is incorrect");
                        resultArea.appendText("\nNie zapłaciłeś biletu\nlub kod kontrolny jest niepoprawny");
                        setCode("");
                        showCode();
                    }

                } catch (SQLException e) {
                    System.out.println("Error occurred while checking ticket in DB.\n" + e);
                    setInfoToResultArea("Error occured with our DataBase\nContact with administrator, please");
                    resultArea.appendText("\nBłąd przy łączeniu z serwerem\nProszę skontakotwać się z administratorem");
                    //throw e;
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("Error occurred while transforming code.\n" + e);
                    setInfoToResultArea("Error: Your code is not correct\nEnter right code");
                    resultArea.appendText("\nNiepoprawny kod\nwprowadź prawidłowy kod");
                    setCode("");
                    //showCode();
                }
        }
    }

    //KeyC erase last digit
    @FXML
    private void numC(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (getCode().length() > 0)
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
        if (getCode().length() > 0)      //Don't start code from 0
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

    void setCode(String newCode) {
        // check if ticketNo is smaller than 2147483647 DONE WITH USE OF TRY/CATCH java.lang.NumberFormatException
        // check if code is no longer than 12 digits (10 digits ticketNo + 2 digits controlCode)
        if (newCode.length() < 13) { // Integer.parseInt(getCode()) < 2147483647
            this.code = newCode;
        }
    }

    String getCode() {
        return this.code;
    }

    //Set information to resultArea
    @FXML
    private void setInfoToResultArea(String infoText) {
        resultArea.setText(infoText);
    }

    //Set information to codeArea
    @FXML
    private void setInfoToCodeArea(String infoText) {
        codeArea.setText(infoText);
    }

    //Show Code in codeArea
    @FXML
    private void showCode() {
        setInfoToCodeArea(getCode());
    }
}
