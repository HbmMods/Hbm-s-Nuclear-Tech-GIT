package com.hbm.blocks.bomb;

import com.hbm.explosion.vanillant.ExplosionVNT;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.world.World;

public class BlockChargeC4 extends BlockChargeBase {

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {
		
		if(!world.isRemote) {
			safe = true;
			world.setBlockToAir(x, y, z);
			safe = false;
			
			ExplosionVNT xnt = new ExplosionVNT(world, x + 0.5, y + 0.5, z + 0.5, 10F).makeStandard();
			xnt.explode();
			
			return BombReturnCode.DETONATED;
		}
		
		return BombReturnCode.UNDEFINED;
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
	}
}
