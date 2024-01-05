package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGunPip;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponLilMac implements IItemRenderer {
	
	public static final ResourceLocation lilmac_plume = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/lilmac_plume.png");

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
		
		//prevent rendering when using scope
		if(item.getItem() == ModItems.gun_revolver_pip && type == ItemRenderType.EQUIPPED_FIRST_PERSON && MainRegistry.proxy.me().isSneaking()) return;
		
		GL11.glPushMatrix();
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.1D;
			GL11.glRotated(25, 0, 0, 1);
			GL11.glTranslated(1.0, 0.25, -0.25);
			GL11.glRotated(170, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);
			double width = 0.5D;
			
			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
			double[] reloadLift = HbmAnimations.getRelevantTransformation("RELOAD_LIFT");
			double[] reloadJolt = HbmAnimations.getRelevantTransformation("RELOAD_JOLT");
			double[] equipSpin = HbmAnimations.getRelevantTransformation("ROTATE");
			GL11.glTranslated(2, 0, 0);
			GL11.glRotated(equipSpin[0], 0, 0, 1);
			GL11.glTranslated(-2, 0, 0);
			
			GL11.glShadeModel(GL11.GL_SMOOTH);
			
			GL11.glPushMatrix();
			Tessellator tess = Tessellator.instance;

			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_BLEND);
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
			GL11.glTranslated(-10, 2.25, 0);
			GL11.glTranslated(-recoil[2] * 3.5, -recoil[2] * 1.375, 0);
			
			if(ItemGunPip.smokeNodes.size() > 1 && equipSpin[0] == 0) {

				tess.startDrawingQuads();
				tess.setNormal(0F, 1F, 0F);
				
				for(int i = 0; i < ItemGunPip.smokeNodes.size() - 1; i++) {
					double[] node = ItemGunPip.smokeNodes.get(i);
					double[] past = ItemGunPip.smokeNodes.get(i + 1);
					
					tess.setColorRGBA_F(1F, 1F, 1F, (float) node[3]);
					tess.addVertex(node[0], node[1], node[2]);
					tess.setColorRGBA_F(1F, 1F, 1F, 0F);
					tess.addVertex(node[0], node[1], node[2] + width);
					tess.setColorRGBA_F(1F, 1F, 1F, 0F);
					tess.addVertex(past[0], past[1], past[2] + width);
					tess.setColorRGBA_F(1F, 1F, 1F, (float) past[3]);
					tess.addVertex(past[0], past[1], past[2]);
					
					tess.setColorRGBA_F(1F, 1F, 1F, (float) node[3]);
					tess.addVertex(node[0], node[1], node[2]);
					tess.setColorRGBA_F(1F, 1F, 1F, 0F);
					tess.addVertex(node[0], node[1], node[2] - width);
					tess.setColorRGBA_F(1F, 1F, 1F, 0F);
					tess.addVertex(past[0], past[1], past[2] - width);
					tess.setColorRGBA_F(1F, 1F, 1F, (float) past[3]);
					tess.addVertex(past[0], past[1], past[2]);
				}
				GL11.glDepthMask(false);
				tess.draw();
				GL11.glDepthMask(true);
			}
			
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);
			GL11.glEnable(GL11.GL_CULL_FACE);
			
			GL11.glPopMatrix();
			
			GL11.glTranslated(0, reloadLift[0] / -22D, 0);
			
			GL11.glTranslated(recoil[0], recoil[1], recoil[2]);
			GL11.glRotated(recoil[2] * 10, 0, 0, 1);
			GL11.glRotated(reloadLift[0], 0, 0, 1);
			GL11.glTranslated(reloadJolt[0], 0, 0);
			
			double[] reloadTilt = HbmAnimations.getRelevantTransformation("RELAOD_TILT");
			GL11.glRotated(reloadTilt[0], 1, 0, 0);

			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.lilmac_scope_tex);
			ResourceManager.lilmac.renderPart("Scope");
			
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.lilmac_tex);
			ResourceManager.lilmac.renderPart("Gun");

			double[] cylinderFlip = HbmAnimations.getRelevantTransformation("RELOAD_CYLINDER");
			GL11.glPushMatrix(); /// DRUM PUSH ///
			GL11.glRotated(cylinderFlip[0], 1, 0, 0);
			ResourceManager.lilmac.renderPart("Pivot");
			GL11.glTranslated(0, 1.75, 0);
			GL11.glRotated(HbmAnimations.getRelevantTransformation("DRUM")[2] * -60, 1, 0, 0);
			GL11.glTranslated(0, -1.75, 0);
			ResourceManager.lilmac.renderPart("Cylinder");
			double[] reloadBullets = HbmAnimations.getRelevantTransformation("RELOAD_BULLETS");
			GL11.glTranslated(reloadBullets[0], reloadBullets[1], reloadBullets[2]);
			if(HbmAnimations.getRelevantTransformation("RELOAD_BULLETS_CON")[0] != 1)
			ResourceManager.lilmac.renderPart("Bullets");
			ResourceManager.lilmac.renderPart("Casings");
			GL11.glPopMatrix(); /// DRUM POP ///
			
			GL11.glPushMatrix(); /// HAMMER ///
			GL11.glTranslated(4, 1.25, 0);
			GL11.glRotated(-30 + 30 * HbmAnimations.getRelevantTransformation("HAMMER")[2], 0, 0, 1);
			GL11.glTranslated(-4, -1.25, 0);
			ResourceManager.lilmac.renderPart("Hammer");
			GL11.glPopMatrix();
			
			GL11.glShadeModel(GL11.GL_FLAT);
			
			int flash = 75;
			if(System.currentTimeMillis() - ItemGunPip.lastShot < flash) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
				GL11.glPushMatrix();
				GL11.glTranslated(0.125, 2.25, 0);
				
				double fire = (System.currentTimeMillis() - ItemGunPip.lastShot) / (double) flash;
				
				double height = 4 * fire;
				double length = 15 * fire;
				double lift = 3 * fire;
				double offset = 1 * fire;
				double lengthOffset = 0.125;
				Minecraft.getMinecraft().renderEngine.bindTexture(lilmac_plume);
				tess.startDrawingQuads();
				tess.setNormal(0F, 1F, 0F);
				tess.setColorRGBA_F(1F, 1F, 1F, 1F);
				
				tess.addVertexWithUV(0, -height, -offset, 1, 1);
				tess.addVertexWithUV(0, height, -offset, 0, 1);
				tess.addVertexWithUV(0, height + lift, length - offset, 0 ,0);
				tess.addVertexWithUV(0, -height + lift, length - offset, 1, 0);

				tess.addVertexWithUV(0, height, offset, 0, 1);
				tess.addVertexWithUV(0, -height, offset, 1, 1);
				tess.addVertexWithUV(0, -height + lift, -length + offset, 1, 0);
				tess.addVertexWithUV(0, height + lift, -length + offset, 0 ,0);
				
				tess.addVertexWithUV(0, -height, -offset, 1, 1);
				tess.addVertexWithUV(0, height, -offset, 0, 1);
				tess.addVertexWithUV(lengthOffset, height, length - offset, 0 ,0);
				tess.addVertexWithUV(lengthOffset, -height, length - offset, 1, 0);

				tess.addVertexWithUV(0, height, offset, 0, 1);
				tess.addVertexWithUV(0, -height, offset, 1, 1);
				tess.addVertexWithUV(lengthOffset, -height, -length + offset, 1, 0);
				tess.addVertexWithUV(lengthOffset, height, -length + offset, 0 ,0);
				
				tess.draw();
				GL11.glPopMatrix();
				GL11.glDisable(GL11.GL_BLEND);
			}
			
			break;
			
		case EQUIPPED:

			double scale = 0.1D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotatef(15F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(100, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(15F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-3F, 1F, 4F);
			
			break;
			
		case ENTITY:

			double s1 = 0.075D;
			GL11.glScaled(s1, s1, s1);
			GL11.glTranslatef(0F, 1F, 0F);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			
			double s = 0.8D;
			GL11.glTranslated(8, 8, 0);
			GL11.glRotated(180, 0, 1, 0);
			GL11.glRotated(135, 0, 0, 1);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}

		if(type != ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.lilmac_scope_tex);
			ResourceManager.lilmac.renderPart("Scope");
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.lilmac_tex);
			ResourceManager.lilmac.renderPart("Gun");
			ResourceManager.lilmac.renderPart("Cylinder");
			ResourceManager.lilmac.renderPart("Bullets");
			ResourceManager.lilmac.renderPart("Casings");
			ResourceManager.lilmac.renderPart("Pivot");
			ResourceManager.lilmac.renderPart("Hammer");
			GL11.glShadeModel(GL11.GL_FLAT);
		}
		
		GL11.glPopMatrix();
	}
}
