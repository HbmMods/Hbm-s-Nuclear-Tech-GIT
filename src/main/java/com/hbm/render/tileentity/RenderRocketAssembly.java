package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.util.MissileMultipart;
import com.hbm.render.util.MissilePart;
import com.hbm.render.util.MissilePronter;
import com.hbm.tileentity.machine.TileEntityMachineRocketAssembly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRocketAssembly extends TileEntitySpecialRenderer {
	
	public RenderRocketAssembly() { }
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		
		if(!(tileentity instanceof TileEntityMachineRocketAssembly))
			return;
		
		TileEntityMachineRocketAssembly te = (TileEntityMachineRocketAssembly)tileentity;
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		switch(te.getBlockMetadata()) {
		case 2:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		}

		bindTexture(ResourceManager.missile_assembly_tex);
		ResourceManager.missile_assembly.renderAll();

		MissileMultipart missile = MissileMultipart.loadFromStruct(te.rocket);
		
		if(missile != null) {
			missile.warhead = MissilePart.getPart(te.getStackInSlot(0));
			missile.fuselage = MissilePart.getPart(te.getStackInSlot(1));
			missile.fins = MissilePart.getPart(te.getStackInSlot(2));
			missile.thruster = MissilePart.getPart(te.getStackInSlot(3));
	
			GL11.glTranslatef(0F, 1.5F, 0F);
	
			GL11.glEnable(GL11.GL_CULL_FACE);
			MissilePronter.prontMissile(missile, Minecraft.getMinecraft().getTextureManager());
		}
		
		GL11.glPopMatrix();
	}
}
