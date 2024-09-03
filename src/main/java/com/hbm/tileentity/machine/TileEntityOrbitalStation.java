package com.hbm.tileentity.machine;

import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

import com.hbm.dim.orbit.OrbitalStation;
import com.hbm.entity.missile.EntityRideableRocket;
import com.hbm.entity.missile.EntityRideableRocket.RocketState;
import com.hbm.items.weapon.ItemCustomRocket;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
        super(16);
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
        despawnRocket();

        docked = rocket;
        hasDocked = true;
    }

    public void undockRocket() {
        docked = null;
        hasDocked = false;
    }

    public void despawnRocket() {
        if(docked != null) {
            Stack<ItemStack> itemsToStuff = new Stack<ItemStack>();
            itemsToStuff.push(ItemCustomRocket.build(docked.getRocket(), true));
            if(docked.navDrive != null) itemsToStuff.push(docked.navDrive.copy());

            for(int i = 0; i < slots.length; i++) {
                if(slots[i] == null) {
                    slots[i] = itemsToStuff.pop();
                    if(itemsToStuff.empty()) break;
                }
            }

            docked.setDead();
            docked = null;
            hasDocked = false;
        }
    }

    public void spawnRocket(ItemStack stack) {
        EntityRideableRocket rocket = new EntityRideableRocket(worldObj, xCoord + 0.5F, yCoord + 1.5F, zCoord + 0.5F, stack);
        rocket.posY -= rocket.height;
        rocket.setState(RocketState.LANDED);
		worldObj.spawnEntityInWorld(rocket);

        dockRocket(rocket);
    }

    public boolean hasStoredItems() {
        for(ItemStack stack : slots) {
            if(stack != null) return true;
        }
        
        return false;
    }

    public void giveStoredItems(EntityPlayer player) {
        for(int i = 0; i < slots.length; i++) {
            if(slots[i] != null) {
                if(!player.inventory.addItemStackToInventory(slots[i].copy())) {
                    player.dropPlayerItemWithRandomChoice(slots[i].copy(), false);
                }
                slots[i] = null;
            }
        }
        player.inventoryContainer.detectAndSendChanges();
        markDirty();
    }

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return true;
	}
    
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return IntStream.range(0, slots.length).toArray();
	}

    @Override
    public void serialize(ByteBuf buf) {
        super.serialize(buf);

        station.serialize(buf);

        buf.writeBoolean(hasDocked);

        for(int i = 0; i < slots.length; i++) {
            if(slots[i] != null) {
                buf.writeShort(Item.getIdFromItem(slots[i].getItem()));
            } else {
                buf.writeShort(-1);
            }
        }
    }

    @Override
    public void deserialize(ByteBuf buf) {
        super.deserialize(buf);

        OrbitalStation.clientStation = station = OrbitalStation.deserialize(buf);

        hasDocked = buf.readBoolean();

        for(int i = 0; i < slots.length; i++) {
            short id = buf.readShort();
            if(id > 0) {
                slots[i] = new ItemStack(Item.getItemById(id));
            } else {
                slots[i] = null;
            }
        }
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
