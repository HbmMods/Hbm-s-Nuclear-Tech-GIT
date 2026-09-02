package com.hbm.entity.missile;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityThrowableInterp;
import com.hbm.tileentity.machine.TileEntityMachineSatDock;
import com.hbm.util.Compat;
import com.hbm.util.InventoryUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySatellitePod extends EntityThrowableInterp {
	
	public ItemStack[] slots = new ItemStack[0];
	
	// wait timer for when the pod lands and unloads
	public int timer = 0;
	
	// y height of the pad that has called this pod, used for braking in time
	public int callerYPos;
	
	public double speed = 0.75D;
	
	public static final int DW_STATE = 3;
	public static final int STATE_LEGS_UP = 0;
	public static final int STATE_LEGS_DOWN = 1;

	public float legs = 0F;
	public float prevLegs = 0F;
	public static final float LEG_SPEED = 1F / 40F; // 2 seconds

	public EntitySatellitePod(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.setSize(0.95F, 3F);
	}
	
	public EntitySatellitePod setup(int caller, ItemStack... cargo) {
		this.callerYPos = caller;
		this.slots = cargo;
		return this;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(DW_STATE, 0);
	}
	
	public boolean doesDeployLegs() {
		return this.dataWatcher.getWatchableObjectInt(DW_STATE) == STATE_LEGS_DOWN;
	}
	
	public void setDeployLegs(boolean deploy) {
		this.dataWatcher.updateObject(DW_STATE, deploy ? STATE_LEGS_DOWN : STATE_LEGS_UP);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!worldObj.isRemote) {
			
			if(this.isLanding()) {
				
				// unloading
				if(this.timer > 0) {
					this.timer++;
					
					if(this.timer >= 100) {
						this.unloadItems();
					}
					
				// just landed
				} else if(this.onGround) {
					this.speed = 0D;
					this.timer = 1;
					this.setDeployLegs(true);
					
				// still descending
				} else {
					if(this.posY < this.callerYPos + 10 && !this.doesDeployLegs()) this.setDeployLegs(true);
					if(this.posY < this.callerYPos + 20) this.speed -= 0.001;
					this.speed = MathHelper.clamp_double(this.speed, 0.025D, 0.75D);
				}
				
				this.motionY = -this.speed;
				
			} else {

				this.speed += 0.001;
				if(this.speed >= 0.05) this.setDeployLegs(false);
				this.speed = MathHelper.clamp_double(this.speed, 0D, 1D);
				this.motionY = this.speed;
				
				if(this.posY > 300) this.setDead();
			}
			
		} else {
			
			this.prevLegs = this.legs;
			
			if(doesDeployLegs()) {
				this.legs += LEG_SPEED;
			} else {
				this.legs -= LEG_SPEED;
			}
			
			this.legs = MathHelper.clamp_float(this.legs, 0F, 1F);
		}
	}
	
	/** Tries to fill a sat dock that this pod is standing on. All items that cannot be added to a dock will be spilled and removed from the pod's inventory. */
	public void unloadItems() {

		int x = (int) Math.floor(posX);
		int y = (int) Math.floor(posY - 0.5);
		int z = (int) Math.floor(posZ);
		
		if(worldObj.getBlock(x, y, z) == ModBlocks.sat_dock) {
			TileEntity tile = Compat.getTileStandard(worldObj, x, y, z);
			if(tile instanceof TileEntityMachineSatDock) {
				TileEntityMachineSatDock satDock = (TileEntityMachineSatDock) tile;
				
				for(int i = 0; i < this.slots.length; i++) {
					if(slots[i] == null || slots[i].stackSize <= 0) continue;
					slots[i] = InventoryUtil.tryAddItemToInventory(satDock.slots, 0, 14, slots[i]);
				}
			}
		}
		
		for(int i = 0; i < this.slots.length; i++) {
			if(slots[i] == null || slots[i].stackSize <= 0) continue;
			this.entityDropItem(slots[i], 0.25F);
		}
		
		this.slots = new ItemStack[0];
	}
	
	public boolean isLanding() {
		return this.slots.length > 0;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		dataWatcher.updateObject(DW_STATE, nbt.getInteger("state"));
		timer = nbt.getInteger("timer");
		speed = nbt.getDouble("speed");

		int itemCount = nbt.getInteger("itemCount");
		NBTTagList items = nbt.getTagList("items", 10);
		
		this.slots = new ItemStack[itemCount];

		for(int i = 0; i < items.tagCount(); i++) {
			
			NBTTagCompound itemTag = items.getCompoundTagAt(i);
			int j = itemTag.getByte("slot") & 255;

			if(j >= 0 && j < this.slots.length) {
				this.slots[j] = ItemStack.loadItemStackFromNBT(itemTag);
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("state", dataWatcher.getWatchableObjectInt(DW_STATE));
		nbt.setInteger("timer", timer);
		nbt.setDouble("speed", speed);

		nbt.setInteger("itemCount", this.slots.length);
		NBTTagList items = new NBTTagList();

		for(int i = 0; i < this.slots.length; i++) {
			
			if(this.slots[i] != null) {
				NBTTagCompound itemTag = new NBTTagCompound();
				itemTag.setByte("slot", (byte) i);
				this.slots[i].writeToNBT(itemTag);
				items.appendTag(itemTag);
			}
		}

		nbt.setTag("items", items);
	}

	@Override protected void onImpact(MovingObjectPosition mop) { }
	@Override protected boolean canTriggerWalking() { return false; }
	@Override public boolean doesImpactEntities() { return false; }
	
	@Override protected float getAirDrag() { return 1F; }
	@Override protected float getWaterDrag() { return 1F; }
	@Override public double getGravityVelocity() { return 0D; }
}
