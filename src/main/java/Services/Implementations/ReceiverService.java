package Services.Implementations;

import DAL.Enums.StatusCode;
import DAL.Interfaces.IOrderRepository;
import DAL.Interfaces.IReceiverRepository;
import Helper.Implementation.BaseResponse;
import Services.Interfaces.Repositories.IReceiverService;

public class ReceiverService extends PersonService implements IReceiverService {

    private IReceiverRepository _receiverRepository;

    public ReceiverService(IReceiverRepository _receiverRepository) {
        super(_receiverRepository);
    }


    @Override
    public BaseResponse<Boolean> collectOrder(int idOrder, IOrderRepository orderRepository) {
        try{
            boolean collected = orderRepository.deliverOrder(idOrder);
            if(collected){
                return new BaseResponse<>(true,
                        "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(false,
                    "The order has not been collected",
                    StatusCode.errorClient);
        }catch (Exception ex){
            return new BaseResponse<>(null,"Server error", StatusCode.errorServer);
        }
    }
}
