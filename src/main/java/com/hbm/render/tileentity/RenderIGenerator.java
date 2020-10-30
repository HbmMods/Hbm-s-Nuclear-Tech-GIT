package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderIGenerator extends TileEntitySpecialRenderer {

    @Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);

		switch(te.getBlockMetadata() - BlockDummyable.offset)
		{
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		TileEntityMachineIGenerator igen = (TileEntityMachineIGenerator)te;

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        
        bindTexture(ResourceManager.igen_tex);
        ResourceManager.igen.renderPart("Base");
        
        float angle = igen.prevRotation + (igen.rotation - igen.prevRotation) * f;
        float px = 0.0625F;
        float sine = (float) Math.sin(Math.toRadians(angle));
        float cosine = (float) Math.cos(Math.toRadians(angle));
        float armAng = 22.5F;
        
        GL11.glPushMatrix();
        GL11.glTranslated(0, 3.5, 0);
        GL11.glRotatef(angle, 0, 0, 1);
        GL11.glTranslated(0, -3.5, 0);
        
        bindTexture(ResourceManager.igen_rotor);
        ResourceManager.igen.renderPart("Rotor");
        GL11.glPopMatrix();
        
        
        
        GL11.glPushMatrix();
        GL11.glTranslated(0, 3.5, px * 5);
        GL11.glRotatef(angle, -1, 0, 0);
        GL11.glTranslated(0, -3.5, px * -5);
        
        bindTexture(ResourceManager.igen_cog);
        ResourceManager.igen.renderPart("CogLeft");
        GL11.glPopMatrix();
        
        
        
        GL11.glPushMatrix();
        GL11.glTranslated(0, 3.5, px * 5);
        GL11.glRotatef(angle, 1, 0, 0);
        GL11.glTranslated(0, -3.5, px * -5);
        
        bindTexture(ResourceManager.igen_cog);
        ResourceManager.igen.renderPart("CogRight");
        GL11.glPopMatrix();
        
        
        
        GL11.glPushMatrix();
        GL11.glTranslated(0, 0, cosine * 0.8725 - 1);
        
        bindTexture(ResourceManager.igen_pistons);
        ResourceManager.igen.renderPart("Pistons");
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        
        GL11.glTranslated(0, sine * 0.55, cosine * 0.8725 - 1.125);
        
        GL11.glTranslated(0, 3.5, px * 6.5);
        GL11.glRotatef(sine * -armAng, 1, 0, 0);
        GL11.glTranslated(0, -3.5, px * -5);
        
        bindTexture(ResourceManager.igen_arm);
        ResourceManager.igen.renderPart("ArmLeft");
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        
        GL11.glTranslated(0, -sine * 0.55, cosine * 0.8725 - 1.125);
        
        GL11.glTranslated(0, 3.5, px * 6.5);
        GL11.glRotatef(sine * armAng, 1, 0, 0);
        GL11.glTranslated(0, -3.5, px * -5);
        
        bindTexture(ResourceManager.igen_arm);
        ResourceManager.igen.renderPart("ArmRight");
        GL11.glPopMatrix();

        GL11.glTranslated(-0.75, 5.5625, -7);
        
        if(igen.torque > 0) {
	        for(int i = 0; i < 2; i++) {
		        BeamPronter.prontBeam(Vec3.createVectorHelper(1.5, 0, 0), EnumWaveType.RANDOM, EnumBeamType.LINE, 0x8080ff, 0x0000ff, (int)te.getWorldObj().getTotalWorldTime() % 1000 + i, 5, px * 4, 0, 0);
		        BeamPronter.prontBeam(Vec3.createVectorHelper(1.5, 0, 0), EnumWaveType.RANDOM, EnumBeamType.LINE, 0xffffff, 0x0000ff, (int)te.getWorldObj().getTotalWorldTime() % 1000 + 2 + i, 5, px * 4, 0, 0);
	        }
        }
        
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        GL11.glPopMatrix();
    }
}
