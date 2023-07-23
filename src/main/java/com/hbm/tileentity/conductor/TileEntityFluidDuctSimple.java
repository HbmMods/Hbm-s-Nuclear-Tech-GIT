package com.hbm.tileentity.conductor;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.calc.UnionOfTileEntitiesAndBooleansForFluids;
import com.hbm.interfaces.IFluidDuct;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFluidDuctSimple extends TileEntity implements IFluidDuct {

	protected FluidType type = Fluids.NONE;
	public List<UnionOfTileEntitiesAndBooleansForFluids> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleansForFluids>();
	
	
	public ForgeDirection[] connections = new ForgeDirection[6];

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		type = Fluids.fromID(nbt.getInteger("fluid"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("fluid", type.getID());
	}
	
	public boolean setType(FluidType type) {

		if(this.type == type)
			return true;
		
		this.type = type;
		this.markDirty();
		
		if(worldObj instanceof WorldServer) {
			WorldServer world = (WorldServer) worldObj;
			world.getPlayerManager().markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		
		return true;
	}

	@Override
	public FluidType getType() {
		return type;
	}
	
	@Override
	public void updateEntity() {
		/*this.updateConnections();

		if(lastType != type) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			lastType = type;
		}*/

		if(this.getBlockType() == ModBlocks.fluid_duct) worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.fluid_duct_neo);
		if(this.getBlockType() == ModBlocks.fluid_duct_solid) worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.fluid_duct_paintable);
		
		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord, zCoord);
		if(tile instanceof TileEntityPipeBaseNT) {
			((TileEntityPipeBaseNT) tile).setType(this.type);
		}
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
}
