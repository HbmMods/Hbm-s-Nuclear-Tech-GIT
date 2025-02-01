package com.hbm.entity.item;

import com.hbm.entity.logic.IChunkLoader;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityDeliveryDrone extends EntityDroneBase implements IInventory, IChunkLoader {
	
	protected ItemStack[] slots = new ItemStack[this.getSizeInventory()];
	public FluidStack fluid;
	
	protected boolean chunkLoading = false;
	private Ticket loaderTicket;
	
	public EntityDeliveryDrone(World world) {
		super(world);
	}

	@Override
	public boolean hitByEntity(Entity attacker) {

		if(attacker instanceof EntityPlayer && !worldObj.isRemote) {
			this.setDead();
			for (ItemStack stack : slots) {
				if(stack != null)
					this.entityDropItem(stack, 1F);
			}
			int meta = 0;

			//whether it is an express drone
			if(this.dataWatcher.getWatchableObjectByte(11) == 1)
				meta = 2;

			if(chunkLoading)
				meta += 1;

			this.entityDropItem(new ItemStack(ModItems.drone, 1, meta), 1F);
		}

		return false;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(11, new Byte((byte) 0));
	}
	
	public EntityDeliveryDrone setChunkLoading() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
		this.chunkLoading = true;
		return this;
	}

	@Override
	public void onUpdate() {
		
		if(!worldObj.isRemote) {
			loadNeighboringChunks((int)Math.floor(posX / 16D), (int)Math.floor(posZ / 16D));
		}
		
		super.onUpdate();
	}

	@Override
	public double getSpeed() {
		return this.dataWatcher.getWatchableObjectByte(11) == 1 ? 0.375 * 3 : 0.375;
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		
		NBTTagList nbttaglist = new NBTTagList();

		for(int i = 0; i < this.slots.length; ++i) {
			if(this.slots[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.slots[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("Items", nbttaglist);
		
		if(fluid != null) {
			nbt.setInteger("fluidType", fluid.type.getID());
			nbt.setInteger("fluidAmount", fluid.fill);
		}

		nbt.setByte("load", this.dataWatcher.getWatchableObjectByte(11));
		nbt.setBoolean("chunkLoading", chunkLoading);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		this.slots = new ItemStack[this.getSizeInventory()];

		for(int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if(j >= 0 && j < this.slots.length) {
				this.slots[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		
		if(nbt.hasKey("fluidType")) {
			FluidType type = Fluids.fromNameCompat(nbt.getString("fluidType"));
			if(type != Fluids.NONE) {
				nbt.removeTag(nbt.getString("fluidType"));
			} else
				type = Fluids.fromID(nbt.getInteger("fluidType"));
			
			this.fluid = new FluidStack(type, nbt.getInteger("fluidAmount"));
		}

		this.dataWatcher.updateObject(11, nbt.getByte("load"));
		if(nbt.getBoolean("chunkLoading")) this.setChunkLoading();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slots[slot];
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(this.slots[slot] != null) {
			ItemStack itemstack;

			if(this.slots[slot].stackSize <= amount) {
				itemstack = this.slots[slot];
				this.slots[slot] = null;
				return itemstack;
			} else {
				itemstack = this.slots[slot].splitStack(amount);

				if(this.slots[slot].stackSize == 0) {
					this.slots[slot] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(this.slots[slot] != null) {
			ItemStack itemstack = this.slots[slot];
			this.slots[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.slots[slot] = stack;

		if(stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override public int getSizeInventory() { return 18; }
	@Override public String getInventoryName() { return "container.drone"; }
	@Override public int getInventoryStackLimit() { return 64; }
	@Override public boolean hasCustomInventoryName() { return false; }
	@Override public boolean isUseableByPlayer(EntityPlayer player) { return false; }
	@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return false; }

	@Override public void markDirty() { }
	@Override public void openInventory() { }
	@Override public void closeInventory() { }

	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if(!worldObj.isRemote && loaderTicket != null) {
			clearChunkLoader();
			ForgeChunkManager.forceChunk(loaderTicket, new ChunkCoordIntPair(newChunkX, newChunkZ));
			ForgeChunkManager.forceChunk(loaderTicket, new ChunkCoordIntPair((int) Math.ceil((this.posX + this.motionX) / 16D), (int) Math.ceil((this.posZ + this.motionZ) / 16D)));
		}
	}
	
	@Override
	public void setDead() {
		super.setDead();
		this.clearChunkLoader();
	}
	
	public void clearChunkLoader() {
		if(!worldObj.isRemote && loaderTicket != null) {
			for(ChunkCoordIntPair chunk : loaderTicket.getChunkList()) {
				ForgeChunkManager.unforceChunk(loaderTicket, chunk);
			}
		}
	}

	@Override
	public void init(Ticket ticket) {
		if(!worldObj.isRemote && ticket != null) {
			if(loaderTicket == null) {
				loaderTicket = ticket;
				loaderTicket.bindEntity(this);
				loaderTicket.getModData();
			}
			ForgeChunkManager.forceChunk(loaderTicket, new ChunkCoordIntPair(chunkCoordX, chunkCoordZ));
		}
	}
}
