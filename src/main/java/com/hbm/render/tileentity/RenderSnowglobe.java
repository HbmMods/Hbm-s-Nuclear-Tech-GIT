package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.blocks.generic.BlockSnowglobe.TileEntitySnowglobe;
import com.hbm.lib.RefStrings;
import com.hbm.render.loader.HFRWavefrontObject;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

public class RenderSnowglobe extends TileEntitySpecialRenderer {
	
	public static final IModelCustom snowglobe = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/trinkets/snowglobe.obj"), false).asDisplayList();
	public static final ResourceLocation socket = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/snowglobe.png");
	public static final ResourceLocation features = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/snowglobe_features.png");
	public static RenderBlocks renderer = new RenderBlocks();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		GL11.glRotated(22.5D * tile.getBlockMetadata() + 90, 0, -1, 0);
		
		double scale = 0.0625D;
		GL11.glScaled(scale, scale, scale);
		
		this.bindTexture(socket);
		snowglobe.renderPart("Socket");

		TileEntitySnowglobe te = (TileEntitySnowglobe) tile;
		this.bindTexture(features);
		
		switch(te.type) {
		case NONE: break;
		case RIVETCITY:		snowglobe.renderPart("RivetCity"); break;
		case TENPENNYTOWER:	snowglobe.renderPart("TenpennyTower"); break;
		case LUCKY38:		snowglobe.renderPart("Lucky38_Plane"); break;
		case SIERRAMADRE:	snowglobe.renderPart("SierraMadre"); break;
		case PRYDWEN:		snowglobe.renderPart("Prydwen"); break;
		default: break;
		}
		
		GL11.glPopMatrix();
	}
}
