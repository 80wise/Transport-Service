package DAL.Models;

public class Person {

    private int id;
    private String name;
    private String passportData;
    private String city;
    private String street;
    private String apartment;
    private String phone;
    private double bankBalance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportData() {
        return passportData;
    }

    public void setPassportData(String passportData) {
        this.passportData = passportData;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(double bankBalance) {
        this.bankBalance = bankBalance;
    }

    public Person() {
    }

    public Person(int id, String name, String passportData,
                  String city, String street, String apartment,
                  String phone, double bankBalance) {
        this.id = id;
        this.name = name;
        this.passportData = passportData;
        this.city = city;
        this.street = street;
        this.apartment = apartment;
        this.phone = phone;
        this.bankBalance = bankBalance;
    }

}
