package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.misc.MissileMultipart;
import com.hbm.render.misc.MissilePart;
import com.hbm.render.misc.MissilePronter;
import com.hbm.render.model.ModelTestRender;
import com.hbm.tileentity.machine.TileEntityMachineMissileAssembly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderMissileAssembly extends TileEntitySpecialRenderer {
	
	public RenderMissileAssembly() { }
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		
		if(!(tileentity instanceof TileEntityMachineMissileAssembly))
			return;
		
		TileEntityMachineMissileAssembly te = (TileEntityMachineMissileAssembly)tileentity;
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180, 0F, 0F, 1F);


		MissileMultipart missile = new MissileMultipart();
		
		if(te.getStackInSlot(1) != null)
			missile.warhead = MissilePart.getPart(te.getStackInSlot(1).getItem());
		
		if(te.getStackInSlot(2) != null)
			missile.fuselage = MissilePart.getPart(te.getStackInSlot(2).getItem());
		
		if(te.getStackInSlot(3) != null)
			missile.fins = MissilePart.getPart(te.getStackInSlot(3).getItem());
		
		if(te.getStackInSlot(4) != null)
			missile.thruster = MissilePart.getPart(te.getStackInSlot(4).getItem());
		
		GL11.glTranslated(-missile.getHeight() / 2, 0, 0);
		//GL11.glScaled(scale, scale, scale);
		
		GL11.glRotatef(-90, 1, 0, 0);
		GL11.glRotatef(-90, 0, 0, 1);
		GL11.glScalef(1, 1, 1);

		MissilePronter.prontMissile(missile, Minecraft.getMinecraft().getTextureManager());
		
		GL11.glPopMatrix();
	}
}
