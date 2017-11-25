package parking.server.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import parking.server.model.Log;
import parking.server.model.LogDAO;

import java.sql.SQLException;

public class LogPaneController {
    private RootLayoutController rootController;
    @FXML
    private TableView<Log> logTableView;

    @FXML
    private TableColumn<Log, Integer> ticketNoColumn;

    @FXML
    private TableColumn<Log, String> entryTimeColumn;

    @FXML
    private TableColumn<Log, String> leaveTimeColumn;

    @FXML
    private TableColumn<Log, String> paymentTimeColumn;

    @FXML
    private TableColumn<Log, String> paymentTypeColumn;

    @FXML
    private TableColumn<Log, Double> chargeColumn;

    @FXML
    private TableColumn<Log, Integer> controlCodeColumn;

    public void initialize() {
        ticketNoColumn.setCellValueFactory( cellData -> cellData.getValue().ticketNoProperty().asObject());
        entryTimeColumn.setCellValueFactory(cellData -> cellData.getValue().entryTimeProperty());
        leaveTimeColumn.setCellValueFactory(cellData -> cellData.getValue().leaveTimeProperty());
        paymentTimeColumn.setCellValueFactory(cellData -> cellData.getValue().paymentTimeProperty());
        paymentTypeColumn.setCellValueFactory(cellData -> cellData.getValue().paymentTypeProperty());
        chargeColumn.setCellValueFactory(cellData -> cellData.getValue().chargeProperty().asObject());
        controlCodeColumn.setCellValueFactory(cellData -> cellData.getValue().controlCodeProperty().asObject());


        // Make columns editable

//        ticketNoColumn.setCellFactory(TextFieldTableCell.<Log, Integer>forTableColumn(new IntegerStringConverter()));
//        entryTimeColumn.setCellFactory(TextFieldTableCell.<Log>forTableColumn());
//        leaveTimeColumn.setCellFactory(TextFieldTableCell.<Log>forTableColumn());
//        paymentTimeColumn.setCellFactory(TextFieldTableCell.<Log>forTableColumn());
//        paymentTypeColumn.setCellFactory(TextFieldTableCell.<Log>forTableColumn());
//        chargeColumn.setCellFactory(TextFieldTableCell.<Log, Double>forTableColumn(new DoubleStringConverter()));
//        controlCodeColumn.setCellFactory(TextFieldTableCell.<Log, Integer>forTableColumn(new IntegerStringConverter()));
        try {
            refreshTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshTableView() throws SQLException {

        logTableView.setItems(LogDAO.getAllLog());


    }


    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }


    public void backToMenu(){
        System.out.println("root w log: " + rootController.toString());
        rootController.loadMainMenuScreen();
    }


}
