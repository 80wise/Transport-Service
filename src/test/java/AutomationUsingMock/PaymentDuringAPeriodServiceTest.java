package AutomationUsingMock;

import DAL.Models.Payment;
import DAL.Repositories.MsSQL.PaymentRepositoryMs;
import Helper.Implementation.BaseResponse;
import Services.Calculations.IncomeDuringAPeriodService;
import Services.Calculations.PaymentsDuringAPeriodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentDuringAPeriodServiceTest {
    private PaymentRepositoryMs paymentRepository;
    private PaymentsDuringAPeriodService paymentsDuringAPeriod;
    private Optional<List<Payment>> payments;

    @BeforeEach
    public void setup() {
        paymentRepository = mock(PaymentRepositoryMs.class);
    }

    @Test
    public void paymentsDuringAPeriodEqualsTwo() throws Exception {
        payments = getAllPayments();
        when(paymentRepository.getAll()).thenReturn(payments);
        paymentsDuringAPeriod = new PaymentsDuringAPeriodService(paymentRepository);

        BaseResponse<Optional<List<Payment>>> paymentsOfACertainPeriod =
                paymentsDuringAPeriod.getPaymentsDuringAPeriod(new Date(122, Calendar.FEBRUARY, 11),
                        new Date(122, Calendar.DECEMBER, 3));

        assert(paymentsOfACertainPeriod.getResultQuery().isPresent());
        assertEquals(paymentsOfACertainPeriod.getResultQuery().get().size(), 2);
    }

    @Test
    public void paymentDuringAPeriodNegativeTestFalse() throws Exception {
        payments = getAllPayments();
        when(paymentRepository.getAll()).thenReturn(payments);
        paymentsDuringAPeriod = new PaymentsDuringAPeriodService(paymentRepository);

        BaseResponse<Optional<List<Payment>>> paymentsOfACertainPeriod =
                paymentsDuringAPeriod.getPaymentsDuringAPeriod(new Date(111, Calendar.FEBRUARY, 11),
                        new Date(115, Calendar.DECEMBER, 3));

        assert(paymentsOfACertainPeriod.getResultQuery().isPresent());
        assertNotEquals(paymentsOfACertainPeriod.getResultQuery().get().size(), 1);
    }

    public Optional<List<Payment>> getAllPayments() {
        return Optional.of(asList(
                new Payment(new Date(122, Calendar.NOVEMBER, 20), 45),
                new Payment(new Date(116, Calendar.OCTOBER, 20), 50),
                new Payment(new Date(122, Calendar.MARCH, 16), 20),
                new Payment(new Date(120, Calendar.NOVEMBER, 20), 100))
        );
    }
}
