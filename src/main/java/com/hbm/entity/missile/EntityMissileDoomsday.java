package com.hbm.entity.missile;

import java.util.List;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMissileDoomsday extends EntityMissileBaseNT {

	public EntityMissileDoomsday(World world) {
		super(world);
	}
	
	public EntityMissileDoomsday(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}
	
	@Override
	public void onImpact() {
		this.worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(worldObj, BombConfig.missileRadius * 2, posX, posY, posZ).moreFallout(100));
		EntityNukeTorex.statFac(worldObj, posX, posY, posZ, BombConfig.missileRadius * 2);
	}

	@Override
	protected void spawnContrail() {
		
		byte rot = this.dataWatcher.getWatchableObjectByte(3);
		
		Vec3 thrust = Vec3.createVectorHelper(0, 0, 1);
		switch(rot) {
		case 2: thrust.rotateAroundY((float) -Math.PI / 2F); break;
		case 4: thrust.rotateAroundY((float) -Math.PI); break;
		case 3: thrust.rotateAroundY((float) -Math.PI / 2F * 3F);  break;
		}
		thrust.rotateAroundY((this.rotationYaw + 90) * (float) Math.PI / 180F);
		thrust.rotateAroundX(this.rotationPitch * (float) Math.PI / 180F);
		thrust.rotateAroundY(-(this.rotationYaw + 90) * (float) Math.PI / 180F);

		this.spawnContraolWithOffset(thrust.xCoord, thrust.yCoord, thrust.zCoord);
		this.spawnContraolWithOffset(0, 0, 0);
		this.spawnContraolWithOffset(-thrust.xCoord, -thrust.zCoord, -thrust.zCoord);
	}

	@Override public List<ItemStack> getDebris() { return null; }
	@Override public ItemStack getDebrisRareDrop() { return null; }
	@Override public String getUnlocalizedName() { return "radar.target.doomsday"; }
	@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_doomsday); }
}
