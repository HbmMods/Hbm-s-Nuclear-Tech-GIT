package com.hbm.entity.missile;

import com.hbm.entity.projectile.EntityThrowableInterp;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySatellitePod extends EntityThrowableInterp {
	
	public ItemStack[] slots = new ItemStack[0];
	
	// wait timer for when the pod lands and unloads
	public int timer = 0;
	
	// y height of the pad that has called this pod, used for braking in time
	public int callerYPos;
	
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
		this.setSize(1F, 3F);
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
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(worldObj.isRemote) {
			this.prevLegs = this.legs;
			
			if(doesDeployLegs()) {
				this.legs += LEG_SPEED;
			} else {
				this.legs -= LEG_SPEED;
			}
			
			this.legs = MathHelper.clamp_float(this.legs, 0F, 1F);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		dataWatcher.updateObject(DW_STATE, nbt.getInteger("state"));
		timer = nbt.getInteger("timer");

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
}
