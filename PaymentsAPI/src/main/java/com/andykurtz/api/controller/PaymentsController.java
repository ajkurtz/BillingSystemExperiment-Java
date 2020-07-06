package com.andykurtz.api.controller;


import com.andykurtz.api.domain.PaymentRequest;
import com.andykurtz.api.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    @PostMapping("/{customerId}/payments")
    public ResponseEntity<?> create(@PathVariable String customerId,
                                    @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.created(null).body(paymentsService.create(customerId, paymentRequest));
    }

    @GetMapping("/{customerId}/payments")
    public ResponseEntity<?> retrieveAll(@PathVariable String customerId) {
        return ResponseEntity.ok(paymentsService.retrieveAll(customerId));
    }

    @GetMapping("/{customerId}/payments/{paymentId}")
    public ResponseEntity<?> retrieveOne(@PathVariable String customerId,
                                         @PathVariable String paymentId) {
        return ResponseEntity.ok(paymentsService.retrieveOne(customerId, paymentId));
    }

    @PutMapping("/{customerId}/payments/{paymentId}")
    public ResponseEntity<?> update(@PathVariable String customerId,
                                    @PathVariable String paymentId,
                                    @RequestBody PaymentRequest paymentRequest) {
        paymentsService.update(customerId, paymentId, paymentRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}/payments/{paymentId}")
    public ResponseEntity<?> delete(@PathVariable String customerId,
                                    @PathVariable String paymentId) {
        paymentsService.delete(customerId, paymentId);
        return ResponseEntity.noContent().build();
    }

}
