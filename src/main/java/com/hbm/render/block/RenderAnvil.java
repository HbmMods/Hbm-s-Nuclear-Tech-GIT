package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.machine.NTMAnvil;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderAnvil implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;

		GL11.glTranslated(0, -0.5, 0);
		GL11.glRotated(180, 0, 1, 0);
		tessellator.startDrawingQuads();
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Top", block.getIcon(1, 0), tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Bottom", block.getIcon(0, 0), tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Front", block.getIcon(0, 0), tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Back", block.getIcon(0, 0), tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Left", block.getIcon(0, 0), tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Right", block.getIcon(0, 0), tessellator, 0, false);
		tessellator.draw();

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		float rotation = 0;

		if(world.getBlockMetadata(x, y, z) == 2)
			rotation = 90F / 180F * (float) Math.PI;

		if(world.getBlockMetadata(x, y, z) == 3)
			rotation = 270F / 180F * (float) Math.PI;

		if(world.getBlockMetadata(x, y, z) == 4)
			rotation = 180F / 180F * (float)Math.PI;

		tessellator.addTranslation(x + 0.5F, y, z + 0.5F);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Top", block.getIcon(1, 0), tessellator, rotation, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Bottom", block.getIcon(0, 0), tessellator, rotation, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Front", block.getIcon(0, 0), tessellator, rotation, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Back", block.getIcon(0, 0), tessellator, rotation, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Left", block.getIcon(0, 0), tessellator, rotation, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Right", block.getIcon(0, 0), tessellator, rotation, true);
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return NTMAnvil.renderID;
	}
}
