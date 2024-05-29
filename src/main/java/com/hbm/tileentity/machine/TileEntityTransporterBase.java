package com.hbm.tileentity.machine;

import java.util.stream.IntStream;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.tool.ItemTransporterLinker.TransporterInfo;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;

import api.hbm.fluid.IFluidStandardTransceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import scala.actors.threadpool.Arrays;

public abstract class TileEntityTransporterBase extends TileEntityMachineBase implements IGUIProvider, IControlReceiver, IFluidStandardTransceiver {

	private String name = "Transporter";

	public int dimensionId = 0;
	public ResourceLocation dimensionImage;

	public FluidTank[] tanks;

	public TileEntityTransporterBase(int slotCount, int tankCount, int tankSize) {
		this(slotCount, tankCount, tankSize, 0, 0, 0);
	}
	
	public TileEntityTransporterBase(int slotCount, int tankCount, int tankSize, int extraSlots, int extraTanks, int extraTankSize) {
		super(slotCount + tankCount / 2 + extraSlots + extraTanks);

		tanks = new FluidTank[tankCount + extraTanks];
		for(int i = 0; i < tankCount; i++) {
			tanks[i] = new FluidTank(Fluids.NONE, tankSize);
		}
		for(int i = tankCount; i < tankCount + extraTanks; i++) {
			tanks[i] = new FluidTank(Fluids.NONE, extraTankSize);
		}

		inputSlotMax = slotCount / 2;
		outputSlotMax = slotCount;

		inputTankMax = tankCount / 2;
		outputTankMax = tankCount;
	}

	// The transporter we're sending our contents to
	private TileEntityTransporterBase linkedTransporter;
	private TransporterInfo linkedTransporterInfo;

	private int inputSlotMax;
	private int outputSlotMax;

	private int inputTankMax;
	private int outputTankMax;

	@Override
	public String getName() {
		return "container.transporter";
	}

	public Class<? extends TileEntityTransporterBase> getCompatible() {
		return TileEntityTransporterBase.class;
	}

	@Override
	public void updateEntity() {
		if(worldObj.isRemote) return;

		// Set tank types
		for(int i = 0; i < inputTankMax; i++) {
			tanks[i].setType(outputSlotMax + i, slots);
			subscribeToAllAround(tanks[i].getTankType(), this);
		}
		for(int i = outputTankMax; i < tanks.length; i++) {
			tanks[i].setType(outputSlotMax + inputTankMax + i - outputTankMax, slots);
			subscribeToAllAround(tanks[i].getTankType(), this);
		}

		fetchLinkedTransporter();

		if(linkedTransporter != null && canSend(linkedTransporter)) {
			boolean isDirty = false;

			// Move all items into the target
			for(int i = 0; i < inputSlotMax; i++) {
				if(slots[i] != null) {
					slots[i] = InventoryUtil.tryAddItemToInventory(linkedTransporter.slots, linkedTransporter.inputSlotMax, linkedTransporter.outputSlotMax - 1, slots[i]);
					isDirty = true;
				}
			}

			// Move all fluids into the target
			for(int i = 0; i < inputTankMax; i++) {
				int o = i+inputTankMax;

				linkedTransporter.tanks[o].setTankType(tanks[i].getTankType());

				int sourceFillLevel = tanks[i].getFill();
				int targetFillLevel = linkedTransporter.tanks[o].getFill();

				int spaceAvailable = linkedTransporter.tanks[o].getMaxFill() - targetFillLevel;
				int amountToSend = Math.min(sourceFillLevel, spaceAvailable);

				if(amountToSend > 0) {
					linkedTransporter.tanks[o].setFill(targetFillLevel + amountToSend);
					tanks[i].setFill(sourceFillLevel - amountToSend);
					isDirty = true;
				}
			}

			if(isDirty)
				markChanged();
		}

			
		NBTTagCompound data = new NBTTagCompound();
		data.setString("name", name);
		if(linkedTransporterInfo != null) {
			data.setInteger("dimensionId", linkedTransporterInfo.dimensionId);
			data.setIntArray("linkedTo", new int[] { linkedTransporterInfo.x, linkedTransporterInfo.y, linkedTransporterInfo.z });
		}
		for(int i = 0; i < tanks.length; i++) tanks[i].writeToNBT(data, "t" + i);
		this.networkPack(data, 250);
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return (FluidTank[]) Arrays.copyOfRange(tanks, inputTankMax, outputTankMax);
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return (FluidTank[]) Arrays.copyOfRange(tanks, 0, inputTankMax);
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);
		name = nbt.getString("name");
		linkedTransporter = null;
		int dimensionId = nbt.getInteger("dimensionId");
		int[] coords = nbt.getIntArray("linkedTo");
		if(coords.length > 0) {
			linkedTransporterInfo = new TransporterInfo("Linked Transporter", dimensionId, coords[0], coords[1], coords[2]);
		} else {
			linkedTransporterInfo = null;
		}
		for(int i = 0; i < tanks.length; i++) tanks[i].readFromNBT(nbt, "t" + i);
	}

	// Designated override for delaying sending or requiring fuel
	public boolean canSend(TileEntityTransporterBase linkedTransporter) {
		return true;
	}

	public String getTransporterName() {
		return name;
	}
	
	public void setTransporterName(String name) {
		this.name = name;
		NBTTagCompound data = new NBTTagCompound();
		data.setString("name", name);
		PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, xCoord, yCoord, zCoord));
	}

	private void fetchLinkedTransporter() {
		if(linkedTransporter == null && linkedTransporterInfo != null) {
			World transporterWorld = DimensionManager.getWorld(linkedTransporterInfo.dimensionId);
			TileEntity te = transporterWorld.getTileEntity(linkedTransporterInfo.x, linkedTransporterInfo.y, linkedTransporterInfo.z);
			if(te != null && te instanceof TileEntityTransporterBase) {
				linkedTransporter = (TileEntityTransporterBase) te;
			}
		}
	}

	public TransporterInfo getLinkedTransporter() {
		return linkedTransporterInfo;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return IntStream.range(0, outputSlotMax).toArray();
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i < inputSlotMax;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int side) {
		return i >= inputSlotMax && i < outputSlotMax;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		name = nbt.getString("name");
		linkedTransporter = null;
		int dimensionId = nbt.getInteger("dimensionId");
		int[] coords = nbt.getIntArray("linkedTo");
		if(coords.length > 0) {
			linkedTransporterInfo = new TransporterInfo("Linked Transporter", dimensionId, coords[0], coords[1], coords[2]);
		} else {
			linkedTransporterInfo = null;
		}
		for(int i = 0; i < tanks.length; i++) tanks[i].readFromNBT(nbt, "t" + i);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("name", name);
		if(linkedTransporterInfo != null) {
			nbt.setInteger("dimensionId", linkedTransporterInfo.dimensionId);
			nbt.setIntArray("linkedTo", new int[] { linkedTransporterInfo.x, linkedTransporterInfo.y, linkedTransporterInfo.z });
		}
		for(int i = 0; i < tanks.length; i++) tanks[i].writeToNBT(nbt, "t" + i);
	}

	// Is commutative, will automatically link and unlink its pair
	@Override
	public void receiveControl(NBTTagCompound nbt) {
		if(nbt.hasKey("name")) name = nbt.getString("name");
		if(nbt.hasKey("unlink")) {
			if(linkedTransporter != null) {
				linkedTransporter.linkedTransporter = null;
				linkedTransporter.linkedTransporterInfo = null;
			}

			linkedTransporter = null;
			linkedTransporterInfo = null;
		}
		if(nbt.hasKey("linkedTo")) {
			// If already linked, unlink the target
			if(linkedTransporter != null) {
				linkedTransporter.linkedTransporter = null;
				linkedTransporter.linkedTransporterInfo = null;
			}

			linkedTransporter = null;
			
			int[] coords = nbt.getIntArray("linkedTo");
			int dimensionId = nbt.getInteger("dimensionId");
			linkedTransporterInfo = new TransporterInfo("Linked Transporter", dimensionId, coords[0], coords[1], coords[2]);

			fetchLinkedTransporter();

			if(linkedTransporter != null) {
				linkedTransporter.linkedTransporterInfo = TransporterInfo.from(worldObj.provider.dimensionId, this);
				linkedTransporter.fetchLinkedTransporter();
			}
		}
		
		this.markDirty();
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

}
