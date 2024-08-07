package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelBroadcaster;
import com.hbm.render.model.ModelGeiger;
import com.hbm.render.model.ModelRadio;
import com.hbm.render.model.ModelSteelBeam;
import com.hbm.render.model.ModelSteelCorner;
import com.hbm.render.model.ModelSteelRoof;
import com.hbm.render.model.ModelSteelScaffold;
import com.hbm.render.model.ModelSteelWall;
import com.hbm.tileentity.machine.TileEntityMachineGasDock;
import com.hbm.tileentity.machine.TileEntityTransporterRocket;
import com.hbm.util.CompatExternal;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderDecoBlock extends TileEntitySpecialRenderer {

	private static final ResourceLocation texture1 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/SteelWall.png");
	private static final ResourceLocation texture2 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/SteelCorner.png");
	private static final ResourceLocation texture3 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/SteelRoof.png");
	private static final ResourceLocation texture4 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/SteelBeam.png");
	private static final ResourceLocation texture5 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/SteelScaffold.png");
	private static final ResourceLocation texture6 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelBroadcaster.png");
	private static final ResourceLocation texture7 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelRadio.png");
	private static final ResourceLocation texture8 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelRadioReceiver.png");
	private static final ResourceLocation texture9 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelGeiger.png");
	
	private ModelSteelWall model1;
	private ModelSteelCorner model2;
	private ModelSteelRoof model3;
	private ModelSteelBeam model4;
	private ModelSteelScaffold model5;
	private ModelBroadcaster model6;
	private ModelRadio model7;
	private ModelGeiger model8;
	
	public RenderDecoBlock() {
		this.model1 = new ModelSteelWall();
		this.model2 = new ModelSteelCorner();
		this.model3 = new ModelSteelRoof();
		this.model4 = new ModelSteelBeam();
		this.model5 = new ModelSteelScaffold();
		this.model6 = new ModelBroadcaster();
		this.model7 = new ModelRadio();
		this.model8 = new ModelGeiger();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		{

			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glRotatef(180, 0F, 0F, 1F);
			
			GL11.glEnable(GL11.GL_LIGHTING);

			Block block = tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
			
			if(block == ModBlocks.steel_wall) {
				this.bindTexture(texture1);
				switch(tileentity.getBlockMetadata()) {
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
			if(block == ModBlocks.steel_corner) {
				this.bindTexture(texture2);
				switch(tileentity.getBlockMetadata()) {
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
			if(block == ModBlocks.steel_roof) {
				this.bindTexture(texture3);
			}
			if(block == ModBlocks.steel_beam) {
				this.bindTexture(texture4);
			}
			if(block == ModBlocks.steel_scaffold) {
				this.bindTexture(texture5);
				switch(tileentity.getBlockMetadata()) {
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
			if(block == ModBlocks.broadcaster_pc) {
				this.bindTexture(texture6);
				switch(tileentity.getBlockMetadata()) {
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
			if(block == ModBlocks.geiger) {
				this.bindTexture(texture9);
				switch(tileentity.getBlockMetadata()) {
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
			if(block == ModBlocks.radiorec) {
				this.bindTexture(texture8);
				switch(tileentity.getBlockMetadata()) {
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
			if(block == ModBlocks.radiobox) {
				this.bindTexture(texture7);
				switch(tileentity.getBlockMetadata()) {
				case 4:
				case 8:
					GL11.glRotatef(270, 0F, 1F, 0F); break;
				case 2:
				case 6:
					GL11.glRotatef(0, 0F, 1F, 0F); break;
				case 5:
				case 9:
					GL11.glRotatef(90, 0F, 1F, 0F); break;
				case 3:
				case 7:
					GL11.glRotatef(180, 0F, 1F, 0F); break;
				}
				GL11.glTranslatef(0, 0, 1);
			}
			
			GL11.glPushMatrix();
				if(block == ModBlocks.steel_wall)
					this.model1.renderModel(0.0625F);
				if(block == ModBlocks.steel_corner)
					this.model2.renderModel(0.0625F);
				if(block == ModBlocks.steel_roof)
					this.model3.renderModel(0.0625F);
				if(block == ModBlocks.steel_beam)
					this.model4.renderModel(0.0625F);
				if(block == ModBlocks.steel_scaffold)
					this.model5.renderModel(0.0625F);
				if(block == ModBlocks.broadcaster_pc)
					this.model6.renderModel(0.0625F);
				if(block == ModBlocks.geiger)
					this.model8.renderModel(0.0625F);
				if(block == ModBlocks.radiobox)
					this.model7.renderModel(0.0625F, tileentity.getBlockMetadata() > 5 ? 160 : 20);
				if(block == ModBlocks.radiorec)
					this.model6.renderModel(0.0625F);
			GL11.glPopMatrix();
			
			if(block == ModBlocks.boxcar) {

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
					GL11.glTranslatef(0, -1.5F, 0);
					break;
				}

	            GL11.glEnable(GL11.GL_CULL_FACE);
	        	bindTexture(ResourceManager.boxcar_tex);
	        	ResourceManager.boxcar.renderAll();
			}
			
			if(block == ModBlocks.boat) {
				GL11.glRotatef(180, 0F, 0F, 1F);
				GL11.glTranslatef(0, 0, -1.5F);
				GL11.glTranslatef(0, 0.5F, 0);

	            GL11.glEnable(GL11.GL_CULL_FACE);
	        	bindTexture(ResourceManager.duchessgambit_tex);
	        	ResourceManager.duchessgambit.renderAll();
			}
			
			if(block == ModBlocks.sat_radar) {
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
			
			if(block == ModBlocks.sat_resonator) {
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
			
			if(block == ModBlocks.sat_scanner) {
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
			
			if(block == ModBlocks.sat_mapper) {
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
			
			if(block == ModBlocks.sat_laser) {
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
			
			if(block == ModBlocks.sat_foeq) {
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
			
			if(block == ModBlocks.sat_dock || block == ModBlocks.transporter_rocket || block == ModBlocks.gas_dock) {
				GL11.glRotatef(180, 0F, 0F, 1F);
				GL11.glTranslatef(0, -1.5F, 0);
				
	        	bindTexture(ResourceManager.satdock_tex);
	        	ResourceManager.satDock.renderAll();

				if(block == ModBlocks.transporter_rocket) {
					if(tileentity instanceof TileEntityTransporterRocket && ((TileEntityTransporterRocket)tileentity).launchTicks < 100) {
						TileEntityTransporterRocket transporter = (TileEntityTransporterRocket) tileentity;
						GL11.glPushMatrix();
						{
							
							GL11.glTranslatef(0.0F, 0.75F + MathHelper.clamp_float(transporter.launchTicks + (transporter.hasRocket ? -f : f), 0, 200), 0.0F);
							GL11.glDisable(GL11.GL_CULL_FACE);
	
							bindTexture(ResourceManager.minerRocket_tex);
	
							ResourceManager.minerRocket.renderAll();
	
							GL11.glEnable(GL11.GL_CULL_FACE);
	
						}
						GL11.glPopMatrix();
						
					
					}
				}
				if(block == ModBlocks.gas_dock) {
					if(tileentity instanceof TileEntityMachineGasDock && ((TileEntityMachineGasDock)tileentity).launchTicks < 100) {
						TileEntityMachineGasDock transporter = (TileEntityMachineGasDock) tileentity;
						GL11.glPushMatrix();
						{
							
							GL11.glTranslatef(0.0F, 0.75F + MathHelper.clamp_float(transporter.launchTicks + (transporter.hasRocket ? -f : f), 0, 200), 0.0F);
							GL11.glDisable(GL11.GL_CULL_FACE);
	
							bindTexture(ResourceManager.minerRocket_tex);
	
							ResourceManager.minerRocket.renderAll();
	
							GL11.glEnable(GL11.GL_CULL_FACE);
	
						}
						GL11.glPopMatrix();
					}
				}
			}

		}
		GL11.glPopMatrix();
	}

}
