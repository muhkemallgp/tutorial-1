package id.ac.ui.cs.advprog.eshop.service;

import enums.OrderStatus;
import enums.PaymentMethod;

import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.*;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;
    @Mock
    PaymentRepository paymentRepository;
    @Mock
    OrderRepository orderRepository;
    List<Payment> payments;
    List<Order> orders;

    @BeforeEach
    void setUp() {
        payments = new ArrayList<>();
        orders = new ArrayList<>();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("e45d7d21-fd29-4533-a569-abbe0819579a");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        Order order = new Order("dbd4aff4-9a7f-4603-92c2-eaf529271cc9",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order);

        Map<String, String> voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP00000000AAA");
        Payment voucherPayment = new VoucherCodePayment(
                "dbd4aff4-9a7f-4603-92c2-eaf529271cc9",
                PaymentMethod.VOUCHER.getValue(),
                order,
                voucherPaymentData
        );

        Map<String, String> CODPaymentData = new HashMap<>();
        CODPaymentData.put("address", "Jalan Raya 1");
        CODPaymentData.put("deliveryFee", "1000");
        Payment CODPayment = new CashOnDeliveryPayment(
                "dbd4aff4-9a7f-4603-92c2-eaf529271cc9",
                PaymentMethod.COD.getValue(),
                order,
                CODPaymentData
        );

        payments.add(voucherPayment);
        payments.add(CODPayment);
    }

    @Test
    void testAddPayment() {
        Order order = orders.get(0);
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, PaymentMethod.VOUCHER.getValue(), Map.of("voucherCode", "ESHOP1234ABC5678"));
        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = payments.get(0);
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(payment.getId());
        doReturn(order).when(orderRepository).save(order);
        doReturn(payment).when(paymentRepository).save(payment);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());
        verify(orderRepository, times(1)).save(order);
        verify(paymentRepository, times(1)).save(payment);
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = payments.get(0);
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(payment.getId());
        doReturn(order).when(orderRepository).save(order);
        doReturn(payment).when(paymentRepository).save(payment);

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());
        verify(orderRepository, times(1)).save(order);
        verify(paymentRepository, times(1)).save(payment);
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusInvalidStatus() {
        Payment payment = payments.get(0);
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(payment.getId());

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "MEOW");
        });

        verify(orderRepository, times(0)).save(any(Order.class));
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testGetPayment() {
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        verify(paymentRepository, times(1)).findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPaymentIfNotFound() {
        doReturn(null).when(paymentRepository).findById("zczc");
        assertNull(paymentService.getPayment("zczc"));
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).getAllPayments();

        List<Payment> result = paymentService.getAllPayments();
        verify(paymentRepository, times(1)).getAllPayments();
        assertEquals(payments, result);
    }

    @Test
    void testGetAllPaymentsEmpty() {
        doReturn(new ArrayList<Payment>()).when(paymentRepository).getAllPayments();

        List<Payment> result = paymentService.getAllPayments();
        verify(paymentRepository, times(1)).getAllPayments();
        assertEquals(new ArrayList<Payment>(), result);
    }
}