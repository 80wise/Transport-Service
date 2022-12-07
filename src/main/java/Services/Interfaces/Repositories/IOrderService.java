package Services.Interfaces.Repositories;

import DAL.Models.Order;
import Helper.Implementation.BaseResponse;
import Services.IBaseService;

public interface IOrderService extends IBaseService<Order> {
    BaseResponse<Boolean> deliverOrder(int idOrder);
}
