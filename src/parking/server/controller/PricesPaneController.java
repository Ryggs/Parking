package parking.server.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import parking.server.model.LoginDAO;
import parking.server.model.Price;
import parking.server.model.PriceDAO;

import java.sql.SQLException;

public class PricesPaneController {
    private RootLayoutController rootController;

    @FXML
    private TableView<Price> pricesTableView;

    @FXML
    private TableColumn<Price, Integer> idColumn;

    @FXML
    private TableColumn<Price, String> typeColumn;

    @FXML
    private TableColumn<Price, Integer> priceColumn;



    @FXML
    void priceEdit(TableColumn.CellEditEvent<Price, Integer> e) throws SQLException {
        int index = (e.getTableView().getItems().get(e.getTablePosition().getRow()).getId());
        Integer newValue = e.getNewValue();
        PriceDAO.update(index,"Price",newValue);
    }


    public void initialize() {
        idColumn.setCellValueFactory( cellData -> cellData.getValue().idProperty().asObject());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().priceTypeProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().pricePriceProperty().asObject());

        // make editable
        priceColumn.setCellFactory(TextFieldTableCell.<Price,Integer>forTableColumn(new IntegerStringConverter()));
        priceColumn.setEditable(true);
        try {
            refreshTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshTableView() throws SQLException {

        pricesTableView.setItems(PriceDAO.getAllPrices());


    }


    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }

    public void backToMenu(){
        rootController.loadMainMenuScreen();
    }
}
