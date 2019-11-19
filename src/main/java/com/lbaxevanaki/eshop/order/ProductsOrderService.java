package com.lbaxevanaki.eshop.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsOrderService {
	@Autowired
	private ProductsOrderRepository productsOrderRepository;
	
	List<ProductsOrder> getAllOrders(){
		List<ProductsOrder> orders = new ArrayList<>();		
		productsOrderRepository.findAll().forEach(orders::add);	
		return orders;
	}
	
	public ProductsOrder getOrder(Long id) {	
		return productsOrderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));	
	}
	

	public List<ProductsOrder> getOrdersByTimePeriod(Date fromDate, Date toDate) {	
		return productsOrderRepository.findAllByCreationDateTimeBetween(fromDate, toDate);	
	}
	
	public ProductsOrder addOrder(ProductsOrder order) {
		order.updateOrderItemsPrice();
		ProductsOrder savedOrder = productsOrderRepository.save(order);
		return savedOrder;
	}
	
	public void updateOrder(ProductsOrder order) {
		order.updateOrderItemsPrice();
		productsOrderRepository.save(order);	
	}

	public void deleteOrder(Long id) {
		productsOrderRepository.deleteById(id);
	}
	

}
