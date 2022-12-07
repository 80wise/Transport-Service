package Services.Implementations;

import DAL.Enums.StatusCode;
import DAL.Interfaces.IOrderRepository;
import DAL.Models.Order;
import Helper.Implementation.BaseResponse;
import Services.Interfaces.Repositories.IOrderService;

import java.util.List;
import java.util.Optional;

public class OrderService implements IOrderService {
    private final IOrderRepository _orderRepository;

    public OrderService(IOrderRepository _orderRepository){
        this._orderRepository = _orderRepository;
    }

    @Override
    public BaseResponse<Optional<Order>> getById(int id) {
        try{
            Optional<Order> order = _orderRepository.getById(id);
            if(order.isPresent()){
                return new BaseResponse<>(order,"Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(Optional.empty(),
                    "There is no order with this id",
                    StatusCode.errorClient);
        }catch (Exception ex){
            return new BaseResponse<>(null,"Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> create(Order entity) {
        try{
            boolean created = _orderRepository.create(entity);
            if(created){
                return new BaseResponse<>(true,"The order have been created",StatusCode.success);
            }
            return new BaseResponse<>(false,"The order was not created",StatusCode.errorClient);
        }catch ( Exception ex){
            return new BaseResponse<>(null,"Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Optional<List<Order>>> getAll() {
        try{
            Optional<List<Order>> orders = _orderRepository.getAll();
            if(orders.isPresent()){
                return new BaseResponse<>(orders,"Successfully processed",StatusCode.success);
            }
            return new BaseResponse<>(Optional.empty(),"There is no order",StatusCode.emptyResource);
        }catch ( Exception ex){
            return new BaseResponse<>(null,"Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> deleteById(int id) {
        try{
            boolean deleted = _orderRepository.deleteById(id);
            if(deleted){
                return new BaseResponse<>(true,"The order have been deleted",StatusCode.success);
            }
            return new BaseResponse<>(false,"The order was not deleted",
                    StatusCode.errorClient);
        }catch ( Exception ex){
            return new BaseResponse<>(null,"Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> update(Order entity) {
        try{
            boolean updated = _orderRepository.update(entity);
            if(updated){
                return new BaseResponse<>(true,"The order has been updated",StatusCode.success);
            }
            return new BaseResponse<>(false, "the order was not updated",
                    StatusCode.errorClient);

        }catch ( Exception ex){
            return new BaseResponse<>(null,"Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> deliverOrder(int idOrder) {
        try{
            boolean delivered = _orderRepository.deliverOrder(idOrder);
            if(delivered){
                return new BaseResponse<>(true,"The order has been delivered",StatusCode.success);
            }
            return new BaseResponse<>(false, "the order was not delivered",
                    StatusCode.errorClient);

        }catch ( Exception ex){
            return new BaseResponse<>(null,"Server error", StatusCode.errorServer);
        }
    }
}
