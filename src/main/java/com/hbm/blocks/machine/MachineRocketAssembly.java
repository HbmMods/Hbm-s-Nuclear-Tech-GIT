package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineRocketAssembly;
import com.hbm.util.ItemStackUtil;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineRocketAssembly extends BlockDummyable implements ITooltipProvider {

	public MachineRocketAssembly(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineRocketAssembly();
		return new TileEntityProxyCombo();
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		ItemStackUtil.spillItems(world, x, y, z, block, world.rand);
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int[] getDimensions() {
		// funky behaviour, but the space checking is still done regularly
		return new int[] {0, 2, 4, 4, 4, 4};
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		x = x + dir.offsetX * o;
		y = y + dir.offsetY * o;
		z = z + dir.offsetZ * o;

		// Top
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {0, 0, 4, 4, 4, 4}, this, dir);

		// Leggies
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {0, 2, 4, -3, 4, -4}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {0, 2, 4, -4, 4, -3}, this, dir);

		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {0, 2, 4, -3, -4, 4}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {0, 2, 4, -4, -3, 4}, this, dir);

		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {0, 2, -3, 4, -4, 4}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {0, 2, -4, 4, -3, 4}, this, dir);

		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {0, 2, -3, 4, 4, -4}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {0, 2, -4, 4, 4, -3}, this, dir);
	}

	@Override
	public int getOffset() {
		return 4;
	}

	@Override
	public int getHeightOffset() {
		return 2;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		addStandardInfo(stack, player, list, ext);
	}

}
