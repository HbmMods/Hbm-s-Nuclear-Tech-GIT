package com.hbm.entity.effect;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityCloudFleija extends Entity {

	public int maxAge = 100;
	public int age;
	public float scale = 0;

	public EntityCloudFleija(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(1, 4);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;
		scale = 0;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, Integer.valueOf(0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}

	@Override
	public float getBrightness(float p_70013_1_) {
		return 1.0F;
	}

	public EntityCloudFleija(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		this.setSize(20, 40);
		this.isImmuneToFire = true;
		this.setMaxAge(maxAge);
	}

	@Override
	public void onUpdate() {
		this.age++;
		this.worldObj.lastLightningBolt = 2;

		if(!worldObj.isRemote && this.age >= this.getMaxAge()) {
			this.age = 0;
			this.setDead();
		}

		this.scale++;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		age = nbt.getShort("age");
		scale = nbt.getShort("scale");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setShort("age", (short) age);
		nbt.setShort("scale", (short) scale);

	}

	public void setMaxAge(int i) {
		this.dataWatcher.updateObject(16, Integer.valueOf(i));
	}

	public int getMaxAge() {
		return this.dataWatcher.getWatchableObjectInt(16);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 25000;
	}
}
