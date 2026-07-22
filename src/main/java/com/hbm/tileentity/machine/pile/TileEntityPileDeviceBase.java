package com.hbm.tileentity.machine.pile;

import com.hbm.tileentity.TileEntityTickingBase;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityPileDeviceBase extends TileEntityTickingBase {
	
	public int chanNum;
	
	public ForgeDirection getOrientation() {
		return ForgeDirection.getOrientation(this.getBlockMetadata() % 4 + 2);
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(this.chanNum);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.chanNum = buf.readInt();
	}
}
