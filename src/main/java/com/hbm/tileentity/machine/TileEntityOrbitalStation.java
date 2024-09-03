package com.hbm.tileentity.machine;

import java.util.List;
import java.util.Stack;

import com.hbm.dim.orbit.OrbitalStation;
import com.hbm.entity.missile.EntityRideableRocket;
import com.hbm.items.weapon.ItemCustomRocket;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityOrbitalStation extends TileEntityMachineBase {

    private OrbitalStation station;
    private EntityRideableRocket docked;

    public boolean hasDocked = false;

    public float rot;
    public float prevRot;

    public TileEntityOrbitalStation() {
        super(27);
    }

    @Override
    public String getName() {
        return "container.orbitalStation";
    }

    @Override
    public void updateEntity() {
        if(!worldObj.isRemote) {
            // Station TEs handle syncing information about the current orbital parameters to players on the station
            station = OrbitalStation.getStationFromPosition(xCoord, zCoord);

            station.update(worldObj);

            if(docked != null && docked.isDead) {
                undockRocket();
            }

            this.networkPackNT(OrbitalStation.STATION_SIZE / 2);
        } else {
            if(station != null) station.update(worldObj);

            prevRot = rot;
            if(hasDocked) {
                rot += 2.25F;
                if(rot > 90) rot = 90;
            } else {
                rot -= 2.25F;
                if(rot < 0) rot = 0;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void enterCapsule(EntityPlayer player) {
        List<Entity> capsules = worldObj.getEntitiesWithinAABB(EntityRideableRocket.class, AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 128, zCoord - 1, xCoord + 2, yCoord + 1, zCoord + 2));
        if(capsules.size() > 0) {
            capsules.get(0).interactFirst(player);
        }
    }

    public void dockRocket(EntityRideableRocket rocket) {
        if(docked != null) {
            Stack<ItemStack> itemsToStuff = new Stack<ItemStack>();
            itemsToStuff.push(ItemCustomRocket.build(docked.getRocket(), true));
            itemsToStuff.push(docked.navDrive);

            for(int i = 0; i < slots.length; i++) {
                if(slots[i] == null) {
                    slots[i] = itemsToStuff.pop();
                    if(itemsToStuff.empty()) break;
                }
            }

            docked.setDead();
        }

        docked = rocket;
        hasDocked = true;
    }

    public void undockRocket() {
        docked = null;
        hasDocked = false;
    }

    @Override
    public void serialize(ByteBuf buf) {
        super.serialize(buf);

        station.serialize(buf);

        buf.writeBoolean(hasDocked);
    }

    @Override
    public void deserialize(ByteBuf buf) {
        super.deserialize(buf);

        OrbitalStation.clientStation = station = OrbitalStation.deserialize(buf);

        hasDocked = buf.readBoolean();
    }

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
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
