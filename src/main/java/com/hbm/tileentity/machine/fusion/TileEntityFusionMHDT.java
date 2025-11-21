package com.hbm.tileentity.machine.fusion;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.PlasmaNetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFusionMHDT extends TileEntityLoadedBase implements IEnergyProviderMK2, IFluidStandardTransceiverMK2, IFusionPowerReceiver {

	protected GenNode plasmaNode;

	public long plasmaEnergy;
	public long plasmaEnergySync;
	public long power;
	
	public float rotor;
	public float prevRotor;
	public float rotorSpeed;
	public static final float ROTOR_ACCELERATION = 0.125F;
	
	public static final double PLASMA_EFFICIENCY = 1.35D;
	public static final int COOLANT_USE = 50;
	
	public FluidTank[] tanks;

	private AudioWrapper audio;
	
	public TileEntityFusionMHDT() {
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.PERFLUOROMETHYL_COLD, 4_000);
		this.tanks[1] = new FluidTank(Fluids.PERFLUOROMETHYL, 4_000);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.plasmaEnergySync = this.plasmaEnergy;
			
			if(isCool()) {
				this.power = (long) Math.floor(this.plasmaEnergy * PLASMA_EFFICIENCY);
				tanks[0].setFill(tanks[0].getFill() - COOLANT_USE);
				tanks[1].setFill(tanks[1].getFill() + COOLANT_USE);
			}
			
			for(DirPos pos : getConPos()) {
				this.tryProvide(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(tanks[0].getTankType() != Fluids.NONE) this.trySubscribe(tanks[0].getTankType(), worldObj, pos);
				if(tanks[1].getFill() > 0) this.tryProvide(tanks[1], worldObj, pos);
			}
			
			if(plasmaNode == null || plasmaNode.expired) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getOpposite();
				plasmaNode = UniNodespace.getNode(worldObj, xCoord + dir.offsetX * 6, yCoord + 2, zCoord + dir.offsetZ * 6, PlasmaNetworkProvider.THE_PROVIDER);
				
				if(plasmaNode == null) {
					plasmaNode = new GenNode(PlasmaNetworkProvider.THE_PROVIDER,
							new BlockPos(xCoord + dir.offsetX * 6, yCoord + 2, zCoord + dir.offsetZ * 6))
							.setConnections(new DirPos(xCoord + dir.offsetX * 7, yCoord + 2, zCoord + dir.offsetZ * 7, dir));
					
					UniNodespace.createNode(worldObj, plasmaNode);
				}
			}
			
			if(plasmaNode != null && plasmaNode.hasValidNet()) plasmaNode.net.addReceiver(this);
			
			this.networkPackNT(150);
			this.plasmaEnergy = 0;
			
		} else {
			
			if(this.plasmaEnergy > 0 && isCool()) this.rotorSpeed += ROTOR_ACCELERATION;
			else this.rotorSpeed -= ROTOR_ACCELERATION;
			
			this.rotorSpeed = MathHelper.clamp_float(this.rotorSpeed, 0F, 15F);
			
			this.prevRotor = this.rotor;
			this.rotor += this.rotorSpeed;
			
			if(this.rotor >= 360F) {
				this.rotor -= 360F;
				this.prevRotor -= 360F;
			}
			
			if(this.rotorSpeed > 0 && MainRegistry.proxy.me().getDistanceSq(xCoord + 0.5, yCoord + 2.5, zCoord + 0.5) < 30 * 30) {
				
				float speed = this.rotorSpeed / 15F;
				
				if(audio == null) {
					audio = MainRegistry.proxy.getLoopedSound("hbm:block.largeTurbineRunning", xCoord + 0.5F, yCoord + 1.5F, zCoord + 0.5F, getVolume(speed), 20F, speed, 20);
					audio.startSound();
				} else {
					audio.updateVolume(getVolume(speed));
					audio.updatePitch(speed);
					audio.keepAlive();
				}
				
			} else {
				
				if(audio != null) {
					if(audio.isPlaying()) audio.stopSound();
					audio = null;
				}
			}
		}
	}
	
	public boolean isCool() {
		return tanks[0].getFill() >= COOLANT_USE && tanks[1].getFill() + COOLANT_USE <= tanks[1].getMaxFill();
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 4 + rot.offsetX * 4, yCoord, zCoord + dir.offsetZ * 4 + rot.offsetZ * 4, rot),
				new DirPos(xCoord + dir.offsetX * 4 - rot.offsetX * 4, yCoord, zCoord + dir.offsetZ * 4 - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 8, yCoord + 1, zCoord + dir.offsetZ * 8, dir)
		};
	}

	@Override public boolean receivesFusionPower() { return true; }
	@Override public void receiveFusionPower(long fusionPower, double neutronPower) { this.plasmaEnergy = fusionPower; }

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(plasmaEnergySync);
		
		this.tanks[0].serialize(buf);
		this.tanks[1].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.plasmaEnergy = buf.readLong();
		
		this.tanks[0].deserialize(buf);
		this.tanks[1].deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.tanks[0].readFromNBT(nbt, "t0");
		this.tanks[1].readFromNBT(nbt, "t1");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		this.tanks[0].writeToNBT(nbt, "t0");
		this.tanks[1].writeToNBT(nbt, "t1");
	}

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

		if(!worldObj.isRemote) {
			if(this.plasmaNode != null) UniNodespace.destroyNode(worldObj, plasmaNode);
		}
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return power; }

	@Override public FluidTank[] getAllTanks() { return tanks; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {tanks[0]}; }
	@Override public FluidTank[] getSendingTanks() { return new FluidTank[] {tanks[1]}; }

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 7,
					yCoord,
					zCoord - 7,
					xCoord + 8,
					yCoord + 4,
					zCoord + 8
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
