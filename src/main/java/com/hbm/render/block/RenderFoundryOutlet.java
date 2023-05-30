package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.machine.FoundryOutlet;
import com.hbm.tileentity.machine.TileEntityFoundryOutlet;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class RenderFoundryOutlet implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		
		double bot = 0.0D;
		double top = 0.5D;
		double x = 0;
		double y = 0;
		double z = 0;
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		FoundryOutlet outlet = (FoundryOutlet) block;

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		
		renderer.setRenderBounds(0.3125D, bot, 0D, 0.6875D, top, 0.375D);
		
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, outlet.iconTop);
		renderer.renderFaceYPos(block, x, y - 0.375D, z, outlet.iconBottom);

		tessellator.setNormal(0F, -1F, 0F);
		renderer.renderFaceYNeg(block, x, y, z, outlet.iconBottom);

		tessellator.setNormal(1F, 0F, 0F);
		renderer.field_152631_f = true;
		renderer.renderFaceXPos(block, x, y, z, outlet.iconSide);
		renderer.renderFaceXPos(block, x - 0.3125D, y, z, outlet.iconInner);
		renderer.field_152631_f = false;

		tessellator.setNormal(-1F, 0F, 0F);
		renderer.renderFaceXNeg(block, x, y, z, outlet.iconSide);
		renderer.renderFaceXNeg(block, x + 0.3125D, y, z, outlet.iconInner);

		tessellator.setNormal(0F, 0F, 1F);
		renderer.renderFaceZPos(block, x, y, z, outlet.iconFront);

		tessellator.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		Tessellator tessellator = Tessellator.instance;
		int colorMult = block.colorMultiplier(world, x, y, z);
		float r = (float) (colorMult >> 16 & 255) / 255.0F;
		float g = (float) (colorMult >> 8 & 255) / 255.0F;
		float b = (float) (colorMult & 255) / 255.0F;
		
		float mulBottom = 0.5F;
		float mulTop = 1.0F;
		float mulZ = 0.8F;
		float mulX = 0.6F;
		
		double bot = 0.0D;
		double top = 0.5D;

		if(EntityRenderer.anaglyphEnable) {
			float aR = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
			float aG = (r * 30.0F + g * 70.0F) / 100.0F;
			float aB = (r * 30.0F + b * 70.0F) / 100.0F;
			r = aR;
			g = aG;
			b = aB;
		}
		
		FoundryOutlet outlet = (FoundryOutlet) block;
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity tile = world.getTileEntity(x, y, z);
		TileEntityFoundryOutlet tileOutlet = tile instanceof TileEntityFoundryOutlet ? (TileEntityFoundryOutlet) tile : null;

		int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
		tessellator.setBrightness(brightness);
		
		if(meta == 4) {
			renderer.setRenderBounds(0.625D, bot, 0.3125D, 1D, top, 0.6875D);
			tessellator.setColorOpaque_F(r * mulBottom, g * mulBottom, b * mulBottom);
			renderer.renderFaceYNeg(block, x, y, z, outlet.iconBottom);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, outlet.iconTop);
			renderer.renderFaceYPos(block, x, y - 0.375D, z, outlet.iconBottom);
			tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
			renderer.renderFaceZPos(block, x, y, z, outlet.iconSide);
			renderer.renderFaceZPos(block, x, y, z - 0.3125D, outlet.iconInner);
			renderer.field_152631_f = true;
			renderer.renderFaceZNeg(block, x, y, z, outlet.iconSide);
			renderer.renderFaceZNeg(block, x, y, z + 0.3125D, outlet.iconInner);
			renderer.field_152631_f = false;
			tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
			renderer.renderFaceXPos(block, x, y, z, outlet.iconFront);
			renderer.renderFaceXNeg(block, x, y, z, outlet.iconFront);
			
			if(tileOutlet != null && tileOutlet.filter != null) {
				renderer.setRenderBounds(0.96875D, 0.0625, 0.375D, 0.96875D, top, 0.625D);
				renderer.renderFaceXPos(block, x, y, z, outlet.iconFilter);
				renderer.renderFaceXNeg(block, x, y, z, outlet.iconFilter);
			}
			
			if(tileOutlet != null && tileOutlet.isClosed()) {
				renderer.setRenderBounds(0.9375D, 0.0625, 0.375D, 0.9375D, top, 0.625D);
				renderer.renderFaceXPos(block, x, y, z, outlet.iconLock);
				renderer.renderFaceXNeg(block, x, y, z, outlet.iconLock);
			}
		}
		
		if(meta == 5) {
			renderer.setRenderBounds(0D, bot, 0.3125D, 0.375D, top, 0.6875D);
			tessellator.setColorOpaque_F(r * mulBottom, g * mulBottom, b * mulBottom);
			renderer.renderFaceYNeg(block, x, y, z, outlet.iconBottom);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, outlet.iconTop);
			renderer.renderFaceYPos(block, x, y - 0.375D, z, outlet.iconBottom);
			tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
			renderer.renderFaceZPos(block, x, y, z, outlet.iconSide);
			renderer.renderFaceZPos(block, x, y, z - 0.3125D, outlet.iconInner);
			renderer.field_152631_f = true;
			renderer.renderFaceZNeg(block, x, y, z, outlet.iconSide);
			renderer.renderFaceZNeg(block, x, y, z + 0.3125D, outlet.iconInner);
			renderer.field_152631_f = false;
			tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
			renderer.renderFaceXPos(block, x, y, z, outlet.iconFront);
			renderer.renderFaceXNeg(block, x, y, z, outlet.iconFront);
			
			if(tileOutlet != null && tileOutlet.filter != null) {
				renderer.setRenderBounds(0.03125D, 0.0625, 0.375D, 0.03125D, top, 0.625D);
				renderer.renderFaceXPos(block, x, y, z, outlet.iconFilter);
				renderer.renderFaceXNeg(block, x, y, z, outlet.iconFilter);
			}
			
			if(tileOutlet != null && tileOutlet.isClosed()) {
				renderer.setRenderBounds(0.0625D, 0.0625, 0.375D, 0.0625D, top, 0.625D);
				renderer.renderFaceXPos(block, x, y, z, outlet.iconLock);
				renderer.renderFaceXNeg(block, x, y, z, outlet.iconLock);
			}
		}
		
		if(meta == 2) {
			renderer.setRenderBounds(0.3125D, bot, 0.625D, 0.6875D, top, 1D);
			tessellator.setColorOpaque_F(r * mulBottom, g * mulBottom, b * mulBottom);
			renderer.renderFaceYNeg(block, x, y, z, outlet.iconBottom);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, outlet.iconTop);
			renderer.renderFaceYPos(block, x, y - 0.375D, z, outlet.iconBottom);
			tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
			renderer.field_152631_f = true;
			renderer.renderFaceXPos(block, x, y, z, outlet.iconSide);
			renderer.renderFaceXPos(block, x - 0.3125D, y, z, outlet.iconInner);
			renderer.field_152631_f = false;
			renderer.renderFaceXNeg(block, x, y, z, outlet.iconSide);
			renderer.renderFaceXNeg(block, x + 0.3125D, y, z, outlet.iconInner);
			tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
			renderer.renderFaceZPos(block, x, y, z, outlet.iconFront);
			renderer.renderFaceZNeg(block, x, y, z, outlet.iconFront);
			
			if(tileOutlet != null && tileOutlet.filter != null) {
				renderer.setRenderBounds(0.375D, 0.0625, 0.96875D, 0.625D, top, 0.96875D);
				renderer.renderFaceZPos(block, x, y, z, outlet.iconFilter);
				renderer.renderFaceZNeg(block, x, y, z, outlet.iconFilter);
			}
			
			if(tileOutlet != null && tileOutlet.isClosed()) {
				renderer.setRenderBounds(0.375D, 0.0625, 0.9375D, 0.625D, top, 0.9375D);
				renderer.renderFaceZPos(block, x, y, z, outlet.iconLock);
				renderer.renderFaceZNeg(block, x, y, z, outlet.iconLock);
			}
		}
		
		if(meta == 3) {
			renderer.setRenderBounds(0.3125D, bot, 0D, 0.6875D, top, 0.375D);
			tessellator.setColorOpaque_F(r * mulBottom, g * mulBottom, b * mulBottom);
			renderer.renderFaceYNeg(block, x, y, z, outlet.iconBottom);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, outlet.iconTop);
			renderer.renderFaceYPos(block, x, y - 0.375D, z, outlet.iconBottom);
			tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
			renderer.field_152631_f = true;
			renderer.renderFaceXPos(block, x, y, z, outlet.iconSide);
			renderer.renderFaceXPos(block, x - 0.3125D, y, z, outlet.iconInner);
			renderer.field_152631_f = false;
			renderer.renderFaceXNeg(block, x, y, z, outlet.iconSide);
			renderer.renderFaceXNeg(block, x + 0.3125D, y, z, outlet.iconInner);
			tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
			renderer.renderFaceZPos(block, x, y, z, outlet.iconFront);
			renderer.renderFaceZNeg(block, x, y, z, outlet.iconFront);
			
			if(tileOutlet != null && tileOutlet.filter != null) {
				renderer.setRenderBounds(0.375D, 0.0625D, 0.03125, 0.625D, top, 0.03125D);
				renderer.renderFaceZPos(block, x, y, z, outlet.iconFilter);
				renderer.renderFaceZNeg(block, x, y, z, outlet.iconFilter);
			}
			
			if(tileOutlet != null && tileOutlet.isClosed()) {
				renderer.setRenderBounds(0.375D, 0.0625, 0.0625D, 0.625D, top, 0.0625D);
				renderer.renderFaceZPos(block, x, y, z, outlet.iconLock);
				renderer.renderFaceZNeg(block, x, y, z, outlet.iconLock);
			}
		}
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return FoundryOutlet.renderID;
	}
}
