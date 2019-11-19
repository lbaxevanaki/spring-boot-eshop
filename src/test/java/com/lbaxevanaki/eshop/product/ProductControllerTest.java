package com.lbaxevanaki.eshop.product;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
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
	void test() {
		fail("Not yet implemented");
	}

	@Test
	public void save_emptyProduct_emptyPrice_400() throws Exception {

		String productInJson = "{\"name\":\"ABC\"}";

		mockMvc.perform(post("/api/v1/products").content(productInJson).header(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.timestamp", is(notNullValue()))).andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.errors").isArray())
				.andExpect(jsonPath("$.errors", hasItem("Please provide a price")));

		verify(mockService, times(0)).addProduct((Product) any(Product.class));

	}

}
