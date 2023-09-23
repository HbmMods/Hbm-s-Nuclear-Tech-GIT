package com.hbm.entity.item;

import com.hbm.entity.logic.IChunkLoader;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityDeliveryDrone extends EntityDroneBase implements IInventory, IChunkLoader {
	
	protected ItemStack[] slots = new ItemStack[this.getSizeInventory()];
	public FluidStack fluid;

	public double targetX = -1;
	public double targetY = -1;
	public double targetZ = -1;
	
	private Ticket loaderTicket;
	public boolean isChunkLoading = false;
	
	public EntityDeliveryDrone(World world) {
		super(world);
		this.setSize(1.5F, 2.0F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(11, new Byte((byte) 0));
	}
	
	public EntityDeliveryDrone setChunkLoading() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
		return this;
	}
	
	public void setTarget(double x, double y, double z) {
		this.targetX = x;
		this.targetY = y;
		this.targetZ = z;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!worldObj.isRemote) {

			this.motionX = 0;
			this.motionY = 0;
			this.motionZ = 0;
			
			if(this.targetY != -1) {
				
				Vec3 dist = Vec3.createVectorHelper(targetX - posX, targetY - posY, targetZ - posZ);
				double speed = getSpeed();
				
				if(dist.lengthVector() >= speed) {
					dist = dist.normalize();
					this.motionX = dist.xCoord * speed;
					this.motionY = dist.yCoord * speed;
					this.motionZ = dist.zCoord * speed;
				}
			}
			
			loadNeighboringChunks((int)Math.floor(posX / 16D), (int)Math.floor(posZ / 16D));
			
			this.moveEntity(motionX, motionY, motionZ);
		}
	}

	@Override
	public double getSpeed() {
		return this.dataWatcher.getWatchableObjectByte(11) == 1 ? 0.375 : 0.125;
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {

		nbt.setDouble("tX", targetX);
		nbt.setDouble("tY", targetY);
		nbt.setDouble("tZ", targetZ);
		
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
			nbt.setString("fluidType", fluid.type.getUnlocalizedName());
			nbt.setInteger("fluidAmount", fluid.fill);
		}

		nbt.setByte("app", this.dataWatcher.getWatchableObjectByte(10));
		nbt.setByte("load", this.dataWatcher.getWatchableObjectByte(11));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {

		if(nbt.hasKey("tY")) {
			this.targetX = nbt.getDouble("tX");
			this.targetY = nbt.getDouble("tY");
			this.targetZ = nbt.getDouble("tZ");
		}
		
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
			this.fluid = new FluidStack(Fluids.fromName(nbt.getString("fluidType")), nbt.getInteger("fluidAmount"));
		}

		this.dataWatcher.updateObject(10, nbt.getByte("app"));
		this.dataWatcher.updateObject(11, nbt.getByte("load"));
	}
	
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int theNumberThree) {
		this.syncPosX = x;
		this.syncPosY = y;
		this.syncPosZ = z;
		this.turnProgress = theNumberThree;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
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
			ForgeChunkManager.forceChunk(loaderTicket, new ChunkCoordIntPair(newChunkX + (int) Math.ceil((this.posX + this.motionX) / 16D), newChunkZ + (int) Math.ceil((this.posZ + this.motionZ) / 16D)));
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
