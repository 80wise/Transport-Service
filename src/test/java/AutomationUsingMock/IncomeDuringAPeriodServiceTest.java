package AutomationUsingMock;

import DAL.Models.Payment;
import DAL.Repositories.MsSQL.PaymentRepositoryMs;
import Helper.Implementation.BaseResponse;
import Services.Calculations.IncomeDuringAPeriodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IncomeDuringAPeriodServiceTest {
    private PaymentRepositoryMs paymentRepository;
    private IncomeDuringAPeriodService incomeDuringAPeriod;
    private Optional<List<Payment>> payments;

    @BeforeEach
    public void setup(){
        paymentRepository = mock(PaymentRepositoryMs.class);
    }
    @Test
    public void incomeDuringAPeriodEquals65() throws Exception {
        payments = getAllPayments();
        when(paymentRepository.getAll()).thenReturn(payments);
        incomeDuringAPeriod = new IncomeDuringAPeriodService(paymentRepository);

        BaseResponse<Double> income = incomeDuringAPeriod.getIncomeDuringAPeriod(
                new Date(122, Calendar.FEBRUARY,11),
                new Date(122, Calendar.DECEMBER,3));

        assertEquals(income.getResultQuery(),65);
    }

    @Test
    public void incomeDuringAPeriodEqualsZero() throws Exception {
        payments = Optional.empty();
        when(paymentRepository.getAll()).thenReturn(payments);
        incomeDuringAPeriod = new IncomeDuringAPeriodService(paymentRepository);

        BaseResponse<Double> income = incomeDuringAPeriod.getIncomeDuringAPeriod(
                new Date(122, Calendar.FEBRUARY,11),
                new Date(122,11,3));

        assertEquals(income.getResultQuery(),0);
    }

    public Optional<List<Payment>> getAllPayments(){
        return Optional.of(asList(
                new Payment(new Date(122, Calendar.NOVEMBER,20),45),
                new Payment(new Date(112, Calendar.OCTOBER,20),50),
                new Payment(new Date(122, Calendar.MARCH,16),20),
                new Payment(new Date(120, Calendar.NOVEMBER,20),100))
        );
    }

}
