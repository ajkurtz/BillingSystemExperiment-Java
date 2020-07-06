package com.andykurtz.api.service;

import com.andykurtz.api.domain.Customer;
import com.andykurtz.api.providers.CustomersProvider;
import com.andykurtz.api.providers.QueueProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SchedulingService {

    @Autowired
    QueueProvider queueProvider;
    @Autowired
    private CustomersProvider customersProvider;

    public void schedule() {

        LocalDate now = LocalDate.now();
        int dayOfMonth = now.getDayOfMonth();

        List<Customer> customerIds = customersProvider.retrieveToBillCustomers(dayOfMonth);
        for (Customer customerId : customerIds) {
            queueProvider.sendMessage(customerId.getId());
        }

    }

}
