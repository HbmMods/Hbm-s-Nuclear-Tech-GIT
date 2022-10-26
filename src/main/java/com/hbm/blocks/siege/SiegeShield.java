package com.hbm.blocks.siege;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.SiegeOrchestrator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class SiegeShield extends SiegeBase {
	
	public SiegeShield(Material material) {
		super(material, 4);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if(SiegeOrchestrator.siegeMobCount > SiegeOrchestrator.getExpansionThreshold(world) || !SiegeOrchestrator.enableBaseSpawning(world) || !SiegeOrchestrator.siegeEnabled(world))
			return;
		
		int succ = 0;
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			
			//if the block is already a siege block, do nothing and increment the success counter
			if(!this.shouldReplace(b)) {
				succ++;
				
			//...if not, check if a new shield can be placed, and try to do so
			} else if(this.solidNeighbors(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
				succ++;
				world.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, this);
			}
		}
		
		//if all the blocks are siege blocks now, replace with an internal
		if(succ == 6) {
			world.setBlock(x, y, z, ModBlocks.siege_internal);

			if(rand.nextInt(10) == 0) {
				Block above = world.getBlock(x, y + 2, z);
				Block surface = world.getBlock(x, y + 3, z);
				
				//if the block above the upper shield is solid and *above that* is air, place a hole
				if(above.getMaterial() != Material.air && above.isNormalCube() && (surface.getMaterial() == Material.air || !surface.isNormalCube())) {
					world.setBlock(x, y + 2, z, ModBlocks.siege_hole);
				}
			}
		}
	}
}
