package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityHeaterOilburner;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class HeaterOilburner extends BlockDummyable implements ITooltipProvider {

	public HeaterOilburner() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityHeaterOilburner();
		
		if(hasExtra(meta) && meta - extra > 1)
			return new TileEntityProxyCombo().fluid();
		
		if(hasExtra(meta))
			return new TileEntityProxyCombo().heatSource();
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}
	@Override
	public double[][] getAABBExtras() {
		return new double[][] {
			{2, 2, 0.5, -0.3, 0.4, -0.4, 1}
		};
	}

	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		return rotatePorts(new int[][] {
			{1, 0, 0, ForgeDirection.EAST.ordinal()},
			{-1, 0, 0, ForgeDirection.WEST.ordinal()},
			{0, 0, 1, ForgeDirection.SOUTH.ordinal()},
			{0, 0, -1, ForgeDirection.NORTH.ordinal()}
		}, dir);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		for(int[] offset : getAllPorts(dir)) {
			this.makeExtra(world, x + offset[0], y + offset[1], z + offset[2]);
		}
		
		this.makeExtra(world, x - dir.offsetX, y + 1, z - dir.offsetZ);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}
