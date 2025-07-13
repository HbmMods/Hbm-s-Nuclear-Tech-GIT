package com.hbm.entity.mob;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.*;
import com.hbm.items.ModItems;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityCreeperGold extends EntityCreeper {

	public EntityCreeperGold(World world) {
		super(world);
	}

	@Override
	public void func_146077_cc() {
		
		if(!this.worldObj.isRemote) {
			this.setDead();
			
			ExplosionVNT vnt = new ExplosionVNT(worldObj, posX, posY, posZ, this.getPowered() ? 14 : 7, this);
			vnt.setBlockAllocator(new BlockAllocatorBulkie(60, this.getPowered() ? 32 : 16));
			vnt.setBlockProcessor(new BlockProcessorStandard().withBlockEffect(new BlockMutatorBulkie(Blocks.gold_ore)));
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

		int amount = byPlayer ? 5 + rand.nextInt(6 + looting * 2) : 3;
		for(int i = 0; i < amount; ++i) {
			this.entityDropItem(new ItemStack(ModItems.crystal_gold), 0F);
		}
	}
}
