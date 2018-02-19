package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.GunB92;
import com.hbm.items.weapon.GunBoltAction;
import com.hbm.items.weapon.GunLeverAction;
import com.hbm.items.weapon.GunLeverActionS;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelB92;
import com.hbm.render.model.ModelBoltAction;
import com.hbm.render.model.ModelDash;
import com.hbm.render.model.ModelDefabricator;
import com.hbm.render.model.ModelEuthanasia;
import com.hbm.render.model.ModelHP;
import com.hbm.render.model.ModelJack;
import com.hbm.render.model.ModelLeverAction;
import com.hbm.render.model.ModelPip;
import com.hbm.render.model.ModelSpark;
import com.hbm.render.model.ModelTwiGun;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class ItemRenderGunAnim implements IItemRenderer {

	protected ModelLeverAction leveraction;
	protected ModelBoltAction boltaction;
	protected ModelB92 b92;
	
	public ItemRenderGunAnim() {
		leveraction = new ModelLeverAction();
		boltaction = new ModelBoltAction();
		b92 = new ModelB92();
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

				if(item.getItem() == ModItems.gun_lever_action || item.getItem() == ModItems.gun_lever_action_sonata)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelLeverAction.png"));
				if(item.getItem() == ModItems.gun_bolt_action)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelBoltActionDark.png"));
				if(item.getItem() == ModItems.gun_lever_action_dark)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelLeverActionDark.png"));
				if(item.getItem() == ModItems.gun_bolt_action_green)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelBoltActionGreen.png"));
				if(item.getItem() == ModItems.gun_b92)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelB92SM.png"));
				
				GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-0.5F, 0.0F, -0.2F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				GL11.glTranslatef(-0.2F, -0.1F, -0.1F);

				if(item.getItem() == ModItems.gun_lever_action_sonata) {
					GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(15F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(2.3F, 0.2F, 0.8F);
				}
				
				if((item.getItem() == ModItems.gun_lever_action || item.getItem() == ModItems.gun_lever_action_dark) && GunLeverAction.getRotationFromAnim(item) > 0) {
					GL11.glRotatef(GunLeverAction.getRotationFromAnim(item) * 25, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(GunLeverAction.getOffsetFromAnim(item) * -1.5F, 0.0F, 0.0F);
				}
				
				if((item.getItem() == ModItems.gun_lever_action_sonata) && GunLeverActionS.getRotationFromAnim(item) > 0) {
					GL11.glRotatef(GunLeverActionS.getRotationFromAnim(item) * -25, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(GunLeverActionS.getOffsetFromAnim(item) * 1.5F, 0.0F, 0.0F);
					GL11.glTranslatef(0.0F, GunLeverActionS.getOffsetFromAnim(item) * -1.5F, 0.0F);
				}
				
				if((item.getItem() == ModItems.gun_bolt_action || item.getItem() == ModItems.gun_bolt_action_green) && GunBoltAction.getRotationFromAnim(item) > 0) {
					GL11.glRotatef(GunBoltAction.getRotationFromAnim(item) * 10, 2.5F, 0.0F, 1.5F);
					GL11.glTranslatef(GunBoltAction.getOffsetFromAnim(item) * -1.75F, 0.0F, 0.0F);
				}
				
				if(item.getItem() == ModItems.gun_b92 && GunB92.getRotationFromAnim(item) > 0) {
					float off = GunB92.getRotationFromAnim(item) * 2;
					GL11.glRotatef(GunB92.getRotationFromAnim(item) * -90, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(off * -0.5F, off * -0.5F, 0.0F);
				}

				if(item.getItem() == ModItems.gun_lever_action || item.getItem() == ModItems.gun_lever_action_dark)
					leveraction.renderAnim((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunLeverAction.getRotationFromAnim(item));
				if(item.getItem() == ModItems.gun_lever_action_sonata)
					leveraction.renderAnim((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunLeverActionS.getRotationFromAnim(item));
				if(item.getItem() == ModItems.gun_bolt_action || item.getItem() == ModItems.gun_bolt_action_green)
					boltaction.renderAnim((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunBoltAction.getLevRotationFromAnim(item), GunBoltAction.getTransFromAnim(item));
				if(item.getItem() == ModItems.gun_b92)
					b92.renderAnim((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunB92.getTransFromAnim(item));
				
			GL11.glPopMatrix();
			break;
		case EQUIPPED:
		case ENTITY:
			GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_CULL_FACE);
				if(item.getItem() == ModItems.gun_lever_action || item.getItem() == ModItems.gun_lever_action_sonata)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelLeverAction.png"));
				if(item.getItem() == ModItems.gun_bolt_action)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelBoltActionDark.png"));
				if(item.getItem() == ModItems.gun_lever_action_dark)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelLeverActionDark.png"));
				if(item.getItem() == ModItems.gun_bolt_action_green)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelBoltActionGreen.png"));
				if(item.getItem() == ModItems.gun_b92)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelB92SM.png"));
				
				GL11.glRotatef(-200.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(75.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.0F, -0.2F, -0.5F);
				GL11.glRotatef(-5.0F, 0.0F, 0.0F, 1.0F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				GL11.glTranslatef(-0.3F, -0.4F, 0.15F);

				if(item.getItem() == ModItems.gun_lever_action_sonata) {
					GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(25F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(2.3F, 0.2F, 0.8F);
				}

				if(item.getItem() == ModItems.gun_lever_action || item.getItem() == ModItems.gun_lever_action_dark)
					leveraction.renderAnim((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunLeverAction.getRotationFromAnim(item));
				if(item.getItem() == ModItems.gun_lever_action_sonata)
					leveraction.renderAnim((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunLeverActionS.getRotationFromAnim(item));
				if(item.getItem() == ModItems.gun_bolt_action || item.getItem() == ModItems.gun_bolt_action_green)
					boltaction.renderAnim((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunBoltAction.getLevRotationFromAnim(item), GunBoltAction.getTransFromAnim(item));
				if(item.getItem() == ModItems.gun_b92)
					b92.renderAnim((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunB92.getTransFromAnim(item));
			GL11.glPopMatrix();
		default: break;
		}
	}
}
