package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.main.MainRegistry;
import com.hbm.main.NTMSounds;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.TilePortShapes;
import com.hbm.tileentity.TilePort.PortDef;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluidmk2.IFluidStandardSenderMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineIntake extends TileEntityLoadedBase implements IEnergyReceiverMK2, IFluidStandardSenderMK2 {
	
	public FluidTank compair;
	public long power;
	public float fan = 0;
	public float prevFan = 0;
	private AudioWrapper audio;
	
	public TileEntityMachineIntake() {
		this.compair = new FluidTank(Fluids.AIR, 1_000);
	}
	
	protected PortDef[] cachedPorts;
	public PortDef[] getPorts() { if(cachedPorts == null) cachedPorts = TilePortShapes.solderer(xCoord, yCoord, zCoord, this.getBlockMetadata()); return cachedPorts; }

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			this.setupAllPorts(getPorts());
			this.updatePortPIFIFO();

			if(this.power >= this.getMaxPower() / 20) {
				this.compair.setFill(this.compair.getMaxFill());
				this.power -= this.getMaxPower() / 20;
			}
			
			this.networkPackNT(50);
			
		} else {
			
			this.prevFan = this.fan;
			
			if(this.power >= this.getMaxPower() / 20) {
				this.fan += 45;
				
				if(this.fan >= 360) {
					this.fan -= 360;
					this.prevFan -= 360;
				}

				if(audio == null) {
					audio = createAudioLoop();
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
				}

				audio.keepAlive();
				audio.updateVolume(this.getVolume(0.25F));
				
			} else {

				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}
		}
	}

	@Override public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound(NTMSounds.ELECTRIC_MOTOR_LOOP, xCoord, yCoord, zCoord, 0.25F, 10F, 1.0F, 20);
	}

	@Override public void onChunkUnload() {
		super.onChunkUnload();
		if(audio != null) { audio.stopSound(); audio = null; }
	}

	@Override public void invalidate() {
		super.invalidate();
		if(audio != null) { audio.stopSound(); audio = null; }
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		compair.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		compair.deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		compair.readFromNBT(nbt, "compair");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		compair.writeToNBT(nbt, "compair");
	}

	@Override public boolean canConnect(ForgeDirection dir) { return dir != ForgeDirection.UP && dir != ForgeDirection.DOWN; }
	@Override public boolean canConnect(FluidType type, ForgeDirection dir) { return type == Fluids.AIR && dir != ForgeDirection.UP && dir != ForgeDirection.DOWN; }

	@Override public void setPower(long i) { power = i; }
	@Override public long getPower() { return power; }
	@Override public long getMaxPower() { return 2_000; }

	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {compair}; }
	@Override public FluidTank[] getSendingTanks() { return new FluidTank[] {compair}; }

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 1,
					zCoord + 2
					);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
