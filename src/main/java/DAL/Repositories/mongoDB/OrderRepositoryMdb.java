package DAL.Repositories.mongoDB;

import DAL.Interfaces.IOrderRepository;
import DAL.Interfaces.IPersonRepository;
import DAL.Models.Order;
import DAL.Models.Person;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class OrderRepositoryMdb implements IOrderRepository {
    private final String _connectionString;
    private final String _dbName;
    private final String _collectionName;
    private IPersonRepository _senderRepository;
    private IPersonRepository _receiverRepository;

    public OrderRepositoryMdb(String connectionString, String dbName, String collectionName,
                              IPersonRepository senderRepository, IPersonRepository receiverRepository) {
        this._connectionString = connectionString;
        this._dbName = dbName;
        this._collectionName = collectionName;
        this._senderRepository = senderRepository;
        this._receiverRepository = receiverRepository;
    }

    public OrderRepositoryMdb(String connectionString, String dbName, String collectionName) {
        this._connectionString = connectionString;
        this._dbName = dbName;
        this._collectionName = collectionName;
    }

    @Override
    public Optional<Order> getById(int id) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> orders = database.getCollection(_collectionName);

        Document orderDocument = orders.find(new Document("_id", id)).first();
        if (orderDocument != null) {
            int idOrder = orderDocument.getInteger("_id");
            int idPrice = orderDocument.getInteger("idPrice");
            double weight = orderDocument.getInteger("weight");
            int idSender = orderDocument.get("sender", Document.class).getInteger("_id");
            int idReceiver = orderDocument.get("receiver", Document.class).getInteger("_id");
            String status = orderDocument.getString("orderStatus");

            Order order = new Order(idOrder, weight, idPrice, idSender, idReceiver, status);
            return Optional.of(order);
        }
        return Optional.empty();
    }

    @Override
    public boolean create(Order entity) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> ordersCollections = database.getCollection(_collectionName);

        if (_senderRepository != null && _receiverRepository != null) {
            Optional<Person> sender = _senderRepository.getById(entity.getIdSender());
            Optional<Person> receiver = _receiverRepository.getById(entity.getIdReceiver());
            if (sender.isPresent() && receiver.isPresent()) {
                Document newOrder = new Document("_id", entity.getId())
                        .append("idPrice", entity.getIdPrice())
                        .append("weight", entity.getWeight())
                        .append("orderStatus", entity.getStatus().toString())
                        .append("sender", new Document("_id", sender.get().getId())
                                .append("name", sender.get().getName())
                                .append("passport", sender.get().getPassportData())
                                .append("city", sender.get().getCity())
                                .append("street", sender.get().getStreet())
                                .append("apartment", sender.get().getApartment())
                                .append("phone", sender.get().getPhone()))
                        .append("receiver", new Document("_id", receiver.get().getId())
                                .append("name", receiver.get().getName())
                                .append("passport", receiver.get().getPassportData())
                                .append("city", receiver.get().getCity())
                                .append("street", receiver.get().getStreet())
                                .append("apartment", receiver.get().getApartment())
                                .append("phone", receiver.get().getPhone()));
                ordersCollections.insertOne(newOrder);
                return true;
            }
        }

        return false;
    }

    @Override
    public Optional<List<Order>> getAll() throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> orderCollection = database.getCollection(_collectionName);

        FindIterable<Document> orderDocuments = orderCollection.find(new Document());
        List<Order> orders = new ArrayList<>();
        int idOrder, idPrice, idSender, idReceiver;
        double weight;
        String status;
        for (Document order : orderDocuments) {
            idOrder = order.getInteger("_id");
            idPrice = order.getInteger("idPrice");
            weight = order.getInteger("weight");
            idSender = order.get("sender", Document.class).getInteger("_id");
            idReceiver = order.get("receiver", Document.class).getInteger("_id");
            status = order.getString("orderStatus");
            orders.add(new Order(idOrder, weight, idPrice, idSender, idReceiver, status));
        }
        return Optional.of(orders);
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> ordersCollection = database.getCollection(_collectionName);

        Document deletedOrder = ordersCollection.findOneAndDelete(eq("_id", id));
        return deletedOrder != null;
    }

    @Override
    public boolean update(Order entity) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> ordersCollection = database.getCollection(_collectionName);

        Document updatedOrder = ordersCollection.findOneAndUpdate(eq("_id", entity.getId()),
                combine(set("weight", entity.getWeight()), set("orderStatus", entity.getStatus().toString())));
        return updatedOrder != null;
    }

    public boolean applyChangesOfBalance(int idPerson, double amount, String personField) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> ordersCollection = database.getCollection(_collectionName);

        UpdateResult updatedBalance = ordersCollection.updateMany(Filters.eq(personField, new Document("_id", idPerson)),
                set(personField, new Document("balance", amount)));

        return updatedBalance.getModifiedCount() >= 0;
    }

    @Override
    public boolean deliverOrder(int idOrder) throws Exception{
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> ordersCollection = database.getCollection(_collectionName);

        Document updatedOrder = ordersCollection.findOneAndUpdate(eq("_id", idOrder),
                set("orderStatus", "delivered"));
        return updatedOrder != null;
    }

}
