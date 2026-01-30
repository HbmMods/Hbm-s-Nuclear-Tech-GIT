package com.hbm.render.tileentity.door;

import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.DoorDecl;
import com.hbm.tileentity.TileEntityDoorGeneric;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class RenderAirlockDoor implements IRenderDoors {
	
	public static final RenderAirlockDoor INSTANCE = new RenderAirlockDoor();

	@Override
	public void render(TileEntityDoorGeneric door, DoubleBuffer buf) {
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(DoorDecl.ROUND_AIRLOCK_DOOR.getSkinFromIndex(door.getSkinIndex()));
		
		double maxOpen = 1.5;
		double open = 0;
		if(door.state == door.STATE_OPEN) open = maxOpen;
		
		if(door.currentAnimation != null) {
			open = IRenderDoors.getRelevantTransformation("DOOR", door.currentAnimation)[1] * maxOpen;
		}

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslated(0, 0, 0.5);
		ResourceManager.pheo_airlock_door.renderPart("Frame");
		
		GL11.glEnable(GL11.GL_CLIP_PLANE0);
		buf.put(new double[] { 0.0, 0.0, 1, 1.999 }); buf.rewind();
		GL11.glClipPlane(GL11.GL_CLIP_PLANE0, buf);
		
		GL11.glEnable(GL11.GL_CLIP_PLANE1);
		buf.put(new double[] { 0.0, 0.0, -1, 1.999 }); buf.rewind();
		GL11.glClipPlane(GL11.GL_CLIP_PLANE1, buf);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, MathHelper.clamp_double(open, 0, maxOpen));
		ResourceManager.pheo_airlock_door.renderPart("Left");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, -MathHelper.clamp_double(open, 0, maxOpen));
		ResourceManager.pheo_airlock_door.renderPart("Right");
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_CLIP_PLANE0);
		GL11.glDisable(GL11.GL_CLIP_PLANE1);
	}
}
