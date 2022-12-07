package DAL.Interfaces;

import DAL.Models.Payment;
import DAL.Models.Person;
import DAL.Models.Price;

import java.util.List;

public interface IPaymentRepository extends IBaseRepositoryDA<Payment>, IDate{
    boolean pay(Payment entity, Person _payer, List<Price> priceList) throws Exception;
}
