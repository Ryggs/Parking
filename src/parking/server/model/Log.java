package parking.server.model;

import javafx.beans.property.*;

public class Log {


        private IntegerProperty ticketNo = new SimpleIntegerProperty();
        private StringProperty entryTime = new SimpleStringProperty("");
        private StringProperty leaveTime = new SimpleStringProperty("");
        private StringProperty paymentTime = new SimpleStringProperty("");
        private StringProperty paymentType = new SimpleStringProperty("");
        private IntegerProperty charge = new SimpleIntegerProperty();
        private IntegerProperty controlCode = new SimpleIntegerProperty();


        public Log() {
        }

        public Log(int ticketNo, String entryTime, String leaveTime, String paymentTime, String paymentType, int charge, int controlCode) {
            this.ticketNo.setValue(ticketNo);
            this.entryTime.setValue(entryTime);
            this.leaveTime.setValue(leaveTime);
            this.paymentTime.setValue(paymentTime);
            this.paymentType.setValue(paymentType);
            this.charge.setValue(charge);
            this.charge.setValue(charge);
            this.controlCode.setValue(controlCode);
        }


    public int getTicketNo() {
        return ticketNo.get();
    }

    public IntegerProperty ticketNoProperty() {
        return ticketNo;
    }

    public void setTicketNo(int ticketNo) {
        this.ticketNo.set(ticketNo);
    }

    public String getEntryTime() {
        return entryTime.get();
    }

    public StringProperty entryTimeProperty() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime.set(entryTime);
    }

    public String getLeaveTime() {
        return leaveTime.get();
    }

    public StringProperty leaveTimeProperty() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime.set(leaveTime);
    }

    public String getPaymentTime() {
        return paymentTime.get();
    }

    public StringProperty paymentTimeProperty() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime.set(paymentTime);
    }

    public String getPaymentType() {
        return paymentType.get();
    }

    public StringProperty paymentTypeProperty() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType.set(paymentType);
    }

    public int getCharge() {
        return charge.get();
    }

    public IntegerProperty chargeProperty() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge.set(charge);
    }

    public int getControlCode() {
        return controlCode.get();
    }

    public IntegerProperty controlCodeProperty() {
        return controlCode;
    }

    public void setControlCode(int controlCode) {
        this.controlCode.set(controlCode);
    }

    @Override
    public String toString() {
        return "Log{" +
                "ticketNo=" + ticketNo +
                ", entryTime=" + entryTime +
                ", leaveTime=" + leaveTime +
                ", paymentTime=" + paymentTime +
                ", paymentType=" + paymentType +
                ", charge=" + charge +
                ", controlCode=" + controlCode +
                '}';
    }
}
