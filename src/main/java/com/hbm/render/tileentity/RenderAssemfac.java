package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineAssemfac;
import com.hbm.tileentity.machine.TileEntityMachineAssemfac.AssemblerArm;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderAssemfac extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		TileEntityMachineAssemfac fac = (TileEntityMachineAssemfac) tileEntity;
		
		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 5: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(0.5D, 0.0D, -0.5D);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.assemfac_tex);
		ResourceManager.assemfac.renderPart("Factory");

		//double rot = Math.sin((double)(System.currentTimeMillis() / 500D)) * 25 - 20;
		//double rot2 = Math.sin((double)(System.currentTimeMillis() / 400D)) * 10;
		double hOff;
		double sOff;
		
		for(int i = 0; i < fac.arms.length; i++) {
			
			AssemblerArm arm = fac.arms[i];
			double pivotRot = arm.prevAngles[0] + (arm.angles[0] - arm.prevAngles[0]) * interp;
			double armRot = arm.prevAngles[1] + (arm.angles[1] - arm.prevAngles[1]) * interp;
			double pistonRot = arm.prevAngles[2] + (arm.angles[2] - arm.prevAngles[2]) * interp;
			double striker = arm.prevAngles[3] + (arm.angles[3] - arm.prevAngles[3]) * interp;
			
			int side = i < 3 ? 1 : -1;
			int index = i + 1;
			
			GL11.glPushMatrix();
			
			hOff = 1.875D;
			sOff = 2D * side;
			GL11.glTranslated(sOff, hOff, sOff);
			GL11.glRotated(pivotRot * side, 1, 0, 0);
			GL11.glTranslated(-sOff, -hOff, -sOff);
			ResourceManager.assemfac.renderPart("Pivot" + index);

			hOff = 3.375D;
			sOff = 2D * side;
			GL11.glTranslated(sOff, hOff, sOff);
			GL11.glRotated(armRot * side, 1, 0, 0);
			GL11.glTranslated(-sOff, -hOff, -sOff);
			ResourceManager.assemfac.renderPart("Arm" + index);

			hOff = 3.375D;
			sOff = 0.625D * side;
			GL11.glTranslated(sOff, hOff, sOff);
			GL11.glRotated(pistonRot * side, 1, 0, 0);
			GL11.glTranslated(-sOff, -hOff, -sOff);
			ResourceManager.assemfac.renderPart("Piston" + index);
			GL11.glTranslated(0, -striker, 0);
			ResourceManager.assemfac.renderPart("Striker" + index);
			
			GL11.glPopMatrix();
		}
		
		/*GL11.glPushMatrix();
		hOff = 1.875D;
		sOff = 2D;
		GL11.glTranslated(sOff, hOff, sOff);
		GL11.glRotated(rot, 1, 0, 0);
		GL11.glTranslated(-sOff, -hOff, -sOff);
		ResourceManager.assemfac.renderPart("Pivot1");
		ResourceManager.assemfac.renderPart("Pivot2");
		ResourceManager.assemfac.renderPart("Pivot3");

		hOff = 3.375D;
		sOff = 2D;
		GL11.glTranslated(sOff, hOff, sOff);
		GL11.glRotated(rot * 1.2, -1, 0, 0);
		GL11.glTranslated(-sOff, -hOff, -sOff);
		ResourceManager.assemfac.renderPart("Arm1");
		ResourceManager.assemfac.renderPart("Arm2");
		ResourceManager.assemfac.renderPart("Arm3");

		hOff = 3.375D;
		sOff = 0.625D;
		GL11.glTranslated(sOff, hOff, sOff);
		GL11.glRotated(rot2, 1, 0, 0);
		GL11.glTranslated(-sOff, -hOff, -sOff);
		ResourceManager.assemfac.renderPart("Piston1");
		ResourceManager.assemfac.renderPart("Piston2");
		ResourceManager.assemfac.renderPart("Piston3");
		GL11.glTranslated(0, Math.sin((double)(System.currentTimeMillis() / 70D)) * 0.75 - 0.5, 0);
		ResourceManager.assemfac.renderPart("Striker1");
		ResourceManager.assemfac.renderPart("Striker2");
		ResourceManager.assemfac.renderPart("Striker3");
		GL11.glPopMatrix();

		rot = -Math.sin((double)((System.currentTimeMillis() + 500) / 500D)) * 25 + 20;
		rot2 = -Math.sin((double)((System.currentTimeMillis() + 150) / 400D)) * 10;
		GL11.glPushMatrix();
		hOff = 1.875D;
		sOff = -2D;
		GL11.glTranslated(sOff, hOff, sOff);
		GL11.glRotated(rot, 1, 0, 0);
		GL11.glTranslated(-sOff, -hOff, -sOff);
		ResourceManager.assemfac.renderPart("Pivot4");
		ResourceManager.assemfac.renderPart("Pivot5");
		ResourceManager.assemfac.renderPart("Pivot6");

		hOff = 3.375D;
		sOff = -2D;
		GL11.glTranslated(sOff, hOff, sOff);
		GL11.glRotated(rot * 1.2, -1, 0, 0);
		GL11.glTranslated(-sOff, -hOff, -sOff);
		ResourceManager.assemfac.renderPart("Arm4");
		ResourceManager.assemfac.renderPart("Arm5");
		ResourceManager.assemfac.renderPart("Arm6");

		hOff = 3.375D;
		sOff = -0.625D;
		GL11.glTranslated(sOff, hOff, sOff);
		GL11.glRotated(rot2, 1, 0, 0);
		GL11.glTranslated(-sOff, -hOff, -sOff);
		ResourceManager.assemfac.renderPart("Piston4");
		ResourceManager.assemfac.renderPart("Piston5");
		ResourceManager.assemfac.renderPart("Piston6");
		GL11.glTranslated(0, Math.sin((double)((System.currentTimeMillis() + 130) / 70D)) * 0.75 - 0.5, 0);
		ResourceManager.assemfac.renderPart("Striker4");
		ResourceManager.assemfac.renderPart("Striker5");
		ResourceManager.assemfac.renderPart("Striker6");
		GL11.glPopMatrix();*/
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}

}
