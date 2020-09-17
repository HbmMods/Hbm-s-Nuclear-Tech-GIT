package com.hbm.blocks.bomb;

import com.hbm.entity.projectile.EntityBullet;
import com.hbm.tileentity.bomb.TileEntityTurretCheapo;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TurretCheapo extends TurretBase {

	public TurretCheapo(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityTurretCheapo();
	}

	@Override
	public boolean executeHoldAction(World world, int i, double yaw, double pitch, int x, int y, int z) {
		
		boolean flag = false;
		
		if(pitch < -30)
			pitch = -30;
		if(pitch > 15)
			pitch = 15;
		
		TileEntityTurretCheapo te = (TileEntityTurretCheapo)world.getTileEntity(x, y, z);
		
		if(i == 0 && te.spin < 10)
			world.playSoundEffect(x, y, z, "hbm:weapon.spinup", 1.0F, 1.0F);
		
		if(te.spin < 35)
			te.spin += 5;
		
		if(te.spin > 25 && i % 2 == 0) {
			Vec3 vector = Vec3.createVectorHelper(
					-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI),
					-Math.sin(pitch / 180.0F * (float) Math.PI),
					Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI));
			
			vector.normalize();
			
			if(!world.isRemote) {
				EntityBullet bullet = new EntityBullet(world);
				bullet.posX = x + vector.xCoord * 1.5 + 0.5;
				bullet.posY = y + vector.yCoord * 1.5 + 1.5;
				bullet.posZ = z + vector.zCoord * 1.5 + 0.5;
				
				bullet.motionX = vector.xCoord * 3;
				bullet.motionY = vector.yCoord * 3;
				bullet.motionZ = vector.zCoord * 3;
				
				bullet.damage = rand.nextInt(11) + 5;
				
				world.spawnEntityInWorld(bullet);
			}

			world.playSoundEffect(x, y, z, "hbm:weapon.sawShoot", 3.0F, 1.0F);
			
			flag = true;
		}
		
		return flag;
	}

	@Override
	public void executeReleaseAction(World world, int i, double yaw, double pitch, int x, int y, int z) {

		TileEntityTurretCheapo te = (TileEntityTurretCheapo)world.getTileEntity(x, y, z);
		
		if(te.spin > 10)
			world.playSoundEffect(x, y, z, "hbm:weapon.spindown", 1.0F, 1.0F);
	}

}
