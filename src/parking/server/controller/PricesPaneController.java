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
    private TableColumn<Price, String> nameColumn;

    @FXML
    private TableColumn<Price, String> typeColumn;

    @FXML
    private TableColumn<Price, Integer> priceColumn;

    @FXML
    private TableColumn<Price, Integer> durationColumn;



    @FXML
    void editColumnItem(TableColumn.CellEditEvent<Price, Integer> e) throws SQLException {
        int index = (e.getTableView().getItems().get(e.getTablePosition().getRow()).getId());
        String colId = e.getTableColumn().getId();
        String colName = convertColNames(colId);


        Object newValue = e.getNewValue();

        PriceDAO.update(index,colName,newValue.toString());
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
            case "nameColumn":
                colName = "Name";
                break;
            case "typeColumn":
                colName = "Type";
                break;
            case "priceColumn":
                colName = "Price";
                break;
            case "durationColumn":
                colName = "Duration";
                break;
        }
        return colName;
    }


    public void initialize() {
        idColumn.setCellValueFactory( cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().priceNameProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().priceTypeProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().pricePriceProperty().asObject());
        durationColumn.setCellValueFactory(cellData -> cellData.getValue().priceDurationProperty().asObject());


        // make editable
        nameColumn.setCellFactory(TextFieldTableCell.<Price>forTableColumn());
        typeColumn.setCellFactory(TextFieldTableCell.<Price>forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.<Price,Integer>forTableColumn(new IntegerStringConverter()));
        durationColumn.setCellFactory(TextFieldTableCell.<Price,Integer>forTableColumn(new IntegerStringConverter()));
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
