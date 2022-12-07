package Services.Calculations;

import DAL.Enums.StatusCode;
import DAL.Interfaces.IPaymentRepository;
import DAL.Models.Payment;
import Helper.Implementation.BaseResponse;
import Services.Interfaces.Calculations.IIncomeDuringAPeriod;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class IncomeDuringAPeriodService implements IIncomeDuringAPeriod {
    private final IPaymentRepository _paymentRepository;

    public IncomeDuringAPeriodService(IPaymentRepository paymentRepository) {
        this._paymentRepository = paymentRepository;
    }

    @Override
    public BaseResponse<Double> getIncomeDuringAPeriod(Date from, Date to) {
        try {
            Optional<List<Payment>> allPayments = _paymentRepository.getAll();
            double income = 0;
            if (allPayments.isPresent()) {
                for (Payment payment : allPayments.get()) {
                    if (payment.getDate().compareTo(from) >= 0 && payment.getDate().compareTo(to) <= 0) {
                        income += payment.getAmount();
                    }
                }
                return new BaseResponse<>(income, "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(0.0, "There is no income", StatusCode.emptyResource);
        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }
}
