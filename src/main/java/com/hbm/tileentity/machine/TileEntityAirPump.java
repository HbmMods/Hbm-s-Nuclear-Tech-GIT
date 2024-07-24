package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.handler.ThreeInts;
import com.hbm.handler.atmosphere.AtmosphereBlob;
import com.hbm.handler.atmosphere.ChunkAtmosphereManager;
import com.hbm.handler.atmosphere.IAtmosphereProvider;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.fluid.IFluidStandardReceiver;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityAirPump extends TileEntityMachineBase implements IFluidStandardReceiver, IAtmosphereProvider {

	private int onTicks = 0;
	private boolean registered = false;

	// When a pump loads, this sets a timer for a bubble to reinitialise, just in case it fails to seal for any reason
	private int recovering = 0;

	private AtmosphereBlob currentBlob;
	private int blobFillAmount = 0;

	public FluidTank tank;

	private Random rand = new Random();

	// Used for synchronizing printable info
	public CBT_Atmosphere currentAtmosphere;

	public TileEntityAirPump() {
		super(1);
		tank = new FluidTank(Fluids.OXYGEN, 16000);
	}
	
	@Override
	public World getWorld() {
		return worldObj;
	}
	
	public void spawnParticles() {

		if(worldObj.getTotalWorldTime() % 2 == 0) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "tower");
			data.setFloat("lift", 0.1F);
			data.setFloat("base", 0.3F);
			data.setFloat("max", 1F);
			data.setInteger("life", 20 + worldObj.rand.nextInt(20));
			data.setInteger("color",0x98bdf9);

			data.setDouble("posX", xCoord + 0.5 + worldObj.rand.nextDouble() - 0.5);
			data.setDouble("posZ", zCoord + 0.5 + worldObj.rand.nextDouble() -0.5);
			data.setDouble("posY", yCoord + 1);
			
			MainRegistry.proxy.effectNT(data);

		}
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
	        if (worldObj.getBlock(xCoord, yCoord + 1, zCoord).isAir(worldObj, xCoord, yCoord + 1, zCoord)) {
	            if (onTicks > 0) onTicks--;

	            if (tank.getFill() >= 20) {
	                onTicks = 20;

					if(!registered) {
						ChunkAtmosphereManager.proxy.registerAtmosphere(this);
						registered = true;

						if(blobFillAmount > 1) {
							recovering = 20;
						}
					} else if(recovering > 0) {
						recovering--;
						if(currentBlob != null) {
							recovering = 0;
						}
					} else {
						if(currentBlob != null) {
							int size = currentBlob.getBlobSize();
							if(size != 0) {
								if(blobFillAmount > size)
									blobFillAmount = size;
	
								// Fill the blob from the tank, 1mB per block
								int toFill = Math.min(size - blobFillAmount, 20);
								blobFillAmount += toFill;
	
								// Fill to the brim, and then trickle randomly afterwards
								if(toFill > 0) {
									tank.setFill(tank.getFill() - toFill);
								} else if(rand.nextBoolean()) {
									tank.setFill(tank.getFill() - 1);
								}
							} else {
								currentBlob = null;
							}
						}
						
						if(currentBlob == null) {
							// Venting to vacuum
							tank.setFill(tank.getFill() - 20);
							blobFillAmount = 0;
						}
					}
	            } else {
					if(registered) {
						ChunkAtmosphereManager.proxy.unregisterAtmosphere(this);
						registered = false;
						currentBlob = null;
						blobFillAmount = 0;
					}
				}
	        }

			if(worldObj.getTotalWorldTime() % 5 == 0) {
				currentAtmosphere = ChunkAtmosphereManager.proxy.getAtmosphere(worldObj, xCoord, yCoord, zCoord);
			}

			subscribeToAllAround(tank.getTankType(), this);

			this.networkPackNT(100);
			
		} else {
			if(onTicks > 0) {
				this.spawnParticles();
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(registered) {
			ChunkAtmosphereManager.proxy.unregisterAtmosphere(this);
			registered = false;
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(onTicks);
		buf.writeInt(blobFillAmount);
		tank.serialize(buf);

		if(currentAtmosphere != null) {
			buf.writeBoolean(true);
			currentAtmosphere.writeToBytes(buf);
		} else {
			buf.writeBoolean(false);
		}
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		onTicks = buf.readInt();
		blobFillAmount = buf.readInt();
		tank.deserialize(buf);

		if(buf.readBoolean()) {
			currentAtmosphere = new CBT_Atmosphere();
			currentAtmosphere.readFromBytes(buf);
		} else {
			currentAtmosphere = null;
		}
	}


	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tank.readFromNBT(nbt, "at");
		blobFillAmount = nbt.getInteger("fill");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tank.writeToNBT(nbt, "at");
		nbt.setInteger("fill", blobFillAmount);
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}
	@Override
	public String getName() {
		return "container.atmosphereVent";
	}


	@Override
	public int getMaxBlobRadius() {
		return 256;
	}

	@Override
	public ThreeInts getRootPosition() {
		return new ThreeInts(xCoord, yCoord, zCoord);
	}

	@Override
	public FluidType getFluidType() {
		return tank.getTankType();
	}

	@Override
	public double getFluidPressure() {
		if(currentBlob == null || currentBlob.getBlobSize() == 0) return 0;

		return ((double)blobFillAmount / (double)currentBlob.getBlobSize()) * 0.2;
	}

	public boolean hasSeal() {
		return blobFillAmount > 1;
	}

	@Override
	public void onBlobCreated(AtmosphereBlob blob) {
		currentBlob = blob;
	}

	@Override
	public void consume(int amount) {
		blobFillAmount -= amount;
		if(blobFillAmount < 1) blobFillAmount = 1;
	}

}
