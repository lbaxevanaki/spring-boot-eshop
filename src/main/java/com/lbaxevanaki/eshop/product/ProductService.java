package com.lbaxevanaki.eshop.product;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	List<Product> getAllProducts(){
		List<Product> products = new ArrayList<>();		
		productRepository.findAll().forEach(products::add);	
		return products;
	}
	
	public Product getProduct(Long id) {	
		return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));	
	}
	
	public Product addProduct(Product product) {
		Product savedProduct = productRepository.save(product);	
		return savedProduct;
	}
	
	public void  updateProduct(Product product) {
		productRepository.findById(product.getId()).orElseThrow(() -> new EntityNotFoundException(product.getId().toString()));
		productRepository.save(product);
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
	

}
