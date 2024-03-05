package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import lombok.Getter;

@Getter
public class VoucherCode extends Payment {

    public VoucherCode(String id, String method, Order order, Map<String, String> paymentData) {
    }

    public VoucherCode(String id, String method, Order order, Map<String, String> paymentData, String status) {
    }

}