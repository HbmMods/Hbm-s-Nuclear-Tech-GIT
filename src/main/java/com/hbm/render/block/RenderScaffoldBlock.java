package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.generic.BlockScaffold;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderScaffoldBlock implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(0, metadata);
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}

		GL11.glTranslated(0, -0.5, 0);
		tessellator.startDrawingQuads();
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.scaffold, iicon, tessellator, 0, false);
		tessellator.draw();

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(0, world.getBlockMetadata(x, y, z));

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}

		float rotation = (float) -Math.PI;

		if((world.getBlockMetadata(x, y, z) & 8) == 0)
			rotation = -90F / 180F * (float) Math.PI;

		tessellator.addTranslation(x + 0.5F, y, z + 0.5F);
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.scaffold, iicon, tessellator, rotation, true);
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return BlockScaffold.renderIDScaffold;
	}

}
