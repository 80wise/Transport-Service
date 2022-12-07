package DAL.Models;

public class Transport {
    private int idTransport;
    private String name;

    public int getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(int idTransport) {
        this.idTransport = idTransport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Transport(int idTransport, String name) {
        this.idTransport = idTransport;
        this.name = name;
    }
}
