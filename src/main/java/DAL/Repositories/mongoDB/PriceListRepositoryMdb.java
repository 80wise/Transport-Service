package DAL.Repositories.mongoDB;

import DAL.Interfaces.IPriceListRepository;
import DAL.Interfaces.ITransportRepository;
import DAL.Models.Price;
import DAL.Models.Transport;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class PriceListRepositoryMdb implements IPriceListRepository {
    private final String _connectionString;
    private final String _dbName;
    private final String _collectionName;
    private ITransportRepository transportRepository;

    public PriceListRepositoryMdb(String _connectionString, String _dbName, String _collectionName) {
        this._connectionString = _connectionString;
        this._dbName = _dbName;
        this._collectionName = _collectionName;
    }

    public PriceListRepositoryMdb(String _connectionString, String _dbName, String _collectionName,
                                  ITransportRepository transportRepository) {
        this._connectionString = _connectionString;
        this._dbName = _dbName;
        this._collectionName = _collectionName;
        this.transportRepository = transportRepository;
    }

    @Override
    public Optional<Price> getById(int id) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> pricesCollection = database.getCollection(_collectionName);

        Document priceDocument = pricesCollection.find(eq("_id", id)).first();
        if (priceDocument != null) {
            String typeOfOrder = priceDocument.getString("orderType");
            double pricePerKg = priceDocument.getDouble("pricePerKg");
            int idTransport = priceDocument.get("transport", Document.class).getInteger("_id");

            return Optional.of(new Price(id, typeOfOrder, idTransport, pricePerKg));
        }
        return Optional.empty();
    }

    @Override
    public boolean create(Price entity) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> pricesCollection = database.getCollection(_collectionName);

        if(transportRepository != null){
            Optional<Transport> transport =  transportRepository.getById(entity.getIdTransport());
            if(transport.isPresent()){
                Document newPrice = new Document("_id", entity.getId())
                        .append("orderType", entity.getTypeOrder())
                        .append("pricePerKg", entity.getPricePerKg())
                        .append("transport", new Document("_id", transport.get().getIdTransport())
                                .append("name", transport.get().getName()));
                pricesCollection.insertOne(newPrice);
            }

        }
        return true;
    }

    @Override
    public Optional<List<Price>> getAll() throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> pricesCollection = database.getCollection(_collectionName);

        List<Price> prices = new ArrayList<>();
        FindIterable<Document> priceListDocument = pricesCollection.find();
        int id;
        String typeOfOrder;
        double pricePerKg;
        int idTransport;
        for (Document priceDocument: priceListDocument) {
            id = priceDocument.getInteger("_id");;
            typeOfOrder = priceDocument.getString("orderType");
            pricePerKg = priceDocument.getDouble("pricePerKg");
            idTransport = priceDocument.get("transport", Document.class).getInteger("_id");

            prices.add(new Price(id, typeOfOrder, idTransport, pricePerKg));
        }
        return Optional.of(prices);
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> pricesCollection = database.getCollection(_collectionName);

        Document deletedPrice = pricesCollection.findOneAndDelete(eq("_id", id));
        return deletedPrice != null;
    }

    @Override
    public boolean update(Price entity) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> pricesCollection = database.getCollection(_collectionName);

        Document updatedPrice = pricesCollection.findOneAndUpdate(eq("_id", entity.getId()),
                combine(set("orderType", entity.getTypeOrder()),set("pricePerKg", entity.getPricePerKg())));
        return updatedPrice != null;
    }
}
