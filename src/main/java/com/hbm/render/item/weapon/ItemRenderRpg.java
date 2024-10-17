package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelGustav;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderRpg implements IItemRenderer {

	protected ModelGustav swordModel;
	
	public ItemRenderRpg() {
		swordModel = new ModelGustav();
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
				if(item.getItem() == ModItems.gun_rpg)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelGustav.png"));
				if(item.getItem() == ModItems.gun_karl)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelGustavYellow.png"));
				
				GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				GL11.glTranslatef(0.4F, -1.0F, -0.7F);

				if(item.getItem() == ModItems.gun_panzerschreck) {
					GL11.glScalef(1.5F, 1.5F, 1.5F);
					
					if(Minecraft.getMinecraft().thePlayer.isSneaking())
						GL11.glTranslatef(-0.2F, -0.3F, -0.5F);
					else
						GL11.glTranslatef(-0.1F, 0.0F, 0.0F);
				} else {
					GL11.glTranslatef(0F, -0.1F, -0.4F);
				}
				
				GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(5.0F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-0.2F, 0.0F, -0.2F);
				
				if(item.getItem() == ModItems.gun_rpg)
					swordModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == ModItems.gun_karl)
					swordModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
		case EQUIPPED:
		case ENTITY:
			GL11.glPushMatrix();
				if(item.getItem() == ModItems.gun_rpg)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelGustav.png"));
				if(item.getItem() == ModItems.gun_karl)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelGustavYellow.png"));
				
				GL11.glRotatef(-200.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(75.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.0F, -0.2F, -0.5F);
				GL11.glRotatef(-5.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(0.9F, 0.0F, 0.1F);

				if(item.getItem() == ModItems.gun_panzerschreck) {
					GL11.glTranslatef(-0.5F, -0.1F, 0F);
					GL11.glScalef(1.5F, 1.5F, 1.5F);
				}
				
				if(item.getItem() == ModItems.gun_rpg)
					swordModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == ModItems.gun_karl)
					swordModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
		default: break;
		}
	}

}
