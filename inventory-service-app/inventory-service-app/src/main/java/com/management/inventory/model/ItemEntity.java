package com.management.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemEntity {

	@Id
	@Column(name = "SKU", nullable = false)
	private String SKU;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "count", nullable = false, columnDefinition = "int default 0")
	private int count;

	public Item toItem() {
		return Item.builder().SKU(this.SKU).name(this.name).count(this.count).build();
	}
}
