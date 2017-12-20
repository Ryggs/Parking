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
public static Sub getLatestSub(String userlogin) throws SQLException {
        String stmt = String.format("call get_newest_sub('%s')", userlogin);
        System.out.println(stmt);
        ResultSet rs = null;
        int flag = -1;

        try {
            rs = DBUtil.dbExecuteQuery(stmt);
        } catch (SQLException e) {
            System.err.println("Error while executing login query");
            e.printStackTrace();
        }
        Sub s = null;
        if (rs.next()) {
           // String userlogin = rs.getString(1);
            String name = rs.getString(2);
            String surname = rs.getString(3);
            String startTime = rs.getString(4);
            String endTime = rs.getString(5);
            s = new Sub(userlogin,name,surname,startTime,endTime);
        }
        return s;
    }


}
