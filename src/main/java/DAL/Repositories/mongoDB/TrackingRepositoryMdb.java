package DAL.Repositories.mongoDB;

import DAL.Interfaces.ITrackingRepository;
import DAL.Models.Tracking;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class TrackingRepositoryMdb implements ITrackingRepository {
    private final String _connectionString;
    private final String _dbName;
    private final String _collectionName;
    private final int idOrderToTrack;

    public TrackingRepositoryMdb(String _connectionString, String _dbName, String _collectionName, int idOrderToTrack) {
        this._connectionString = _connectionString;
        this._dbName = _dbName;
        this._collectionName = _collectionName;
        this.idOrderToTrack = idOrderToTrack;
    }

    @Override
    public Optional<Tracking> getById(int id) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> orders = database.getCollection(_collectionName);

        Document orderDocument = orders.find(eq("_id", idOrderToTrack)).first();
        if (orderDocument != null) {
            List<Document> tracks = (List<Document>) orderDocument.get("Tracking");
            for (Document trackDocument : tracks) {
                if (trackDocument.getInteger("_id") == id) {
                    int idTrack = trackDocument.getInteger("_id");
                    int idOrder = trackDocument.getInteger("idOrder");
                    String location = trackDocument.getString("location");
                    Date date = trackDocument.getDate("date");

                    Tracking track = new Tracking(idTrack, date, location, idOrder);
                    return Optional.of(track);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean create(Tracking entity) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> orders = database.getCollection(_collectionName);

        Document orderDocument = orders.findOneAndUpdate(eq("_id", idOrderToTrack),
                push("Tracking", new Document("_id", entity.getId())
                        .append("idOrder", entity.getIdOrder())
                        .append("date", entity.getDate())
                        .append("location", entity.getLocation())));
        return orderDocument != null;
    }

    @Override
    public Optional<List<Tracking>> getAll() throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> orders = database.getCollection(_collectionName);

        List<Tracking> tracking = new ArrayList<>();
        Document orderDocument = orders.find(eq("_id", idOrderToTrack)).first();
        if (orderDocument != null) {
            List<Document> tracks = (List<Document>) orderDocument.get("Tracking");
            int idTrack, idOrder;
            String location;
            Date date;
            for (Document trackDocument : tracks) {
                idTrack = trackDocument.getInteger("_id");
                idOrder = trackDocument.getInteger("idOrder");
                location = trackDocument.getString("location");
                date = trackDocument.getDate("date");

                tracking.add(new Tracking(idTrack, date, location, idOrder));
            }
        }
        return Optional.of(tracking);
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> orders = database.getCollection(_collectionName);

        Document orderDocument = orders.findOneAndUpdate(eq("_id", idOrderToTrack),
                Updates.pull("Tracking", eq("_id", id)));

        return orderDocument != null;
    }

    @Override
    public boolean update(Tracking entity) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> orders = database.getCollection(_collectionName);

        Document orderDocument = orders.findOneAndUpdate(and(eq("_id", idOrderToTrack),
                eq("Tracking._id", entity.getId())), combine(set("Tracking.$.location",entity.getLocation()),
                set("Tracking.$.date", entity.getDate())));

        return orderDocument != null;
    }

    @Override
    public boolean setDate(int idEntity, Date date) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> orders = database.getCollection(_collectionName);

        Document orderDocument = orders.findOneAndUpdate(and(eq("_id", idOrderToTrack),
                eq("Tracking._id", idEntity)), set("Tracking.$.date", date));

        return orderDocument != null;
    }

    @Override
    public boolean changeLocation(int idTrack, String location) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> orders = database.getCollection(_collectionName);

        Document orderDocument = orders.findOneAndUpdate(and(eq("_id", idOrderToTrack),
                eq("Tracking._id", idTrack)), set("Tracking.$.location", location));

        return orderDocument != null;
    }
}
