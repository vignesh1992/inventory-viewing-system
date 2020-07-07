package com.management.inventory.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

	@NotBlank
	private String SKU;

	@NotBlank
	private String name;

	private int count;

	public ItemEntity toItemEntity() {
		return ItemEntity.builder().SKU(this.SKU).name(this.name).count(this.count).build();
	}

}
