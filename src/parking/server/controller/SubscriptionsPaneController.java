package parking.server.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import parking.server.model.Subscription;
import parking.server.model.SubscriptionDAO;

import java.sql.SQLException;

public class SubscriptionsPaneController {

    private RootLayoutController rootController;

    @FXML
    private TableView<Subscription> subTableView;

    @FXML
    private TableColumn<Subscription, Integer> userNoColumn;

    @FXML
    private TableColumn<Subscription, Integer> subNoColumn;

    @FXML
    private TableColumn<Subscription, String> startTimeColumn;

    @FXML
    private TableColumn<Subscription, String> endTimeColumn;

    @FXML
    private TableColumn<Subscription, String> purchaseTimeColumn;

    @FXML
    private TableColumn<Subscription, String> typeColumn;

    @FXML
    private TableColumn<Subscription, Integer> priceColumn;

    @FXML
    private TextField subNoTextField;



    public void initialize() {
        userNoColumn.setCellValueFactory( cellData -> cellData.getValue().userNoProperty().asObject());
        subNoColumn.setCellValueFactory(cellData -> cellData.getValue().subNoProperty().asObject());
        startTimeColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
        endTimeColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());
        purchaseTimeColumn.setCellValueFactory(cellData -> cellData.getValue().buyTimeProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        try {
            refreshTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshTableView() throws SQLException {

            subTableView.setItems(SubscriptionDAO.getAllSubs());

    }

    @FXML
    void giveUnlimited(ActionEvent event) {
        String subNo = subNoTextField.getText();
        SubscriptionDAO.giveUserUnlimitedSubscription(subNo);
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
        rootController.loadMainMenuScreen();
    }
}
