package com.hbm.tileentity.network;

import java.util.List;

import com.hbm.entity.item.EntityDeliveryDrone;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.container.ContainerDroneCrate;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIDroneCrate;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityDroneCrate extends TileEntityMachineBase implements IGUIProvider, INBTPacketReceiver, IControlReceiver, IDroneLinkable, IFluidStandardTransceiver, IFluidCopiable {
	
	public FluidTank tank;
	
	public int nextX = -1;
	public int nextY = -1;
	public int nextZ = -1;
	
	public boolean sendingMode = false;
	public boolean itemType = true;

	public TileEntityDroneCrate() {
		super(19);
		this.tank = new FluidTank(Fluids.NONE, 64_000);
	}

	@Override
	public String getName() {
		return "container.droneCrate";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			BlockPos pos = getCoord();
			this.tank.setType(18, slots);
			
			if(sendingMode && !itemType && worldObj.getTotalWorldTime() % 20 == 0) {
				this.subscribeToAllAround(tank.getTankType(), this);
			}
			
			if(!sendingMode && !itemType && worldObj.getTotalWorldTime() % 20 == 0) {
				this.sendFluidToAll(tank, this);
			}
			
			if(nextY != -1) {
				
				List<EntityDeliveryDrone> drones = worldObj.getEntitiesWithinAABB(EntityDeliveryDrone.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1, zCoord, xCoord + 1, yCoord + 2, zCoord + 1));
				for(EntityDeliveryDrone drone : drones) {
					if(Vec3.createVectorHelper(drone.motionX, drone.motionY, drone.motionZ).lengthVector() < 0.05) {
						drone.setTarget(nextX + 0.5, nextY, nextZ + 0.5);

						if(sendingMode && itemType) loadItems(drone);
						if(!sendingMode && itemType) unloadItems(drone);
						if(sendingMode && !itemType) loadFluid(drone);
						if(!sendingMode && !itemType) unloadFluid(drone);
					}
				}

				ParticleUtil.spawnDroneLine(worldObj,
						pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
						(nextX  - pos.getX()), (nextY - pos.getY()), (nextZ - pos.getZ()), 0x00ffff);
			}


			
			NBTTagCompound data = new NBTTagCompound();
			data.setIntArray("pos", new int[] {nextX, nextY, nextZ});
			data.setBoolean("mode", sendingMode);
			data.setBoolean("type", itemType);
			tank.writeToNBT(data, "t");
			INBTPacketReceiver.networkPack(this, data, 25);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		int[] pos = nbt.getIntArray("pos");
		this.nextX = pos[0];
		this.nextY = pos[1];
		this.nextZ = pos[2];
		this.sendingMode = nbt.getBoolean("mode");
		this.itemType = nbt.getBoolean("type");
		tank.readFromNBT(nbt, "t");
	}
	
	protected void loadItems(EntityDeliveryDrone drone) {
		
		if(drone.getAppearance() != 0) return;
		
		boolean loaded = false;
		
		for(int i = 0; i < 18; i++) {
			if(this.slots[i] != null) {
				loaded = true;
				drone.setInventorySlotContents(i, this.slots[i].copy());
				this.slots[i] = null;
			}
		}
		
		if(loaded) {
			this.markDirty();
			drone.setAppearance(1);
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:item.unpack", 0.5F, 0.75F);
		}
	}
	
	protected void unloadItems(EntityDeliveryDrone drone) {
		
		if(drone.getAppearance() != 1) return;
		
		boolean emptied = true;
		
		for(int i = 0; i < 18; i++) {
			ItemStack droneSlot = drone.getStackInSlot(i);
			
			if(this.slots[i] == null && droneSlot != null) {
				this.slots[i] = droneSlot.copy();
				drone.setInventorySlotContents(i, null);
			} else if(this.slots[i] != null && droneSlot != null) {
				emptied = false;
			}
		}
		
		this.markDirty();
		
		if(emptied) {
			drone.setAppearance(0);
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:item.unpack", 0.5F, 0.75F);
		}
	}
	
	protected void loadFluid(EntityDeliveryDrone drone) {
		
		if(drone.getAppearance() != 0) return;
		
		if(this.tank.getFill() > 0) {
			drone.fluid = new FluidStack(tank.getTankType(), tank.getFill());
			this.tank.setFill(0);
			drone.setAppearance(2);
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:item.unpack", 0.5F, 0.75F);
			
			this.markDirty();
		}
	}
	
	protected void unloadFluid(EntityDeliveryDrone drone) {
		
		if(drone.getAppearance() != 2) return;
		
		if(drone.fluid != null && drone.fluid.type == tank.getTankType()) {
			
			if(drone.fluid.fill + tank.getFill() <= tank.getMaxFill()) {
				tank.setFill(tank.getFill() + drone.fluid.fill);
				drone.fluid = null;
				drone.setAppearance(0);
			} else {
				int overshoot = drone.fluid.fill + tank.getFill() - tank.getMaxFill();
				tank.setFill(tank.getMaxFill());
				drone.fluid.fill = overshoot;
			}
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:item.unpack", 0.5F, 0.75F);
			
			this.markDirty();
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return true;
	}

	@Override
	public BlockPos getPoint() {
		return new BlockPos(xCoord, yCoord + 1, zCoord);
	}

	@Override
	public void setNextTarget(int x, int y, int z) {
		this.nextX = x;
		this.nextY = y;
		this.nextZ = z;
		this.markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		int[] pos = nbt.getIntArray("pos");
		this.nextX = pos[0];
		this.nextY = pos[1];
		this.nextZ = pos[2];
		this.sendingMode = nbt.getBoolean("mode");
		this.itemType = nbt.getBoolean("type");
		tank.readFromNBT(nbt, "t");
	}

	public BlockPos getCoord() {
		return new BlockPos(xCoord, yCoord + 1, zCoord);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setIntArray("pos", new int[] {nextX, nextY, nextZ});
		nbt.setBoolean("mode", sendingMode);
		nbt.setBoolean("type", itemType);
		tank.writeToNBT(nbt, "t");
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerDroneCrate(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIDroneCrate(player.inventory, this);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("mode")) {
			this.sendingMode = !this.sendingMode;
			this.markChanged();
		}
		
		if(data.hasKey("type")) {
			this.itemType = !this.itemType;
			this.markChanged();
		}
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return !sendingMode && !itemType ? new FluidTank[] { tank } : new FluidTank[0];
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return sendingMode && !itemType ? new FluidTank[] { tank } : new FluidTank[0];
	}
}
