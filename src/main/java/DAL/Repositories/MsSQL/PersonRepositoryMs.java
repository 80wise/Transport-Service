package DAL.Repositories.MsSQL;

import DAL.Interfaces.IPersonRepository;
import DAL.Models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class PersonRepositoryMs implements IPersonRepository {
    protected final String _connectionString;
    protected final String _tableName;

    public PersonRepositoryMs(String _connectionString, String _tableName) {
        this._connectionString = _connectionString;
        this._tableName = _tableName;
    }

    @Override
    public Optional<Person> getById(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(_connectionString);
             Statement statement = connection.createStatement()) {
            String query = "SELECT id,name,passport,city,street,apartment,phone,balance FROM " + _tableName
                    + " WHERE id=" + id;
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return Optional.of(new Person(rs.getInt("id"), rs.getString("name"),
                        rs.getString("passport"), rs.getString("city"),
                        rs.getString("street"), rs.getString("apartment"),
                        rs.getString("phone"), rs.getDouble("balance")));
            }
            return Optional.empty();
        }
    }

    @Override
    public boolean create(Person entity) throws SQLException {
        String insertSql = "INSERT INTO " + _tableName + " (name,passport,city,street,apartment,phone,balance) VALUES "
                + "('" + entity.getName() + "','" + entity.getPassportData() + "','" + entity.getCity()
                + "','" + entity.getStreet() + "','" + entity.getApartment() + "','" + entity.getPhone() + "'," +
                entity.getBankBalance() + ");";

        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsInsertPerson = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);) {
            prepsInsertPerson.execute();
            resultSet = prepsInsertPerson.getGeneratedKeys();

            return resultSet.next();
        }
    }

    @Override
    public Optional<List<Person>> getAll() throws SQLException {
        List<Person> people = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(_connectionString);
             Statement statement = connection.createStatement()) {
            String query = "SELECT id,name,passport,city,street,apartment,phone,balance FROM " + _tableName + ";";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                people.add(new Person(rs.getInt("id"), rs.getString("name"),
                        rs.getString("passport"), rs.getString("city"),
                        rs.getString("street"), rs.getString("apartment"),
                        rs.getString("phone"), rs.getDouble("balance")));
            }
            if (!people.isEmpty())
                return Optional.of(people);
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        String query = "DELETE FROM " + _tableName + " WHERE id=" + id;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsDeletePerson = connection.prepareStatement(query);) {
            prepsDeletePerson.execute();
            return prepsDeletePerson.getUpdateCount() == 1;
        }
    }

    @Override
    public boolean update(Person entity) throws SQLException {
        String query = "UPDATE " + _tableName + " SET name = '" + entity.getName() +
                "',passport = '" + entity.getPassportData() +
                "',city = '" + entity.getCity() + "',street = '" + entity.getStreet() +
                "',apartment = '" + entity.getApartment() +
                "',phone = '" + entity.getPhone() + "',balance = " + entity.getBankBalance() +
                " WHERE id=" + entity.getId();

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsUpdatePerson = connection.prepareStatement(query);) {
            prepsUpdatePerson.execute();
            return prepsUpdatePerson.getUpdateCount() == 1;
        }
    }

    @Override
    public Optional<List<Person>> getByName(String name) throws SQLException {
        List<Person> people = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(_connectionString);
             Statement statement = connection.createStatement()) {
            String query = "SELECT id,name,passport,city,street,apartment,phone,balance FROM " + _tableName +
                    "WHERE name = " + name + ";";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                people.add(new Person(rs.getInt("id"), rs.getString("name"),
                        rs.getString("passport"), rs.getString("city"),
                        rs.getString("street"), rs.getString("apartment"),
                        rs.getString("phone"), rs.getDouble("balance")));
            }
            if (!people.isEmpty())
                return Optional.of(people);
            return Optional.empty();
        }
    }

    @Override
    public boolean changeBankBalance(int id, double amount) throws SQLException {
        String query = "UPDATE " + _tableName + " SET balance = " + amount +
                " WHERE id=" + id;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsUpdatePerson = connection.prepareStatement(query);) {
            prepsUpdatePerson.execute();
            return prepsUpdatePerson.getUpdateCount() == 1;
        }
    }
}
