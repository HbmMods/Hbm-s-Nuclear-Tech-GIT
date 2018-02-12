package com.hbm.blocks.bomb;

import java.util.List;

import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.bomb.TileEntityTurretCWIS;
import com.hbm.tileentity.bomb.TileEntityTurretSpitfire;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
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
		
		if(te.spin > 25 && i % 2 == 0) {
			Vec3 vector = Vec3.createVectorHelper(
					-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI),
					-Math.sin(pitch / 180.0F * (float) Math.PI),
					Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI));
			
			vector.normalize();
			
			if(!world.isRemote) {
				
				rayShot(world, vector, x + vector.xCoord * 2.5 + 0.5, y + vector.yCoord * 2.5 + 0.5, z + vector.zCoord * 2.5 + 0.5, 100, 10.0F, 50);
				
				EntityGasFlameFX smoke = new EntityGasFlameFX(world);
				smoke.posX = x + vector.xCoord * 2.5 + 0.5;
				smoke.posY = y + vector.yCoord * 2.5 + 1.5;
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
	
	private void rayShot(World world, Vec3 vec, double posX, double posY, double posZ, int range, float damage, int hitPercent) {
		List<Entity> entities = world.getLoadedEntityList();
		
		for(float i = 0; i < range; i += 0.25F) {
			double pX = posX + vec.xCoord * i;
			double pY = posY + vec.yCoord * i;
			double pZ = posZ + vec.zCoord * i;
			
			for(int j = 0; j < entities.size(); j++) {
				Entity ent = entities.get(j);
				if(rand.nextInt(100) < hitPercent) {
					if(ent.posX + ent.width * 0.75 > pX && ent.posX - ent.width * 0.75 < pX && 
						ent.posY + ent.height > pY && ent.posY < pY && 
						ent.posZ + ent.width * 0.75 > pZ && ent.posZ - ent.width * 0.75 < pZ) {
						ent.attackEntityFrom(ModDamageSource.shrapnel, (damage * 0.25F) + (rand.nextFloat() * damage * 0.75F));
					}
				}
			}
		}
	}

	@Override
	public void executeReleaseAction(World world, int i, double yaw, double pitch, int x, int y, int z) { }
}
