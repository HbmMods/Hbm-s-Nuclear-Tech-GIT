package com.hbm.tileentity.machine;

import com.hbm.dim.SolarSystem;
import com.hbm.dim.orbit.OrbitalStation;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityOrbitalStation extends TileEntityMachineBase {

    private OrbitalStation station;

    public TileEntityOrbitalStation() {
        super(0);
    }

    @Override
    public String getName() {
        return "container.orbitalStation";
    }

    @Override
    public void updateEntity() {
        if(!worldObj.isRemote) {
            // Station TEs handle syncing information about the current orbital parameters to players on the station
            station = OrbitalStation.getStation(xCoord, zCoord);

            station.update(worldObj);

            this.networkPackNT(OrbitalStation.STATION_SIZE / 2);
        } else if(station != null) {
            station.update(worldObj);
        }
    }

    // debug
    public void toggleOrbiting() {
        int target = station.orbiting.getEnum().ordinal();
        target++;
        if(target >= SolarSystem.Body.values().length) target = 1;

        station.travelTo(SolarSystem.Body.values()[target].getBody());
    }

    @Override
    public void serialize(ByteBuf buf) {
        super.serialize(buf);

        station.serialize(buf);
    }

    @Override
    public void deserialize(ByteBuf buf) {
        super.deserialize(buf);

        OrbitalStation.clientStation = station = OrbitalStation.deserialize(buf);
    }

    AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
                xCoord - 2,
                yCoord - 2,
                zCoord - 2,
                xCoord + 3,
                yCoord + 2,
                zCoord + 3
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
