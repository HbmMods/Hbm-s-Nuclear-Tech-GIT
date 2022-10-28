package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockCrystal;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderCrystal implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(0, 0);
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}
		
		GL11.glRotated(180, 0, 1, 0);
		tessellator.startDrawingQuads();

		if(block == ModBlocks.crystal_power)
			ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.crystal_power, iicon, tessellator, 0, false);
		if(block == ModBlocks.crystal_energy)
			ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.crystal_energy, iicon, tessellator, 0, false);
		if(block == ModBlocks.crystal_robust)
			ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.crystal_robust, iicon, tessellator, 0, false);
		if(block == ModBlocks.crystal_trixite)
			ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.crystal_trixite, iicon, tessellator, 0, false);
		
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

		if(block == ModBlocks.crystal_power)
			ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.crystal_power, iicon, tessellator, rotation, flip, true);
		if(block == ModBlocks.crystal_energy)
			ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.crystal_energy, iicon, tessellator, rotation, flip, true);
		if(block == ModBlocks.crystal_robust)
			ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.crystal_robust, iicon, tessellator, rotation, flip, true);
		if(block == ModBlocks.crystal_trixite)
			ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.crystal_trixite, iicon, tessellator, rotation, flip, true);
		
		tessellator.addTranslation(-x - 0.5F, -y - 0.5F, -z - 0.5F);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return BlockCrystal.renderID;
	}
}
