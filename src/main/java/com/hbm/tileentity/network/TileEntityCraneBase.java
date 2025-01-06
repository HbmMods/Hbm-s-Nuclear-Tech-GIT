package com.hbm.tileentity.network;

import com.hbm.interfaces.ICopiable;
import com.hbm.tileentity.IControlReceiverFilter;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityCraneBase extends TileEntityMachineBase implements ICopiable {
	
	public TileEntityCraneBase(int scount) {
		super(scount);
	}

	// extension to the meta system
	// for compatibility purposes, normal meta values are still used by default
	private ForgeDirection outputOverride = ForgeDirection.UNKNOWN;

	// for extra stability in case the screwdriver action doesn't get synced to
	// other clients
	private ForgeDirection cachedOutputOverride = ForgeDirection.UNKNOWN;

	@Override
	public void updateEntity() {
		if(hasWorldObj() && worldObj.isRemote) {
			if(cachedOutputOverride != outputOverride) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				cachedOutputOverride = outputOverride;
			}
		}
	}

	public ForgeDirection getInputSide() {
		return ForgeDirection.getOrientation(getBlockMetadata());
	}

	public ForgeDirection getOutputSide() {
		ForgeDirection override = getOutputOverride();
		return override != ForgeDirection.UNKNOWN ? override : ForgeDirection.getOrientation(getBlockMetadata()).getOpposite();
	}

	public ForgeDirection getOutputOverride() {
		return outputOverride;
	}

	public void setOutputOverride(ForgeDirection direction) {
		ForgeDirection oldSide = getOutputSide();
		if(oldSide == direction) direction = direction.getOpposite();

		outputOverride = direction;

		if(direction == getInputSide())
			setInput(oldSide);
		else
			onBlockChanged();
	}

	public void setInput(ForgeDirection direction) {
		outputOverride = getOutputSide(); // save the current output, if it isn't saved yet

		ForgeDirection oldSide = getInputSide();
		if(oldSide == direction) direction = direction.getOpposite();

		boolean needSwapOutput = direction == getOutputSide();
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, direction.ordinal(), needSwapOutput ? 4 : 3);

		if(needSwapOutput)
			setOutputOverride(oldSide);
	}

	protected void onBlockChanged() {
		if(!hasWorldObj()) return;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		worldObj.notifyBlockChange(xCoord, yCoord, zCoord, getBlockType());
		markDirty();
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(nbt.hasKey("CraneOutputOverride", Constants.NBT.TAG_BYTE))
			outputOverride = ForgeDirection.getOrientation(nbt.getByte("CraneOutputOverride"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("CraneOutputOverride", (byte) outputOverride.ordinal());
	}

	@Override
	public NBTTagCompound getSettings(World world, int x, int y, int z) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("inputSide", getInputSide().ordinal());
		nbt.setInteger("outputSide", getOutputSide().ordinal());

		if(this instanceof IControlReceiverFilter){
			IControlReceiverFilter filter = ((IControlReceiverFilter) this);
			IInventory inv = this;
			NBTTagList tags = new NBTTagList();
			int count = 0;

			for (int i = filter.getFilterSlots()[0]; i <  filter.getFilterSlots()[1]; i++) {
				NBTTagCompound slotNBT = new NBTTagCompound();
				if(inv.getStackInSlot(i) != null) {
					slotNBT.setByte("slot", (byte) count);
					inv.getStackInSlot(i).writeToNBT(slotNBT);
					tags.appendTag(slotNBT);
				}
				count++;
			}
			nbt.setTag("items", tags);
		}

		return nbt;
	}

	@Override
	public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
		if(index == 1) {
			if (nbt.hasKey("outputSide")) {
				outputOverride = ForgeDirection.getOrientation(nbt.getInteger("outputSide"));
				onBlockChanged();
			}
			if (nbt.hasKey("inputSide")) {
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, nbt.getInteger("inputSide"), 3);
			}
		} else {
			if (this instanceof IControlReceiverFilter) {
				IControlReceiverFilter filter = ((IControlReceiverFilter) this);
				IInventory inv = this;

				NBTTagList items = nbt.getTagList("items", 10);
				int listSize = items.tagCount();
				if (listSize > 0) {
					int count = 0;
					for (int i = filter.getFilterSlots()[0]; i < filter.getFilterSlots()[1]; i++) {
						if (i < listSize) {
							NBTTagCompound slotNBT = items.getCompoundTagAt(count);
							byte slot = slotNBT.getByte("slot");
							ItemStack loadedStack = ItemStack.loadItemStackFromNBT(slotNBT);
							//whether the filter info came from a router
							boolean router = nbt.hasKey("modes") && slot > index * 5 && slot < index * + 5;
							if (loadedStack != null && (slot < filter.getFilterSlots()[1] || router)) {
								inv.setInventorySlotContents(slot + filter.getFilterSlots()[0], ItemStack.loadItemStackFromNBT(slotNBT));
								filter.nextMode(slot);
								this.getWorldObj().markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
							}
						}
						count++;
					}
				}
			}
		}
	}

	@Override
	public String[] infoForDisplay(World world, int x, int y, int z) {
		return new String[]{"copytool.filter", "copytool.orientation"};
	}
}
