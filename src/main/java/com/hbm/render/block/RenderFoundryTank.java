package com.hbm.render.block;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.FoundryTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.TileEntityFoundryTank;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class RenderFoundryTank implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		FoundryTank basin = (FoundryTank) block;
		double x = 0;
		double y = 0;
		double z = 0;
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, basin.iconTop);
		renderer.renderFaceYPos(block, x, y - 0.875D, z, basin.iconBottom);
		tessellator.setNormal(0F, -1F, 0F);
		renderer.renderFaceYNeg(block, x, y, z, basin.iconBottom);
		tessellator.setNormal(1F, 0F, 0F);
		renderer.renderFaceXPos(block, x, y, z, basin.iconSide);
		renderer.renderFaceXPos(block, x - 0.875D, y, z, basin.iconInner);
		tessellator.setNormal(-1F, 0F, 0F);
		renderer.renderFaceXNeg(block, x, y, z, basin.iconSide);
		renderer.renderFaceXNeg(block, x + 0.875D, y, z, basin.iconInner);
		tessellator.setNormal(0F, 0F, 1F);
		renderer.renderFaceZPos(block, x, y, z, basin.iconSide);
		renderer.renderFaceZPos(block, x, y, z - 0.875D, basin.iconInner);
		tessellator.setNormal(0F, 0F, -1F);
		renderer.renderFaceZNeg(block, x, y, z, basin.iconSide);
		renderer.renderFaceZNeg(block, x, y, z + 0.875D, basin.iconInner);
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
		
		FoundryTank tank = (FoundryTank) block;
		
		TileEntity te = world.getTileEntity(x, y, z);
		TileEntityFoundryTank tile = null;
		
		if(te instanceof TileEntityFoundryTank) {
			tile = (TileEntityFoundryTank) te;
		}

		boolean conPosX = world.getBlock(x + 1, y, z) == ModBlocks.foundry_tank;
		boolean conNegX = world.getBlock(x - 1, y, z) == ModBlocks.foundry_tank;
		boolean conPosZ = world.getBlock(x, y, z + 1) == ModBlocks.foundry_tank;
		boolean conNegZ = world.getBlock(x, y, z - 1) == ModBlocks.foundry_tank;
		boolean conPosY = world.getBlock(x, y + 1, z) == ModBlocks.foundry_tank;
		boolean conNegY = world.getBlock(x, y - 1, z) == ModBlocks.foundry_tank;

		boolean outPosX = world.getBlock(x + 1, y, z) == ModBlocks.foundry_outlet && world.getBlockMetadata(x + 1, y, z) == Library.POS_X.ordinal();
		boolean outNegX = world.getBlock(x - 1, y, z) == ModBlocks.foundry_outlet && world.getBlockMetadata(x - 1, y, z) == Library.NEG_X.ordinal();
		boolean outPosZ = world.getBlock(x, y, z + 1) == ModBlocks.foundry_outlet && world.getBlockMetadata(x, y, z + 1) == Library.POS_Z.ordinal();
		boolean outNegZ = world.getBlock(x, y, z - 1) == ModBlocks.foundry_outlet && world.getBlockMetadata(x, y, z - 1) == Library.NEG_Z.ordinal();
		
		boolean doRender = tile != null ? (tile.amount > 0 && tile.type != null) : false;
		double max = 0.75D + (conNegY ? 0.125D : 0) + (conPosY ? 0.125D : 0);
		double level = doRender ? tile.amount * max / tile.getCapacity() : 0;
		Color color = doRender ? new Color(tile.type.moltenColor).brighter() : null;
		
		if(color != null) {
			double brightener = 0.7D;
			int nr = (int) (255D - (255D - color.getRed()) * brightener);
			int ng = (int) (255D - (255D - color.getGreen()) * brightener);
			int nb = (int) (255D - (255D - color.getBlue()) * brightener);
			
			color = new Color(nr, ng, nb);
		}
		
		renderer.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);
		
		if(!conNegY) {
			renderer.setRenderBounds(0D, 0D, 0D, 1D, 0.125D, 1D);
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, tank.iconBottom);
			tessellator.setColorOpaque_F(r * mulBottom, g * mulBottom, b * mulBottom);
			renderer.renderFaceYNeg(block, x, y, z, tank.iconBottom);
		}
		
		if(!conPosX) {
			renderer.setRenderBounds(0.875D, 0D, 0D, 1D, 1D, 1D);
			tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
			renderer.renderFaceXPos(block, x, y, z, conNegY ? (outPosX ? tank.iconSideUpperOutlet : tank.iconSideUpper) : (outPosX ? tank.iconSideOutlet : tank.iconSide));
			renderer.renderFaceXNeg(block, x, y, z, conPosY ? tank.iconBottom : tank.iconInner);
			
			tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
			if(conPosZ) renderer.renderFaceZPos(block, x, y, z, conPosY ? tank.iconBottom : tank.iconInner);
			if(conNegZ) renderer.renderFaceZNeg(block, x, y, z, conPosY ? tank.iconBottom : tank.iconInner);
			
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, tank.iconTop);
		}
		
		if(!conNegX) {
			renderer.setRenderBounds(0D, 0D, 0D, 0.125D, 1D, 1D);
			tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
			renderer.renderFaceXPos(block, x, y, z, conPosY ? tank.iconBottom : tank.iconInner);
			renderer.renderFaceXNeg(block, x, y, z, conNegY ? (outNegX ? tank.iconSideUpperOutlet : tank.iconSideUpper) : (outNegX ? tank.iconSideOutlet : tank.iconSide));
			
			tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
			if(conPosZ) renderer.renderFaceZPos(block, x, y, z, conPosY ? tank.iconBottom : tank.iconInner);
			if(conNegZ) renderer.renderFaceZNeg(block, x, y, z, conPosY ? tank.iconBottom : tank.iconInner);
			
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, tank.iconTop);
		}
		
		if(!conPosZ) {
			renderer.setRenderBounds(0D, 0D, 0.875D, 1D, 1D, 1D);
			tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
			renderer.renderFaceZPos(block, x, y, z, conNegY ? (outPosZ ? tank.iconSideUpperOutlet : tank.iconSideUpper) : (outPosZ ? tank.iconSideOutlet : tank.iconSide));
			renderer.renderFaceZNeg(block, x, y, z, conPosY ? tank.iconBottom : tank.iconInner);
			
			tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
			if(conPosX) renderer.renderFaceXPos(block, x, y, z, conPosY ? tank.iconBottom : tank.iconInner);
			if(conNegX) renderer.renderFaceXNeg(block, x, y, z, conPosY ? tank.iconBottom : tank.iconInner);
			
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, tank.iconTop);
		}
		
		if(!conNegZ) {
			renderer.setRenderBounds(0D, 0D, 0D, 1D, 1D, 0.125D);
			tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
			renderer.renderFaceZPos(block, x, y, z, conPosY ? tank.iconBottom : tank.iconInner);
			renderer.renderFaceZNeg(block, x, y, z, conNegY ? (outNegZ ? tank.iconSideUpperOutlet : tank.iconSideUpper) : (outNegZ ? tank.iconSideOutlet : tank.iconSide));
			
			tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
			if(conPosX) renderer.renderFaceXPos(block, x, y, z, conPosY ? tank.iconBottom : tank.iconInner);
			if(conNegX) renderer.renderFaceXNeg(block, x, y, z, conPosY ? tank.iconBottom : tank.iconInner);
			
			tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
			renderer.renderFaceYPos(block, x, y, z, tank.iconTop);
		}
		
		if(doRender) {
			double height = conNegY ? 0D : 0.125D;
			renderer.setRenderBounds(0D, height, 0D, 1D, height + level, 1D);
			tessellator.setColorOpaque_F(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
			tessellator.setBrightness(fullBright);
			renderer.renderFaceYPos(block, x, y, z, tank.iconLava);
			if(conPosX) renderer.renderFaceXPos(block, x, y, z, tank.iconLava);
			if(conNegX) renderer.renderFaceXNeg(block, x, y, z, tank.iconLava);
			if(conPosZ) renderer.renderFaceZPos(block, x, y, z, tank.iconLava);
			if(conNegZ) renderer.renderFaceZNeg(block, x, y, z, tank.iconLava);
			tessellator.setBrightness(brightness);
		}
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return FoundryTank.renderID;
	}
}
