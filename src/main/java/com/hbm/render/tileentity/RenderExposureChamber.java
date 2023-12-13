package com.hbm.render.tileentity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.machine.TileEntityMachineExposureChamber;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

public class RenderExposureChamber extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float interp) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		
		TileEntityMachineExposureChamber chamber = (TileEntityMachineExposureChamber) tileEntity;
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.exposure_chamber_tex);
		ResourceManager.exposure_chamber.renderPart("Chamber");
		
		double rotation = chamber.prevRotation + (chamber.rotation - chamber.prevRotation) * interp;
		
		GL11.glPushMatrix();
		GL11.glRotated(rotation, 0, 1, 0);
		ResourceManager.exposure_chamber.renderPart("Magnets");
		GL11.glPopMatrix();
		
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glDisable(GL11.GL_LIGHTING);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		
		if(chamber.isOn) {
			GL11.glPushMatrix();
			GL11.glRotated(rotation / 2D, 0, 1, 0);
			GL11.glTranslated(0, Math.sin((tileEntity.getWorldObj().getTotalWorldTime() % (Math.PI * 16D) + interp) * 0.125) * 0.0625, 0);
			ResourceManager.exposure_chamber.renderPart("Core");
			GL11.glPopMatrix();
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		if(chamber.isOn) {
			
			int duration = 8;
			Random rand = new Random(chamber.getWorldObj().getTotalWorldTime() / duration);
			int chance = 2;
			int color = chamber.getWorldObj().getTotalWorldTime() % duration >= duration / 2 ? 0x80d0ff : 0xffffff;
			rand.nextInt(chance); //RNG behaves weirldy in the first iteration
			if(rand.nextInt(chance) == 0) {
				GL11.glPushMatrix();
				GL11.glTranslated(0, 3.675, -7.5);
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, 5), EnumWaveType.RANDOM, EnumBeamType.LINE, color, 0xffffff, (int)(System.currentTimeMillis() % 1000) / 50, 15, 0.125F, 1, 0);
				GL11.glPopMatrix();
			}
			if(rand.nextInt(chance) == 0) {
				GL11.glPushMatrix();
				GL11.glTranslated(1.1875, 2.5, -7.5);
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, 5), EnumWaveType.RANDOM, EnumBeamType.LINE, color, 0xffffff, (int)(System.currentTimeMillis() % 1000) / 50, 15, 0.125F, 1, 0);
				GL11.glPopMatrix();
			}
			if(rand.nextInt(chance) == 0) {
				GL11.glPushMatrix();
				GL11.glTranslated(-1.1875, 2.5, -7.5);
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, 5), EnumWaveType.RANDOM, EnumBeamType.LINE, color, 0xffffff, (int)(System.currentTimeMillis() % 1000) / 50, 15, 0.125F, 1, 0);
				GL11.glPopMatrix();
			}
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 1.75, 0);
			BeamPronter.prontBeam(Vec3.createVectorHelper(0, 1.5, 0), EnumWaveType.RANDOM, EnumBeamType.LINE, 0x80d0ff, 0xffffff, (int)(System.currentTimeMillis() % 1000) / 50, 10, 0.125F, 1, 0);
			BeamPronter.prontBeam(Vec3.createVectorHelper(0, 1.5, 0), EnumWaveType.RANDOM, EnumBeamType.LINE, 0x8080ff, 0xffffff, (int)(System.currentTimeMillis() + 5 % 1000) / 50, 10, 0.125F, 1, 0);
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 2.5, 0);
			BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -1), EnumWaveType.SPIRAL, EnumBeamType.LINE, 0xffff80, 0xffffff, (int)(System.currentTimeMillis() % 360), 15, 0.125F, 1, 0);
			BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -1), EnumWaveType.SPIRAL, EnumBeamType.LINE, 0xff8080, 0xffffff, (int)(System.currentTimeMillis() % 360) + 180, 15, 0.125F, 1, 0);
			GL11.glPopMatrix();
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();

		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_exposure_chamber);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -1.5, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				GL11.glTranslated(1.5, 0, 0);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.exposure_chamber_tex);
				ResourceManager.exposure_chamber.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}};
	}
}
