package com.hbm.blocks.bomb;

import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.tileentity.bomb.TileEntityTurretHeavy;
import com.hbm.tileentity.bomb.TileEntityTurretLight;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TurretLight extends TurretBase {

	public TurretLight(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityTurretLight();
	}

	@Override
	public boolean executeHoldAction(World world, int i, double yaw, double pitch, int x, int y, int z) {
		
		boolean flag = false;
		
		if(pitch < -60)
			pitch = -60;
		if(pitch > 30)
			pitch = 30;
		
		if(i != 0 && i % 2 == 0) {
			Vec3 vector = Vec3.createVectorHelper(
					-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI),
					-Math.sin(pitch / 180.0F * (float) Math.PI),
					Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI));
			
			vector.normalize();
			
			if(!world.isRemote) {
				EntityBullet bullet = new EntityBullet(world);
				bullet.posX = x + vector.xCoord * 1.5 + 0.5;
				bullet.posY = y + vector.yCoord * 1.5 + 1.1;
				bullet.posZ = z + vector.zCoord * 1.5 + 0.5;
				
				bullet.motionX = vector.xCoord * 3;
				bullet.motionY = vector.yCoord * 3;
				bullet.motionZ = vector.zCoord * 3;
				
				bullet.damage = rand.nextInt(11) + 10;
				
				world.spawnEntityInWorld(bullet);
			}

			world.playSoundEffect(x, y, z, "hbm:weapon.rifleShoot", 1.0F, 0.5F + rand.nextFloat() * 0.25F);
			
			flag = true;
		}
		
		return flag;
	}

	@Override
	public void executeReleaseAction(World world, int i, double yaw, double pitch, int x, int y, int z) { }

}
