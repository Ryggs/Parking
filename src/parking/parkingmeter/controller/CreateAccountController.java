package parking.parkingmeter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import parking.parkingmeter.model.UserDAO;
import parking.parkingmeter.utils.FXMLUtils;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateAccountController {

    RootLayoutController rootController;
    ResourceBundle bundle = FXMLUtils.getResourceBundle();
    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label infoLabel;

    @FXML
    void backToMenu(ActionEvent event) {
            Stage currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            rootController.loadRootLayout(currStage);

    }

    @FXML
    void createAcc(ActionEvent event) {
        String login = loginTextField.getText();
        String pass = passTextField.getText();
        String firstName = firstNameTextField.getText();
        String secondName = lastNameTextField.getText();
        Integer phone = Integer.parseInt(phoneTextField.getText());
        String email = emailTextField.getText();


        try {
            if((UserDAO.addNewUser(login,pass,"user",firstName,secondName,phone,email)) == true){
                infoLabel.setTextFill(Color.rgb(0, 255, 0));
                infoLabel.setText(bundle.getString("create.ok"));
                infoLabel.setVisible(true);
            }
            else {
                infoLabel.setTextFill(Color.rgb(255, 0, 0));
                infoLabel.setText(bundle.getString("create.err"));
                infoLabel.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }
}
