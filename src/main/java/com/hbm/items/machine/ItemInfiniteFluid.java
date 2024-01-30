package com.hbm.items.machine;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.ModItems;

import net.minecraft.item.Item;

public class ItemInfiniteFluid extends Item {

	private FluidType type;
	private int amount;
	private int chance;
	
	public ItemInfiniteFluid(FluidType type, int amount) {
		this(type, amount, 1);
	}
	
	public ItemInfiniteFluid(FluidType type, int amount, int chance) {
		this.type = type;
		this.amount = amount;
		this.chance = chance;
	}

	public FluidType getType() { return this.type; }
	public int getAmount() { return this.amount; }
	public int getChance() { return this.chance; }
	public boolean allowPressure(int pressure) { return this == ModItems.fluid_barrel_infinite || pressure == 0; }
}
