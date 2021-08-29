package com.hbm.extprop;

import java.util.Random;

import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.saveddata.TimeSavedData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class HbmPlayerProps implements IExtendedEntityProperties {
	
	public static final String key = "NTM_EXT_PLAYER";
	public EntityPlayer player;
	
	// Data
	private double age = 20D;
	private byte birthday = 1;
	private boolean hasAscended = false;
	
	private boolean[] keysPressed = new boolean[EnumKeybind.values().length];
	
	public HbmPlayerProps(EntityPlayer player) {
		this.player = player;
	}
	
	public static HbmPlayerProps registerData(EntityPlayer player) {
		
		player.registerExtendedProperties(key, new HbmPlayerProps(player));
		HbmPlayerProps props = (HbmPlayerProps) player.getExtendedProperties(key);
		props.age = getNewAge();
		return props;
	}
	
	public static HbmPlayerProps getData(EntityPlayer player) {
		
		HbmPlayerProps props = (HbmPlayerProps) player.getExtendedProperties(key);
		return props != null ? props : registerData(player);
	}
	
	public static double getAge(EntityPlayer player)
	{
		HbmPlayerProps props = (HbmPlayerProps) player.getExtendedProperties(key);
		return getData(player).age;
	}
	
	public static byte getBirthday(EntityPlayer player)
	{
		HbmPlayerProps props = (HbmPlayerProps) player.getExtendedProperties(key);
		return getData(player).birthday;
	}
	
	public static boolean hasAscended(EntityPlayer player)
	{
		HbmPlayerProps props = (HbmPlayerProps) player.getExtendedProperties(key);
		return getData(player).hasAscended;
	}
	
	public static void setAge(EntityPlayer player, double newAge, boolean increment)
	{
		if (increment)
			getData(player).age += newAge;
		else
			getData(player).age = newAge;
	}
	
	public boolean getKeyPressed(EnumKeybind key) {
		return keysPressed[key.ordinal()];
	}
	
	public void setKeyPressed(EnumKeybind key, boolean pressed) {
		keysPressed[key.ordinal()] = pressed;
	}

	public static double getNewAge()
	{
		return 20 + new Random().nextInt(5);
	}
	
	@Override
	public void init(Entity entity, World world) { }

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound props = new NBTTagCompound();
		props.setDouble("age", age);
		props.setByte("bithday", birthday);
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
				age = props.getDouble("age");
			else
				age = getNewAge();
			
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
