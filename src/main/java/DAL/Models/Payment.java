package DAL.Models;

import DAL.Enums.PaymentStatus;

import java.util.Date;

public class Payment {

    private int id;
    private PaymentStatus status;
    private Date date;
    private double amount;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Payment(int id, PaymentStatus status, Date date, double amount) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.amount = amount;
    }

    public Payment(Date date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public Payment(){}

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", status=" + status +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
