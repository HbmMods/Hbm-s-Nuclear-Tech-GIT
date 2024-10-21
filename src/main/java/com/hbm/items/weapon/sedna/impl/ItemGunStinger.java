package com.hbm.items.weapon.sedna.impl;

import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.hud.IHUDComponent;
import com.hbm.render.util.RenderScreenOverlay;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class ItemGunStinger extends ItemGunBaseNT {

	public static final String KEY_LOCKINGON = "lockingon";
	public static final String KEY_LOCKEDON = "lockedon";
	public static final String KEY_LOCKONTARGET = "lockontarget";
	public static final String KEY_LOCKONPROGRESS = "lockonprogress";

	public static float prevLockon;
	public static float lockon;
	
	public ItemGunStinger(GunConfig... cfg) {
		super(cfg);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {
		super.onUpdate(stack, world, entity, slot, isHeld);
		
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if(!world.isRemote && !isHeld && this.getIsLockingOn(stack)) {
				this.setIsLockingOn(stack, false);
			}
			
			this.prevLockon = this.lockon;
			int prevTarget = this.getLockonTarget(stack);
			if(isHeld && this.getIsLockingOn(stack) && this.getIsAiming(stack)) {
				int newLockonTarget = this.getLockonTarget(player);
				
				if(newLockonTarget == -1) {
					resetLockon(world, stack);
				} else {
					if(newLockonTarget != prevTarget) {
						resetLockon(world, stack);
						this.setLockonTarget(stack, newLockonTarget);
					}
					progressLockon(world, stack);
				}
			} else {
				resetLockon(world, stack);
			}
		}
	}
	
	public void resetLockon(World world, ItemStack stack) {
		if(world.isRemote) this.lockon = 0F;
		if(!world.isRemote) this.setLockonProgress(stack, 0);
	}
	
	public void progressLockon(World world, ItemStack stack) {
		if(world.isRemote) this.lockon += (1F / 100F);
		if(!world.isRemote) this.setLockonProgress(stack, this.getLockonProgress(stack) + 1);
	}
	
	public static int getLockonTarget(EntityPlayer player) {
		return -1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(Pre event, ElementType type, EntityPlayer player, ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		
		if(type == ElementType.CROSSHAIRS) {
			event.setCanceled(true);
			if(aimingProgress < 1F) return;
			RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, gun.getConfig(stack, 0).getCrosshair(stack));
			RenderScreenOverlay.renderStingerLockon(event.resolution, Minecraft.getMinecraft().ingameGUI);
		}
		
		int confNo = this.configs_DNA.length;
		
		for(int i = 0; i < confNo; i++) {
			IHUDComponent[] components = gun.getConfig(stack, i).getHUDComponents(stack);
			
			if(components != null) for(IHUDComponent component : components) {
				int bottomOffset = 0;
				component.renderHUDComponent(event, type, player, stack, bottomOffset, i);
				bottomOffset += component.getComponentHeight(player, stack);
			}
		}
	}

	public static boolean getIsLockingOn(ItemStack stack) { return getValueBool(stack, KEY_LOCKINGON); }
	public static void setIsLockingOn(ItemStack stack, boolean value) { setValueBool(stack, KEY_LOCKINGON, value); }
	public static boolean getIsLockedOn(ItemStack stack) { return getValueBool(stack, KEY_LOCKEDON); }
	public static void setIsLockedOn(ItemStack stack, boolean value) { setValueBool(stack, KEY_LOCKEDON, value); }
	public static int getLockonTarget(ItemStack stack) { return getValueInt(stack, KEY_LOCKONTARGET); }
	public static void setLockonTarget(ItemStack stack, int value) { setValueInt(stack, KEY_LOCKONTARGET, value); }
	public static int getLockonProgress(ItemStack stack) { return getValueInt(stack, KEY_LOCKONPROGRESS); }
	public static void setLockonProgress(ItemStack stack, int value) { setValueInt(stack, KEY_LOCKONPROGRESS, value); }
}
