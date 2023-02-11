package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.network.CableDiode;
import com.hbm.lib.Library;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderDiode implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		block.setBlockBoundsForItemRender();
		renderer.setRenderBoundsFromBlock(block);
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.625F, -0.5F);
		
		IIcon iiconPad = ModBlocks.hadron_coil_alloy.getIcon(0, 0);
		IIcon iconCable = ModBlocks.red_cable.getIcon(0, 0);
		tessellator.setColorOpaque_F(1, 1, 1);
		
		for(int i = 0; i< 2; i++) {
			
			if(i == 0) {
				renderer.setRenderBounds( 0D, 0.875D, 0D, 1D, 1D, 1D);
			} else {

				renderer.setOverrideBlockTexture(iiconPad);
				double radius = 0.375D;
				renderer.setRenderBounds(0.5D - radius, 0.5D - radius, 0.5D - radius, 0.5D + radius, 0.5D + radius, 0.5D + radius);
			}
			
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
			tessellator.draw();
		}

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glRotated(180, 0, 1, 0);
		
		tessellator.startDrawingQuads();
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posX", iconCable, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negX", iconCable, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negY", iconCable, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posZ", iconCable, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negZ", iconCable, tessellator, 0, false);
		tessellator.draw();
		
		renderer.clearOverrideBlockTexture();

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		IIcon iiconPad = ModBlocks.hadron_coil_alloy.getIcon(0, 0);
		IIcon iconCable = ModBlocks.red_cable.getIcon(0, 0);
		int meta = world.getBlockMetadata(x, y, z);
		tessellator.setColorOpaque_F(1, 1, 1);
		
		double width = 0.875D;
		renderer.setRenderBounds(
				0D + (meta == 4 ? width : 0),
				0D + (meta == 0 ? width : 0),
				0D + (meta == 2 ? width : 0),
				1D - (meta == 5 ? width : 0),
				1D - (meta == 1 ? width : 0),
				1D - (meta == 3 ? width : 0)
				);
		renderer.renderStandardBlock(block, x, y, z);
		
		renderer.setOverrideBlockTexture(iiconPad);
		double radius = 0.375D;
		double minus = 0D;
		renderer.setRenderBounds(
				0.5D - radius + (meta == 4 ? minus : 0),
				0.5D - radius + (meta == 0 ? minus : 0),
				0.5D - radius + (meta == 2 ? minus : 0),
				0.5D + radius - (meta == 5 ? minus : 0),
				0.5D + radius - (meta == 1 ? minus : 0),
				0.5D + radius - (meta == 3 ? minus : 0)
				);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.clearOverrideBlockTexture();

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		boolean pX = Library.canConnect(world, x + 1, y, z, Library.POS_X);
		boolean nX = Library.canConnect(world, x - 1, y, z, Library.NEG_X);
		boolean pY = Library.canConnect(world, x, y + 1, z, Library.POS_Y);
		boolean nY = Library.canConnect(world, x, y - 1, z, Library.NEG_Y);
		boolean pZ = Library.canConnect(world, x, y, z + 1, Library.POS_Z);
		boolean nZ = Library.canConnect(world, x, y, z - 1, Library.NEG_Z);
		
		tessellator.addTranslation(x + 0.5F, y + 0.5F, z + 0.5F);
		
		//ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "Core", iconCable, tessellator, 0, true);
		if(pX) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posX", iconCable, tessellator, 0, true);
		if(nX) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negX", iconCable, tessellator, 0, true);
		if(pY) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posY", iconCable, tessellator, 0, true);
		if(nY) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negY", iconCable, tessellator, 0, true);
		if(nZ) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posZ", iconCable, tessellator, 0, true);
		if(pZ) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negZ", iconCable, tessellator, 0, true);
		
		tessellator.addTranslation(-x - 0.5F, -y - 0.5F, -z - 0.5F);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return CableDiode.renderID;
	}
}
