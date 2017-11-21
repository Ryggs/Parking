package parking.bar.model;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import parking.util.DBUtil;

        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Timestamp;


public class TicketDAO {

//*******************************
//Get new ticket - insert new ticket into database
//*******************************
    public static Ticket getNewTicket() throws SQLException, ClassNotFoundException {

        String stmt = "CALL get_ticket()";

        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsTicket = DBUtil.dbExecuteQuery(stmt);
            Ticket newTicket = new Ticket();

            if (rsTicket.next()) {
                System.out.println("TicketNo");
                newTicket.setTicketNo(rsTicket.getInt("TicketNo"));
                newTicket.setEntryTime(Timestamp.valueOf(rsTicket.getString("EntryTime")));
            }
            else{
                System.out.println("Ticket not found");
            }
            //Return newTicket object
            return newTicket;
        } catch (SQLException e) {
            System.out.println("While getting new ticket an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

//*******************************
//Check if car can exit within 15 minutes
//*******************************
    public static boolean isTicketPaid(int ticketNo) throws SQLException, ClassNotFoundException {

        String stmt = "CALL is_ticket_paid(" + ticketNo + ")";

        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsTicket = DBUtil.dbExecuteQuery(stmt);
            if (rsTicket.next()) {
                if (rsTicket.getString(1) == "DONE")
                    return true;
                else
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
}
