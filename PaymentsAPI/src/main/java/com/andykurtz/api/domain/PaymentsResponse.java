package com.andykurtz.api.domain;

import java.util.List;
import java.util.Objects;

public class PaymentsResponse {

    List<Payment> payments;

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "PaymentsResponse{" +
                "payments=" + payments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentsResponse that = (PaymentsResponse) o;
        return Objects.equals(payments, that.payments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payments);
    }
}
