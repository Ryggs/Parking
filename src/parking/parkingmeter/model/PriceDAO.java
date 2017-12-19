package parking.parkingmeter.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import parking.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PriceDAO {

    public static ObservableList<String> getSubTypes() throws SQLException {
        String stmt = "SELECT `Name` FROM `prices` WHERE Type='subscription';";
        ResultSet rs = null;
        try {
            rs= DBUtil.dbExecuteQuery(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<String> list = FXCollections.observableArrayList();

        while (rs.next()) {

            String priceType = rs.getString(1);

            list.add(priceType);
        }
        return list;


    }


    public static int getPrice(String value) throws SQLException {

        String stmt = "SELECT `Price` FROM `prices` WHERE Name='" + value + "';";
        ResultSet rs = null;
        try {
            rs= DBUtil.dbExecuteQuery(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int price = -1;

        if (rs.next()) {

            price = rs.getInt(1);


        }
        return price;


    }
}
