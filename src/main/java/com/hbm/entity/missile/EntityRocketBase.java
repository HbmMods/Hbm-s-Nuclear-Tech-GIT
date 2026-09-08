package com.hbm.entity.missile;

import java.util.List;

import com.hbm.items.ISatChip;
import com.hbm.saveddata.satellites.XSatelliteRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public abstract class EntityRocketBase extends Entity {

	protected ItemStack[] payload;
	protected double acceleration = 0.00D;

	public EntityRocketBase(World world, int payloadCapacity) {
		super(world);
		this.ignoreFrustumCheck = true;
		this.payload = new ItemStack[payloadCapacity];
	}

	@Override
	protected void entityInit() { }
	
	@Override
	public void onUpdate() {
		
		if(motionY < 2.0D) {
			acceleration += 0.00025D;
			motionY += acceleration;
		}

		this.prevPosX = this.lastTickPosX = this.posX;
		this.prevPosY = this.lastTickPosY = this.posY;
		this.prevPosZ = this.lastTickPosZ = this.posZ;
		
		this.setPosition(posX + this.motionX, posY + this.motionY, posZ + this.motionZ);
		
		if(this.posY > 600) {
			deployPayload();
			this.setDead();
		}
	}

	public void setSat(ItemStack stack) {
		this.payload[0] = stack;
	}
	
	public void setPayload(List<ItemStack> payload) {
		
		for(int i = 0; i < payload.size(); i++) {
			this.payload[i] = payload.get(i);
		}
	}
	
	protected void deployPayload() {
		
		if(payload != null && payload[0] != null) {
			
			ItemStack load = payload[0];
			
			if(load.getItem() instanceof ISatChip) {
				int freq = ISatChip.getFreqS(load);
				XSatelliteRegistry.orbit(worldObj, load, freq, posX, posY, posZ);
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {

		NBTTagList list = nbt.getTagList("items", 10);

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < payload.length) {
				payload[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {

		NBTTagList list = new NBTTagList();

		for (int i = 0; i < payload.length; i++) {
			if (payload[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				payload[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 500000;
	}
}
