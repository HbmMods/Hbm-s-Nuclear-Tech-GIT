package com.hbm.tileentity;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;

import api.hbm.fluid.IFluidStandardSender;

import com.hbm.inventory.fluid.trait.FT_Polluting;
import com.hbm.inventory.fluid.trait.FluidTrait;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashMap;
import java.util.Map;

public abstract class TileEntityMachinePolluting extends TileEntityMachineBase implements IFluidStandardSender {

	public FluidTank smoke;
	public FluidTank smoke_leaded;
	public FluidTank smoke_poison;

	public TileEntityMachinePolluting(int scount, int buffer) {
		super(scount);
		smoke = new FluidTank(Fluids.SMOKE, buffer);
		smoke_leaded = new FluidTank(Fluids.SMOKE_LEADED, buffer);
		smoke_poison = new FluidTank(Fluids.SMOKE_POISON, buffer);
	}
	
	public void pollute(PollutionType type, float amount) {
		FluidTank tank = type == PollutionType.SOOT ? smoke : type == PollutionType.HEAVYMETAL ? smoke_leaded : smoke_poison;
		
		int fluidAmount = (int) Math.ceil(amount * 100);
		tank.setFill(tank.getFill() + fluidAmount);
		
		if(tank.getFill() > tank.getMaxFill()) {
			int overflow = tank.getFill() - tank.getMaxFill();
			tank.setFill(tank.getMaxFill());
			PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, type, overflow / 100F);
			
			if(worldObj.rand.nextInt(3) == 0) worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.fizz", 0.1F, 1.5F);
		}
	}
	public void pollute(FluidType type, FluidTrait.FluidReleaseType release, float amount) {
		FluidTank tank;
		FT_Polluting trait = type.getTrait(FT_Polluting.class);
		if(trait == null) return;
		if(release == FluidTrait.FluidReleaseType.VOID) return;

		HashMap<PollutionType, Float> map = release == FluidTrait.FluidReleaseType.BURN ? trait.burnMap : trait.releaseMap;

		for(Map.Entry<PollutionType, Float> entry : map.entrySet()) {

			tank = entry.getKey() == PollutionType.SOOT ? smoke : entry.getKey() == PollutionType.HEAVYMETAL ? smoke_leaded : smoke_poison;
			int fluidAmount = (int) Math.ceil(entry.getValue() * amount * 100);
			tank.setFill(tank.getFill() + fluidAmount);

			if (tank.getFill() > tank.getMaxFill()) {
				int overflow = tank.getFill() - tank.getMaxFill();
				tank.setFill(tank.getMaxFill());
				PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, entry.getKey(), overflow / 100F);

				if (worldObj.rand.nextInt(3) == 0)
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.fizz", 0.1F, 1.5F);
			}
		}
	}
	
	public void sendSmoke(int x, int y, int z, ForgeDirection dir) {
		if(this.smoke.getFill() > 0) this.sendFluid(smoke, worldObj, x, y, z, dir);
		if(this.smoke_leaded.getFill() > 0) this.sendFluid(smoke_leaded, worldObj, x, y, z, dir);
		if(this.smoke_poison.getFill() > 0) this.sendFluid(smoke_poison, worldObj, x, y, z, dir);
	}
	
	public FluidTank[] getSmokeTanks() {
		return new FluidTank[] {smoke, smoke_leaded, smoke_poison};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		smoke.readFromNBT(nbt, "smoke0");
		smoke_leaded.readFromNBT(nbt, "smoke1");
		smoke_poison.readFromNBT(nbt, "smoke2");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		smoke.writeToNBT(nbt, "smoke0");
		smoke_leaded.writeToNBT(nbt, "smoke1");
		smoke_poison.writeToNBT(nbt, "smoke2");
	}
}
