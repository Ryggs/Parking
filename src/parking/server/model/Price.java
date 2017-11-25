package parking.server.model;

import javafx.beans.property.*;

public class Price {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty priceType = new SimpleStringProperty("");
    private IntegerProperty pricePrice = new SimpleIntegerProperty();


    public Price() {

    }

    public Price(int id, String priceType, int pricePrice) {
        this.setId(id);
        this.setPriceType(priceType);
        this.setPricePrice(pricePrice);
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

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", priceType=" + priceType +
                ", pricePrice=" + pricePrice +
                '}';
    }
}
