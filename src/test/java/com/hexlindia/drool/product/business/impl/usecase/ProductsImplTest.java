package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.product.data.repository.api.ProductRepository;
import com.hexlindia.drool.product.dto.mapper.ProductDocDtoMapper;
import com.hexlindia.drool.product.exception.ProductNotFoundException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductsImplTest {

    private ProductViewImpl productsImplSpy;

    @Mock
    private ProductDocDtoMapper productDocDtoMapperMock;

    @Mock
    private ProductRepository productRepositoryMock;

    @BeforeEach
    void setUp() {
        this.productsImplSpy = Mockito.spy(new ProductViewImpl(productRepositoryMock, productDocDtoMapperMock));
    }

    @Test
    void findById_testFindUnavailableProduct() {
        when(this.productRepositoryMock.findById(new ObjectId())).thenReturn(null);
        Assertions.assertThrows(ProductNotFoundException.class, () -> productsImplSpy.getProductPageById(new ObjectId()));
    }
}
