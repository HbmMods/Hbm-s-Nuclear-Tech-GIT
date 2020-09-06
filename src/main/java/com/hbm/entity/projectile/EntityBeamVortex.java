package com.hbm.entity.projectile;

import java.util.List;

import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBeamVortex extends EntityBeamBase {

	public EntityBeamVortex(World world) {
		super(world);
	}

	public EntityBeamVortex(World world, EntityPlayer player) {
		super(world, player);
	}
	
	@Override
	public void onUpdate() {
		
		if(this.ticksExisted > 10)
			this.setDead();
		
		if(this.ticksExisted > 1)
			return;
		
		int range = 100;
		
		EntityPlayer player = worldObj.getPlayerEntityByName(this.dataWatcher.getWatchableObjectString(20));
		
		if(player != null) {
			
			MovingObjectPosition pos = Library.rayTrace(player, range, 1, false, true, false);
			
			if(pos == null)
				return;
			
			worldObj.spawnParticle("cloud", pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, 0, 0, 0);
			worldObj.playSound(pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, "random.fizz", 1, 1, true);
			
			List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(pos.hitVec.xCoord - 1, pos.hitVec.yCoord - 1, pos.hitVec.zCoord - 1, pos.hitVec.xCoord + 1, pos.hitVec.yCoord + 1, pos.hitVec.zCoord + 1));
			
			for(Entity e : list)
				e.attackEntityFrom(ModDamageSource.radiation, 5);
		}
	}

}
