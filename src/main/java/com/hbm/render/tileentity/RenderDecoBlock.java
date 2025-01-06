package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelBroadcaster;
import com.hbm.render.model.ModelGeiger;
import com.hbm.render.model.ModelRadio;
import com.hbm.render.model.ModelSteelRoof;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderDecoBlock extends TileEntitySpecialRenderer {

	private static final ResourceLocation texture3 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/SteelRoof.png");
	private static final ResourceLocation texture6 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelBroadcaster.png");
	private static final ResourceLocation texture7 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelRadio.png");
	private static final ResourceLocation texture8 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelRadioReceiver.png");
	private static final ResourceLocation texture9 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelGeiger.png");
	
	private ModelSteelRoof model3;
	private ModelBroadcaster model6;
	private ModelRadio model7;
	private ModelGeiger model8;
	
	public RenderDecoBlock() {
		this.model3 = new ModelSteelRoof();
		this.model6 = new ModelBroadcaster();
		this.model7 = new ModelRadio();
		this.model8 = new ModelGeiger();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180, 0F, 0F, 1F);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		
		Block b = tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
		
		if(b == ModBlocks.steel_roof) {
			this.bindTexture(texture3);
		}
		if(b == ModBlocks.broadcaster_pc) {
			this.bindTexture(texture6);
			switch(tileentity.getBlockMetadata())
			{
			case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
			}
		}
		if(b == ModBlocks.geiger) {
			this.bindTexture(texture9);
			switch(tileentity.getBlockMetadata())
			{
			case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
			}
		}
		if(b == ModBlocks.radiorec) {
			this.bindTexture(texture8);
			switch(tileentity.getBlockMetadata())
			{
			case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
			}
		}
		if(b == ModBlocks.radiobox) {
			this.bindTexture(texture7);
			switch(tileentity.getBlockMetadata())
			{
			case 4:
			case 8: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 2:
			case 6: GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 5:
			case 9: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3:
			case 7: GL11.glRotatef(180, 0F, 1F, 0F); break;
			}
			GL11.glTranslatef(0, 0, 1);
		}
		
		GL11.glPushMatrix();
			if(b == ModBlocks.steel_roof) this.model3.renderModel(0.0625F);
			if(b == ModBlocks.broadcaster_pc) this.model6.renderModel(0.0625F);
			if(b== ModBlocks.geiger) this.model8.renderModel(0.0625F);
			if(b == ModBlocks.radiobox) this.model7.renderModel(0.0625F, tileentity.getBlockMetadata() > 5 ? 160 : 20);
			if(b == ModBlocks.radiorec) this.model6.renderModel(0.0625F);
		GL11.glPopMatrix();
			
		if(b == ModBlocks.boxcar) {

			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);

			switch(tileentity.getBlockMetadata()) {
			case 4: GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
			default:
				GL11.glRotatef(180, 0F, 0F, 1F);
				GL11.glRotated(90, 1, 0, 0);
				GL11.glTranslatef(0, -1.5F, 0); break;
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.boxcar_tex);
			ResourceManager.boxcar.renderAll();
		}

		if(b == ModBlocks.boat) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, 0, -1.5F);
			GL11.glTranslatef(0, 0.5F, 0);

			GL11.glEnable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.duchessgambit_tex);
			ResourceManager.duchessgambit.renderAll();
		}

		if(b == ModBlocks.sat_radar) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);
			GL11.glRotated(90, 0, 1, 0);

			switch(tileentity.getBlockMetadata()) {
			case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.sat_base_tex);
			ResourceManager.sat_base.renderAll();
			bindTexture(ResourceManager.sat_radar_tex);
			ResourceManager.sat_radar.renderAll();
		}

		if(b == ModBlocks.sat_resonator) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);
			GL11.glRotated(90, 0, 1, 0);

			switch(tileentity.getBlockMetadata()) {
			case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.sat_base_tex);
			ResourceManager.sat_base.renderAll();
			bindTexture(ResourceManager.sat_resonator_tex);
			ResourceManager.sat_resonator.renderAll();
		}

		if(b == ModBlocks.sat_scanner) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);
			GL11.glRotated(90, 0, 1, 0);

			switch(tileentity.getBlockMetadata()) {
			case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.sat_base_tex);
			ResourceManager.sat_base.renderAll();
			bindTexture(ResourceManager.sat_scanner_tex);
			ResourceManager.sat_scanner.renderAll();
		}

		if(b == ModBlocks.sat_mapper) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);
			GL11.glRotated(90, 0, 1, 0);

			switch(tileentity.getBlockMetadata()) {
			case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.sat_base_tex);
			ResourceManager.sat_base.renderAll();
			bindTexture(ResourceManager.sat_mapper_tex);
			ResourceManager.sat_mapper.renderAll();
		}

		if(b == ModBlocks.sat_laser) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);
			GL11.glRotated(90, 0, 1, 0);

			switch(tileentity.getBlockMetadata()) {
			case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.sat_base_tex);
			ResourceManager.sat_base.renderAll();
			bindTexture(ResourceManager.sat_laser_tex);
			ResourceManager.sat_laser.renderAll();
		}

		if(b == ModBlocks.sat_foeq) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);
			GL11.glRotated(90, 0, 1, 0);

			switch(tileentity.getBlockMetadata()) {
			case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.sat_foeq_tex);
			ResourceManager.sat_foeq.renderAll();
		}

		if(b == ModBlocks.sat_dock) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);

			bindTexture(ResourceManager.satdock_tex);
			ResourceManager.satDock.renderAll();
		}

		GL11.glPopMatrix();
	}

}
