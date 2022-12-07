package DAL.Interfaces;

public interface IReceiverRepository extends IPersonRepository {
    //TODO: just in case
    boolean collectOrder(int idOrder, IOrderRepository orderRepository) throws Exception;
}
