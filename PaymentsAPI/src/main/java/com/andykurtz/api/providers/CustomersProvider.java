package com.andykurtz.api.providers;

import com.andykurtz.api.exception.ExceptionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomersProvider {

    @Value("${application.customersApiUrl}")
    String customersApiUrl;

    public void validateCustomerExists(String customerId) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = customersApiUrl + "/" + customerId;

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.getForEntity(resourceUrl, String.class);
        } catch (RestClientException e) {
            if (e instanceof HttpStatusCodeException) {
                HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) e;
                ExceptionFactory.throwExceptionForStatus(httpStatusCodeException.getStatusCode(), "Could not get the customer");
            } else {
                throw e;
            }
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            ExceptionFactory.throwExceptionForStatus(response.getStatusCode(), "Could not get the customer");
        }
    }

}
