package com.hbm.tileentity.conductor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleansForFluids;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidDuct;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEFluidPipePacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFluidDuct extends TileEntity implements IFluidDuct {
	
	public ForgeDirection[] connections = new ForgeDirection[6];
	public FluidType type = FluidType.NONE;
	public List<UnionOfTileEntitiesAndBooleansForFluids> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleansForFluids>();
	
	public TileEntityFluidDuct() {
		
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new TEFluidPipePacket(xCoord, yCoord, zCoord, type), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 25));
		
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
		super.readFromNBT(nbt);
		type = FluidType.getEnum(nbt.getInteger("fluid"));
    }

    @Override
	public void writeToNBT(NBTTagCompound nbt)
    {
		super.writeToNBT(nbt);
		nbt.setInteger("fluid", Arrays.asList(FluidType.values()).indexOf(type));
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public FluidType getType() {
		return type;
	}
}
