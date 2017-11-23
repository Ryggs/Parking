package parking.server.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import parking.server.model.Admin;
import parking.server.model.AdminDAO;


import java.sql.SQLException;

public class AdminsPaneController {
    private RootLayoutController rootController;




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
    private Button addButton;

    @FXML
    private TableView<Admin> userTableView;

    @FXML
    private TableColumn<Admin, Integer> idColumn;

    @FXML
    private TableColumn<Admin, String> loginColumn;

    @FXML
    private TableColumn<Admin, String> passColumn;

    @FXML
    private TableColumn<Admin, String> permissionColumn;

    @FXML
    private TableColumn<Admin, String> firstNameColumn;

    @FXML
    private TableColumn<Admin, String> secondNameColumn;

    @FXML
    private TableColumn<Admin, Integer> phoneColumn;

    @FXML
    private TableColumn<Admin, String> emailColumn;

    private Admin admin;

    private ObservableList<Admin> personData = FXCollections.observableArrayList();

    public void initialize() {

        idColumn.setCellValueFactory( cellData -> cellData.getValue().userNoProperty().asObject());
        loginColumn.setCellValueFactory(cellData -> cellData.getValue().userLoginProperty());
        passColumn.setCellValueFactory(cellData -> cellData.getValue().userPassProperty());
        permissionColumn.setCellValueFactory(cellData -> cellData.getValue().permTypeProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        secondNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        phoneColumn.setCellValueFactory( cellData -> cellData.getValue().phoneProperty().asObject());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        refreshTableView();
    }

    public void refreshTableView(){
        try {
            userTableView.setItems(AdminDAO.getAllUsers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }

    public void backToMenu(){
        rootController.loadMainMenuScreen();
    }

    public void addNewAdmin(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
    String login = loginTextField.getText();
    String pass = passTextField.getText();
    String firstName = firstNameTextField.getText();
    String secondName = lastNameTextField.getText();
    Integer phone = Integer.parseInt(phoneTextField.getText());
    String email = emailTextField.getText();

    AdminDAO.addNewUser(login,pass,"admin",firstName,secondName,phone,email);
        refreshTableView();
    }
}
