package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.turret.TileEntityTurretBase;
import com.hbm.tileentity.turret.TileEntityTurretCheapo;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderCheapoTurret extends TileEntitySpecialRenderer {
	
	public RenderCheapoTurret() { }
	
    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		
		double yaw = 0;
		double pitch = 0;
		
		if(tileEntity instanceof TileEntityTurretBase) {
			yaw = ((TileEntityTurretBase)tileEntity).rotationYaw;
			pitch = ((TileEntityTurretBase)tileEntity).rotationPitch;
		}
        
		this.bindTexture(ResourceManager.turret_cheapo_base_tex);
        ResourceManager.turret_cheapo_base.renderAll();

        GL11.glPopMatrix();
        
        renderTileEntityAt2(tileEntity, x, y, z, f, yaw, pitch);
    }
    
	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f, double yaw, double pitch)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

		GL11.glRotated(yaw + 180, 0F, -1F, 0F);

		this.bindTexture(ResourceManager.turret_cheapo_rotor_tex);
        ResourceManager.turret_cheapo_rotor.renderAll();

        GL11.glPopMatrix();
        
        renderTileEntityAt3(tileEntity, x, y, z, f, yaw, pitch);
    }
    
	public void renderTileEntityAt3(TileEntity tileEntity, double x, double y, double z, float f, double yaw, double pitch)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 1.25D, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

		GL11.glRotated(yaw + 180, 0F, -1F, 0F);
		GL11.glRotated(pitch, 1F, 0F, 0F);

		this.bindTexture(ResourceManager.turret_cheapo_head_tex);
        ResourceManager.turret_cheapo_head.renderAll();

        GL11.glPopMatrix();
        
        renderTileEntityAt4(tileEntity, x, y, z, f, yaw, pitch);
    }
	
	public void renderTileEntityAt4(TileEntity tileEntity, double x, double y, double z, float f, double yaw, double pitch)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 1.25D, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
        
		GL11.glRotated(yaw + 180, 0F, -1F, 0F);
		GL11.glRotated(pitch, 1F, 0F, 0F);
		
        GL11.glTranslated(0, 0.25D, 0);
        
		GL11.glRotated(((TileEntityTurretCheapo)tileEntity).rotation, 0F, 0F, 1F);

		this.bindTexture(ResourceManager.turret_cheapo_gun_tex);
        ResourceManager.turret_cheapo_gun.renderAll();

        GL11.glPopMatrix();
    }
}
