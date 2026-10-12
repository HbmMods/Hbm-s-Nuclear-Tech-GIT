package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachinePUREX;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachinePUREX extends BlockDummyable implements ITooltipProvider {

	public MachinePUREX(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachinePUREX();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override public int[] getDimensions() { return new int[] {4, 0, 2, 2, 2, 2}; }
	@Override public int getOffset() { return 2; }
	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		return rotatePorts(new int[][] {
			{-2, 0, -2, ForgeDirection.WEST.ordinal()},
			{-2, 0, -2, ForgeDirection.NORTH.ordinal()},
			{-1, 0, -2, ForgeDirection.NORTH.ordinal()},
			{0, 0, -2, ForgeDirection.NORTH.ordinal()},
			{1, 0, -2, ForgeDirection.NORTH.ordinal()},
			{2, 0, -2, ForgeDirection.NORTH.ordinal()},
			{2, 0, -2, ForgeDirection.EAST.ordinal()},
			{-2, 0, -1, ForgeDirection.WEST.ordinal()},
			{2, 0, -1, ForgeDirection.EAST.ordinal()},
			{-2, 0, 0, ForgeDirection.WEST.ordinal()},
			{2, 0, 0, ForgeDirection.EAST.ordinal()},
			{-2, 0, 1, ForgeDirection.WEST.ordinal()},
			{2, 0, 1, ForgeDirection.EAST.ordinal()},
			{-2, 0, 2, ForgeDirection.WEST.ordinal()},
			{-2, 0, 2, ForgeDirection.SOUTH.ordinal()},
			{-1, 0, 2, ForgeDirection.SOUTH.ordinal()},
			{0, 0, 2, ForgeDirection.SOUTH.ordinal()},
			{1, 0, 2, ForgeDirection.SOUTH.ordinal()},
			{2, 0, 2, ForgeDirection.SOUTH.ordinal()},
			{2, 0, 2, ForgeDirection.EAST.ordinal()}
		}, dir);
	}
	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		for(int[] offset : getAllPorts(dir)) {
			this.makeExtra(world, x + offset[0], y + offset[1], z + offset[2]);
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}
