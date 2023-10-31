package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.render.util.RenderSparks;
import com.hbm.tileentity.machine.TileEntityAABattery;
import com.hbm.tileentity.machine.TileEntityTesla;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderAABattery extends TileEntitySpecialRenderer {
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		
        bindTexture(ResourceManager.aa_tex);

        ResourceManager.emp.renderOnly("Body");
        
        TileEntityAABattery emp = (TileEntityAABattery)tile;
        
        boolean on = emp.getPower() > 6000;

        GL11.glPushMatrix();
        	if(on)
        		GL11.glRotatef(System.currentTimeMillis() / 2 % 360, 0, 1, 0);
	    	
        	float rotors = 3;
	        for(int i = 0; i < rotors; i++) {
	        	ResourceManager.emp.renderOnly("Rotor");
	        	GL11.glRotatef(360 / rotors, 0, 1, 0);
	        }
        GL11.glPopMatrix();

    	if(on) {
			for(int i = 0; i < 5; i++) {
				RenderSparks.renderSpark((int) System.currentTimeMillis() / 100 + i * 10000, 0, 2, 0, 0.5F, 5, 10, 0xFFFF00, 0xFFFFFF);
				RenderSparks.renderSpark((int) System.currentTimeMillis() / 50 + i * 10000, 0, 2, 0, 0.5F, 5, 10, 0xFFFF00, 0xFFFFFF);
			}
    	}
        double sx = emp.xCoord + 0.5D;
        double sy = emp.yCoord + emp.offset;
        double sz = emp.zCoord + 0.5D;

        GL11.glTranslated(0.0D, emp.offset, 0.0D);
        for(double[] target : emp.targets) {
        	
        	double length = Math.sqrt(Math.pow(target[0] - sx, 2) + Math.pow(target[1] - sy, 2) + Math.pow(target[2] - sz, 2));
        	
	        BeamPronter.prontBeam(Vec3.createVectorHelper(-target[0] + sx, target[1] - sy, -target[2] + sz), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x404040, 0x404040, (int)emp.getWorldObj().getTotalWorldTime() % 1000 + 1, (int) (length * 5), 0.125F, 2, 0.03125F);

        }
        GL11.glPopMatrix();

	}
}