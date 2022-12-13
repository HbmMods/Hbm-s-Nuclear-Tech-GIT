package com.hbm.tileentity.machine.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FT_Corrosive;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous_ART;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IOverpressurable;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineFluidTank extends TileEntityMachineBase implements IFluidContainer, IFluidSource, IFluidAcceptor, IFluidStandardTransceiver, IPersistentNBT, IOverpressurable {
	
	public FluidTank tank;
	public short mode = 0;
	public static final short modes = 4;
	public boolean hasExploded = false;
	
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList();
	
	public TileEntityMachineFluidTank() {
		super(6);
		tank = new FluidTank(Fluids.NONE, 256000, 0);
	}

	@Override
	public String getName() {
		return "container.fluidtank";
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			age++;
			
			if(age >= 20)
				age = 0;
			
			tank.setFill(TileEntityBarrel.transmitFluidFairly(worldObj, tank.getTankType(), this, tank.getFill(), this.mode == 0 || this.mode == 1, this.mode == 1 || this.mode == 2, getConPos()));
			
			if((mode == 1 || mode == 2) && (age == 9 || age == 19))
				fillFluidInit(tank.getTankType());
			
			tank.loadTank(2, 3, slots);
			tank.setType(0, 1, slots);
			
			if(tank.getFill() > 0) {
				if(tank.getTankType().isAntimatter()) {
					//worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
					worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5, true, false);
					this.hasExploded = true;
					this.tank.setFill(0);
				}
				
				if(tank.getTankType().hasTrait(FT_Corrosive.class) && tank.getTankType().getTrait(FT_Corrosive.class).isHighlyCorrosive()) {
					//worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
					this.hasExploded = true;
					this.tank.setFill(0);
				}
				this.hasExploded = true;
				if(this.hasExploded) {

					int leaking = 0;
					if(tank.getTankType().isAntimatter()) {
						leaking = tank.getFill();
					} else if(tank.getTankType().hasTrait(FT_Gaseous.class) || tank.getTankType().hasTrait(FT_Gaseous_ART.class)) {
						leaking = Math.min(tank.getFill(), tank.getMaxFill() / 100);
					} else {
						leaking = Math.min(tank.getFill(), tank.getMaxFill() / 10000);
					}
					
					updateLeak(leaking);
				}
			}
			
			tank.unloadTank(4, 5, slots);
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("mode", mode);
			data.setBoolean("hasExploded", hasExploded);
			this.networkPack(data, 50);
		}
	}
	
	public void updateLeak(int amount) {
		if(!hasExploded) return;
		if(amount <= 0) return;
		
		this.tank.getTankType().onFluidRelease(this, tank, amount);
		this.tank.setFill(Math.max(0, this.tank.getFill() - amount));
		
		if(tank.getTankType().hasTrait(FT_Flammable.class)) {
			Random rand = worldObj.rand;
			ParticleUtil.spawnGasFlame(worldObj, xCoord + rand.nextDouble(), yCoord + 0.5 + rand.nextDouble(), zCoord + rand.nextDouble(), rand.nextGaussian() * 0.2, 0.1, rand.nextGaussian() * 0.2);
		} else if(tank.getTankType().hasTrait(FT_Gaseous.class) || tank.getTankType().hasTrait(FT_Gaseous_ART.class)) {
			
			if(worldObj.getTotalWorldTime() % 5 == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tower");
				data.setFloat("lift", 1F);
				data.setFloat("base", 1F);
				data.setFloat("max", 5F);
				data.setInteger("life", 100 + worldObj.rand.nextInt(20));
				data.setInteger("color", tank.getTankType().getColor());
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, xCoord + 0.5, yCoord + 1, zCoord + 0.5), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			}
		}
	}

	@Override
	public void explode(World world, int x, int y, int z) {
		
		if(this.hasExploded) return;
		
		this.hasExploded = true;
		this.markChanged();
	}
	
	protected DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord - 1, Library.POS_X),
				new DirPos(xCoord + 2, yCoord, zCoord + 1, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord - 1, Library.NEG_X),
				new DirPos(xCoord - 2, yCoord, zCoord + 1, Library.NEG_X),
				new DirPos(xCoord - 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord + 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord - 1, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord + 1, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}
	
	public void networkUnpack(NBTTagCompound data) {
		this.mode = data.getShort("mode");
		this.hasExploded = data.getBoolean("hasExploded");
	}
	
	public void handleButtonPacket(int value, int meta) {
		
		mode = (short) ((mode + 1) % modes);
		markDirty();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(mode == 2 || mode == 3)
			return 0;
		
		return type.name().equals(this.tank.getTankType().name()) ? tank.getMaxFill() : 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord - 2, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord - 2, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getFill() : 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			tank.setFill(i);
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return this.list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		this.list.clear();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		mode = nbt.getShort("mode");
		tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setShort("mode", mode);
		tank.writeToNBT(nbt, "tank");
	}

	@Override
	public long transferFluid(FluidType type, long fluid) {
		long toTransfer = Math.min(getDemand(type), fluid);
		tank.setFill(tank.getFill() + (int) toTransfer);
		return fluid - toTransfer;
	}

	@Override
	public long getDemand(FluidType type) {
		
		if(this.mode == 2 || this.mode == 3)
			return 0;
		
		return type == tank.getTankType() ? tank.getMaxFill() - tank.getFill() : 0;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		if(tank.getFill() == 0 && !this.hasExploded) return;
		NBTTagCompound data = new NBTTagCompound();
		this.tank.writeToNBT(data, "tank");
		data.setShort("mode", mode);
		data.setBoolean("hasExploded", hasExploded);
		nbt.setTag(NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(NBT_PERSISTENT_KEY);
		this.tank.readFromNBT(data, "tank");
		this.mode = data.getShort("mode");
		this.hasExploded = data.getBoolean("hasExploded");
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return (mode == 1 || mode == 2) ? new FluidTank[] {tank} : new FluidTank[0];
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return (mode == 0 || mode == 1) ? new FluidTank[] {tank} : new FluidTank[0];
	}
}
