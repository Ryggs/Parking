package parking.server.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;


public class LoginPopupController {
    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordField;

    @FXML
    Button loginButton;

    @FXML
    Label message;

    @FXML
    public void login() {
        if( usernameTextField.getText().equals("admin") && passwordField.getText().equals("admin") ){
     //       message.setVisible(true);
            message.setTextFill(Color.rgb(0, 255, 0));
            message.setText("Password correct!");

            // TODO: Check password with Database
            // TODO: Start  application

        } else {
     //       message.setVisible(true);
            message.setTextFill(Color.rgb(255, 0, 0));
            message.setText("Password incorrect!");
            usernameTextField.clear();
            passwordField.clear();
        }
    }
}
