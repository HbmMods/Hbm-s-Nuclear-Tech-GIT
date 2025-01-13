package com.hbm.entity.item;

import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.conveyor.IConveyorItem;
import api.hbm.conveyor.IEnterableBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityMovingItem extends EntityMovingConveyorObject implements IConveyorItem {

	public EntityMovingItem(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(0.375F, 0.375F);
	}

	public void setItemStack(ItemStack stack) {
		this.getDataWatcher().updateObject(10, stack);
		this.getDataWatcher().setObjectWatched(10);
	}

	@Override
	public ItemStack getItemStack() {
		ItemStack stack = this.getDataWatcher().getWatchableObjectItemStack(10);
		return stack == null ? new ItemStack(Blocks.stone) : stack;
	}

	/** Adds the item to the player's inventory */
	@Override
	public boolean interactFirst(EntityPlayer player) {

		if(!worldObj.isRemote && !this.isDead && player.inventory.addItemStackToInventory(this.getItemStack().copy())) {
			player.inventoryContainer.detectAndSendChanges();
			this.setDead();
		}

		return false;
	}

	/** Knocks the item off the belt */
	@Override
	public boolean hitByEntity(Entity attacker) {

		if(!worldObj.isRemote && !this.isDead) {
			this.setDead();
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, this.getItemStack()));
		}
		return false;
	}

	/** Ensures the item is knocked off the belt due to non-player attacks (explosions, etc) */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		this.hitByEntity(source.getEntity());
		return true;
	}

	@Override
	protected void entityInit() {
		this.getDataWatcher().addObjectByDataType(10, 5);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {

		NBTTagCompound compound = nbt.getCompoundTag("Item");
		this.setItemStack(ItemStack.loadItemStackFromNBT(compound));

		ItemStack stack = getDataWatcher().getWatchableObjectItemStack(10);

		if(stack == null || stack.stackSize <= 0)
			this.setDead();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {

		if(this.getItemStack() != null)
			nbt.setTag("Item", this.getItemStack().writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void enterBlock(IEnterableBlock enterable, BlockPos pos, ForgeDirection dir) {
		
		if(enterable.canItemEnter(worldObj, pos.getX(), pos.getY(), pos.getZ(), dir, this)) {
			enterable.onItemEnter(worldObj, pos.getX(), pos.getY(), pos.getZ(), dir, this);
			this.setDead();
		}
	}

	@Override
	public boolean onLeaveConveyor() {
		
		if(this.isDead) return true;
		
		this.setDead();
		EntityItem item = new EntityItem(worldObj, posX + motionX * 2, posY + motionY * 2, posZ + motionZ * 2, this.getItemStack());
		item.motionX = this.motionX * 2;
		item.motionY = 0.1;
		item.motionZ = this.motionZ * 2;
		item.velocityChanged = true;
		worldObj.spawnEntityInWorld(item);
		
		return true;
	}
}
