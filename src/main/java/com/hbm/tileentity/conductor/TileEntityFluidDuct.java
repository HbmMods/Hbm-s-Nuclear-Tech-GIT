package com.hbm.tileentity.conductor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleansForFluids;
import com.hbm.interfaces.IFluidDuct;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFluidDuct extends TileEntityFluidDuctSimple {
	
	public ForgeDirection[] connections = new ForgeDirection[6];
	
	public TileEntityFluidDuct() { }
	
	@Override
	public void updateEntity() {
		this.updateConnections();
	}
	
	public void updateConnections() {
		if(Library.checkFluidConnectables(this.worldObj, xCoord, yCoord + 1, zCoord, type)) connections[0] = ForgeDirection.UP;
		else connections[0] = null;
		
		if(Library.checkFluidConnectables(this.worldObj, xCoord, yCoord - 1, zCoord, type)) connections[1] = ForgeDirection.DOWN;
		else connections[1] = null;
		
		if(Library.checkFluidConnectables(this.worldObj, xCoord, yCoord, zCoord - 1, type)) connections[2] = ForgeDirection.NORTH;
		else connections[2] = null;
		
		if(Library.checkFluidConnectables(this.worldObj, xCoord + 1, yCoord, zCoord, type)) connections[3] = ForgeDirection.EAST;
		else connections[3] = null;
		
		if(Library.checkFluidConnectables(this.worldObj, xCoord, yCoord, zCoord + 1, type)) connections[4] = ForgeDirection.SOUTH;
		else connections[4] = null;
		
		if(Library.checkFluidConnectables(this.worldObj, xCoord - 1, yCoord, zCoord, type)) connections[5] = ForgeDirection.WEST;
		else connections[5] = null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
