package id.ac.ui.cs.advprog.eshop.model;
import java.util.Map;
import lombok.Getter;
@Getter
public class CashOnDeliveryPayment extends Payment {
    public CashOnDeliveryPayment(String id, String method, Order order, Map<String, String> paymentData, String status) {
        super(id, method, order, paymentData, status);
    }

    public CashOnDeliveryPayment(String id, String method, Order order, Map<String, String> paymentData) {
        super(id, method, order, paymentData);
    }


    @Override
    protected void setPaymentData(Map<String, String> paymentData) {
        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException("Payment data cannot be empty");
        }

        if (paymentData.get("address") == null || paymentData.get("address").isEmpty()) {
            throw new IllegalArgumentException("Address name cannot be empty");
        }

        if (paymentData.get("deliveryFee") == null || paymentData.get("deliveryFee").isEmpty()) {
            throw new IllegalArgumentException("DeliveryFee cannot be empty");
        }

        this.paymentData = paymentData;
    }
}