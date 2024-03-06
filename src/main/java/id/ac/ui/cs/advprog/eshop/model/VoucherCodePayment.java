package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import lombok.Getter;

@Getter
public class VoucherCodePayment extends Payment {

    public VoucherCodePayment(String id, String method, Order order, Map<String, String> paymentData) {
        super(id, method, order, paymentData);
    }

    public VoucherCodePayment(String id, String method, Order order, Map<String, String> paymentData, String status) {
        super(id, method, order, paymentData, status);
    }

    @Override
    protected void setPaymentData(Map<String, String> paymentData) {
        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException("Payment data cannot be empty");
        }

        if (paymentData.get("voucherCode") == null || paymentData.get("voucherCode").isEmpty()) {
            throw new IllegalArgumentException("Voucher code cannot be empty");
        }

        if (!isVoucherValid(paymentData.get("voucherCode"))) {
            throw new IllegalArgumentException("Invalid voucher code");
        }

        this.paymentData = paymentData;
    }

    private boolean isVoucherValid(String voucherCode) {
        if (voucherCode.length() != 16 ||
                !voucherCode.startsWith("ESHOP") ||
                numericsInVoucherCounter(voucherCode) != 8) {
            return false;
        }

        return true;
    }

    private long numericsInVoucherCounter(String voucherCode) {
        long numberOfNumerics = 0;

        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                numberOfNumerics++;
            }
        }

        return numberOfNumerics;
    }
}