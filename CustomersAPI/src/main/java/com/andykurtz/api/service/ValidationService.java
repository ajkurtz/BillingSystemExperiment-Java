package com.andykurtz.api.service;

import com.andykurtz.api.domain.Customer;
import com.andykurtz.api.domain.CustomerRequest;
import com.andykurtz.api.exception.BadRequestException;
import com.andykurtz.api.providers.CustomersProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    @Autowired
    CustomersProvider customersProvider;

    public void validateCustomerRequest(CustomerRequest customerRequest) {
        if (customerRequest.getCustomer() == null) {
            throw new BadRequestException("A customer is required");
        }

        Customer customer = customerRequest.getCustomer();

        if (StringUtils.isBlank(customer.getFirstName())) {
            throw new BadRequestException("A first name is required");
        }

        if (StringUtils.isBlank(customer.getLastName())) {
            throw new BadRequestException("A last name is required");
        }

        if (StringUtils.isBlank(customer.getStreet1())) {
            throw new BadRequestException("A street is required");
        }

        if (StringUtils.isBlank(customer.getCity())) {
            throw new BadRequestException("A city is required");
        }

        if (StringUtils.isBlank(customer.getState())) {
            throw new BadRequestException("A state is required");
        }

        if (StringUtils.isBlank(customer.getZip())) {
            throw new BadRequestException("A zip is required");
        }

        if (StringUtils.isBlank(customer.getCreditCardToken())) {
            throw new BadRequestException("A credit card token is required");
        }
        if (customer.getPaymentDay() == null) {
            throw new BadRequestException("A payment day is required");
        }

        if (customer.getPaymentAmount() == null) {
            throw new BadRequestException("A payment amount is required");
        }
    }

    public void validateCustomerExists(String customerId) {
        customersProvider.retrieveOne(customerId);
    }
}
