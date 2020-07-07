package com.management.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.inventory.model.ItemEntity;

@Repository
public interface InventoryRepository extends JpaRepository<ItemEntity, String> {

}