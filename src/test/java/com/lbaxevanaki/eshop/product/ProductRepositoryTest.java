package com.lbaxevanaki.eshop.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepository productRespository;

    @Before
    public void setUp(){
    	Product product1 = new Product("PRODUCT A", 1000.0);
    	
        testEntityManager.persist(product1);
    }


    @Test
    public void whenFindAll_thenReturnProductList() {
        // when
    	List<Product> products = new ArrayList<Product>();
        productRespository.findAll().forEach(products::add);

        // then
        assertThat(products).hasSize(1);
    }
}