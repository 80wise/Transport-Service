package Integreted;

import DAL.Enums.StatusCode;
import DAL.Interfaces.IPaymentRepository;
import DAL.Models.Payment;
import DAL.Models.Person;
import DAL.Models.Price;
import DAL.Models.Sender;
import DAL.Repositories.MsSQL.PaymentRepositoryMs;
import Helper.Implementation.BaseResponse;
import Services.Implementations.PaymentService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseResponsesPaymentServiceTest {
    private IPaymentRepository paymentRepository;
    private PaymentService payment;
    private BaseResponse<Boolean> response;

    @Test
    public void paySuccessfully() {
        try {
            Person sender = getAPerson();
            Payment newPayment = getPayment();
            List<Price> priceList = getAllPrices();

            paymentRepository = mock(PaymentRepositoryMs.class);
            when(paymentRepository.pay(newPayment, sender, priceList)).thenReturn(true);
            payment = new PaymentService(paymentRepository);
            response = payment.pay(newPayment, sender, priceList);

            assertTrue(response.getResultQuery());
            assertEquals(response.getDescription(), "Successfully paid");
            assertEquals(response.getStatusCode(), StatusCode.success);

        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void payUnsuccessfullyThrowsSQLException() {
        try {
            Person sender = getAPerson();
            Payment newPayment = getPayment();
            List<Price> priceList = getAllPrices();

            paymentRepository = mock(PaymentRepositoryMs.class);
            when(paymentRepository.pay(newPayment, sender, priceList)).thenThrow(new SQLException());
            payment = new PaymentService(paymentRepository);
            response = payment.pay(newPayment, sender, priceList);


            assertNull(response.getResultQuery());
            assertEquals(response.getDescription(), "Server error");
            assertEquals(response.getStatusCode(), StatusCode.errorServer);

        } catch (Exception exception) {
            fail();
        }
    }

    private Person getAPerson() {
        return new Sender();
    }

    private Payment getPayment() {
        return new Payment();
    }

    private List<Price> getAllPrices() {
        return Collections.emptyList();
    }
}
