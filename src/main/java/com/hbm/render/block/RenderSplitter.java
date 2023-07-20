package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.network.CraneSplitter;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderSplitter implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		tessellator.setColorOpaque_F(1, 1, 1);
		
		GL11.glScaled(0.625, 0.625, 0.625);
		GL11.glRotated(-90, 0, 1, 0);
		GL11.glTranslatef(0F, -0.5F, 0.5F);
		tessellator.startDrawingQuads();
		drawSplitter(tessellator, block, true, 0, false);
		tessellator.draw();
		GL11.glTranslatef(0F, 0F, -1F);
		tessellator.startDrawingQuads();
		drawSplitter(tessellator, block, false, 0, false);
		tessellator.draw();

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);
		
		tessellator.addTranslation(x + 0.5F, y, z + 0.5F);

		int meta = world.getBlockMetadata(x, y, z);
		float rotation = 0;
		if(meta == 12 || meta == 4) rotation = 90F / 180F * (float) Math.PI;
		if(meta == 13 || meta == 5) rotation = 270F / 180F * (float) Math.PI;
		if(meta == 14 || meta == 3) rotation = 180F / 180F * (float)Math.PI;
		
		boolean isLeft = meta >= 12;
		drawSplitter(tessellator, block, isLeft, rotation, true);
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);
		
		return true;
	}
	
	private static void drawSplitter(Tessellator tessellator, Block block, boolean isLeft, float rotation, boolean shadeNormals) {
		CraneSplitter splitter = (CraneSplitter) block;
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.splitter, "Top", isLeft ? splitter.iconTopLeft : splitter.iconTopRight, tessellator, rotation, shadeNormals);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.splitter, "Bottom", isLeft ? splitter.iconTopRight : splitter.iconTopLeft , tessellator, rotation, shadeNormals);
		if(isLeft) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.splitter, "Left", splitter.iconLeft, tessellator, rotation, shadeNormals);
		if(!isLeft) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.splitter, "Right", splitter.iconRight, tessellator, rotation, shadeNormals);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.splitter, "Back", isLeft ? splitter.iconBackLeft : splitter.iconBackRight, tessellator, rotation, shadeNormals);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.splitter, "Front", isLeft ? splitter.iconFrontLeft : splitter.iconFrontRight, tessellator, rotation, shadeNormals);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.splitter, "Inner", splitter.iconInner, tessellator, rotation, shadeNormals);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.splitter, "InnerLeft", splitter.iconInnerSide, tessellator, rotation, shadeNormals);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.splitter, "InnerRight", splitter.iconInnerSide, tessellator, rotation, shadeNormals);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.splitter, "InnerTop", splitter.iconInnerSide, tessellator, rotation, shadeNormals);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.splitter, "InnerBottom", splitter.iconBelt, tessellator, rotation, shadeNormals);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return CraneSplitter.renderID;
	}
}
