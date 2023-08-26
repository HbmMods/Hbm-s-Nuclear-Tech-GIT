package com.hbm.tileentity.network;

import com.hbm.tileentity.TileEntityMachineBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityCraneBase extends TileEntityMachineBase {
	public TileEntityCraneBase(int scount) {
		super(scount);
	}

	// extension to the meta system
	// for compatibility purposes, normal meta values are still used by default
	private ForgeDirection outputOverride = ForgeDirection.UNKNOWN;

	// for extra stability in case the screwdriver action doesn't get synced to other clients
	@SideOnly(Side.CLIENT)
	private ForgeDirection cachedOutputOverride = ForgeDirection.UNKNOWN;

	@Override
	public void updateEntity() {
		if (hasWorldObj() && worldObj.isRemote) {
			if (cachedOutputOverride != outputOverride) {
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

	public ForgeDirection cycleOutputOverride() {
		do {
			outputOverride = ForgeDirection.getOrientation(Math.floorMod(outputOverride.ordinal() - 1, 7));
		} while (outputOverride.ordinal() == getBlockMetadata());

		onBlockChanged();
		return outputOverride;
	}

	public void ensureOutputOverrideValid() {
		if (outputOverride.ordinal() == getBlockMetadata())
			cycleOutputOverride();
	}

	protected void onBlockChanged() {
		if (!hasWorldObj()) return;
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
		if (nbt.hasKey("CraneOutputOverride", Constants.NBT.TAG_BYTE))
			outputOverride = ForgeDirection.getOrientation(nbt.getByte("CraneOutputOverride"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("CraneOutputOverride", (byte) outputOverride.ordinal());
	}
}
