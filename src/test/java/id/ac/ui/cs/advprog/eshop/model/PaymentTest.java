package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PaymentTest {
    private Map<String, String> paymentData;
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
    }

    @Test
    void testCreatePaymentWithWaitingPaymentStatus() {
        Payment payment = new Payment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                "",
                order,
                paymentData
        );
        assertSame(payment.getOrder(), order);
        assertNull(payment.getPaymentData());
        assertEquals("e45d7d21-fd29-4533-a569-abbe0819579a", payment.getId());
        assertEquals("", payment.getMethod());
        assertEquals("WAITING_PAYMENT", payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithSuccessStatus() {

        Payment payment = new Payment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                "",
                order,
                paymentData,
                "SUCCESS"
        );
        assertSame(payment.getOrder(), order);
        assertNull(payment.getPaymentData());
        assertEquals("e45d7d21-fd29-4533-a569-abbe0819579a", payment.getId());
        assertEquals("", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithRejectedStatus() {

        Payment payment = new Payment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                "",
                order,
                paymentData,
                "REJECTED"
        );
        assertSame(payment.getOrder(), order);
        assertNull(payment.getPaymentData());
        assertEquals("e45d7d21-fd29-4533-a569-abbe0819579a", payment.getId());
        assertEquals("", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        paymentData.clear();
    }


    @Test
    void testCreatePaymentWithInvalidStatus() {

        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new Payment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    "",
                    order,
                    paymentData,
                    "HORE"
            );
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithNullStatus() {

        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new Payment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    "",
                    order,
                    paymentData,
                    null
            );
        });
        paymentData.clear();
    }

    @Test
    void testSetStatusOfPaymentDataWithSuccess() {

        Payment payment = new Payment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                "",
                order,
                paymentData
        );
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testSetStatusOfPaymentWithRejected() {

        Payment payment = new Payment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                "",
                order,
                paymentData
        );
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testSetStatusOfPaymentWithInvalidStatus() {

        Payment payment = new Payment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                "",
                order,
                paymentData
        );
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("HORE");
        });
        paymentData.clear();
    }

    @Test
    void testSetStatusOfPaymentWithNullStatus() {

        Payment payment = new Payment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                "",
                order,
                paymentData
        );
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus(null);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithNullOrder() {
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new Payment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    "",
                    null,
                    paymentData
            );
        });
    }

    @Test
    void testSetPaymentData(){
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new Payment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    "",
                    null,
                    paymentData
            );
            payment.setPaymentData(paymentData);
        });
    }
}