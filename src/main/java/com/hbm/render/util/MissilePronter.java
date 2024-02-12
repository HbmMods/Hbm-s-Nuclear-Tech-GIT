package com.hbm.render.util;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.ItemCustomMissilePart.PartType;

import net.minecraft.client.renderer.texture.TextureManager;

public class MissilePronter {

	public static void prontMissile(MissileMultipart missile, TextureManager tex) {
		
		GL11.glPushMatrix();
		
		if(missile.thruster != null && missile.thruster.type.name().equals(PartType.THRUSTER.name())) {
			
			tex.bindTexture(missile.thruster.texture);
			missile.thruster.model.renderAll();
			GL11.glTranslated(0, missile.thruster.height, 0);
		}
		
		if(missile.fuselage != null && missile.fuselage.type.name().equals(PartType.FUSELAGE.name())) {

			if(missile.fins != null && missile.fins.type.name().equals(PartType.FINS.name())) {
				
				tex.bindTexture(missile.fins.texture);
				missile.fins.model.renderAll();
			}
			
			tex.bindTexture(missile.fuselage.texture);
			missile.fuselage.model.renderAll();
			GL11.glTranslated(0, missile.fuselage.height, 0);
		}
		
		if(missile.warhead != null && missile.warhead.type.name().equals(PartType.WARHEAD.name())) {
			
			tex.bindTexture(missile.warhead.texture);
			missile.warhead.model.renderAll();
		}

		GL11.glPopMatrix();
	}
}
