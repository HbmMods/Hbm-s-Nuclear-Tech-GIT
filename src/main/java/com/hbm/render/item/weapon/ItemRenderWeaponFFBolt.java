package com.hbm.render.item.weapon;

import com.hbm.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;

public class ItemRenderWeaponFFBolt implements IItemRenderer {
	
	ResourceLocation texture;
	IModelCustom model;
	
	public ItemRenderWeaponFFBolt(IModelCustom model, ResourceLocation texture) {
		this.model = model;
		this.texture = texture;
	}

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
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		GL11.glShadeModel(GL11.GL_SMOOTH);
		boolean renderBolt = true;
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			if(item.getItem() == ModItems.gun_bolt_action_saturnite && Minecraft.getMinecraft().thePlayer.isSneaking()) {
				GL11.glPopMatrix();
				return;
			}
			
			double s0 = 0.5D;
			GL11.glTranslated(0.5, 0.25, -0.2);
			GL11.glScaled(s0, s0, s0);
			GL11.glRotated(15, 0, 0, 1);
			GL11.glRotated(20, 0, -1, 0);
			if(player.isSneaking()){
			if (item.getItem() != ModItems.gun_bolt_action_saturnite) {
				//GL11.glRotated(75, 0, -1, 0);
				GL11.glTranslated(-1, 0.6, -1.26);
				GL11.glRotated(10.5, 0, 0, 1);
				GL11.glRotated(14.68, 0, 1, 0);
			} else {
				GL11.glPopMatrix();
				return;
			}
		}
			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
			GL11.glTranslated(recoil[0] * -0.5, 0, 0);

			double[] pull = HbmAnimations.getRelevantTransformation("LEVER_PULL");
			double[] rotate = HbmAnimations.getRelevantTransformation("LEVER_ROTATE");
			
			GL11.glPushMatrix();
			GL11.glTranslated(pull[0], 0, 0);
			double heightOffset = 0.52D;
			GL11.glTranslated(0, heightOffset, 0);
			GL11.glRotated(rotate[0] * 35, -1, 0, 0);
			GL11.glTranslated(0, -heightOffset, 0);
			model.renderPart("Bolt");
			GL11.glPopMatrix();
			
			renderBolt = false;

			/*if(item.getItem() == ModItems.gun_bolt_action_saturnite) {
				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_LIGHTING);
	
				Tessellator tessellator = Tessellator.instance;
				int color = 0x00FF00;
	
				tessellator.startDrawing(3);
				tessellator.setColorOpaque_I(color);
				tessellator.addVertex(5, 0, 0);
				tessellator.addVertex(150, 0, 0);
				tessellator.draw();
	
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glPopMatrix();
			}*/
			
			break;
			
		case EQUIPPED:

			double scale = 0.25D;
			GL11.glRotated(-75, 0, 1, 0);
			GL11.glRotated(-10, 0, 0, 1);
			GL11.glRotated(10, 1, 0, 0);
			GL11.glTranslated(0.3, 0.15, -0.5);
			GL11.glScaled(scale, scale, scale);
			
			break;
			
		case ENTITY:

			double s1 = 0.25D;
			GL11.glScaled(s1, s1, s1);
			GL11.glRotated(90, 0, 1, 0);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 1.5D;
			GL11.glTranslated(10, 11, 0);
			GL11.glRotated(-135, 0, 0, 1);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}
		
		model.renderPart("Gun");
		if(renderBolt)
			model.renderPart("Bolt");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
