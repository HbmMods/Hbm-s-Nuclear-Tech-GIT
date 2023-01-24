package com.hbm.tileentity.network;

import net.minecraft.block.Block;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRadioTorchSender extends TileEntityRadioTorchBase {

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
			int input = worldObj.getIndirectPowerLevelTo(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, this.getBlockMetadata());
			
			Block b = worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			
			if(b.hasComparatorInputOverride()) {
				input = b.getComparatorInputOverride(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir.getOpposite().ordinal());
			}
			
			boolean shouldSend = this.polling;
			
			if(input != this.lastState) {
				this.markDirty();
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				this.lastState = input;
				shouldSend = true;
			}

			if(shouldSend && !this.channel.isEmpty()) {
				RTTYSystem.broadcast(worldObj, this.channel, this.customMap ? this.mapping[input] : (input + ""));
			}
		}
		
		super.updateEntity();
	}
}
