package DAL.Repositories.MsSQL;

import DAL.Interfaces.IPriceListRepository;
import DAL.Models.Price;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PriceListRepositoryMs implements IPriceListRepository {

    private final String _connectionString;

    public PriceListRepositoryMs(String _connectionString) {
        this._connectionString = _connectionString;
    }

    @Override
    public Optional<Price> getById(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(_connectionString);
             Statement statement = connection.createStatement()) {
            String query = "SELECT id,orderType,idTransport,pricePerKg FROM PriceList WHERE id=" + id;
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return Optional.of(new Price(rs.getInt("id"), rs.getString("orderType"),
                        rs.getInt("idTransport"), rs.getDouble("priceList")));
            }
            return Optional.empty();
        }
    }

    @Override
    public boolean create(Price entity) throws SQLException {
        String insertSql = "INSERT INTO PriceList (orderType,idTransport,pricePerKg) VALUES "
                + "('" + entity.getTypeOrder() + "'," + entity.getIdTransport() + "," + entity.getPricePerKg() + ");";

        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsInsertPrice = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);) {
            prepsInsertPrice.execute();
            resultSet = prepsInsertPrice.getGeneratedKeys();

            return resultSet.next();
        }
    }

    @Override
    public Optional<List<Price>> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(_connectionString);
             Statement statement = connection.createStatement()) {
            String query = "SELECT id,orderType,idTransport,pricePerKg FROM PriceList";
            List<Price> priceList = new ArrayList<>();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                priceList.add(new Price(rs.getInt("id"), rs.getString("orderType"),
                        rs.getInt("idTransport"), rs.getDouble("priceList")));
            }
            return Optional.of(priceList);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        String query = "DELETE FROM PriceList WHERE id=" + id;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsDeletePriceList = connection.prepareStatement(query);) {
            prepsDeletePriceList.execute();
            return prepsDeletePriceList.getUpdateCount() == 1;
        }
    }

    @Override
    public boolean update(Price entity) throws SQLException {
        String query = "UPDATE PriceList SET orderType='" + entity.getTypeOrder() +
                "',idTransport=" + entity.getIdTransport() + ",pricePerKg=" + entity.getPricePerKg() +
                " WHERE id=" + entity.getId();

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsUpdatePrice = connection.prepareStatement(query);) {
            prepsUpdatePrice.execute();
            return prepsUpdatePrice.getUpdateCount() == 1;
        }
    }
}
