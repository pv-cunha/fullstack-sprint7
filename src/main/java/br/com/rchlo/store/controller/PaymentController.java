package br.com.rchlo.store.controller;

import br.com.rchlo.store.domain.Payment;
import br.com.rchlo.store.dto.PaymentDto;
import br.com.rchlo.store.form.PaymentForm;
import br.com.rchlo.store.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/payments/{id}")
    public ResponseEntity<PaymentDto> detail(@PathVariable Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        if (payment.isPresent()) {
            return ResponseEntity.ok(new PaymentDto(payment.get()));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/payments")
    @Transactional
    public ResponseEntity<PaymentDto> create(@RequestBody @Valid PaymentForm form, UriComponentsBuilder uriBuilder) {
        Payment payment = form.convert();
        paymentRepository.save(payment);

        URI uri = uriBuilder.path("/payments/{id}").buildAndExpand(payment.getId()).toUri();
        return ResponseEntity.created(uri).body(new PaymentDto(payment));
    }

    @PutMapping("/payments/{id}")
    @Transactional
    public ResponseEntity<PaymentDto> confirmPayment(@PathVariable Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        try {
            payment.get().confirmPayment();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new PaymentDto(payment.get()));
    }

    @DeleteMapping("/payments/{id}")
    @Transactional
    public ResponseEntity<PaymentDto> cancelPayment(@PathVariable Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        try {
            payment.get().cancelPayment();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new PaymentDto(payment.get()));
    }
}
