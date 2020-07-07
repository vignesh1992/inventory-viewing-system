package com.management.inventory.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.inventory.model.Item;
import com.management.inventory.model.ItemEntity;
import com.management.inventory.repository.InventoryRepository;

@Service
public class InventoryService {

	private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

	private final InventoryRepository inventoryRepository;

	private final PublishToTopic publishToTopic;
	
	@Autowired
	public InventoryService(PublishToTopic publishToTopic, InventoryRepository inventoryRepository) {
		this.publishToTopic = publishToTopic;
		this.inventoryRepository = inventoryRepository;
	}

	public List<Item> fetchAllItems() {

		List<ItemEntity> itemEntity = inventoryRepository.findAll();

		if (itemEntity.isEmpty()) {
			logger.info("No items present, returning empty list");
			return Collections.emptyList();
		}

		List<Item> items = itemEntity.stream().map(i -> i.toItem()).collect(Collectors.toList());

		logger.info("Returning the list of records from the inventory with count {}", itemEntity.size());
		return items;
	}

	public Item addItem(Item item) {

		Optional<ItemEntity> optionalItem = inventoryRepository.findById(item.getSKU());

		if (!optionalItem.isPresent()) {
			ItemEntity itemEntity = inventoryRepository.save(item.toItemEntity());

			// Publish change to the Topic
			publishToTopic.send(itemEntity.toItem());

			logger.info("Successfully added the item to the inventory with SKU {}", item.getSKU());
			return itemEntity.toItem();
		}

		return null;

	}

	public Item updateItem(String sku, Item item) {
		Optional<ItemEntity> optionalItem = inventoryRepository.findById(sku);

		if (optionalItem.isPresent()) {
			ItemEntity itemEntity = inventoryRepository.save(item.toItemEntity());

			// Publish change to the Topic
			publishToTopic.send(itemEntity.toItem());

			logger.info("Successfully updated the item in the inventory with SKU {}", item.getSKU());
			return itemEntity.toItem();
		}

		return null;
	}

	public boolean deleteItem(String sku) {
		Optional<ItemEntity> optionalItem = inventoryRepository.findById(sku);

		if (optionalItem.isPresent()) {
			inventoryRepository.deleteById(sku);

			// Publish change to the Topic
			publishToTopic.send(optionalItem.get().toItem());

			logger.info("Successfully deleted the item in the inventory with SKU {}", optionalItem.get().getSKU());
			return true;
		}

		return false;
	}

}
