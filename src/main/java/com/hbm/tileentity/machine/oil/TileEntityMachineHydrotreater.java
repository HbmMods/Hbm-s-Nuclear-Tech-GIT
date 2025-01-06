package com.hbm.tileentity.machine.oil;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.container.ContainerMachineHydrotreater;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineHydrotreater;
import com.hbm.inventory.recipes.HydrotreatingRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Triplet;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineHydrotreater extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardTransceiver, IPersistentNBT, IGUIProvider, IFluidCopiable {
	
	public long power;
	public static final long maxPower = 1_000_000;
	
	public FluidTank[] tanks;

	public TileEntityMachineHydrotreater() {
		super(11);
		
		this.tanks = new FluidTank[4];
		this.tanks[0] = new FluidTank(Fluids.OIL, 64_000);
		this.tanks[1] = new FluidTank(Fluids.HYDROGEN, 64_000).withPressure(1);
		this.tanks[2] = new FluidTank(Fluids.OIL_DS, 24_000);
		this.tanks[3] = new FluidTank(Fluids.SOURGAS, 24_000);
	}

	@Override
	public String getName() {
		return "container.hydrotreater";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0) this.updateConnections();
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			tanks[0].setType(9, slots);
			
			tanks[0].loadTank(1, 2, slots);
			tanks[1].loadTank(3, 4, slots);
			
			if(worldObj.getTotalWorldTime() % 2 == 0) reform();

			tanks[2].unloadTank(5, 6, slots);
			tanks[3].unloadTank(7, 8, slots);
			
			for(DirPos pos : getConPos()) {
				for(int i = 2; i < 4; i++) {
					if(tanks[i].getFill() > 0) {
						this.sendFluid(tanks[i], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					}
				}
			}
			
			this.networkPackNT(25);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		for(int i = 0; i < 4; i++) tanks[i].serialize(buf);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		for(int i = 0; i < 4; i++) tanks[i].deserialize(buf);
	}
	
	private void reform() {
		
		Triplet<FluidStack, FluidStack, FluidStack> out = HydrotreatingRecipes.getOutput(tanks[0].getTankType());
		if(out == null) {
			tanks[2].setTankType(Fluids.NONE);
			tanks[3].setTankType(Fluids.NONE);
			return;
		}

		tanks[1].withPressure(out.getX().pressure).setTankType(out.getX().type);
		tanks[2].setTankType(out.getY().type);
		tanks[3].setTankType(out.getZ().type);
		
		if(power < 20_000) return;
		if(tanks[0].getFill() < 100) return;
		if(tanks[1].getFill() < out.getX().fill) return;
		if(slots[10] == null || slots[10].getItem() != ModItems.catalytic_converter) return;

		if(tanks[2].getFill() + out.getY().fill > tanks[2].getMaxFill()) return;
		if(tanks[3].getFill() + out.getZ().fill > tanks[3].getMaxFill()) return;

		tanks[0].setFill(tanks[0].getFill() - 100);
		tanks[1].setFill(tanks[1].getFill() - out.getX().fill);
		tanks[2].setFill(tanks[2].getFill() + out.getY().fill);
		tanks[3].setFill(tanks[3].getFill() + out.getZ().fill);
		
		power -= 20_000;
	}
	
	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[1].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {
		
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord + 1, Library.POS_X),
				new DirPos(xCoord + 2, yCoord, zCoord - 1, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord + 1, Library.NEG_X),
				new DirPos(xCoord - 2, yCoord, zCoord - 1, Library.NEG_X),
				new DirPos(xCoord + 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord - 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord + 1, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord - 1, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
		tanks[0].readFromNBT(nbt, "t0");
		tanks[1].readFromNBT(nbt, "t1");
		tanks[2].readFromNBT(nbt, "t2");
		tanks[3].readFromNBT(nbt, "t3");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		tanks[0].writeToNBT(nbt, "t0");
		tanks[1].writeToNBT(nbt, "t1");
		tanks[2].writeToNBT(nbt, "t2");
		tanks[3].writeToNBT(nbt, "t3");
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
					yCoord + 7,
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

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }
	@Override public FluidTank[] getAllTanks() { return tanks; }
	@Override public FluidTank[] getSendingTanks() { return new FluidTank[] {tanks[2], tanks[3]}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {tanks[0], tanks[1]}; }
	@Override public boolean canConnect(ForgeDirection dir) { return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN; }
	@Override public boolean canConnect(FluidType type, ForgeDirection dir) { return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN; }

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		if(tanks[0].getFill() == 0 && tanks[1].getFill() == 0 && tanks[2].getFill() == 0 && tanks[3].getFill() == 0) return;
		NBTTagCompound data = new NBTTagCompound();
		for(int i = 0; i < 4; i++) this.tanks[i].writeToNBT(data, "" + i);
		nbt.setTag(NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(NBT_PERSISTENT_KEY);
		for(int i = 0; i < 4; i++) this.tanks[i].readFromNBT(data, "" + i);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineHydrotreater(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineHydrotreater(player.inventory, this);
	}

	@Override
	public FluidTank getTankToPaste() {
		return tanks[0];
	}
}
