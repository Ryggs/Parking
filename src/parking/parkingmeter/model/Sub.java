package parking.parkingmeter.model;

public class Sub {
    private String userlogin;
    private String name;
    private String surname;
    private String startTime;
    private String endTime;

    public Sub(String userlogin, String name, String surname, String startTime, String endTime) {
        this.userlogin = userlogin;
        this.name = name;
        this.surname = surname;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getUserlogin() {
        return userlogin;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
