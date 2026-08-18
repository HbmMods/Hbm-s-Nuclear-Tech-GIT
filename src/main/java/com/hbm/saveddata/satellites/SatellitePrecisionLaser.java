package com.hbm.saveddata.satellites;

import java.util.Locale;

import com.hbm.entity.logic.EntityOrbitalLaser;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemSatellite.EnumSatType;

import api.hbm.redstoneoverradio.IRORInteractive;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class SatellitePrecisionLaser extends SatelliteBase {
	
	public static final String CMD_FIRE = "fire";
	public static final String CMD_CANFIRE = "canfire";
	public static final String CMD_SETENTITYTARGET = "setentitytarget";
	
	public static final int MAX_TARGET_RANGE = 1_000;
	public static final int CHARGE_TIME = 5 * 20;
	
	public long lastShot;
	public int targetedEntity = -1;
	
	public SatellitePrecisionLaser() { }

	@Override public String getType() { return "ORBITAL_TATOO_REMOVER"; }
	
	@Override
	public IChatComponent[] getInfo(World world) {
		
		boolean canFire = lastShot + CHARGE_TIME < world.getTotalWorldTime();
		int cooldown = (int) ((lastShot + CHARGE_TIME) - world.getTotalWorldTime());
		
		return new IChatComponent[] {
				new ChatComponentTranslation(ModItems.satellite.getUnlocalizedName(new ItemStack(ModItems.satellite, 1, EnumSatType.PRECISION_LASER.ordinal())) + ".name"),
				canFire ? new ChatComponentTranslation("satellite.ready") : new ChatComponentTranslation("satellite.cooldown", cooldown / 20 + "s"),
		};
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("lastShot", lastShot);
		nbt.setInteger("targetedEntity", targetedEntity);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		lastShot = nbt.getLong("lastShot");
		targetedEntity = nbt.getInteger("targetedEntity");
	}

	@Override
	public void onCommandImpl(World world, String... cmd) {
		if(cmd.length <= 0) return;
		
		if(cmd[0].equals(CMD_FIRE)) {
			
			if(this.targetedEntity != -1) {
				Entity e = world.getEntityByID(this.targetedEntity);
				this.targetedEntity = -1;
				
				if(e == null || e.isDead) return;
				
				int x = (int) Math.floor(e.posX);
				int z = (int) Math.floor(e.posZ);
				
				double dX = x - targetX;
				double dZ = z - targetZ;
				
				if(dX * dX + dZ * dZ <= MAX_TARGET_RANGE * MAX_TARGET_RANGE) {
					this.deathBlast(world, e.posX, e.posY, e.posZ);
					return;
				}
			}
			
			deathBlast(world, targetX, targetZ);
			return;
		}
		
		if(cmd[0].equals(CMD_CANFIRE)) {
			this.tx = (lastShot + CHARGE_TIME < world.getTotalWorldTime()) + "";
			this.tx = this.tx.toUpperCase(Locale.US);
			return;
		}
		
		if(cmd[0].equals(CMD_SETENTITYTARGET) && cmd.length == 2) {
			this.targetedEntity = IRORInteractive.parseInt(cmd[1]);
			return;
		}
	}

	@Override
	public void onCoordAction(World world, EntityPlayer player, int x, int y, int z) {
		this.setTarget(x, z);
		this.deathBlast(world, targetX, targetZ);
	}
	
	public void deathBlast(World world, int x, int z) {
		int y = world.getHeightValue(x, z);
		deathBlast(world, x + 0.5, y, z + 0.5);
	}
	
	public void deathBlast(World world, double x, double y, double z) {

		if(lastShot + CHARGE_TIME < world.getTotalWorldTime()) {
			lastShot = world.getTotalWorldTime();

			EntityOrbitalLaser blast = new EntityOrbitalLaser(world);
			blast.posX = x;
			blast.posY = y;
			blast.posZ = z;
			blast.explode();

			world.spawnEntityInWorld(blast);
		}
	}
}
