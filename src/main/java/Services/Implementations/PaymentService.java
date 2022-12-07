package Services.Implementations;

import DAL.Enums.StatusCode;
import DAL.Interfaces.IPaymentRepository;
import DAL.Models.Payment;
import DAL.Models.Person;
import DAL.Models.Price;
import Helper.Implementation.BaseResponse;
import Services.Interfaces.Repositories.IPaymentService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class PaymentService implements IPaymentService {
    private final IPaymentRepository _paymentRepository;

    public PaymentService(IPaymentRepository _paymentRepository) {
        this._paymentRepository = _paymentRepository;
    }

    @Override
    public BaseResponse<Optional<Payment>> getById(int id) {
        try {
            Optional<Payment> payment = _paymentRepository.getById(id);
            if (payment.isPresent()) {
                return new BaseResponse<>(payment,
                        "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(Optional.empty(),
                    "There is no payment with this id",
                    StatusCode.errorClient);
        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> create(Payment entity) {
        try {
            boolean created = _paymentRepository.create(entity);
            if (created) {
                return new BaseResponse<>(true,
                        "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(false,
                    "The payment  has not been created",
                    StatusCode.errorClient);
        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Optional<List<Payment>>> getAll() {
        try {
            Optional<List<Payment>> payments = _paymentRepository.getAll();
            if (payments.isPresent()) {
                return new BaseResponse<>(payments,
                        "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(Optional.empty(),
                    "There is no payment",
                    StatusCode.errorClient);
        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> deleteById(int id) {
        try {
            boolean deleted = _paymentRepository.deleteById(id);
            if (deleted) {
                return new BaseResponse<>(true,
                        "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(false,
                    "The payment has not been deleted",
                    StatusCode.errorClient);
        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> update(Payment entity) {
        try {
            boolean updated = _paymentRepository.update(entity);
            if (updated) {
                return new BaseResponse<>(true, "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(false, "The payment has not been updated", StatusCode.errorClient);
        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> setDate(int idEntity, Date date) {
        try {
            boolean updated = _paymentRepository.setDate(idEntity, date);
            if (updated) {
                return new BaseResponse<>(true,
                        "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(false,
                    "The payment has not been updated",
                    StatusCode.errorClient);
        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }


    @Override
    public BaseResponse<Boolean> pay(Payment entity, Person _payer, List<Price> priceList) {
        try {
            boolean paid = _paymentRepository.pay(entity, _payer, priceList);
            if (paid) {
                return new BaseResponse<>(true,"Successfully paid", StatusCode.success);
            }
            return new BaseResponse<>(false,"The payment has not been done",
                    StatusCode.errorClient);
        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }
}
