package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;
import java.util.Map;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;

public interface PaymentService {
    public Payment addPayment (Order order, String method, Map<String, String> paymentDetails);
    public Payment setStatus (Payment payment, String status);
    public Payment getPayment (String paymentId);
    public List<Payment> getAllPayments();
}