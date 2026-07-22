package com.hbm.render.tileentity.door;

import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.DoorDecl;
import com.hbm.tileentity.TileEntityDoorGeneric;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class RenderCargoDoor implements IRenderDoors {

	public static final RenderCargoDoor INSTANCE = new RenderCargoDoor();

	@Override
	public void render(TileEntityDoorGeneric door, DoubleBuffer buf) {

		Minecraft.getMinecraft().getTextureManager().bindTexture(DoorDecl.CARGO_DOOR.getSkinFromIndex(door.getSkinIndex()));

		double botMove = 0;
		double topMove = 0;

		if (door.state == door.STATE_OPEN) {
			botMove = 2.0;
			topMove = 1.0;
		}

		if (door.currentAnimation != null) {
			double botProgress = MathHelper.clamp_double(IRenderDoors.getRelevantTransformation("BOT", door.currentAnimation)[1], 0, 1);
			double topProgress = MathHelper.clamp_double(IRenderDoors.getRelevantTransformation("TOP", door.currentAnimation)[1], 0, 1);
			botMove = botProgress * 2.0;
			topMove = topProgress * 1.0;
		}

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 0);

		ResourceManager.pheo_cargo_door.renderPart("Frame");

		GL11.glPushMatrix();
		GL11.glTranslated(0, topMove, 0);
		ResourceManager.pheo_cargo_door.renderPart("DoorTop");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, botMove, 0);
		ResourceManager.pheo_cargo_door.renderPart("DoorBot");
		GL11.glPopMatrix();

		GL11.glPopMatrix();
	}
}
