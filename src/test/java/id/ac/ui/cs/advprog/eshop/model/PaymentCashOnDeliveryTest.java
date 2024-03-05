package id.ac.ui.cs.advprog.eshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enums.PaymentMethod;
import enums.PaymentStatus;

import static org.junit.jupiter.api.Assertions.*;

class PaymentCashOnDeliveryTest {
    Map<String, String> paymentData;
    private Order order;
    private List<Product> products;

    @BeforeEach
    void setup() {
        this.paymentData = new HashMap<>();

        products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("e45d7d21-fd29-4533-a569-abbe0819579a");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Product product2 = new Product();
        product2.setProductId("8a76b99c-a0b3-46d2-a688-4c1831b21119");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        products.add(product2);

        order= new Order(
                "dbd4aff4-9a7f-4603-92c2-eaf529271cc9",
                products,
                1708560000L,
                "Qemul Sabiz"
        );

        paymentData.put("address", "Jalan Raya 12");
        paymentData.put("deliveryFee", "1000");
    }

    @Test
    void testCreateCashOnDeliveryPaymentPendingStatus() {
        Payment payment = new CashOnDeliveryPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData
        );
        assertSame(order, payment.getOrder());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals("e45d7d21-fd29-4533-a569-abbe0819579a", payment.getId());
        assertEquals(PaymentMethod.COD.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), payment.getStatus());
    }

    @Test
    void testCreateCashOnDeliveryPaymentSuccessStatus() {
        Payment payment = new CashOnDeliveryPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData,
                PaymentStatus.SUCCESS.getValue()
        );
        assertSame(order, payment.getOrder());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals("e45d7d21-fd29-4533-a569-abbe0819579a", payment.getId());
        assertEquals(PaymentMethod.COD.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreateCashOnDeliveryPaymentRejectedStatus() {
        Payment payment = new CashOnDeliveryPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData,
                PaymentStatus.REJECTED.getValue()
        );
        assertSame(order, payment.getOrder());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals("e45d7d21-fd29-4533-a569-abbe0819579a", payment.getId());
        assertEquals(PaymentMethod.COD.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreateCashOnDeliveryPaymentInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CashOnDeliveryPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData,
                    "HORE"
            );
        });
    }

    @Test
    void testCreateCashOnDeliveryPaymentNullStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CashOnDeliveryPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData,
                    null
            );
        });
    }

    @Test
    void testSetCashOnDeliveryPaymentStatStatusToSuccess() {
        Payment payment = new CashOnDeliveryPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData
        );
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetCashOnDeliveryPaymentStatStatusToRejected() {
        Payment payment = new CashOnDeliveryPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData
        );
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetCashOnDeliveryPaymentStatStatusToPending() {
        Payment payment = new CashOnDeliveryPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData
        );
        payment.setStatus(PaymentStatus.WAITING_PAYMENT.getValue());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), payment.getStatus());
    }

    @Test
    void testSetCashOnDeliveryPaymentStatStatusToInvalid() {
        Payment payment = new CashOnDeliveryPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData
        );
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("HORE");
        });
    }

    @Test
    void testSetCashOnDeliveryPaymentStatStatusToNull() {
        Payment payment = new CashOnDeliveryPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData
        );
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus(null);
        });
    }

    @Test
    void testCreateCashOnDeliveryPaymentWithNullOrder() {
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CashOnDeliveryPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    null,
                    paymentData
            );
        });
    }

    @Test
    void testCreateCashOnDeliveryPaymentWithEmptyPaymentData() {
        paymentData.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CashOnDeliveryPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData
            );
        });
    }

    @Test
    void testCreateCashOnDeliveryPaymentWithNullPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CashOnDeliveryPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    null
            );
        });
    }

    @Test
    void testCreateCashOnDeliveryPaymentInvalidDeliveryFee() {
        paymentData.put("deliveryFee", "");
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CashOnDeliveryPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData
            );
        });
    }

    @Test
    void testCreateCashOnDeliveryPaymentInvalidAddress() {
        paymentData.put("address", "");
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CashOnDeliveryPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData
            );
        });
    }

    @Test
    void testCreateCashOnDeliveryPaymentWithNullDeliveryFee() {
        paymentData.put("deliveryFee", null);
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CashOnDeliveryPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData
            );
        });
    }

    @Test
    void testCreateCashOnDeliveryPaymentWithNullAddress() {
        paymentData.put("address", null);
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CashOnDeliveryPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData
            );
        });
    }

    @Test
    void testCreateCashOnDeliveryPaymentWithInvalidDeliveryFeeAndAddress() {
        paymentData.put("deliveryFee", "");
        paymentData.put("address", "");
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CashOnDeliveryPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData
            );
        });
    }

    @Test
    void testCreateCashOnDeliveryPaymentWithNullDeliveryFeeAndAddress() {
        paymentData.put("deliveryFee", null);
        paymentData.put("address", null);
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CashOnDeliveryPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData
            );
        });
    }
}