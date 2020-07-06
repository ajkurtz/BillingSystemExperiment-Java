package com.andykurtz.api.providers;

import com.andykurtz.api.domain.Customer;
import com.andykurtz.api.domain.CustomerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomersProvider {

    @Value("${application.customersApiUrl}")
    String customersApiUrl;

    public Customer retrieve(String customerId) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = customersApiUrl + "/" + customerId;

        ResponseEntity<CustomerResponse> response = null;
        try {
            response = restTemplate.getForEntity(resourceUrl, CustomerResponse.class);
        } catch (RestClientException e) {
            return null;
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            return null;
        }

        return response.getBody().getCustomer();
    }

}
