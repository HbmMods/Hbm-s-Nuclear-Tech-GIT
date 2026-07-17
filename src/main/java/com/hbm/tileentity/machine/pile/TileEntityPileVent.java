package com.hbm.tileentity.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.pile.BlockPile;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.machine.pile.TileEntityPileCore.PileChannel;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Compat;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPileVent extends TileEntityPileDeviceBase implements IFluidStandardReceiverMK2 {
	
	public FluidTank compair;
	public boolean isActive = false;
	
	public float fan;
	public float lastFan;
	
	public TileEntityPileVent() {
		this.compair = new FluidTank(Fluids.AIR, 4_000).withPressure(1);
	}

	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {compair}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {compair}; }
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		if(type != compair.getTankType()) return false;
		ForgeDirection conDir = getOrientation();
		return dir == conDir;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			ForgeDirection dir = getOrientation();
			this.trySubscribe(compair.getTankType(), worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir);
			
			this.isActive = false;
			
			int x = xCoord - dir.offsetX;
			int y = yCoord;
			int z = zCoord - dir.offsetZ;

			if(worldObj.getBlock(x, y, z) == ModBlocks.pile_block && worldObj.getBlockMetadata(x, y, z) == BlockPile.META_AIR_IN) {
				TileEntity tile = Compat.getTileStandard(worldObj, x, y, z);

				if(tile instanceof TileEntityPileBaseMK2) {
					TileEntityPileBaseMK2 pile = (TileEntityPileBaseMK2) tile;
					TileEntityPileCore core = pile.getCore();

					if(core != null) {
						PileChannel ventChan = core.getVentilationChannel(x, y, z);

						if(ventChan != null) {
							this.chanNum = core.getVentilationChannelNum(ventChan);
							int toFill = BobMathUtil.min(compair.getFill(), 1_000 - ventChan.air);
							ventChan.air += toFill;
							this.compair.setFill(this.compair.getFill() - toFill);
							this.isActive = toFill > 0;
						}
					}
				}
			}
			
			this.networkPackNT(35);
			
		} else {
			
			this.lastFan = fan;
			if(this.isActive) {
				this.fan += 45;
				if(worldObj.rand.nextInt(20) == 0) worldObj.spawnParticle("cloud", xCoord + 0.5, yCoord + 1, zCoord + 0.5, 0, 0.05, 0);
			}
			
			if(this.fan >= 360) {
				this.lastFan -= 360;
				this.fan -= 360;
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(this.isActive);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.isActive = buf.readBoolean();
	}
}
