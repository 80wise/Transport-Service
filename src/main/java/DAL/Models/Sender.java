package DAL.Models;

public class Sender extends Person{
    public Sender(int id, String name, String passportData, String city, String street,
                  String apartment, String phone, double bankBalance) {
        super(id, name, passportData, city, street, apartment, phone, bankBalance);
    }
    public Sender(){}
}
