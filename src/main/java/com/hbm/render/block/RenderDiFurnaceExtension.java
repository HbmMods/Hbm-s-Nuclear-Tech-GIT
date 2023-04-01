package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineDiFurnaceExtension;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderDiFurnaceExtension implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		tessellator.setColorOpaque_F(1, 1, 1);
		
		GL11.glTranslatef(0F, -0.5F, 0F);
		tessellator.startDrawingQuads();
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.difurnace_extension, "Top", ModBlocks.machine_difurnace_extension.getIcon(1, 0), tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.difurnace_extension, "Bottom", ModBlocks.machine_difurnace_extension.getIcon(0, 0), tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.difurnace_extension, "Side", ModBlocks.machine_difurnace_extension.getIcon(3, 0), tessellator, 0, false);
		tessellator.draw();

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		
		tessellator.addTranslation(x + 0.5F, y, z + 0.5F);
		
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.difurnace_extension, "Top", ModBlocks.machine_difurnace_extension.getIcon(1, 0), tessellator, 0, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.difurnace_extension, "Bottom", ModBlocks.machine_difurnace_extension.getIcon(0, 0), tessellator, 0, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.difurnace_extension, "Side", ModBlocks.machine_difurnace_extension.getIcon(3, 0), tessellator, 0, true);
		
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return MachineDiFurnaceExtension.renderID;
	}
}
