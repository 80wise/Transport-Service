package Services.Interfaces.Repositories;

import DAL.Models.Payment;
import DAL.Models.Person;
import DAL.Models.Price;
import Helper.Implementation.BaseResponse;
import Services.IBaseService;

import java.util.List;

public interface IPaymentService extends IBaseService<Payment>, IDateService {
    BaseResponse<Boolean> pay(Payment entity, Person _payer, List<Price> priceList);
}
