package com.hbm.extprop;

import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class HbmPlayerProps implements IExtendedEntityProperties {
	
	public static final String key = "NTM_EXT_PLAYER";
	public EntityPlayer player;
	
	public boolean enableHUD = true;
	public boolean enableBackpack = true;
	
	private boolean[] keysPressed = new boolean[EnumKeybind.values().length];
	
	public boolean dashActivated = true;
	
	public static final int dashCooldownLength = 5;
	public int dashCooldown = 0;
	
	public int totalDashCount = 0;
	public int stamina = 0;
	
	public HbmPlayerProps(EntityPlayer player) {
		this.player = player;
	}
	
	public static HbmPlayerProps registerData(EntityPlayer player) {
		
		player.registerExtendedProperties(key, new HbmPlayerProps(player));
		return (HbmPlayerProps) player.getExtendedProperties(key);
	}
	
	public static HbmPlayerProps getData(EntityPlayer player) {
		
		HbmPlayerProps props = (HbmPlayerProps) player.getExtendedProperties(key);
		return props != null ? props : registerData(player);
	}
	
	public boolean getKeyPressed(EnumKeybind key) {
		return keysPressed[key.ordinal()];
	}
	
	public boolean isJetpackActive() {
		return this.enableBackpack && getKeyPressed(EnumKeybind.JETPACK);
	}
	
	public void setKeyPressed(EnumKeybind key, boolean pressed) {
		
		if(!getKeyPressed(key) && pressed) {
			
			if(key == EnumKeybind.TOGGLE_JETPACK) {
				this.enableBackpack = !this.enableBackpack;
				
				if(this.enableBackpack)
					MainRegistry.proxy.displayTooltip(EnumChatFormatting.GREEN + "Jetpack ON", MainRegistry.proxy.ID_JETPACK);
				else
					MainRegistry.proxy.displayTooltip(EnumChatFormatting.RED + "Jetpack OFF", MainRegistry.proxy.ID_JETPACK);
			}
			if(key == EnumKeybind.TOGGLE_HEAD) {
				this.enableHUD = !this.enableHUD;
				
				if(this.enableHUD)
					MainRegistry.proxy.displayTooltip(EnumChatFormatting.GREEN + "HUD ON", MainRegistry.proxy.ID_HUD);
				else
					MainRegistry.proxy.displayTooltip(EnumChatFormatting.RED + "HUD OFF", MainRegistry.proxy.ID_HUD);
			}
		}
		
		keysPressed[key.ordinal()] = pressed;
	}
	
	public void setDashCooldown(int cooldown) {
		this.dashCooldown = cooldown;
		return;
	}
	
	public int getDashCooldown() {
		return this.dashCooldown;
	}
	
	public void setStamina(int stamina) {
		this.stamina = stamina;
		return;
	}
	
	public int getStamina() {
		return this.stamina;
	}
	
	public void setDashCount(int count) {
		this.totalDashCount = count;
		return;
	}
	
	public int getDashCount() {
		return this.totalDashCount;
	}

	@Override
	public void init(Entity entity, World world) { }

	@Override
	public void saveNBTData(NBTTagCompound compound) { }

	@Override
	public void loadNBTData(NBTTagCompound compound) { }
}
