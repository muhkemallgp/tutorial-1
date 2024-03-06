package id.ac.ui.cs.advprog.eshop.service;

import enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;

import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentDetails) {
        Payment payment = new Payment(order.getId(), method,order, paymentDetails);
        paymentRepository.save(payment);
        return payment;
    }
    @Override
    public Payment setStatus(Payment payment, String status) {
        Order order = orderRepository.findById(payment.getId());
        if (order != null) {
            if (status.equals("SUCCESS")) {
                order.setStatus(OrderStatus.SUCCESS.getValue());
            } else if (status.equals("REJECTED")) {
                order.setStatus(OrderStatus.FAILED.getValue());
            } else {
                throw new IllegalArgumentException();
            }
            orderRepository.save(order);
            payment.setStatus(status);
            paymentRepository.save(payment);
        } else {
            throw new NoSuchElementException();
        }
        return payment;
    }
    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }
    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }
}