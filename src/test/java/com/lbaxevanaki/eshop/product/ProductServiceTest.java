package com.lbaxevanaki.eshop.product;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ProductServiceTest {
	@InjectMocks
	private ProductService productService;  
	
	@Mock
	private ProductRepository productRepository;  


	@Test
	void testGetAllProducts() {
		Product product1 = new Product("PRODUCT A", 1000.0);
		Product product2 = new Product("PRODUCT B", 2000.0);
		Product product3 = new Product("PRODUCT C", 3000.0);
		Product product4 = new Product("PRODUCT D", 4000.0);
		Product product5 = new Product("PRODUCT D", 5000.0);
		
		List<Product> expectedProducts = new ArrayList<Product>(Arrays.asList(product1, product2, product3,  product4,  product5)); 
		doReturn(expectedProducts).when(productRepository).findAll();  
		
		 // when
        List<Product> actualProducts = productService.getAllProducts();

        // then
        assertThat(actualProducts).isEqualTo(expectedProducts);
    
		
	}
	
	@Test
	void testGetAllProductsNoProducts() {
		List<Product> expectedProducts = new ArrayList<Product>(); 
		doReturn(expectedProducts).when(productRepository).findAll();  
		
		 // when
        List<Product> actualProducts = productService.getAllProducts();

        // then
        assertThat(actualProducts).isEqualTo(expectedProducts);
  	
	}
	
	@Test
	void testCreateProduct() {
		Product product = new Product("PRODUCT A", 1000.0);
		Product savedProduct = new Product("PRODUCT A", 1000.0);
		savedProduct.setId(1000L);
		doReturn(savedProduct).when(productRepository).save(product);  

		//when
		Product actualProduct = productService.addProduct(product);
		
        // then
        assertThat(actualProduct).isEqualTo(savedProduct);
  	
	}
	
	@Test
	void testUpdateProduct() {
		Product product = new Product("PRODUCT A", 1000.0);
		product.setId(1000L);	
		Optional<Product> optionalProduct =  Optional.of(product);
		doReturn(optionalProduct).when(productRepository).findById(1000L);  
		//when, no exception thrown
		productService.updateProduct(product);
	}
	
	@Test
	void testUpdateNotExistingProduct() {
		Product product = new Product("PRODUCT A", 1000.0);
		product.setId(1000L);
		
		doReturn(Optional.empty()).when(productRepository).findById(1000L);  
        assertThrows(EntityNotFoundException.class, () -> 
        {productService.updateProduct(product);
        });
  	
	}


}
