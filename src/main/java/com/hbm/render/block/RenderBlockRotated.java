package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderBlockRotated implements ISimpleBlockRenderingHandler {

	private int renderID;
	private IModelCustom model;
	
	public RenderBlockRotated(int renderType, IModelCustom IModelCustom) {
		this.renderID = renderType;
		this.model = IModelCustom;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(0, 0);
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}

		GL11.glRotated(-90, 0, 1, 0);
		GL11.glRotated(-90, 0, 0, 1);
		GL11.glTranslated(0, 0.375, 0);
		tessellator.startDrawingQuads();
		ObjUtil.renderWithIcon((WavefrontObject) model, iicon, tessellator, 0, false);
		tessellator.draw();

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(0, 0);
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		float flip = 0;
		float rotation = 0;
		
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta == 0)
			flip = (float)Math.PI;

		if(meta == 2)
			rotation = 90F / 180F * (float) Math.PI;

		if(meta == 3)
			rotation = 270F / 180F * (float) Math.PI;

		if(meta == 4)
			rotation = 180F / 180F * (float)Math.PI;
		
		if(rotation != 0F || meta == 5)
			flip = (float)Math.PI * 0.5F;

		tessellator.addTranslation(x + 0.5F, y + 0.5F, z + 0.5F);
		ObjUtil.renderWithIcon((WavefrontObject) model, iicon, tessellator, rotation, flip, true);
		tessellator.addTranslation(-x - 0.5F, -y - 0.5F, -z - 0.5F);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return this.renderID;
	}
}
