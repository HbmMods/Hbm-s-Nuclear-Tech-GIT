package com.hbm.tileentity.machine;

import java.io.IOException;
import java.util.Random;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.inventory.fluid.trait.FT_Coolable.CoolingType;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.util.fauxpointtwelve.DirPos;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineIndustrialTurbine extends TileEntityTurbineBase implements IConfigurableMachine {

	public static int inputTankSize = 512_000;
	public static int outputTankSize = 2_048_000;
	public static double efficiency = 1D;

	public float rotor;
	public float lastRotor;
	
	public double spin = 0;
	public static double ACCELERATION = 1D / 400D;
	public long lastPowerTarget = 0;

	private AudioWrapper audio;
	private float audioDesync;

	@Override
	public String getConfigName() {
		return "steamturbineIndustrial";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		inputTankSize = IConfigurableMachine.grab(obj, "I:inputTankSize", inputTankSize);
		outputTankSize = IConfigurableMachine.grab(obj, "I:outputTankSize", outputTankSize);
		efficiency = IConfigurableMachine.grab(obj, "D:efficiency", efficiency);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("INFO").value("industrial steam turbine consumes 20% of availible steam per tick");
		writer.name("I:inputTankSize").value(inputTankSize);
		writer.name("I:outputTankSize").value(outputTankSize);
		writer.name("D:efficiency").value(efficiency);
	}

	public TileEntityMachineIndustrialTurbine() {
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.STEAM, inputTankSize);
		tanks[1] = new FluidTank(Fluids.SPENTSTEAM, outputTankSize);

		Random rand = new Random();
		audioDesync = rand.nextFloat() * 0.05F;
	}

	// sets the power target so we know how much this steam type can theoretically make, and increments the spin based on actual throughput
	@Override
	public void generatePower(long power, int steamConsumed) {
		FT_Coolable trait = tanks[0].getTankType().getTrait(FT_Coolable.class);
		double eff = trait.getEfficiency(CoolingType.TURBINE) * getEfficiency();
		int maxOps = (int) Math.ceil((tanks[0].getMaxFill() * consumptionPercent()) / trait.amountReq);
		this.lastPowerTarget = (long) (maxOps * trait.heatEnergy * eff); // theoretical max output at full blast with this type
		double fraction = (double) steamConsumed / (double) (trait.amountReq * maxOps); // % of max steam throughput currently achieved
		
		if(Math.abs(spin - fraction) <= ACCELERATION) {
			this.spin = fraction;
		} else if(spin < fraction) {
			this.spin += ACCELERATION;
		} else if(spin > fraction) {
			this.spin -= ACCELERATION;
		}
	}

	@Override
	public void onServerTick() {
		if(!operational) {
			this.spin -= ACCELERATION;
		}
		
		if(this.spin <= 0) {
			this.spin = 0;
		} else {
			this.powerBuffer = (long) (this.lastPowerTarget * this.spin);
		}
	}
	
	@Override
	public void onClientTick() {
		
		this.lastRotor = this.rotor;
		this.rotor += this.spin * 30;
		
		if(this.rotor >= 360) {
			this.lastRotor -= 360;
			this.rotor -= 360;
		}
		
		if(this.spin > 0 && MainRegistry.proxy.me().getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 35) {

			float spinNum = (float) Math.min(1F, spin * 2);
			float volume = this.getVolume(0.25F + spinNum * 0.75F);
			float pitch = 0.5F + spinNum * 0.5F + this.audioDesync;

			if(audio == null) {
				audio = MainRegistry.proxy.getLoopedSound("hbm:block.largeTurbineRunning", xCoord + 0.5F, yCoord + 0.5F, zCoord + 0.5F, volume, 20F, pitch, 20);
				audio.startSound();
			}
			
			audio.keepAlive();
			audio.updatePitch(pitch);
			audio.updateVolume(volume);
			
		} else {
			if(audio != null) {
				audio.stopSound();
				audio = null;
			}
		}
	}
	
	@Override
	public boolean canConnect(ForgeDirection dir) {
		ForgeDirection myDir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		return dir == myDir.getOpposite();
	}
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		if(!type.hasTrait(FT_Coolable.class)) return false;
		ForgeDirection myDir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		return dir != myDir && dir != myDir.getOpposite();
	}
	
	@Override public double consumptionPercent() { return 0.2D; }
	@Override public double getEfficiency() { return efficiency; }
	@Override public boolean doesResizeCompressor() { return true; }

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeDouble(this.spin);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.spin = buf.readDouble();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		lastPowerTarget = nbt.getLong("lastPowerTarget");
		spin = nbt.getDouble("spin");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("lastPowerTarget", lastPowerTarget);
		nbt.setDouble("spin", spin);
	}

	@Override
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 3 + rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 3 + rot.offsetZ * 2, rot),
				new DirPos(xCoord + dir.offsetX * 3 - rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 3 - rot.offsetZ * 2, rot.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 1 + rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 1 + rot.offsetZ * 2, rot),
				new DirPos(xCoord - dir.offsetX * 1 - rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 1 - rot.offsetZ * 2, rot.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 3, yCoord + 3, zCoord + dir.offsetZ * 3, ForgeDirection.UP),
				new DirPos(xCoord - dir.offsetX * 1, yCoord + 3, zCoord - dir.offsetZ * 1, ForgeDirection.UP),
		};
	}

	@Override
	public DirPos[] getPowerPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		return new DirPos[] {
				new DirPos(xCoord - dir.offsetX * 4, yCoord + 1, zCoord - dir.offsetZ * 4, dir.getOpposite())
		};
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 3,
					yCoord,
					zCoord - 3,
					xCoord + 4,
					yCoord + 3,
					zCoord + 4
					);
		}
		
		return bb;
	}
}
