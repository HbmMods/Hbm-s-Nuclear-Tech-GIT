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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityGasDuct extends TileEntity implements IFluidDuct {

	public ForgeDirection[] connections = new ForgeDirection[6];
	public FluidType type = Fluids.GAS;
	public List<UnionOfTileEntitiesAndBooleansForFluids> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleansForFluids>();
	
	@Override
	public void updateEntity() {
		
		//this.updateConnections();
		
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.fluid_duct_neo, 1, 3);
		
		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord, zCoord);
		if(tile instanceof TileEntityPipeBaseNT) {
			((TileEntityPipeBaseNT) tile).setType(this.type);
		}
	}

	public void updateConnections() {
		if(Library.checkFluidConnectables(this.worldObj, xCoord, yCoord + 1, zCoord, type))
			connections[0] = ForgeDirection.UP;
		else
			connections[0] = null;

		if(Library.checkFluidConnectables(this.worldObj, xCoord, yCoord - 1, zCoord, type))
			connections[1] = ForgeDirection.DOWN;
		else
			connections[1] = null;

		if(Library.checkFluidConnectables(this.worldObj, xCoord, yCoord, zCoord - 1, type))
			connections[2] = ForgeDirection.NORTH;
		else
			connections[2] = null;

		if(Library.checkFluidConnectables(this.worldObj, xCoord + 1, yCoord, zCoord, type))
			connections[3] = ForgeDirection.EAST;
		else
			connections[3] = null;

		if(Library.checkFluidConnectables(this.worldObj, xCoord, yCoord, zCoord + 1, type))
			connections[4] = ForgeDirection.SOUTH;
		else
			connections[4] = null;

		if(Library.checkFluidConnectables(this.worldObj, xCoord - 1, yCoord, zCoord, type))
			connections[5] = ForgeDirection.WEST;
		else
			connections[5] = null;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		type = Fluids.GAS;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public FluidType getType() {
		return type;
	}

	@Override
	public boolean setType(FluidType type) {
		return false;
	}
}
