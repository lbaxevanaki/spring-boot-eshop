package com.lbaxevanaki.eshop.order;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lbaxevanaki.eshop.product.Product;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/orders")
public class ProductsOrderController {

	@Autowired
	private ProductsOrderService orderService;

	@ApiOperation(value = "View a list of all orders", response = List.class)
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully retrieved list"),
		})
	@GetMapping
	ResponseEntity<List<ProductsOrder>> getAllOrders() {
		return ResponseEntity.ok( orderService.getAllOrders());
	};

	@GetMapping(params = { "fromDate", "toDate" })
	ResponseEntity<List<ProductsOrder>> getOrdersByTimePeriod(
			@Valid @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date fromDate,
			@Valid @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date toDate) {
		return ResponseEntity.ok(orderService.getOrdersByTimePeriod(fromDate, toDate));
	};

	@ApiOperation(value = "Retrieve the order given its product id ", response = ProductsOrder.class)
	@GetMapping("/{id}")
	public ResponseEntity<ProductsOrder> getOrder(@ApiParam(value = "Order id", required = true) @Min(1) @PathVariable Long id) {
		return ResponseEntity.ok(orderService.getOrder(id));
	}

	@ApiOperation(value = "Create a new order with all its order items ", response = ProductsOrder.class)
	@PostMapping
	public ResponseEntity<Object> createOrder(@ApiParam(value = "Order object including ist order items to store in database table", required = true) @Valid @RequestBody ProductsOrder order) {
		ProductsOrder savedOrder = orderService.addOrder(order);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedOrder.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@ApiOperation(value = "Update an order and its order items")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateOrder(@ApiParam(value = "Products Order object to store in database table", required = true) @Valid @RequestBody ProductsOrder order,  @ApiParam(value = "Order id", required = true) @PathVariable Long id) {
		order.setId(id);
		orderService.updateOrder(order);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Delete an order given its order id")
	public void deleteOrder(@ApiParam(value = "Product id", required = true) @Min(1) @PathVariable Long id) {
		orderService.deleteOrder(id);
	}

	/*
	@PostMapping(value = "/{id}/orderItems")
	public void addOrderItem(@RequestBody OrderItem orderItem, @PathVariable Long id) {
		ProductsOrder productsOrder = orderService.getOrder(id);
		productsOrder.getOrderItems().add(orderItem);
		orderService.updateOrder(productsOrder);
	}*/

}
