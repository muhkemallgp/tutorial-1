package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import lombok.Getter;

@Getter
public class CODPayment extends Payment {
    public CODPayment(String id, String method, Order order, Map<String, String> paymentData, String status) {
        return null;
    }

    public CODPayment(String id, String method, Order order, Map<String, String> paymentData) {
        return null;
    }
}