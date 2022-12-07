package DAL.Repositories.mongoDB;

import DAL.Interfaces.ITransportRepository;
import DAL.Models.Transport;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class TransportRepositoryMdb implements ITransportRepository {
    private final String _connectionString;
    private final String _dbName;
    private final String _collectionName;

    public TransportRepositoryMdb(String _connectionString, String _dbName, String _collectionName) {
        this._connectionString = _connectionString;
        this._dbName = _dbName;
        this._collectionName = _collectionName;
    }

    @Override
    public Optional<Transport> getById(int id) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> transportsCollection = database.getCollection(_collectionName);

        Document transDocument = transportsCollection.find(eq("_id", id)).first();
        if (transDocument != null) {
            String name = transDocument.getString("name");

            return Optional.of(new Transport(id, name));
        }
        return Optional.empty();
    }

    @Override
    public boolean create(Transport entity) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> transportsCollection = database.getCollection(_collectionName);

        Document newTrans = new Document("_id", entity.getIdTransport())
                .append("name", entity.getName());
        transportsCollection.insertOne(newTrans);
        return true;
    }

    @Override
    public Optional<List<Transport>> getAll() throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> transportsCollection = database.getCollection(_collectionName);

        List<Transport> transports = new ArrayList<>();
        FindIterable<Document> transDocuments = transportsCollection.find();
        int id;
        String name;
        for (Document transDocument : transDocuments) {
            name = transDocument.getString("name");
            id = transDocument.getInteger("_id");
            transports.add(new Transport(id, name));
        }
        return Optional.of(transports);
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> transportsCollection = database.getCollection(_collectionName);

        Document deletedTrans = transportsCollection.findOneAndDelete(eq("_id", id));
        return deletedTrans != null;
    }

    @Override
    public boolean update(Transport entity) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> transportsCollection = database.getCollection(_collectionName);

        Document updatedTrans = transportsCollection.findOneAndUpdate(eq("_id", entity.getIdTransport()),
                set("name", entity.getName()));
        return updatedTrans != null;
    }


    @Override
    public Optional<List<Transport>> getByName(String name) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> transportsCollection = database.getCollection(_collectionName);

        List<Transport> transports = new ArrayList<>();
        FindIterable<Document> transDocuments = transportsCollection.find(eq("name", name));
        int id;
        for (Document transDocument : transDocuments) {
            id = transDocument.getInteger("_id");
            transports.add(new Transport(id, name));
        }
        return Optional.of(transports);
    }
}
