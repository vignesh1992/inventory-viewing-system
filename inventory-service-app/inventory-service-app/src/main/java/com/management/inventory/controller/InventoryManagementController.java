package com.management.inventory.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.inventory.model.Item;
import com.management.inventory.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryManagementController {

	@Autowired
	private InventoryService inventoryService;

	private static final Logger logger = LoggerFactory.getLogger(InventoryManagementController.class);

	@GetMapping
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<Item>> retrieveInventoryRecords() {
		logger.info("Attempting to fetch all items from the inventory");

		List<Item> items = inventoryService.fetchAllItems();

		return ResponseEntity.status(HttpStatus.OK).body(items);

	}

	@PostMapping
	public ResponseEntity<Item> addItemToInventory(@RequestBody Item item) {

		logger.info("Attempting to add item to the inventory");

		Item addedItem = inventoryService.addItem(item);

		if (addedItem == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(addedItem);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(addedItem);
	}

	@PutMapping("/{sku}")
	public ResponseEntity<Item> updateItemInInventory(@PathVariable(name = "sku") String sku, @RequestBody Item item) {

		logger.info("Attempting to update item in the inventory");

		Item updatedItem = inventoryService.updateItem(sku, item);

		if (updatedItem == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(updatedItem);
	}

	@DeleteMapping("/{sku}")
	public ResponseEntity<Item> deleteItemInInventory(@PathVariable(name = "sku") String sku) {

		logger.info("Attempting to delete item in the inventory");

		boolean deletedItem = inventoryService.deleteItem(sku);

		if (!deletedItem) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
