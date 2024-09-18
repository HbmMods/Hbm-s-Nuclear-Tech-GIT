package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.network.FluidDuctBox;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;
import com.hbm.render.util.RenderBlocksNT;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;
import com.hbm.util.ColorUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class RenderBoxDuct implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		FluidDuctBox duct = (FluidDuctBox) block;
		int type = duct.rectify(metadata);

		float lower = 0.125F;
		float upper = 0.875F;
		
		for(int i = 2; i < 13; i += 3) {
			
			if(metadata > i) {
				lower += 0.0625F;
				upper -= 0.0625F;
			}
		}
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		renderer.setRenderBounds(lower, lower, 0.0F, upper, upper, 1.0F);
		
		renderer.uvRotateNorth = 1;
		renderer.uvRotateSouth = 2;
		
		tessellator.startDrawingQuads();
		tessellator.setNormal(0F, 1F, 0F);	renderer.renderFaceYPos(block, 0, 0, 0, duct.iconStraight[type]);
		tessellator.setNormal(0F, -1F, 0F);	renderer.renderFaceYNeg(block, 0, 0, 0, duct.iconStraight[type]);
		tessellator.setNormal(1F, 0F, 0F);	renderer.renderFaceXPos(block, 0, 0, 0, duct.iconStraight[type]);
		tessellator.setNormal(-1F, 0F, 0F);	renderer.renderFaceXNeg(block, 0, 0, 0, duct.iconStraight[type]);
		tessellator.setNormal(0F, 0F, 1F);	renderer.renderFaceZPos(block, 0, 0, 0, duct.iconEnd[type]);
		tessellator.setNormal(0F, 0F, -1F);	renderer.renderFaceZNeg(block, 0, 0, 0, duct.iconEnd[type]);
		tessellator.draw();

		renderer.uvRotateNorth = 0;
		renderer.uvRotateSouth = 0;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		FluidType type = Fluids.NONE;
		
		renderer = RenderBlocksNT.INSTANCE.setWorld(world);
		
		TileEntity te = world.getTileEntity(x, y, z);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		int meta = world.getBlockMetadata(x, y, z);

		boolean pX = false;
		boolean nX = false;
		boolean pY = false;
		boolean nY = false;
		boolean pZ = false;
		boolean nZ = false;

		FluidDuctBox.cachedColor = 0xffffff;
		FluidDuctBox duct = (FluidDuctBox) block;
		
		pX = duct.canConnectTo(world, x, y, z, Library.POS_X, te);
		nX = duct.canConnectTo(world, x, y, z, Library.NEG_X, te);
		pY = duct.canConnectTo(world, x, y, z, Library.POS_Y, te);
		nY = duct.canConnectTo(world, x, y, z, Library.NEG_Y, te);
		pZ = duct.canConnectTo(world, x, y, z, Library.POS_Z, te);
		nZ = duct.canConnectTo(world, x, y, z, Library.NEG_Z, te);
		
		if(te instanceof TileEntityPipeBaseNT) {
			TileEntityPipeBaseNT pipe = (TileEntityPipeBaseNT) te;
			type = pipe.getType();
			if(meta % 3 == 2) {
				FluidDuctBox.cachedColor = ColorUtil.lightenColor(type.getColor(), 0.25D); //making very dark things not vantablack
			}
		}

		int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);
		int count = 0 + (pX ? 1 : 0) + (nX ? 1 : 0) + (pY ? 1 : 0) + (nY ? 1 : 0) + (pZ ? 1 : 0) + (nZ ? 1 : 0);

		double lower = 0.125D;
		double upper = 0.875D;
		double jLower = 0.0625D;
		double jUpper = 0.9375D;
		
		for(int i = 2; i < 13; i += 3) {
			
			if(meta > i) {
				lower += 0.0625D;
				upper -= 0.0625D;
				jLower += 0.0625D;
				jUpper -= 0.0625D;
			}
		}
		
		//Straight along X
		if((mask & 0b001111) == 0 && mask > 0) {
			renderer.uvRotateTop = 1;
			renderer.uvRotateBottom = 1;
			renderer.uvRotateEast = 2;
			renderer.uvRotateWest = 1;
			renderer.setRenderBounds(0.0D, lower, lower, 1.0D, upper, upper);
			renderer.renderStandardBlock(block, x, y, z);
			
		//Straight along Z
		} else if((mask & 0b111100) == 0 && mask > 0) {
			renderer.uvRotateNorth = 1;
			renderer.uvRotateSouth = 2;
			renderer.setRenderBounds(lower, lower, 0.0D, upper, upper, 1.0D);
			renderer.renderStandardBlock(block, x, y, z);
			
		//Straight along Y
		} else if((mask & 0b110011) == 0 && mask > 0) {
			renderer.setRenderBounds(lower, 0.0D, lower, upper, 1.0D, upper);
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
			
			renderer.setRenderBounds(lower, lower, lower, upper, upper, upper);
			renderer.renderStandardBlock(block, x, y, z);

			if(nY) {
				renderer.setRenderBounds(lower, 0.0D, lower, upper, lower, upper);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(pY) {
				renderer.setRenderBounds(lower, upper, lower, upper, 1.0D, upper);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(nX) {
				renderer.setRenderBounds(0.0D, lower, lower, lower, upper, upper);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(pX) {
				renderer.setRenderBounds(upper, lower, lower, 1.0D, upper, upper);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(nZ) {
				renderer.setRenderBounds(lower, lower, 0.0D, upper, upper, lower);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(pZ) {
				renderer.setRenderBounds(lower, lower, upper, upper, upper, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			
		//Junction
		} else {
			renderer.setRenderBounds(jLower, jLower, jLower, jUpper, jUpper, jUpper);
			renderer.renderStandardBlock(block, x, y, z);

			if(nY) {
				renderer.setRenderBounds(lower, 0.0D, lower, upper, jLower, upper);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(pY) {
				renderer.setRenderBounds(lower, jUpper, lower, upper, 1.0D, upper);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(nX) {
				renderer.setRenderBounds(0.0D, lower, lower, jLower, upper, upper);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(pX) {
				renderer.setRenderBounds(jUpper, lower, lower, 1.0D, upper, upper);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(nZ) {
				renderer.setRenderBounds(lower, lower, 0.0D, upper, upper, jLower);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(pZ) {
				renderer.setRenderBounds(lower, lower, jUpper, upper, upper, 1.0D);
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
		return true;
	}

	@Override
	public int getRenderId() {
		return FluidDuctBox.renderID;
	}
}
