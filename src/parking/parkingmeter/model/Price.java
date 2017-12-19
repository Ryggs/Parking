package parking.parkingmeter.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Price {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty priceName = new SimpleStringProperty("");
    private StringProperty priceType = new SimpleStringProperty("");
    private IntegerProperty pricePrice = new SimpleIntegerProperty();
    private IntegerProperty priceDuration = new SimpleIntegerProperty();


    public Price() {

    }

    public Price(int id, String priceName, String priceType, int pricePrice, int priceDuration) {
        this.setId(id);
        this.setPriceName(priceName);
        this.setPriceType(priceType);
        this.setPricePrice(pricePrice);
        this.setPriceDuration(priceDuration);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getPriceName() {
        return priceName.get();
    }

    public StringProperty priceNameProperty() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName.set(priceName);
    }

    public String getPriceType() {
        return priceType.get();
    }

    public StringProperty priceTypeProperty() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType.set(priceType);
    }

    public double getPricePrice() {
        return pricePrice.get();
    }

    public IntegerProperty pricePriceProperty() {
        return pricePrice;
    }

    public void setPricePrice(int pricePrice) {
        this.pricePrice.set(pricePrice);
    }

    public int getPriceDuration() {
        return priceDuration.get();
    }

    public IntegerProperty priceDurationProperty() {
        return priceDuration;
    }

    public void setPriceDuration(int priceDuration) {
        this.priceDuration.set(priceDuration);
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", priceType=" + priceType +
                ", pricePrice=" + pricePrice +
                '}';
    }
}
