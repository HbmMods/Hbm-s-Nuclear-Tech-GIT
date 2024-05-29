package com.hbm.tileentity.machine;

import java.util.stream.IntStream;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public abstract class TileEntityTransporterBase extends TileEntityMachineBase implements IGUIProvider, IControlReceiver {

	private String name = "Transporter";

	public int dimensionId = 0;
	public ResourceLocation dimensionImage;

	FluidTank[] tanks;
	
	public TileEntityTransporterBase(int slotCount, int tankCount, int tankSize) {
		super(slotCount + tankCount / 2);

		tanks = new FluidTank[tankCount];
		for(int i = 0; i < tankCount; i++) {
			tanks[i] = new FluidTank(Fluids.NONE, tankSize);
		}

		inputSlotMax = slotCount / 2;
		outputSlotMax = slotCount;
	}

	// The transporter we're sending our contents to
	private TileEntityTransporterBase linkedTransporter;
	private int linkedTransporterDimensionId;
	private int[] linkedTransporterCoords;

	protected int inputSlotMax;
	protected int outputSlotMax;

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

		if(linkedTransporter == null && linkedTransporterCoords != null) {
			getLinkedTransporter(); // Reusing the memoisation
		}

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


			if(isDirty)
				markChanged();
		}

			
		NBTTagCompound data = new NBTTagCompound();
		data.setString("name", name);
		if(linkedTransporterCoords != null) {
			data.setInteger("dimensionId", linkedTransporterDimensionId);
			data.setIntArray("linkedTo", linkedTransporterCoords);
		}
		this.networkPack(data, 250);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);

		name = nbt.getString("name");
		linkedTransporter = null;
		linkedTransporterDimensionId = nbt.getInteger("dimensionId");

		int[] coords = nbt.getIntArray("linkedTo");
		if(coords.length > 0) {
			linkedTransporterCoords = coords;
		} else {
			linkedTransporterCoords = null;
		}
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

	public TileEntityTransporterBase getLinkedTransporter() {
		if(linkedTransporter == null && linkedTransporterCoords != null) {
			World transporterWorld = DimensionManager.getWorld(linkedTransporterDimensionId);
			TileEntity te = transporterWorld.getTileEntity(linkedTransporterCoords[0], linkedTransporterCoords[1], linkedTransporterCoords[2]);
			if(te != null && te instanceof TileEntityTransporterBase) {
				linkedTransporter = (TileEntityTransporterBase) te;
			}
		}

		return linkedTransporter;
	}

	public void setLinkedTransporter(TileEntityTransporterBase linkTo) {
		if(linkTo == this) return;

		linkedTransporter = null;

		if(linkTo == null) {
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("unlink", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, xCoord, yCoord, zCoord));
		}

		int[] linkedTransporterCoords = new int[] { linkTo.xCoord, linkTo.yCoord, linkTo.zCoord };
		int linkedTransporterDimensionId = linkTo.worldObj.provider.dimensionId;

		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("dimensionId", linkedTransporterDimensionId);
		data.setIntArray("linkedTo", linkedTransporterCoords);
		PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, xCoord, yCoord, zCoord));
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
		linkedTransporterDimensionId = nbt.getInteger("dimensionId");

		int[] coords = nbt.getIntArray("linkedTo");
		if(coords.length > 0) {
			linkedTransporterCoords = coords;
		} else {
			linkedTransporterCoords = null;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("name", name);
		if(linkedTransporterCoords != null) {
			nbt.setInteger("dimensionId", linkedTransporterDimensionId);
			nbt.setIntArray("linkedTo", linkedTransporterCoords);
		}
	}

	@Override
	public void receiveControl(NBTTagCompound nbt) {
		if(nbt.hasKey("name")) name = nbt.getString("name");
		if(nbt.hasKey("unlink")) {
			linkedTransporter = null;
			linkedTransporterCoords = null;
		}
		if(nbt.hasKey("linkedTo")) {
			linkedTransporter = null;
			linkedTransporterCoords = nbt.getIntArray("linkedTo");
			linkedTransporterDimensionId = nbt.getInteger("dimensionId");
		}
		
		this.markDirty();
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

}
