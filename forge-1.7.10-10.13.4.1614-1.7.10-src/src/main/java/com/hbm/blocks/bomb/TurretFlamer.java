package com.hbm.blocks.bomb;

import com.hbm.entity.projectile.EntityFire;
import com.hbm.tileentity.bomb.TileEntityTurretFlamer;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TurretFlamer extends TurretBase {

	public TurretFlamer(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityTurretFlamer();
	}

	@Override
	public boolean executeHoldAction(World world, int i, double yaw, double pitch, int x, int y, int z) {
		
		boolean flag = false;
		
		if(pitch < -60)
			pitch = -60;
		if(pitch > 30)
			pitch = 30;
		
		if(true) {
			Vec3 vector = Vec3.createVectorHelper(
					-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI),
					-Math.sin(pitch / 180.0F * (float) Math.PI),
					Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI));
			
			vector.normalize();
			
			if(!world.isRemote) {
				EntityFire bullet = new EntityFire(world);
				bullet.posX = x + vector.xCoord * 2 + 0.5;
				bullet.posY = y + vector.yCoord * 2 + 1;
				bullet.posZ = z + vector.zCoord * 2 + 0.5;
				
				bullet.motionX = vector.xCoord * 3;
				bullet.motionY = vector.yCoord * 3;
				bullet.motionZ = vector.zCoord * 3;

				bullet.setDamage(6 + rand.nextInt(5));
				
				world.spawnEntityInWorld(bullet);
			}
			
			if(i == 0)
				world.playSoundEffect(x, y, z, "hbm:weapon.flamethrowerIgnite", 1.0F, 1.0F);
			else
				world.playSoundEffect(x, y, z, "hbm:weapon.flamethrowerShoot", 1.0F, 1.0F);
			
			flag = true;
		}
		
		return flag;
	}

	@Override
	public void executeReleaseAction(World world, int i, double yaw, double pitch, int x, int y, int z) { }
	
}
