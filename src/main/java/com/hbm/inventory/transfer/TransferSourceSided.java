package com.hbm.inventory.transfer;

import net.minecraftforge.common.util.ForgeDirection;

public abstract class TransferSourceSided implements ITransferSource {
	
	protected ForgeDirection fromSide;

	public TransferSourceSided fromSide(ForgeDirection side) {
		this.fromSide = side;
		return this;
	}
}
