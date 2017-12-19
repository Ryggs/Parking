package parking.parkingmeter.controller;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import parking.parkingmeter.model.PriceDAO;
import parking.parkingmeter.model.SubDAO;
import parking.parkingmeter.utils.FXMLUtils;
import parking.server.model.LoginDAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class BuySubLoginController {
    RootLayoutController rootController;
    private String username;
    private String password;
    private ResourceBundle bundle = FXMLUtils.getResourceBundle();
    private int valueToPay;
    private int valuePaid;
    private String type;
    
    @FXML
    private ChoiceBox<String> durationChoiceBox;

    @FXML
    private AnchorPane loggedPane;

    @FXML
    private Label valueToPayLabel;

    @FXML
    private Label valuePaidLabel;

    @FXML
    private Label infoLabel;
    

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label message;

    @FXML
    void durationChosen(ActionEvent event) {
        String selected = durationChoiceBox.getValue();
        try {
            valueToPay = PriceDAO.getPrice(selected);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        valueToPayLabel.setText(valueToPay/100.0+"");
        type = selected;

    }

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

            int flag = SubDAO.addSub(username, type);
            if (flag == 0) {
                infoLabel.setTextFill(Color.rgb(0, 255, 0));
                infoLabel.setText(bundle.getString("buy.ok"));
            }
        }


    }

    @FXML
    void initialize() {
//        valueToPay = new BigDecimal(0);
//        valuePaid = new BigDecimal(0);
    }

    @FXML
    void login(ActionEvent event) throws SQLException {
        username = usernameTextField.getText();
        password = passwordField.getText();


        if(LoginDAO.login(username, password, "user")){

            message.setTextFill(Color.rgb(0, 255, 0));
            message.setText(bundle.getString("login.password.correct"));
            message.setVisible(true);
            loggedPane.setVisible(true);
            usernameTextField.setEditable(false);
            passwordField.setEditable(false);

            initChoiceBox();

            valueToPay = PriceDAO.getPrice(durationChoiceBox.getValue());

            durationChoiceBox.getSelectionModel().getSelectedItem();

        } else {

            message.setTextFill(Color.rgb(255, 0, 0));
            message.setText(bundle.getString("login.password.incorrect"));
            message.setVisible(true);
            loggedPane.setVisible(false);
            usernameTextField.clear();
            passwordField.clear();

        }
    }

    private void initChoiceBox() {
        ObservableList<String> listOfSubTypes = null;
        try {
            listOfSubTypes = PriceDAO.getSubTypes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(listOfSubTypes);
        durationChoiceBox.setItems(listOfSubTypes);

    }


    public void backToMenu(ActionEvent actionEvent) {
        Stage currStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        rootController.loadRootLayout(currStage);
    }

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }
}
