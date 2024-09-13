package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderLPW2 extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		switch(te.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		long time = te.getWorldObj().getTotalWorldTime();
		
		double swayTimer = ((time + interp) / 3D) % (Math.PI * 4);
		double sway = (Math.sin(swayTimer) + Math.sin(swayTimer * 2) + Math.sin(swayTimer * 4) + 2.23255D) * 0.5;

		double bellTimer = ((time + interp) / 5D) % (Math.PI * 4);
		double h = (Math.sin(bellTimer + Math.PI) + Math.sin(bellTimer * 1.5D)) / 1.90596D;
		double v = (Math.sin(bellTimer) + Math.sin(bellTimer * 1.5D)) / 1.90596D;

		double pistonTimer = ((time + interp) / 5D) % (Math.PI * 2);
		double piston = BobMathUtil.sps(pistonTimer);
		double rotorTimer = ((time + interp) / 5D) % (Math.PI * 16);
		double rotor = (BobMathUtil.sps(rotorTimer) + rotorTimer / 2D - 1) / 25.1327412287D;
		double turbine = ((time + interp) % 100) / 100D;

		bindTexture(ResourceManager.lpw2_tex);
		ResourceManager.lpw2.renderPart("Frame");
		
		renderMainAssembly(sway, h, v, piston, rotor, turbine);
		
		GL11.glPushMatrix();
		GL11.glTranslated(-2.9375, 0, 2.375);
		GL11.glRotated(sway * 10, 0, 1, 0);
		GL11.glTranslated(2.9375, 0, -2.375);
		ResourceManager.lpw2.renderPart("WireLeft");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(2.9375, 0, 2.375);
		GL11.glRotated(sway * -10, 0, 1, 0);
		GL11.glTranslated(-2.9375, 0, -2.375);
		ResourceManager.lpw2.renderPart("WireRight");
		GL11.glPopMatrix();

		double coverTimer = ((time + interp) / 5D) % (Math.PI * 4);
		double cover = (Math.sin(coverTimer) + Math.sin(coverTimer * 2) + Math.sin(coverTimer * 4)) * 0.5;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, -cover * 0.125);
		ResourceManager.lpw2.renderPart("Cover");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 3.5);
		GL11.glScaled(1, 1, (3 + cover * 0.125) / 3);
		GL11.glTranslated(0, 0, -3.5);
		ResourceManager.lpw2.renderPart("SuspensionCoverFront");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, -5.5);
		GL11.glScaled(1, 1, (1.5 - cover * 0.125) / 1.5);
		GL11.glTranslated(0, 0, 5.5);
		ResourceManager.lpw2.renderPart("SuspensionCoverBack");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, -9);
		GL11.glScaled(1, 1, (1.25 - sway * 0.125) / 1.25);
		GL11.glTranslated(0, 0, 9);
		ResourceManager.lpw2.renderPart("SuspensionBackOuter");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, -9.5);
		GL11.glScaled(1, 1, (1.75 - sway * 0.125) / 1.75);
		GL11.glTranslated(0, 0, 9.5);
		ResourceManager.lpw2.renderPart("SuspensionBackCenter");
		GL11.glPopMatrix();

		double serverTimer = ((time + interp) / 2D) % (Math.PI * 4);
		double sx = (Math.sin(serverTimer + Math.PI) + Math.sin(serverTimer * 1.5D)) / 1.90596D;
		double sy = (Math.sin(serverTimer) + Math.sin(serverTimer * 1.5D)) / 1.90596D;

		double serverSway = 0.0625D * 0.25D;
		
		GL11.glPushMatrix();
		GL11.glTranslated(sx * serverSway, 0, sy * serverSway);
		ResourceManager.lpw2.renderPart("Server1");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(-sy * serverSway, 0, sx * serverSway);
		ResourceManager.lpw2.renderPart("Server2");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(sy * serverSway, 0, -sx * serverSway);
		ResourceManager.lpw2.renderPart("Server3");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(-sx * serverSway, 0, -sy * serverSway);
		ResourceManager.lpw2.renderPart("Server4");
		GL11.glPopMatrix();

		double errorTimer = ((time + interp) / 3D);
		
		GL11.glPushMatrix();
		GL11.glTranslated(sy * serverSway, 0, sx * serverSway);
		
		ResourceManager.lpw2.renderPart("Monitor");

		/*Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.lpw2_term_tex);
		ResourceManager.lpw2.renderPart("Screen");*/
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.lpw2_error_tex);
		
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		GL11.glTranslated(0, (BobMathUtil.sps(errorTimer) + errorTimer / 2D) % 1, 0);
		ResourceManager.lpw2.renderPart("Screen");
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glPopMatrix();

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
	
	public static void renderMainAssembly(double sway, double h, double v, double piston, double rotor, double turbine) {
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, -sway * 0.125);
		ResourceManager.lpw2.renderPart("Center");

		GL11.glPushMatrix();
		GL11.glTranslated(0, 3.5, 0);
		
		GL11.glPushMatrix();
		GL11.glRotated(rotor * 360, 0, 0, -1);
		GL11.glTranslated(0, -3.5, 0);
		ResourceManager.lpw2.renderPart("Rotor");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glRotated(turbine * 360, 0, 0, 1);
		GL11.glTranslated(0, -3.5, 0);
		ResourceManager.lpw2.renderPart("TurbineFront");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glRotated(turbine * 360, 0, 0, -1);
		GL11.glTranslated(0, -3.5, 0);
		ResourceManager.lpw2.renderPart("TurbineBack");
		GL11.glPopMatrix();

		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, piston * 0.375D + 0.375D);
		ResourceManager.lpw2.renderPart("Piston");
		GL11.glPopMatrix();
		
		renderBell(h, v);
		GL11.glPopMatrix();
		
		renderShroud(h, v);
	}
	
	public static void renderBell(double h, double v) {
		GL11.glPushMatrix();
		GL11.glTranslated(0, 3.5, 2.75);
		double magnitude = 2D;
		GL11.glRotated(v * magnitude, 0, 1, 0);
		GL11.glRotated(h * magnitude, 1, 0, 0);
		GL11.glTranslated(0, -3.5, -2.75);
		ResourceManager.lpw2.renderPart("Engine");
		GL11.glPopMatrix();
	}
	
	public static void renderShroud(double h, double v) {

		double magnitude = 0.125D;
		double rotation = 5D;
		double offset = 10D;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, -h * magnitude, 0);
		ResourceManager.lpw2.renderPart("ShroudH");

		renderFlap(90 + 22.5D, rotation * v + offset);
		renderFlap(90 - 22.5D, rotation * v + offset);
		renderFlap(270 + 22.5D, rotation * -v + offset);
		renderFlap(270 - 22.5D, rotation * -v + offset);
		
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(v * magnitude, 0, 0);
		ResourceManager.lpw2.renderPart("ShroudV");

		renderFlap(22.5D, rotation * h + offset);
		renderFlap(-22.5D, rotation * h + offset);
		renderFlap(180 + 22.5D, rotation * -h + offset);
		renderFlap(180 - 22.5D, rotation * -h + offset);
		
		GL11.glPopMatrix();
		
		double length = 0.6875D;
		
		GL11.glPushMatrix();
		GL11.glTranslated(-2.625D, 0, 0);
		GL11.glScaled((length + v * magnitude) / length, 1, 1);
		GL11.glTranslated(2.625D, 0, 0);
		ResourceManager.lpw2.renderPart("SuspensionLeft");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(2.625D, 0, 0);
		GL11.glScaled((length - v * magnitude) / length, 1, 1);
		GL11.glTranslated(-2.625D, 0, 0);
		ResourceManager.lpw2.renderPart("SuspensionRight");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 6.125D, 0);
		GL11.glScaled(1, (length + h * magnitude) / length, 1);
		GL11.glTranslated(0, -6.125D, 0);
		ResourceManager.lpw2.renderPart("SuspensionTop");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.875D, 0);
		GL11.glScaled(1, (length - h * magnitude) / length, 1);
		GL11.glTranslated(0, -0.875D, 0);
		ResourceManager.lpw2.renderPart("SuspensionBottom");
		GL11.glPopMatrix();
	}
	
	public static void renderFlap(double position, double rotation) {
		GL11.glPushMatrix();
		
		GL11.glTranslated(0, 3.5D, 0);
		GL11.glRotated(position, 0, 0, 1);
		GL11.glTranslated(0, -3.5D, 0);
		
		GL11.glTranslated(0, 6.96875D, 8.5D);
		GL11.glRotated(rotation, 1, 0, 0);
		GL11.glTranslated(0, -6.96875D, -8.5D);
		
		ResourceManager.lpw2.renderPart("Flap");
		GL11.glPopMatrix();
	}
}
