package Services.Interfaces.Repositories;

import DAL.Interfaces.IOrderRepository;
import Helper.Implementation.BaseResponse;

public interface ISenderService {
    BaseResponse<Boolean> cancelOrder(int idOrder, IOrderRepository orderRepository);
}
