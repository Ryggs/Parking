package parking.server.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
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

    @FXML
    private ChoiceBox<String> permChoiceBox;


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


        // Make columns editable
        //idColumn.setCellFactory(TextFieldTableCell.<Admin, Integer>forTableColumn(new IntegerStringConverter()));
        loginColumn.setCellFactory(TextFieldTableCell.<Admin>forTableColumn());
        passColumn.setCellFactory(TextFieldTableCell.<Admin>forTableColumn());
        permissionColumn.setCellFactory(TextFieldTableCell.<Admin>forTableColumn());
        firstNameColumn.setCellFactory(TextFieldTableCell.<Admin>forTableColumn());
        secondNameColumn.setCellFactory(TextFieldTableCell.<Admin>forTableColumn());
        phoneColumn.setCellFactory(TextFieldTableCell.<Admin,Integer>forTableColumn(new IntegerStringConverter()));
        emailColumn.setCellFactory(TextFieldTableCell.<Admin>forTableColumn());

        ObservableList<String> perms = FXCollections.observableArrayList("admin","user");
        permChoiceBox.setItems(perms);

        idColumn.setEditable(true);


//        try {
//            personData = AdminDAO.getAllUsers();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            refreshTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void refreshTableView() throws SQLException {

           userTableView.setItems(AdminDAO.getAllUsers());


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
    String perm = permChoiceBox.getValue();
    String firstName = firstNameTextField.getText();
    String secondName = lastNameTextField.getText();
    Integer phone = Integer.parseInt(phoneTextField.getText());
    String email = emailTextField.getText();



    AdminDAO.addNewUser(login,pass,perm,firstName,secondName,phone,email);


        refreshTableView();
    }
//firstname
    public void editColumnItem(TableColumn.CellEditEvent<Admin, String> e) throws SQLException {


        String colId = e.getTableColumn().getId();
        String colName = convertColNames(colId);
        System.out.println(colId);
        System.out.println(colName);



        // UserID of account to be edited
        int index = (e.getTableView().getItems().get(e.getTablePosition().getRow()).getUserNo());
//        System.out.println(index);

        Object newValue = e.getNewValue();
//        System.out.println(newValue.toString());  // e.getNewValue().toString() nie działą

         AdminDAO.update(index, colName, newValue);
    }

    /**
     * Method converts TableView columns names to corresponding in Database
     * @param colId - column name in TableView
     *
     * @return - column name in Database
     */
    private String convertColNames(String colId) {
        String colName = "";
        switch (colId){
            case "loginColumn":
                    colName = "UserLogin";
                    break;
            case "passColumn":
                    colName = "UserPass";
                    break;
            case "permissionColumn":
                    colName = "PermType";
                    break;
            case "firstNameColumn":
                    colName = "Name";
                    break;
            case "secondNameColumn":
                    colName = "Surname";
                    break;
            case "phoneColumn":
                    colName = "Phone";
                    break;
            case "emailColumn":
                    colName = "Email";
                    break;

        }
        return colName;
    }


}
