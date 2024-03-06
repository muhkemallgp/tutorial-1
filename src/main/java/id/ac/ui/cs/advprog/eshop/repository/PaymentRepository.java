package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;

@Repository
public class PaymentRepository {
    private List<Payment> payments = new ArrayList<>();

    public Payment save(Payment payment) {
        int i = 0;
        for (Payment p : payments) {
            if (p.getId().equals(payment.getId())) {
                payments.set(i, payment);
                return payment;
            }
            i++;
        }

        payments.add(payment);
        return payment;
    }

    public Payment findById(String id) {
        for (Payment p : payments) {
            if (p.getId().equals(id)) {
                return p;
            }
        }

        return null;
    }

    public List<Payment> getAllPayments() {
        return payments;
    }
}