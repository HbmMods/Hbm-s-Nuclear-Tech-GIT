package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityZirnoxDebris;
import com.hbm.entity.projectile.EntityZirnoxDebris.DebrisType;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderZirnoxDebris extends Render {
	
	//for fallback only
		private static final ResourceLocation tex_base = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_side.png");
		private static final ResourceLocation tex_element = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_element.png");
		private static final ResourceLocation tex_blank = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_blank.png");
		private static final ResourceLocation tex_graphite = new ResourceLocation(RefStrings.MODID + ":textures/blocks/block_graphite.png");

		@Override
		public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {

			GL11.glPushMatrix();
			GL11.glTranslated(x, y + 0.125D, z);
			
			EntityZirnoxDebris debris = (EntityZirnoxDebris)entity;

			GL11.glRotatef(debris.getEntityId() % 360, 0, 1, 0); //rotate based on entity ID to add unique randomness
			GL11.glRotatef(debris.lastRot + (debris.rot - debris.lastRot) * f1, 1, 1, 1);
			
			DebrisType type = debris.getType();
			
			switch(type) {
			case BLANK: bindTexture(tex_blank); ResourceManager.deb_blank.renderAll(); break;
			case ELEMENT: bindTexture(tex_element); ResourceManager.deb_element.renderAll(); break;
			case SHRAPNEL: bindTexture(tex_blank); ResourceManager.deb_blank.renderAll(); break;
			case GRAPHITE: bindTexture(tex_graphite); ResourceManager.deb_graphite.renderAll(); break;
			case CONCRETE: bindTexture(tex_blank); ResourceManager.deb_lid.renderAll(); break;
			case EXCHANGER: bindTexture(tex_blank); ResourceManager.deb_lid.renderAll(); break;
			default: break;
			}
			
			GL11.glPopMatrix();
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity entity) {
			return tex_base;
		}
		
}
