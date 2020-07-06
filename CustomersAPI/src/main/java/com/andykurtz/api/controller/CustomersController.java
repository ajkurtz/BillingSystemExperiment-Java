package com.andykurtz.api.controller;


import com.andykurtz.api.domain.CustomerRequest;
import com.andykurtz.api.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomersController {

    @Autowired
    private CustomersService customersService;

    @PostMapping("/customers")
    public ResponseEntity<?> create(@RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.created(null).body(customersService.create(customerRequest));
    }

    @GetMapping("/customers")
    public ResponseEntity<?> retrieveAll() {
        return ResponseEntity.ok(customersService.retrieveAll());
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<?> retrieveOne(@PathVariable String customerId) {
        return ResponseEntity.ok(customersService.retrieveOne(customerId));
    }

    @PutMapping("/customers/{customerId}")
    public ResponseEntity<?> update(@PathVariable String customerId,
                                    @RequestBody CustomerRequest customerRequest) {
        customersService.update(customerId, customerRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<?> delete(@PathVariable String customerId) {
        customersService.delete(customerId);
        return ResponseEntity.noContent().build();
    }

}
