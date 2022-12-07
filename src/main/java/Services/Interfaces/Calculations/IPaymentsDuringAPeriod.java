package Services.Interfaces.Calculations;

import DAL.Models.Payment;
import Helper.Implementation.BaseResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPaymentsDuringAPeriod {
    BaseResponse<Optional<List<Payment>>> getPaymentsDuringAPeriod(Date from, Date to);
}
