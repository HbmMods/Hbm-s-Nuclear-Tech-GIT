package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.network.CranePartitioner;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderPartitioner implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		tessellator.setColorOpaque_F(1, 1, 1);
		
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslatef(0F, -0.5F, 0F);
		
		tessellator.startDrawingQuads();
		drawPartitioner(tessellator, block, 0, false);
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
		if(meta == 2) rotation = 90F / 180F * (float) Math.PI;
		if(meta == 4) rotation = 180F / 180F * (float) Math.PI;
		if(meta == 5) rotation = 0F / 180F * (float) Math.PI;
		if(meta == 3) rotation = 270F / 180F * (float)Math.PI;
		drawPartitioner(tessellator, block, rotation, true);
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}
	
	private static void drawPartitioner(Tessellator tessellator, Block block, float rotation, boolean shadeNormals) {
		CranePartitioner partitioner = (CranePartitioner) block;
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.crane_buffer, "Side", partitioner.getIcon(0, 0), tessellator, rotation, shadeNormals);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.crane_buffer, "Back", partitioner.iconBack, tessellator, rotation, shadeNormals);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.crane_buffer, "Top_Top.001", partitioner.iconTop, tessellator, rotation, shadeNormals);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.crane_buffer, "Inner", partitioner.iconInner, tessellator, rotation, shadeNormals);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.crane_buffer, "InnerSide", partitioner.iconInnerSide, tessellator, rotation, shadeNormals);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.crane_buffer, "Belt", partitioner.iconBelt, tessellator, rotation, shadeNormals);
	}

	@Override
	public int getRenderId() {
		return CranePartitioner.renderID;
	}
}
