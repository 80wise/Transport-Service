package DAL.Repositories.mongoDB;

import DAL.Interfaces.IOrderRepository;
import DAL.Interfaces.ISenderRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class SenderRepositoryMdb extends PersonRepositoryMdb implements ISenderRepository {

    public SenderRepositoryMdb(String connectionString, String dbName, String collectionName) {
        super(connectionString, dbName, collectionName);
    }

    public SenderRepositoryMdb(String connectionString, String dbName, String collectionName,
                               IOrderRepository _orderRepository) {
        super(connectionString, dbName, collectionName, _orderRepository);
    }

    @Override
    public boolean changeBankBalance(int id, double amount) throws Exception {
        MongoClient client = MongoClients.create(_connectionString);
        MongoDatabase database = client.getDatabase(_dbName);
        MongoCollection<Document> peopleCollection = database.getCollection(_collectionName);

        //TODO: improve this part to set back the balance in case of unsuccessful change;
        if(_orderRepository instanceof OrderRepositoryMdb){
            Document updatedPerson = peopleCollection.findOneAndUpdate(eq("_id", id),
                    set("balance", amount));
            return ((OrderRepositoryMdb) _orderRepository).applyChangesOfBalance(id,amount, "sender")
                    && updatedPerson != null;
        }
        return false;
    }

    @Override
    public boolean cancelOrder(int idOrder, IOrderRepository orderRepository) throws Exception {
        return orderRepository.deleteById(idOrder);
    }
}
