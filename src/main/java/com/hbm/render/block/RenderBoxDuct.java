package com.hbm.render.block;

import com.hbm.blocks.network.FluidDuctBox;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class RenderBoxDuct implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) { }

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		FluidType type = Fluids.NONE;
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityPipeBaseNT) {
			TileEntityPipeBaseNT pipe = (TileEntityPipeBaseNT) te;
			type = pipe.getType();
		}

		boolean pX = Library.canConnectFluid(world, x + 1, y, z, Library.NEG_X, type);
		boolean nX = Library.canConnectFluid(world, x - 1, y, z, Library.POS_X, type);
		boolean pY = Library.canConnectFluid(world, x, y + 1, z, Library.NEG_Y, type);
		boolean nY = Library.canConnectFluid(world, x, y - 1, z, Library.POS_Y, type);
		boolean pZ = Library.canConnectFluid(world, x, y, z + 1, Library.NEG_Z, type);
		boolean nZ = Library.canConnectFluid(world, x, y, z - 1, Library.POS_Z, type);

		int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);
		int count = 0 + (pX ? 1 : 0) + (nX ? 1 : 0) + (pY ? 1 : 0) + (nY ? 1 : 0) + (pZ ? 1 : 0) + (nZ ? 1 : 0);
		
		//Straight along X
		if((mask & 0b001111) == 0 && mask > 0) {
			renderer.uvRotateTop = 1;
			renderer.uvRotateBottom = 1;
			renderer.uvRotateEast = 2;
			renderer.uvRotateWest = 1;
			renderer.setRenderBounds(0.0D, 0.125D, 0.125D, 1.0D, 0.875D, 0.875D);
			renderer.renderStandardBlock(block, x, y, z);
			
		//Straight along Z
		} else if((mask & 0b111100) == 0 && mask > 0) {
			renderer.uvRotateNorth = 1;
			renderer.uvRotateSouth = 2;
			renderer.setRenderBounds(0.125D, 0.125D, 0.0D, 0.875D, 0.875D, 1.0D);
			renderer.renderStandardBlock(block, x, y, z);
			
		//Straight along Y
		} else if((mask & 0b110011) == 0 && mask > 0) {
			renderer.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
			renderer.renderStandardBlock(block, x, y, z);
			
		//Curve
		} else if(count == 2) {
			
			if(nY && (pX || nX)) {
				renderer.uvRotateTop = 1;
				renderer.uvRotateBottom = 1;
			}
			
			if(pY && (pX || nX)) {
				renderer.uvRotateTop = 1;
				renderer.uvRotateBottom = 1;
			}
			
			if(!nY && !pY) {
				renderer.uvRotateNorth = 1;
				renderer.uvRotateSouth = 2;
				renderer.uvRotateEast = 2;
				renderer.uvRotateWest = 1;
			}
			
			renderer.setRenderBounds(0.125D, 0.125D, 0.125D, 0.875D, 0.875D, 0.875D);
			renderer.renderStandardBlock(block, x, y, z);

			if(nY) {
				renderer.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.125D, 0.875D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(pY) {
				renderer.setRenderBounds(0.125D, 0.875D, 0.125D, 0.875D, 1.0D, 0.875D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(nX) {
				renderer.setRenderBounds(0.0D, 0.125D, 0.125D, 0.125D, 0.875D, 0.875D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(pX) {
				renderer.setRenderBounds(0.875D, 0.125D, 0.125D, 1.0D, 0.875D, 0.875D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(nZ) {
				renderer.setRenderBounds(0.125D, 0.125D, 0.0D, 0.875D, 0.875D, 0.125D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(pZ) {
				renderer.setRenderBounds(0.125D, 0.125D, 0.875D, 0.875D, 0.875D, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			
		//Junction
		} else {
			renderer.setRenderBounds(0.0625D, 0.0625D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
			renderer.renderStandardBlock(block, x, y, z);

			if(nY) {
				renderer.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.0625D, 0.875D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(pY) {
				renderer.setRenderBounds(0.125D, 0.9375D, 0.125D, 0.875D, 1.0D, 0.875D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(nX) {
				renderer.setRenderBounds(0.0D, 0.125D, 0.125D, 0.0625D, 0.875D, 0.875D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(pX) {
				renderer.setRenderBounds(0.9375D, 0.125D, 0.125D, 1.0D, 0.875D, 0.875D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(nZ) {
				renderer.setRenderBounds(0.125D, 0.125D, 0.0D, 0.875D, 0.875D, 0.0625D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(pZ) {
				renderer.setRenderBounds(0.125D, 0.125D, 0.9375D, 0.875D, 0.875D, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}

		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return FluidDuctBox.renderID;
	}
}
