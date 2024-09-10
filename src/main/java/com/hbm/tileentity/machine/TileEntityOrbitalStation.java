package com.hbm.tileentity.machine;

import java.util.Stack;
import java.util.stream.IntStream;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.SolarSystem;
import com.hbm.dim.orbit.OrbitalStation;
import com.hbm.entity.missile.EntityRideableRocket;
import com.hbm.entity.missile.EntityRideableRocket.RocketState;
import com.hbm.handler.RocketStruct;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.weapon.ItemCustomRocket;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityOrbitalStation extends TileEntityMachineBase implements IFluidStandardReceiver {

	private OrbitalStation station;
	private EntityRideableRocket docked;

	private FluidTank[] tanks;

	public boolean isReserved = false;

	// Client synced state information
	public boolean hasDocked = false;
	public boolean hasRider = false;
	public boolean needsFuel = false;
	public boolean hasFuel = false;

	public float rot;
	public float prevRot;

	public TileEntityOrbitalStation() {
		super(16);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.HYDROGEN, 16_000);
		tanks[1] = new FluidTank(Fluids.OXYGEN, 16_000);
	}

	@Override
	public String getName() {
		return "container.orbitalStation";
	}

	@Override
	public void updateEntity() {
		if(!CelestialBody.inOrbit(worldObj)) return;

		if(!worldObj.isRemote) {
			// Station TEs handle syncing information about the current orbital parameters to players on the station
			station = OrbitalStation.getStationFromPosition(xCoord, zCoord);

			if(isCore()) station.update(worldObj);
			station.addPort(this);

			if(docked != null && docked.isReusable()) {
				int fillRequirement = getFillRequirement(false); // Use higher fill requirement for tank sizing

				// Update tank sizes based on fuel requirement, preserving existing fills
				for(FluidTank tank : tanks) {
					tank.changeTankSize(Math.max(fillRequirement, tank.getFill()));
				}

				// Connections
				for(DirPos pos : getConPos()) {
					for(FluidTank tank : tanks) {
						if(tank.getTankType() == Fluids.NONE) continue;
						trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					}
				}
			}

			if(docked != null && (docked.isDead || docked.getState() == RocketState.UNDOCKING)) {
				// Drain tanks only upon successful undocking, just in case the pod gets stashed before the player can launch (in multi-player stations)
				if(!docked.isDead && docked.isReusable()) {
					boolean toOrbit = docked.getTarget().inOrbit;
					for(FluidTank tank : tanks) tank.changeTankSize(Math.max(0, tank.getFill() - getFillRequirement(toOrbit)));
				}
				
				undockRocket();
			}

			// if we have enough fuel, transition to ready to launch
			if(docked != null && docked.isReusable()) {
				boolean hasFuel = hasSufficientFuel(docked.getTarget().inOrbit);
				
				if(hasFuel && docked.getState() == RocketState.NEEDSFUEL) {
					docked.setState(docked.navDrive != null ? RocketState.AWAITING : RocketState.LANDED);
				} else if (!hasFuel && docked.getState() != RocketState.NEEDSFUEL) {
					docked.setState(RocketState.NEEDSFUEL);
				}
			}

			hasDocked = docked != null;
			hasRider = hasDocked && docked.riddenByEntity != null;
			needsFuel = hasDocked && docked.isReusable();
			hasFuel = needsFuel && hasSufficientFuel(docked.getTarget().inOrbit);

			if(hasDocked) isReserved = false;

			this.networkPackNT(OrbitalStation.STATION_SIZE / 2);
		} else {
			if(isCore() && station != null) station.update(worldObj);

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

	public boolean isCore() {
		return getBlockType() == ModBlocks.orbital_station;
	}

	public DirPos[] getConPos() {
		return new DirPos[] {
			new DirPos(xCoord - 1, yCoord + 1, zCoord + 3, ForgeDirection.NORTH),
			new DirPos(xCoord + 0, yCoord + 1, zCoord + 3, ForgeDirection.NORTH),
			new DirPos(xCoord + 1, yCoord + 1, zCoord + 3, ForgeDirection.NORTH),

			new DirPos(xCoord - 1, yCoord + 1, zCoord - 3, ForgeDirection.SOUTH),
			new DirPos(xCoord + 0, yCoord + 1, zCoord - 3, ForgeDirection.SOUTH),
			new DirPos(xCoord + 1, yCoord + 1, zCoord - 3, ForgeDirection.SOUTH),

			new DirPos(xCoord + 3, yCoord + 1, zCoord - 1, ForgeDirection.EAST),
			new DirPos(xCoord + 3, yCoord + 1, zCoord + 0, ForgeDirection.EAST),
			new DirPos(xCoord + 3, yCoord + 1, zCoord + 1, ForgeDirection.EAST),

			new DirPos(xCoord - 3, yCoord + 1, zCoord - 1, ForgeDirection.WEST),
			new DirPos(xCoord - 3, yCoord + 1, zCoord + 0, ForgeDirection.WEST),
			new DirPos(xCoord - 3, yCoord + 1, zCoord + 1, ForgeDirection.WEST),
		};
	}

	public void enterCapsule(EntityPlayer player) {
		if(docked == null || docked.riddenByEntity != null) return;
		docked.interactFirst(player);
	}

	public void dockRocket(EntityRideableRocket rocket) {
		despawnRocket();

		docked = rocket;
	}

	public void undockRocket() {
		docked = null;
	}

	public void despawnRocket() {
		if(docked != null) {
			Stack<ItemStack> itemsToStuff = new Stack<ItemStack>();

			RocketStruct rocket = docked.getRocket();
			if(rocket.stages.size() > 0) {
				itemsToStuff.push(ItemCustomRocket.build(docked.getRocket(), true));
			} else {
				itemsToStuff.push(new ItemStack(rocket.capsule.part));
			}

			if(docked.navDrive != null) itemsToStuff.push(docked.navDrive.copy());

			for(int i = 0; i < slots.length; i++) {
				if(slots[i] == null) {
					slots[i] = itemsToStuff.pop();
					if(itemsToStuff.empty()) break;
				}
			}

			docked.setDead();
			docked = null;
		}
	}

	public void reservePort() {
		isReserved = true;
	}

	public void spawnRocket(ItemStack stack) {
		EntityRideableRocket rocket = new EntityRideableRocket(worldObj, xCoord + 0.5F, yCoord + 1.5F, zCoord + 0.5F, stack);
		rocket.posY -= rocket.height;
		rocket.setState(rocket.isReusable() ? RocketState.NEEDSFUEL : RocketState.LANDED);
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

	public boolean hasSufficientFuel(boolean toOrbit) {
		int fillRequirement = getFillRequirement(toOrbit);
		for(FluidTank tank : tanks) {
			if(tank.getFill() < fillRequirement) return false;
		}

		return true;
	}

	public int getFillRequirement(boolean toOrbit) {
		if(toOrbit) return 500; // Transferring between stations is much cheaper
		int mass = docked != null ? docked.getRocket().getLaunchMass() : 4_000;
		return SolarSystem.getCostBetween(station.orbiting, station.orbiting, mass, 600_000, 350, false, true);
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

		if(isCore()) station.serialize(buf);

		buf.writeBoolean(hasDocked);
		buf.writeBoolean(hasRider);
		buf.writeBoolean(needsFuel);
		buf.writeBoolean(hasFuel);

		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null) {
				buf.writeShort(Item.getIdFromItem(slots[i].getItem()));
			} else {
				buf.writeShort(-1);
			}
		}

		for(int i = 0; i < tanks.length; i++) tanks[i].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		if(isCore()) OrbitalStation.clientStation = station = OrbitalStation.deserialize(buf);

		hasDocked = buf.readBoolean();
		hasRider = buf.readBoolean();
		needsFuel = buf.readBoolean();
		hasFuel = buf.readBoolean();

		for(int i = 0; i < slots.length; i++) {
			short id = buf.readShort();
			if(id > 0) {
				slots[i] = new ItemStack(Item.getItemById(id));
			} else {
				slots[i] = null;
			}
		}

		for(int i = 0; i < tanks.length; i++) tanks[i].deserialize(buf);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		for(int i = 0; i < tanks.length; i++) tanks[i].writeToNBT(nbt, "t" + i);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		for(int i = 0; i < tanks.length; i++) tanks[i].readFromNBT(nbt, "t" + i);
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

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return tanks;
	}
	
}
