package DAL.Interfaces;

public interface ISenderRepository extends IPersonRepository {
    boolean cancelOrder(int idOrder, IOrderRepository orderRepository) throws Exception;
}
