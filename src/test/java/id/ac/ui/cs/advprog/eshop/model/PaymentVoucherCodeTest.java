package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enums.PaymentMethod;
import enums.PaymentStatus;

class PaymentVoucherCodeTest {
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

        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreateVoucherPaymentWaitingPaymentStatus() {
        Payment payment = new VoucherPayment(
                "f3b4a3e3-9a7f-4603-92c2-eaf529271cc9",
                PaymentMethod.VOUCHER.getValue(),
                order,
                paymentData
        );

        assertEquals("f3b4a3e3-9a7f-4603-92c2-eaf529271cc9", payment.getId());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
        assertSame(order, payment.getOrder());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), payment.getStatus());
    }

    @Test
    void testCreateVoucherPaymentSuccessStatus() {
        Payment payment = new VoucherPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.VOUCHER.getValue(),
                order,
                paymentData,
                PaymentStatus.SUCCESS.getValue()
        );
        assertSame(order, payment.getOrder());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals("e45d7d21-fd29-4533-a569-abbe0819579a", payment.getId());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreateVoucherPaymentRejectedStatus() {
        Payment payment = new VoucherPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.VOUCHER.getValue(),
                order,
                paymentData,
                PaymentStatus.REJECTED.getValue()
        );
        assertSame(order, payment.getOrder());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals("e45d7d21-fd29-4533-a569-abbe0819579a", payment.getId());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreateVoucherPaymentInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new VoucherPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.VOUCHER.getValue(),
                    order,
                    paymentData,
                    "MEOW"
            );
        });
    }

    @Test
    void testCreateVoucherPaymentNullStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new VoucherPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.VOUCHER.getValue(),
                    order,
                    paymentData,
                    null
            );
        });
    }

    @Test
    void testSetVoucherPaymentStatStatusToSuccess() {
        Payment payment = new VoucherPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.VOUCHER.getValue(),
                order,
                paymentData
        );
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetVoucherPaymentStatStatusToRejected() {
        Payment payment = new VoucherPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.VOUCHER.getValue(),
                order,
                paymentData
        );
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetVoucherPaymentStatStatusToWaitingPayment() {
        Payment payment = new VoucherPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.VOUCHER.getValue(),
                order,
                paymentData
        );
        payment.setStatus(PaymentStatus.WAITING_PAYMENT.getValue());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), payment.getStatus());
    }

    @Test
    void testSetVoucherPaymentStatStatusToInvalid() {
        Payment payment = new VoucherPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.VOUCHER.getValue(),
                order,
                paymentData
        );
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("MEOW");
        });
    }

    @Test
    void testSetVoucherPaymentStatStatusToNull() {
        Payment payment = new VoucherPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.VOUCHER.getValue(),
                order,
                paymentData
        );
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus(null);
        });
    }

    @Test
    void testCreateVoucherPaymentWithNullOrder() {
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new VoucherPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.VOUCHER.getValue(),
                    null,
                    paymentData
            );
        });
    }

    @Test
    void testCreateVoucherPaymentWithEmptyPaymentData() {
        paymentData.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new VoucherPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.VOUCHER.getValue(),
                    order,
                    paymentData
            );
        });
    }

    @Test
    void testCreateVoucherPaymentWithNullPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new VoucherPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.VOUCHER.getValue(),
                    order,
                    null
            );
        });
    }

    @Test
    void testCreateVoucherPaymentWithEmptyVoucherCode() {
        paymentData.put("voucherCode", "");
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new VoucherPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.VOUCHER.getValue(),
                    order,
                    paymentData
            );
        });
    }

    @Test
    void testCreateVoucherPaymentWithNullVoucherCode() {
        paymentData.put("voucherCode", null);
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new VoucherPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.VOUCHER.getValue(),
                    order,
                    paymentData
            );
        });
    }
}