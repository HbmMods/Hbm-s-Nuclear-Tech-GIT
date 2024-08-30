package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.dim.orbit.OrbitalStation;
import com.hbm.entity.missile.EntityRideableRocket;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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

    @SuppressWarnings("unchecked")
    public void enterCapsule(EntityPlayer player) {
        List<Entity> capsules = worldObj.getEntitiesWithinAABB(EntityRideableRocket.class, AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 128, zCoord - 1, xCoord + 2, yCoord + 1, zCoord + 2));
        if(capsules.size() > 0) {
            capsules.get(0).interactFirst(player);
        }
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
