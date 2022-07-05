package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.ItemGunBio;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderBioRevolver implements IItemRenderer {

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
		
		GL11.glEnable(GL11.GL_CULL_FACE);

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bio_revolver_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.1D;
			GL11.glRotated(25, 0, 0, 1);
			GL11.glTranslated(1.0, 0.25, -0.25);
			GL11.glRotated(80, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);
			double width = 0.3D;
			
			GL11.glPushMatrix();
			GL11.glTranslated(0.0, 2.0, 10.0);
			
			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
			GL11.glTranslated(0, -recoil[2] * 1.5, recoil[2]);
			
			if(ItemGunBio.smokeNodes.size() > 1) {
				
				Tessellator tess = Tessellator.instance;
				tess.startDrawingQuads();
				tess.setNormal(0F, 1F, 0F);
				
				for(int i = 0; i < ItemGunBio.smokeNodes.size() - 1; i++) {
					double[] node = ItemGunBio.smokeNodes.get(i);
					double[] past = ItemGunBio.smokeNodes.get(i + 1);
					
					tess.setColorRGBA_F(1F, 1F, 1F, (float) node[3]);
					tess.addVertex(node[0] - width, node[1], node[2]);
					tess.addVertex(node[0] + width, node[1], node[2]);
					tess.setColorRGBA_F(1F, 1F, 1F, (float) past[3]);
					tess.addVertex(past[0] + width, past[1], past[2]);
					tess.addVertex(past[0] - width, past[1], past[2]);
				}
				GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				tess.draw();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);
			}
			
			GL11.glPopMatrix();
			GL11.glTranslated(recoil[0], recoil[1], recoil[2]);
			GL11.glRotated(recoil[2] * 10, 1, 0, 0);
			
			double[] reloadMove = HbmAnimations.getRelevantTransformation("RELOAD_MOVE");
			GL11.glTranslated(reloadMove[0], reloadMove[1], reloadMove[2]);

			double[] reloadRot = HbmAnimations.getRelevantTransformation("RELOAD_ROT");
			GL11.glRotated(reloadRot[0], 1, 0, 0);
			GL11.glRotated(reloadRot[2], 0, 0, 1);
			GL11.glRotated(reloadRot[1], 0, 1, 0);
			
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ResourceManager.bio_revolver.renderPart("Grip");
			
			GL11.glPushMatrix(); /// FRONT PUSH ///
			GL11.glRotated(HbmAnimations.getRelevantTransformation("FRONT")[2], 1, 0, 0);
			ResourceManager.bio_revolver.renderPart("Barrel");
			GL11.glPushMatrix(); /// LATCH PUSH ///
			GL11.glTranslated(0, 2.3125, -0.875);
			GL11.glRotated(HbmAnimations.getRelevantTransformation("LATCH")[2], 1, 0, 0);
			GL11.glTranslated(0, -2.3125, 0.875);
			ResourceManager.bio_revolver.renderPart("Latch");
			GL11.glPopMatrix(); /// LATCH POP ///
			
			GL11.glPushMatrix(); /// DRUM PUSH ///
			GL11.glTranslated(0, 1, 0);
			GL11.glRotated(HbmAnimations.getRelevantTransformation("DRUM")[2] * 60, 0, 0, 1);
			GL11.glTranslated(0, -1, 0);
			GL11.glTranslated(0, 0, HbmAnimations.getRelevantTransformation("DRUM_PUSH")[2]);
			ResourceManager.bio_revolver.renderPart("Drum");
			GL11.glPopMatrix(); /// DRUM POP ///
			
			GL11.glPopMatrix(); /// FRONT POP ///
			
			GL11.glPushMatrix(); /// HAMMER ///
			GL11.glTranslated(0, 0, -4.5);
			GL11.glRotated(-45 + 45 * HbmAnimations.getRelevantTransformation("HAMMER")[2], 1, 0, 0);
			GL11.glTranslated(0, 0, 4.5);
			ResourceManager.bio_revolver.renderPart("Hammer");
			GL11.glPopMatrix();
			
			GL11.glShadeModel(GL11.GL_FLAT);
			
			break;
			
		case EQUIPPED:

			double scale = 0.125D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(10, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(15F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(4F, -2F, 5F);
			
			break;
			
		case ENTITY:

			double s1 = 0.1D;
			GL11.glScaled(s1, s1, s1);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 0.8D;
			GL11.glTranslated(8, 7, 0);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(-135, 1, 0, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}

		if(type != ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ResourceManager.bio_revolver.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		}
		
		GL11.glPopMatrix();
	}
}
