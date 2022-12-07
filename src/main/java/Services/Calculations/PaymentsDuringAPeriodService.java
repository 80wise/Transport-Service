package Services.Calculations;

import DAL.Enums.StatusCode;
import DAL.Interfaces.IPaymentRepository;
import DAL.Models.Payment;
import Helper.Implementation.BaseResponse;
import Services.Interfaces.Calculations.IPaymentsDuringAPeriod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PaymentsDuringAPeriodService implements IPaymentsDuringAPeriod {
    private final IPaymentRepository _paymentRepository;

    public PaymentsDuringAPeriodService(IPaymentRepository paymentRepository) {
        this._paymentRepository = paymentRepository;
    }

    @Override
    public BaseResponse<Optional<List<Payment>>> getPaymentsDuringAPeriod(Date from, Date to) {
        try {
            Optional<List<Payment>> allPayments = _paymentRepository.getAll();
            Optional<List<Payment>> requiredPayments = Optional.of(new ArrayList<>());
            if (allPayments.isPresent()) {
                for (Payment payment : allPayments.get()) {
                    if (payment.getDate().compareTo(from) >= 0 && payment.getDate().compareTo(to) <= 0) {
                        requiredPayments.get().add(payment);
                    }
                }
                return new BaseResponse<>(requiredPayments, "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(Optional.empty(), "There is no payment", StatusCode.emptyResource);
        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }
}
