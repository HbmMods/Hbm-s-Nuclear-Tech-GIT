package com.hbm.entity.cart;

import com.hbm.items.tool.ItemModMinecart;
import com.hbm.items.tool.ItemModMinecart.EnumCartBase;
import com.hbm.items.tool.ItemModMinecart.EnumMinecart;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMinecartOre extends EntityMinecartNTM {

	public EntityMinecartOre(World p_i1712_1_) {
		super(p_i1712_1_);
	}

	public EntityMinecartOre(World world, double x, double y, double z, EnumCartBase type) {
		super(world, x, y, z, type);
	}

	@Override
	public ItemStack getCartItem() {
		return ItemModMinecart.createCartItem(this.getBase(), EnumMinecart.EMPTY);
	}
}
