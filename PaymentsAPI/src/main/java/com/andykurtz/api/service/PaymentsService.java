package com.andykurtz.api.service;

import com.andykurtz.api.domain.Payment;
import com.andykurtz.api.domain.PaymentRequest;
import com.andykurtz.api.domain.PaymentResponse;
import com.andykurtz.api.domain.PaymentsResponse;
import com.andykurtz.api.providers.PaymentsProvider;
import com.andykurtz.api.providers.StripeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentsService {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private StripeProvider stripeProvider;

    @Autowired
    private PaymentsProvider paymentsProvider;

    public PaymentResponse create(String customerId, PaymentRequest paymentRequest) {

        validationService.validatePaymentRequest(paymentRequest);

        validationService.validateCustomerExists(customerId);

        stripeProvider.charge(paymentRequest.getPayment());

        Payment payment = new Payment();
        payment.setId(paymentsProvider.create(customerId, paymentRequest.getPayment()));

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPayment(payment);

        return paymentResponse;
    }

    public PaymentsResponse retrieveAll(String customerId) {

        validationService.validateCustomerExists(customerId);

        PaymentsResponse paymentsResponse = new PaymentsResponse();
        paymentsResponse.setPayments(paymentsProvider.retrieveAll(customerId));

        return paymentsResponse;
    }

    public PaymentResponse retrieveOne(String customerId, String paymentId) {

        validationService.validateCustomerExists(customerId);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPayment(paymentsProvider.retrieveOne(customerId, paymentId));

        return paymentResponse;
    }

    public void update(String customerId, String paymentId, PaymentRequest paymentRequest) {

        validationService.validateCustomerExists(customerId);

        validationService.validatePaymentRequest(paymentRequest);

        validationService.validatePaymentExists(customerId, paymentId);

        paymentsProvider.update(customerId, paymentId, paymentRequest.getPayment());
    }

    public void delete(String customerId, String paymentId) {

        validationService.validateCustomerExists(customerId);

        validationService.validatePaymentExists(customerId, paymentId);

        paymentsProvider.delete(customerId, paymentId);
    }
}
