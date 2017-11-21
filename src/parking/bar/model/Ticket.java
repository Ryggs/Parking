package parking.bar.model;

import javafx.beans.property.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 */
public class Ticket {
    //Declare Ticket Table Columns
    private IntegerProperty TicketNo;
    private SimpleObjectProperty<Timestamp> EntryTime;
    private SimpleObjectProperty<Timestamp> LeaveTime;
    private SimpleObjectProperty<Timestamp> PaymentTime;
    private StringProperty PaymentType;
    private IntegerProperty Charge;

    //Constructor
    public Ticket() {
        this.TicketNo = new SimpleIntegerProperty();
        this.EntryTime = new SimpleObjectProperty<>();
        this.LeaveTime = new SimpleObjectProperty<>();
        this.PaymentTime = new SimpleObjectProperty<>();
        this.PaymentType = new SimpleStringProperty();
        this.Charge = new SimpleIntegerProperty();
    }

    //TicketNo
    public int getTicketNo() {
        return TicketNo.get();
    }

    public void setTicketNo(int TicketNo){
        this.TicketNo.set(TicketNo);
    }

    public IntegerProperty ticketNoProperty(){
        return TicketNo;
    }

    //EntryTime
    public Object getEntryTime(){
        return EntryTime.get();
    }

    public void setEntryTime(Timestamp EntryTime){
        this.EntryTime.set(EntryTime);
    }

    public SimpleObjectProperty<Timestamp> entryTimeProperty(){
        return EntryTime;
    }

    //LeaveTime
    public Object getLeaveTime(){
        return LeaveTime.get();
    }

    public void setLeaveTime(Timestamp LeaveTime){
        this.LeaveTime.set(LeaveTime);
    }

    public SimpleObjectProperty<Timestamp> leaveTimeProperty(){
        return LeaveTime;
    }

    //PaymentTime
    public Object getPaymentTime(){
        return PaymentTime.get();
    }

    public void setPaymentTime(Timestamp PaymentTime){
        this.PaymentTime.set(PaymentTime);
    }

    public SimpleObjectProperty<Timestamp> paymentTimeProperty(){
        return PaymentTime;
    }

    //PaymentType
    public String getPaymentType () {
        return PaymentType.get();
    }

    public void setPaymentType(String PaymentType){
        this.PaymentType.set(PaymentType);
    }

    public StringProperty paymentTypeProperty() {
        return PaymentType;
    }

    //Charge
    public int getCharge() {
        return Charge.get();
    }

    public void setCharge(int Charge){
        this.Charge.set(Charge);
    }

    public IntegerProperty chargeProperty(){
        return Charge;
    }



}