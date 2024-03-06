package id.ac.ui.cs.advprog.eshop.repository;

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

import enums.PaymentMethod;
import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.CashOnDeliveryPayment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.VoucherCodePayment;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Product> products;
    List<Payment> payments;
    Order order;

    @BeforeEach
    void setup() {
        paymentRepository = new PaymentRepository();

        payments = new ArrayList<>();
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

        order = new Order("dbd4aff4-9a7f-4603-92c2-eaf529271cc9",
                products, 1708560000L, "Safira Sudrajat");

        Payment payment1 = new Payment(
                "a0f81308-9911-40c5-8da4-fa3194485aa1",
                "",
                order,
                null
        ),
                payment2 = new Payment(
                        "b0f81308-9911-40c5-8da4-fa3194485aa1",
                        "",
                        order,
                        null
                );
        payments.add(payment1);
        payments.add(payment2);

        Map<String, String> voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment voucherPayment = new VoucherCodePayment(
                "c0f81308-9911-40c5-8da4-fa3194485aa1",
                PaymentMethod.VOUCHER.getValue(),
                order,
                voucherPaymentData
        );

        Map<String, String> bankPaymentData = new HashMap<>();
        bankPaymentData.put("bankName", "BCA");
        bankPaymentData.put("referenceCode", "1234567890");
        Payment bankPayment = new CashOnDeliveryPayment(
                "d0f81308-9911-40c5-8da4-fa3194485aa1",
                PaymentMethod.COD.getValue(),
                order,
                bankPaymentData
        );

        payments.add(voucherPayment);
        payments.add(bankPayment);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(1);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getOrder(), findResult.getOrder());
        assertSame(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), payment.getStatus());
    }

    @Test
    void testSaveCreateVoucher() {
        Payment payment = payments.get(2);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payments.get(2).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getOrder(), findResult.getOrder());
        assertSame(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), payment.getStatus());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
    }

    @Test
    void testSaveCreateCODPayment() {
        Payment payment = payments.get(3);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payments.get(3).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getOrder(), findResult.getOrder());
        assertSame(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), payment.getStatus());
        assertEquals(PaymentMethod.COD.getValue(), payment.getMethod());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = payments.get(1);
        paymentRepository.save(payment);

        List<Product> newProducts = new ArrayList<>();
        Product product = new Product();
        product.setProductId("e45d7d21-fd29-4533-a569-abbe0819579a");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        newProducts.add(product);

        Order newOrder = new Order("dbd4aff4-9a7f-4603-92c2-eaf529271cc9",
                newProducts, 1708560000L, "Safira Sudrajat");
        Payment newPayment = new Payment(
                payment.getId(),
                payment.getMethod(),
                newOrder,
                null
        );
        Payment result = paymentRepository.save(newPayment);

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(newOrder, findResult.getOrder());
        assertEquals(result.getOrder(), findResult.getOrder());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(result.getMethod(), findResult.getMethod());
        assertSame(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(result.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(result.getStatus(), findResult.getStatus());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), findResult.getStatus());
    }

    @Test
    void testSaveUpdateVoucher() {
        Payment payment = payments.get(2);
        paymentRepository.save(payment);

        List<Product> newProducts = new ArrayList<>();
        Product product = new Product();
        product.setProductId("e45d7d21-fd29-4533-a569-abbe0819579a");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        newProducts.add(product);

        Order newOrder = new Order("dbd4aff4-9a7f-4603-92c2-eaf529271cc9",
                newProducts, 1708560000L, "Safira Sudrajat");
        Map<String, String> newVoucherCodePaymentData = new HashMap<>();
        newVoucherCodePaymentData.put("voucherCode", "ESHOP1234ABC0000");
        Payment newPayment = new VoucherCodePayment(
                payment.getId(),
                payment.getMethod(),
                newOrder,
                newVoucherCodePaymentData
        );
        Payment result = paymentRepository.save(newPayment);

        Payment findResult = paymentRepository.findById(payments.get(2).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(newOrder, findResult.getOrder());
        assertEquals(result.getOrder(), findResult.getOrder());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(result.getMethod(), findResult.getMethod());
        assertSame(result.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(result.getStatus(), findResult.getStatus());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), findResult.getStatus());
        assertEquals(PaymentMethod.VOUCHER.getValue(), findResult.getMethod());
    }

    @Test
    void testSaveUpdateCODPayment() {
        Payment payment = payments.get(3);
        paymentRepository.save(payment);

        List<Product> newProducts = new ArrayList<>();
        Product product = new Product();
        product.setProductId("e45d7d21-fd29-4533-a569-abbe0819579a");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        newProducts.add(product);

        Order newOrder = new Order("dbd4aff4-9a7f-4603-92c2-eaf529271cc9",
                newProducts, 1708560000L, "Safira Sudrajat");
        Map<String, String> newCashOnDeliveryPaymentData = new HashMap<>();
        newCashOnDeliveryPaymentData.put("bankName", "BNI");
        newCashOnDeliveryPaymentData.put("referenceCode", "1234567890");
        Payment newPayment = new CashOnDeliveryPayment(
                payment.getId(),
                payment.getMethod(),
                newOrder,
                newCashOnDeliveryPaymentData
        );
        Payment result = paymentRepository.save(newPayment);

        Payment findResult = paymentRepository.findById(payments.get(3).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(newOrder, findResult.getOrder());
        assertEquals(result.getOrder(), findResult.getOrder());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(result.getMethod(), findResult.getMethod());
        assertSame(result.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(result.getStatus(), findResult.getStatus());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), findResult.getStatus());
        assertEquals(PaymentMethod.COD.getValue(), findResult.getMethod());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payments.get(1).getId(), findResult.getId());
        assertSame(payments.get(1).getPaymentData(), findResult.getPaymentData());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
        assertEquals(payments.get(1).getOrder(), findResult.getOrder());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        assertNull(paymentRepository.findById("zczc"));
    }

    @Test
    void testGetAllPayments() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> allPayments = paymentRepository.getAllPayments();
        assertEquals(4, allPayments.size());
    }

    @Test
    void testAddPaymentDuplicateId() {
        Payment payment = payments.get(1);
        paymentRepository.save(payment);

        Payment newPayment = new Payment(
                payment.getId(),
                payment.getMethod(),
                payment.getOrder(),
                payment.getPaymentData()
        );

        assertThrows(IllegalStateException.class, () -> {
            paymentRepository.save(newPayment);
        });
    }

    @Test
    void testAddPaymentDuplicateIdVoucher() {
        Payment payment = payments.get(2);
        paymentRepository.save(payment);

        Payment newPayment = new VoucherCodePayment(
                payment.getId(),
                payment.getMethod(),
                payment.getOrder(),
                payment.getPaymentData()
        );

        assertThrows(IllegalStateException.class, () -> {
            paymentRepository.save(newPayment);
        });
    }

    @Test
    void testAddPaymentDuplicateIdCODPayment() {
        Payment payment = payments.get(3);
        paymentRepository.save(payment);

        Payment newPayment = new CashOnDeliveryPayment(
                payment.getId(),
                payment.getMethod(),
                payment.getOrder(),
                payment.getPaymentData()
        );

        assertThrows(IllegalStateException.class, () -> {
            paymentRepository.save(newPayment);
        });
    }
}