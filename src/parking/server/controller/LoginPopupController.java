package parking.server.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import parking.server.model.LoginDAO;

import java.sql.SQLException;


public class LoginPopupController {
    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordField;

    @FXML
    Button loginButton;

    @FXML
    Label message;

    private String username;
    private String password;

    @FXML
    public void login() throws SQLException {

        username = usernameTextField.getText();
        password = passwordField.getText();

          if(LoginDAO.login(username, password)){

            message.setTextFill(Color.rgb(0, 255, 0));
            message.setText("Password correct!");

            // TODO: Start  application

        } else {

            message.setTextFill(Color.rgb(255, 0, 0));
            message.setText("Password incorrect!");
            usernameTextField.clear();
            passwordField.clear();
        }
    }
}
