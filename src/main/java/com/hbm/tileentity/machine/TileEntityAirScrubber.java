package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.handler.ThreeInts;
import com.hbm.handler.atmosphere.AtmosphereBlob;
import com.hbm.handler.atmosphere.ChunkAtmosphereManager;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardSender;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityAirScrubber extends TileEntityMachineBase implements IFluidStandardSender, IEnergyReceiverMK2 {

	private TileEntityAirPump pump;
	public FluidTank tank;

	private long power;

	public float rot;
	public float prevRot;
	private float rotSpeed;

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

			if(canOperate()) {
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
			}

			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
				if(tank.getFill() > 0) sendFluid(tank, worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
			}

			networkPackNT(20);
		} else {
			float maxSpeed = 30F;
			
			if(canOperate()) {
				rotSpeed += 0.2;
				if(rotSpeed > maxSpeed) rotSpeed = maxSpeed;
			} else {
				rotSpeed -= 0.1;
				if(rotSpeed < 0) rotSpeed = 0;
			}
			
			prevRot = rot;
			
			rot += rotSpeed;
			
			if(rot >= 360) {
				rot -= 360;
				prevRot -= 360;
			}
		}
	}

	public boolean canOperate() {
		return power > 200;
	}

	public void scrub(int amount) {
		if(!canOperate()) return;
		int add = Math.min(tank.getMaxFill() - tank.getFill(), amount);
		tank.setFill(tank.getFill() + add);
		power -= add * 10;
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		power = buf.readLong();
		tank.deserialize(buf);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		tank.writeToNBT(nbt, "t");
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
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

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return 10000;
	}

	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
				xCoord,
				yCoord,
				zCoord,
				xCoord + 1,
				yCoord + 2,
				zCoord + 1
			);
		}
		
		return bb;
	}
	
}
