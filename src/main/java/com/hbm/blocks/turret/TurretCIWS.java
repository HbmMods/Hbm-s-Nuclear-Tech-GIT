package com.hbm.blocks.turret;

import java.util.List;

import com.hbm.config.WeaponConfig;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.turret.TileEntityTurretCIWS;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TurretCIWS extends TurretBase {

	public TurretCIWS(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityTurretCIWS();
	}

	@Override
	public boolean executeHoldAction(World world, int i, double yaw, double pitch, int x, int y, int z) {
		
		boolean flag = false;
		
		if(pitch < -60)
			pitch = -60;
		if(pitch > 30)
			pitch = 30;
		
		TileEntityTurretCIWS te = (TileEntityTurretCIWS)world.getTileEntity(x, y, z);
		
		if(i == 0 && te.spin < 10)
			world.playSoundEffect(x, y, z, "hbm:weapon.ciwsSpinup", 1.0F, 1.0F);
		
		if(te.spin < 35)
			te.spin += 5;
		
		if(te.spin > 25 && i % 2 == 0) {
			Vec3 vector = Vec3.createVectorHelper(
					-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI),
					-Math.sin(pitch / 180.0F * (float) Math.PI),
					Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI));
			
			vector.normalize();

			world.playSoundEffect(x, y, z, "hbm:weapon.ciwsFiringLoop", 1.0F, 1.25F);
			
			flag = true;
		}
		
		return flag;
	}
	
	private void rayShot(World world, Vec3 vec, double posX, double posY, double posZ, int range, float damage, int hitPercent) {
		
		for(float i = 0; i < range; i += 0.25F) {
			double pX = posX + vec.xCoord * i;
			double pY = posY + vec.yCoord * i;
			double pZ = posZ + vec.zCoord * i;
			
			if(world.getBlock((int)pX, (int)pY, (int)pZ).getMaterial() != Material.air)
				break;
			
			List<Entity> hit = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(pX - 0.125, pY - 0.125, pZ - 0.125, pX + 0.125, pY + 0.125, pZ + 0.125));
			
			for(int j = 0; j < hit.size(); j++) {
				Entity ent = hit.get(j);
				
				if(rand.nextInt(100) < hitPercent) {
					ent.attackEntityFrom(ModDamageSource.shrapnel, 10.0F);
				}
			}
		}
	}

	@Override
	public void executeReleaseAction(World world, int i, double yaw, double pitch, int x, int y, int z) {

		TileEntityTurretCIWS te = (TileEntityTurretCIWS)world.getTileEntity(x, y, z);
		
		if(te.spin > 10)
			world.playSoundEffect(x, y, z, "hbm:weapon.ciwsSpindown", 1.0F, 1.0F);
		
	}
}
