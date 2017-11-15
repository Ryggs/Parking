package parking.util;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;

public class DBUtil {
    // Declare JDBC Driver
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

    // Connection
    private static Connection conn = null;

    // Login data
    private static final String username = "javaparking";
    private static final String pass = "javaparking";
    private static final String ip = "localhost";
    private static final String port = "3306";
    private static final String sid = "javaparking";

    //Connection String
    //String connStr = "jdbc:oracle:thin:Username/Password@IP:Port/SID";  - oracle
    //       jdbc:mysql://localhost:3306/
    private static final String connStr = String.format("jdbc:mysql://%s:%s", ip, port);

    // Connect to DB
    public static void dbConnect() throws SQLException {
        // setting Oracle driver
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println(connStr);
        } catch (ClassNotFoundException e) {
            System.err.printf("Where is your Oracle JDBC driver?");
            e.printStackTrace();
        }
        System.out.println("Oracle JDBC Driver Registered!");

        // Establish the Oracle Connection
        try {
            conn = DriverManager.getConnection(connStr,username,pass);
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }
    }

    // Close connection
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error while closing the connection!");
            e.printStackTrace();
        }
    }

    // DB Execute Query Operation (select)
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSet cachedRowSet = null;

        try {
            // Connect to DB
            dbConnect();
            System.out.println("Select statement: " + queryStmt);

            // Create statement
            stmt = conn.createStatement();

            // Execute operation
            resultSet = stmt.executeQuery(queryStmt);

            // Cached Row Set Implementation
            // In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next error
            cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(resultSet);


        } catch (SQLException e) {
            System.err.println("Error at executing query");
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (stmt != null) {
                stmt.close();
            }

            // Close connection
            conn.close();
        }
        return cachedRowSet;
    }

    public static void dbExecuteUpdate(String sqlStmt) throws SQLException {
        Statement stmt = null;

        try {
            dbConnect();

            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.err.println("Error while executing update statement");
            e.printStackTrace();
        } finally {
            if(stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
    }

}
