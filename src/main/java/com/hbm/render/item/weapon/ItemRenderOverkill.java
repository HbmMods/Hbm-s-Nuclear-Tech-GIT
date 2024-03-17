package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.GunFolly;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelDash;
import com.hbm.render.model.ModelDefabricator;
import com.hbm.render.model.ModelEuthanasia;
import com.hbm.render.model.ModelFolly;
import com.hbm.render.model.ModelHP;
import com.hbm.render.model.ModelJack;
import com.hbm.render.model.ModelLacunae;
import com.hbm.render.model.ModelPip;
import com.hbm.render.model.ModelSpark;
import com.hbm.render.model.ModelTwiGun;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderOverkill implements IItemRenderer {

	protected ModelJack powerJack;
	protected ModelSpark sparkPlug;
	protected ModelHP hppLaserjet;
	protected ModelEuthanasia euthanasia;
	protected ModelDefabricator defab;
	protected ModelDash dasher;
	protected ModelTwiGun rgottp;
	protected ModelPip pip;
	protected ModelLacunae lacunae;
	protected ModelFolly folly;

	public ItemRenderOverkill() {
		powerJack = new ModelJack();
		sparkPlug = new ModelSpark();
		hppLaserjet = new ModelHP();
		euthanasia = new ModelEuthanasia();
		defab = new ModelDefabricator();
		dasher = new ModelDash();
		rgottp = new ModelTwiGun();
		pip = new ModelPip();
		lacunae = new ModelLacunae();
		folly = new ModelFolly();
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
		float f = 0;

		if((Entity)data[1] instanceof EntityPlayer)
			f = ((EntityPlayer)data[1]).getItemInUseDuration();
		
		switch(type) {
		case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_CULL_FACE);
				
				if(item.getItem() == ModItems.gun_jack)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelJack.png"));
				if(item.getItem() == ModItems.gun_spark)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelSpark.png"));
				if(item.getItem() == ModItems.gun_hp)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelHP.png"));
				if(item.getItem() == ModItems.gun_euthanasia)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelEuthanasia.png"));
				if(item.getItem() == ModItems.gun_defabricator)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelDefabricator.png"));
				if(item.getItem() == ModItems.gun_dash)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelDash.png"));
				if(item.getItem() == ModItems.gun_twigun)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelTwiGun.png"));
				if(item.getItem() == ModItems.gun_revolver_pip)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPip.png"));
				if(item.getItem() == ModItems.gun_revolver_nopip)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPipNoScope.png"));
				if(item.getItem() == ModItems.gun_revolver_blackjack)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPipGrey.png"));
				if(item.getItem() == ModItems.gun_revolver_silver)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPipSilver.png"));
				if(item.getItem() == ModItems.gun_revolver_red)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPipRed.png"));
				if(item.getItem() == ModItems.gun_minigun)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelLacunae.png"));
				if(item.getItem() == ModItems.gun_lacunae)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelLacunaeReal.png"));
				if(item.getItem() == ModItems.gun_folly)
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelFolly.png"));

				GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-0.5F, 0.0F, -0.2F);
				//GL11.glScalef(2.0F, 2.0F, 2.0F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				//GL11.glTranslatef(-0.4F, -0.1F, 0.1F);
				GL11.glTranslatef(-0.2F, -0.1F, -0.1F);
				
				if(item.getItem() == ModItems.gun_defabricator)
					GL11.glTranslatef(0, 0.5F, 0.4F);
				
				if(item.getItem() == ModItems.gun_revolver_pip ||
						item.getItem() == ModItems.gun_revolver_nopip ||
						item.getItem() == ModItems.gun_revolver_blackjack ||
						item.getItem() == ModItems.gun_revolver_silver ||
						item.getItem() == ModItems.gun_revolver_red) {
					GL11.glScalef(0.60F, 0.60F, 0.60F);
					GL11.glTranslatef(0.0F, 0.0F, -0.1F);
					GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(5.0F, 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef(-0.2F, 0.0F, -0.2F);
				}
				
				if(item.getItem() == ModItems.gun_lacunae ||
						item.getItem() == ModItems.gun_minigun) {
					GL11.glRotatef(-15.0F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(180, 0, 1, 0);
					GL11.glTranslatef(0.5F, 0.3F, -0.2F);
				}
				
				if(item.getItem() == ModItems.gun_folly) {
					GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(5.0F, 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef(-0.2F, 0.0F, -0.2F);
				}
				
				if(item.getItem() == ModItems.gun_jack)
					powerJack.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == ModItems.gun_spark)
					sparkPlug.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == ModItems.gun_hp)
					hppLaserjet.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == ModItems.gun_euthanasia)
					euthanasia.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == ModItems.gun_defabricator)
					defab.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == ModItems.gun_dash)
					dasher.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == ModItems.gun_twigun)
					rgottp.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				if(item.getItem() == ModItems.gun_revolver_pip ||
						item.getItem() == ModItems.gun_revolver_nopip ||
						item.getItem() == ModItems.gun_revolver_blackjack ||
						item.getItem() == ModItems.gun_revolver_silver ||
						item.getItem() == ModItems.gun_revolver_red)
					pip.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				
				if(item.getItem() == ModItems.gun_lacunae ||
						item.getItem() == ModItems.gun_minigun)
					lacunae.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, ItemGunBase.readNBT(item, "rot"));
				
				if(item.getItem() == ModItems.gun_folly) {
					int state = GunFolly.getState(item);
					int time = GunFolly.getTimer(item);
					folly.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, state, time);
				}
				
			GL11.glPopMatrix();
			break;
		case EQUIPPED:
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_CULL_FACE);
			if(item.getItem() == ModItems.gun_jack)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelJack.png"));
			if(item.getItem() == ModItems.gun_spark)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelSpark.png"));
			if(item.getItem() == ModItems.gun_hp)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelHP.png"));
			if(item.getItem() == ModItems.gun_euthanasia)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelEuthanasia.png"));
			if(item.getItem() == ModItems.gun_defabricator)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelDefabricator.png"));
			if(item.getItem() == ModItems.gun_dash)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelDash.png"));
			if(item.getItem() == ModItems.gun_twigun)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelTwiGun.png"));
			if(item.getItem() == ModItems.gun_revolver_pip)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPip.png"));
			if(item.getItem() == ModItems.gun_revolver_nopip)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPipNoScope.png"));
			if(item.getItem() == ModItems.gun_revolver_blackjack)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPipGrey.png"));
			if(item.getItem() == ModItems.gun_revolver_silver)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPipSilver.png"));
			if(item.getItem() == ModItems.gun_revolver_red)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPipRed.png"));
			if(item.getItem() == ModItems.gun_minigun)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelLacunae.png"));
			if(item.getItem() == ModItems.gun_lacunae)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelLacunaeReal.png"));
			if(item.getItem() == ModItems.gun_folly)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelFolly.png"));
			
			GL11.glRotatef(-200.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(75.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0.0F, -0.2F, -0.5F);
			GL11.glRotatef(-5.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.5F, -0.2F, 0.0F);
			//GL11.glScalef(0.75F, 0.75F, 0.75F);
			GL11.glTranslatef(-1.4F, 0.0F, 0.0F);
			if(item.getItem() == ModItems.gun_jack)
				GL11.glTranslatef(0.3F, 0, 0);
			if(item.getItem() == ModItems.gun_spark)
				GL11.glTranslatef(0.4F, 0, 0);
			if(item.getItem() == ModItems.gun_hp)
				GL11.glTranslatef(0.5F, 0.2F, 0);
			if(item.getItem() == ModItems.gun_defabricator)
				GL11.glTranslatef(0.5F, 0.6F, -0.2F);

			if(item.getItem() == ModItems.gun_revolver_pip ||
					item.getItem() == ModItems.gun_revolver_nopip ||
					item.getItem() == ModItems.gun_revolver_blackjack ||
					item.getItem() == ModItems.gun_revolver_silver ||
					item.getItem() == ModItems.gun_revolver_red) {
				GL11.glScalef(0.60F, 0.60F, 0.60F);
				GL11.glTranslatef(0.7F, 0.3F, 0.0F);
			}
			if(item.getItem() == ModItems.gun_lacunae ||
					item.getItem() == ModItems.gun_minigun) {
				GL11.glRotatef(5.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(185, 0, 1, 0);
				GL11.glTranslatef(0.5F, 0.6F, 0.2F);
			}
			if(item.getItem() == ModItems.gun_folly) {
				GL11.glTranslatef(0.6F, 0.0F, 0.0F);
			}
			
			if(item.getItem() == ModItems.gun_jack)
				powerJack.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_spark)
				sparkPlug.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_hp)
				hppLaserjet.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_euthanasia)
				euthanasia.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_defabricator)
				defab.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_dash)
				dasher.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_twigun)
				rgottp.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_revolver_pip ||
					item.getItem() == ModItems.gun_revolver_nopip ||
					item.getItem() == ModItems.gun_revolver_blackjack ||
					item.getItem() == ModItems.gun_revolver_silver ||
					item.getItem() == ModItems.gun_revolver_red)
				pip.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			
			if(item.getItem() == ModItems.gun_lacunae ||
					item.getItem() == ModItems.gun_minigun)
				lacunae.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, f);
			
			if(item.getItem() == ModItems.gun_folly) {
				int state = GunFolly.getState(item);
				int time = GunFolly.getTimer(item);
				folly.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, state, time);
			}
			GL11.glPopMatrix();
			break;
		case ENTITY:
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_CULL_FACE);
			if(item.getItem() == ModItems.gun_jack)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelJack.png"));
			if(item.getItem() == ModItems.gun_spark)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelSpark.png"));
			if(item.getItem() == ModItems.gun_hp)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelHP.png"));
			if(item.getItem() == ModItems.gun_euthanasia)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelEuthanasia.png"));
			if(item.getItem() == ModItems.gun_defabricator)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelDefabricator.png"));
			if(item.getItem() == ModItems.gun_dash)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelDash.png"));
			if(item.getItem() == ModItems.gun_twigun)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelTwiGun.png"));
			if(item.getItem() == ModItems.gun_revolver_pip)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPip.png"));
			if(item.getItem() == ModItems.gun_revolver_nopip)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPipNoScope.png"));
			if(item.getItem() == ModItems.gun_revolver_blackjack)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPipGrey.png"));
			if(item.getItem() == ModItems.gun_revolver_silver)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPipSilver.png"));
			if(item.getItem() == ModItems.gun_revolver_red)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPipRed.png"));
			if(item.getItem() == ModItems.gun_minigun)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelLacunae.png"));
			if(item.getItem() == ModItems.gun_lacunae)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelLacunaeReal.png"));
			if(item.getItem() == ModItems.gun_folly)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelFolly.png"));

			
			GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);

			if(item.getItem() == ModItems.gun_revolver_pip ||
					item.getItem() == ModItems.gun_revolver_nopip ||
					item.getItem() == ModItems.gun_revolver_blackjack ||
					item.getItem() == ModItems.gun_revolver_silver ||
					item.getItem() == ModItems.gun_revolver_red) {
				GL11.glScalef(0.60F, 0.60F, 0.60F);
			}
			if(item.getItem() == ModItems.gun_calamity) {
				GL11.glScalef(0.75F, 0.75F, 0.75F);
			}
			if(item.getItem() == ModItems.gun_lacunae ||
					item.getItem() == ModItems.gun_minigun) {
				GL11.glTranslatef(0, -1, 0);
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			}
			
			if(item.getItem() == ModItems.gun_jack)
				powerJack.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_spark)
				sparkPlug.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_hp)
				hppLaserjet.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_euthanasia)
				euthanasia.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_defabricator)
				defab.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_dash)
				dasher.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_twigun)
				rgottp.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_revolver_pip ||
					item.getItem() == ModItems.gun_revolver_nopip ||
					item.getItem() == ModItems.gun_revolver_blackjack ||
					item.getItem() == ModItems.gun_revolver_silver ||
					item.getItem() == ModItems.gun_revolver_red)
				pip.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			
			if(item.getItem() == ModItems.gun_lacunae ||
					item.getItem() == ModItems.gun_minigun)
				lacunae.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, f);
			
			if(item.getItem() == ModItems.gun_folly) {
				int state = GunFolly.getState(item);
				int time = GunFolly.getTimer(item);
				folly.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, state, time);
			}
			GL11.glPopMatrix();
			break;
		default: break;
		}
	}
}
