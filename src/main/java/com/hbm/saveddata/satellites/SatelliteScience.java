package com.hbm.saveddata.satellites;

import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemDrive.EnumDriveType;
import com.hbm.items.special.ItemSatellite.EnumSatType;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class SatelliteScience extends SatelliteBase {
	
	public static final int COOLDOWN = 15 * 60 * 20;
	
	public long lastScience;

	@Override
	public String getType() {
		return "SCIENCE_PROBE";
	}
	
	@Override
	public boolean hasData(World world) {
		if(super.hasData(world)) return true;
		
		if(world.getTotalWorldTime() > this.lastScience + COOLDOWN) {
			this.produceData(EnumDriveType.DISK_EMPTY, EnumDriveType.DISK_FLIGHTDATA);
			this.lastScience = world.getTotalWorldTime();
		}
		
		return super.hasData(world);
	}
	
	@Override
	public IChatComponent[] getInfo(World world) {
		return new IChatComponent[] {
				new ChatComponentTranslation(ModItems.satellite.getUnlocalizedName(new ItemStack(ModItems.satellite, 1, EnumSatType.SCIENCE.ordinal())) + ".name")
		};
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("lastScience", lastScience);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		lastScience = nbt.getLong("lastScience");
	}
}
