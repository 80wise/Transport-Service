package Services.Implementations;

import DAL.Enums.StatusCode;
import DAL.Interfaces.IOrderRepository;
import DAL.Interfaces.ISenderRepository;
import Helper.Implementation.BaseResponse;
import Services.Interfaces.Repositories.ISenderService;

public class SenderService extends PersonService implements ISenderService {

    private ISenderRepository _senderRepository;

    public SenderService(ISenderRepository _senderRepository) {
        super(_senderRepository);
    }

    @Override
    public BaseResponse<Boolean> cancelOrder(int idOrder, IOrderRepository orderRepository) {
        try{
            boolean cancelled = orderRepository.deleteById(idOrder);
            if(cancelled){
                return new BaseResponse<>(true,
                        "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(false,
                    "The order has not been cancelled",
                    StatusCode.errorClient);
        }catch (Exception ex){
            return new BaseResponse<>(null,"Server error", StatusCode.errorServer);
        }
    }
}
