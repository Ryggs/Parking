package parking.bar.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import parking.bar.model.Ticket;
import parking.bar.model.TicketDAO;

import java.sql.SQLException;


public class BarExitController {

    private String code = "";   // max number is 10 digits long ticketNo + 2 ditigs controlCode


    @FXML
    private TextArea resultArea;

    @FXML
    private TextField codeArea;

    //OpenBar
    @FXML
    private void openBar(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        //TODO THIS FUNCTION

        setInfoToResultArea("Enter correct code");
        System.out.println("OpenBar");
        if (code.length() > 2) // 1 digit ticketNo + 2 digits controlCode
            try {
                // getCode().substring(0, getCode().length() - 2) ticketNo
                // getCode().substring(getCode().length() - 2, getCode().length()) controlCode

                boolean canOpenBar = TicketDAO.canTicketExit(Integer.parseInt(getCode().substring(0, getCode().length() - 2)),
                        Integer.parseInt(getCode().substring(getCode().length() - 2, getCode().length())));
                System.out.println("ticketNo" + Integer.parseInt(getCode().substring(0, getCode().length() - 2)));
                System.out.println("controlCode" + Integer.parseInt(getCode().substring(getCode().length() - 2,getCode().length())));

                if (canOpenBar) {
                    setInfoToResultArea("Bar is opened for 30 sec\nHave a nice day!");
                    setCode("");
                    showCode();
                } else {
                    setInfoToResultArea("Error occured\nYou didn't pay your ticket\nor code you have entered is incorrect");
                    setCode("");
                    showCode();
                }


            } catch (SQLException e) {
                System.out.println("Error occurred while checking ticket in DB.\n" + e);
                setInfoToResultArea("Error occured with our DataBase\nContact with administrator, please");
                //throw e;
            } catch(java.lang.NumberFormatException e){
                System.out.println("Error occurred while transforming code.\n" + e);
                setInfoToResultArea("Error: Your code is not correct\nEnter right code");
                setCode("");
                showCode();
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
        setCode("CE");
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
        boolean canSetNewCode = false;
        if(newCode.equals("CE"))
            this.code = "";
        else if (newCode.length() < 13) { //&& Integer.parseInt(getCode()) < 2147483647
            canSetNewCode = true;
            //TODO check if ticketNo is smaller than 2147483647
//            if(newCode.length() == 0)
//                canSetNewCode = true;
//            if(newCode.length() == 1) {
//                if (Integer.parseInt(newCode) < 3)
//                    canSetNewCode = true;
//            }
//            else if (newCode.length() == 2 || newCode.length() == 3)
//                canSetNewCode = true;
//            else {
//                int newTicektNo = Integer.parseInt(getCode().substring(0, getCode().length() - 2));
//
//                if (newCode.length() == 4 && newTicektNo <= 21)
//                    canSetNewCode = true;
//                else if (newCode.length() == 5 && newTicektNo <= 214)
//                    canSetNewCode = true;
//                else if (newCode.length() == 6 && newTicektNo <= 2147)
//                    canSetNewCode = true;
//                else if (newCode.length() == 7 && newTicektNo <= 21474)
//                    canSetNewCode = true;
//                else if (newCode.length() == 8 && newTicektNo <= 214748)
//                    canSetNewCode = true;
//                else if (newCode.length() == 9 && newTicektNo <= 2147483)
//                    canSetNewCode = true;
//                else if (newCode.length() == 10 && newTicektNo <= 21474836)
//                    canSetNewCode = true;
//                else if (newCode.length() == 11 && newTicektNo <= 214748364)
//                    canSetNewCode = true;
//                else if (newTicektNo <= 2147483647)
//                    canSetNewCode = true;
//
//            }
        }

        if(canSetNewCode)
            this.code = newCode;

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
