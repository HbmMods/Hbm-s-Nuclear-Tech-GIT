package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.inventory.fluid.trait.FT_Coolable.CoolingType;
import com.hbm.lib.Library;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityChungus extends TileEntityLoadedBase implements IFluidAcceptor, IFluidSource, IEnergyGenerator, INBTPacketReceiver, IFluidStandardTransceiver {

	public long power;
	public static final long maxPower = 100000000000L;
	private int turnTimer;
	public float rotor;
	public float lastRotor;
	public float fanAcceleration = 0F;
	
	public List<IFluidAcceptor> list2 = new ArrayList();
	
	public FluidTank[] tanks;
	
	public TileEntityChungus() {
		
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.STEAM, 1000000000, 0);
		tanks[1] = new FluidTank(Fluids.SPENTSTEAM, 1000000000, 1);
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			boolean operational = false;
			FluidType in = tanks[0].getTankType();
			boolean valid = false;
			if(in.hasTrait(FT_Coolable.class)) {
				FT_Coolable trait = in.getTrait(FT_Coolable.class);
				double eff = trait.getEfficiency(CoolingType.TURBINE) * 0.85D; //85% efficiency
				if(eff > 0) {
					tanks[1].setTankType(trait.coolsTo);
					int inputOps = tanks[0].getFill() / trait.amountReq;
					int outputOps = (tanks[1].getMaxFill() - tanks[1].getFill()) / trait.amountProduced;
					int ops = Math.min(inputOps, outputOps);
					tanks[0].setFill(tanks[0].getFill() - ops * trait.amountReq);
					tanks[1].setFill(tanks[1].getFill() + ops * trait.amountProduced);
					this.power += (ops * trait.heatEnergy * eff);
					valid = true;
					operational = ops > 0;
				}
			}
			
			if(!valid) tanks[1].setTankType(Fluids.NONE);
			if(power > maxPower) power = maxPower;
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
			this.sendPower(worldObj, xCoord - dir.offsetX * 11, yCoord, zCoord - dir.offsetZ * 11, dir.getOpposite());
			
			for(DirPos pos : this.getConPos()) {
				this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			if(power > maxPower)
				power = maxPower;
			
			turnTimer--;
			
			if(operational)
				turnTimer = 25;
			
			this.fillFluidInit(tanks[1].getTankType());
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("type", tanks[0].getTankType().getID());
			data.setInteger("operational", turnTimer);
			this.networkPack(data, 150);
			
		} else {
			
			this.lastRotor = this.rotor;
			this.rotor += this.fanAcceleration;
				
			if(this.rotor >= 360) {
				this.rotor -= 360;
				this.lastRotor -= 360;
			}
			
			if(turnTimer > 0) {

				this.fanAcceleration = Math.max(0F, Math.min(25F, this.fanAcceleration += 0.1F));

				Random rand = worldObj.rand;
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
				ForgeDirection side = dir.getRotation(ForgeDirection.UP);
				
				for(int i = 0; i < 10; i++) {
					worldObj.spawnParticle("cloud",
							xCoord + 0.5 + dir.offsetX * (rand.nextDouble() + 1.25) + rand.nextGaussian() * side.offsetX * 0.65,
							yCoord + 2.5 + rand.nextGaussian() * 0.65,
							zCoord + 0.5 + dir.offsetZ * (rand.nextDouble() + 1.25) + rand.nextGaussian() * side.offsetZ * 0.65,
							-dir.offsetX * 0.2, 0, -dir.offsetZ * 0.2);
				}
			}
			if(turnTimer < 0) {
				this.fanAcceleration = Math.max(0F, Math.min(25F, this.fanAcceleration -= 0.1F));
			}	
		}
	}
	
	public void onLeverPull(FluidType previous) {
		for(BlockPos pos : getConPos()) {
			this.tryUnsubscribe(previous, worldObj, pos.getX(), pos.getY(), pos.getZ());
		}
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 5, yCoord + 2, zCoord + dir.offsetZ * 5, dir),
				new DirPos(xCoord + rot.offsetX * 3, yCoord, zCoord + rot.offsetZ * 3, rot),
				new DirPos(xCoord - rot.offsetX * 3, yCoord, zCoord - rot.offsetZ * 3, rot.getOpposite())
		};
	}
	
	public void networkPack(NBTTagCompound nbt, int range) {
		PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, xCoord, yCoord, zCoord), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.turnTimer = data.getInteger("operational");
		this.tanks[0].setTankType(Fluids.fromID(data.getInteger("type")));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "steam");
		power = nbt.getLong("power");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "steam");
		nbt.setLong("power", power);
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		dir = dir.getRotation(ForgeDirection.UP);

		fillFluid(xCoord + dir.offsetX * 3, yCoord, zCoord + dir.offsetZ * 3, getTact(), type);
		fillFluid(xCoord + dir.offsetX * -3, yCoord, zCoord + dir.offsetZ * -3, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	public boolean getTact() {
		return worldObj.getTotalWorldTime() % 2 == 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type == tanks[0].getTankType())
			tanks[0].setFill(i);
		else if(type == tanks[1].getTankType())
			tanks[1].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == tanks[0].getTankType())
			return tanks[0].getFill();
		else if(type == tanks[1].getTankType())
			return tanks[1].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type == tanks[0].getTankType())
			return tanks[0].getMaxFill();
		
		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
	}
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list2;
	}
	
	@Override
	public void clearFluidList(FluidType type) {
		list2.clear();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UP && dir != ForgeDirection.DOWN && dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0]};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}
}
