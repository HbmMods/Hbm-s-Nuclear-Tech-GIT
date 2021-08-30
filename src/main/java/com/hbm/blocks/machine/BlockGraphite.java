package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockFlammable;

import api.hbm.block.IToolable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockGraphite extends BlockFlammable implements IToolable {

	public BlockGraphite(Material mat, int en, int flam) {
		super(mat, en, flam);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool != ToolType.HAND_DRILL)
			return false;
		
		if(!world.isRemote) {
			world.setBlock(x, y, z, ModBlocks.block_graphite_drilled);
		}
		
		return true;
	}

}
