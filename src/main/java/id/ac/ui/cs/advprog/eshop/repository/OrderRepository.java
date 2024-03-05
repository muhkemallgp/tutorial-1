package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private List<Order> orderData = new ArrayList<>();
    public Order save (Order order) {
        int i = 0;
        for (Order savedOrder : orderData) {
            if (savedOrder.getId().equals(order.getId())) {
                orderData.remove(i);
                orderData.add(i, order);
                return order;
            }
            i++;
        }

        orderData.add(order);
        return order;
    }
    public Order findById (String id) {
        for (Order savedOrder : orderData) {
            if (savedOrder.getId().equals(id)) {
                return savedOrder;
            }
        }
        return null;
    }
    public List <Order> findAllByAuthor (String author) {
        List <Order> result = new ArrayList<>();
        for (Order savedOrder : orderData) {
            if (savedOrder.getAuthor().equals(author)) {
                result.add(savedOrder);
            }
        }
        return result;
    }
}