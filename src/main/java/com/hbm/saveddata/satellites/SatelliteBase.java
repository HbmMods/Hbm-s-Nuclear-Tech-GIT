package com.hbm.saveddata.satellites;

import com.hbm.tileentity.network.RTTYSystem;

import api.hbm.redstoneoverradio.IRORInteractive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public abstract class SatelliteBase {
	
	public static final String CHAN_SATLINK = "SAT_LINK";

	public static final String CMD_SETTARGET = "settarget";
	public static final String CMD_GETTARGET = "gettarget";
	public static final String CMD_GETTARGETX = "gettargetx";
	public static final String CMD_GETTARGETZ = "gettargetz";
	
	public int targetX;
	public int targetZ;
	
	public String tx = "";
	
	public int getID() {
		return XSatelliteRegistry.idToClass.inverse().get(this.getClass());
	}
	
	public abstract String getType();
	
	public abstract IChatComponent[] getInfo(World world);
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("targetX", targetX);
		nbt.setInteger("targetZ", targetZ);
		nbt.setString("tx", tx);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		this.targetX = nbt.getInteger("targetX");
		this.targetZ = nbt.getInteger("targetZ");
		this.tx = nbt.getString("tx");
	}
	
	/** When a satellite is created, i.e. this frequency is occupied for the first time */
	public void onOrbit(World world, double x, double y, double z) {
		setTarget((int) Math.floor(x), (int) Math.floor(z));
		
		RTTYSystem.broadcast(world, CHAN_SATLINK, "Established connection to " + getType() + " at " + targetX + " / " + targetZ);
	}
	
	/** For subsequent items sent under the same frequency as an existing satellite */
	public void onPartDelivered(World world, ItemStack part) { }
	
	public void onCommand(World world, String... cmd) {
		onCommandTarget(world, cmd);
		onCommandImpl(world, cmd);
	}
	
	public void onCommandTarget(World world, String... cmd) {
		if(cmd.length <= 0) return;
		
		if(cmd[0].equals(CMD_SETTARGET)) {
			if(cmd.length == 3) {
				targetX = IRORInteractive.parseInt(cmd[1]);
				targetZ = IRORInteractive.parseInt(cmd[2]);
			}
			if(cmd.length == 4) {
				targetX = IRORInteractive.parseInt(cmd[1]);
				targetZ = IRORInteractive.parseInt(cmd[3]);
			}
			return;
		}
		
		if(cmd[0].equals(CMD_GETTARGET)) {
			this.tx = targetX + ";" + targetZ;
			return;
		}
		
		if(cmd[0].equals(CMD_GETTARGETX)) {
			this.tx = "" + targetX;
			return;
		}
		
		if(cmd[0].equals(CMD_GETTARGETZ)) {
			this.tx = "" + targetZ;
			return;
		}
	}
	
	public void setTarget(int x, int z) {
		this.targetX = x;
		this.targetZ = z;
	}
	
	public void onCommandImpl(World world, String... cmd) { }
	
	public void onCoordAction(World world, EntityPlayer player, int x, int y, int z) { }
}
