package com.hbm.render.tileentity.door;

import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.TileEntityDoorGeneric;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RenderVaultDoor implements IRenderDoors {
	
	public static final RenderVaultDoor INSTANCE = new RenderVaultDoor();

	@Override
	public void render(TileEntityDoorGeneric door, DoubleBuffer buf) {

		ResourceLocation doorTex = ResourceManager.pheo_vault_door_3;
		ResourceLocation labelTex = ResourceManager.pheo_label_101;
		
		switch(door.getSkinIndex()) {
		case 1: labelTex = ResourceManager.pheo_label_87; break;
		case 2: labelTex = ResourceManager.pheo_label_106; break;
		case 3: doorTex = ResourceManager.pheo_vault_door_4; labelTex = ResourceManager.pheo_label_81; break;
		case 4: doorTex = ResourceManager.pheo_vault_door_4; labelTex = ResourceManager.pheo_label_111; break;
		case 5: doorTex = ResourceManager.pheo_vault_door_s; labelTex = ResourceManager.pheo_label_2; break;
		case 6: doorTex = ResourceManager.pheo_vault_door_s; labelTex = ResourceManager.pheo_label_99; break;
		}
		
		double pull = 0;
		double slide = 0;
		
		if(door.state == door.STATE_OPEN) {
			pull = 1;
			slide = 1;
		}
		
		if(door.currentAnimation != null) {
			pull = IRenderDoors.getRelevantTransformation("PULL", door.currentAnimation)[2];
			slide = IRenderDoors.getRelevantTransformation("SLIDE", door.currentAnimation)[0];
		}
		
		double diameter = 4.25D;
		double circumference = diameter * Math.PI;
		slide *= 5D;
		double roll = 360D * slide / circumference;

		Minecraft.getMinecraft().getTextureManager().bindTexture(doorTex);
		ResourceManager.pheo_vault_door.renderPart("Frame");
		GL11.glTranslated(-pull, 0, 0);
		GL11.glTranslated(0, 0, slide);
		GL11.glTranslated(0, 2.5, 0);
		GL11.glRotated(roll, 1, 0, 0);
		GL11.glTranslated(0, -2.5, 0);
		ResourceManager.pheo_vault_door.renderPart("Door");
		Minecraft.getMinecraft().getTextureManager().bindTexture(labelTex);
		ResourceManager.pheo_vault_door.renderPart("Label");
	}
}
