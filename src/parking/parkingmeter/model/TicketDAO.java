package parking.parkingmeter.model;

import parking.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDAO {
    public static TicketCharge getTicketCharge(String ticketNo) throws SQLException {
        String stmt = String.format("call set_ticket_charge('%s')", ticketNo);
        System.out.println(stmt);
        ResultSet rs = null;

        try {
            rs = DBUtil.dbExecuteQuery(stmt);
        } catch (SQLException e) {
            System.err.println("Error while executing login query");
            e.printStackTrace();
        }
        TicketCharge t = null;
        if (rs.next()) {

            int errType = rs.getInt("ErrType");
            int charge;
            int duration;
            String info;

            if (errType == 0) {
                charge = rs.getInt("TicketCharge");
                duration = rs.getInt("DurationTime");
                info = rs.getString("Info");
            }
            else {
                info = rs.getString("Info");
                charge = -1;
                duration = -1;
            }
            t = new TicketCharge(charge, duration, info);
        }
        return t;
    }

    public static TicketPay payTicketCash(String ticketNo) throws SQLException {
        String stmt = String.format("call pay_ticket(%s,\"cash\",0);", ticketNo);
        System.out.println(stmt);
        ResultSet rs = null;

        try {
            rs = DBUtil.dbExecuteQuery(stmt);
        } catch (SQLException e) {
            System.err.println("Error while executing login query");
            e.printStackTrace();
        }
        TicketPay t = null;
        if (rs.next()) {

            int errType = rs.getInt("ErrType");
            String paymentTime;
            int controlCode;
            String info;

            if (errType == 0) {
                paymentTime = rs.getString("PaymentTime");
                controlCode = (int) rs.getDouble("ControlCode");
                info = rs.getString("Info");
            }
            else {
                paymentTime = "";
                controlCode = -1;
                info = rs.getString("Info");
                System.err.println(info);
            }
            t = new TicketPay(paymentTime,controlCode,info);
        }
        return t;
    }





    public static Ticket getPaidTicket(String ticketNo) throws SQLException {
        String stmt = String.format("SELECT * FROM `ticket` where ticketNo = %s;", ticketNo);
        System.out.println(stmt);
        ResultSet rs = null;

        try {
            rs = DBUtil.dbExecuteQuery(stmt);
        } catch (SQLException e) {
            System.err.println("Error while executing login query");
            e.printStackTrace();
        }
        Ticket t = null;
        if (rs.next()) {
            int tNo = rs.getInt("TicketNo");
            String entryTime = rs.getString("EntryTime");
            String paymentTime = rs.getString("PaymentTime");
            String paymentType = rs.getString("PaymentType");
            int charge = rs.getInt("Charge");
            int controlCode = rs.getInt("ControlCode");

            t = new Ticket(tNo, entryTime,paymentTime, paymentType, charge, controlCode);
        }
        return t;
    }
    // TODO: Naprawić wysypywanie przy próbie zapłaty subskrypcją przy już opłaconym gotówką bilecie
    public static TicketPay payTicketSub(String ticketNo, String subNo) throws SQLException {
        String stmt = String.format("call pay_ticket(%s,\"subscription\",%s);", ticketNo, subNo);
        System.out.println(stmt);
        ResultSet rs = null;

        try {
            rs = DBUtil.dbExecuteQuery(stmt);
        } catch (SQLException e) {
            System.err.println("Error while executing login query");
            e.printStackTrace();
        }
        TicketPay t = null;
        if (rs.next()) {

            int errType = rs.getInt("ErrType");
            String paymentTime;
            int controlCode;
            String info;

            if (errType == 0) {
                paymentTime = rs.getString("PaymentTime");
                controlCode = (int) rs.getDouble("ControlCode");
                info = rs.getString("Info");
            }
            else {
                paymentTime = "";
                controlCode = -1;
                info = rs.getString("Info");
                System.err.println(info);
            }
            t = new TicketPay(paymentTime,controlCode,info);
        }
        return t;
    }
}
