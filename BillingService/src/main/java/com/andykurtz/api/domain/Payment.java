package com.andykurtz.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Payment {

    String id;
    String date;
    Double amount;
    String creditCardToken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCreditCardToken() {
        return creditCardToken;
    }

    public void setCreditCardToken(String creditCardToken) {
        this.creditCardToken = creditCardToken;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", creditCardToken='" + creditCardToken + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) &&
                Objects.equals(date, payment.date) &&
                Objects.equals(amount, payment.amount) &&
                Objects.equals(creditCardToken, payment.creditCardToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, amount, creditCardToken);
    }
}

