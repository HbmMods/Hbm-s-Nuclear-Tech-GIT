package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.test.TestPipe;
import com.hbm.lib.Library;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import api.hbm.fluid.IFluidConductor;
import api.hbm.fluid.IFluidConnector;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderTestPipe implements ISimpleBlockRenderingHandler {

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
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "pX", iicon, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "nX", iicon, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "pZ", iicon, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "nZ", iicon, tessellator, 0, false);
		tessellator.draw();

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(0, 0);
		IIcon overlay = block.getIcon(1, 0);
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		boolean pX = world.getTileEntity(x + 1, y, z) instanceof IFluidConductor;
		boolean nX = world.getTileEntity(x - 1, y, z) instanceof IFluidConductor;
		boolean pY = world.getTileEntity(x, y + 1, z) instanceof IFluidConductor;
		boolean nY = world.getTileEntity(x, y - 1, z) instanceof IFluidConductor;
		boolean pZ = world.getTileEntity(x, y, z + 1) instanceof IFluidConductor;
		boolean nZ = world.getTileEntity(x, y, z - 1) instanceof IFluidConductor;
		
		int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);
		
		tessellator.addTranslation(x + 0.5F, y + 0.5F, z + 0.5F);
		
		int color = 0xff0000;
		
		if(mask == 0) {
			renderDuct(iicon, overlay, color, tessellator, "pX");
			renderDuct(iicon, overlay, color, tessellator, "nX");
			renderDuct(iicon, overlay, color, tessellator, "pY");
			renderDuct(iicon, overlay, color, tessellator, "nY");
			renderDuct(iicon, overlay, color, tessellator, "pZ");
			renderDuct(iicon, overlay, color, tessellator, "nZ");
		} else if(mask == 0b100000 || mask == 0b010000) {
			renderDuct(iicon, overlay, color, tessellator, "pX");
			renderDuct(iicon, overlay, color, tessellator, "nX");
		} else if(mask == 0b001000 || mask == 0b000100) {
			renderDuct(iicon, overlay, color, tessellator, "pY");
			renderDuct(iicon, overlay, color, tessellator, "nY");
		} else if(mask == 0b000010 || mask == 0b000001) {
			renderDuct(iicon, overlay, color, tessellator, "pZ");
			renderDuct(iicon, overlay, color, tessellator, "nZ");
		} else {
	
			if(pX) renderDuct(iicon, overlay, color, tessellator, "pX");
			if(nX) renderDuct(iicon, overlay, color, tessellator, "nX");
			if(pY) renderDuct(iicon, overlay, color, tessellator, "pY");
			if(nY) renderDuct(iicon, overlay, color, tessellator, "nY");
			if(pZ) renderDuct(iicon, overlay, color, tessellator, "nZ");
			if(nZ) renderDuct(iicon, overlay, color, tessellator, "pZ");
	
			if(!pX && !pY && !pZ) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "ppn", iicon, tessellator, 0, true);
			if(!pX && !pY && !nZ) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "ppp", iicon, tessellator, 0, true);
			if(!nX && !pY && !pZ) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "npn", iicon, tessellator, 0, true);
			if(!nX && !pY && !nZ) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "npp", iicon, tessellator, 0, true);
			if(!pX && !nY && !pZ) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "pnn", iicon, tessellator, 0, true);
			if(!pX && !nY && !nZ) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "pnp", iicon, tessellator, 0, true);
			if(!nX && !nY && !pZ) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "nnn", iicon, tessellator, 0, true);
			if(!nX && !nY && !nZ) ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "nnp", iicon, tessellator, 0, true);
		}
		
		tessellator.addTranslation(-x - 0.5F, -y - 0.5F, -z - 0.5F);

		return true;
	}
	
	private void renderDuct(IIcon iicon, IIcon overlay, int color, Tessellator tessellator, String part) {
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, part, iicon, tessellator, 0, true);
		ObjUtil.setColor(color);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, part, overlay, tessellator, 0, true);
		ObjUtil.clearColor();
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return TestPipe.renderID;
	}
}
