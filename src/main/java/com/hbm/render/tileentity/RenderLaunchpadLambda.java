package com.hbm.render.tileentity;

import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityLaunchpadLambda;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderLaunchpadLambda extends TileEntitySpecialRenderer {
	
	private static DoubleBuffer buf = null;

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		if(buf == null) buf = GLAllocation.createDirectByteBuffer(8*4).asDoubleBuffer();
		
		float rotation = 0F;

		switch(tile.getBlockMetadata() - 10) {
		case 2: rotation = 90F; break;
		case 4: rotation = 180F; break;
		case 3: rotation = 270F; break;
		case 5: rotation = 0F; break;
		}
		
		GL11.glRotatef(rotation, 0F, 1F, 0F);
		
		TileEntityLaunchpadLambda launchpad = (TileEntityLaunchpadLambda) tile;

		double doors = launchpad.getInterpPos(launchpad.INDEX_DOORS, interp);
		double erector = launchpad.getInterpPos(launchpad.INDEX_ERECTOR, interp) - 25D;
		double rotor = launchpad.getInterpPos(launchpad.INDEX_ROTOR, interp);
		double clamps = launchpad.getInterpPos(launchpad.INDEX_CLAMPS, interp);
		double pistons = launchpad.getInterpPos(launchpad.INDEX_PISTONS, interp);
		
		boolean renderRocket = launchpad.erecting || launchpad.erected;
		boolean rocketFixed = launchpad.erected;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 2, 0);
		if(rocketFixed) {
			GL11.glRotatef(-rotation, 0F, 1F, 0F);
			bindTexture(ResourceManager.lambda_rocket_tex);
			ResourceManager.lambda_rocket.renderAll();
		}
		GL11.glPopMatrix();

		bindTexture(ResourceManager.launchpad_lambda_tex);
		ResourceManager.launchpad_lambda.renderPart("Silo");

		GL11.glPushMatrix(); {
			GL11.glEnable(GL11.GL_CLIP_PLANE0);
			buf.put(new double[] { 0, 0, -1, 6} ).rewind();
			GL11.glClipPlane(GL11.GL_CLIP_PLANE0, buf);
			
			GL11.glTranslated(0, 0, doors);
			ResourceManager.launchpad_lambda.renderPart("DoorLeft");
			GL11.glDisable(GL11.GL_CLIP_PLANE0);
		} GL11.glPopMatrix();
		
		GL11.glPushMatrix(); {
			GL11.glEnable(GL11.GL_CLIP_PLANE0);
			buf.put(new double[] { 0, 0, 1, 6} ).rewind();
			GL11.glClipPlane(GL11.GL_CLIP_PLANE0, buf);
			
			GL11.glTranslated(0, 0, -doors);
			ResourceManager.launchpad_lambda.renderPart("DoorRight");
			GL11.glDisable(GL11.GL_CLIP_PLANE0);
		} GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_CLIP_PLANE0);
		buf.put(new double[] { 0, 1, 0, -0.25} ).rewind();
		GL11.glClipPlane(GL11.GL_CLIP_PLANE0, buf);
		
		GL11.glTranslated(0, erector, 0);
		
		ResourceManager.launchpad_lambda.renderPart("Erector");

		GL11.glTranslated(0, 13.25, 0);
		GL11.glRotated(rotor, 1, 0, 0);
		GL11.glTranslated(0, -13.25, 0);
		
		ResourceManager.launchpad_lambda.renderPart("Rotor");
		
		GL11.glPushMatrix(); {
			GL11.glTranslated(3.5, 19.75, 0);
			GL11.glRotated(-clamps, 0, 0, 1);
			GL11.glTranslated(-3.5, -19.75, 0);
			ResourceManager.launchpad_lambda.renderPart("PivotUpper1");
			GL11.glTranslated(pistons, 0, 0);
			ResourceManager.launchpad_lambda.renderPart("ClampUpper1");
		} GL11.glPopMatrix();

		GL11.glPushMatrix(); {
			GL11.glTranslated(3.5, 6.75, 0);
			GL11.glRotated(clamps, 0, 0, 1);
			GL11.glTranslated(-3.5, -6.75, 0);
			ResourceManager.launchpad_lambda.renderPart("PivotLower1");
			GL11.glTranslated(pistons, 0, 0);
			ResourceManager.launchpad_lambda.renderPart("ClampLower1");
		} GL11.glPopMatrix();
		
		GL11.glPushMatrix(); {
			GL11.glTranslated(-3.5, 19.75, 0);
			GL11.glRotated(clamps, 0, 0, 1);
			GL11.glTranslated(3.5, -19.75, 0);
			ResourceManager.launchpad_lambda.renderPart("PivotUpper2");
			GL11.glTranslated(-pistons, 0, 0);
			ResourceManager.launchpad_lambda.renderPart("ClampUpper2");
		} GL11.glPopMatrix();

		GL11.glPushMatrix(); {
			GL11.glTranslated(-3.5, 6.75, 0);
			GL11.glRotated(-clamps, 0, 0, 1);
			GL11.glTranslated(3.5, -6.75, 0);
			ResourceManager.launchpad_lambda.renderPart("PivotLower2");
			GL11.glTranslated(-pistons, 0, 0);
			ResourceManager.launchpad_lambda.renderPart("ClampLower2");
		} GL11.glPopMatrix();

		GL11.glTranslated(0, 2, 0);

		if(renderRocket && !rocketFixed) {
			GL11.glRotatef(-rotation, 0F, 1F, 0F);
			bindTexture(ResourceManager.lambda_rocket_tex);
			ResourceManager.lambda_rocket.renderAll();
		}
		
		GL11.glDisable(GL11.GL_CLIP_PLANE0);
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

}
