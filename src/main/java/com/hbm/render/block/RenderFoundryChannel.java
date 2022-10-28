package com.hbm.render.block;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.machine.FoundryChannel;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.TileEntityFoundryChannel;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class RenderFoundryChannel implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		
		double height = 0.5D;
		double x = 0;
		double y = 0;
		double z = 0;
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		FoundryChannel channel = (FoundryChannel) block;

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		
		//center
		renderer.setRenderBounds(0.375D, 0D, 0.375D, 0.625D, 0.125D, 0.625D);
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, channel.iconBottom);
		renderer.setRenderBounds(0.3125D, 0D, 0.3125D, 0.6875D, 0.125D, 0.6875D);
		tessellator.setNormal(0F, -1F, 0F);
		renderer.renderFaceYNeg(block, x, y, z, channel.iconBottom);
		
		//pos X bottom
		renderer.setRenderBounds(0.625D, 0D, 0.3125D, 1D, 0.125D, 0.6875D);
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, channel.iconBottom);
		tessellator.setNormal(0F, -1F, 0F);
		renderer.renderFaceYNeg(block, x, y, z, channel.iconBottom);
		tessellator.setNormal(1F, 0F, 0F);
		renderer.renderFaceXPos(block, x, y, z, channel.iconSide);

		//pos X
		renderer.setRenderBounds(0.625D, 0D, 0.3125D, 1D, height, 0.375D);
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
		tessellator.setNormal(0F, 0F, -1F);
		renderer.renderFaceZNeg(block, x, y, z, channel.iconSide);
		tessellator.setNormal(0F, 0F, 1F);
		renderer.renderFaceZPos(block, x, y, z, channel.iconInner);
		tessellator.setNormal(1F, 0F, 0F);
		renderer.renderFaceXPos(block, x, y, z, channel.iconSide);

		renderer.setRenderBounds(0.625D, 0D, 0.625D, 1D, height, 0.6875D);
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
		tessellator.setNormal(0F, 0F, -1F);
		renderer.renderFaceZNeg(block, x, y, z, channel.iconInner);
		tessellator.setNormal(0F, 0F, 1F);
		renderer.renderFaceZPos(block, x, y, z, channel.iconSide);
		tessellator.setNormal(1F, 0F, 0F);
		renderer.renderFaceXPos(block, x, y, z, channel.iconSide);

		//neg x bottom
		renderer.setRenderBounds(0D, 0D, 0.3125D, 0.375D, 0.125D, 0.6875D);
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, channel.iconBottom);
		tessellator.setNormal(0F, -1F, 0F);
		renderer.renderFaceYNeg(block, x, y, z, channel.iconBottom);
		tessellator.setNormal(-1F, 0F, 0F);
		renderer.renderFaceXNeg(block, x, y, z, channel.iconSide);

		//neg x
		renderer.setRenderBounds(0D, 0D, 0.3125D, 0.375D, height, 0.375D);
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
		tessellator.setNormal(0F, 0F, -1F);
		renderer.renderFaceZNeg(block, x, y, z, channel.iconSide);
		tessellator.setNormal(0F, 0F, 1F);
		renderer.renderFaceZPos(block, x, y, z, channel.iconInner);
		tessellator.setNormal(-1F, 0F, 0F);
		renderer.renderFaceXNeg(block, x, y, z, channel.iconSide);

		renderer.setRenderBounds(0D, 0D, 0.625D, 0.375D, height, 0.6875D);
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
		tessellator.setNormal(0F, 0F, -1F);
		renderer.renderFaceZNeg(block, x, y, z, channel.iconInner);
		tessellator.setNormal(0F, 0F, 1F);
		renderer.renderFaceZPos(block, x, y, z, channel.iconSide);
		tessellator.setNormal(-1F, 0F, 0F);
		renderer.renderFaceXNeg(block, x, y, z, channel.iconSide);

		//pos Z bottom
		renderer.setRenderBounds(0.3125D, 0D, 0.625D, 0.6875D, 0.125D, 1D);
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, channel.iconBottom);
		tessellator.setNormal(0F, -1F, 0F);
		renderer.renderFaceYNeg(block, x, y, z, channel.iconBottom);
		tessellator.setNormal(0F, 0F, 1F);
		renderer.renderFaceZPos(block, x, y, z, channel.iconSide);

		//pos z
		renderer.setRenderBounds(0.3125D, 0D, 0.625D, 0.375D, height, 1D);
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
		tessellator.setNormal(-1F, 0F, 0F);
		renderer.renderFaceXNeg(block, x, y, z, channel.iconSide);
		tessellator.setNormal(1F, 0F, 0F);
		renderer.renderFaceXPos(block, x, y, z, channel.iconInner);
		tessellator.setNormal(0F, 0F, 1F);
		renderer.renderFaceZPos(block, x, y, z, channel.iconSide);

		renderer.setRenderBounds(0.625D, 0D, 0.625D, 0.6875D, height, 1D);
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
		tessellator.setNormal(-1F, 0F, 0F);
		renderer.renderFaceXNeg(block, x, y, z, channel.iconInner);
		tessellator.setNormal(1F, 0F, 0F);
		renderer.renderFaceXPos(block, x, y, z, channel.iconSide);
		tessellator.setNormal(0F, 0F, 1F);
		renderer.renderFaceZPos(block, x, y, z, channel.iconSide);

		//neg z bottom
		renderer.setRenderBounds(0.3125D, 0D, 0D, 0.6875D, 0.125D, 0.375D);
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, channel.iconBottom);
		tessellator.setNormal(0F, -1F, 0F);
		renderer.renderFaceYNeg(block, x, y, z, channel.iconBottom);
		tessellator.setNormal(0F, 0F, -1F);
		renderer.renderFaceZNeg(block, x, y, z, channel.iconSide);

		//neg z
		renderer.setRenderBounds(0.3125D, 0D, 0D, 0.375D, height, 0.375D);
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
		tessellator.setNormal(-1F, 0F, 0F);
		renderer.renderFaceXNeg(block, x, y, z, channel.iconSide);
		tessellator.setNormal(1F, 0F, 0F);
		renderer.renderFaceXPos(block, x, y, z, channel.iconInner);
		tessellator.setNormal(0F, 0F, -1F);
		renderer.renderFaceZNeg(block, x, y, z, channel.iconSide);

		renderer.setRenderBounds(0.625D, 0D, 0D, 0.6875D, height, 0.375D);
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
		tessellator.setNormal(-1F, 0F, 0F);
		renderer.renderFaceXNeg(block, x, y, z, channel.iconInner);
		tessellator.setNormal(1F, 0F, 0F);
		renderer.renderFaceXPos(block, x, y, z, channel.iconSide);
		tessellator.setNormal(0F, 0F, -1F);
		renderer.renderFaceZNeg(block, x, y, z, channel.iconSide);

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

		int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
		int fullBright = 240;
		tessellator.setBrightness(brightness);

		if(EntityRenderer.anaglyphEnable) {
			float aR = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
			float aG = (r * 30.0F + g * 70.0F) / 100.0F;
			float aB = (r * 30.0F + b * 70.0F) / 100.0F;
			r = aR;
			g = aG;
			b = aB;
		}
		
		FoundryChannel channel = (FoundryChannel) block;
		
		TileEntity te = world.getTileEntity(x, y, z);
		TileEntityFoundryChannel tile = null;
		
		if(te instanceof TileEntityFoundryChannel) {
			tile = (TileEntityFoundryChannel) te;
		}
		
		boolean doRender = tile != null ? (tile.amount > 0 && tile.type != null) : false;
		double level = doRender ? tile.amount * 0.25D / tile.getCapacity() : 0;
		Color color = doRender ? new Color(tile.type.moltenColor).brighter() : null;
		
		if(color != null) {
			double brightener = 0.7D;
			int nr = (int) (255D - (255D - color.getRed()) * brightener);
			int ng = (int) (255D - (255D - color.getGreen()) * brightener);
			int nb = (int) (255D - (255D - color.getBlue()) * brightener);
			
			color = new Color(nr, ng, nb);
		}

		boolean posX = channel.canConnectTo(world, x, y, z, Library.POS_X);
		boolean negX = channel.canConnectTo(world, x, y, z, Library.NEG_X);
		boolean posZ = channel.canConnectTo(world, x, y, z, Library.POS_Z);
		boolean negZ = channel.canConnectTo(world, x, y, z, Library.NEG_Z);
		
		double height = 0.5D;
		
		renderer.setRenderBounds(0.375D, 0D, 0.375D, 0.625D, 0.125D, 0.625D);
		tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
		renderer.renderFaceYPos(block, x, y, z, channel.iconBottom);
		renderer.setRenderBounds(0.3125D, 0D, 0.3125D, 0.6875D, 0.125D, 0.6875D);
		tessellator.setColorOpaque_F(r * mulBottom, g * mulBottom, b * mulBottom);
		renderer.renderFaceYNeg(block, x, y, z, channel.iconBottom);
		
		if(doRender) {
			renderer.setRenderBounds(0.375D, 0.125D, 0.375D, 0.625D, 0.125D + level, 0.625D);
			tessellator.setColorOpaque_F(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
			tessellator.setBrightness(fullBright);
			renderer.renderFaceYPos(block, x, y, z, channel.iconLava);
			tessellator.setBrightness(brightness);
		}
		
		if(posX) {
			renderer.setRenderBounds(0.625D, 0D, 0.3125D, 1D, 0.125D, 0.6875D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconBottom);
			tessellator.setColorOpaque_F(r * mulBottom, g * mulBottom, b * mulBottom);
			renderer.renderFaceYNeg(block, x, y, z, channel.iconBottom);
			
			renderer.setRenderBounds(0.625D, 0D, 0.3125D, 1D, height, 0.375D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
			tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
			renderer.renderFaceZNeg(block, x, y, z, channel.iconSide);
			renderer.renderFaceZPos(block, x, y, z, channel.iconInner);
			
			renderer.setRenderBounds(0.625D, 0D, 0.625D, 1D, height, 0.6875D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
			tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
			renderer.renderFaceZNeg(block, x, y, z, channel.iconInner);
			renderer.renderFaceZPos(block, x, y, z, channel.iconSide);
			
			if(doRender) {
				renderer.setRenderBounds(0.625D, 0.125D, 0.3125D, 1D, 0.125D + level, 0.6875D);
				tessellator.setColorOpaque_F(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
				tessellator.setBrightness(fullBright);
				renderer.renderFaceYPos(block, x, y, z, channel.iconLava);
				renderer.renderFaceXPos(block, x, y, z, channel.iconLava);
				tessellator.setBrightness(brightness);
			}
			
		} else {
			renderer.setRenderBounds(0.625, 0D, 0.3125D, 0.6875D, height, 0.6875D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
			tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
			renderer.renderFaceXNeg(block, x, y, z, channel.iconInner);
			renderer.renderFaceXPos(block, x, y, z, channel.iconSide);
		}
		
		if(negX) {
			renderer.setRenderBounds(0D, 0D, 0.3125D, 0.375D, 0.125D, 0.6875D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconBottom);
			tessellator.setColorOpaque_F(r * mulBottom, g * mulBottom, b * mulBottom);
			renderer.renderFaceYNeg(block, x, y, z, channel.iconBottom);
			
			renderer.setRenderBounds(0D, 0D, 0.3125D, 0.375D, height, 0.375D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
			tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
			renderer.renderFaceZNeg(block, x, y, z, channel.iconSide);
			renderer.renderFaceZPos(block, x, y, z, channel.iconInner);
			
			renderer.setRenderBounds(0D, 0D, 0.625D, 0.375D, height, 0.6875D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
			tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
			renderer.renderFaceZNeg(block, x, y, z, channel.iconInner);
			renderer.renderFaceZPos(block, x, y, z, channel.iconSide);
			
			if(doRender) {
				renderer.setRenderBounds(0D, 0.125D, 0.3125D, 0.375D, 0.125D + level, 0.6875D);
				tessellator.setColorOpaque_F(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
				tessellator.setBrightness(fullBright);
				renderer.renderFaceYPos(block, x, y, z, channel.iconLava);
				renderer.renderFaceXNeg(block, x, y, z, channel.iconLava);
				tessellator.setBrightness(brightness);
			}
			
		} else {
			renderer.setRenderBounds(0.3125D, 0D, 0.3125D, 0.375D, height, 0.6875D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
			tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
			renderer.renderFaceXNeg(block, x, y, z, channel.iconSide);
			renderer.renderFaceXPos(block, x, y, z, channel.iconInner);
		}
		
		if(posZ) {
			renderer.setRenderBounds(0.3125D, 0D, 0.625D, 0.6875D, 0.125D, 1D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconBottom);
			tessellator.setColorOpaque_F(r * mulBottom, g * mulBottom, b * mulBottom);
			renderer.renderFaceYNeg(block, x, y, z, channel.iconBottom);
			
			renderer.setRenderBounds(0.3125D, 0D, 0.625D, 0.375D, height, 1D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
			tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
			renderer.renderFaceXNeg(block, x, y, z, channel.iconSide);
			renderer.renderFaceXPos(block, x, y, z, channel.iconInner);
			
			renderer.setRenderBounds(0.625D, 0D, 0.625D, 0.6875D, height, 1D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
			tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
			renderer.renderFaceXNeg(block, x, y, z, channel.iconInner);
			renderer.renderFaceXPos(block, x, y, z, channel.iconSide);
			
			if(doRender) {
				renderer.setRenderBounds(0.3125D, 0.125D, 0.625D, 0.6875D, 0.125D + level, 1D);
				tessellator.setColorOpaque_F(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
				tessellator.setBrightness(fullBright);
				renderer.renderFaceYPos(block, x, y, z, channel.iconLava);
				renderer.renderFaceZPos(block, x, y, z, channel.iconLava);
				tessellator.setBrightness(brightness);
			}
			
		} else {
			renderer.setRenderBounds(0.3125D, 0D, 0.625D, 0.6875D, height, 0.6875D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
			tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
			renderer.renderFaceZNeg(block, x, y, z, channel.iconInner);
			renderer.renderFaceZPos(block, x, y, z, channel.iconSide);
		}
		
		if(negZ) {
			renderer.setRenderBounds(0.3125D, 0D, 0D, 0.6875D, 0.125D, 0.375D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconBottom);
			tessellator.setColorOpaque_F(r * mulBottom, g * mulBottom, b * mulBottom);
			renderer.renderFaceYNeg(block, x, y, z, channel.iconBottom);
			
			renderer.setRenderBounds(0.3125D, 0D, 0D, 0.375D, height, 0.375D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
			tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
			renderer.renderFaceXNeg(block, x, y, z, channel.iconSide);
			renderer.renderFaceXPos(block, x, y, z, channel.iconInner);
			
			renderer.setRenderBounds(0.625D, 0D, 0D, 0.6875D, height, 0.375D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
			tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
			renderer.renderFaceXNeg(block, x, y, z, channel.iconInner);
			renderer.renderFaceXPos(block, x, y, z, channel.iconSide);
			
			if(doRender) {
				renderer.setRenderBounds(0.3125D, 0.125D, 0D, 0.6875D, 0.125D + level, 0.375D);
				tessellator.setColorOpaque_F(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
				tessellator.setBrightness(fullBright);
				renderer.renderFaceYPos(block, x, y, z, channel.iconLava);
				renderer.renderFaceZNeg(block, x, y, z, channel.iconLava);
				tessellator.setBrightness(brightness);
			}
			
		} else {
			renderer.setRenderBounds(0.3125D, 0D, 0.3125D, 0.6875D, height, 0.375D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, channel.iconTop);
			tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
			renderer.renderFaceZNeg(block, x, y, z, channel.iconSide);
			renderer.renderFaceZPos(block, x, y, z, channel.iconInner);
		}
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return FoundryChannel.renderID;
	}
}
