package com.andykurtz.api.providers;

import com.andykurtz.api.domain.Customer;
import com.andykurtz.api.domain.Payment;
import com.andykurtz.api.domain.PaymentRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Component
public class PaymentsProvider {

    @Value("${application.paymentsApiUrl}")
    String paymentsApiUrl;

    public boolean submitPayment(Customer customer) {

        Payment payment = new Payment();
        payment.setDate(LocalDate.now().toString());
        payment.setAmount(customer.getPaymentAmount());
        payment.setCreditCardToken(customer.getCreditCardToken());

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPayment(payment);

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = paymentsApiUrl + "/" + customer.getId() + "/payments";

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.postForEntity(resourceUrl, paymentRequest, String.class);
        } catch (RestClientException e) {
            return false;
        }

        return response.getStatusCode() == HttpStatus.CREATED;
    }

}
