package com.hbm.saveddata.satellites;

import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemDrive.EnumDriveType;
import com.hbm.items.special.ItemSatellite.EnumSatType;
import com.hbm.util.BobMathUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class SatelliteScience extends SatelliteBase {
	
	public static final int COOLDOWN = 15 * 60 * 20;
	public long lastScience;
	
	public static final int SENSOR_DURATION = 100 * 60 * 60 * 20; // 100 fucking hour recipe
	public int sensorProgress;
	public int sensorCount;

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
	public void onPartDelivered(World world, ItemStack part) {
		
		if(part.getItem() == ModItems.satellite && part.getItemDamage() == EnumSatType.SCIENCE_SENSOR.ordinal()) {
			this.sensorCount++;
			this.markDirty();
		}
	}

	@Override
	public void onUpdateTick(World world) {
		
		if(this.sensorProgress < SENSOR_DURATION) {
			this.sensorProgress += this.sensorCount;
		} else {
			this.sensorProgress = 0;
			this.produceData(EnumDriveType.DISK_EMPTY, EnumDriveType.DISK_ORBITDATA);
			this.markDirty();
		}
	}
	
	@Override
	public IChatComponent[] getInfo(World world) {
		
		int cooldown = (int) ((lastScience + COOLDOWN) - world.getTotalWorldTime());
		int seconds = cooldown / 20;
		
		return new IChatComponent[] {
				new ChatComponentTranslation(ModItems.satellite.getUnlocalizedName(new ItemStack(ModItems.satellite, 1, EnumSatType.SCIENCE.ordinal())) + ".name"),
				cooldown <= 0 ? new ChatComponentTranslation("satellite.ready") : new ChatComponentTranslation("satellite.cooldown", (seconds / 60) + "m" + (seconds % 60) + "s"),
				this.sensorCount > 0 ? new ChatComponentTranslation("satellite.sensors", this.sensorCount) : null,
				this.sensorCount > 0 ? new ChatComponentTranslation("satellite.pending", BobMathUtil.format(SENSOR_DURATION - sensorProgress)) : null,
				this.driveOutput == EnumDriveType.DISK_ORBITDATA ? new ChatComponentTranslation("satellite.data") : null
		};
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("lastScience", lastScience);
		nbt.setInteger("sensorProgress", sensorProgress);
		nbt.setInteger("sensorCount", sensorCount);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		lastScience = nbt.getLong("lastScience");
		sensorProgress = nbt.getInteger("sensorProgress");
		sensorCount = nbt.getInteger("sensorCount");
	}
}
