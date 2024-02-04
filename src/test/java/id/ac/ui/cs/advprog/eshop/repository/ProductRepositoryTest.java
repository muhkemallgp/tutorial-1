package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Product Jamur");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Product Jamur");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        product2.setProductName("Product Bawang");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindById(){
        String numberId = UUID.randomUUID().toString();

        Product product = new Product();
        product.setProductId(numberId);
        product.setProductName("Produk Makanan");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product selectedProduct = productRepository.findById(numberId);
        assertEquals(product.getProductId(),selectedProduct.getProductId());
        assertEquals(product.getProductName(),selectedProduct.getProductName());
        assertEquals(product.getProductQuantity(),selectedProduct.getProductQuantity());
    }

    @Test
    void testNoFindById(){
        String numberId = UUID.randomUUID().toString();
        String numberIdRandom = UUID.randomUUID().toString();

        Product product = new Product();
        product.setProductId(numberId);
        product.setProductName("Produk Makanan");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product selectedProduct = productRepository.findById(numberIdRandom);
        assertNull(selectedProduct);
    }

    @Test
    void testFindByIdForMoreOneProduct(){
        String numberId = "";
        Product product = null;
        for (int i=0;i<5;i++){
            numberId = UUID.randomUUID().toString();
            product = new Product();
            product.setProductId(numberId);
            product.setProductName(String.format("Produk Hewan %d",i+1));
            product.setProductQuantity(150+i);
            productRepository.create(product);
        }

        Product selectedProduct = productRepository.findById(numberId);
        assertEquals(product.getProductQuantity(),selectedProduct.getProductQuantity());
        assertEquals(product.getProductId(),selectedProduct.getProductId());
        assertEquals(product.getProductName(),selectedProduct.getProductName());
    }
    @Test
    void testUpdateProduct(){
        String numberId = UUID.randomUUID().toString();

        Product product = new Product();
        product.setProductId(numberId);
        product.setProductName("Produk Makanan");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product changeProduct = new Product();
        changeProduct.setProductId(numberId);
        changeProduct.setProductName("Produk Minuman");
        changeProduct.setProductQuantity(200);
        productRepository.updateProduct(changeProduct);

        Product selectedProduct = productRepository.findById(numberId);
        assertEquals(changeProduct.getProductQuantity(),selectedProduct.getProductQuantity());
        assertEquals(changeProduct.getProductId(),selectedProduct.getProductId());
        assertEquals(changeProduct.getProductName(),selectedProduct.getProductName());
    }
}