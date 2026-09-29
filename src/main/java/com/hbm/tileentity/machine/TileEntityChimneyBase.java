package com.hbm.tileentity.machine;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.module.portmanager.ModulePortManFluidAdaptive;
import com.hbm.tileentity.IBufPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.TilePortShapes;
import com.hbm.tileentity.TilePort.PortDef;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityChimneyBase extends TileEntityLoadedBase implements IFluidStandardReceiverMK2, IBufPacketReceiver {

	public long ashTick = 0;
	public long sootTick = 0;
	public int onTicks;
	
	public FluidTank[] tanks;
	
	protected ModulePortManFluidAdaptive moduleSmokePorts;
	
	public TileEntityChimneyBase() {
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(Fluids.SMOKE, 1_000_000);
		tanks[1] = new FluidTank(Fluids.SMOKE_LEADED, 1_000_000);
		tanks[2] = new FluidTank(Fluids.SMOKE_POISON, 1_000_000);
		
		moduleSmokePorts = new ModulePortManFluidAdaptive(this).setInputTanks(tanks);
	}
	
	protected PortDef[] cachedPorts;
	public PortDef[] getPorts() { if(cachedPorts == null) cachedPorts = TilePortShapes.flare(xCoord, yCoord, zCoord); return cachedPorts; }

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			moduleSmokePorts.update(getPorts());

			if(ashTick > 0 || sootTick > 0) {

				TileEntity below = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);

				if(below instanceof TileEntityAshpit) {
					TileEntityAshpit ashpit = (TileEntityAshpit) below;
					ashpit.ashLevelFly += ashTick;
					ashpit.ashLevelSoot += sootTick;
				}
				this.ashTick = 0;
				this.sootTick = 0;
			}

			networkPackNT(150);

			if(onTicks > 0) onTicks--;

		} else {

			if(onTicks > 0) {
				this.spawnParticles();
			}
		}
	}

	public boolean capturesAsh() { return true; }
	public boolean capturesSoot() { return false; }
	public void spawnParticles() { }

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeInt(this.onTicks);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.onTicks = buf.readInt();
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH || dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) &&
				(type == Fluids.SMOKE || type == Fluids.SMOKE_LEADED || type == Fluids.SMOKE_POISON);
	}

	@Override
	public long transferFluid(FluidType type, int pressure, long fluid) {
		
		if(type != Fluids.SMOKE && type != Fluids.SMOKE_LEADED && type != Fluids.SMOKE_POISON) return fluid;

		onTicks = 20;

		if(capturesAsh()) ashTick += fluid;
		if(capturesSoot()) sootTick += fluid;

		fluid *= getPollutionMod();

		if(type == Fluids.SMOKE) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, fluid / 100F);
		if(type == Fluids.SMOKE_LEADED) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.HEAVYMETAL, fluid / 100F);
		if(type == Fluids.SMOKE_POISON) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.POISON, fluid / 100F);

		return 0;
	}

	public abstract double getPollutionMod();

	@Override
	public long getDemand(FluidType type, int pressure) {
		return 1_000_000;
	}

	@Override public FluidTank[] getAllTanks() { return tanks; }
	@Override public FluidTank[] getReceivingTanks() { return tanks; }
}
