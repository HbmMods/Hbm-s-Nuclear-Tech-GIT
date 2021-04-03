package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderRBMKReflector implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(0, 0);
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}

		GL11.glTranslated(0, -0.675, 0);
		GL11.glScalef(0.35F, 0.35F, 0.35F);
		
		for(int i = 0; i < 4; i++) {
			tessellator.startDrawingQuads();
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.rbmk_reflector, "Column", iicon, tessellator, 0, false);
			tessellator.draw();
			GL11.glTranslated(0, 1, 0);
		}

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

		tessellator.addTranslation(x + 0.5F, y, z + 0.5F);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.rbmk_reflector, "Column", iicon, tessellator, 0, true);
		
		if(world.getBlock(x, y + 1, z) == Blocks.air)
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.rbmk_element, "Lid", iicon, tessellator, 0, true);
		
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);

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
