package DAL.Repositories.MsSQL;

import DAL.Interfaces.ITrackingRepository;
import DAL.Models.Tracking;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TrackingRepositoryMs implements ITrackingRepository {
    private final String _connectionString;

    public TrackingRepositoryMs(String _connectionString) {
        this._connectionString = _connectionString;
    }

    @Override
    public Optional<Tracking> getById(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(_connectionString);
             Statement statement = connection.createStatement()) {
            String query = "SELECT id,idOrder,date,location FROM Tracking WHERE id=" + id;
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return Optional.of(new Tracking(rs.getInt("id"), rs.getDate("date"),
                        rs.getString("location"), rs.getInt("idOrder")));
            }
            return Optional.empty();
        }
    }

    @Override
    public boolean create(Tracking entity) throws SQLException {
        String insertSql = "INSERT INTO Tracking (idOrder,date,location) VALUES "
                + "(" + entity.getIdOrder() + ",'" + entity.getDate() + "'," + entity.getLocation() + ");";

        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsInsertTrack = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);) {
            prepsInsertTrack.execute();
            resultSet = prepsInsertTrack.getGeneratedKeys();

            return resultSet.next();
        }
    }

    @Override
    public Optional<List<Tracking>> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(_connectionString);
             Statement statement = connection.createStatement()) {
            String query = "SELECT id,idOrder,date,location FROM Tracking";
            List<Tracking> tracks = new ArrayList<>();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                tracks.add(new Tracking(rs.getInt("id"), rs.getDate("date"),
                        rs.getString("location"), rs.getInt("idOrder")));
            }
            return Optional.of(tracks);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        String query = "DELETE FROM Tracking WHERE id=" + id;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsDeleteTrack = connection.prepareStatement(query);) {
            prepsDeleteTrack.execute();
            return prepsDeleteTrack.getUpdateCount() == 1;
        }
    }

    @Override
    public boolean update(Tracking entity) throws SQLException {
        String query = "UPDATE Tracking SET date='" + entity.getDate() +
                "',location='" + entity.getLocation() + "',idOrder=" + entity.getIdOrder() +
                " WHERE id=" + entity.getId();

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsUpdateTrack = connection.prepareStatement(query);) {
            prepsUpdateTrack.execute();
            return prepsUpdateTrack.getUpdateCount() == 1;
        }
    }

    @Override
    public boolean setDate(int idEntity, Date date) throws SQLException {
        String query = "UPDATE Tracking SET date='" + date +
                "' WHERE id=" + idEntity;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsUpdateTrack = connection.prepareStatement(query);) {
            prepsUpdateTrack.execute();
            return prepsUpdateTrack.getUpdateCount() == 1;
        }
    }

    @Override
    public boolean changeLocation(int idTrack, String location) throws SQLException {
        String query = "UPDATE Tracking SET location='" + location +
                "' WHERE id=" + idTrack;

        try (Connection connection = DriverManager.getConnection(_connectionString);
             PreparedStatement prepsUpdateTrack = connection.prepareStatement(query);) {
            prepsUpdateTrack.execute();
            return prepsUpdateTrack.getUpdateCount() == 1;
        }
    }
}
