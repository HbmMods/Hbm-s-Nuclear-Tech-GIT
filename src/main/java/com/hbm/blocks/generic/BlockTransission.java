package com.hbm.blocks.generic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockTransission extends Block {

	public BlockTransission(Material mat) {
		super(mat);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
			
		} else if(!player.isSneaking()) {

			if(side == 0) {
				player.setPositionAndUpdate(x + 0.5, y + 1, z + 0.5);
			}
			if(side == 1) {
				player.setPositionAndUpdate(x + 0.5, y - 2, z + 0.5);
			}
			return true;
		} else {
			return false;
		}
	}
}
