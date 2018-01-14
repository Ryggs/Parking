package parking.server.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
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
    private TableColumn<Log, Integer> chargeColumn;

    @FXML
    private TableColumn<Log, Integer> controlCodeColumn;

    @FXML
    private TextField ticketNoTextField;

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
        chargeColumn.setCellFactory(TextFieldTableCell.<Log, Integer>forTableColumn(new IntegerStringConverter()));
//        controlCodeColumn.setCellFactory(TextFieldTableCell.<Log, Integer>forTableColumn(new IntegerStringConverter()));
        logTableView.setEditable(true);

        try {
            refreshTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void changeCharge(TableColumn.CellEditEvent<Log, String> e) {

        int ticketNo = (e.getTableView().getItems().get(e.getTablePosition().getRow()).getTicketNo());

        Object newValue = e.getNewValue();
//        System.out.println(newValue.toString());  // e.getNewValue().toString() nie działą

        LogDAO.changeCharge(ticketNo,(int)newValue);
    }

    public void refreshTableView() throws SQLException {

        logTableView.setItems(LogDAO.getAllLog());


    }

    @FXML
    void cancelCharge(ActionEvent event) {
        String ticketNo = ticketNoTextField.getText();
        LogDAO.cancelCharge(ticketNo);
        try {
            refreshTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }


    public void backToMenu(){
        System.out.println("root w log: " + rootController.toString());
        rootController.loadMainMenuScreen();
    }


}
