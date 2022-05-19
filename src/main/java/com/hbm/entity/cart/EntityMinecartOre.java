package com.hbm.entity.cart;

import com.hbm.items.tool.ItemModMinecart;
import com.hbm.items.tool.ItemModMinecart.EnumCartBase;
import com.hbm.items.tool.ItemModMinecart.EnumMinecart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMinecartOre extends EntityMinecart {

	public EntityMinecartOre(World p_i1712_1_) {
		super(p_i1712_1_);
	}

	public EntityMinecartOre(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	public int getMinecartType() {
		return -1;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entity) {
		return entity.boundingBox;
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return this.boundingBox;
	}

	@Override
	public void killMinecart(DamageSource p_94095_1_) {
		this.setDead();
		ItemStack itemstack = ItemModMinecart.createCartItem(EnumCartBase.STEEL, EnumMinecart.EMPTY);

		if(this.func_95999_t() != null) {
			itemstack.setStackDisplayName(this.func_95999_t());
		}

		this.entityDropItem(itemstack, 0.0F);
	}

	@Override
	public ItemStack getCartItem() {
		return ItemModMinecart.createCartItem(EnumCartBase.STEEL, EnumMinecart.EMPTY);
	}
}
