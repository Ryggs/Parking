package parking.parkingmeter.model;

import parking.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubDAO {

    public static int addSub(String username, String duration) throws SQLException {
        String stmt = String.format("CALL buy_sub('%s', '%s', NOW())",username,duration);
        System.out.println(stmt);
        ResultSet rs = null;
        int flag = -1;

        try {
            rs = DBUtil.dbExecuteQuery(stmt);
        } catch (SQLException e) {
            System.err.println("Error while executing login query");
            e.printStackTrace();
        }

        if (rs.next()) {
            System.err.println(rs.getString(1));
            System.err.println(flag = rs.getInt(2));
            System.err.println(rs.getString(3));
            System.err.println(rs.getString(4));

        }
        return flag;
    }

}
