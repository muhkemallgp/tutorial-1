package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public List<Product> findAll();
    public  Product findById(String id);
    public Product updateProduct(Product product);
    public Product deleteProduct(String productId);
}
