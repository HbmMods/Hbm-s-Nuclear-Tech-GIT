package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.GunB92;
import com.hbm.items.weapon.GunLeverActionS;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelB92;
import com.hbm.render.model.ModelB93;
import com.hbm.render.model.ModelLeverAction;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderGunAnim implements IItemRenderer {

	protected ModelLeverAction flippedGun;
	protected ModelB92 b92;
	protected ModelB93 b93;
	
	public ItemRenderGunAnim() {
		flippedGun = new ModelLeverAction();
		b92 = new ModelB92();
		b93 = new ModelB93();
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

				if(item.getItem() == ModItems.gun_lever_action_sonata)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelLeverAction.png"));
				if(item.getItem() == ModItems.gun_b92)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelB92SM.png"));
				if(item.getItem() == ModItems.gun_b93)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelB93.png"));
				
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
				
				if((item.getItem() == ModItems.gun_lever_action_sonata) && GunLeverActionS.getRotationFromAnim(item) > 0) {
					GL11.glRotatef(GunLeverActionS.getRotationFromAnim(item) * -25, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(GunLeverActionS.getOffsetFromAnim(item) * 1.5F, 0.0F, 0.0F);
					GL11.glTranslatef(0.0F, GunLeverActionS.getOffsetFromAnim(item) * -1.5F, 0.0F);
				}
				
				if(item.getItem() == ModItems.gun_b92 && GunB92.getRotationFromAnim(item) > 0) {
					float off = GunB92.getRotationFromAnim(item) * 2;
					GL11.glRotatef(GunB92.getRotationFromAnim(item) * -90, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(off * -0.5F, off * -0.5F, 0.0F);
				}
				
				if(item.getItem() == ModItems.gun_b93 && GunB92.getRotationFromAnim(item) > 0) {
					float off = GunB92.getRotationFromAnim(item) * 2;
					GL11.glRotatef(GunB92.getRotationFromAnim(item) * -90, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(off * -0.5F, off * -0.5F, 0.0F);
				}
				
				if(item.getItem() == ModItems.gun_lever_action_sonata)
					flippedGun.renderAnim((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunLeverActionS.getRotationFromAnim(item));
				if(item.getItem() == ModItems.gun_b92)
					b92.renderAnim((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunB92.getTransFromAnim(item));
				if(item.getItem() == ModItems.gun_b93)
					b93.renderAnim((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunB92.getTransFromAnim(item));
				
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
				if(item.getItem() == ModItems.gun_bolt_action_saturnite)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelBoltActionSaturnite.png"));
				if(item.getItem() == ModItems.gun_b92)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelB92SM.png"));
				if(item.getItem() == ModItems.gun_b93)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelB93.png"));
				
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

				if(item.getItem() == ModItems.gun_lever_action_sonata)
					flippedGun.renderAnim((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunLeverActionS.getRotationFromAnim(item));
				if(item.getItem() == ModItems.gun_b92)
					b92.renderAnim((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunB92.getTransFromAnim(item));
				if(item.getItem() == ModItems.gun_b93)
					b93.renderAnim((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunB92.getTransFromAnim(item));
			GL11.glPopMatrix();
		default: break;
		}
	}
}
