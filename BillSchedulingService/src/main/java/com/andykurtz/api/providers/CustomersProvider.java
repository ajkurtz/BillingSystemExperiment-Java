package com.andykurtz.api.providers;

import com.andykurtz.api.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomersProvider {

    @Autowired
    NamedParameterJdbcTemplate billingDatabaseJdbcTemplate;

    public List<Customer> retrieveToBillCustomers(int paymentDay) {
        final String SELECT_TO_BILL_SQL_TEMPLATE = "" +
                "SELECT " +
                "id " +
                "FROM customers " +
                "WHERE paymentDay = :paymentDay ";

        List<Customer> customerIds;

        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("paymentDay", paymentDay);

            customerIds = billingDatabaseJdbcTemplate.query(
                    SELECT_TO_BILL_SQL_TEMPLATE,
                    mapSqlParameterSource,
                    new BeanPropertyRowMapper(Customer.class));
        } catch (Exception e) {
            throw new RuntimeException("There was an error while getting the customerIds");
        }

        return customerIds;
    }

}
