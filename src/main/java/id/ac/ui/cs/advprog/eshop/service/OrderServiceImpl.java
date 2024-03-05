package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public Order createOrder(Order order) {
        if (orderRepository.findById(order.getId()) == null) {
            orderRepository.save(order);
            return order;
        }
        return null;
    }
    @Override
    public Order updateStatus(String orderId, String status) {
        Order order = orderRepository.findById(orderId);

        if (order != null) {
            Order newOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(),
                    order.getAuthor(), status);
            orderRepository.save(newOrder);
            return newOrder;
        } else {
            throw new NoSuchElementException("Order not found");
        }
    }
    @Override
    public List<Order> findAllByAuthor (String author) {
        return orderRepository.findAllByAuthor(author);
    }
    @Override
    public Order findById(String orderId) {
        return orderRepository.findById(orderId);
    }
}