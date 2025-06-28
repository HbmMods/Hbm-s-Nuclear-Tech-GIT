package com.hbm.blocks.bomb;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectTiny;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.interfaces.IBomb;
import com.hbm.particle.helper.ExplosionCreator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BombFlameWar extends Block implements IBomb {

	public BombFlameWar(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
			explode(world, x, y, z);
		}
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {

		if(!world.isRemote) {
			
			world.func_147480_a(x, y, z, false);
			
			for(int i = 0; i < 150; i++) {
				ExplosionVNT vnt = new ExplosionVNT(world, x + world.rand.nextInt(51) - 25, y + world.rand.nextInt(11) - 5, z + world.rand.nextInt(51) - 25, 4, null);
				vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, 25));
				vnt.setPlayerProcessor(new PlayerProcessorStandard());
				vnt.setSFX(new ExplosionEffectTiny());
				vnt.explode();
			}
			
			ExplosionVNT xnt = new ExplosionVNT(world, x + 0.5, y + 0.5, z + 0.5, 15F);
			xnt.setBlockAllocator(new BlockAllocatorStandard(32));
			xnt.setBlockProcessor(new BlockProcessorStandard().setNoDrop());
			xnt.setEntityProcessor(new EntityProcessorCrossSmooth(2, 200));
			xnt.setPlayerProcessor(new PlayerProcessorStandard());
			xnt.explode();
			ExplosionCreator.composeEffectSmall(world, x + 0.5, y + 0.5, z + 0.5);
		}

		return BombReturnCode.DETONATED;
	}
}
