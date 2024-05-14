package com.hbm.dim.laythe;

import org.lwjgl.opengl.GL11;

import com.hbm.dim.SkyProviderCelestial;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;

public class SkyProviderLaytheSunset extends SkyProviderCelestial {

	public SkyProviderLaytheSunset() {
		super();
	}
	
	
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
    	super.render(partialTicks, world, mc);
	}

}
