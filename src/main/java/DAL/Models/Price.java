package DAL.Models;

public class Price {
    private int id;
    private String orderType;
    private int idTransport;
    private double pricePerKg;

    public int getId(){return id;}

    public String getTypeOrder() {
        return orderType;
    }

    public void setTypeOrder(String typeOrder) {
        this.orderType = typeOrder;
    }

    public int getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(int idTransport) {
        this.idTransport = idTransport;
    }

    public double getPricePerKg() {
        return pricePerKg;
    }

    public void setPricePerKg(float pricePerKg) {
        this.pricePerKg = pricePerKg;
    }

    public Price(int id, String orderType, int idTransport, double pricePerKg) {
        this.id = id;
        this.orderType = orderType;
        this.idTransport = idTransport;
        this.pricePerKg = pricePerKg;
    }
}
