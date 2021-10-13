package com.hbm.blocks.generic;

import api.hbm.item.IDepthRockTool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockDepth extends Block {

	public BlockDepth() {
		super(Material.rock);
		this.setBlockUnbreakable();
		this.setResistance(10.0F);
	}
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IDepthRockTool) {
			
			if(((IDepthRockTool)player.getHeldItem().getItem()).canBreakRock(world, player, player.getHeldItem(), this, x, y, z))
			return (float) (1D / 100D);
		}
		
		return super.getPlayerRelativeBlockHardness(player, world, x, y, z);
	}
}