package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.machine.TileEntityTesla;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderTesla extends TileEntitySpecialRenderer {

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(ResourceManager.tesla_tex);
        ResourceManager.tesla.renderAll();
        
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        TileEntityTesla tesla = (TileEntityTesla)tileEntity;

        double sx = tesla.xCoord + 0.5D;
        double sy = tesla.yCoord + tesla.offset;
        double sz = tesla.zCoord + 0.5D;

        GL11.glTranslated(0.0D, tesla.offset, 0.0D);
        for(double[] target : tesla.targets) {
        	
        	double length = Math.sqrt(Math.pow(target[0] - sx, 2) + Math.pow(target[1] - sy, 2) + Math.pow(target[2] - sz, 2));
        	
	        BeamPronter.prontBeam(Vec3.createVectorHelper(-target[0] + sx, target[1] - sy, -target[2] + sz), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x404040, 0x404040, (int)tileEntity.getWorldObj().getTotalWorldTime() % 1000 + 1, (int) (length * 5), 0.125F, 2, 0.03125F, 0.5F);

        }

        GL11.glPopMatrix();
    }
}
