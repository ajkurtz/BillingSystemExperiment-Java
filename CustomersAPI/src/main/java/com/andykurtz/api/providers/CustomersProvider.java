package com.andykurtz.api.providers;

import com.andykurtz.api.domain.Customer;
import com.andykurtz.api.exception.NotFoundException;
import com.andykurtz.api.exception.ServerErrorException;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CustomersProvider {

    @Autowired
    NamedParameterJdbcTemplate billingDatabaseJdbcTemplate;

    @Value("${application.encryptionPassword}")
    String encryptionPassword;


    public String create(Customer customer) {
        final String INSERT_SQL_TEMPLATE = "" +
                "INSERT INTO customers" +
                "(id, firstName, lastName, street1, street2, city, state, zip, creditCardToken, paymentDay, paymentAmount) " +
                "VALUES (:id, :firstName, :lastName, :street1, :street2, :city, :state, :zip, :creditCardToken, :paymentDay, :paymentAmount) ";

        String id = UUID.randomUUID().toString();

        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(encryptionPassword);

        String creditCardToken = textEncryptor.encrypt(customer.getCreditCardToken());

        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("id", id);
            mapSqlParameterSource.addValue("firstName", customer.getFirstName());
            mapSqlParameterSource.addValue("lastName", customer.getLastName());
            mapSqlParameterSource.addValue("street1", customer.getStreet1());
            mapSqlParameterSource.addValue("street2", customer.getStreet2());
            mapSqlParameterSource.addValue("city", customer.getCity());
            mapSqlParameterSource.addValue("state", customer.getState());
            mapSqlParameterSource.addValue("zip", customer.getZip());
            mapSqlParameterSource.addValue("creditCardToken", creditCardToken);
            mapSqlParameterSource.addValue("paymentDay", customer.getPaymentDay());
            mapSqlParameterSource.addValue("paymentAmount", customer.getPaymentAmount());

            billingDatabaseJdbcTemplate.update(
                    INSERT_SQL_TEMPLATE,
                    mapSqlParameterSource);
        } catch (Exception e) {
            throw new ServerErrorException("There was an error creating the customer");
        }

        return id;
    }

    public List<Customer> retrieveAll() {
        final String SELECT_ALL_SQL_TEMPLATE = "" +
                "SELECT " +
                "id, firstName, lastName, street1, street2, city, state, zip, creditCardToken, paymentDay, paymentAmount " +
                "FROM customers ";

        List<Customer> customers;

        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

            customers = billingDatabaseJdbcTemplate.query(
                    SELECT_ALL_SQL_TEMPLATE,
                    mapSqlParameterSource,
                    new BeanPropertyRowMapper(Customer.class));
        } catch (Exception e) {
            throw new ServerErrorException("There was an error while getting the customers");
        }

        return customers;
    }

    public Customer retrieveOne(String customerId) {
        final String SELECT_ONE_SQL_TEMPLATE = "" +
                "SELECT " +
                "id, firstName, lastName, street1, street2, city, state, zip, creditCardToken, paymentDay, paymentAmount " +
                "FROM customers " +
                "WHERE id = :customerId ";

        List<Customer> customers;

        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("customerId", customerId);

            customers = billingDatabaseJdbcTemplate.query(
                    SELECT_ONE_SQL_TEMPLATE,
                    mapSqlParameterSource,
                    new BeanPropertyRowMapper(Customer.class));
        } catch (Exception e) {
            throw new ServerErrorException("There was an error while getting the customer");
        }

        if (customers.size() < 1) {
            throw new NotFoundException("The customer was not found");
        }

        if (customers.size() > 1) {
            throw new ServerErrorException("There was an unexpected error while getting the customer");
        }

        return customers.get(0);
    }

    public void update(String customerId, Customer customer) {
        final String UPDATE_SQL_TEMPLATE = "" +
                "UPDATE customers " +
                "SET firstName = :firstName, " +
                "lastName = :lastName, " +
                "street1 = :street1, " +
                "street2 = :street2, " +
                "city = :city, " +
                "state = :state, " +
                "zip = :zip, " +
                "creditCardToken = :creditCardToken, " +
                "paymentDay = :paymentDay, " +
                "paymentAmount = :paymentAmount " +
                "WHERE id = :customerId ";

        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(encryptionPassword);

        String creditCardToken = textEncryptor.encrypt(customer.getCreditCardToken());

        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("customerId", customerId);
            mapSqlParameterSource.addValue("firstName", customer.getFirstName());
            mapSqlParameterSource.addValue("lastName", customer.getLastName());
            mapSqlParameterSource.addValue("street1", customer.getStreet1());
            mapSqlParameterSource.addValue("street2", customer.getStreet2());
            mapSqlParameterSource.addValue("city", customer.getCity());
            mapSqlParameterSource.addValue("state", customer.getState());
            mapSqlParameterSource.addValue("zip", customer.getZip());
            mapSqlParameterSource.addValue("creditCardToken", creditCardToken);
            mapSqlParameterSource.addValue("paymentDay", customer.getPaymentDay());
            mapSqlParameterSource.addValue("paymentAmount", customer.getPaymentAmount());
            billingDatabaseJdbcTemplate.update(
                    UPDATE_SQL_TEMPLATE,
                    mapSqlParameterSource);
        } catch (Exception e) {
            throw new ServerErrorException("There was an error updating the payment");
        }
    }

    public void delete(String customerId) {
        final String UPDATE_SQL_TEMPLATE = "" +
                "DELETE FROM customers " +
                "WHERE id = :customerId ";

        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("customerId", customerId);

            billingDatabaseJdbcTemplate.update(
                    UPDATE_SQL_TEMPLATE,
                    mapSqlParameterSource);
        } catch (Exception e) {
            throw new ServerErrorException("There was an error deleting the customer");
        }
    }
}
