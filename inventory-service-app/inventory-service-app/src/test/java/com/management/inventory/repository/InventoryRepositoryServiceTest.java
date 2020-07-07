package com.management.inventory.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.management.inventory.model.ItemEntity;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class InventoryRepositoryServiceTest {

	 @Autowired
	 private InventoryRepository inventoryRepository;
	 
	 @Autowired    
	 TestEntityManager entityManager; 

	 @Test
	 public void testSaveEmployee() {
		 ItemEntity itemEntity = ItemEntity.builder().name("iPad Pro").count(1).SKU("S12T-APP-RS").build();
		 
		 entityManager.persistAndFlush(itemEntity);        
		 assertTrue(inventoryRepository.findAll().contains(itemEntity));
	 }
	 
	 @Test
	 public void testDeleteEmployee() {
		 ItemEntity itemEntity = ItemEntity.builder().name("iPad Pro").count(1).SKU("S12T-APP-RS").build();
		 
		 entityManager.persistAndFlush(itemEntity);
		 assertTrue(inventoryRepository.findAll().contains(itemEntity));
		 
		 inventoryRepository.delete(itemEntity);
		 assertFalse(inventoryRepository.findAll().contains(itemEntity));
	 }
	 
	 @Test
	 public void testUpdateEmployee() {
		 ItemEntity itemEntity = ItemEntity.builder().name("iPad Pro").count(1).SKU("S12T-APP-RS").build();
		 
		 entityManager.persistAndFlush(itemEntity);
		 assertTrue(inventoryRepository.findAll().contains(itemEntity));
		 
		 itemEntity.setCount(2);
		 inventoryRepository.save(itemEntity);
		 assertEquals(inventoryRepository.findById("S12T-APP-RS").get().getCount(), 2);
	 }
}