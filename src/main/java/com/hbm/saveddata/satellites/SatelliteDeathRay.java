package com.hbm.saveddata.satellites;

import java.util.Locale;

import com.hbm.entity.logic.EntityDeathBlast;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class SatelliteDeathRay extends SatelliteBase {
	
	public static final String CMD_FIRE = "fire";
	public static final String CMD_CANFIRE = "canfire";
	
	public static final int CHARGE_TIME = 5 * 60 * 20;
	
	public long lastShot;
	
	public SatelliteDeathRay() { }

	@Override public String getType() { return "ORBITAL_FUN_PLATFORM_:)"; }
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("lastShot", lastShot);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		lastShot = nbt.getLong("lastShot");
	}

	@Override
	public void onCommandImpl(World world, String... cmd) {
		if(cmd.length <= 0) return;
		
		if(cmd[0].equals(CMD_FIRE)) {
			deathBlast(world, targetX, targetZ);
			return;
		}
		
		if(cmd[0].equals(CMD_CANFIRE)) {
			this.tx = (lastShot + CHARGE_TIME < world.getTotalWorldTime()) + "";
			this.tx = this.tx.toUpperCase(Locale.US);
			return;
		}
	}

	@Override
	public void onCoordAction(World world, EntityPlayer player, int x, int y, int z) {
		this.setTarget(x, z);
		this.deathBlast(world, targetX, targetZ);
	}
	
	public void deathBlast(World world, int x, int z) {

		if(lastShot + CHARGE_TIME < world.getTotalWorldTime()) {
			lastShot = world.getTotalWorldTime();

			int y = world.getHeightValue(x, z);

			EntityDeathBlast blast = new EntityDeathBlast(world);
			blast.posX = x;
			blast.posY = y;
			blast.posZ = z;

			world.spawnEntityInWorld(blast);
		}
	}
}
