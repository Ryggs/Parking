package parking.server.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import parking.server.Main;
import parking.server.model.LoginDAO;

import java.io.IOException;
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


    private Stage loginStage;

    private AnchorPane loginLayout;



    @FXML
    public void login(ActionEvent event) throws SQLException {

        username = usernameTextField.getText();
        password = passwordField.getText();

          if(LoginDAO.login(username, password, "admin")){

            message.setTextFill(Color.rgb(0, 255, 0));
            message.setText("Password correct!");

              // Start  application, if logged
              RootLayoutController appInstance = new RootLayoutController();


              try {
                  appInstance.startApp();
              } catch (IOException e) {
                  System.err.println("Error launching app");
                  e.printStackTrace();
              }
              // Close login window
              ((Node)(event.getSource())).getScene().getWindow().hide();


          } else {

            message.setTextFill(Color.rgb(255, 0, 0));
            message.setText("Password incorrect!");
            usernameTextField.clear();
            passwordField.clear();

        }
    }

    public void showLoginWindow(Stage loginStage) {
        try {
            this.loginStage = loginStage;
            loginStage.setTitle("Parking Server Login");

            // Init login layout
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("../view/LoginPopup.fxml"));
            loginLayout = loader.load();

            // Show the scene
            Scene scene = new Scene(loginLayout);
            loginStage.setScene(scene);
            loginStage.show();

        } catch (IOException e) {
            System.err.println("Error while showing login window");
            e.printStackTrace();
        }
    }


}
