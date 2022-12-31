package com.hbm.entity.cart;

import com.hbm.items.tool.ItemModMinecart.EnumCartBase;
import com.hbm.render.entity.item.RenderNeoCart;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityMinecartNTM extends EntityMinecart {
	
	public static final int cart_base_id = 23;

	public EntityMinecartNTM(World p_i1712_1_) {
		super(p_i1712_1_);
	}

	public EntityMinecartNTM(World world, double x, double y, double z, EnumCartBase type) {
		super(world, x, y, z);
		this.setBase(type);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(cart_base_id, new Integer(0)); //EnumCartBase
	}
	
	public void setBase(EnumCartBase type) {
		this.dataWatcher.updateObject(cart_base_id, type.ordinal());
	}
	
	public EnumCartBase getBase() {
		return EnumCartBase.values()[this.dataWatcher.getWatchableObjectInt(cart_base_id)];
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
		ItemStack itemstack = getCartItem();

		if(this.func_95999_t() != null) {
			itemstack.setStackDisplayName(this.func_95999_t());
		}

		this.entityDropItem(itemstack, 0.0F);
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("base", this.dataWatcher.getWatchableObjectInt(cart_base_id));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.dataWatcher.updateObject(23, nbt.getInteger("base"));
	}
	
	@SideOnly(Side.CLIENT)
	public void renderSpecialContent(RenderNeoCart renderer) { }
}
