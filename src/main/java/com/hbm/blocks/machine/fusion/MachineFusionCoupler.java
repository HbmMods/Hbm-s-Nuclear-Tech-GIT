package com.hbm.blocks.machine.fusion;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.machine.fusion.TileEntityFusionCoupler;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineFusionCoupler extends BlockDummyable implements ITooltipProvider {

	public MachineFusionCoupler() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityFusionCoupler();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 3, 0, 1, 1, 1, 1 };
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		addStandardInfo(stack, player, list, ext);
	}
}
