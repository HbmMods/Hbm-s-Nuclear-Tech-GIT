package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.machine.rbmk.RBMKBase;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class RenderRBMKReflector implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		tessellator.setColorOpaque_F(1, 1, 1);

		GL11.glTranslated(0, -0.675, 0);
		GL11.glScalef(0.35F, 0.35F, 0.35F);
		
		for(int i = 0; i < 4; i++) {
			tessellator.startDrawingQuads();
			tessellator.setNormal(0F, 1F, 0F);	renderer.renderFaceYPos(block, -0.5, 0, -0.5, block.getIcon(ForgeDirection.UP.ordinal(), 0));
			tessellator.setNormal(0F, -1F, 0F);	renderer.renderFaceYNeg(block, -0.5, 0, -0.5, block.getIcon(ForgeDirection.DOWN.ordinal(), 0));
			tessellator.setNormal(1F, 0F, 0F);	renderer.renderFaceXPos(block, -0.5, 0, -0.5, block.getIcon(ForgeDirection.NORTH.ordinal(), 0));
			tessellator.setNormal(-1F, 0F, 0F);	renderer.renderFaceXNeg(block, -0.5, 0, -0.5, block.getIcon(ForgeDirection.SOUTH.ordinal(), 0));
			tessellator.setNormal(0F, 0F, 1F);	renderer.renderFaceZPos(block, -0.5, 0, -0.5, block.getIcon(ForgeDirection.EAST.ordinal(), 0));
			tessellator.setNormal(0F, 0F, -1F);	renderer.renderFaceZNeg(block, -0.5, 0, -0.5, block.getIcon(ForgeDirection.WEST.ordinal(), 0));
			tessellator.draw();
			GL11.glTranslated(0, 1, 0);
		}

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);
		int meta = world.getBlockMetadata(x, y, z);
		
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		renderer.renderStandardBlock(block, x, y, z);
		
		if(meta >= 6 && meta < 12) {
			int[] pos = ((BlockDummyable) block).findCore(world, x, y, z);
			if(pos != null) {
				int coreMeta = world.getBlockMetadata(pos[0], pos[1], pos[2]);
				
				if(coreMeta - 10 == RBMKBase.DIR_NORMAL_LID.ordinal()) {
					tessellator.addTranslation(0, 1, 0);
					renderer.setRenderBounds(0, 0, 0, 1, 0.25, 1);
					RBMKBase.renderLid = true;
					renderer.renderStandardBlock(block, x, y, z);
					RBMKBase.renderLid = false;
					tessellator.addTranslation(0, -1, 0);
				}
			}
		}

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RBMKBase.renderIDPassive;
	}
}
