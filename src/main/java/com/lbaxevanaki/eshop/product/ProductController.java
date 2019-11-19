package com.lbaxevanaki.eshop.product;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Eshop products management web service", description = "CRUD operations for products")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@ApiOperation(value = "View a list of available products", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list")})

	@GetMapping
	ResponseEntity<List<Product>> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	};

	@ApiOperation(value = "Get the product given its product id ", response = Product.class)
	@GetMapping("/{id}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = 404, message = "Product id not found")

	})
	public ResponseEntity<Object> getProduct(
			@ApiParam(value = "Product id", required = true) @Min(1) @PathVariable Long id) {
		try {
			return ResponseEntity.ok(productService.getProduct(id));
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>("Invalid order id " + id, HttpStatus.NOT_FOUND);
		}

	}

	@ApiOperation(value = "Create a new product ", response = Product.class)
	@PostMapping
	public ResponseEntity<Object> createProduct(
			@ApiParam(value = "Product object to store in database table", required = true) @Valid @RequestBody Product product) {
		Product savedProduct = productService.addProduct(product);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedProduct.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Update an product")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully update"),
			@ApiResponse(code = 404, message = "Product id not found") })
	public ResponseEntity<Object> updateProduct(
			@ApiParam(value = "Product object to store in database table", required = true) @Valid @RequestBody Product product,
			@ApiParam(value = "Product id", required = true) @Min(1) @PathVariable Long id) {
		product.setId(id);
		try {
			productService.updateProduct(product);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>("Invalid order id " + id, HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Delete a product given its product id")
	public void deleteProduct(@ApiParam(value = "Product id", required = true) @Min(1) @PathVariable Long id) {
		productService.deleteProduct(id);
	}

}
