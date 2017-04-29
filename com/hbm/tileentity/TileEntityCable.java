package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.interfaces.IConductor;
import com.hbm.lib.Library;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import scala.Int;

public class TileEntityCable extends TileEntity implements IConductor {
	
	public ForgeDirection[] connections = new ForgeDirection[6];
	
	public List<UnionOfTileEntitiesAndBooleans> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleans>();
	
	public TileEntityCable() {
		
	}
	
	public void updateEntity() {
		this.updateConnections();
	}
	
	public void updateConnections() {
		if(Library.checkConnectables(this.worldObj, xCoord, yCoord + 1, zCoord)) connections[0] = ForgeDirection.UP;
		else connections[0] = null;
		
		if(Library.checkConnectables(this.worldObj, xCoord, yCoord - 1, zCoord)) connections[1] = ForgeDirection.DOWN;
		else connections[1] = null;
		
		if(Library.checkConnectables(this.worldObj, xCoord, yCoord, zCoord - 1)) connections[2] = ForgeDirection.NORTH;
		else connections[2] = null;
		
		if(Library.checkConnectables(this.worldObj, xCoord + 1, yCoord, zCoord)) connections[3] = ForgeDirection.EAST;
		else connections[3] = null;
		
		if(Library.checkConnectables(this.worldObj, xCoord, yCoord, zCoord + 1)) connections[4] = ForgeDirection.SOUTH;
		else connections[4] = null;
		
		if(Library.checkConnectables(this.worldObj, xCoord - 1, yCoord, zCoord)) connections[5] = ForgeDirection.WEST;
		else connections[5] = null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

}
