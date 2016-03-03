package com.hbm.blocks;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.PowerNet;
import com.hbm.interfaces.IConductor;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import scala.Int;

public class TileEntityCable extends TileEntity implements IConductor {
	
	public ForgeDirection[] connections = new ForgeDirection[6];
	
	//Won't work as intended, shit.
	//public List<PowerNet> globalPowerNet = new ArrayList<PowerNet>();
	
	public TileEntityCable() {
		
	}
	
	public void updateEntity() {
		this.updateConnections();
	}
	
	public void updateConnections() {
		TileEntity e0 = this.worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
		if(e0 instanceof IConductor) connections[0] = ForgeDirection.UP;
		else connections[0] = null;
		TileEntity e1 = this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		if(e1 instanceof IConductor) connections[1] = ForgeDirection.DOWN;
		else connections[1] = null;
		TileEntity e2 = this.worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
		if(e2 instanceof IConductor) connections[2] = ForgeDirection.NORTH;
		else connections[2] = null;
		TileEntity e3 = this.worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
		if(e3 instanceof IConductor) connections[3] = ForgeDirection.EAST;
		else connections[3] = null;
		TileEntity e4 = this.worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
		if(e4 instanceof IConductor) connections[4] = ForgeDirection.SOUTH;
		else connections[4] = null;
		TileEntity e5 = this.worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
		if(e5 instanceof IConductor) connections[5] = ForgeDirection.WEST;
		else connections[5] = null;
	}

}
