package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponNovac implements IItemRenderer {
	
	public ItemRenderWeaponNovac() { }

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
		
		GL11.glEnable(GL11.GL_CULL_FACE);

		if(item.getItem() == ModItems.gun_revolver_nopip) Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.novac_tex);
		if(item.getItem() == ModItems.gun_revolver_pip) Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.lil_pip_tex);
		if(item.getItem() == ModItems.gun_revolver_blackjack) Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.blackjack_tex);
		if(item.getItem() == ModItems.gun_revolver_silver) Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.lent_gun_tex);
		if(item.getItem() == ModItems.gun_revolver_red) Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.red_key_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.4D;
			GL11.glRotated(25, 0, 0, 1);
			GL11.glRotated(80, 0, 1, 0);
			GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.0F, 0.0F, -1.25F);
			GL11.glScaled(s0, s0, s0);

			GL11.glShadeModel(GL11.GL_SMOOTH);

			HbmAnimations.applyRelevantTransformation("Body");
			ResourceManager.novac.renderPart("Body");

			GL11.glPushMatrix();
			HbmAnimations.applyRelevantTransformation("Cylinder");
			ResourceManager.novac.renderPart("Cylinder");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			HbmAnimations.applyRelevantTransformation("Hammer");
			ResourceManager.novac.renderPart("Hammer");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			HbmAnimations.applyRelevantTransformation("Trigger");
			ResourceManager.novac.renderPart("Trigger");
			GL11.glPopMatrix();

			if (item.getItem() == ModItems.gun_revolver_pip) {
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.novac_scope_tex);
				ResourceManager.novac.renderPart("Scope");
			}

			GL11.glShadeModel(GL11.GL_FLAT);
			
			GL11.glPopMatrix();
			
			return;
			
		case EQUIPPED:

			double scale = 0.35D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-5, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(10, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(15F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-1.25F, 0.25F, -1F);
			
			break;
			
		case ENTITY:

			double s1 = 0.3D;
			GL11.glScaled(s1, s1, s1);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 3D;
			GL11.glTranslated(7, 8, 0);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(135, 1, 0, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.novac.renderAllExcept("Scope");

		if(item.getItem() == ModItems.gun_revolver_pip) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.novac_scope_tex);
			ResourceManager.novac.renderPart("Scope");
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
