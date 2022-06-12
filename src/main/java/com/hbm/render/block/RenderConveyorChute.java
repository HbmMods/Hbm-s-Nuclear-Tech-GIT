package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.network.BlockConveyorChute;

import api.hbm.conveyor.IConveyorBelt;
import api.hbm.conveyor.IEnterableBlock;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderConveyorChute implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		tessellator.setColorOpaque_F(1, 1, 1);

		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		meta = 2;

		if(meta == 2) {
			renderer.uvRotateTop = 3;
			renderer.uvRotateBottom = 0;
			renderer.uvRotateWest = 3;
		}
		if(meta == 3) {
			renderer.uvRotateTop = 0;
			renderer.uvRotateBottom = 3;
			renderer.uvRotateEast = 3;
		}
		if(meta == 4) {
			renderer.uvRotateTop = 1;
			renderer.uvRotateBottom = 1;
			renderer.uvRotateSouth = 3;
		}
		if(meta == 5) {
			renderer.uvRotateTop = 2;
			renderer.uvRotateBottom = 2;
			renderer.uvRotateNorth = 3;
		}
		
		tessellator.startDrawingQuads();
		renderer.setRenderBounds(0.25D, 0.0D, 0D, 0.75D, 0.25D, 1D); standardBundle(tessellator, block, meta, renderer);
		renderer.setRenderBounds(0.0D, 0.0D, 0.25D, 0.25D, 0.25D, 0.75D); standardBundle(tessellator, block, meta, renderer);
		renderer.setRenderBounds(0.75D, 0.0D, 0.25D, 1.0D, 0.25D, 0.75D); standardBundle(tessellator, block, meta, renderer);
		
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;

		double minOuter = 0.0;
		double maxOuter = 1.0;
		double minInner = 0.25;
		double maxInner = 0.75;
		double glassMin = 0.125;
		double glassMax = 0.875;
		
		renderer.setRenderBounds(minOuter, 0.0, minOuter, minInner, 1.0, minInner); standardBundle(tessellator, ModBlocks.concrete_smooth, 0, renderer);
		renderer.setRenderBounds(maxInner, 0.0, minOuter, maxOuter, 1.0, minInner); standardBundle(tessellator, ModBlocks.concrete_smooth, 0, renderer);
		renderer.setRenderBounds(minOuter, 0.0, maxInner, minInner, 1.0, maxOuter); standardBundle(tessellator, ModBlocks.concrete_smooth, 0, renderer);
		renderer.setRenderBounds(maxInner, 0.0, maxInner, maxOuter, 1.0, maxOuter); standardBundle(tessellator, ModBlocks.concrete_smooth, 0, renderer);

		IIcon iconGlass = ModBlocks.steel_grate.getIcon(0, 0);
		renderer.setOverrideBlockTexture(iconGlass);
		
		renderer.setRenderBounds(glassMin, 0.25, minInner, glassMin, 1.0, maxInner); standardBundle(tessellator, ModBlocks.steel_grate, 2, renderer);
		renderer.setRenderBounds(glassMax, 0.25, minInner, glassMax, 1.0, maxInner); standardBundle(tessellator, ModBlocks.steel_grate, 2, renderer);
		renderer.setRenderBounds(minInner, 0.25, glassMin, maxInner, 1.0, glassMin); standardBundle(tessellator, ModBlocks.steel_grate, 2, renderer);
		
		tessellator.draw();
		
		renderer.clearOverrideBlockTexture();

		GL11.glPopMatrix();
	}
	
	private void standardBundle(Tessellator tessellator, Block block, int meta, RenderBlocks renderer) {
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		int meta = world.getBlockMetadata(x, y, z);

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);
		
		boolean belt = false;
		
		boolean nX = world.getBlock(x - 1, y, z) instanceof IConveyorBelt;
		boolean pX = world.getBlock(x + 1, y, z) instanceof IConveyorBelt;
		boolean nZ = world.getBlock(x, y, z - 1) instanceof IConveyorBelt;
		boolean pZ = world.getBlock(x, y, z + 1) instanceof IConveyorBelt;
		
		
		if(y > 0) {
			Block below = world.getBlock(x, y - 1, z);
			if(!(below instanceof IConveyorBelt || below instanceof IEnterableBlock)) {
				
				if(meta == 2) {
					renderer.uvRotateTop = 3;
					renderer.uvRotateBottom = 0;
					renderer.uvRotateWest = 3;
				}
				if(meta == 3) {
					renderer.uvRotateTop = 0;
					renderer.uvRotateBottom = 3;
					renderer.uvRotateEast = 3;
				}
				if(meta == 4) {
					renderer.uvRotateTop = 1;
					renderer.uvRotateBottom = 1;
					renderer.uvRotateSouth = 3;
				}
				if(meta == 5) {
					renderer.uvRotateTop = 2;
					renderer.uvRotateBottom = 2;
					renderer.uvRotateNorth = 3;
				}

				renderer.setRenderBounds(0.25D, 0.0D, 0D, 0.75D, 0.25D, 1D); renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.0D, 0.0D, 0.25D, 0.25D, 0.25D, 0.75D); renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.75D, 0.0D, 0.25D, 1.0D, 0.25D, 0.75D); renderer.renderStandardBlock(block, x, y, z);
				renderer.uvRotateTop = 0;
				renderer.uvRotateBottom = 0;
				renderer.uvRotateNorth = 0;
				renderer.uvRotateSouth = 0;
				renderer.uvRotateEast = 0;
				renderer.uvRotateWest = 0;
				
				belt = true;
			} else {
				
				if(nX) {
					renderer.uvRotateTop = 1;
					renderer.setRenderBounds(0.0D, 0.0D, 0.25D, 0.125D, 0.25D, 0.75D); renderer.renderStandardBlock(block, x, y, z);
				}
				
				if(pX) {
					renderer.uvRotateTop = 2;
					renderer.setRenderBounds(0.875D, 0.0D, 0.25D, 1.0D, 0.25D, 0.75D); renderer.renderStandardBlock(block, x, y, z);
				}
				
				if(nZ) {
					renderer.uvRotateTop = 3;
					renderer.setRenderBounds(0.25D, 0.0D, 0.0D, 0.75D, 0.25D, 0.125D); renderer.renderStandardBlock(block, x, y, z);
				}
				
				if(pZ) {
					renderer.uvRotateTop = 0;
					renderer.setRenderBounds(0.25D, 0.0D, 0.875D, 0.75D, 0.25D, 1.0D); renderer.renderStandardBlock(block, x, y, z);
				}
				
				renderer.uvRotateTop = 0;
			}
		}

		IIcon iconSteel = ModBlocks.concrete_smooth.getIcon(0, 0);
		IIcon iconGlass = ModBlocks.steel_grate.getIcon(0, 0);
		renderer.setOverrideBlockTexture(iconSteel);

		double minOuter = 0.0;
		double maxOuter = 1.0;
		double minInner = 0.25;
		double maxInner = 0.75;
		
		renderer.setRenderBounds(minOuter, 0.0, minOuter, minInner, 1.0, minInner); renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(maxInner, 0.0, minOuter, maxOuter, 1.0, minInner); renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(minOuter, 0.0, maxInner, minInner, 1.0, maxOuter); renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(maxInner, 0.0, maxInner, maxOuter, 1.0, maxOuter); renderer.renderStandardBlock(block, x, y, z);
		
		double glassMin = 0.125;
		double glassMax = 0.875;

		renderer.setOverrideBlockTexture(iconGlass);
		if(!nX && (!belt || meta != 5)) { renderer.setRenderBounds(glassMin, belt ? 0.25 : 0.0, minInner, glassMin, 1.0, maxInner); renderer.renderStandardBlock(block, x, y, z); }
		if(!pX && (!belt || meta != 4)) { renderer.setRenderBounds(glassMax, belt ? 0.25 : 0.0, minInner, glassMax, 1.0, maxInner); renderer.renderStandardBlock(block, x, y, z); }
		if(!nZ && (!belt || meta != 3)) { renderer.setRenderBounds(minInner, belt ? 0.25 : 0.0, glassMin, maxInner, 1.0, glassMin); renderer.renderStandardBlock(block, x, y, z); }
		if(!pZ && (!belt || meta != 2)) { renderer.setRenderBounds(minInner, belt ? 0.25 : 0.0, glassMax, maxInner, 1.0, glassMax); renderer.renderStandardBlock(block, x, y, z); }
		
		renderer.clearOverrideBlockTexture();

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return BlockConveyorChute.renderID;
	}
}
