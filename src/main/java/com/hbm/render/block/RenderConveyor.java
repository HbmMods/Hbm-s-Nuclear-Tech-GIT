package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.network.BlockConveyor;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderConveyor implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(0, 0);
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}

		GL11.glTranslated(0, -0.125, 0);
		tessellator.startDrawingQuads();
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.arrow, iicon, tessellator, 0, false);
		tessellator.draw();

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		int meta = world.getBlockMetadata(x, y, z);

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

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

		renderer.setRenderBounds((double) 0, 0.0D, (double) 0, (double) 1, 0.25D, (double) 1);
		renderer.renderStandardBlock(block, x, y, z);
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
		return BlockConveyor.renderID;
	}

}
