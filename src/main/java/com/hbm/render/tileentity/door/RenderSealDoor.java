package com.hbm.render.tileentity.door;

import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.Library;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.TileEntityDoorGeneric;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class RenderSealDoor implements IRenderDoors {
	
	public static final RenderSealDoor INSTANCE = new RenderSealDoor();

	@Override
	public void render(TileEntityDoorGeneric door, DoubleBuffer buf) {
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.pheo_seal_door_tex);
		
		double maxRaise = 1;
		double raise = 0;
		if(door.state == door.STATE_OPEN) raise = maxRaise;
		
		if(door.currentAnimation != null) {
			raise = IRenderDoors.getRelevantTransformation("DOOR", door.currentAnimation)[1] * maxRaise;
		}

		GL11.glTranslated(0.5, 0, 0);
		ResourceManager.pheo_seal_door.renderPart("Frame");
		
		GL11.glEnable(GL11.GL_CLIP_PLANE0);
		buf.put(new double[] { 0, 0, -1, 0.5001 }); buf.rewind();
		GL11.glClipPlane(GL11.GL_CLIP_PLANE0, buf);
		
		GL11.glTranslated(0, 0, Library.smoothstep(MathHelper.clamp_double(raise, 0, maxRaise), 0, 1) * 0.9);
		ResourceManager.pheo_seal_door.renderPart("Door");
		
		GL11.glDisable(GL11.GL_CLIP_PLANE0);
	}
}
