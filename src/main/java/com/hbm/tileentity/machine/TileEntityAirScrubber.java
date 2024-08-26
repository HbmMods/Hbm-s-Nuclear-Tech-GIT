package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.handler.ThreeInts;
import com.hbm.handler.atmosphere.AtmosphereBlob;
import com.hbm.handler.atmosphere.ChunkAtmosphereManager;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.fluid.IFluidStandardSender;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityAirScrubber extends TileEntityMachineBase implements IFluidStandardSender {

	private TileEntityAirPump pump;
	public FluidTank tank;

	public TileEntityAirScrubber() {
		super(0);
		tank = new FluidTank(Fluids.CARBONDIOXIDE, 16_000);
	}

	@Override
	public String getName() {
		return "container.airScrubber";
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {

			// Fetch a new pump to scrub CO2 from
			if(worldObj.getTotalWorldTime() % 5 == 0 && (pump == null || pump.getFluidPressure() == 0 || !pump.registerScrubber(this))) {
				pump = null;

				List<AtmosphereBlob> blobs = ChunkAtmosphereManager.proxy.getBlobs(worldObj, xCoord, yCoord, zCoord);

				for(AtmosphereBlob blob : blobs) {
					if(blob != null) {
						ThreeInts pos = blob.getRootPosition();
						TileEntity te = worldObj.getTileEntity(pos.x, pos.y, pos.z);
						if(te != null && te instanceof TileEntityAirPump) {
							pump = (TileEntityAirPump) te;
							if(!pump.registerScrubber(this)) {
								pump = null;
							} else {
								break;
							}
						}
					}
				}
			}

			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if(tank.getFill() > 0) sendFluid(tank, worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
			}

			networkPackNT(20);
		}
	}

	public void scrub(int amount) {
		tank.setFill(Math.min(tank.getMaxFill(), tank.getFill() + amount));
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		tank.deserialize(buf);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tank.writeToNBT(nbt, "t");
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tank.readFromNBT(nbt, "t");
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tank };
	}
	
}
