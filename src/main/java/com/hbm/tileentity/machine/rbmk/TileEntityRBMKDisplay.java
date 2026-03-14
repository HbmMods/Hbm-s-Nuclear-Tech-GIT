package com.hbm.tileentity.machine.rbmk;

import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.RBMKColumn;
import com.hbm.util.BufferUtil;
import com.hbm.util.Compat;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRBMKDisplay extends TileEntityLoadedBase {

	private int targetX;
	private int targetY;
	private int targetZ;

	private byte rotation;
	
	public RBMKColumn[] columns = new RBMKColumn[7 * 7];

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			if(this.worldObj.getTotalWorldTime() % 10 == 0) {
				rescan();
				this.networkPackNT(50);
			}
		}
	}

	public void setTarget(int x, int y, int z) {
		this.targetX = x;
		this.targetY = y;
		this.targetZ = z;
		this.markDirty();
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);

		for(RBMKColumn column : this.columns) {
			if(column == null || column.type == null)
				buf.writeByte(-1);
			else {
				buf.writeByte((byte) column.type.ordinal());
				BufferUtil.writeNBT(buf, column.data);
			}
		}
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		for(int i = 0; i < this.columns.length; i++) {
			byte ordinal = buf.readByte();
			if(ordinal == -1)
				this.columns[i] = null;
			else
				this.columns[i] = new RBMKColumn(ColumnType.values()[ordinal], BufferUtil.readNBT(buf));
		}
	}

	private void rescan() {

		for(int index = 0; index < columns.length; index++) {
			int rx = getXFromIndex(index);
			int rz = getZFromIndex(index);

			TileEntity te = Compat.getTileStandard(worldObj, targetX + rx, targetY, targetZ + rz);

			if(te instanceof TileEntityRBMKBase) {

				TileEntityRBMKBase rbmk = (TileEntityRBMKBase) te;

				columns[index] = new RBMKColumn(rbmk.getConsoleType(), rbmk.getNBTForConsole());
				columns[index].data.setDouble("heat", rbmk.heat);
				columns[index].data.setDouble("maxHeat", rbmk.maxHeat());
				columns[index].data.setByte("indicator", (byte) rbmk.craneIndicator);
				
				if(te instanceof TileEntityRBMKControlManual) {
					TileEntityRBMKControlManual control = (TileEntityRBMKControlManual) te;
					if(control.color != null) {
						columns[index].data.setByte("color", (byte) control.color.ordinal());
					} else {
						columns[index].data.setByte("color", (byte) -1);
					}
				}
				
			} else {
				columns[index] = null;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.targetX = nbt.getInteger("tX");
		this.targetY = nbt.getInteger("tY");
		this.targetZ = nbt.getInteger("tZ");
		this.rotation = nbt.getByte("rotation");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("tX", this.targetX);
		nbt.setInteger("tY", this.targetY);
		nbt.setInteger("tZ", this.targetZ);
		nbt.setByte("rotation", this.rotation);
	}

	public void rotate() {
		rotation = (byte)((rotation + 1) % 4);
	}

	public int getXFromIndex(int col) {
		int i = col % 7 - 3;
		int j = col / 7 - 3;
		switch (rotation) {
			case 0: return i;
			case 1: return -j;
			case 2: return -i;
			case 3: return j;
		}
		return i;
	}

	public int getZFromIndex(int col) {
		int i = col % 7 - 3;
		int j = col / 7 - 3;
		switch (rotation) {
			case 0: return j;
			case 1: return i;
			case 2: return -j;
			case 3: return -i;
		}
		return j;
	}
}
