package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.network.BlockCable;
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

public class RenderCable implements ISimpleBlockRenderingHandler {

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
		GL11.glScaled(1.25D, 1.25D, 1.25D);
		tessellator.startDrawingQuads();
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "Core", iicon, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posX", iicon, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negX", iicon, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posZ", iicon, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negZ", iicon, tessellator, 0, false);
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

		boolean pX = Library.canConnect(world, x + 1, y, z, Library.POS_X);
		boolean nX = Library.canConnect(world, x - 1, y, z, Library.NEG_X);
		boolean pY = Library.canConnect(world, x, y + 1, z, Library.POS_Y);
		boolean nY = Library.canConnect(world, x, y - 1, z, Library.NEG_Y);
		boolean pZ = Library.canConnect(world, x, y, z + 1, Library.POS_Z);
		boolean nZ = Library.canConnect(world, x, y, z - 1, Library.NEG_Z);
		
		tessellator.addTranslation(x + 0.5F, y + 0.5F, z + 0.5F);

		if(pX && nX && !pY && !nY && !pZ && !nZ)
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "CX", iicon, tessellator, 0, true);
		else if(!pX && !nX && pY && nY && !pZ && !nZ)
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "CY", iicon, tessellator, 0, true);
		else if(!pX && !nX && !pY && !nY && pZ && nZ)
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "CZ", iicon, tessellator, 0, true);
		
		else {
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "Core", iicon, tessellator, 0, true);
			if(pX) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posX", iicon, tessellator, 0, true);
			if(nX) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negX", iicon, tessellator, 0, true);
			if(pY) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posY", iicon, tessellator, 0, true);
			if(nY) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negY", iicon, tessellator, 0, true);
			if(nZ) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "posZ", iicon, tessellator, 0, true);
			if(pZ) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.cable_neo, "negZ", iicon, tessellator, 0, true);
		}
		
		tessellator.addTranslation(-x - 0.5F, -y - 0.5F, -z - 0.5F);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return BlockCable.renderID;
	}
}
