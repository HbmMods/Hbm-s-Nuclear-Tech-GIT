package com.hbm.blocks.turret;

import com.hbm.entity.projectile.EntityAAShell;
import com.hbm.tileentity.turret.TileEntityTurretSpitfire;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TurretSpitfire extends TurretBase {

	public TurretSpitfire(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityTurretSpitfire();
	}

	@Override
	public boolean executeHoldAction(World world, int i, double yaw, double pitch, int x, int y, int z) {
		
		boolean flag = false;
		
		if(pitch < -60)
			pitch = -60;
		if(pitch > 30)
			pitch = 30;
		
		if(i != 0 && i % 35 == 0) {
			Vec3 vector = Vec3.createVectorHelper(
					-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI),
					-Math.sin(pitch / 180.0F * (float) Math.PI),
					Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI));
			
			vector.normalize();
			
			if(!world.isRemote) {
				EntityAAShell bullet = new EntityAAShell(world);
				bullet.posX = x + vector.xCoord * 2.75 + 0.5;
				bullet.posY = y + vector.yCoord * 2.75 + 1.5;
				bullet.posZ = z + vector.zCoord * 2.75 + 0.5;
				
				bullet.motionX = vector.xCoord;
				bullet.motionY = vector.yCoord;
				bullet.motionZ = vector.zCoord;
				
				bullet.speedOverride = 3;
				
				world.spawnEntityInWorld(bullet);
			}

			world.playSoundEffect(x, y, z, "hbm:entity.oldExplosion", 1.0F, 0.5F);
			
			flag = true;
		}
		
		return flag;
	}

	@Override
	public void executeReleaseAction(World world, int i, double yaw, double pitch, int x, int y, int z) { }
}
