package com.hbm.tileentity.machine;

import com.hbm.dim.CelestialBody;
import com.hbm.dim.orbit.OrbitalStation;
import com.hbm.dim.orbit.OrbitalStation.StationState;
import com.hbm.tileentity.TileEntityMachineBase;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;

public class TileEntityOrbitalStationComputer extends TileEntityMachineBase {

	public boolean hasDrive;
	
	public TileEntityOrbitalStationComputer() {
		super(1);
	}

	public boolean travelTo(CelestialBody body, ItemStack drive) {
		OrbitalStation station = OrbitalStation.getStationFromPosition(xCoord, zCoord);

		if(station.orbiting == body) return false;

		station.travelTo(worldObj, body);
		slots[0] = drive;

		return true;
	}

	public boolean isTravelling() {
		OrbitalStation station = OrbitalStation.getStationFromPosition(xCoord, zCoord);

		return station.state != StationState.ORBIT;
	}

	@Override
	public String getName() {
		return "container.orbitalStationComputer";
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			hasDrive = slots[0] != null;
			networkPackNT(50);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(hasDrive);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		hasDrive = buf.readBoolean();
	}

}
