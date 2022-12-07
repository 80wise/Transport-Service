package DAL.Repositories.MsSQL;

import DAL.Interfaces.IOrderRepository;
import DAL.Interfaces.ISenderRepository;

import java.sql.SQLException;

public class SenderRepositoryMs extends PersonRepositoryMs implements ISenderRepository {

    public SenderRepositoryMs(String _connectionString, String _tableName) throws SQLException {
        super(_connectionString, _tableName);
    }

    @Override
    public boolean cancelOrder(int idOrder, IOrderRepository orderRepository) throws Exception {
        return orderRepository.deleteById(idOrder);
    }
}
