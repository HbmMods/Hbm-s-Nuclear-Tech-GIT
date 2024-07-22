package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockVolcanoV2.TileEntityLightningVolcano;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderVol2 extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glRotatef(90, 0F, 1F, 0F);

		switch(tileEntity.getBlockMetadata()) {
		case 0:
			GL11.glTranslated(0.0D, 0.5D, -0.5D);
			GL11.glRotatef(90, 1F, 0F, 0F); break;
		case 1:
			GL11.glTranslated(0.0D, 0.5D, 0.5D);
			GL11.glRotatef(90, -1F, 0F, 0F); break;
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(0, 0.5, 0.5);
		TileEntityLightningVolcano emitter = (TileEntityLightningVolcano) tileEntity;
		float scale = (emitter.flashd) * 1F;
		float alphad = 1.0F - Math.min(1.0F, scale / 110);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glColor4f(1, 1, 1, alphad);
		if(emitter.chargetime == 0) {
			BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -100 - scale), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x101020, 0x101020, 0, 8, 0F, 6, (float)3 * alphad, alphad * 2F);
			BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -100 - scale), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x202060, 0x202060, (int)(emitter.getWorldObj().getTotalWorldTime() / 2) % 1000, 26, (float)3, 2, 0.0625F * (float)2, 0.5F *alphad );
			BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -100 - scale), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x202060, 0x202060, (int)(emitter.getWorldObj().getTotalWorldTime() / 4) % 1000, 26, (float)3, 2, 0.0625F * (float)2, 0.5F*alphad );
		}

		GL11.glDisable(GL11.GL_BLEND);

		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glPopMatrix();
	}
}
