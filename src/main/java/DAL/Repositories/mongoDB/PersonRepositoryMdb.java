package DAL.Repositories.mongoDB;

import DAL.Interfaces.IOrderRepository;
import DAL.Interfaces.IPersonRepository;
import DAL.Models.Person;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public abstract class PersonRepositoryMdb implements IPersonRepository {
    protected final String _connectionString;
    protected final String _dbName;
    protected final String _collectionName;
    protected IOrderRepository _orderRepository;

    public PersonRepositoryMdb(String connectionString, String dbName, String collectionName) {
        this._connectionString = connectionString;
        this._dbName = dbName;
        this._collectionName = collectionName;
    }

    public PersonRepositoryMdb(String connectionString, String dbName, String collectionName,
                               IOrderRepository _orderRepository) {
        this._connectionString = connectionString;
        this._dbName = dbName;
        this._collectionName = collectionName;
        this._orderRepository = _orderRepository;
    }

    @Override
    public Optional<Person> getById(int id) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> peopleCollection = database.getCollection(_collectionName);

        Document personDocument = peopleCollection.find(new Document("_id", id)).first();
        if (personDocument != null) {
            String name = personDocument.getString("name");
            String passport = personDocument.getString("passport");
            String city = personDocument.getString("city");
            String street = personDocument.getString("street");
            String apartment = personDocument.getString("apartment");
            String phone = personDocument.getString("phone");
            double bankBalance = personDocument.getDouble("balance");

            Person person = new Person(id, name, passport, city, street, apartment, phone, bankBalance);
            return Optional.of(person);
        }
        return Optional.empty();
    }

    @Override
    public boolean create(Person entity) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> peopleCollection = database.getCollection(_collectionName);

        Document newPerson = new Document("_id", entity.getId())
                .append("name", entity.getName())
                .append("passport", entity.getPassportData())
                .append("city", entity.getCity())
                .append("street", entity.getStreet())
                .append("apartment", entity.getApartment())
                .append("balance", entity.getBankBalance());
        peopleCollection.insertOne(newPerson);
        return true;
    }

    @Override
    public Optional<List<Person>> getAll() throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> peopleCollection = database.getCollection(_collectionName);

        FindIterable<Document> peopleDocuments = peopleCollection.find();
        List<Person> people = new ArrayList<>();
        int id;
        String name, passport, city, street, apartment, phone;
        double bankBalance;
        for (Document person : peopleDocuments) {
            id = person.getInteger("_id");
            name = person.getString("name");
            passport = person.getString("passport");
            city = person.getString("city");
            street = person.getString("street");
            apartment = person.getString("apartment");
            phone = person.getString("phone");
            bankBalance = person.getDouble("balance");
            people.add(new Person(id, name, passport, city, street, apartment, phone, bankBalance));
        }
        return Optional.of(people);
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> peopleCollection = database.getCollection(_collectionName);

        Document deletedPerson = peopleCollection.findOneAndDelete(eq("_id", id));
        return deletedPerson != null;
    }

    @Override
    public boolean update(Person entity) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> peopleCollection = database.getCollection(_collectionName);

        Document updatedPerson = peopleCollection.findOneAndUpdate(eq("_id", entity.getId()),
                combine(set("name", entity.getName()), set("passport", entity.getPassportData()),
                        set("city", entity.getCity()), set("street", entity.getStreet()),
                        set("apartment", entity.getApartment()), set("phone", entity.getPhone()),
                        set("balance", entity.getBankBalance())));
        return updatedPerson != null;
    }

    @Override
    public Optional<List<Person>> getByName(String name) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> peopleCollection = database.getCollection(_collectionName);

        FindIterable<Document> peopleDocument = peopleCollection.find(new Document("name", name));
        List<Person> people = new ArrayList<>();
        for (Document personDocument : peopleDocument) {
            int id = personDocument.getInteger("_id");
            String passport = personDocument.getString("passport");
            String city = personDocument.getString("city");
            String street = personDocument.getString("street");
            String apartment = personDocument.getString("apartment");
            String phone = personDocument.getString("phone");
            double bankBalance = personDocument.getDouble("balance");

            people.add(new Person(id, name, passport, city, street, apartment, phone, bankBalance));
        }
        return Optional.of(people);
    }

}
