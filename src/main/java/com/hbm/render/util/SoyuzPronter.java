package com.hbm.render.util;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class SoyuzPronter {
	
	public static enum SoyuzSkin {
		
		SOYUZ(
				ResourceManager.soyuz_engineblock,
				ResourceManager.soyuz_bottomstage,
				ResourceManager.soyuz_topstage,
				ResourceManager.soyuz_payload,
				ResourceManager.soyuz_payloadblocks,
				ResourceManager.soyuz_les,
				ResourceManager.soyuz_lesthrusters,
				ResourceManager.soyuz_mainengines,
				ResourceManager.soyuz_sideengines,
				ResourceManager.soyuz_booster,
				ResourceManager.soyuz_boosterside
		),
		LUNA(
				ResourceManager.soyuz_luna_engineblock,
				ResourceManager.soyuz_luna_bottomstage,
				ResourceManager.soyuz_luna_topstage,
				ResourceManager.soyuz_luna_payload,
				ResourceManager.soyuz_luna_payloadblocks,
				ResourceManager.soyuz_luna_les,
				ResourceManager.soyuz_luna_lesthrusters,
				ResourceManager.soyuz_luna_mainengines,
				ResourceManager.soyuz_luna_sideengines,
				ResourceManager.soyuz_luna_booster,
				ResourceManager.soyuz_luna_boosterside
		),
		AUTHENTIC(
				ResourceManager.soyuz_authentic_engineblock,
				ResourceManager.soyuz_authentic_bottomstage,
				ResourceManager.soyuz_authentic_topstage,
				ResourceManager.soyuz_authentic_payload,
				ResourceManager.soyuz_authentic_payloadblocks,
				ResourceManager.soyuz_authentic_les,
				ResourceManager.soyuz_authentic_lesthrusters,
				ResourceManager.soyuz_authentic_mainengines,
				ResourceManager.soyuz_authentic_sideengines,
				ResourceManager.soyuz_authentic_booster,
				ResourceManager.soyuz_authentic_boosterside
		);
		
		public ResourceLocation engineblock;
		public ResourceLocation bottomstage;
		public ResourceLocation topstage;
		public ResourceLocation payload;
		public ResourceLocation payloadblocks;
		public ResourceLocation les;
		public ResourceLocation lesthrusters;
		public ResourceLocation mainengines;
		public ResourceLocation sideengines;
		public ResourceLocation booster;
		public ResourceLocation boosterside;
		
		SoyuzSkin(
				ResourceLocation engineblock,
				ResourceLocation bottomstage,
				ResourceLocation topstage,
				ResourceLocation payload,
				ResourceLocation payloadblocks,
				ResourceLocation les,
				ResourceLocation lesthrusters,
				ResourceLocation mainengines,
				ResourceLocation sideengines,
				ResourceLocation booster,
				ResourceLocation boosterside
		) {
			this.engineblock = engineblock;
			this.bottomstage = bottomstage;
			this.topstage = topstage;
			this.payload = payload;
			this.payloadblocks = payloadblocks;
			this.les = les;
			this.lesthrusters = lesthrusters;
			this.mainengines = mainengines;
			this.sideengines = sideengines;
			this.booster = booster;
			this.boosterside = boosterside;
		}
	}
	
	public static void prontSoyuz(int type) {
		
		if(type >= SoyuzSkin.values().length || type < 0)
			return;
		
		prontMain(type);
		prontBoosters(type);
	}
	
	public static void prontMain(int type) {
		SoyuzSkin skin = SoyuzSkin.values()[type];

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		TextureManager tex = Minecraft.getMinecraft().getTextureManager();

		tex.bindTexture(skin.engineblock);
		ResourceManager.soyuz.renderOnly("EngineBlock");
		
		tex.bindTexture(skin.bottomstage);
		ResourceManager.soyuz.renderOnly("BottomStage");
		
		tex.bindTexture(skin.topstage);
		ResourceManager.soyuz.renderOnly("TopStage");
		
		tex.bindTexture(skin.payload);
		ResourceManager.soyuz.renderOnly("Payload");
		
		tex.bindTexture(ResourceManager.soyuz_memento);
		ResourceManager.soyuz.renderOnly("Memento");
		
		tex.bindTexture(skin.payloadblocks);
		ResourceManager.soyuz.renderOnly("PayloadBlocks");
		
		tex.bindTexture(skin.les);
		ResourceManager.soyuz.renderOnly("LES");
		
		tex.bindTexture(skin.lesthrusters);
		ResourceManager.soyuz.renderOnly("LESThrusters");
		
		tex.bindTexture(skin.mainengines);
		ResourceManager.soyuz.renderOnly("MainEngines");
		
		tex.bindTexture(skin.sideengines);
		ResourceManager.soyuz.renderOnly("SideEngines");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
	
	public static void prontBoosters(int type) {
		
		SoyuzSkin skin = SoyuzSkin.values()[type];

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		TextureManager tex = Minecraft.getMinecraft().getTextureManager();
		
		tex.bindTexture(skin.booster);
		ResourceManager.soyuz.renderOnly("Booster.000");
		ResourceManager.soyuz.renderOnly("Booster.001");
		ResourceManager.soyuz.renderOnly("Booster.002");
		ResourceManager.soyuz.renderOnly("Booster.003");

		tex.bindTexture(skin.mainengines);
		ResourceManager.soyuz.renderOnly("BoosterEngines.000");
		ResourceManager.soyuz.renderOnly("BoosterEngines.001");
		ResourceManager.soyuz.renderOnly("BoosterEngines.002");
		ResourceManager.soyuz.renderOnly("BoosterEngines.003");

		tex.bindTexture(skin.boosterside);
		ResourceManager.soyuz.renderOnly("BoosterSide.000");
		ResourceManager.soyuz.renderOnly("BoosterSide.001");
		ResourceManager.soyuz.renderOnly("BoosterSide.002");
		ResourceManager.soyuz.renderOnly("BoosterSide.003");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
	
	public static void prontCapsule() {

		GL11.glPushMatrix();

		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		TextureManager tex = Minecraft.getMinecraft().getTextureManager();

		tex.bindTexture(ResourceManager.soyuz_module_dome_tex);
		ResourceManager.soyuz_module.renderPart("Dome");
		tex.bindTexture(ResourceManager.soyuz_module_lander_tex);
		ResourceManager.soyuz_module.renderPart("Capsule");
		tex.bindTexture(ResourceManager.soyuz_module_propulsion_tex);
		ResourceManager.soyuz_module.renderPart("Propulsion");
		tex.bindTexture(ResourceManager.soyuz_module_solar_tex);
		ResourceManager.soyuz_module.renderPart("Solar");

		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();

	}

}
