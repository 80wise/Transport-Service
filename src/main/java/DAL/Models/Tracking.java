package DAL.Models;

import java.util.Date;

public class Tracking {
    private int id;
    private Date date;
    private String location;
    private int idOrder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public Tracking(int id, Date date, String location, int idOrder) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.idOrder = idOrder;
    }

    @Override
    public String toString() {
        return "Tracking{" +
                "id=" + id +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", idOrder=" + idOrder +
                '}';
    }
}
