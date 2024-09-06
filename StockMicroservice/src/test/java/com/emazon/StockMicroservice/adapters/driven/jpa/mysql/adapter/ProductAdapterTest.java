package com.emazon.StockMicroservice.adapters.driven.jpa.mysql.adapter;

import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.entity.ProductEntity;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.mapper.IProductEntityMapper;
import com.emazon.StockMicroservice.adapters.driven.jpa.mysql.repository.IProductRepository;
import com.emazon.StockMicroservice.domain.model.Product;
import com.emazon.StockMicroservice.domain.util.PagedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductAdapterTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private IProductEntityMapper productEntityMapper;

    @InjectMocks
    private ProductAdapter productAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productAdapter = new ProductAdapter(productRepository, productEntityMapper);
    }

    @Test
    @DisplayName("Should save the product correctly")
    void shouldSaveProduct() {
        Product product = new Product(1L, "Laptop", "High-end gaming laptop", 5, 1500.00, List.of(), null);
        ProductEntity productEntity = new ProductEntity();
        when(productEntityMapper.toEntity(product)).thenReturn(productEntity);

        productAdapter.saveProduct(product);

        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    @DisplayName("Should return true if the product exists by name")
    void shouldReturnTrueIfProductExistsByName() {
        String productName = "Laptop";
        when(productRepository.findByName(productName)).thenReturn(Optional.of(new ProductEntity()));

        boolean exists = productAdapter.existsByName(productName);

        assertTrue(exists);
        verify(productRepository, times(1)).findByName(productName);
    }

    @Test
    @DisplayName("Should return a paginated result of products")
    void shouldReturnPagedResultOfProducts() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("Laptop");
        productEntity.setDescription("High-end gaming laptop");
        productEntity.setQuantity(5);
        productEntity.setPrice(1500.00);

        Product product = new Product(1L, "Laptop", "High-end gaming laptop", 5, 1500.00, List.of(), null);

        when(productEntityMapper.toDomain(productEntity)).thenReturn(product);

        List<ProductEntity> productEntities = List.of(productEntity, productEntity, productEntity, productEntity, productEntity,
                productEntity, productEntity, productEntity, productEntity, productEntity);
        Page<ProductEntity> productPage = new PageImpl<>(productEntities, PageRequest.of(0, 10), 10);
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(productPage);

        PagedResult<Product> result = productAdapter.getAllProducts(0, 10, "ASC", "name");

        assertEquals(10, result.getContent().size());
        assertEquals(product.getId(), result.getContent().get(0).getId());
        assertEquals(product.getName(), result.getContent().get(0).getName());
        assertEquals(product.getDescription(), result.getContent().get(0).getDescription());
        assertEquals(0, result.getPage());
        assertEquals(10, result.getSize());
        assertEquals(10, result.getTotalElements());

        verify(productRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")));
    }
}
