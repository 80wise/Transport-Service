package Services.Interfaces.Repositories;

import DAL.Interfaces.IOrderRepository;
import Helper.Implementation.BaseResponse;

public interface IReceiverService {
    BaseResponse<Boolean> collectOrder(int idOrder, IOrderRepository orderRepository);
}
