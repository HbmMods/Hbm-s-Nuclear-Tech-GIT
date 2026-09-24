package com.hbm.blocks.network;

import java.util.List;

import com.hbm.blocks.IAnalyzable;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.tileentity.network.TileEntityFluidPipeAnchor;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FluidPipeAnchor extends BasePipeAnchor implements IBlockFluidDuct, IAnalyzable {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFluidPipeAnchor();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fX, float fY, float fZ) {
		return FluidDuctBase.handleActivation(world, x, y, z, player, this);
	}

	@Override // didn't think this was overridable, that makes everything so much easier. good job martin
	public void changeTypeRecursively(World world, int x, int y, int z, FluidType prevType, FluidType type, int loopsRemaining) {

		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof TileEntityFluidPipeAnchor) {
			TileEntityFluidPipeAnchor pipe = (TileEntityFluidPipeAnchor) te;

			if(pipe.getType() == prevType && pipe.getType() != type) {
				pipe.setType(type);

				if(loopsRemaining > 0) {
					ForgeDirection dir = ForgeDirection.getOrientation(pipe.getBlockMetadata()).getOpposite();
					Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

					if(b instanceof IBlockFluidDuct) ((IBlockFluidDuct) b).changeTypeRecursively(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, prevType, type, loopsRemaining - 1);

					for(int[] pos : pipe.getConnected()) {
						Block c = world.getBlock(pos[0], pos[1], pos[2]);
						if(c instanceof IBlockFluidDuct) ((IBlockFluidDuct) c).changeTypeRecursively(world, pos[0], pos[1], pos[2], prevType, type, loopsRemaining - 1);
					}
				}
			}
		}
	}

	@Override
	public List<String> getDebugInfo(World world, int x, int y, int z) {
		return FluidDuctBase.getDuctDebugInfo(world, x, y, z);
	}

	@Override
	public void addHookText(List<String> text, World world, int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityFluidPipeAnchor)) return;
		TileEntityFluidPipeAnchor duct = (TileEntityFluidPipeAnchor) te;

		text.add("&[" + duct.getType().getColor() + "&]" + duct.getType().getLocalizedName());
	}
}
