package parking.server.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import parking.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogDAO {

    public static ObservableList<Log> getAllLog() throws SQLException {
        String stmt = "SELECT * FROM ticket";
        ResultSet rs = null;
        try {
            rs= DBUtil.dbExecuteQuery(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<Log> list = FXCollections.observableArrayList();

        while (rs.next()) {
            int ticketNo = rs.getInt(1);
            String entryTime = rs.getString(2);
            String leaveTime = rs.getString(3);
            String paymentTime = rs.getString(4);
            String paymentType = rs.getString(5);
            int charge = rs.getInt(6);
            int controlCode = rs.getInt(7);


            Log l = new Log(ticketNo,entryTime,leaveTime,paymentTime,paymentType,charge,controlCode);
            list.add(l);
        }
        return list;


    }

    public static void changeCharge(int ticketNo, int newCharge) {

        String stmt = "UPDATE ticket SET `Charge` = '" +newCharge+ "' WHERE `TicketNo` = " + ticketNo;

        try {
            DBUtil.dbExecuteUpdate(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void cancelCharge(String ticketNo) {
        String stmt = "UPDATE ticket SET `Charge` = 0, ControlCode = 10 , PaymentTime = '9999-12-31 23:00:00' WHERE `TicketNo` = " + ticketNo;

        try {
            DBUtil.dbExecuteUpdate(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
