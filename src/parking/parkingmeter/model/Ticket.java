package parking.parkingmeter.model;

public class Ticket {
    private int ticketNo;
    private String entryTime;
    private String paymentTime;
    private String paymentType;
    private int charge;
    private int controlCode;

    public Ticket(int ticketNo, String entryTime, String paymentTime, String paymentType, int charge, int controlCode) {
        this.ticketNo = ticketNo;
        this.entryTime = entryTime;
        this.paymentTime = paymentTime;
        this.paymentType = paymentType;
        this.charge = charge;
        this.controlCode = controlCode;
    }

    public int getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(int ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getControlCode() {
        return controlCode;
    }

    public void setControlCode(int controlCode) {
        this.controlCode = controlCode;
    }
}
