package com.hbm.blocks.machine.albion;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.albion.TileEntityPADetector;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPADetector extends BlockDummyable implements ITooltipProvider {

	public BlockPADetector() {
		super(Material.iron);
		this.setCreativeTab(MainRegistry.machineTab);
		this.setBlockTextureName(RefStrings.MODID + ":block_steel");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityPADetector();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power().fluid();
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, side);
	}

	@Override public int[] getDimensions() { return new int[] {2, 2, 2, 2, 4, 4}; }
	@Override public int getOffset() { return 0; }
	@Override public int getHeightOffset() { return 2; }
	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new int[][] {
			{-rot.offsetX * 4, 0, -rot.offsetZ * 4, rot.getOpposite().ordinal()},
			{-rot.offsetX * 4, 1, -rot.offsetZ * 4, rot.getOpposite().ordinal()},
			{-rot.offsetX * 4, -1, -rot.offsetZ * 4, rot.getOpposite().ordinal()},
			{-rot.offsetX * 4 + dir.offsetX, 0, -rot.offsetZ * 4 + dir.offsetZ, rot.getOpposite().ordinal()},
			{-rot.offsetX * 4 - dir.offsetX, 0, -rot.offsetZ * 4 - dir.offsetZ, rot.getOpposite().ordinal()}
		};
	}
	@Override
	public double[][] getAABBExtras() {
		return new double[][] {
			{0.9, 0.1, 0, 0.5, 4, 4}
		};
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
		addStandardInfo(stack, player, list, ext);
	}
}
