package DAL.Repositories.MsSQL;

import DAL.Interfaces.IOrderRepository;
import DAL.Interfaces.IReceiverRepository;

public class ReceiverRepositoryMs extends PersonRepositoryMs implements IReceiverRepository {

    public ReceiverRepositoryMs(String _connectionString, String _tableName) {
        super(_connectionString, _tableName);
    }

    @Override
    public boolean collectOrder(int idOrder, IOrderRepository orderRepository) throws Exception {
        return orderRepository.deliverOrder(idOrder);
    }
}
