package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.machine.TileEntityCoreEmitter;
import com.hbm.tileentity.machine.TileEntityCoreInjector;
import com.hbm.tileentity.machine.TileEntityCoreReceiver;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderCoreComponent extends TileEntitySpecialRenderer {
	
	public RenderCoreComponent() { }

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
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
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
        GL11.glTranslated(0.0D, 0D, 0.0D);

        if(tileEntity instanceof TileEntityCoreEmitter) {
	        bindTexture(ResourceManager.dfc_emitter_tex);
	        ResourceManager.dfc_emitter.renderAll();
	        GL11.glTranslated(0, 0.5, 0);
	        int range = ((TileEntityCoreEmitter)tileEntity).beam;
	        
	        if(range > 0) {
		        BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x404000, 0x404000, 0, 1, 0F, 2, 0.0625F);
		        BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x401500, 0x401500, (int)tileEntity.getWorldObj().getTotalWorldTime() % 1000, range * 2, 0.125F, 4, 0.0625F);
		        BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x401500, 0x401500, (int)tileEntity.getWorldObj().getTotalWorldTime() % 1000 + 1, range * 2, 0.125F, 4, 0.0625F);
	        }
        }

        if(tileEntity instanceof TileEntityCoreReceiver) {
	        bindTexture(ResourceManager.dfc_receiver_tex);
	        ResourceManager.dfc_receiver.renderAll();
        }

        if(tileEntity instanceof TileEntityCoreInjector) {
	        bindTexture(ResourceManager.dfc_injector_tex);
	        ResourceManager.dfc_injector.renderAll();
	        
	        GL11.glTranslated(0, 0.5, 0);
	        int range = ((TileEntityCoreInjector)tileEntity).beam;
	        
	        if(range > 0) {
		        BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.RANDOM, EnumBeamType.LINE, 0x0000ff, 0x8080ff, (int)tileEntity.getWorldObj().getTotalWorldTime() % 1000, range, 0.0625F, 0, 0);
		        BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.RANDOM, EnumBeamType.LINE, 0x0000ff, 0x8080ff, (int)tileEntity.getWorldObj().getTotalWorldTime() % 1000 + 1, range, 0.0625F, 0, 0);
	        }
        }
        
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
    }

}
