package com.hbm.render.util;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;

public class SoyuzPronter {

	
	public static void prontSoyuz() {
		
		prontMain();
		prontBoosters();
	}
	
	public static void prontMain() {

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		TextureManager tex = Minecraft.getMinecraft().getTextureManager();

		tex.bindTexture(ResourceManager.soyuz_engineblock);
		ResourceManager.soyuz.renderOnly("EngineBlock");
		
		tex.bindTexture(ResourceManager.soyuz_bottomstage);
		ResourceManager.soyuz.renderOnly("BottomStage");
		
		tex.bindTexture(ResourceManager.soyuz_topstage);
		ResourceManager.soyuz.renderOnly("TopStage");
		
		tex.bindTexture(ResourceManager.soyuz_payload);
		ResourceManager.soyuz.renderOnly("Payload");
		
		tex.bindTexture(ResourceManager.soyuz_memento);
		ResourceManager.soyuz.renderOnly("Memento");
		
		tex.bindTexture(ResourceManager.soyuz_payloadblocks);
		ResourceManager.soyuz.renderOnly("PayloadBlocks");
		
		tex.bindTexture(ResourceManager.soyuz_les);
		ResourceManager.soyuz.renderOnly("LES");
		
		tex.bindTexture(ResourceManager.soyuz_lesthrusters);
		ResourceManager.soyuz.renderOnly("LESThrusters");
		
		tex.bindTexture(ResourceManager.soyuz_mainengines);
		ResourceManager.soyuz.renderOnly("MainEngines");
		
		tex.bindTexture(ResourceManager.soyuz_sideengines);
		ResourceManager.soyuz.renderOnly("SideEngines");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
	
	public static void prontBoosters() {

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		TextureManager tex = Minecraft.getMinecraft().getTextureManager();
		
		tex.bindTexture(ResourceManager.soyuz_booster);
		ResourceManager.soyuz.renderOnly("Booster.000");
		ResourceManager.soyuz.renderOnly("Booster.001");
		ResourceManager.soyuz.renderOnly("Booster.002");
		ResourceManager.soyuz.renderOnly("Booster.003");

		tex.bindTexture(ResourceManager.soyuz_mainengines);
		ResourceManager.soyuz.renderOnly("BoosterEngines.000");
		ResourceManager.soyuz.renderOnly("BoosterEngines.001");
		ResourceManager.soyuz.renderOnly("BoosterEngines.002");
		ResourceManager.soyuz.renderOnly("BoosterEngines.003");

		tex.bindTexture(ResourceManager.soyuz_boosterside);
		ResourceManager.soyuz.renderOnly("BoosterSide.000");
		ResourceManager.soyuz.renderOnly("BoosterSide.001");
		ResourceManager.soyuz.renderOnly("BoosterSide.002");
		ResourceManager.soyuz.renderOnly("BoosterSide.003");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

}
