package com.andykurtz.api.service;

import com.andykurtz.api.domain.PaymentRequest;
import com.andykurtz.api.exception.BadRequestException;
import com.andykurtz.api.providers.CustomersProvider;
import com.andykurtz.api.providers.PaymentsProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    @Autowired
    PaymentsProvider paymentsProvider;

    @Autowired
    CustomersProvider customersProvider;

    public void validateCustomerExists(String customerId) {
        customersProvider.validateCustomerExists(customerId);
    }

    public void validatePaymentRequest(PaymentRequest paymentRequest) {
        if (paymentRequest.getPayment() == null) {
            throw new BadRequestException("A payment is required");
        }

        if (StringUtils.isBlank(paymentRequest.getPayment().getDate())) {
            throw new BadRequestException("A payment date is required");
        }

        if (paymentRequest.getPayment().getAmount() == null) {
            throw new BadRequestException("A payment amount is required");
        }

        if (paymentRequest.getPayment().getCreditCardToken() == null) {
            throw new BadRequestException("A payment credit card token is required");
        }

    }

    public void validatePaymentExists(String customerId, String paymentId) {
        paymentsProvider.retrieveOne(customerId, paymentId);
    }
}
