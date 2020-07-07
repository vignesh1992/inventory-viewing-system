package com.management.inventory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.management.inventory.model.Item;
import com.management.inventory.service.InventoryService;

@SpringBootApplication
@ComponentScan(basePackages = { "com.management.inventory.service", "com.management.inventory.repository",
		"com.management.inventory.controller", "com.management.inventory.config", "com.management.inventory.model" })
public class InventoryManagementApplication {

	private InventoryService inventoryService;

	@Autowired
	public InventoryManagementApplication(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	public static void main(String[] args) {
		SpringApplication.run(InventoryManagementApplication.class, args);
	}

	@PostConstruct
	public void init() {

		// Populating the DB initially for test purpose.
		inventoryService.addItem(new Item("S17T-APP-RS", "iPhone X", 5));
		inventoryService.addItem(new Item("S18T-APP-RM", "iPhone XR", 10));
		inventoryService.addItem(new Item("S19T-APP-RK", "iPhone 11", 2));
		inventoryService.addItem(new Item("S20R-OPP-RS", "One Plus 6", 5));
		inventoryService.addItem(new Item("S21R-OPP-RM", "One Plus 7", 10));
		inventoryService.addItem(new Item("S22R-OPP-RK", "One Plus 8", 2));
		inventoryService.addItem(new Item("S23O-SAM-RS", "Samsung Note 6", 5));
		inventoryService.addItem(new Item("S24O-SAM-RM", "Samsung Note 7", 10));
		inventoryService.addItem(new Item("S25O-SAM-RK", "Samsung Note 9", 2));
		inventoryService.addItem(new Item("S26L-APP-RY", "iPad Air", 2));

	}

}
