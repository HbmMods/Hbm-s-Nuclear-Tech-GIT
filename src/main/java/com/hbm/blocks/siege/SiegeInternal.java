package com.hbm.blocks.siege;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class SiegeInternal extends SiegeBase {

	public SiegeInternal(Material material) {
		super(material, 2);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		//if exposed to air, harden
		if(!this.solidNeighbors(world, x, y, z)) {
			world.setBlock(x, y, z, ModBlocks.siege_emergency);
			return;
		}
		
		int succ = 0;
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			
			//if the bordering block is either an internal or a circuit, increment
			if(b == this || b == ModBlocks.siege_circuit) {
				succ++;
			}
		}
		
		//all neighbors are internals or circuits? turn into a circuit
		if(succ == 6)
			world.setBlock(x, y, z, ModBlocks.siege_circuit);
	}
}