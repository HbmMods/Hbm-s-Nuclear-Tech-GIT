package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.RenderMiscEffects;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.bomb.TileEntityAntimatter;
import com.hbm.tileentity.machine.storage.TileEntityMachineOrbus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderAntimatter extends TileEntitySpecialRenderer {

    @Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f)
    {
    	TileEntityAntimatter amat = (TileEntityAntimatter) te;
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		switch(te.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		double scale = 0.1;
		if(amat.hasAmatCore()) {
			
			//GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			int c = 0x010101;
			GL11.glColor3ub((byte)((c & 0xff0000) >> 16), (byte)((c & 0x00ff00) >> 8), (byte)((c & 0x0000ff)));
			GL11.glPushMatrix();
			GL11.glTranslated(0, 2.4D + Math.sin(((te.getWorldObj().getTotalWorldTime() + f) * 0.1D) % (Math.PI * 2D)) * 0.125 * scale, 0);
			GL11.glScaled(scale, scale, scale);
			ResourceManager.sphere_uv.renderAll();
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
		
        GL11.glShadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.Antimatter_tex);
        ResourceManager.Antimatter.renderAll();
		
		if(amat.hasAmatCore()) {
			GL11.glTranslated(0, 1, 0);
			BeamPronter.prontBeam(Vec3.createVectorHelper(0, 2.5, 0), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x101020, 0x101020, 0, 1, 0F, 6, (float)scale * 0.1F, 0.5F);
			BeamPronter.prontBeam(Vec3.createVectorHelper(0, 2.5, 0), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x202060, 0x202060, (int)(te.getWorldObj().getTotalWorldTime() / 2) % 1000, 6, (float)scale, 2, 0.0625F * (float)scale, 0.5F);
			BeamPronter.prontBeam(Vec3.createVectorHelper(0, 2.5, 0), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x202060, 0x202060, (int)(te.getWorldObj().getTotalWorldTime() / 4) % 1000, 6, (float)scale, 2, 0.0625F * (float)scale, 0.5F);
		}
		

       
     
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
    }
}
