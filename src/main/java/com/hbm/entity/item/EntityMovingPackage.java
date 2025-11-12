package com.hbm.entity.item;

import com.hbm.util.ItemStackUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.conveyor.IConveyorPackage;
import api.hbm.conveyor.IEnterableBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityMovingPackage extends EntityMovingConveyorObject implements IConveyorPackage {
	
	protected ItemStack[] contents = new ItemStack[0];

	public EntityMovingPackage(World world) {
		super(world);
		this.setSize(0.5F, 0.5F);
	}

	@Override
	protected void entityInit() { }

	public void setItemStacks(ItemStack[] stacks) {
		this.contents = ItemStackUtil.carefulCopyArray(stacks);
	}

	@Override
	public ItemStack[] getItemStacks() {
		return contents;
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {

		if(!worldObj.isRemote && !this.isDead) {
			
			for(ItemStack stack : contents) {
				if(!player.inventory.addItemStackToInventory(stack.copy())) {
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY + 0.125, posZ, stack));
				}
			}
			
			this.setDead();
		}

		return false;
	}

	@Override
	public boolean hitByEntity(Entity attacker) {

		if(!worldObj.isRemote && !this.isDead) {
			this.setDead();
			
			for(ItemStack stack : contents) {
				worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY + 0.125, posZ, stack));
			}
		}
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		this.hitByEntity(source.getEntity());
		return true;
	}

	@Override
	public void enterBlock(IEnterableBlock enterable, BlockPos pos, ForgeDirection dir) {
		
		if(enterable.canPackageEnter(worldObj, pos.getX(), pos.getY(), pos.getZ(), dir, this)) {
			enterable.onPackageEnter(worldObj, pos.getX(), pos.getY(), pos.getZ(), dir, this);
			this.setDead();
		}
	}

	@Override
	public boolean onLeaveConveyor() {
		
		this.setDead();
		
		for(ItemStack stack : contents) {
			EntityItem item = new EntityItem(worldObj, posX + motionX * 2, posY + motionY * 2, posZ + motionZ * 2, stack);
			item.motionX = this.motionX * 2;
			item.motionY = 0.1;
			item.motionZ = this.motionZ * 2;
			item.velocityChanged = true;
			worldObj.spawnEntityInWorld(item);
		}
		
		return true;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		NBTTagList nbttaglist = new NBTTagList();

		for(int i = 0; i < this.contents.length; ++i) {
			if(this.contents[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("slot", (byte) i);
				this.contents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("contents", nbttaglist);
		nbt.setInteger("count", this.contents.length);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.contents = new ItemStack[nbt.getInteger("count")];
		NBTTagList nbttaglist = nbt.getTagList("contents", 10);

		for(int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("slot") & 255;

			if(j >= 0 && j < this.contents.length) {
				this.contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}
}
