package DAL.Repositories.MsSQL;

import DAL.Interfaces.ITransportRepository;
import DAL.Models.Transport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransportRepositoryMs implements ITransportRepository {
    private final String _connectionString;

    public TransportRepositoryMs(String _connectionString) {
        this._connectionString = _connectionString;
    }

    @Override
    public Optional<Transport> getById(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(_connectionString);
             Statement statement = connection.createStatement()) {
            String query = "SELECT id,name FROM Transport WHERE id=" + id;
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return Optional.of(new Transport(rs.getInt("id"), rs.getString("name")));
            }
            return Optional.empty();
        }
    }

    @Override
    public boolean create(Transport entity) throws SQLException {
        String insertSql = "INSERT INTO Transport (name) VALUES "
                + "('" + entity.getName() + "');";

        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsInsertTransport = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);) {
            prepsInsertTransport.execute();
            resultSet = prepsInsertTransport.getGeneratedKeys();

            return resultSet.next();
        }
    }

    @Override
    public Optional<List<Transport>> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(_connectionString);
             Statement statement = connection.createStatement()) {
            String query = "SELECT id,name FROM Transport";
            ResultSet rs = statement.executeQuery(query);
            List<Transport> transports = new ArrayList<>();
            while (rs.next()) {
                transports.add(new Transport(rs.getInt("id"), rs.getString("name")));
            }
            return Optional.of(transports);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        String query = "DELETE FROM Transport WHERE id=" + id;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsDeleteTransport = connection.prepareStatement(query);) {
            prepsDeleteTransport.execute();
            return prepsDeleteTransport.getUpdateCount() == 1;
        }
    }

    @Override
    public boolean update(Transport entity) throws SQLException {
        String query = "UPDATE Transport SET name='" + entity.getName() +
                "' WHERE idOrder=" + entity.getIdTransport();

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsUpdateTransport = connection.prepareStatement(query);) {
            prepsUpdateTransport.execute();
            return prepsUpdateTransport.getUpdateCount() == 1;
        }
    }

    @Override
    public Optional<List<Transport>> getByName(String name) throws SQLException {
        try (Connection connection = DriverManager.getConnection(_connectionString);
             Statement statement = connection.createStatement()) {
            List<Transport> transports = new ArrayList<>();
            String query = "SELECT id,name FROM Transport WHERE name='" + name + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                transports.add(new Transport(rs.getInt("id"), rs.getString("name")));
            }
            return Optional.of(transports);
        }
    }
}
