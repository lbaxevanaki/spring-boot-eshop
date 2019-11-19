package com.lbaxevanaki.eshop.order;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProductsOrderRepository extends CrudRepository<ProductsOrder, Long> {
	public List<ProductsOrder> findAllByCreationDateTimeBetween(Date publicationTimeStart, Date publicationTimeEnd);

}
