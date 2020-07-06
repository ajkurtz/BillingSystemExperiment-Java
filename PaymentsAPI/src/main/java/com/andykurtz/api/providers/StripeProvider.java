package com.andykurtz.api.providers;

import com.andykurtz.api.domain.Payment;
import com.andykurtz.api.exception.ServerErrorException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StripeProvider {

    @Value("${application.encryptionPassword}")
    String encryptionPassword;

    @Value("${application.stripeAPIKey}")
    String stripeAPIKey;

    public void charge(Payment payment) {
        Stripe.apiKey = stripeAPIKey;

        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(encryptionPassword);

        String creditCardToken = textEncryptor.decrypt(payment.getCreditCardToken());

        int cents = (int) (payment.getAmount() * 100);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", cents);
        params.put("currency", "usd");
        params.put("source", creditCardToken);

        Charge charge = null;
        try {
            charge = Charge.create(params);
        } catch (StripeException e) {
            throw new ServerErrorException("Exception while charging credit card");
        }

        if (!"succeeded".equalsIgnoreCase(charge.getStatus())) {
            throw new ServerErrorException("Error while charging credit card");
        }

    }

}
