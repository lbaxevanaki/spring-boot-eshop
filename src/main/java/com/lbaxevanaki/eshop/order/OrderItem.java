package com.lbaxevanaki.eshop.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.lbaxevanaki.eshop.product.Product;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class OrderItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	@ApiModelProperty(notes = "The product that has been ordered ")
	private Product product;
	
	@Column(nullable=false)
	@NotNull
	@ApiModelProperty(notes = "The quantity of the product that has been ordered ")
	@Min(1)
	private Short quantity = new Short((short) 0);
	
	@Column(nullable=false)
	@NotNull
	@ApiModelProperty(notes = "The current product price ")
	private Double price = new Double(0.0);
	
	 @ManyToOne
	 private ProductsOrder productsOrder;
	
	 public OrderItem() {
		 
	 }
	public OrderItem(Product product, Short quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.price = product.getPrice();
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
		price = product.getPrice();
	}
	public Short getQuantity() {
		return quantity;
	}
	public void setQuantity(Short quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		if (price == null) {
			price = product.getPrice();
		}
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
