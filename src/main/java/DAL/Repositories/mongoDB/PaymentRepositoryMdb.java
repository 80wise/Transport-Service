package DAL.Repositories.mongoDB;

import DAL.Enums.PaymentStatus;
import DAL.Interfaces.IPaymentRepository;
import DAL.Models.*;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class PaymentRepositoryMdb implements IPaymentRepository {
    private final String _connectionString;
    private final String _dbName;
    private final String _collectionName;

    public PaymentRepositoryMdb(String connectionString, String dbName, String collectionName) {
        this._connectionString = connectionString;
        this._dbName = dbName;
        this._collectionName = collectionName;
    }

    @Override
    public Optional<Payment> getById(int id) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> orders = database.getCollection(_collectionName);

        Document orderDocument = orders.find(new Document("_id", id)).first();
        if (orderDocument != null) {
            int idPayment = orderDocument.getInteger("_id");
            String status = orderDocument.get("payment", Document.class).getString("status");
            Date date = orderDocument.get("payment", Document.class).getDate("date");
            double amount = orderDocument.get("payment", Document.class).getDouble("amount");

            PaymentStatus paymentStatus = null;
            for (PaymentStatus payStatus : PaymentStatus.values()) {
                if (payStatus.toString().equals(status))
                    paymentStatus = payStatus;
            }
            Payment payment = new Payment(idPayment, paymentStatus, date, amount);
            return Optional.of(payment);
        }
        return Optional.empty();
    }

    @Override
    public boolean create(Payment entity) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> ordersCollection = database.getCollection(_collectionName);

        Iterable<Document> orders = ordersCollection.find();
        for (Document doc : orders) {
            if (doc.getInteger("_id") == entity.getId()) {
                Document updatedOrder = ordersCollection.findOneAndUpdate(eq("_id", entity.getId()),
                        set("payment", new Document("status", "inProcess")
                                .append("date", entity.getDate())
                                .append("amount", 0)));
                return updatedOrder != null;
            }
        }
        return false;
    }

    @Override
    public Optional<List<Payment>> getAll() throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> orderCollection = database.getCollection(_collectionName);

        FindIterable<Document> orderDocuments = orderCollection.find();
        List<Payment> payments = new ArrayList<>();
        int idPayment;
        String status;
        Date date;
        for (Document orderDocument : orderDocuments) {
            idPayment = orderDocument.getInteger("_id");
            status = orderDocument.get("payment", Document.class).getString("status");
            date = orderDocument.get("payment", Document.class).getDate("date");
            double amount = orderDocument.get("payment", Document.class).getDouble("amount");

            PaymentStatus paymentStatus = null;
            for (PaymentStatus payStatus : PaymentStatus.values()) {
                if (payStatus.toString().equals(status))
                    paymentStatus = payStatus;
            }
            payments.add(new Payment(idPayment, paymentStatus, date, amount));
        }
        return Optional.of(payments);
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> ordersCollection = database.getCollection(_collectionName);

        Document deletedPayment = ordersCollection.findOneAndUpdate(eq("_id", id),
                set("payment", new Document()));
        return deletedPayment != null;
    }

    @Override
    public boolean update(Payment entity) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> ordersCollection = database.getCollection(_collectionName);

        Document updatedPayment = ordersCollection.findOneAndUpdate(eq("_id", entity.getId()),
                set("payment", new Document("status", entity.getStatus().toString())
                        .append("date", entity.getDate())
                        .append("amount", entity.getAmount())));
        return updatedPayment != null;
    }

    @Override
    public boolean setDate(int idEntity, Date date) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> ordersCollection = database.getCollection(_collectionName);

        Document updatedPayment = ordersCollection.findOneAndUpdate(eq("_id", idEntity),
                set("payment.$.date", date));
        return updatedPayment != null;
    }

    @Override
    public boolean pay(Payment entity, Person _payer, List<Price> priceList) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> ordersCollection = database.getCollection(_collectionName);

        double pricePerKg = 0;
        if (_payer != null && entity != null && priceList != null) {
            Iterable<Document> orders = ordersCollection.find();
            for (Document order : orders) {
                if (order.getInteger("_id") == entity.getId()) {
                    for (Price p : priceList) {
                        if (p.getId() == order.getInteger("idPrice"))
                            pricePerKg = p.getPricePerKg();
                    }
                    if (_payer instanceof Sender
                            && order.get("sender", Document.class).getInteger("_id") == _payer.getId()
                            && order.get("sender", Document.class).getDouble("balance") >=
                            pricePerKg * order.getDouble("weight")) {

                        Document updatedOrder = ordersCollection.findOneAndUpdate(eq("_id", entity.getId()),
                                set("payment", new Document("status", "paid")
                                        .append("date", entity.getDate())
                                        .append("amount", pricePerKg * order.getDouble("weight"))));
                        return updatedOrder != null;

                    } else if (_payer instanceof Receiver
                            && order.get("receiver", Document.class).getInteger("_id") == _payer.getId()
                            && order.get("receiver", Document.class).getDouble("balance") >=
                            pricePerKg * order.getDouble("weight")) {

                        Document updatedOrder = ordersCollection.findOneAndUpdate(eq("_id", entity.getId()),
                                set("payment", new Document("status", "paid")
                                        .append("date", entity.getDate())
                                        .append("amount", pricePerKg * order.getDouble("weight"))));
                        return updatedOrder != null;

                    }
                }
            }
        }

        return false;
    }
}
