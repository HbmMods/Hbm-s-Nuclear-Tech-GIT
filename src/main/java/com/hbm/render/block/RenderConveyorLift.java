package com.hbm.render.block;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.network.BlockConveyorLift;

import api.hbm.conveyor.IConveyorBelt;
import api.hbm.conveyor.IEnterableBlock;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderConveyorLift implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		int meta = world.getBlockMetadata(x, y, z);

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		boolean isBottom = false;
		boolean isTop = false;

		IIcon iconConcrete = ModBlocks.concrete_smooth.getIcon(0, 0);
		IIcon iconIron = Blocks.iron_block.getIcon(0, 0);
		IIcon iconBelt = block.getIcon(0, 0);
		
		
		if(y > 0) {
			Block below = world.getBlock(x, y - 1, z);
			if(!(below instanceof IConveyorBelt)) {
				
				renderer.setOverrideBlockTexture(iconBelt);

				if(meta != 5) {
					renderer.uvRotateTop = 1;
					renderer.uvRotateBottom = 1;
					renderer.setRenderBounds(0.0D, 0.0D, 0.25D, 0.25D, 0.25D, 0.75D); renderer.renderStandardBlock(block, x, y, z);
				}
				if(meta != 4) {
					renderer.uvRotateTop = 2;
					renderer.uvRotateBottom = 2;
					renderer.setRenderBounds(0.75D, 0.0D, 0.25D, 1.0D, 0.25D, 0.75D); renderer.renderStandardBlock(block, x, y, z);
				}
				if(meta != 3) {
					renderer.uvRotateTop = 3;
					renderer.uvRotateBottom = 0;
					renderer.setRenderBounds(0.25D, 0.0D, 0.0D, 0.75D, 0.25D, 0.25D); renderer.renderStandardBlock(block, x, y, z);
				}
				if(meta != 2) {
					renderer.uvRotateTop = 0;
					renderer.uvRotateBottom = 3;
					renderer.setRenderBounds(0.25D, 0.0D, 0.75D, 0.75D, 0.25D, 1.0D); renderer.renderStandardBlock(block, x, y, z);
				}

				renderer.uvRotateTop = 0;
				renderer.uvRotateBottom = 0;
				
				renderer.clearOverrideBlockTexture();
				
				isBottom = true;
			}
		}
		
		if(y < 255) {
			Block above = world.getBlock(x, y + 1, z);
			isTop = !(above instanceof IConveyorBelt) && !isBottom && !(world.getBlock(x, y + 1, z) instanceof IEnterableBlock);
		}

		double minOuter = 0.0;
		double maxOuter = 1.0;
		double minInner = 0.25;
		double maxInner = 0.75;

		renderer.setOverrideBlockTexture(iconConcrete);
		
		if(!isTop) {
			if(meta == 2) {
				renderer.setRenderBounds(minOuter, 0.0, minOuter, minInner, 1.0, minInner); renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(maxInner, 0.0, minOuter, maxOuter, 1.0, minInner); renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(minOuter, 0.0, maxInner, maxOuter, 1.0, maxOuter); renderer.renderStandardBlock(block, x, y, z);
				renderer.setOverrideBlockTexture(iconBelt);
				renderer.uvRotateTop = 3;
				renderer.setRenderBounds(minInner, 0.0, maxInner - 0.125, maxInner, 1.0, maxInner); renderer.renderStandardBlock(block, x, y, z);
			}
			if(meta == 3) {
				renderer.setRenderBounds(minOuter, 0.0, minOuter, maxOuter, 1.0, minInner); renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(minOuter, 0.0, maxInner, minInner, 1.0, maxOuter); renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(maxInner, 0.0, maxInner, maxOuter, 1.0, maxOuter); renderer.renderStandardBlock(block, x, y, z);
				renderer.setOverrideBlockTexture(iconBelt);
				renderer.uvRotateTop = 0;
				renderer.setRenderBounds(minInner, 0.0, minInner, maxInner, 1.0, minInner + 0.125); renderer.renderStandardBlock(block, x, y, z);
			}
			if(meta == 4) {
				renderer.setRenderBounds(minOuter, 0.0, minOuter, minInner, 1.0, minInner); renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(minOuter, 0.0, maxInner, minInner, 1.0, maxOuter); renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(maxInner, 0.0, minOuter, maxOuter, 1.0, maxOuter); renderer.renderStandardBlock(block, x, y, z);
				renderer.setOverrideBlockTexture(iconBelt);
				renderer.uvRotateTop = 1;
				renderer.setRenderBounds(maxInner - 0.125, 0.0, minInner, maxInner, 1.0, maxInner); renderer.renderStandardBlock(block, x, y, z);
			}
			if(meta == 5) {
				renderer.setRenderBounds(maxInner, 0.0, minOuter, maxOuter, 1.0, minInner); renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(maxInner, 0.0, maxInner, maxOuter, 1.0, maxOuter); renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(minOuter, 0.0, minOuter, minInner, 1.0, maxOuter); renderer.renderStandardBlock(block, x, y, z);
				renderer.setOverrideBlockTexture(iconBelt);
				renderer.uvRotateTop = 2;
				renderer.setRenderBounds(minInner, 0.0, minInner, minInner + 0.125, 1.0, maxInner); renderer.renderStandardBlock(block, x, y, z);
			}
		} else {
			if(meta == 2 || meta == 3) {
				renderer.setRenderBounds(minOuter, 0.0, minOuter, minInner, 0.5, maxOuter); renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(maxInner, 0.0, minOuter, maxOuter, 0.5, maxOuter); renderer.renderStandardBlock(block, x, y, z);
				renderer.clearOverrideBlockTexture();
				
				if(meta == 2) {
					renderer.uvRotateTop = 3;
					renderer.uvRotateWest = 3;
					renderer.setRenderBounds(minInner, 0.0, maxInner - 0.125, maxInner, 0.25, maxOuter); renderer.renderStandardBlock(block, x, y, z);
				} else {
					renderer.uvRotateTop = 0;
					renderer.uvRotateEast = 3;
					renderer.setRenderBounds(minInner, 0.0, minOuter, maxInner, 0.25, minInner + 0.125); renderer.renderStandardBlock(block, x, y, z);
				}
			}
			
			if(meta == 4 || meta == 5) {
				renderer.setRenderBounds(minOuter, 0.0, minOuter, maxOuter, 0.5, minInner); renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(minOuter, 0.0, maxInner, maxOuter, 0.5, maxOuter); renderer.renderStandardBlock(block, x, y, z);
				renderer.clearOverrideBlockTexture();
				
				if(meta == 4) {
					renderer.uvRotateTop = 1;
					renderer.uvRotateSouth = 3;
					renderer.setRenderBounds(maxInner - 0.125, 0.0, minInner, maxOuter, 0.25, maxInner); renderer.renderStandardBlock(block, x, y, z);
				} else {
					renderer.uvRotateTop = 2;
					renderer.uvRotateNorth = 3;
					renderer.setRenderBounds(minOuter, 0.0, minInner, minInner + 0.125, 0.25, maxInner); renderer.renderStandardBlock(block, x, y, z);
				}
			}
		}

		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;

		if(isBottom) {
			renderer.setOverrideBlockTexture(iconIron);
			renderer.setRenderBounds(0.25 + (meta == 5 ? 0.125 : 0), 0, 0.25 + (meta == 3 ? 0.125 : 0), 0.75 - (meta == 4 ? 0.125 : 0), 0.25, 0.75 - (meta == 2 ? 0.125 : 0)); renderer.renderStandardBlock(block, x, y, z);
		}
		
		renderer.clearOverrideBlockTexture();

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return BlockConveyorLift.renderID;
	}
}
