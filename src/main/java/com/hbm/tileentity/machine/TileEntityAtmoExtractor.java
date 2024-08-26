package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.handler.atmosphere.ChunkAtmosphereManager;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Gaseous;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardReceiver;
import api.hbm.fluid.IFluidStandardSender;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityAtmoExtractor extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardSender {

	int consumption = 200;
	public float rot;
	public float prevRot;
	private float rotSpeed;
	public long power = 0;
	public FluidTank tank;
	public List<IFluidStandardReceiver> list = new ArrayList<>();

	public TileEntityAtmoExtractor() {
		super(0);
		tank = new FluidTank(Fluids.AIR, 50000);
	}

	@Override
	public String getName() {
		return "container.air";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.updateConnections();
			
			// Extractors will not work indoors
			CBT_Atmosphere atmosphere = !ChunkAtmosphereManager.proxy.hasAtmosphere(worldObj, xCoord, yCoord, zCoord)
				? CelestialBody.getTrait(worldObj, CBT_Atmosphere.class)
				: null;

			if(atmosphere != null) {
				tank.setTankType(atmosphere.getMainFluid());
			} else {
				tank.setTankType(Fluids.NONE);
			}
			
			if(hasPower() && tank.getFill() + 100 <= tank.getMaxFill()) {
				tank.setFill(tank.getFill() + 100);
				power -= this.getMaxPower() / 100;

				FT_Gaseous.capture(worldObj, tank.getTankType(), 100);
			}

			markDirty();
			
			this.networkPackNT(50);
		} else {
			float maxSpeed = 30F;
			
			if(hasPower()) {
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

	protected void updateConnections() {
		for(DirPos pos : getConPos()) {
			trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			sendFluid(tank, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
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

	public boolean hasPower() {
		return power >= this.getMaxPower() / 100;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		tank.readFromNBT(nbt, "water");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		tank.writeToNBT(nbt, "water");
	}



	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return 1000000;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}
	
	private DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		return new DirPos[] {
				new DirPos(this.xCoord - dir.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ * 2, dir.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 2 + rot.offsetX, this.yCoord, this.zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite()),
				
				new DirPos(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ, dir),
				new DirPos(this.xCoord + dir.offsetX + rot.offsetX, this.yCoord, this.zCoord + dir.offsetZ  + rot.offsetZ, dir),
				
				new DirPos(this.xCoord - rot.offsetX, this.yCoord, this.zCoord - rot.offsetZ, rot.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX - rot.offsetX, this.yCoord, this.zCoord - dir.offsetZ - rot.offsetZ, rot.getOpposite()),
				
				new DirPos(this.xCoord + rot.offsetX * 2, this.yCoord, this.zCoord + rot.offsetZ * 2, rot),
				new DirPos(this.xCoord - dir.offsetX + rot.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ + rot.offsetZ * 2, rot),
		};
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
				xCoord - 1,
				yCoord,
				zCoord - 1,
				xCoord + 2,
				yCoord + 10,
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