package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.item.IDepthRockTool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class BlockDepth extends Block implements ITooltipProvider {

	public BlockDepth() {
		super(Material.rock);
		this.setBlockUnbreakable();
		this.setResistance(10.0F);
	}
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IDepthRockTool) {
			
			if(((IDepthRockTool)player.getHeldItem().getItem()).canBreakRock(world, player, player.getHeldItem(), this, x, y, z))
			return (float) (1D / 50D);
		}
		
		return super.getPlayerRelativeBlockHardness(player, world, x, y, z);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.tile.depth"));
	}
}