package com.hbm.wiaj.actors;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public interface ITileActorRenderer {

	public void renderActor(int ticks, float interp, NBTTagCompound data);
	public void updateActor(int ticks, NBTTagCompound data);
	
	public static void bindTexture(ResourceLocation tex) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
	}
}
