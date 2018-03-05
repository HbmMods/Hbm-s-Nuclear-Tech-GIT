package com.hbm.entity.missile;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.explosion.ExplosionThermo;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileTaint extends EntityMissileBaseAdvanced {

	public EntityMissileTaint(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileTaint(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 10.0F, true);
        
	    for(int i = 0; i < 100; i++) {
	    	int a = rand.nextInt(11) + (int)this.posX - 5;
	    	int b = rand.nextInt(11) + (int)this.posY - 5;
	    	int c = rand.nextInt(11) + (int)this.posZ - 5;
	           if(worldObj.getBlock(a, b, c).isReplaceable(worldObj, a, b, c) && BlockTaint.hasPosNeightbour(worldObj, a, b, c))
	        	   worldObj.setBlock(a, b, c, ModBlocks.taint);
	    }
	}

	@Override
	public List<ItemStack> getDebris() {
		return null;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return null;
	}

	@Override
	public int getMissileType() {
		return 0;
	}
}
