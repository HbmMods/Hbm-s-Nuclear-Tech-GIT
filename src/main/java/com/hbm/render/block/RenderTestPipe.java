package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.network.FluidDuctStandard;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderTestPipe implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(0, metadata);
		IIcon overlay = block.getIcon(1, metadata);
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
		tessellator.startDrawingQuads();
		ObjUtil.setColor(Fluids.NONE.getColor());
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "pX", overlay, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "nX", overlay, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "pZ", overlay, tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.pipe_neo, "nZ", overlay, tessellator, 0, false);
		ObjUtil.clearColor();
		tessellator.draw();

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		int meta = world.getBlockMetadata(x, y, z);
		IIcon iicon = block.getIcon(0, meta);
		IIcon overlay = block.getIcon(1, meta);
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		int color = 0xff00ff;
		FluidType type = Fluids.NONE;
		
		if(te instanceof TileEntityPipeBaseNT) {
			TileEntityPipeBaseNT pipe = (TileEntityPipeBaseNT) te;
			color = pipe.getType().getColor();
			type = pipe.getType();
		}

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		boolean pX = Library.canConnectFluid(world, x + 1, y, z, Library.POS_X, type);
		boolean nX = Library.canConnectFluid(world, x - 1, y, z, Library.NEG_X, type);
		boolean pY = Library.canConnectFluid(world, x, y + 1, z, Library.POS_Y, type);
		boolean nY = Library.canConnectFluid(world, x, y - 1, z, Library.NEG_Y, type);
		boolean pZ = Library.canConnectFluid(world, x, y, z + 1, Library.POS_Z, type);
		boolean nZ = Library.canConnectFluid(world, x, y, z - 1, Library.NEG_Z, type);
		
		int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);
		
		tessellator.addTranslation(x + 0.5F, y + 0.5F, z + 0.5F);
		
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
	
			if(!pX && !pY && !pZ) renderDuct(iicon, overlay, color, tessellator, "ppn");
			if(!pX && !pY && !nZ) renderDuct(iicon, overlay, color, tessellator, "ppp");
			if(!nX && !pY && !pZ) renderDuct(iicon, overlay, color, tessellator, "npn");
			if(!nX && !pY && !nZ) renderDuct(iicon, overlay, color, tessellator, "npp");
			if(!pX && !nY && !pZ) renderDuct(iicon, overlay, color, tessellator, "pnn");
			if(!pX && !nY && !nZ) renderDuct(iicon, overlay, color, tessellator, "pnp");
			if(!nX && !nY && !pZ) renderDuct(iicon, overlay, color, tessellator, "nnn");
			if(!nX && !nY && !nZ) renderDuct(iicon, overlay, color, tessellator, "nnp");
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
		return FluidDuctStandard.renderID;
	}
}
