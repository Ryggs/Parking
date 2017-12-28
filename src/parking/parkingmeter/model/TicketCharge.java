package parking.parkingmeter.model;

public class TicketCharge {
    private int charge;
    private int duration;
    private String info;

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public TicketCharge(int charge, int duration, String info) {
        this.charge = charge;
        this.duration = duration;
        this.info = info;
    }
}
