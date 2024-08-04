package com.hbm.tileentity.machine.oil;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerLiquefactor;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUILiquefactor;
import com.hbm.inventory.recipes.LiquefactionRecipes;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardSender;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class TileEntityMachineLiquefactor extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidSource, IFluidStandardSender, IGUIProvider, IUpgradeInfoProvider, IInfoProviderEC {

	public long power;
	public static final long maxPower = 100000;
	public static final int usageBase = 500;
	public int usage;
	public int progress;

	public static final int processTimeBase = 80;

	public int processTime;
	
	public FluidTank tank;
	
	public TileEntityMachineLiquefactor() {
		super(4);
		tank = new FluidTank(Fluids.NONE, 24000, 0);
	}

	@Override
	public String getName() {
		return "container.machineLiquefactor";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.power = Library.chargeTEFromItems(slots, 1, power, maxPower);
			tank.updateTank(this);
			
			this.updateConnections();

			UpgradeManager.eval(slots, 2, 3);
			int speed = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			int power = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);
			int over = Math.min(UpgradeManager.getLevel(UpgradeType.OVERDRIVE), 3);

			this.processTime = processTimeBase * (4 - speed) / (1 + over) / 4;
			this.usage = usageBase * (speed + 1) * (over + 1) / (power + 1);
			
			if(this.canProcess())
				this.process();
			else
				this.progress = 0;
			
			if(worldObj.getTotalWorldTime() % 10 == 0) {
				this.fillFluidInit(tank.getTankType());
			}
			
			this.sendFluid();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progress", this.progress);
			data.setInteger("usage", this.usage);
			data.setInteger("processTime", this.processTime);
			this.networkPack(data, 50);
		}
	}
	
	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private void sendFluid() {
		for(DirPos pos : getConPos()) {
			this.sendFluid(tank, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private DirPos[] getConPos() {
		return new DirPos[] {
			new DirPos(xCoord, yCoord + 4, zCoord, Library.POS_Y),
			new DirPos(xCoord, yCoord - 1, zCoord, Library.NEG_Y),
			new DirPos(xCoord + 2, yCoord + 1, zCoord, Library.POS_X),
			new DirPos(xCoord - 2, yCoord + 1, zCoord, Library.NEG_X),
			new DirPos(xCoord, yCoord + 1, zCoord + 2, Library.POS_Z),
			new DirPos(xCoord, yCoord + 1, zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 0 && LiquefactionRecipes.getOutput(itemStack) != null;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}
	
	public boolean canProcess() {
		
		if(this.power < usage)
			return false;
		
		if(slots[0] == null)
			return false;
		
		FluidStack out = LiquefactionRecipes.getOutput(slots[0]);
		
		if(out == null)
			return false;
		
		if(out.type != tank.getTankType() && tank.getFill() > 0)
			return false;
		
		if(out.fill + tank.getFill() > tank.getMaxFill())
			return false;
		
		return true;
	}
	
	public void process() {
		
		this.power -= usage;
		
		progress++;
		
		if(progress >= processTime) {
			
			FluidStack out = LiquefactionRecipes.getOutput(slots[0]);
			tank.setTankType(out.type);
			tank.setFill(tank.getFill() + out.fill);
			this.decrStackSize(0, 1);
			
			progress = 0;
			
			this.markDirty();
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);
		
		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
		this.usage = nbt.getInteger("usage");
		this.processTime = nbt.getInteger("processTime");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tank.writeToNBT(nbt, "tank");
	}

	@Override
	public void setPower(long power) {
		this.power = power;
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
	public void setFillForSync(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == tank.getTankType())
			tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type == tank.getTankType() ? tank.getFill() : 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(xCoord, yCoord - 1, zCoord, getTact(), type);
		fillFluid(xCoord, yCoord + 4, zCoord, getTact(), type);
		fillFluid(xCoord + 2, yCoord + 1, zCoord, getTact(), type);
		fillFluid(xCoord - 2, yCoord + 1, zCoord, getTact(), type);
		fillFluid(xCoord, yCoord + 1, zCoord + 2, getTact(), type);
		fillFluid(xCoord, yCoord + 1, zCoord - 2, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		return worldObj.getTotalWorldTime() % 20 < 10;
	}

	private List<IFluidAcceptor> consumers = new ArrayList();
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return consumers;
	}

	@Override
	public void clearFluidList(FluidType type) {
		consumers.clear();
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
					yCoord + 4,
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

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerLiquefactor(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUILiquefactor(player.inventory, this);
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.POWER || type == UpgradeType.OVERDRIVE;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_liquefactor));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level * 25) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 100) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (100 - 100 / (level + 1)) + "%"));
		}
		if(type == UpgradeType.OVERDRIVE) {
			info.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_GRAY) + "YES");
		}
	}

	@Override
	public int getMaxLevel(UpgradeType type) {
		if(type == UpgradeType.SPEED) return 3;
		if(type == UpgradeType.POWER) return 3;
		if(type == UpgradeType.OVERDRIVE) return 3;
		return 0;
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setBoolean(CompatEnergyControl.B_ACTIVE, this.progress > 0);
		data.setDouble(CompatEnergyControl.D_CONSUMPTION_HE, this.usage);
	}
}
