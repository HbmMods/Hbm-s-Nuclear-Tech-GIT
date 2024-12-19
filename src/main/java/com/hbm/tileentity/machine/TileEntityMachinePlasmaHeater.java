package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineITER;
import com.hbm.inventory.container.ContainerPlasmaHeater;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIPlasmaHeater;
import com.hbm.lib.Library;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachinePlasmaHeater extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardReceiver, IGUIProvider, IFluidCopiable {
	
	public long power;
	public static final long maxPower = 100000000;
	
	public FluidTank[] tanks;
	public FluidTank plasma;

	public TileEntityMachinePlasmaHeater() {
		super(5);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.DEUTERIUM, 16_000);
		tanks[1] = new FluidTank(Fluids.TRITIUM, 16_000);
		plasma = new FluidTank(Fluids.PLASMA_DT, 64_000);
	}

	@Override
	public String getName() {
		return "container.plasmaHeater";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0)
				this.updateConnections();

			/// START Managing all the internal stuff ///
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			tanks[0].setType(1, 2, slots);
			tanks[1].setType(3, 4, slots);
			
			updateType();
			
			int maxConv = 50;
			int powerReq = 10000;
			
			int convert = Math.min(tanks[0].getFill(), tanks[1].getFill());
			convert = Math.min(convert, (plasma.getMaxFill() - plasma.getFill()) / 2);
			convert = Math.min(convert, maxConv);
			convert = (int) Math.min(convert, power / powerReq);
			convert = Math.max(0, convert);
			
			if(convert > 0 && plasma.getTankType() != Fluids.NONE) {

				tanks[0].setFill(tanks[0].getFill() - convert);
				tanks[1].setFill(tanks[1].getFill() - convert);
				
				plasma.setFill(plasma.getFill() + convert * 2);
				power -= convert * powerReq;
				
				this.markDirty();
			}
			/// END Managing all the internal stuff ///

			/// START Loading plasma into the ITER ///
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
			int dist = 11;
			
			if(worldObj.getBlock(xCoord + dir.offsetX * dist, yCoord + 2, zCoord + dir.offsetZ * dist) == ModBlocks.iter) {
				int[] pos = ((MachineITER)ModBlocks.iter).findCore(worldObj, xCoord + dir.offsetX * dist, yCoord + 2, zCoord + dir.offsetZ * dist);
				
				if(pos != null) {
					TileEntity te = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
					
					if(te instanceof TileEntityITER) {
						TileEntityITER iter = (TileEntityITER)te;
							
						if(iter.plasma.getFill() == 0 && this.plasma.getTankType() != Fluids.NONE) {
							iter.plasma.setTankType(this.plasma.getTankType());
						}
							
							if(iter.isOn) {
							
							if(iter.plasma.getTankType() == this.plasma.getTankType()) {
								
								int toLoad = Math.min(iter.plasma.getMaxFill() - iter.plasma.getFill(), this.plasma.getFill());
								toLoad = Math.min(toLoad, 40);
								this.plasma.setFill(this.plasma.getFill() - toLoad);
								iter.plasma.setFill(iter.plasma.getFill() + toLoad);
								this.markDirty();
								iter.markDirty();
							}
						}
					}
				}
			}
			
			/// END Loading plasma into the ITER ///

			/// START Notif packets ///
			this.networkPackNT(50);
			/// END Notif packets ///
		}
	}
	
	private void updateConnections()  {
		
		this.getBlockMetadata();
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.blockMetadata - BlockDummyable.offset);
		ForgeDirection side = dir.getRotation(ForgeDirection.UP);
		
		for(int i = 1; i < 4; i++) {
			for(int j = -1; j < 2; j++) {
				this.trySubscribe(worldObj, xCoord + side.offsetX * j + dir.offsetX * 2, yCoord + i, zCoord + side.offsetZ * j + dir.offsetZ * 2, j < 0 ? ForgeDirection.DOWN : ForgeDirection.UP);
				this.trySubscribe(tanks[0].getTankType(), worldObj, xCoord + side.offsetX * j + dir.offsetX * 2, yCoord + i, zCoord + side.offsetZ * j + dir.offsetZ * 2, j < 0 ? ForgeDirection.DOWN : ForgeDirection.UP);
				this.trySubscribe(tanks[1].getTankType(), worldObj, xCoord + side.offsetX * j + dir.offsetX * 2, yCoord + i, zCoord + side.offsetZ * j + dir.offsetZ * 2, j < 0 ? ForgeDirection.DOWN : ForgeDirection.UP);
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
		plasma.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
		plasma.deserialize(buf);
	}
	
	private void updateType() {
		
		List<FluidType> types = new ArrayList() {{ add(tanks[0].getTankType()); add(tanks[1].getTankType()); }};

		if(types.contains(Fluids.DEUTERIUM) && types.contains(Fluids.TRITIUM)) {
			plasma.setTankType(Fluids.PLASMA_DT);
			return;
		}
		if(types.contains(Fluids.DEUTERIUM) && types.contains(Fluids.HELIUM3)) {
			plasma.setTankType(Fluids.PLASMA_DH3);
			return;
		}
		if(types.contains(Fluids.DEUTERIUM) && types.contains(Fluids.HYDROGEN)) {
			plasma.setTankType(Fluids.PLASMA_HD);
			return;
		}
		if(types.contains(Fluids.HYDROGEN) && types.contains(Fluids.TRITIUM)) {
			plasma.setTankType(Fluids.PLASMA_HT);
			return;
		}
		if(types.contains(Fluids.HELIUM4) && types.contains(Fluids.OXYGEN)) {
			plasma.setTankType(Fluids.PLASMA_XM);
			return;
		}
		if(types.contains(Fluids.BALEFIRE) && types.contains(Fluids.AMAT)) {
			plasma.setTankType(Fluids.PLASMA_BF);
			return;
		}
		
		plasma.setTankType(Fluids.NONE);
	}
	
	public long getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		tanks[0].readFromNBT(nbt, "fuel_1");
		tanks[1].readFromNBT(nbt, "fuel_2");
		plasma.readFromNBT(nbt, "plasma");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
		tanks[0].writeToNBT(nbt, "fuel_1");
		tanks[1].writeToNBT(nbt, "fuel_2");
		plasma.writeToNBT(nbt, "plasma");
	}

	@Override
	public void setPower(long i) {
		this.power = i;
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
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tanks[0], tanks[1], plasma};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return tanks;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPlasmaHeater(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPlasmaHeater(player.inventory, this);
	}
}
