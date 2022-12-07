package DAL.Interfaces;

import DAL.Models.Order;

public interface IOrderRepository extends IBaseRepositoryDA<Order>{
    boolean deliverOrder(int idOrder) throws Exception;
}
