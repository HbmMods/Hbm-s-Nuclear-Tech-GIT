package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.calc.UnionOfTileEntitiesAndBooleansForFluids;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidDuct;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEFluidPipePacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFluidDuct extends TileEntity implements IFluidDuct {
	
	public ForgeDirection[] connections = new ForgeDirection[6];
	public FluidType type = FluidType.NONE;
	public List<UnionOfTileEntitiesAndBooleansForFluids> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleansForFluids>();
	
	//public List<UnionOfTileEntitiesAndBooleansForGas> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleansForGas>();
	
	public TileEntityFluidDuct() {
		
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAll(new TEFluidPipePacket(xCoord, yCoord, zCoord, type));
		
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
	public void readFromNBT(NBTTagCompound nbt)
    {
    	this.type = FluidType.values()[nbt.getInteger("fluid")];
    }

    @Override
	public void writeToNBT(NBTTagCompound nbt)
    {
    	for(int i = 0; i < FluidType.values().length; i++)
    		if(FluidType.values()[i] == this.type)
    			nbt.setInteger("fluid", i);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
