package com.hbm.entity.item;

import com.hbm.blocks.ModBlocks;

import api.hbm.conveyor.IConveyorItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityMovingItem extends Entity implements IConveyorItem {
	
	private int turnProgress;
	private double syncPosX;
	private double syncPosY;
	private double syncPosZ;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;

	public EntityMovingItem(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(0.5F, 0.5F);
		this.noClip = true;
	}

	public void setItemStack(ItemStack stack) {

		this.getDataWatcher().updateObject(10, stack);
		this.getDataWatcher().setObjectWatched(10);
	}

	public ItemStack getItemStack() {

		ItemStack stack = this.getDataWatcher().getWatchableObjectItemStack(10);
		return stack == null ? new ItemStack(Blocks.stone) : stack;
	}

	public boolean canBeCollidedWith() {
		return true;
	}

	public boolean interactFirst(EntityPlayer player) {

		if(!worldObj.isRemote && player.inventory.addItemStackToInventory(this.getItemStack().copy())) {
			this.setDead();
		}

		return false;
	}

	public boolean attackEntityFrom(DamageSource source, float amount) {

		if(!worldObj.isRemote) {
			this.setDead();
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, this.getItemStack()));
		}
		return true;
	}

	public boolean canAttackWithItem() {
		return true;
	}

	public boolean hitByEntity(Entity attacker) {

		if(attacker instanceof EntityPlayer) {
		}

		this.setDead();

		return false;
	}

	protected boolean canTriggerWalking() {
		return true;
	}

	private int schedule = 0;

	public void onUpdate() {
		
		if(worldObj.isRemote) {
			if(this.turnProgress > 0) {
				double interpX = this.posX + (this.syncPosX - this.posX) / (double) this.turnProgress;
				double interpY = this.posY + (this.syncPosY - this.posY) / (double) this.turnProgress;
				double interpZ = this.posZ + (this.syncPosZ - this.posZ) / (double) this.turnProgress;
				--this.turnProgress;
				this.setPosition(interpX, interpY, interpZ);
			} else {
				this.setPosition(this.posX, this.posY, this.posZ);
			}
		}

		if(!worldObj.isRemote) {

			if(worldObj.getBlock((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ)) != ModBlocks.conveyor) {
				this.setDead();
				EntityItem item = new EntityItem(worldObj, posX, posY, posZ, this.getItemStack());
				item.motionX = this.motionX * 3;
				item.motionY = 0.1;
				item.motionZ = this.motionZ * 3;
				item.velocityChanged = true;
				worldObj.spawnEntityInWorld(item);
				return;
			}

			if(worldObj.getBlock((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ)) == ModBlocks.conveyor) {

				if(schedule <= 0) {
					ForgeDirection dir = ForgeDirection.getOrientation(worldObj.getBlockMetadata((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ)));

					if(worldObj.getBlock((int) Math.floor(posX), (int) Math.floor(posY) + 1, (int) Math.floor(posZ)) == ModBlocks.conveyor && motionY >= 0) {
						dir = ForgeDirection.DOWN;
					}

					if(worldObj.getBlock((int) Math.floor(posX), (int) Math.floor(posY) - 1, (int) Math.floor(posZ)) == ModBlocks.conveyor && motionY <= 0) {
						dir = ForgeDirection.UP;
					}

					double speed = 0.0625;

					schedule = (int) (1 / speed);
					motionX = -speed * dir.offsetX;
					motionY = -speed * dir.offsetY;
					motionZ = -speed * dir.offsetZ;

					this.velocityChanged = true;
				}

				schedule--;
			}
			
			this.moveEntity(motionX, motionY, motionZ);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
		this.velocityX = this.motionX = p_70016_1_;
		this.velocityY = this.motionY = p_70016_3_;
		this.velocityZ = this.motionZ = p_70016_5_;
	}
	
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int theNumberThree) {
		this.syncPosX = x;
		this.syncPosY = y;
		this.syncPosZ = z;
		this.turnProgress = theNumberThree + 7; //use 4-ply for extra smoothness
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
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

		schedule = nbt.getInteger("schedule");

		if(stack == null || stack.stackSize <= 0)
			this.setDead();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {

		if(this.getItemStack() != null)
			nbt.setTag("Item", this.getItemStack().writeToNBT(new NBTTagCompound()));

		nbt.setInteger("schedule", schedule);
	}
}
