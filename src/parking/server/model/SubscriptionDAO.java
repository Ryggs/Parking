package parking.server.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import parking.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionDAO {
    public static ObservableList<Subscription> getAllSubs() throws SQLException {
        String stmt = "SELECT UserNo, SubNo, StartTime, EndTime, PurchaseTime, Type, Price FROM `user_sub` NATURAL JOIN `subscription`";
        ResultSet rs = null;
        try {
            rs= DBUtil.dbExecuteQuery(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<Subscription> list = FXCollections.observableArrayList();

        while (rs.next()) {
            int userNo = rs.getInt(1);
            int subNo = rs.getInt(2);
            String startTime = rs.getString(3);
            String endTime = rs.getString(4);
            String buyTime = rs.getString(5);
            String type = rs.getString(6);
            int price = rs.getInt(7);


            Subscription s = new Subscription(userNo, subNo, startTime, endTime, buyTime, type, price);
            list.add(s);
        }
        return list;


    }

}
