package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineStardar;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineStardar extends BlockDummyable implements ITooltipProvider {

	public MachineStardar(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineStardar();
		if(meta >= extra) return new TileEntityProxyCombo(false, false, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 3, 2, 2, 2, 2};
	}

	@Override
	public int getOffset() {
		return 2;
	}

	@Override
	public int getHeightOffset() {
		return 3;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		// Main body
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {0, 0, 2, 2, 2, 2}, this, dir);

		// Legs
		for(int ox = -2; ox <= 2; ox += 2) {
			for(int oz = -2; oz <= 2; oz += 2) {
				if(ox == 0 && oz == 0) continue;
				MultiblockHandlerXR.fillSpace(world, x + ox, y, z + oz, new int[] {0, 3, 0, 0, 0, 0}, this, dir);
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		addStandardInfo(stack, player, list, ext);
	}
	
}