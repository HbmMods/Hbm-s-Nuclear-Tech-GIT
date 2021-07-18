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
					MainRegistry.proxy.displayTooltip(EnumChatFormatting.GREEN + "Jetpack ON");
				else
					MainRegistry.proxy.displayTooltip(EnumChatFormatting.RED + "Jetpack OFF");
			}
			if(key == EnumKeybind.TOGGLE_HEAD) {
				this.enableHUD = !this.enableHUD;
				
				if(this.enableHUD)
					MainRegistry.proxy.displayTooltip(EnumChatFormatting.GREEN + "HUD ON");
				else
					MainRegistry.proxy.displayTooltip(EnumChatFormatting.RED + "HUD OFF");
			}
		}
		
		keysPressed[key.ordinal()] = pressed;
	}

	@Override
	public void init(Entity entity, World world) { }

	@Override
	public void saveNBTData(NBTTagCompound compound) { }

	@Override
	public void loadNBTData(NBTTagCompound compound) { }
}
