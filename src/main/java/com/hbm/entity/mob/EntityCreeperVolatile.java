package com.hbm.entity.mob;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.*;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.world.World;

public class EntityCreeperVolatile extends EntityCreeper {

	public EntityCreeperVolatile(World world) {
		super(world);
	}

	@Override
	public void func_146077_cc() {
		
		if(!this.worldObj.isRemote) {
			this.setDead();
			
			ExplosionVNT vnt = new ExplosionVNT(worldObj, posX, posY, posZ, 7);
			vnt.setBlockAllocator(new BlockAllocatorBulkie(60));
			vnt.setBlockProcessor(new BlockProcessorStandard().withBlockEffect(new BlockMutatorBulkie(ModBlocks.block_slag, 1)));
			vnt.setEntityProcessor(new EntityProcessorStandard().withRangeMod(0.5F));
			vnt.setPlayerProcessor(new PlayerProcessorStandard());
			vnt.setSFX(new ExplosionEffectStandard());
			vnt.explode();
		}
	}
	
	@Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere() && this.posY <= 40;
	}
}
