package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelUzi;
import com.hbm.render.model.ModelUziBarrel;
import com.hbm.render.model.ModelUziSilencer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderUZI implements IItemRenderer {

	protected ModelUzi uzi;
	protected ModelUziBarrel barrel;
	protected ModelUziSilencer silencer;
	
	public ItemRenderUZI() {
		uzi = new ModelUzi();
		barrel = new ModelUziBarrel();
		silencer = new ModelUziSilencer();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
			return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type) {
		case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
				GL11.glScalef(0.25F, 0.25F, 0.25F);
				GL11.glTranslatef(-2.5F, -0.5F, -0.9F);
				
				GL11.glRotatef(-15.0F, 0.0F, 0.0F, 1.0F);

				if(item.getItem() == ModItems.gun_uzi || item.getItem() == ModItems.gun_uzi_silencer)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelUzi.png"));
				if(item.getItem() == ModItems.gun_uzi_saturnite || item.getItem() == ModItems.gun_uzi_saturnite_silencer)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelUziSaturnite.png"));
				
				uzi.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				
				if(item.getItem() == ModItems.gun_uzi || item.getItem() == ModItems.gun_uzi_saturnite) {
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelUziBarrel.png"));
					barrel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				}
				
				if(item.getItem() == ModItems.gun_uzi_silencer || item.getItem() == ModItems.gun_uzi_saturnite_silencer) {
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelUziSilencer.png"));
					silencer.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				}
				
			GL11.glPopMatrix();
			break;
		case EQUIPPED:
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glRotatef(-200.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(75.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-5.0F, 0.0F, 0.0F, 1.0F);
			
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			
			GL11.glTranslatef(0.2F, -0.8F, -1.0F);

			if(item.getItem() == ModItems.gun_uzi || item.getItem() == ModItems.gun_uzi_silencer)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelUzi.png"));
			if(item.getItem() == ModItems.gun_uzi_saturnite || item.getItem() == ModItems.gun_uzi_saturnite_silencer)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelUziSaturnite.png"));
			uzi.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			
			if(item.getItem() == ModItems.gun_uzi || item.getItem() == ModItems.gun_uzi_saturnite) {
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelUziBarrel.png"));
				barrel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			}
			
			if(item.getItem() == ModItems.gun_uzi_silencer || item.getItem() == ModItems.gun_uzi_saturnite_silencer) {
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelUziSilencer.png"));
				silencer.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			}
			
		GL11.glPopMatrix();
		break;
		case ENTITY:
			GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
				
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				
				GL11.glTranslatef(0.0F, -1.0F, 0.0F);

				if(item.getItem() == ModItems.gun_uzi || item.getItem() == ModItems.gun_uzi_silencer)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelUzi.png"));
				if(item.getItem() == ModItems.gun_uzi_saturnite || item.getItem() == ModItems.gun_uzi_saturnite_silencer)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelUziSaturnite.png"));
				uzi.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				
				if(item.getItem() == ModItems.gun_uzi || item.getItem() == ModItems.gun_uzi_saturnite) {
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelUziBarrel.png"));
					barrel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				}
				
				if(item.getItem() == ModItems.gun_uzi_silencer || item.getItem() == ModItems.gun_uzi_saturnite_silencer) {
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelUziSilencer.png"));
					silencer.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				}
				
			GL11.glPopMatrix();
		default: break;
		}
	}
}
