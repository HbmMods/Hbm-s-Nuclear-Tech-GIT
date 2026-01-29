package com.hbm.render.tileentity.door;

import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.TileEntityDoorGeneric;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class RenderFireDoor implements IRenderDoors {
	
	public static final RenderFireDoor INSTANCE = new RenderFireDoor();

	@Override
	public void render(TileEntityDoorGeneric door, DoubleBuffer buf) {
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(
				door.getSkinIndex() == 2 ? ResourceManager.pheo_fire_door_orange_tex :
				door.getSkinIndex() == 1 ? ResourceManager.pheo_fire_door_black_tex :
					ResourceManager.pheo_fire_door_tex);
		
		double maxRaise = 2.75;
		double raise = 0;
		if(door.state == door.STATE_OPEN) raise = maxRaise;
		
		if(door.currentAnimation != null) {
			raise = IRenderDoors.getRelevantTransformation("DOOR", door.currentAnimation)[1] * maxRaise;
		}

		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(-0.5, 0, 0);
		ResourceManager.pheo_fire_door.renderPart("Frame");
		GL11.glTranslated(0, MathHelper.clamp_double(raise, 0, maxRaise), 0);
		ResourceManager.pheo_fire_door.renderPart("Door");
	}
}
