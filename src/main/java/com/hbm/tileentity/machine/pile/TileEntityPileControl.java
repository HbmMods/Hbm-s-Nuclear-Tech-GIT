package com.hbm.tileentity.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.pile.BlockPile;
import com.hbm.tileentity.TileEntityTickingBase;
import com.hbm.tileentity.machine.pile.TileEntityPileCore.PileChannel;
import com.hbm.util.Compat;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPileControl extends TileEntityTickingBase {
	
	public double syncLevel;
	public double level;
	public double lastLevel;
	
	public int turnProgress;
	
	public int chanNum;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			boolean canMove = false;
			
			if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == ModBlocks.pile_block && worldObj.getBlockMetadata(xCoord, yCoord - 1, zCoord) == BlockPile.META_CONTROL) {

				TileEntity tile = Compat.getTileStandard(worldObj, xCoord, yCoord - 1, zCoord);
				
				if(tile instanceof TileEntityPileBaseMK2) {
					TileEntityPileBaseMK2 pile = (TileEntityPileBaseMK2) tile;
					TileEntityPileCore core = pile.getCore();
					
					if(core != null) {
						PileChannel controlChan = core.getControlChannel(xCoord, yCoord - 1, zCoord);
						
						if(controlChan != null) {
							canMove = true;
							this.chanNum = core.getControlChannelNum(controlChan);
							controlChan.control = this.level;
						}
					}
				}
			}
			
			this.networkPackNT(100);
			
		} else {

			this.lastLevel = this.level;

			if(this.turnProgress > 0) {
				this.level = this.level + ((this.syncLevel - this.level) / (double) this.turnProgress);
				--this.turnProgress;
			} else {
				this.level = this.syncLevel;
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeDouble(this.level);
		buf.writeInt(this.chanNum);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		double lastSync = this.syncLevel;
		this.syncLevel = buf.readDouble();
		this.chanNum = buf.readInt();

		if(this.syncLevel != lastSync) this.turnProgress = 2;
	}
}
