package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.rail.RailStandardBuffer;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderStandardBufferRail implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;

		GL11.glTranslated(0, -0.0625, 0);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(0.3, 0.3, 0.3);
		tessellator.startDrawingQuads();
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.rail_standard_buffer, block.getIcon(1, 0), tessellator, 0, false);
		tessellator.draw();

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		int meta = world.getBlockMetadata(x, y, z);
		if(meta < 12) return true;

		Tessellator tessellator = Tessellator.instance;

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		float rotation = 0;

		if(meta == 12) rotation = (float) Math.PI;
		if(meta == 14) rotation = 270F / 180F * (float) Math.PI;
		if(meta == 15) rotation = 90F / 180F * (float) Math.PI;

		if(meta == 12) tessellator.addTranslation(0.5F, 0F, 0F);
		if(meta == 13) tessellator.addTranslation(-0.5F, 0F, 0F);
		if(meta == 14) tessellator.addTranslation(0F, 0F, -0.5F);
		if(meta == 15) tessellator.addTranslation(0F, 0F, 0.5F);

		tessellator.addTranslation(x + 0.5F, y, z + 0.5F);
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.rail_standard_buffer, block.getIcon(1, 0), tessellator, rotation, true);
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);

		if(meta == 12) tessellator.addTranslation(-0.5F, 0F, 0F);
		if(meta == 13) tessellator.addTranslation(0.5F, 0F, 0F);
		if(meta == 14) tessellator.addTranslation(0F, 0F, 0.5F);
		if(meta == 15) tessellator.addTranslation(0F, 0F, -0.5F);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RailStandardBuffer.renderID;
	}
}
