package parking.server.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subscription {
    private IntegerProperty userNo = new SimpleIntegerProperty();
    private IntegerProperty subNo = new SimpleIntegerProperty();
    private StringProperty startTime = new SimpleStringProperty("");
    private StringProperty endTime = new SimpleStringProperty("");
    private StringProperty buyTime = new SimpleStringProperty("");
    private StringProperty type = new SimpleStringProperty("");
    private IntegerProperty price = new SimpleIntegerProperty();

    public Subscription() {
    }

    public Subscription(int userNo, int subNo, String startTime, String endTime, String buyTime, String type, int price) {
        setUserNo(userNo);
        setSubNo(subNo);
        setStartTime(startTime);
        setEndTime(endTime);
        setBuyTime(buyTime);
        setType(type);
        setPrice(price);
    }

    public int getUserNo() {
        return userNo.get();
    }

    public IntegerProperty userNoProperty() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo.set(userNo);
    }

    public int getSubNo() {
        return subNo.get();
    }

    public IntegerProperty subNoProperty() {
        return subNo;
    }

    public void setSubNo(int subNo) {
        this.subNo.set(subNo);
    }

    public String getStartTime() {
        return startTime.get();
    }

    public StringProperty startTimeProperty() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }

    public String getEndTime() {
        return endTime.get();
    }

    public StringProperty endTimeProperty() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime.set(endTime);
    }

    public String getBuyTime() {
        return buyTime.get();
    }

    public StringProperty buyTimeProperty() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime.set(buyTime);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }
}
