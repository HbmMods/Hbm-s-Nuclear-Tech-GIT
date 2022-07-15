package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.BulletConfiguration;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponGlass implements IItemRenderer {
	
	public ItemRenderWeaponGlass() { }

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
		case INVENTORY:
			return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.5D;
			GL11.glRotated(25, 0, 0, 1);
			GL11.glTranslated(2.25, 0.0, 0.125);
			GL11.glRotatef(-10, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);
			
			break;
			
		case EQUIPPED:

			double scale = 0.25D;
			GL11.glScaled(-scale, scale, scale);
			GL11.glRotatef(20F, -3.0F, -0.75F, -1.0F);
			GL11.glRotatef(-170, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-30F, 2.0F, -1F, -5.0F);
			GL11.glTranslatef(5F, -0.35F, 0.25F);
			
			break;
			
		case ENTITY:

			double s1 = 0.5D;
			GL11.glScaled(s1, s1, s1);
			
			break;
			
		case INVENTORY:
			
			double s = 1.65D;
			GL11.glTranslatef(8F, 8F, 0F);
			GL11.glRotated(90, 0, 0, 1);
			GL11.glRotated(135, 0, 0, 1);
			GL11.glScaled(s, s, s);
			
			break;
			
		default: break;
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(0.9F, 1.0F, 1.0F, 0.5F);
		GL11.glDepthMask(false);
		
		ResourceManager.glass_cannon.renderPart("Gun");

		GL11.glDepthMask(true);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.glass_cannon_panel_tex);
		ResourceManager.glass_cannon.renderPart("Panel");
		
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		int color = 0xFFFFFF;
		double freq = 1;
		
//		if(item.getItem() instanceof ItemEnergyGunBase) {
//			BulletConfiguration config = ((ItemEnergyGunBase)item.getItem()).getConfig(item);
//			String name = config.modeName;
//			switch(name) {
//				case "weapon.elecGun.glass_cannon.radio": color = 0xaa2200; freq = 0.5; break;
//				case "weapon.elecGun.glass_cannon.micro": color = 0xdc221f; freq = 1; break;
//				case "weapon.elecGun.glass_cannon.ir": color = 0xfc3d3a; freq = 1.5; break;
//				case "weapon.elecGun.glass_cannon.visible": color = 0x8fe325; freq = 2; break;
//				case "weapon.elecGun.glass_cannon.uv": color = 0x37d5f3; freq = 2.5; break;
//				case "weapon.elecGun.glass_cannon.xray": color = 0x2542fd; freq = 3; break;
//				case "weapon.elecGun.glass_cannon.gamma": color = 0xdc20f3; freq = 3.5; break;
//			}
//		}
		
		float px = 0.0625F;
		GL11.glTranslatef(-2F, px * 18, -px * 14);
		GL11.glRotatef(90, 0, 1, 0);
		GL11.glTranslatef(-0.7F, -0.86F, -0.33F);
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawing(GL11.GL_QUADS);
		
		int sub = 32;
		double width = px * 21.25;
		double len = width / sub;
		double time = System.currentTimeMillis() / -100D;
		double amplitude = 0.075;
		
		tess.setColorOpaque_I(color);
		
		for(int i = 0; i < sub; i++) {
			double h0 = Math.sin(freq * i * 0.5 + time) * amplitude;
			double h1 = Math.sin(freq * (i + 1) * 0.5 + time) * amplitude;
			tess.addVertex(0, -px * 0.25 + h1, len * (i + 1));
			tess.addVertex(0, px * 0.25 + h1, len * (i + 1));
			tess.addVertex(0, px * 0.25 + h0, len * i);
			tess.addVertex(0, -px * 0.25 + h0, len * i);
		}
		tess.setColorOpaque_F(1F, 1F, 1F);
		
		tess.draw();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();

		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
		
	}

}
