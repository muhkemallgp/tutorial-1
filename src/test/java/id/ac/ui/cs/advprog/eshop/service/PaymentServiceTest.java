package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import enums.PaymentMethod;
import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.CashOnDeliveryPayment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.VoucherCodePayment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @Spy
    @InjectMocks
    PaymentServiceImpl paymentService;
    @Mock
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setup() {
        payments = new ArrayList<>();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("e45d7d21-fd29-4533-a569-abbe0819579a");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        Order order = new Order("dbd4aff4-9a7f-4603-92c2-eaf529271cc9",
                products, 1708560000L, "Safira Sudrajat");

        Map<String, String> voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP00000000AAA");
        Payment voucherPayment = new VoucherCodePayment(
                "c0f81308-9911-40c5-8da4-fa3194485aa1",
                PaymentMethod.VOUCHER.getValue(),
                order,
                voucherPaymentData
        );

        Map<String, String> CODPaymentData = new HashMap<>();
        CODPaymentData.put("address", "Jalan Raya 1");
        CODPaymentData.put("deliveryFee", "1000");
        Payment CODPayment = new CashOnDeliveryPayment(
                "d0f81308-9911-40c5-8da4-fa3194485aa1",
                PaymentMethod.COD.getValue(),
                order,
                CODPaymentData
        );

        payments.add(voucherPayment);
        payments.add(CODPayment);
    }

    @Test
    void testAddCODPayment() {
        Payment CODPayment = payments.get(1);
        doReturn(CODPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(
                CODPayment.getMethod(),
                CODPayment.getOrder(),
                CODPayment.getPaymentData()
        );

        verify(paymentRepository, times(1)).save(CODPayment);
        assertEquals(CODPayment.getId(), result.getId());
        assertEquals(CODPayment.getMethod(), result.getMethod());
        assertEquals(CODPayment.getOrder(), result.getOrder());
        assertEquals(CODPayment.getPaymentData(), result.getPaymentData());
        assertEquals(PaymentMethod.COD.getValue(), result.getMethod());
    }

    @Test
    void testAddVoucherCodePayment() {
        Payment voucherPayment = payments.get(0);
        doReturn(voucherPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(
                voucherPayment.getMethod(),
                voucherPayment.getOrder(),
                voucherPayment.getPaymentData()
        );

        verify(paymentRepository, times(1)).save(voucherPayment);
        assertEquals(voucherPayment.getId(), result.getId());
        assertEquals(voucherPayment.getMethod(), result.getMethod());
        assertEquals(voucherPayment.getOrder(), result.getOrder());
        assertEquals(voucherPayment.getPaymentData(), result.getPaymentData());
        assertEquals(PaymentMethod.VOUCHER.getValue(), result.getMethod());
    }

    @Test
    void testAddCODPaymentButOrderAlreadyExist() {
        Payment CODPayment = payments.get(1);
        doReturn(CODPayment).when(paymentRepository).findById(CODPayment.getId());

        assertNull(paymentService.addPayment(
                CODPayment.getMethod(),
                CODPayment.getOrder(),
                CODPayment.getPaymentData()
        ));
        verify(paymentRepository, times(0)).save(CODPayment);
    }

    @Test
    void testAddVoucherCodePaymentButOrderAlreadyExist() {
        Payment voucherPayment = payments.get(0);
        doReturn(voucherPayment).when(paymentRepository).findById(voucherPayment.getId());

        assertNull(paymentService.addPayment(
                voucherPayment.getMethod(),
                voucherPayment.getOrder(),
                voucherPayment.getPaymentData()
        ));
        verify(paymentRepository, times(0)).save(voucherPayment);
    }

    @Test
    void testUpdateStatusCODPayment() {
        Payment CODPayment = payments.get(1);
        Payment newCODPayment = new CashOnDeliveryPayment(
                CODPayment.getId(),
                CODPayment.getMethod(),
                CODPayment.getOrder(),
                CODPayment.getPaymentData(),
                PaymentStatus.SUCCESS.getValue()
        );

        doReturn(CODPayment).when(paymentRepository).findById(CODPayment.getId());
        doReturn(newCODPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.updatePaymentStatus(
                CODPayment.getId(),
                PaymentStatus.SUCCESS.getValue()
        );

        assertEquals(CODPayment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(CODPayment.getMethod(), result.getMethod());
        assertEquals(CODPayment.getOrder(), result.getOrder());
        assertEquals(CODPayment.getPaymentData(), result.getPaymentData());
        assertEquals(PaymentMethod.COD.getValue(), result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusVoucherCodePayment() {
        Payment voucherPayment = payments.get(0);
        Payment newVoucherCodePayment = new VoucherCodePayment(
                voucherPayment.getId(),
                voucherPayment.getMethod(),
                voucherPayment.getOrder(),
                voucherPayment.getPaymentData(),
                PaymentStatus.SUCCESS.getValue()
        );

        doReturn(voucherPayment).when(paymentRepository).findById(voucherPayment.getId());
        doReturn(newVoucherCodePayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.updatePaymentStatus(
                voucherPayment.getId(),
                PaymentStatus.SUCCESS.getValue()
        );

        assertEquals(voucherPayment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(voucherPayment.getMethod(), result.getMethod());
        assertEquals(voucherPayment.getOrder(), result.getOrder());
        assertEquals(voucherPayment.getPaymentData(), result.getPaymentData());
        assertEquals(PaymentMethod.VOUCHER.getValue(), result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusCODPaymentInvalidStatus() {
        Payment CODPayment = payments.get(1);
        doReturn(CODPayment).when(paymentRepository).findById(CODPayment.getId());

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.updatePaymentStatus(CODPayment.getId(), "MEOW");
        });
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusVoucherCodePaymentInvalidStatus() {
        Payment voucherPayment = payments.get(0);
        doReturn(voucherPayment).when(paymentRepository).findById(voucherPayment.getId());

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.updatePaymentStatus(voucherPayment.getId(), "MEOW");
        });
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusCODPaymentInvalidId() {
        doReturn(null).when(paymentRepository).findById("zczc");

        assertThrows(NoSuchElementException.class, () -> {
            paymentService.updatePaymentStatus("zczc", PaymentStatus.SUCCESS.getValue());
        });

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testFindByIdIfIdFound() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.findById(payment.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getOrder(), result.getOrder());
        assertEquals(payment.getPaymentData(), result.getPaymentData());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        doReturn(null).when(paymentRepository).findById("zczc");

        assertNull(paymentService.findById("zczc"));
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).getAllPayments();

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(payments, result);
    }
}