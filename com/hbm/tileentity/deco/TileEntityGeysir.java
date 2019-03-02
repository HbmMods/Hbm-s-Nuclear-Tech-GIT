package com.hbm.tileentity.deco;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.particle.EntityChlorineFX;
import com.hbm.entity.particle.EntityOrangeFX;
import com.hbm.entity.projectile.EntityWaterSplash;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityGeysir extends TileEntity {
	
	int timer;

	@Override
	public void updateEntity() {
		
		if (!this.worldObj.isRemote) {
			
			timer--;
			
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			
			if(timer <= 0) {
				timer = getDelay();

				if(meta == 0)
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
				else
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
			}
			
			if(meta == 1) {
				perform();
			}
		}
	}
	
	private void water() {
		
		EntityWaterSplash fx = new EntityWaterSplash(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5);

		fx.motionX = worldObj.rand.nextGaussian() * 0.35;
		fx.motionZ = worldObj.rand.nextGaussian() * 0.35;
		fx.motionY = 2;
		
		worldObj.spawnEntityInWorld(fx);
	}
	
	private void chlorine() {
		
		for(int i = 0; i < 3; i++) {
			EntityOrangeFX fx = new EntityOrangeFX(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, 0.0, 0.0, 0.0);
	
			fx.motionX = worldObj.rand.nextGaussian() * 0.45;
			fx.motionZ = worldObj.rand.nextGaussian() * 0.45;
			fx.motionY = timer * 0.3;
			
			worldObj.spawnEntityInWorld(fx);
		}
		
	}
	
	private void vapor() {

		List<Entity> entities = this.worldObj.getEntitiesWithinAABB(Entity.class,
				AxisAlignedBB.getBoundingBox(this.xCoord - 0.5, this.yCoord, this.zCoord - 0.5, this.xCoord + 1.5,
						this.yCoord + 2, this.zCoord + 1.5));
		
		if (!entities.isEmpty()) {
			for (Entity e : entities) {

				if(e instanceof EntityLivingBase)
				((EntityLivingBase)e).addPotionEffect(new PotionEffect(Potion.regeneration.id, 20, 0));
			}
		}
	}
	
	private int getDelay() {
		
		Block b = worldObj.getBlock(xCoord, yCoord, zCoord);
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		Random rand = worldObj.rand;
		
		if(b == ModBlocks.geysir_water) {
			
			return (meta == 0 ? 30 : 100 + rand.nextInt(40));
			
		} else if(b == ModBlocks.geysir_chlorine) {
			
			return (meta == 0 ? 20 : 400 + rand.nextInt(100));
			
		} else if(b == ModBlocks.geysir_vapor) {
			
			return (meta == 0 ? 20 : 30 + rand.nextInt(20));
			
		}
		
		return 0;
	}
	
	private void perform() {

		Block b = worldObj.getBlock(xCoord, yCoord, zCoord);
		
		if(b == ModBlocks.geysir_water) {
			
			water();
			
		} else if(b == ModBlocks.geysir_chlorine) {
			
			chlorine();
			
		} else if(b == ModBlocks.geysir_vapor) {
			
			vapor();
			
		}
	}

}
