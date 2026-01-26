package com.hbm.render.tileentity.door;

import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.TileEntityDoorGeneric;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class RenderSlidingBlastDoor implements IRenderDoors {
	
	public static final RenderSlidingBlastDoor INSTANCE = new RenderSlidingBlastDoor();

	@Override
	public void render(TileEntityDoorGeneric door, DoubleBuffer buf) {
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.pheo_blast_door_tex);
		
		double maxOpen = 2.125;
		double open = 0;
		double lock = 0;
		if(door.state == door.STATE_OPEN) {
			open = maxOpen;
			lock = 90;
		}
		
		if(door.currentAnimation != null) {
			open = IRenderDoors.getRelevantTransformation("DOOR", door.currentAnimation)[1] * maxOpen;
			lock = IRenderDoors.getRelevantTransformation("LOCK", door.currentAnimation)[0] * 90;
		}

		GL11.glDisable(GL11.GL_CULL_FACE);
		ResourceManager.pheo_blast_door.renderPart("Frame");
		
		GL11.glEnable(GL11.GL_CLIP_PLANE0);
		buf.put(new double[] { 0.0, 0.0, 1, 2.5 }); buf.rewind();
		GL11.glClipPlane(GL11.GL_CLIP_PLANE0, buf);
		
		GL11.glEnable(GL11.GL_CLIP_PLANE1);
		buf.put(new double[] { 0.0, 0.0, -1, 2.5 }); buf.rewind();
		GL11.glClipPlane(GL11.GL_CLIP_PLANE1, buf);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, MathHelper.clamp_double(open, 0, maxOpen));
		ResourceManager.pheo_blast_door.renderPart("LeftDoor");
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.8125, 0);
		GL11.glRotated(90 + lock, 1, 0, 0);
		GL11.glTranslated(0, -1.8125, 0);
		ResourceManager.pheo_blast_door.renderPart("RightLock");
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, -MathHelper.clamp_double(open, 0, maxOpen));
		ResourceManager.pheo_blast_door.renderPart("RightDoor");
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.8125, 0);
		GL11.glRotated(90 + lock, 1, 0, 0);
		GL11.glTranslated(0, -1.8125, 0);
		ResourceManager.pheo_blast_door.renderPart("LeftLock");
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_CLIP_PLANE0);
		GL11.glDisable(GL11.GL_CLIP_PLANE1);
	}

}
