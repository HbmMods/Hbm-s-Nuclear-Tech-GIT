package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.item.EntityTNTPrimedBase;
import com.hbm.explosion.ExplosionNukeSmall;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockFissureBomb extends BlockTNTBase {

	@Override
	public void explodeEntity(World world, double x, double y, double z, EntityTNTPrimedBase entity) {
		ExplosionNukeSmall.explode(world, x, y, z, ExplosionNukeSmall.PARAMS_MEDIUM);
		
		int range = 5;
		
		for(int i = -range; i <= range; i++) {
			for(int j = -range; j <= range; j++) {
				for(int k = -range; k <= range; k++) {

					int a = (int) Math.floor(x + i);
					int b = (int) Math.floor(y + j);
					int c = (int) Math.floor(z + k);
					
					Block block = world.getBlock(a, b, c);
					
					if(block == ModBlocks.ore_bedrock) {
						world.setBlock(a, b, c, ModBlocks.ore_volcano);
					} else if(block == ModBlocks.ore_bedrock_oil) {
						world.setBlock(a, b, c, Blocks.bedrock);
					}
				}
			}
		}
	}
}
