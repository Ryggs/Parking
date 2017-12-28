package parking.parkingmeter.model;

public class TicketPay {
    private String paymentTime;
    private int controlCode;
    private String info;

    public TicketPay(String paymentTime, int controlCode, String info) {
        this.paymentTime = paymentTime;
        this.controlCode = controlCode;
        this.info = info;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public int getControlCode() {
        return controlCode;
    }

    public void setControlCode(int controlCode) {
        this.controlCode = controlCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
