package com.hbm.tileentity.machine;

import com.hbm.dim.SolarSystem;
import com.hbm.dim.orbit.OrbitalStation;
import com.hbm.tileentity.TileEntityMachineBase;

import io.netty.buffer.ByteBuf;

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

            this.networkPackNT(OrbitalStation.STATION_SIZE / 2);
        }
    }

    // debug
    public void toggleOrbiting() {
        int target = station.orbiting.getEnum().ordinal();
        target++;
        if(target >= SolarSystem.Body.values().length) target = 1;

        station.orbiting = SolarSystem.Body.values()[target].getBody();
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
    
}
