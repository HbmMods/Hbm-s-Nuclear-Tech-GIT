package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerMachineMilkReformer;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMilkReformer;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;


public class TileEntityMachineMilkReformer extends TileEntityMachineBase implements IGUIProvider, IFluidStandardTransceiver, IEnergyReceiverMK2 {

	public FluidTank tanks[];
	public long power;
	public static final long maxPower = 100_000_000;
	
	public TileEntityMachineMilkReformer() {
		super(11);
		
		this.tanks = new FluidTank[4];
		this.tanks[0] = new FluidTank(Fluids.MILK, 64_000);
		this.tanks[1] = new FluidTank(Fluids.EMILK, 32_000);
		this.tanks[2] = new FluidTank(Fluids.CMILK, 32_000);
		this.tanks[3] = new FluidTank(Fluids.CREAM, 32_000);
	}

	@Override
	public String getName() {
		return "container.milkreformer";
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
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
		return new FluidTank[] {tanks[1], tanks[2], tanks[3]};	
		}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0]};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineMilkReformer(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMilkReformer(player.inventory, this);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.updateConnections();
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			tanks[0].loadTank(1, 2, slots);
			
			refine();

			tanks[1].unloadTank(3, 4, slots);
			tanks[2].unloadTank(5, 6, slots);
			tanks[3].unloadTank(7, 8, slots);
			
			for(DirPos pos : getConPos()) {
				for(int i = 1; i < 4; i++) {
					if(tanks[i].getFill() > 0) {
						this.sendFluid(tanks[i], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					}
				}
			}
			
			this.networkPackNT(150);
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
		power = buf.readLong();
		for(int i = 0; i < 4; i++) tanks[i].deserialize(buf);
	}
	
	private void refine() {
		
		if(power < 10_000) return;
		if(tanks[0].getFill() < 100) return;
		if(tanks[1].getFill() + 50 > tanks[1].getMaxFill()) return;
		if(tanks[2].getFill() + 35 > tanks[2].getMaxFill()) return;
		if(tanks[3].getFill() + 15 > tanks[3].getMaxFill()) return;

		power -= 10_000;
		tanks[0].setFill(tanks[0].getFill() - 100);
		tanks[1].setFill(tanks[1].getFill() + 50);
		tanks[2].setFill(tanks[2].getFill() + 35);
		tanks[3].setFill(tanks[3].getFill() + 15);
	}
	
	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
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
		tanks[0].readFromNBT(nbt, "input");
		tanks[1].readFromNBT(nbt, "m1");
		tanks[2].readFromNBT(nbt, "m2");
		tanks[3].readFromNBT(nbt, "m3");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		tanks[0].writeToNBT(nbt, "input");
		tanks[1].writeToNBT(nbt, "m1");
		tanks[2].writeToNBT(nbt, "m2");
		tanks[3].writeToNBT(nbt, "m3");
	}
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
				xCoord - 2,
				yCoord,
				zCoord - 2,
				xCoord + 3,
				yCoord + 7,
				zCoord + 3
			);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN;
	}

}
