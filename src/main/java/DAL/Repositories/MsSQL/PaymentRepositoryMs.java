package DAL.Repositories.MsSQL;

import DAL.Enums.PaymentStatus;
import DAL.Interfaces.IPaymentRepository;
import DAL.Models.Order;
import DAL.Models.Payment;
import DAL.Models.Person;
import DAL.Models.Price;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PaymentRepositoryMs implements IPaymentRepository {
    private final String _connectionString;
    private Order orderToPayFor;

    public PaymentRepositoryMs(String _connectionString) {
        this._connectionString = _connectionString;
    }
    public PaymentRepositoryMs(String _connectionString, Order orderToPayFor) {
        this._connectionString = _connectionString;
        this.orderToPayFor = orderToPayFor;
    }

    @Override
    public Optional<Payment> getById(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(_connectionString);
             Statement statement = connection.createStatement()) {
            String query = "SELECT idOrder,status,date,amount FROM Payment WHERE idOrder=" + id;
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                for (PaymentStatus status : PaymentStatus.values()) {
                    if (status.toString().equals(rs.getString("status"))) {
                        return Optional.of(new Payment(rs.getInt("idOrder"), status,
                                rs.getDate("date"), rs.getDouble("amount")));
                    }
                }
            }
            return Optional.empty();
        }
    }

    @Override
    public boolean create(Payment entity) throws SQLException {
        String insertSql = "INSERT INTO Payment (idOrder, status, date, amount) VALUES "
                + "(" + entity.getId() + ",'" + entity.getStatus().toString() + "','"
                + entity.getDate() + "'," + entity.getAmount() + ");";

        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsInsertPayment = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);) {
            prepsInsertPayment.execute();
            resultSet = prepsInsertPayment.getGeneratedKeys();

            return resultSet.next();
        }
    }

    @Override
    public Optional<List<Payment>> getAll() throws Exception {
        try (Connection connection = DriverManager.getConnection(_connectionString);
             Statement statement = connection.createStatement()) {
            String query = "SELECT idOrder,status,date,amount FROM Payment";
            ResultSet rs = statement.executeQuery(query);
            List<Payment> payments = new ArrayList<>();
            while (rs.next()) {
                for (PaymentStatus status : PaymentStatus.values()) {
                    if (status.toString().equals(rs.getString("status"))) {
                        payments.add(new Payment(rs.getInt("idOrder"), status,
                                rs.getDate("date"), rs.getDouble("amount")));
                    }
                }
            }
            return Optional.of(payments);
        }
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        String query = "DELETE FROM Payment WHERE id=" + id;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsDeletePayment = connection.prepareStatement(query);) {
            prepsDeletePayment.execute();
            return prepsDeletePayment.getUpdateCount() == 1;
        }
    }

    @Override
    public boolean update(Payment entity) throws Exception {
        String query = "UPDATE Payment SET status='" + entity.getStatus() +
                "',date='" + entity.getDate() + "',amount=" + entity.getAmount() +
                " WHERE idOrder=" + entity.getId();

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsUpdatePayment = connection.prepareStatement(query);) {
            prepsUpdatePayment.execute();
            return prepsUpdatePayment.getUpdateCount() == 1;
        }
    }

    @Override
    public boolean setDate(int idEntity, Date date) throws SQLException {
        String query = "UPDATE Payment SET date='" + date +
                "' WHERE idOrder=" + idEntity;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsUpdatePayment = connection.prepareStatement(query);) {
            prepsUpdatePayment.execute();
            return prepsUpdatePayment.getUpdateCount() == 1;
        }
    }

    @Override
    public boolean pay(Payment entity, Person _payer, List<Price> priceList) throws Exception {
        String query = "UPDATE Payment SET amount=" + entity.getAmount() + "," +
                " status='" + PaymentStatus.paid + "' WHERE idOrder=" + entity.getId();
        if(orderToPayFor != null)
            for (Price price : priceList) {
                if (price.getId() == entity.getId()) {
                    if (_payer.getBankBalance() >= price.getPricePerKg() * orderToPayFor.getWeight()){
                        try (Connection connection = DriverManager.getConnection(_connectionString);
                             PreparedStatement prepsUpdatePayment = connection.prepareStatement(query);) {
                            prepsUpdatePayment.execute();
                            return prepsUpdatePayment.getUpdateCount() == 1;
                        }
                    }
                    break;
                }
            }
        return false;
    }
}
