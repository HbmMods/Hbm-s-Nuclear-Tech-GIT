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

public class RenderBlockDecoModel implements ISimpleBlockRenderingHandler {
	
	private int renderID;
	private IModelCustom model;
	
	public RenderBlockDecoModel(int renderType, IModelCustom IModelCustom) {
		renderID = renderType;
		model = IModelCustom;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		
		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(0, metadata);
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}
		
		GL11.glTranslated(0, 0.1D, 0);
		GL11.glScaled(1.2D, 1.2D, 1.2D);
		tessellator.startDrawingQuads();
		ObjUtil.renderWithIcon((WavefrontObject) model, iicon, tessellator, 0, false);
		
		tessellator.draw();
		
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		Tessellator tessellator = Tessellator.instance;
		int meta = world.getBlockMetadata(x, y, z);
		IIcon iicon = block.getIcon(0, meta & 3);
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);
		
		float rotation = 0;
		
		switch(meta >> 2) {
		default: //North
			rotation = (float) Math.PI; break;
		case 1: //South
			break;
		case 2: //West
			rotation = 1.5F * (float) Math.PI;break;
		case 3: //East
			rotation = 0.5F * (float) Math.PI; break;
		}
		
		tessellator.addTranslation(x + 0.5F, y + 0.5F, z + 0.5F);
		ObjUtil.renderWithIcon((WavefrontObject) model, iicon, tessellator, rotation, true);
		tessellator.addTranslation(-x - 0.5F, -y - 0.5F, -z - 0.5F);
		
		return false;
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
