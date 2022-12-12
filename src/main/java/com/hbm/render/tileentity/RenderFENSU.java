package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.storage.TileEntityMachineFENSU;
import com.hbm.wiaj.WorldInAJar;
import com.hbm.wiaj.actors.ITileActorRenderer;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class RenderFENSU extends TileEntitySpecialRenderer implements ITileActorRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		switch(te.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		bindTexture(ResourceManager.fensu_tex);
		ResourceManager.fensu.renderPart("Base");

		TileEntityMachineFENSU fensu = (TileEntityMachineFENSU) te;
		float rot = fensu.prevRotation + (fensu.rotation - fensu.prevRotation) * f;

		GL11.glTranslated(0, 2.5, 0);
		GL11.glRotated(rot, 1, 0, 0);
		GL11.glTranslated(0, -2.5, 0);
		ResourceManager.fensu.renderPart("Disc");

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		ResourceManager.fensu.renderPart("Lights");
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glPopAttrib();
		GL11.glPopMatrix();

		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}

	@Override
	public void renderActor(WorldInAJar world, int ticks, float interp, NBTTagCompound data) {
		double x = data.getDouble("x");
		double y = data.getDouble("y");
		double z = data.getDouble("z");
		int rotation = data.getInteger("rotation");
		float lastSpin = data.getFloat("lastSpin");
		float spin = data.getFloat("spin");
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		switch(rotation) {
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}

		ITileActorRenderer.bindTexture(ResourceManager.fensu_tex);
		ResourceManager.fensu.renderPart("Base");

		float rot = lastSpin + (spin - lastSpin) * interp;

		GL11.glTranslated(0, 2.5, 0);
		GL11.glRotated(rot, 1, 0, 0);
		GL11.glTranslated(0, -2.5, 0);
		ResourceManager.fensu.renderPart("Disc");
		ResourceManager.fensu.renderPart("Lights");
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}

	@Override
	public void updateActor(int ticks, NBTTagCompound data) {
		
		float lastSpin = 0;
		float spin = data.getFloat("spin");
		float speed = data.getFloat("speed");
		
		lastSpin = spin;
		spin += speed;
		
		if(spin >= 360) {
			lastSpin -= 360;
			spin -= 360;
		}
		
		data.setFloat("lastSpin", lastSpin);
		data.setFloat("spin", spin);
	}
}
