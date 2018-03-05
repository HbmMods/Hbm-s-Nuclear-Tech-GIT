package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelBroadcaster;
import com.hbm.render.model.ModelSteelBeam;
import com.hbm.render.model.ModelSteelCorner;
import com.hbm.render.model.ModelSteelRoof;
import com.hbm.render.model.ModelSteelScaffold;
import com.hbm.render.model.ModelSteelWall;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderDecoBlock extends TileEntitySpecialRenderer {

	private static final ResourceLocation texture1 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/SteelWall.png");
	private static final ResourceLocation texture2 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/SteelCorner.png");
	private static final ResourceLocation texture3 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/SteelRoof.png");
	private static final ResourceLocation texture4 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/SteelBeam.png");
	private static final ResourceLocation texture5 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/SteelScaffold.png");
	private static final ResourceLocation texture6 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelBroadcaster.png");
	
	private ModelSteelWall model1;
	private ModelSteelCorner model2;
	private ModelSteelRoof model3;
	private ModelSteelBeam model4;
	private ModelSteelScaffold model5;
	private ModelBroadcaster model6;
	
	public RenderDecoBlock() {
		this.model1 = new ModelSteelWall();
		this.model2 = new ModelSteelCorner();
		this.model3 = new ModelSteelRoof();
		this.model4 = new ModelSteelBeam();
		this.model5 = new ModelSteelScaffold();
		this.model6 = new ModelBroadcaster();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glRotatef(180, 0F, 0F, 1F);
			
			if(tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == ModBlocks.steel_wall)
			{
				this.bindTexture(texture1);
				switch(tileentity.getBlockMetadata())
				{
				case 4:
					GL11.glRotatef(90, 0F, 1F, 0F); break;
				case 2:
					GL11.glRotatef(180, 0F, 1F, 0F); break;
				case 5:
					GL11.glRotatef(270, 0F, 1F, 0F); break;
				case 3:
					GL11.glRotatef(0, 0F, 1F, 0F); break;
				}
			}
			if(tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == ModBlocks.steel_corner)
			{
				this.bindTexture(texture2);
				switch(tileentity.getBlockMetadata())
				{
				case 4:
					GL11.glRotatef(90, 0F, 1F, 0F); break;
				case 2:
					GL11.glRotatef(180, 0F, 1F, 0F); break;
				case 5:
					GL11.glRotatef(270, 0F, 1F, 0F); break;
				case 3:
					GL11.glRotatef(0, 0F, 1F, 0F); break;
				}
			}
			if(tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == ModBlocks.steel_roof)
			{
				this.bindTexture(texture3);
			}
			if(tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == ModBlocks.steel_beam)
			{
				this.bindTexture(texture4);
			}
			if(tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == ModBlocks.steel_scaffold)
			{
				this.bindTexture(texture5);
				switch(tileentity.getBlockMetadata())
				{
				case 4:
					GL11.glRotatef(90, 0F, 1F, 0F); break;
				case 2:
					GL11.glRotatef(0, 0F, 1F, 0F); break;
				case 5:
					GL11.glRotatef(90, 0F, 1F, 0F); break;
				case 3:
					GL11.glRotatef(0, 0F, 1F, 0F); break;
				}
			}
			if(tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == ModBlocks.broadcaster_pc)
			{
				this.bindTexture(texture6);
				switch(tileentity.getBlockMetadata())
				{
				case 4:
					GL11.glRotatef(90, 0F, 1F, 0F); break;
				case 2:
					GL11.glRotatef(180, 0F, 1F, 0F); break;
				case 5:
					GL11.glRotatef(270, 0F, 1F, 0F); break;
				case 3:
					GL11.glRotatef(0, 0F, 1F, 0F); break;
				}
			}
			
			GL11.glPushMatrix();
				if(tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == ModBlocks.steel_wall)
					this.model1.renderModel(0.0625F);
				if(tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == ModBlocks.steel_corner)
					this.model2.renderModel(0.0625F);
				if(tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == ModBlocks.steel_roof)
					this.model3.renderModel(0.0625F);
				if(tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == ModBlocks.steel_beam)
					this.model4.renderModel(0.0625F);
				if(tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == ModBlocks.steel_scaffold)
					this.model5.renderModel(0.0625F);
				if(tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == ModBlocks.broadcaster_pc)
					this.model6.renderModel(0.0625F);
			GL11.glPopMatrix();
			
			if(tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == ModBlocks.boxcar) {
				GL11.glTranslatef(0, 0, -1.5F);
	        	GL11.glRotated(90, 1, 0, 0);

	            GL11.glDisable(GL11.GL_CULL_FACE);
	        	bindTexture(ResourceManager.boxcar_tex);
	        	ResourceManager.boxcar.renderAll();
	            GL11.glEnable(GL11.GL_CULL_FACE);
			}
			
		GL11.glPopMatrix();
	}

}
