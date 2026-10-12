package com.hbm.tileentity.machine;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.config.ClientConfig;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.TilePortShapes;
import com.hbm.tileentity.TilePort.PortDef;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityTowerLarge extends TileEntityCondenser {
	
	//Configurable values
	public static int inputTankSizeTL = 10_000;
	public static int outputTankSizeTL = 10_000;
	
	public TileEntityTowerLarge() {
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.SPENTSTEAM, inputTankSizeTL);
		tanks[1] = new FluidTank(Fluids.WATER, outputTankSizeTL);
	}
	
	@Override
	public String getConfigName() {
		return "condenserTowerLarge";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		inputTankSizeTL = IConfigurableMachine.grab(obj, "I:inputTankSize", inputTankSizeTL);
		outputTankSizeTL = IConfigurableMachine.grab(obj, "I:outputTankSize", outputTankSizeTL);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:inputTankSize").value(inputTankSizeTL);
		writer.name("I:outputTankSize").value(outputTankSizeTL);
	}
	
	@Override
	public PortDef[] getPorts() { if(cachedPorts == null) cachedPorts = TilePortShapes.bigTower(xCoord, yCoord, zCoord); return cachedPorts; }

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(worldObj.isRemote) {
			
			if(ClientConfig.COOLING_TOWER_PARTICLES.get() && (this.waterTimer > 0 && this.worldObj.getTotalWorldTime() % 4 == 0)) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tower");
				data.setFloat("lift", 0.5F);
				data.setFloat("base", 1F);
				data.setFloat("max", 10F);
				data.setInteger("life", 750 + worldObj.rand.nextInt(250));
	
				data.setDouble("posX", xCoord + 0.5 + worldObj.rand.nextDouble() * 3 - 1.5);
				data.setDouble("posZ", zCoord + 0.5 + worldObj.rand.nextDouble() * 3 - 1.5);
				data.setDouble("posY", yCoord + 1);
				
				MainRegistry.proxy.effectNT(data);
			}
		}
	}

	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 4,
					yCoord,
					zCoord - 4,
					xCoord + 5,
					yCoord + 13,
					zCoord + 5
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
