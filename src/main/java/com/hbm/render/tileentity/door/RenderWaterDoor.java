package com.hbm.render.tileentity.door;

import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.TileEntityDoorGeneric;

import net.minecraft.client.Minecraft;

public class RenderWaterDoor implements IRenderDoors {
	
	public static final RenderWaterDoor INSTANCE = new RenderWaterDoor();

	@Override
	public void render(TileEntityDoorGeneric door, DoubleBuffer buf) {
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.pheo_water_door_tex);

		double maxRot = 120;
		double rot = 0;
		double bolt = 0;
		if(door.state == door.STATE_OPEN) {
			rot = maxRot;
			bolt = 1D;
		}
		
		if(door.currentAnimation != null) {
			rot = IRenderDoors.getRelevantTransformation("DOOR", door.currentAnimation)[1] * maxRot;
			bolt = IRenderDoors.getRelevantTransformation("BOLT", door.currentAnimation)[2];
		}

		GL11.glTranslated(0.375, 0.0, 0.0);
		GL11.glRotated(90, 0, 1, 0);
		ResourceManager.pheo_water_door.renderPart("Frame");

		GL11.glTranslated(-1.1875, 0, 0);
		GL11.glRotated(-rot, 0, 1, 0);
		GL11.glTranslated(1.1875, 0, 0);
		ResourceManager.pheo_water_door.renderPart("Door_Cube.003"); // ah fuck it
		
		GL11.glPushMatrix();
		GL11.glTranslated(-0.4 * bolt, 0, 0);
		ResourceManager.pheo_water_door.renderPart("Bolts");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0.40625F, 2.28125, 0);
		GL11.glRotated(bolt * 360, 0, 0, 1);
		GL11.glTranslated(-0.40625F, -2.28125, 0);
		ResourceManager.pheo_water_door.renderPart("Top");
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(0.40625F, 0.71875, 0);
		GL11.glRotated(bolt * 360, 0, 0, 1);
		GL11.glTranslated(-0.40625F, -0.71875, 0);
		ResourceManager.pheo_water_door.renderPart("Bottom");
		GL11.glPopMatrix();
	}
}
