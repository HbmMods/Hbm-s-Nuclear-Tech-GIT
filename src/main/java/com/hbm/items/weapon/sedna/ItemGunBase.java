package com.hbm.items.weapon.sedna;

import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.items.IKeybindReceiver;
import com.hbm.util.EnumUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemGunBase implements IKeybindReceiver {

	public static final String KEY_TIMER = "timer";
	public static final String KEY_STATE = "state";

	public static enum GunState {
		IDLE,		//gun can be fired or reloaded
		WINDUP,		//fire button is down, added delay before fire
		JUST_FIRED,	//gun has been fired, cooldown
		RELOADING	//gun is currently reloading
	}
	
	@Override
	public void handleKeybind(EntityPlayer player, ItemStack stack, EnumKeybind keybind, boolean state) {
		
	}

	// GUN STATE TIMER //
	public static int getTimer(ItemStack stack) { return getValueInt(stack, KEY_TIMER); }
	public static void setTimer(ItemStack stack, int value) { setValueInt(stack, KEY_TIMER, value); }

	// GUN STATE //
	public static GunState getState(ItemStack stack) { return EnumUtil.grabEnumSafely(GunState.class, getValueByte(stack, KEY_STATE)); }
	public static void setState(ItemStack stack, GunState value) { setValueByte(stack, KEY_STATE, (byte) value.ordinal()); }
	
	
	/// UTIL ///
	public static int getValueInt(ItemStack stack, String name) { if(stack.hasTagCompound()) stack.getTagCompound().getInteger(name); return 0; }
	public static void setValueInt(ItemStack stack, String name, int value) { if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound(); stack.getTagCompound().setInteger(name, value); }
	
	public static byte getValueByte(ItemStack stack, String name) { if(stack.hasTagCompound()) stack.getTagCompound().getByte(name); return 0; }
	public static void setValueByte(ItemStack stack, String name, byte value) { if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound(); stack.getTagCompound().setByte(name, value); }
}
