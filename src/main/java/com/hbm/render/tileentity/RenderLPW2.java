package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;

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
		
		double timer = ((te.getWorldObj().getTotalWorldTime() + interp) / 3D) % (Math.PI * 4);
		//double sway = Math.sin(timer) + Math.sin(timer * 2) + 1.76017D;
		double sway = Math.sin(timer) + Math.sin(timer * 2) + Math.sin(timer * 4) + 2.23255D;
		GL11.glTranslated(0, 0, -sway * 0.125);

		bindTexture(ResourceManager.lpw2_tex);
		ResourceManager.lpw2.renderAll();

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
	
	public static void renderMainAssembly(double sway) {
		//main assembly
		//turbine rotors
		//pump rotor
		//pump piston
		//engine bell
		//suspension
	}
	
	public static void renderBell(double h, double v) {
		//bell with rotations
	}
	
	public static void renderShroud(double h, double v) {
		//shrouds
		//flaps
		//suspension
	}
	
	public static void renderGamingRig() {
		//screen
		//absolute fucking chaos
	}
	
	public static void renderScreen() {
		//screen (term/error)
	}
}
