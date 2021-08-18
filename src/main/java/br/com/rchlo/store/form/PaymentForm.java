package br.com.rchlo.store.form;

import br.com.rchlo.store.domain.Card;
import br.com.rchlo.store.domain.Payment;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.YearMonth;

public class PaymentForm {

    @NotNull
    @Positive
    private BigDecimal value;

    @NotBlank
    @Length(max = 100)
    private String cardNameClient;

    @NotBlank
    @Pattern(regexp = "\\d{16}")
    private String cardNumber;

    @NotNull
    @Future
    private YearMonth cardExpiration;

    @NotBlank
    @Pattern(regexp = "\\d{3}")
    private String cardVerificationCode;

    public BigDecimal getValue() {
        return value;
    }

    public String getCardNameClient() {
        return cardNameClient;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public YearMonth getCardExpiration() {
        return cardExpiration;
    }

    public String getCardVerificationCode() {
        return cardVerificationCode;
    }

    public Payment convert() {
        Card card = new Card(cardNameClient, cardNumber, cardExpiration.toString(), cardVerificationCode);

        return new Payment(value, card);
    }
}
