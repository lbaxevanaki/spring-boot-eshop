package com.lbaxevanaki.eshop.order;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.lbaxevanaki.eshop.product.Product;



@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductsOrderController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
class ProductsOrderControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductsOrderRepository mockRepository;

	@MockBean
	private ProductsOrderService mockService;


	@Test
	@Ignore
	public void save_Order() throws Exception {
		ProductsOrder savedOrder = new ProductsOrder();		
		OrderItem oi = new OrderItem();
		
		Product p = new Product("product A", 10000.0);
		oi.setProduct(p);
		savedOrder.setOrderItems(new ArrayList<OrderItem>(Arrays.asList(oi)));
		
		doReturn(savedOrder).when(mockRepository).save(any(ProductsOrder.class));  
		
		String orderInJson = "{\r\n" + 
				"  \"creationDateTime\": \"2019-11-19T15:00:39.258Z\",\r\n" + 
				"  \"email\": \"me@me.com\",\r\n" + 
				"  \"id\": 0,\r\n" + 
				"  \"orderItems\": [\r\n" + 
				"    {\r\n" + 
				"      \"price\": 0,\r\n" + 
				"      \"product\": {\r\n" + 
				"        \"id\": 1,\r\n" + 
				"        \"name\": \"string\",\r\n" + 
				"        \"price\":1000\r\n" + 
				"      },\r\n" + 
				"      \"quantity\": 1\r\n" + 
				"    }\r\n" + 
				"  ],\r\n" + 
				"  \"totalOrderPrice\": 0\r\n" + 
				"}";
		
		mockMvc.perform(post("/api/v1/orders").content(orderInJson).header(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
			
		verify(mockService, times(1)).addOrder(any(ProductsOrder.class));

	}

	@Test
	public void save_emptyOrder_no_email_400() throws Exception {

		
		String orderInJson = "{\"id\":\"0\"}";
		
		mockMvc.perform(post("/api/v1/orders").content(orderInJson).header(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.timestamp", is(notNullValue())))
				.andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.errors").isArray())
				.andExpect(jsonPath("$.errors", hasItem("must not be empty")));

		verify(mockService, times(0)).addOrder(any(ProductsOrder.class));

	}

}
