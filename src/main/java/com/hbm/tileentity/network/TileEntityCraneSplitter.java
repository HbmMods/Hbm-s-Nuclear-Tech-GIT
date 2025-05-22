package com.hbm.tileentity.network;

import com.hbm.tileentity.TileEntityLoadedBase;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCraneSplitter extends TileEntityLoadedBase {

	/* false: left belt is preferred, true: right belt is preferred */
	private boolean position;
	private byte remaining; // count until position swaps

	public byte leftRatio = 1;
	public byte rightRatio = 1;

	// Splits the input stack into two, based on current ratio and internal state
	public ItemStack[] splitStack(ItemStack stack) {
		int left = 0;
		int right = 0;
		int count = stack.stackSize;

		if(remaining <= 0) remaining = position ? rightRatio : leftRatio;

		while(count > 0) {
			int toExtract = Math.min(remaining, count);

			remaining -= toExtract;
			count -= toExtract;
			if(position) right += toExtract; else left += toExtract;

			if(remaining <= 0) {
				position = !position;
				remaining = position ? rightRatio : leftRatio;
			}
		}

		ItemStack leftStack = stack.copy();
		ItemStack rightStack = stack.copy();
		leftStack.stackSize = left;
		rightStack.stackSize = right;

		worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
		return new ItemStack[] { leftStack, rightStack };
	}

	public void updateEntity() {
		if(worldObj.isRemote) return;
		networkPackNT(15);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		position = nbt.getBoolean("pos");
		remaining = nbt.getByte("count");

		// Make sure existing conveyors are initialised with ratios
		leftRatio = (byte)Math.max(nbt.getByte("left"), 1);
		rightRatio = (byte)Math.max(nbt.getByte("right"), 1);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("pos", position);
		nbt.setByte("count", remaining);

		nbt.setByte("left", leftRatio);
		nbt.setByte("right", rightRatio);
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeByte(leftRatio);
		buf.writeByte(rightRatio);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		leftRatio = buf.readByte();
		rightRatio = buf.readByte();
	}

}
