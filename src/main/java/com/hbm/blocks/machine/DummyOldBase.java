package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.interfaces.IDummy;
import com.hbm.interfaces.IMultiblock;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.tileentity.machine.TileEntityDummy;

import api.hbm.energymk2.IEnergyConnectorBlock;
import api.hbm.fluid.IFluidConnectorBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class DummyOldBase extends BlockContainer implements IDummy, IEnergyConnectorBlock, IFluidConnectorBlock {

	public static boolean safeBreak = false;
	private boolean port = false;

	public DummyOldBase(Material mat, boolean port) {
		super(mat);
		this.port = port;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityDummy();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i) {
		if(!safeBreak) {
			TileEntity te = world.getTileEntity(x, y, z);
			if(te != null && te instanceof TileEntityDummy) {
				int a = ((TileEntityDummy) te).targetX;
				int b = ((TileEntityDummy) te).targetY;
				int c = ((TileEntityDummy) te).targetZ;

				if(!world.isRemote && world.getBlock(a, b, c) instanceof IMultiblock)
					world.func_147480_a(a, b, c, true);
			}
		}
		world.removeTileEntity(x, y, z);
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
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return null;
	}

	@Override public boolean canConnect(IBlockAccess world, int x, int y, int z, ForgeDirection dir) { return port; }
	@Override public boolean canConnect(FluidType type, IBlockAccess world, int x, int y, int z, ForgeDirection dir) { return port; }
}
