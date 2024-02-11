package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        when(productRepository.create(product)).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertEquals(product, createdProduct);
        verify(productRepository, times(1)).create(createdProduct);
    }

    @Test
    void testFindAllProducts() {
        Product product = new Product();
        when(productRepository.create(product)).thenReturn(product);
        productService.create(product);

        when(productRepository.findAll()).thenReturn(List.of(product).iterator());
        Iterator<Product> foundProducts = productService.findAll().iterator();

        assertTrue(foundProducts.hasNext());
        Product savedProduct = foundProducts.next();

        assertEquals(product, savedProduct);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindProductById() {
        Product product = new Product();
        String productId = product.getProductId();
        when(productRepository.findById(productId)).thenReturn(product);

        Product foundProduct = productService.findById(productId);

        assertEquals(product, foundProduct);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testEditProduct() {
        Product product = new Product();
        when(productRepository.updateProduct(product)).thenReturn(product);

        Product editedProduct = productService.updateProduct(product);

        assertEquals(product, editedProduct);
        verify(productRepository, times(1)).updateProduct(product);
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        String productId = product.getProductId();
        when(productRepository.deleteProduct(productId)).thenReturn(product);

        Product deletedProduct = productService.deleteProduct(productId);

        assertEquals(product, deletedProduct);
        verify(productRepository, times(1)).deleteProduct(productId);
    }
}