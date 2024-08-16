package com.hbm.tileentity.machine;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.tool.ItemTransporterLinker.TransporterInfo;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BufferUtil;
import com.hbm.util.InventoryUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public abstract class TileEntityTransporterBase extends TileEntityMachineBase implements IGUIProvider, IControlReceiver, IFluidStandardTransceiver {

	private String name = "Transporter";

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

	@Override
	public void updateEntity() {
		if(worldObj.isRemote) return;

		// Set tank types and split fills
		for(int i = 0; i < inputTankMax; i++) {
			tanks[i].setType(outputSlotMax + i, slots);

			// Evenly distribute fluids between all matching tanks
			// this is so that if you are sending oxygen and are fueled by oxygen
			// it doesn't only fill one tank
			for(int o = outputTankMax; o < tanks.length; o++) {
				splitFill(tanks[i], tanks[o]);
			}
			for(int o = i + 1; o < inputTankMax; o++) {
				splitFill(tanks[i], tanks[o]);
			}
		}
		for(int i = inputTankMax; i < outputTankMax; i++) {
			for(int o = i + 1; o < outputTankMax; o++) {
				splitFill(tanks[i], tanks[o]);
			}
		}
		for(int i = outputTankMax; i < tanks.length; i++) {
			tanks[i].setType(outputSlotMax + inputTankMax + i - outputTankMax, slots);
		}
			
		if(worldObj.getTotalWorldTime() % 10 == 0) {
			updateConnections();
		}

		fetchLinkedTransporter();

		if(linkedTransporter != null && canSend(linkedTransporter)) {
			boolean isDirty = false;

			int sentItems = 0;
			int sentFluid = 0;

			// Move all items into the target
			for(int i = 0; i < inputSlotMax; i++) {
				if(slots[i] != null) {
					int beforeSize = slots[i].stackSize;
					slots[i] = InventoryUtil.tryAddItemToInventory(linkedTransporter.slots, linkedTransporter.inputSlotMax, linkedTransporter.outputSlotMax - 1, slots[i]);
					int afterSize = slots[i] != null ? slots[i].stackSize : 0;
					sentItems += beforeSize - afterSize;
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
					sentFluid += amountToSend;
					isDirty = true;
				}
			}



			hasSent(linkedTransporter, sentItems + (sentFluid / 1000));

			if(isDirty) {
				markChanged();
			}
		}

		this.networkPackNT(250);
	}

	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			for(int i = 0; i < tanks.length; i++) {
				if(tanks[i].getTankType() != Fluids.NONE) {
					trySubscribe(tanks[i].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					this.sendFluid(tanks[i], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());

				}
			}
		}
	}

	// splitting is commutative, order don't matter
	private void splitFill(FluidTank in, FluidTank out) {
		if(in.getTankType() == out.getTankType()) {
			int fill = in.getFill() + out.getFill();

			float iFill = in.getMaxFill();
			float oFill = out.getMaxFill();
			float total = iFill + oFill;
			float iFrac = iFill / total;
			float oFrac = oFill / total;

			in.setFill(MathHelper.ceiling_float_int(iFrac * (float)fill));
			out.setFill(MathHelper.floor_double(oFrac * (float)fill));

			// cap filling (this will generate 1mB of fluid in rare cases)
			if(out.getFill() == out.getMaxFill() - 1) out.setFill(out.getMaxFill());
		}
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return (FluidTank[]) Arrays.copyOfRange(tanks, inputTankMax, outputTankMax);
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		FluidTank[] inputTanks = (FluidTank[]) Arrays.copyOfRange(tanks, 0, inputTankMax);
		FluidTank[] extraTanks = (FluidTank[]) Arrays.copyOfRange(tanks, outputTankMax, tanks.length);
		return ArrayUtils.addAll(inputTanks, extraTanks);
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);

		if(linkedTransporterInfo != null) {
			buf.writeBoolean(true);
			buf.writeInt(linkedTransporterInfo.dimensionId);
			buf.writeInt(linkedTransporterInfo.x);
			buf.writeInt(linkedTransporterInfo.y);
			buf.writeInt(linkedTransporterInfo.z);
		} else {
			buf.writeBoolean(false);
		}

		for(int i = 0; i < tanks.length; i++) tanks[i].serialize(buf);

		BufferUtil.writeString(buf, name);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		linkedTransporter = null;
		if(buf.readBoolean()) {
			int id = buf.readInt();
			int x = buf.readInt();
			int y = buf.readInt();
			int z = buf.readInt();
			linkedTransporterInfo = new TransporterInfo("Linked Transporter", id, x, y, z);
		} else {
			linkedTransporterInfo = null;
		}

		for(int i = 0; i < tanks.length; i++) tanks[i].deserialize(buf);

		name = BufferUtil.readString(buf);
	}

	protected abstract DirPos[] getConPos();

	// Designated overrides for delaying sending or requiring fuel
	protected abstract boolean canSend(TileEntityTransporterBase linkedTransporter);
	protected abstract void hasSent(TileEntityTransporterBase linkedTransporter, int quantitySent);
	protected abstract void hasConnected(TileEntityTransporterBase linkedTransporter);

	// Turns items and fluids into a "mass" of sorts
	protected int itemCount() {
		int count = 0;
		for(int i = 0; i < inputSlotMax; i++) {
			if(slots[i] != null) count += slots[i].stackSize;
		}
		for(int i = 0; i < inputTankMax; i++) {
			count += tanks[i].getFill() / 1000;
		}
		return count;
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

	public void unlinkTransporter() {
		if(linkedTransporter != null) {
			linkedTransporter.linkedTransporter = null;
			linkedTransporter.linkedTransporterInfo = null;
		}

		linkedTransporter = null;
		linkedTransporterInfo = null;
	}

	// Is commutative, will automatically link and unlink its pair
	@Override
	public void receiveControl(NBTTagCompound nbt) {
		if(nbt.hasKey("name")) name = nbt.getString("name");
		if(nbt.hasKey("unlink")) {
			unlinkTransporter();
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

				hasConnected(linkedTransporter);
			}
		}
		
		this.markDirty();
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

}
