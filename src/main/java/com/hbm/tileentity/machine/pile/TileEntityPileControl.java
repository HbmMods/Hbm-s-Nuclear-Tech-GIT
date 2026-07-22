package com.hbm.tileentity.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.pile.BlockPile;
import com.hbm.tileentity.machine.pile.TileEntityPileCore.PileChannel;
import com.hbm.util.Compat;

import api.hbm.redstoneoverradio.IRORInteractive;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPileControl extends TileEntityPileDeviceBase implements IRORInteractive {
	
	public double syncLevel;
	public double level;
	public double lastLevel;
	
	public int turnProgress;
	
	public double targetLevel;
	public static final double SPEED = 1D / 60D; // full traverse takes 3s
	public boolean wasRedstone;
	
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
			
			if(canMove && this.level != this.targetLevel) {
				if(Math.abs(level - targetLevel) <= SPEED) {
					this.level = this.targetLevel;
				} else if(level < targetLevel) {
					this.level += SPEED;
				} else if(level > targetLevel) {
					this.level -= SPEED;
				}
			}
			
			ForgeDirection dir = this.getOrientation();
			boolean redstone = worldObj.getIndirectPowerOutput(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir.getOpposite().ordinal());

			if(redstone && !wasRedstone) this.setTarget(1D);
			if(!redstone && wasRedstone) this.setTarget(0D);
			
			this.wasRedstone = redstone;
			
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
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		double lastSync = this.syncLevel;
		this.syncLevel = buf.readDouble();

		if(this.syncLevel != lastSync) this.turnProgress = 2;
	}

	@Override
	public String[] getFunctionInfo() {
		return new String[] {
				PREFIX_FUNCTION + "setrods" + NAME_SEPARATOR + "percent",
				PREFIX_FUNCTION + "extendrods" + NAME_SEPARATOR + "percent"
		};
	}

	@Override
	public String runRORFunction(String name, String[] params) {

		if((PREFIX_FUNCTION + "setrods").equals(name) && params.length > 0) {
			int percent = IRORInteractive.parseInt(params[0], 0, 100);
			this.setTarget(percent / 100D);
			this.markChanged();
			return null;
		}

		if((PREFIX_FUNCTION + "extendrods").equals(name) && params.length > 0) {
			int percent = IRORInteractive.parseInt(params[0], -100, 100);
			this.setTarget(MathHelper.clamp_double(this.targetLevel + percent / 100D, 0D, 1D));
			this.markChanged();
			return null;
		}

		return null;
	}

	public void setTarget(double target) {
		this.targetLevel = target;
	}
}
