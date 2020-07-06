package com.andykurtz.api.providers;

import com.andykurtz.api.domain.Payment;
import com.andykurtz.api.exception.NotFoundException;
import com.andykurtz.api.exception.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PaymentsProvider {

    @Autowired
    NamedParameterJdbcTemplate billingDatabaseJdbcTemplate;

    public String create(String customerId, Payment payment) {
        final String INSERT_SQL_TEMPLATE = "" +
                "INSERT INTO payments" +
                "(id, customerId, date, amount) " +
                "VALUES (:id, :customerId, :date, :amount) ";
        final String GET_ID_SQL = "SELECT last_insert_rowid()";

        String id = UUID.randomUUID().toString();

        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("id", id);
            mapSqlParameterSource.addValue("customerId", customerId);
            mapSqlParameterSource.addValue("date", payment.getDate());
            mapSqlParameterSource.addValue("amount", payment.getAmount());

            billingDatabaseJdbcTemplate.update(
                    INSERT_SQL_TEMPLATE,
                    mapSqlParameterSource);
        } catch (Exception e) {
            throw new ServerErrorException("There was an exception while creating the payment");
        }

        return id;
    }

    public List<Payment> retrieveAll(String customerId) {
        final String SELECT_ALL_SQL_TEMPLATE = "" +
                "SELECT " +
                "id, " +
                "date, " +
                "amount " +
                "FROM payments " +
                "WHERE customerId = :customerId ";

        List<Payment> payments;

        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("customerId", customerId);

            payments = billingDatabaseJdbcTemplate.query(
                    SELECT_ALL_SQL_TEMPLATE,
                    mapSqlParameterSource,
                    new BeanPropertyRowMapper(Payment.class));
        } catch (Exception e) {
            throw new ServerErrorException("There was an exception while getting the payments");
        }

        return payments;
    }

    public Payment retrieveOne(String customerId, String paymentId) {
        final String SELECT_ONE_SQL_TEMPLATE = "" +
                "SELECT " +
                "id, " +
                "date, " +
                "amount " +
                "FROM payments " +
                "WHERE customerId = :customerId " +
                "AND id = :paymentId ";

        List<Payment> payments;

        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("customerId", customerId);
            mapSqlParameterSource.addValue("paymentId", paymentId);

            payments = billingDatabaseJdbcTemplate.query(
                    SELECT_ONE_SQL_TEMPLATE,
                    mapSqlParameterSource,
                    new BeanPropertyRowMapper(Payment.class));
        } catch (Exception e) {
            throw new ServerErrorException("There was an exception while getting the payment");
        }

        if (payments.size() < 1) {
            throw new NotFoundException("The payment was not found");
        }

        if (payments.size() > 1) {
            throw new ServerErrorException("There was an unexpected error while getting the payment");
        }

        return payments.get(0);
    }

    public void update(String customerId, String paymentId, Payment payment) {
        final String UPDATE_SQL_TEMPLATE = "" +
                "UPDATE payments " +
                "SET date = :date, amount = :amount " +
                "WHERE customerId = :customerId " +
                "AND id = :paymentId ";

        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("customerId", customerId);
            mapSqlParameterSource.addValue("paymentId", paymentId);
            mapSqlParameterSource.addValue("date", payment.getDate());
            mapSqlParameterSource.addValue("amount", payment.getAmount());

            billingDatabaseJdbcTemplate.update(
                    UPDATE_SQL_TEMPLATE,
                    mapSqlParameterSource);
        } catch (Exception e) {
            throw new ServerErrorException("There was an exception while updating the payment");
        }
    }

    public void delete(String customerId, String paymentId) {
        final String UPDATE_SQL_TEMPLATE = "" +
                "DELETE FROM payments " +
                "WHERE customerId = :customerId " +
                "AND id = :paymentId ";

        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("customerId", customerId);
            mapSqlParameterSource.addValue("paymentId", paymentId);

            billingDatabaseJdbcTemplate.update(
                    UPDATE_SQL_TEMPLATE,
                    mapSqlParameterSource);
        } catch (Exception e) {
            throw new ServerErrorException("There was an exception while deleting the payment");
        }
    }
}
