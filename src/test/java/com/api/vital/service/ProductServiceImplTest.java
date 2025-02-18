package com.api.vital.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.vital.models.entity.Product;
import com.api.vital.models.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ProductServiceImplTest {
	
	@Mock
	private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

	@Test
	void findByNameTest() {
		Product prod = Product.builder()
				.id("1")
				.name("Pan")
				.price(123)
				.type(new ArrayList<String>())
				.build();
        when(productRepository.findByName("pan")).thenReturn(List.of(prod));
        
        List<Product> prodObtained = productServiceImpl.findByName("pan");
        Assertions.assertEquals(List.of(prod), prodObtained);

	}
}
