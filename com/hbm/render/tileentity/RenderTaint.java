package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.render.loader.RUVertice;
import com.hbm.tileentity.deco.TileEntityTaint;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;


public class RenderTaint extends TileEntitySpecialRenderer
{
	float	s	= 0.5F;
	
	RUVertice	v1	= new RUVertice(s, s, s);
	RUVertice	v2	= new RUVertice(s, s, -s);
	RUVertice	v3	= new RUVertice(-s, s, -s);
	RUVertice	v4	= new RUVertice(-s, s, s);
	
	RUVertice	v5	= new RUVertice(s, -s, s);
	RUVertice	v6	= new RUVertice(s, -s, -s);
	RUVertice	v7	= new RUVertice(-s, -s, -s);
	RUVertice	v8	= new RUVertice(-s, -s, s);
	
	public void renderAModelAt(TileEntityTaint tile, double x, double y, double z, float f)
	{
		World world = tile.getWorldObj();
		
		boolean ceil = world.isBlockNormalCubeDefault(tile.xCoord, tile.yCoord + 1, tile.zCoord, false);
		boolean floor = world.isBlockNormalCubeDefault(tile.xCoord, tile.yCoord - 1, tile.zCoord, false);
		boolean side1 = world.isBlockNormalCubeDefault(tile.xCoord, tile.yCoord, tile.zCoord + 1, false);
		boolean side2 = world.isBlockNormalCubeDefault(tile.xCoord - 1, tile.yCoord, tile.zCoord, false);
		boolean side3 = world.isBlockNormalCubeDefault(tile.xCoord, tile.yCoord, tile.zCoord - 1, false);
		boolean side4 = world.isBlockNormalCubeDefault(tile.xCoord + 1, tile.yCoord, tile.zCoord, false);
		
		int meta = tile.getBlockMetadata();
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		
		
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("hbm:textures/blocks/taint_" + meta + ".png"));
		
		GL11.glDisable(GL11.GL_LIGHTING);
		Tessellator tessellator = Tessellator.instance;
		
		if (side1)
		{
			tessellator.startDrawingQuads();
			addVertex(v1, 0, 0, true);
			addVertex(v5, 1, 0, true);
			addVertex(v8, 1, 1, true);
			addVertex(v4, 0, 1, true);
			tessellator.draw();
		}
		
		if (side2)
		{
			tessellator.startDrawingQuads();
			addVertex(v4, 0, 0, true);
			addVertex(v8, 1, 0, true);
			addVertex(v7, 1, 1, true);
			addVertex(v3, 0, 1, true);
			tessellator.draw();
		}
		
		if (side3)
		{
			tessellator.startDrawingQuads();
			addVertex(v3, 0, 0, true);
			addVertex(v7, 1, 0, true);
			addVertex(v6, 1, 1, true);
			addVertex(v2, 0, 1, true);
			tessellator.draw();
		}
		
		if (side4)
		{
			tessellator.startDrawingQuads();
			addVertex(v2, 0, 0, true);
			addVertex(v6, 1, 0, true);
			addVertex(v5, 1, 1, true);
			addVertex(v1, 0, 1, true);
			tessellator.draw();
		}
		
		if (ceil)
		{
			tessellator.startDrawingQuads();
			addVertex(v4, 0, 0, true);
			addVertex(v3, 1, 0, true);
			addVertex(v2, 1, 1, true);
			addVertex(v1, 0, 1, true);
			tessellator.draw();
		}
		
		if (floor)
		{
			tessellator.startDrawingQuads();
			addVertex(v5, 0, 0, true);
			addVertex(v6, 1, 0, true);
			addVertex(v7, 1, 1, true);
			addVertex(v8, 0, 1, true);
			tessellator.draw();
		}
		
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f)
	{
		renderAModelAt((TileEntityTaint) tileentity, d, d1, d2, f);
	}
	
	private void addVertex(RUVertice v, double t, double t2, boolean offset)
	{
		Tessellator tessellator = Tessellator.instance;
		tessellator.addVertexWithUV(v.x * 0.99, v.y * 0.99, v.z * 0.99, t, t2);
	}
}
