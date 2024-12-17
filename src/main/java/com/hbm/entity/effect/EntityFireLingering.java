package com.hbm.entity.effect;

import java.util.List;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.particle.helper.FlameCreator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityFireLingering extends Entity {

	public static int TYPE_DIESEL = 0;
	public static int TYPE_BALEFIRE = 1;
	public static int TYPE_PHOSPHORUS = 2;
	public int maxAge = 150;
	
	public EntityFireLingering(World world) {
		super(world);
	}
	
	public EntityFireLingering setArea(float width, float height) {
		this.dataWatcher.updateObject(11, width);
		this.dataWatcher.updateObject(12, height);
		return this;
	}
	public EntityFireLingering setDuration(int duration){
		this.maxAge = duration;
		return this;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(10, new Integer(0));
		this.dataWatcher.addObject(11, new Float(0));
		this.dataWatcher.addObject(12, new Float(0));
	}
	
	public EntityFireLingering setType(int type) {
		this.dataWatcher.updateObject(10, type);
		return this;
	}
	
	public int getType() {
		return this.dataWatcher.getWatchableObjectInt(10);
	}

	@Override
	public void onEntityUpdate() {
		
		float height = this.dataWatcher.getWatchableObjectFloat(12);
		this.yOffset = 0;
		this.setSize(this.dataWatcher.getWatchableObjectFloat(11), height);
		this.setPosition(this.posX, this.posY, this.posZ);
		
		if(!worldObj.isRemote) {
			
			if(this.ticksExisted >= maxAge) {
				this.setDead();
			}
			
			List<Entity> affected = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(posX - width / 2, posY, posZ - width / 2, posX + width / 2, posY + height, posZ + width / 2));
			
			for(Entity e : affected) {
				if(e instanceof EntityLivingBase) {
					EntityLivingBase livng = (EntityLivingBase) e;
					HbmLivingProps props = HbmLivingProps.getData(livng);
					if(this.getType() == this.TYPE_DIESEL) if(props.fire < 60) props.fire = 60;
					if(this.getType() == this.TYPE_PHOSPHORUS) if(props.fire < 300) props.fire = 300;
					if(this.getType() == this.TYPE_BALEFIRE) if(props.balefire < 100) props.balefire = 100;
				} else {
					e.setFire(4);
				}
			}
		} else {

			for(int i = 0; i < (width >= 5 ? 2 : 1); i++) {
				double x = posX - width / 2 + rand.nextDouble() * width;
				double z = posZ - width / 2 + rand.nextDouble() * width;
	
				Vec3 up = Vec3.createVectorHelper(x, posY + height, z);
				Vec3 down = Vec3.createVectorHelper(x, posY - height, z);
				MovingObjectPosition mop = worldObj.func_147447_a(up, down, false, true, true);
				if(mop != null && mop.typeOfHit == mop.typeOfHit.BLOCK) down = mop.hitVec;
				if(this.getType() == this.TYPE_DIESEL) FlameCreator.composeEffectClient(worldObj, x, down.yCoord, z, FlameCreator.META_FIRE);
				if(this.getType() == this.TYPE_PHOSPHORUS) FlameCreator.composeEffectClient(worldObj, x, down.yCoord, z, FlameCreator.META_FIRE);
				if(this.getType() == this.TYPE_BALEFIRE) FlameCreator.composeEffectClient(worldObj, x, down.yCoord, z, FlameCreator.META_BALEFIRE);
			}
		}
	}

	@Override @SideOnly(Side.CLIENT) public boolean canRenderOnFire() { return false; }
	@Override protected void writeEntityToNBT(NBTTagCompound nbt) { }
	@Override public boolean writeToNBTOptional(NBTTagCompound nbt) { return false; }
	@Override public void writeToNBT(NBTTagCompound nbt) { }
	@Override public void readEntityFromNBT(NBTTagCompound nbt) { this.setDead(); }
}
