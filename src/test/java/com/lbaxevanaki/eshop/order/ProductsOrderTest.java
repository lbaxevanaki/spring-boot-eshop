package com.lbaxevanaki.eshop.order;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.lbaxevanaki.eshop.product.Product;

class ProductsOrderTest {

	@Test
	void test() {
		ProductsOrder po = new ProductsOrder();
		
		po.updateOrderItemsPrice();
		
		po.getOrderItems().add(new OrderItem());
		
		po.updateOrderItemsPrice();
		
		OrderItem oi = new OrderItem();
		
		Product p = new Product("product A", 10000.0);
		oi.setProduct(p);
		
		po.setOrderItems(new ArrayList<OrderItem>(Arrays.asList(oi)));
		assert(po.getOrderItems().get(0).getPrice().equals(po.getOrderItems().get(0).getProduct().getPrice()));
		
	}

}
