package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityHeaterOven;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class HeaterOven extends BlockDummyable implements ITooltipProvider {

	public HeaterOven() {
		super(Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12) return new TileEntityHeaterOven();
		return new TileEntityProxyCombo().inventory().fluid();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}
