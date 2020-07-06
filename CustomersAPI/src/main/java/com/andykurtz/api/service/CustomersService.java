package com.andykurtz.api.service;

import com.andykurtz.api.domain.Customer;
import com.andykurtz.api.domain.CustomerRequest;
import com.andykurtz.api.domain.CustomerResponse;
import com.andykurtz.api.domain.CustomersResponse;
import com.andykurtz.api.providers.CustomersProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomersService {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private CustomersProvider customersProvider;

    public CustomerResponse create(CustomerRequest customerRequest) {

        validationService.validateCustomerRequest(customerRequest);

        Customer customer = new Customer();
        customer.setId(customersProvider.create(customerRequest.getCustomer()));

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomer(customer);

        return customerResponse;
    }

    public CustomersResponse retrieveAll() {

        CustomersResponse customersResponse = new CustomersResponse();
        customersResponse.setCustomers(customersProvider.retrieveAll());

        return customersResponse;
    }

    public CustomerResponse retrieveOne(String customerId) {

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomer(customersProvider.retrieveOne(customerId));

        return customerResponse;
    }

    public void update(String customerId, CustomerRequest customerRequest) {
        validationService.validateCustomerRequest(customerRequest);
        validationService.validateCustomerExists(customerId);
        customersProvider.update(customerId, customerRequest.getCustomer());
    }

    public void delete(String customerId) {
        validationService.validateCustomerExists(customerId);
        customersProvider.delete(customerId);
    }
}
