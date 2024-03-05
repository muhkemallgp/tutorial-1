package id.ac.ui.cs.advprog.eshop.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Payment {
    String id;
    String method;
    Order order;
    Map<String, String> paymentData;
    String status;
    ArrayList<String> listStatus = new ArrayList<>(Arrays.asList("WAITING_PAYMENT", "SUCCESS", "REJECTED"));
    ArrayList<String> listMethod = new ArrayList<>(Arrays.asList("CASH_ON_DELIVERY","VOUCHER_CODE"));
    public Payment(String id, String method, Order order, Map<String, String> paymentData, String status) {
        this.id = id;
        this.method = method;
        this.setOrder(order);
        this.setPaymentData(paymentData);
        this.setStatus(status);
    }

    public Payment(String id, String method, Order order, Map<String, String> paymentData) {
        this(id, method, order, paymentData, "WAITING_PAYMENT");
    }

    public void setStatus(String status) {
        if (!listStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status");
        }

        this.status = status;
    }

    public void setOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        this.order = order;
    }

    protected void setPaymentData(Map<String, String> paymentData) {
        if (listMethod.contains(this.method)) {
            throw new IllegalArgumentException(
                    "Cannot set method-specific payment data for non-method-specific payment"
            );
        }

        this.paymentData = null;
    }
}