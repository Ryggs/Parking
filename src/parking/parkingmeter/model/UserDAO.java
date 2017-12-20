package parking.parkingmeter.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import parking.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {


    public static boolean addNewUser(String newLogin, String newPass, String newPerm, String newName,
                                     String newSurname, int newPhone, String newEmail )
            throws SQLException, ClassNotFoundException {

        String stmt = "CALL new_user('" + newLogin + "','" + newPass + "','" + newPerm + "','"
                + newName + "','" + newSurname + "'," + newPhone + ",'" + newEmail + "');";


        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rs= DBUtil.dbExecuteQuery(stmt);
            if (rs.next()) {

                if (rs.getString(1).equals("DONE")) {

                    return true;
                }
                else {
                    System.err.println("ErrType: " + rs.getInt(2) + "\nInfo: " + rs.getString(4));
                    return false;
                }
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("While checking if ticket is paid an error occurred: " + e);
            //Return exception
            throw e;
        }

    }

}
