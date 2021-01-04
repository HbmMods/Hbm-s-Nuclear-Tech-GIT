package com.hbm.extprop;

import com.hbm.handler.HbmKeybinds.EnumKeybind;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class HbmExtendedProperties implements IExtendedEntityProperties {
	
	public static final String key = "NTM_EXT_PROPS";
	public EntityPlayer player;
	
	private boolean[] keysPressed = new boolean[EnumKeybind.values().length];
	
	public HbmExtendedProperties(EntityPlayer player) {
		this.player = player;
	}
	
	public static HbmExtendedProperties registerData(EntityPlayer player) {
		
		player.registerExtendedProperties(key, new HbmExtendedProperties(player));
		return (HbmExtendedProperties) player.getExtendedProperties(key);
	}
	
	public static HbmExtendedProperties getData(EntityPlayer player) {
		
		HbmExtendedProperties props = (HbmExtendedProperties) player.getExtendedProperties(key);
		return props != null ? props : registerData(player);
	}
	
	public boolean getKeyPressed(EnumKeybind key) {
		return keysPressed[key.ordinal()];
	}
	
	public void setKeyPressed(EnumKeybind key, boolean pressed) {
		keysPressed[key.ordinal()] = pressed;
	}

	@Override
	public void init(Entity entity, World world) { }

	@Override
	public void saveNBTData(NBTTagCompound compound) { }

	@Override
	public void loadNBTData(NBTTagCompound compound) { }
}
