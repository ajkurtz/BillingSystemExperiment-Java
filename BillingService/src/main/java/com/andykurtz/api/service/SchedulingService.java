package com.andykurtz.api.service;

import com.amazonaws.services.sqs.model.Message;
import com.andykurtz.api.domain.Customer;
import com.andykurtz.api.providers.CustomersProvider;
import com.andykurtz.api.providers.PaymentsProvider;
import com.andykurtz.api.providers.QueueProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulingService {

    @Autowired
    QueueProvider queueProvider;
    @Autowired
    private CustomersProvider customersProvider;
    @Autowired
    private PaymentsProvider paymentsProvider;

    public void schedule() {

        Message message = queueProvider.receiveMessage();
        while (message != null) {
            String customerId = message.getBody();
            Customer customer = customersProvider.retrieve(customerId);
            if (customer != null) {
                if (paymentsProvider.submitPayment(customer)) {
                    queueProvider.deleteMessage(message);
                }
            }

            message = queueProvider.receiveMessage();
        }

    }

}
