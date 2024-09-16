package com.hbm.tileentity.machine;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.IConfigurableMachine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRadiator extends TileEntityCondenser {
	
	public static int inputTankSize = 500;
	public static int outputTankSize = 500;

	public TileEntityRadiator() {
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.SPENTSTEAM, inputTankSize);
		tanks[1] = new FluidTank(Fluids.WATER, outputTankSize);
		vacuumOptimised = true;
	}

	@Override
	public String getConfigName() {
		return "radiator";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		inputTankSize = IConfigurableMachine.grab(obj, "I:inputTankSize", inputTankSize);
		outputTankSize = IConfigurableMachine.grab(obj, "I:outputTankSize", outputTankSize);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:inputTankSize").value(inputTankSize);
		writer.name("I:outputTankSize").value(outputTankSize);
	}

	@Override
	public void subscribeToAllAround(FluidType type, TileEntity te) {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.trySubscribe(this.tanks[0].getTankType(), worldObj, xCoord - dir.offsetX + rot.offsetX, yCoord + 1, zCoord - dir.offsetZ + rot.offsetZ, dir);
		this.trySubscribe(this.tanks[0].getTankType(), worldObj, xCoord - dir.offsetX + rot.offsetX, yCoord - 1, zCoord - dir.offsetZ + rot.offsetZ, dir);
		this.trySubscribe(this.tanks[0].getTankType(), worldObj, xCoord - dir.offsetX - rot.offsetX, yCoord + 1, zCoord - dir.offsetZ - rot.offsetZ, dir);
		this.trySubscribe(this.tanks[0].getTankType(), worldObj, xCoord - dir.offsetX - rot.offsetX, yCoord - 1, zCoord - dir.offsetZ - rot.offsetZ, dir);
	}

	@Override
	public void sendFluidToAll(FluidTank tank, TileEntity te) {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.sendFluid(this.tanks[1], worldObj, xCoord - dir.offsetX + rot.offsetX, yCoord + 1, zCoord - dir.offsetZ + rot.offsetZ, dir);
		this.sendFluid(this.tanks[1], worldObj, xCoord - dir.offsetX + rot.offsetX, yCoord - 1, zCoord - dir.offsetZ + rot.offsetZ, dir);
		this.sendFluid(this.tanks[1], worldObj, xCoord - dir.offsetX - rot.offsetX, yCoord + 1, zCoord - dir.offsetZ - rot.offsetZ, dir);
		this.sendFluid(this.tanks[1], worldObj, xCoord - dir.offsetX - rot.offsetX, yCoord - 1, zCoord - dir.offsetZ - rot.offsetZ, dir);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
				xCoord - 20,
				yCoord - 1,
				zCoord - 20,
				xCoord + 21,
				yCoord + 1,
				zCoord + 21
			);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
}
