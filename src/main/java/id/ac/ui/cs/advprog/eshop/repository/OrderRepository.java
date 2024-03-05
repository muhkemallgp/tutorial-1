package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private List<Order> orderData = new ArrayList<>();
    public Order save (Order order) { return null; }
    public Order findById (String id) { return null; }
    public List <Order> findAllByAuthor (String author) { return null; }
}