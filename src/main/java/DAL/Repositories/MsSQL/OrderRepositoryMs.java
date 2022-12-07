package DAL.Repositories.MsSQL;

import DAL.Interfaces.IOrderRepository;
import DAL.Models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryMs implements IOrderRepository {
    private final String _connectionString;

    public OrderRepositoryMs(String connectionString) {
        this._connectionString = connectionString;
    }

    @Override
    public Optional<Order> getById(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(_connectionString);
             Statement statement = connection.createStatement()) {
            String query = "SELECT id,[weight],idPrice,idSender,idReceiver,orderStatus FROM [Order] WHERE id=" + id;
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return Optional.of(new Order(rs.getInt("id"), rs.getDouble("weight"),
                        rs.getInt("idPrice"), rs.getInt("idSender"),
                        rs.getInt("idReceiver"), rs.getString("orderStatus")));
            }
            return Optional.empty();
        }

    }

    @Override
    public boolean create(Order entity) throws SQLException {

        String insertSql = "INSERT INTO [Order] ([weight], idPrice, idSender, idReceiver, orderStatus) VALUES "
                + "(" + entity.getWeight() + "," + entity.getIdPrice() + "," + entity.getIdSender() + "," +
                entity.getIdReceiver() + ",'" + entity.getStatus().toString() + "');";

        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsInsertOrder = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);) {
            prepsInsertOrder.execute();
            resultSet = prepsInsertOrder.getGeneratedKeys();

            return resultSet.next();
        }
    }

    @Override
    public Optional<List<Order>> getAll() throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(_connectionString);
             Statement statement = connection.createStatement()) {
            String query = "SELECT id,[weight],idPrice,idSender,idReceiver,orderStatus FROM [Order]";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                orders.add(new Order(rs.getInt("id"), rs.getDouble("weight"),
                        rs.getInt("idPrice"), rs.getInt("idSender"),
                        rs.getInt("idReceiver"), rs.getString("orderStatus")));
            }
            if (!orders.isEmpty())
                return Optional.of(orders);
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        String query = "DELETE FROM [Order] WHERE id=" + id;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsDeleteOrder = connection.prepareStatement(query);) {
            prepsDeleteOrder.execute();
            return prepsDeleteOrder.getUpdateCount() == 1;
        }
    }

    @Override
    public boolean update(Order entity) throws SQLException {
        String query = "UPDATE [Order] SET [weight]=" + entity.getWeight() +
                ",idPrice=" + entity.getIdPrice() + ",idSender=" + entity.getIdSender() +
                ",idReceiver=" + entity.getIdReceiver() + ",orderStatus='" + entity.getStatus().toString() +
                "' WHERE id=" + entity.getId();

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsUpdateOrder = connection.prepareStatement(query);) {
            prepsUpdateOrder.execute();
            return prepsUpdateOrder.getUpdateCount() == 1;
        }
    }

    @Override
    public boolean deliverOrder(int idOrder) throws SQLException {
        String query = "UPDATE [Order] SET orderStatus= 'delivered' WHERE id=" + idOrder;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsUpdateOrder = connection.prepareStatement(query);) {
            prepsUpdateOrder.execute();
            return prepsUpdateOrder.getUpdateCount() == 1;
        }
    }
}
