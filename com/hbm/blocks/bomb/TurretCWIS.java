package com.hbm.blocks.bomb;

import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.tileentity.bomb.TileEntityTurretCWIS;
import com.hbm.tileentity.bomb.TileEntityTurretSpitfire;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TurretCWIS extends TurretBase {

	public TurretCWIS(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityTurretCWIS();
	}

	@Override
	public boolean executeHoldAction(World world, int i, double yaw, double pitch, int x, int y, int z) {
		
		boolean flag = false;
		
		if(pitch < -60)
			pitch = -60;
		if(pitch > 30)
			pitch = 30;
		
		TileEntityTurretCWIS te = (TileEntityTurretCWIS)world.getTileEntity(x, y, z);
		
		if(te.spin < 35)
			te.spin += 5;
		
		if(te.spin > 25) {
			Vec3 vector = Vec3.createVectorHelper(
					-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI),
					-Math.sin(pitch / 180.0F * (float) Math.PI),
					Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI));
			
			vector.normalize();
			
			if(!world.isRemote) {
				EntityBullet bullet = new EntityBullet(world);
				bullet.posX = x + vector.xCoord * 2.5 + 0.5;
				bullet.posY = y + vector.yCoord * 2.5 + 1.5;
				bullet.posZ = z + vector.zCoord * 2.5 + 0.5;
				
				bullet.motionX = vector.xCoord * 3;
				bullet.motionY = vector.yCoord * 3;
				bullet.motionZ = vector.zCoord * 3;

				bullet.setDamage(65 + rand.nextInt(55));
				
				world.spawnEntityInWorld(bullet);
				
				EntityGasFlameFX smoke = new EntityGasFlameFX(world);
				smoke.posX = x + vector.xCoord * 2.5 + 0.5;
				smoke.posY = y + vector.yCoord * 2.5 + 1;
				smoke.posZ = z + vector.zCoord * 2.5 + 0.5;
				
				smoke.motionX = vector.xCoord * 0.25;
				smoke.motionY = vector.yCoord * 0.25;
				smoke.motionZ = vector.zCoord * 0.25;
				
				world.spawnEntityInWorld(smoke);
			}

			world.playSoundEffect(x, y, z, "hbm:entity.oldExplosion", 1.0F, 0.5F);
			
			flag = true;
		}
		
		return flag;
	}

	@Override
	public void executeReleaseAction(World world, int i, double yaw, double pitch, int x, int y, int z) { }
}
