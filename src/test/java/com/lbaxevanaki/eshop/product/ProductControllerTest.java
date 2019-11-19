package com.lbaxevanaki.eshop.product;


import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductRepository mockRepository;

	@MockBean
	private ProductService mockService;

	@Test
	public void save_Product() throws Exception {
		Product savedProduct = new Product();
		
		doReturn(savedProduct).when(mockRepository).save(any(Product.class));  

		String productInJson = "{\r\n" + 
				"  \"id\": 0,\r\n" + 
				"  \"name\": \"Product D\",\r\n" + 
				"  \"price\": 2000\r\n" + 
				"}";

		mockMvc.perform(post("/api/v1/products").content(productInJson).header(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		//verify(mockService, times(0)).addProduct(any(Product.class));

	}
	@Test
	public void save_emptyProduct_no_name_400() throws Exception {

		String productInJson = "{\"id\":\"0\", \"price\":\"1000\"}";

		mockMvc.perform(post("/api/v1/products").content(productInJson).header(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.timestamp", is(notNullValue())))
				.andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.errors").isArray())
				.andExpect(jsonPath("$.errors", hasItem("Please provide a name")));

		verify(mockService, times(0)).addProduct(any(Product.class));

	}

}
