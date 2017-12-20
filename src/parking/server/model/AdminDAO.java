package parking.server.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import parking.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {


    public static boolean addNewUser(String newLogin, String newPass, String newPerm, String newName,
                                     String newSurname, int newPhone, String newEmail )
            throws SQLException, ClassNotFoundException {

        String stmt = "CALL new_user('" + newLogin + "','" + newPass + "','" + newPerm + "','"
                + newName + "','" + newSurname + "'," + newPhone + ",'" + newEmail + "');";


        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rs= DBUtil.dbExecuteQuery(stmt);
              if (rs.next()) {
                    if (rs.getString(1) == "DONE")
                    return true;
                else
                    System.err.println("ErrType: " + rs.getInt(2) + "\nInfo: " + rs.getString(4) );
                    return false;
            }
            else
                return false;
        } catch (SQLException e) {
            System.out.println("While checking if ticket is paid an error occurred: " + e);
            //Return exception
            throw e;
        }

    }

    public static ObservableList<Admin> getAllUsers() throws SQLException {
        String stmt = "SELECT * FROM userparking";
        ResultSet rs = null;
        try {
            rs= DBUtil.dbExecuteQuery(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<Admin> list = FXCollections.observableArrayList();

        while (rs.next()) {
            int userNo = rs.getInt(1);
            String userLogin = rs.getString(2);
            String userPass = rs.getString(3);
            String permType = rs.getString(4);
            String firstName = rs.getString(5);
            String lastName = rs.getString(6);
            int phone = rs.getInt(7);
            String email = rs.getString(8);


            Admin a = new Admin(userNo,userLogin,userPass,permType,firstName,lastName,phone,email);
            list.add(a);
        }
        return list;


    }

    public static void update(int index, String columnName, Object newValue) throws SQLException {


        String stmt = "UPDATE userparking SET `"+ columnName +"` = '" +newValue+ "' WHERE `UserNo` = " + index;

        DBUtil.dbExecuteUpdate(stmt);


    }
}
