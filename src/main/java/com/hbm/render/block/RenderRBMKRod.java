package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.blocks.machine.rbmk.RBMKRod;
import com.hbm.main.ResourceManager;
import com.hbm.render.loader.HFRWavefrontObject;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderRBMKRod implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		RBMKRod rod = (RBMKRod) block;
		Tessellator tessellator = Tessellator.instance;
		RBMKBase.renderLid = RBMKBase.LID_NONE;
		IIcon iicon = block.getIcon(0, 0);
		IIcon sideIcon = block.getIcon(2, 0);
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}

		GL11.glTranslated(0, -0.675, 0);
		GL11.glScalef(0.35F, 0.35F, 0.35F);
		
		for(int i = 0; i < 4; i++) {
			tessellator.startDrawingQuads();
			ObjUtil.renderPartWithIcon((HFRWavefrontObject) ResourceManager.rbmk_element, "Inner", rod.inner, tessellator, 0, false);
			ObjUtil.renderPartWithIcon((HFRWavefrontObject) ResourceManager.rbmk_element, "Cap", iicon, tessellator, 0, false);
			tessellator.setNormal(-1F, 0F, 0F);	renderer.renderFaceXNeg(block, -0.5, 0, -0.5, sideIcon);
			tessellator.setNormal(1F, 0F, 0F);	renderer.renderFaceXPos(block, -0.5, 0, -0.5, sideIcon);
			tessellator.setNormal(0F, 0F, -1F);	renderer.renderFaceZNeg(block, -0.5, 0, -0.5, sideIcon);
			tessellator.setNormal(0F, 0F, 1F);	renderer.renderFaceZPos(block, -0.5, 0, -0.5, sideIcon);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorOpaque_I(0x304825);
			ObjUtil.renderPartWithIcon((HFRWavefrontObject) ResourceManager.rbmk_element_rods, "Rods", rod.fuel, tessellator, 0, false);
			tessellator.draw();
			GL11.glTranslated(0, 1, 0);
		}

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		RBMKRod rod = (RBMKRod) block;
		Tessellator tessellator = Tessellator.instance;
		int meta = world.getBlockMetadata(x, y, z);
		IIcon iicon = block.getIcon(0, meta);

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}
		
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		rod.overrideOnlyRenderSides = true;
		renderer.renderStandardBlock(block, x, y, z);
		rod.overrideOnlyRenderSides = false;

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.addTranslation(x + 0.5F, y, z + 0.5F);
		ObjUtil.renderPartWithIcon((HFRWavefrontObject) ResourceManager.rbmk_element, "Cap", iicon, tessellator, 0, true);
		ObjUtil.renderPartWithIcon((HFRWavefrontObject) ResourceManager.rbmk_element, "Inner", rod.inner, tessellator, 0, true);
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);
		
		if(meta >= 6 && meta < 12) {
			int[] pos = ((BlockDummyable) block).findCore(world, x, y, z);
			if(pos != null) {
				int coreMeta = world.getBlockMetadata(pos[0], pos[1], pos[2]);
				int lid = RBMKBase.metaToLid(coreMeta);

				if(lid != RBMKBase.LID_NONE) {
					renderer.setRenderBounds(0, 0, 0, 1, 0.25, 1);
					RBMKBase.renderLid = lid;
					renderer.renderStandardBlock(block, x, y + 1, z);
					RBMKBase.renderLid = RBMKBase.LID_NONE;
				}
			}
		}

		return true;
	}

	@Override public boolean shouldRender3DInInventory(int modelId) { return true; }
	@Override public int getRenderId() { return RBMKBase.renderIDRods; }
}
