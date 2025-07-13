package com.hbm.entity.mob;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.*;
import com.hbm.items.ModItems;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityCreeperVolatile extends EntityCreeper {

	public EntityCreeperVolatile(World world) {
		super(world);
	}

	@Override
	public void func_146077_cc() {
		
		if(!this.worldObj.isRemote) {
			this.setDead();
			
			ExplosionVNT vnt = new ExplosionVNT(worldObj, posX, posY, posZ, this.getPowered() ? 14 : 7, this);
			vnt.setBlockAllocator(new BlockAllocatorBulkie(60, this.getPowered() ? 32 : 16));
			vnt.setBlockProcessor(new BlockProcessorStandard().withBlockEffect(new BlockMutatorBulkie(ModBlocks.block_slag, 1)));
			vnt.setEntityProcessor(new EntityProcessorStandard().withRangeMod(0.5F));
			vnt.setPlayerProcessor(new PlayerProcessorStandard());
			vnt.setSFX(new ExplosionEffectStandard());
			vnt.explode();
		}
	}
	
	@Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere() && this.posY <= 40 && this.dimension == 0;
	}
	
	@Override
	protected void dropFewItems(boolean byPlayer, int looting) {
		this.entityDropItem(new ItemStack(ModItems.sulfur, 2 + rand.nextInt(3)), 0F);
		this.entityDropItem(new ItemStack(ModItems.stick_tnt, 1 + rand.nextInt(2)), 0F);
	}
}
