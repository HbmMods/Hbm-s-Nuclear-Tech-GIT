package com.hbm.tileentity.machine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.inventory.fluid.trait.FT_Coolable.CoolingType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySteamEngine extends TileEntityLoadedBase implements IFluidAcceptor, IFluidSource, IEnergyGenerator, IFluidStandardTransceiver, INBTPacketReceiver, IConfigurableMachine {

	public long powerBuffer;

	public float rotor;
	public float lastRotor;
	private float syncRotor;
	public List<IFluidAcceptor> list2 = new ArrayList();
	public FluidTank[] tanks;

	private int turnProgress;
	private float acceleration = 0F;
	
	/* CONFIGURABLE */
	private static int steamCap = 2_000;
	private static int ldsCap = 20;
	private static double efficiency = 0.85D;
	
	public TileEntitySteamEngine() {
		
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.STEAM, steamCap, 0);
		tanks[1] = new FluidTank(Fluids.SPENTSTEAM, ldsCap, 1);
	}

	@Override
	public String getConfigName() {
		return "steamengine";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		steamCap = IConfigurableMachine.grab(obj, "I:steamCap", steamCap);
		ldsCap = IConfigurableMachine.grab(obj, "I:ldsCap", ldsCap);
		efficiency = IConfigurableMachine.grab(obj, "D:efficiency", efficiency);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:steamCap").value(steamCap);
		writer.name("I:ldsCap").value(ldsCap);
		writer.name("D:efficiency").value(efficiency);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.powerBuffer = 0;

			tanks[0].setTankType(Fluids.STEAM);
			tanks[1].setTankType(Fluids.SPENTSTEAM);
			
			NBTTagCompound data = new NBTTagCompound();
			tanks[0].writeToNBT(data, "s");

			FT_Coolable trait = tanks[0].getTankType().getTrait(FT_Coolable.class);
			double eff = trait.getEfficiency(CoolingType.TURBINE) * efficiency;
			
			int inputOps = tanks[0].getFill() / trait.amountReq;
			int outputOps = (tanks[1].getMaxFill() - tanks[1].getFill()) / trait.amountProduced;
			int ops = Math.min(inputOps, outputOps);
			tanks[0].setFill(tanks[0].getFill() - ops * trait.amountReq);
			tanks[1].setFill(tanks[1].getFill() + ops * trait.amountProduced);
			this.powerBuffer += (ops * trait.heatEnergy * eff);
			
			if(ops > 0) {
				this.acceleration += 0.1F;
			} else {
				this.acceleration -= 0.1F;
			}
			
			this.acceleration = MathHelper.clamp_float(this.acceleration, 0F, 40F);
			this.rotor += this.acceleration;
			
			if(this.rotor >= 360D) {
				this.rotor -= 360D;
				
				this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:block.steamEngineOperate", 1.0F, 0.5F + (acceleration / 80F));
			}
			
			data.setLong("power", this.powerBuffer);
			data.setFloat("rotor", this.rotor);
			tanks[1].writeToNBT(data, "w");

			for(DirPos pos : getConPos()) {
				if(this.powerBuffer > 0)
					this.sendPower(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			if(tanks[1].getFill() > 0) fillFluidInit(tanks[1].getTankType());
			
			INBTPacketReceiver.networkPack(this, data, 150);
		} else {
			this.lastRotor = this.rotor;
			
			if(this.turnProgress > 0) {
				double d = MathHelper.wrapAngleTo180_double(this.syncRotor - (double) this.rotor);
				this.rotor = (float) ((double) this.rotor + d / (double) this.turnProgress);
				--this.turnProgress;
			} else {
				this.rotor = this.syncRotor;
			}
		}
	}
	
	protected DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(xCoord + rot.offsetX * 2, yCoord + 1, zCoord + rot.offsetZ * 2, rot),
				new DirPos(xCoord + rot.offsetX * 2 + dir.offsetX, yCoord + 1, zCoord + rot.offsetZ * 2 + dir.offsetZ, rot),
				new DirPos(xCoord + rot.offsetX * 2 - dir.offsetX, yCoord + 1, zCoord + rot.offsetZ * 2 - dir.offsetZ, rot)
		};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.powerBuffer = nbt.getLong("powerBuffer");
		this.acceleration = nbt.getFloat("acceleration");
		this.tanks[0].readFromNBT(nbt, "s");
		this.tanks[1].readFromNBT(nbt, "w");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("powerBuffer", powerBuffer);
		nbt.setFloat("acceleration", acceleration);
		tanks[0].writeToNBT(nbt, "s");
		tanks[1].writeToNBT(nbt, "w");
	}

	@Override
	public void fillFluidInit(FluidType type) {
		for(DirPos pos : getConPos()) fillFluid(pos.getX(), pos.getY(), pos.getZ(), getTact(), type);
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
		return powerBuffer;
	}

	@Override
	public long getMaxPower() {
		return powerBuffer;
	}

	@Override
	public void setPower(long power) {
		this.powerBuffer = power;
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

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.powerBuffer = nbt.getLong("power");
		this.syncRotor = nbt.getFloat("rotor");
		this.turnProgress = 3; //use 3-ply for extra smoothness
		this.tanks[0].readFromNBT(nbt, "s");
		this.tanks[1].readFromNBT(nbt, "w");
	}
}
