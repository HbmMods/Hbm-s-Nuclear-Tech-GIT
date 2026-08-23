package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.util.SoyuzPronter;
import com.hbm.tileentity.machine.TileEntityLaunchpadSoyuz;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class RenderLaunchpadSoyuz extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		switch(tile.getBlockMetadata() - 10) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(-4, 0, -4);
		
		TileEntityLaunchpadSoyuz launchpad = (TileEntityLaunchpadSoyuz) tile;
		
		float rotor = MathHelper.clamp_float(launchpad.getInterpPos(launchpad.INDEX_ROTOR, interp) * -180F + 180F, 0F, 180F);
		float carriage = MathHelper.clamp_float(launchpad.getInterpPos(launchpad.INDEX_CARRIAGE, interp) * -19.5F + 19.5F, 0F, 19.5F);
		float wheels = (float) (carriage * 360D / Math.PI);
		float tilt = launchpad.getInterpPos(launchpad.INDEX_TILT, interp) * 1;
		
		bindTexture(ResourceManager.launchpad_soyuz_tex);

		ResourceManager.launchpad_soyuz.renderPart("Launchpad");
		
		for(int i = 1; i <= 5; i++) {
			GL11.glPushMatrix();
			float ext = i == 5 ? 3F : 4.5F;
			float strut = MathHelper.clamp_float(launchpad.getInterpPos(i - 1, interp) * -ext + ext, 0F, ext);
			GL11.glTranslated(0, 0, strut);
			ResourceManager.launchpad_soyuz.renderPart("Strut" + i);
			GL11.glPopMatrix();
		}
		
		GL11.glTranslated(0, 0, -carriage);
		
		GL11.glTranslated(0, 1.5, -32);
		GL11.glRotated(-tilt, 1, 0, 0);
		GL11.glTranslated(0, -1.5, 32);
		
		ResourceManager.launchpad_soyuz.renderPart("Carriage");

		double[] wheelsForward = new double[] {17D, 19D, 29D, 31D};
		double[] wheelsSide = new double[] {6.75D, 5.25D, -5.25D, -6.75D};
		
		for(int i = 1; i <= 4; i++) for(int j = 1; j <= 4; j++) {
			GL11.glPushMatrix();
			double v0 = wheelsForward[i - 1];
			double v1 = wheelsSide[j - 1];
			
			GL11.glTranslated(v1, 0, -v0);
			GL11.glRotated(wheels * (j % 2 == 0 ? -1 : 1), 0, 1, 0);
			GL11.glTranslated(-v1, 0, v0);
			
			ResourceManager.launchpad_soyuz.renderPart("Wheel_" + i + "_" + j);
			GL11.glPopMatrix();
		}

		GL11.glTranslated(0, 24.5, -18);
		GL11.glRotated(-rotor, 1, 0, 0);
		GL11.glTranslated(0, -24.5, 18);
		
		ResourceManager.launchpad_soyuz.renderPart("Rotor");

		GL11.glTranslated(0, 24.5, -6);
		GL11.glRotated(rotor, 1, 0, 0);
		GL11.glTranslated(0, -24.5, 6);
		
		ResourceManager.launchpad_soyuz.renderPart("Mount");
		
		GL11.glTranslated(0, 4, 0);
		
		if(launchpad.loadedType >= 0)
			SoyuzPronter.prontSoyuz(launchpad.loadedType);
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
