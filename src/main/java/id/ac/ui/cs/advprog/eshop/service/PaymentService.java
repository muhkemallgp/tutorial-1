package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;
import java.util.Map;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;

public interface PaymentService {
    Payment addPayment(Order order, String method, Map<String, String> data);
    List<Payment> getAllPayments();
    Payment findById(String id);
    Payment updatePaymentStatu(String id, String status);
}