package DAL.Models;

import DAL.Enums.OrderStatus;

public class Order {
    private int id;
    private double weight;
    private int idPrice;
    private int idSender;
    private int idReceiver;
    private OrderStatus status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getIdPrice() {
        return idPrice;
    }

    public void setIdPrice(int idPrice) {
        this.idPrice = idPrice;
    }

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public int getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(int idReceiver) {
        this.idReceiver = idReceiver;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Order(int id, double weight, int idPrice, int idSender, int idReceiver, String status) {
        this.id = id;
        this.weight = weight;
        this.idPrice = idPrice;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        for(OrderStatus s: OrderStatus.values())
            if(s.toString().equals(status))
                this.status = s;
    }
    public Order(int id, double weight, int idPrice, int idSender, int idReceiver) {
        this.id = id;
        this.weight = weight;
        this.idPrice = idPrice;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
    }

    public Order(double weight, int idPrice, int idSender, int idReceiver) {
        this.weight = weight;
        this.idPrice = idPrice;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
    }

    public Order(int id, double weight, OrderStatus status) {
        this.id = id;
        this.weight = weight;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Id:" + id + " Weight: " + weight + " IdSender: " + idSender + " idReceiver: " + idReceiver + "\n";
    }
}
