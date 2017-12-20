package parking.parkingmeter.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private IntegerProperty userNo = new SimpleIntegerProperty();
    private StringProperty userLogin = new SimpleStringProperty("");
    private StringProperty userPass = new SimpleStringProperty("");
    private StringProperty permType = new SimpleStringProperty("");
    private StringProperty firstName = new SimpleStringProperty("");
    private StringProperty lastName = new SimpleStringProperty("");
    private IntegerProperty phone = new SimpleIntegerProperty();
    private StringProperty email = new SimpleStringProperty("");

    public User() {
    }

    public User(int userNo, String userLogin, String userPass, String permType, String firstName, String lastName, int phone, String email) {
        this.userNo.setValue(userNo);
        this.userLogin.setValue(userLogin);
        this.userPass.setValue(userPass);
        this.permType.setValue(permType);
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
        this.phone.setValue(phone);
        this.email.setValue(email);
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

    public String getUserLogin() {
        return userLogin.get();
    }

    public StringProperty userLoginProperty() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin.set(userLogin);
    }

    public String getUserPass() {
        return userPass.get();
    }

    public StringProperty userPassProperty() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass.set(userPass);
    }

    public String getPermType() {
        return permType.get();
    }

    public StringProperty permTypeProperty() {
        return permType;
    }

    public void setPermType(String permType) {
        this.permType.set(permType);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public int getPhone() {
        return phone.get();
    }

    public IntegerProperty phoneProperty() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone.set(phone);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }


    @Override
    public String toString() {
        return "User{" +
                "userNo=" + userNo +
                ", userLogin=" + userLogin +
                ", userPass=" + userPass +
                ", permType=" + permType +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", phone=" + phone +
                ", email=" + email +
                '}';
    }
}

