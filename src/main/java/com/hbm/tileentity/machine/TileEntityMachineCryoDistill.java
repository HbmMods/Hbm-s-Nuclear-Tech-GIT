package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.container.ContainerMachineCryoDistill;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineCryoDistill;
import com.hbm.inventory.recipes.CryoRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Quartet;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyConnector;
import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidConnector;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineCryoDistill extends TileEntityMachineBase implements IEnergyUser, IFluidStandardTransceiver, IPersistentNBT, IGUIProvider, IEnergyConnector, IFluidConnector{
	
	public long power;
	public static final long maxPower = 1_000_000;
	
	public FluidTank[] tanks;

	public TileEntityMachineCryoDistill() {
		super(11);
		
		this.tanks = new FluidTank[5];
		this.tanks[0] = new FluidTank(Fluids.AIR, 64_000);
		this.tanks[1] = new FluidTank(Fluids.NITROGEN, 24_000);
		this.tanks[2] = new FluidTank(Fluids.OXYGEN, 24_000);
		this.tanks[3] = new FluidTank(Fluids.KRYPTON, 24_000);
		this.tanks[4] = new FluidTank(Fluids.CARBONDIOXIDE, 24_000);
	}

	@Override
	public String getName() {
		return "container.cryoDistillator";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.updateConnections();
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			tanks[0].setType(7, slots);
			//tanks[0].loadTank();
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			
			this.trySubscribe(worldObj, xCoord - dir.offsetX * 3 - rot.offsetX * 2, yCoord, zCoord + rot.offsetZ * 3 + dir.offsetZ * 2, dir);
			this.trySubscribe(tanks[0].getTankType(), worldObj, xCoord + dir.offsetX * 2 - rot.offsetX * 2, yCoord, zCoord + rot.offsetZ * 3 - dir.offsetZ *3, dir);
			distill();

			tanks[1].unloadTank(1, 2, slots);
			tanks[2].unloadTank(3, 4, slots);
			tanks[3].unloadTank(5, 6, slots);
			tanks[4].unloadTank(8, 9, slots);


			for(DirPos pos : getConPos()) {
				for(int i = 1; i < 5; i++) {
					if(tanks[i].getFill() > 0) {
						this.sendFluid(tanks[i], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			for(int i = 0; i < 5; i++) tanks[i].writeToNBT(data, "" + i);
			this.networkPack(data, 150);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		for(int i = 0; i < 5; i++) tanks[i].readFromNBT(nbt, "" + i);
	}
	
	private void distill() {
		
		Quartet<FluidStack, FluidStack, FluidStack, FluidStack> out = CryoRecipes.getOutput(tanks[0].getTankType());
		if(out == null) {
			tanks[1].setTankType(Fluids.NONE);
			tanks[2].setTankType(Fluids.NONE);
			tanks[3].setTankType(Fluids.NONE);
			tanks[4].setTankType(Fluids.NONE);
			return;
		}

		tanks[1].setTankType(out.getW().type);
		tanks[2].setTankType(out.getX().type);
		tanks[3].setTankType(out.getY().type);
		tanks[4].setTankType(out.getZ().type);
		
		if(power < 20_000) return;
		if(tanks[0].getFill() < 100) return;

		if(tanks[1].getFill() + out.getW().fill > tanks[1].getMaxFill()) return;
		if(tanks[2].getFill() + out.getX().fill > tanks[2].getMaxFill()) return;
		if(tanks[3].getFill() + out.getY().fill > tanks[3].getMaxFill()) return;
		if(tanks[4].getFill() + out.getZ().fill > tanks[4].getMaxFill()) return;


		tanks[0].setFill(tanks[0].getFill() - 100);
		tanks[1].setFill(tanks[1].getFill() + out.getW().fill);
		tanks[2].setFill(tanks[2].getFill() + out.getX().fill);
		tanks[3].setFill(tanks[3].getFill() + out.getY().fill);
		tanks[4].setFill(tanks[4].getFill() + out.getZ().fill);

		
		power -= 20_000;
	}
	
	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			//this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				//new DirPos(xCoord + dir.offsetX * 2 - rot.offsetX * 2, yCoord, zCoord + rot.offsetZ * 3 - dir.offsetZ *3, dir),
				new DirPos(xCoord + dir.offsetX * 2- rot.offsetX * -3, yCoord, zCoord + rot.offsetZ * -2 - dir.offsetZ *3, dir),
				new DirPos(xCoord + dir.offsetX * 2 - rot.offsetX * -2, yCoord, zCoord + rot.offsetZ * -1 - dir.offsetZ *3, dir),
				//new DirPos(xCoord - dir.offsetX * 3 - rot.offsetX * 2, yCoord, zCoord + rot.offsetZ * 3 + dir.offsetZ * 2, dir),
				new DirPos(xCoord - dir.offsetX * 3 - rot.offsetX * -2, yCoord, zCoord + rot.offsetZ * -1 + dir.offsetZ * 2, dir),
				new DirPos(xCoord - dir.offsetX * 3 - rot.offsetX * -3, yCoord, zCoord + rot.offsetZ * -2 + dir.offsetZ * 2, dir),
		};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		power = nbt.getLong("power");
		tanks[0].readFromNBT(nbt, "input");
		tanks[1].readFromNBT(nbt, "o1");
		tanks[2].readFromNBT(nbt, "o2");
		tanks[3].readFromNBT(nbt, "o3");
		tanks[4].readFromNBT(nbt, "o4");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		tanks[0].writeToNBT(nbt, "input");
		tanks[1].writeToNBT(nbt, "o1");
		tanks[2].writeToNBT(nbt, "o2");
		tanks[3].writeToNBT(nbt, "o3");
		tanks[4].writeToNBT(nbt, "o4");
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 4,
					yCoord,
					zCoord - 3,
					xCoord + 6,
					yCoord + 5,
					zCoord + 4
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
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[1], tanks[2], tanks[3], tanks[4]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0]};
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN;
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		if(tanks[0].getFill() == 0 && tanks[1].getFill() == 0 && tanks[2].getFill() == 0 && tanks[3].getFill() == 0 && tanks[4].getFill() == 0) return;
		NBTTagCompound data = new NBTTagCompound();
		for(int i = 0; i < 5; i++) this.tanks[i].writeToNBT(data, "" + i);
		nbt.setTag(NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(NBT_PERSISTENT_KEY);
		for(int i = 0; i < 5; i++) this.tanks[i].readFromNBT(data, "" + i);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineCryoDistill(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineCryoDistill(player.inventory, this);
	}
}
