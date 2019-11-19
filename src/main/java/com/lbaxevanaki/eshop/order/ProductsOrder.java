package com.lbaxevanaki.eshop.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class ProductsOrder {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsOrder.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	@NotEmpty
	@ApiModelProperty(notes = "The email of the person that has placed this order")
	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	Date creationDateTime;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "PRODUCTS_ORDER_ID", referencedColumnName = "id", insertable = true, updatable = true)
	@ApiModelProperty(notes = "The list order items of this order")
	private List<OrderItem> orderItems = new ArrayList<>();

	public ProductsOrder() {

	}

	public ProductsOrder(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return String.format("Order[id=%d, customer email ='%s', total price='%d']", id, email, getTotalOrderPrice());
	}

	/*
	 * private Double calculateTotalPrice() { Double totalPrice =
	 * orderItems.stream() .map(x -> (x.getPrice() * x.getQuantity()))
	 * .reduce(Double::sum).orElse(0.0); LOGGER.debug("Order price: " + totalPrice);
	 * return totalPrice; }
	 */

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the creationDateTime
	 */
	public Date getCreationDateTime() {
		return creationDateTime;
	}

	/**
	 * @param creationDateTime the creationDateTime to set
	 */
	public void setCreationDateTime(Date creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	@Transient
	public Double getTotalOrderPrice() {
		Double totalPrice = orderItems.stream().map(x -> (x.getPrice() * x.getQuantity())).reduce(Double::sum)
				.orElse(0.0);
		LOGGER.debug("Order price: " + totalPrice);
		return totalPrice;
	}

	/**
	 * @return the orderItems
	 */
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * @param orderItems the orderItems to set
	 */
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
		// update the order item price with the current product price
		orderItems.forEach(i -> i.setPrice(i.getProduct().getPrice()));
	}
	
	public void updateOrderItemsPrice() {
		// update the order item price with the current product price
		orderItems.stream().filter((i) -> i.getProduct()!=null).forEach(i -> i.setPrice(i.getProduct().getPrice()));
		
	
	}

}
