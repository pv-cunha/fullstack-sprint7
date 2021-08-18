package br.com.rchlo.store.controller;

import br.com.rchlo.store.domain.Card;
import br.com.rchlo.store.domain.Payment;
import br.com.rchlo.store.repository.PaymentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/schema.sql")
@ActiveProfiles("test")
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void shouldNotConfirmAnAlreadyCanceledPayment() throws Exception {
        String uri = "/payments/{id}";

        Payment canceledPayment = paymentFake().cancelPayment();

        paymentRepository.save(canceledPayment);

        mockMvc.perform(MockMvcRequestBuilders.put(uri , canceledPayment.getId()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldNotCancelAnAlreadyConfirmedPayment() throws Exception {
        String url = "/payments/{id}";

        Payment confirmedPayment = paymentFake().confirmPayment();

        paymentRepository.save(confirmedPayment);

        mockMvc.perform(MockMvcRequestBuilders.delete(url, confirmedPayment.getId()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private Payment paymentFake() {
        Card cardFake = new Card("Paulo", "1234567890120987", "2022-04", "123");

        return new Payment(new BigDecimal("100.00"), cardFake);
    }

}
