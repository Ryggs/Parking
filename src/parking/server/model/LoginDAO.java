package parking.server.model;

import parking.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for getting login information from DB
 */
public class LoginDAO {

    /**
     * Method checks if user with given login and password existing in DB and has admin privileges
     * @param
     *  username - username
     *  password - password
     *  permType - admin / user
     *int perm_level
     * @return  true - user correctly logged in
     *          false - wrong password
     */
    public static boolean login(String username, String password, String permType) throws SQLException {
        String selectstmt = String.format( "SELECT count(*) as 'logged' FROM carpark.user " +
                "WHERE UserLogin = '%s' AND UserPass = '%s' AND PermType = '%s'",username, password, permType);

        ResultSet rs = null;
        boolean isLogged = false;

        try {
            rs = DBUtil.dbExecuteQuery(selectstmt);
        } catch (SQLException e) {
            System.err.println("Error while executing login query");
            e.printStackTrace();
        }

        if (rs.next()) {
            isLogged = rs.getBoolean("logged");
        }
        return isLogged;
    }
}
