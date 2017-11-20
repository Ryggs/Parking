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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import parking.server.model.LoginDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;


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

    private ResourceBundle bundle;
    private FXMLLoader loader;

    private Stage loginStage;

    private AnchorPane loginLayout;

    public LoginPopupController() {
        // Init login layout
        loader = new FXMLLoader();
        bundle = ResourceBundle.getBundle("parking.server.bundles.messages");
        loader.setLocation(this.getClass().getResource("../view/LoginPopup.fxml"));
        loader.setResources(bundle);
    }

    @FXML
    public void login(ActionEvent event) throws SQLException {

        username = usernameTextField.getText();
        password = passwordField.getText();

          if(LoginDAO.login(username, password, "admin")){

            message.setTextFill(Color.rgb(0, 255, 0));
            message.setText(bundle.getString("login.password.correct"));

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
            message.setText(bundle.getString("login.password.incorrect"));
              System.out.println(loader.getResources());
            usernameTextField.clear();
            passwordField.clear();

        }
    }


    public void showLoginWindow(Stage loginStage) {
        try {
            this.loginStage = loginStage;
            loginStage.setTitle(bundle.getString("title.login"));
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
