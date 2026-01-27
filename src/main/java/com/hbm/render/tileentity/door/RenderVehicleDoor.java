package com.hbm.render.tileentity.door;

import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.TileEntityDoorGeneric;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class RenderVehicleDoor implements IRenderDoors {
	
	public static final RenderVehicleDoor INSTANCE = new RenderVehicleDoor();

	@Override
	public void render(TileEntityDoorGeneric door, DoubleBuffer buf) {
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.pheo_vehicle_door_tex);
		
		double maxOpen = 3;
		double open = 0;
		if(door.state == door.STATE_OPEN) open = maxOpen;
		
		if(door.currentAnimation != null) {
			open = IRenderDoors.getRelevantTransformation("DOOR", door.currentAnimation)[1] * maxOpen;
		}

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotated(90, 0, 1, 0);
		ResourceManager.pheo_vehicle_door.renderPart("Frame");
		
		GL11.glEnable(GL11.GL_CLIP_PLANE0);
		buf.put(new double[] { 1, 0, 0, 3.4375 }); buf.rewind();
		GL11.glClipPlane(GL11.GL_CLIP_PLANE0, buf);
		
		GL11.glEnable(GL11.GL_CLIP_PLANE1);
		buf.put(new double[] { -1, 0 ,0, 3.4375 }); buf.rewind();
		GL11.glClipPlane(GL11.GL_CLIP_PLANE1, buf);
		
		GL11.glPushMatrix();
		GL11.glTranslated(-MathHelper.clamp_double(open, 0, maxOpen), 0, 0);
		ResourceManager.pheo_vehicle_door.renderPart("Left");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(MathHelper.clamp_double(open, 0, maxOpen), 0, 0);
		ResourceManager.pheo_vehicle_door.renderPart("Right");
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_CLIP_PLANE0);
		GL11.glDisable(GL11.GL_CLIP_PLANE1);
	}
}
