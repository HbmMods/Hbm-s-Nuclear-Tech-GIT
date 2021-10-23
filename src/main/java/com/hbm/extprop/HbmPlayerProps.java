package com.hbm.extprop;

import java.util.Random;

import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.TimeSavedData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class HbmPlayerProps implements IExtendedEntityProperties {
	
	public static final String key = "NTM_EXT_PLAYER";
	public EntityPlayer player;
	
	// Data
	private long age = 20;
	private byte birthday = 1;
	private long birthyear = 2280;
	private boolean hasAscended = false;
	public boolean enableHUD = true;
	public boolean enableBackpack = true;
	
	private boolean[] keysPressed = new boolean[EnumKeybind.values().length];
	
	public HbmPlayerProps(EntityPlayer player) {
		this.player = player;
	}
	
	public static HbmPlayerProps registerData(EntityPlayer player) {
		
		player.registerExtendedProperties(key, new HbmPlayerProps(player));
		HbmPlayerProps props = (HbmPlayerProps) player.getExtendedProperties(key);
		props.age = getNewAge();
		props.birthyear = TimeSavedData.getData(player.worldObj).getYear() - props.age;
		return props;
	}
	
	public static HbmPlayerProps getData(EntityPlayer player) {
		
		HbmPlayerProps props = (HbmPlayerProps) player.getExtendedProperties(key);
		return props != null ? props : registerData(player);
	}
	
	public static long getAge(EntityPlayer player)
	{
		return getData(player).age;
	}
	
	public static byte getBirthday(EntityPlayer player)
	{
		return getData(player).birthday;
	}
	
	public static long getBirthyear(EntityPlayer player)
	{
		return getData(player).birthyear;
	}
	
	public static boolean hasAscended(EntityPlayer player)
	{
		return getData(player).hasAscended;
	}
	
	public static void setAge(EntityPlayer player, long newAge, boolean increment)
	{
		if (increment)
			getData(player).age += newAge;
		else
			getData(player).age = newAge;
	}
	
	public boolean getKeyPressed(EnumKeybind key) {
		return keysPressed[key.ordinal()];
	}
	
	public static long getNewAge()
	{
		return 20 + new Random().nextInt(5);
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
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound props = new NBTTagCompound();
		props.setDouble("age", age);
		props.setByte("bithday", birthday);
		props.setLong("birthyear", birthyear);
		props.setBoolean("ascended", hasAscended);
		compound.setTag("HbmPlayerProps", props);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound props = (NBTTagCompound) compound.getTag("HbmPlayerProps");
		
		if (props != null)
		{
			if (props.hasKey("age"))
				age = props.getLong("age");
			else
				age = getNewAge();
			
			if (props.hasKey("birthyear"))
				birthyear = props.getLong("birthyear");
			else
				birthyear = player == null ? 2300 - age : TimeSavedData.getData(player.worldObj).getYear() - age;
			
			if (props.hasKey("birthday"))
				birthday = props.getByte("birthday");
			else
				birthday = 1;
			
			if (props.hasKey("ascended"))
				hasAscended = props.getBoolean("ascended");
			else
				hasAscended = false;
		}
	}
}
