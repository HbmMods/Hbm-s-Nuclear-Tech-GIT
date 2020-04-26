package com.hbm.main;

import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.lib.Library;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.util.RenderAccessoryUtility;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.sound.MovingSoundChopper;
import com.hbm.sound.MovingSoundChopperMine;
import com.hbm.sound.MovingSoundCrashing;
import com.hbm.sound.MovingSoundPlayerLoop;
import com.hbm.sound.MovingSoundXVL1456;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.hbm.sound.MovingSoundPlayerLoop.EnumHbmSound;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;

public class ModEventHandlerClient {
	
	@SubscribeEvent
	public void onOverlayRender(RenderGameOverlayEvent.Pre event) {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		if(event.type == ElementType.HOTBAR && player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemGunBase) {
			// Get gun data
			ItemGunBase gun = ((ItemGunBase)player.getHeldItem().getItem());
			GunConfiguration gcfg = gun.mainConfig;
			// Create variables for the returned values
			Item ammo = null;
			String count = null;
			String max = null;
			int durability = ItemGunBase.getItemWear(player.getHeldItem()) * 50;
			// Figure out what to use for bullet configuration
			BulletConfiguration bcfg;
			if (!gcfg.ammoless) {
				bcfg = BulletConfigSyncingUtil.pullConfig(gcfg.config.get(ItemGunBase.getMagType(player.getHeldItem(), gcfg)));
				if (gcfg.reloadType == gcfg.RELOAD_NONE) {
					ammo = ItemGunBase.getBeltType(player, player.getHeldItem());
				} else {
					ammo = bcfg.ammo;
				}
			} else {
				bcfg = BulletConfigSyncingUtil.pullConfig(gcfg.ammoType);
			}
			// Figure out what to display for "max"
			if (gcfg.ammoMaxValue != null) {
				max = gcfg.ammoMaxValue;
			} else {
				if (gcfg.reloadType == gcfg.RELOAD_NONE) {
					// TODO: MAKE THIS DISPLAY AS INFINITY
					max = "inf";
				} else {
					max = Integer.toString(gcfg.ammoCap);
				}
			}
			// Next figure out what kind of value to display for the actual ammo value
			if (gcfg.ammoDisplayTag != null) {
				count = Integer.toString(ItemGunBase.readNBT(player.getHeldItem(), gcfg.ammoDisplayTag));
			} else {
				if (gcfg.reloadType == gcfg.RELOAD_NONE) {
					if (gcfg.ammoless) {
						count = "inf";
					} else {
						count = Integer.toString(ItemGunBase.getBeltSize(player.getHeldItem(), player, bcfg));
					}
				} else {
					count = Integer.toString(ItemGunBase.getMag(player.getHeldItem()));
				}
			}
			// Display the actual values
			RenderScreenOverlay.renderAmmo(event.resolution, Minecraft.getMinecraft().ingameGUI, ammo, count, max, durability);
			/*
			if (!gcfg.ammoless) {
				BulletConfiguration bcfg = BulletConfigSyncingUtil.pullConfig(gcfg.config.get(ItemGunBase.getMagType(player.getHeldItem(), gcfg)));
			
				Item ammo = bcfg.ammo;
				int count = ItemGunBase.getMag(player.getHeldItem());
				int max = gcfg.ammoCap;
			
				if(gcfg.reloadType == gcfg.RELOAD_NONE) {
					ammo = ItemGunBase.getBeltType(player, player.getHeldItem());
					count = ItemGunBase.getBeltSize(player, ammo);
					max = -1;
				}
			
				int dura = ItemGunBase.getItemWear(player.getHeldItem()) * 50 / gcfg.durability;
			
				RenderScreenOverlay.renderAmmo(event.resolution, Minecraft.getMinecraft().ingameGUI, ammo, count, max, dura);
			} else {
				// Maybe render something later
			}*/
		}
		
		if(event.type == ElementType.HOTBAR) {
			
			if(player.inventory.hasItem(ModItems.geiger_counter)) {

				float rads = 0;

				rads = player.getEntityData().getFloat("hfr_radiation");
				
				RenderScreenOverlay.renderRadCounter(event.resolution, rads, Minecraft.getMinecraft().ingameGUI);
			}
		}
		
		if(event.type == ElementType.CROSSHAIRS && player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IHoldableWeapon && MainRegistry.enableCrosshairs) {
			event.setCanceled(true);
			
			if(!(player.getHeldItem().getItem() instanceof ItemGunBase && ((ItemGunBase)player.getHeldItem().getItem()).mainConfig.hasSights && player.isSneaking()))
				RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, ((IHoldableWeapon)player.getHeldItem().getItem()).getCrosshair());
			
		}
	}
	
	@SubscribeEvent
	public void preRenderEvent(RenderPlayerEvent.Pre event) {
		
		RenderPlayer renderer = event.renderer;
		AbstractClientPlayer player = (AbstractClientPlayer)event.entityPlayer;
		
		ResourceLocation cloak = RenderAccessoryUtility.getCloakFromPlayer(player);
		
		if(cloak != null)
			player.func_152121_a(Type.CAPE, cloak);
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IHoldableWeapon) {
			renderer.modelBipedMain.aimedBow = true;
			renderer.modelArmor.aimedBow = true;
			renderer.modelArmorChestplate.aimedBow = true;
		}
	}

	@SubscribeEvent
	public void clickHandler(MouseEvent event) {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemGunBase) {
			
			if(event.button == 0)
				event.setCanceled(true);
			
			ItemGunBase item = (ItemGunBase)player.getHeldItem().getItem();
			
			if(event.button == 0 && !item.m1 && !item.m2) {
				item.m1 = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 0));
				item.startActionClient(player.getHeldItem(), player.worldObj, player, true);
			}
			else if(event.button == 1 && !item.m2 && !item.m1) {
				item.m2 = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 1));
				item.startActionClient(player.getHeldItem(), player.worldObj, player, false);
			}
		}

	}

	@Spaghetti("please get this shit out of my face")
	@SubscribeEvent
	public void onPlaySound(PlaySoundEvent17 e) {
		ResourceLocation r = e.sound.getPositionedSoundLocation();

		WorldClient wc = Minecraft.getMinecraft().theWorld;
		
		//Alright, alright, I give the fuck up, you've wasted my time enough with this bullshit. You win.
		//A winner is you.
		//Conglaturations.
		//Fuck you.

		if(r.toString().equals("hbm:misc.nullTau") && Library.getClosestPlayerForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2) != null)
		{
			EntityPlayer ent = Library.getClosestPlayerForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2);
			
			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundXVL1456(new ResourceLocation("hbm:weapon.tauChargeLoop2"), ent, EnumHbmSound.soundTauLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).setPitch(0.5F);
			} else {
				if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).getPitch() < 1.5F)
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).setPitch(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).getPitch() + 0.01F);
			}
		}
		
		if(r.toString().equals("hbm:misc.nullChopper") && Library.getClosestChopperForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2) != null)
		{
			EntityHunterChopper ent = Library.getClosestChopperForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2);
			
			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundChopperLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundChopper(new ResourceLocation("hbm:entity.chopperFlyingLoop"), ent, EnumHbmSound.soundChopperLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundChopperLoop).setVolume(10.0F);
			}
		}
		
		if(r.toString().equals("hbm:misc.nullCrashing") && Library.getClosestChopperForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2) != null)
		{
			EntityHunterChopper ent = Library.getClosestChopperForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2);
			
			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundCrashingLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundCrashing(new ResourceLocation("hbm:entity.chopperCrashingLoop"), ent, EnumHbmSound.soundCrashingLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundCrashingLoop).setVolume(10.0F);
			}
		}
		
		if(r.toString().equals("hbm:misc.nullMine") && Library.getClosestMineForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2) != null)
		{
			EntityChopperMine ent = Library.getClosestMineForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2);
			
			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundMineLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundChopperMine(new ResourceLocation("hbm:entity.chopperMineLoop"), ent, EnumHbmSound.soundMineLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundMineLoop).setVolume(10.0F);
			}
		}

		for(MovingSoundPlayerLoop sounds : MovingSoundPlayerLoop.globalSoundList)
		{
			if(!sounds.init || sounds.isDonePlaying()) {
				sounds.init = true;
				sounds.setDone(false);
				Minecraft.getMinecraft().getSoundHandler().playSound(sounds);
			}
		}
	}
}
