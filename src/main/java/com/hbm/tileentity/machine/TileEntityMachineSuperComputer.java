package com.hbm.tileentity.machine;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerMachineSuperComputer;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineSuperComputer;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.module.machine.ModuleMachineSuperComputer;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IBatteryItem;
import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineSuperComputer extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardTransceiverMK2, IControlReceiver, IGUIProvider {

	public FluidTank inputTank;
	public FluidTank outputTank;
	
	public long power;
	public long maxPower = 100_000;
	public boolean didProcess = false;
	
	public ModuleMachineSuperComputer computerModule;
	
	public TileEntityMachineSuperComputer() {
		super(8);
		this.inputTank = new FluidTank(Fluids.NONE, 4_000);
		this.outputTank = new FluidTank(Fluids.NONE, 4_000);
		
		this.computerModule = new ModuleMachineSuperComputer(0, this, slots)
				.itemInput(2).itemOutput(5)
				.fluidInput(inputTank).fluidOutput(outputTank);
	}

	@Override
	public String getName() {
		return "container.machineSuperComputer";
	}

	@Override
	public void updateEntity() {
		
		if(maxPower <= 0) this.maxPower = 1_000_000;
		
		if(!worldObj.isRemote) {
			
			GenericRecipe recipe = computerModule.getRecipe();
			if(recipe != null) {
				this.maxPower = recipe.power * 100;
			}
			this.maxPower = BobMathUtil.max(this.power, this.maxPower, 100_000);
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			for(DirPos pos : getConPos()) {
				this.trySubscribe(worldObj, pos);
				if(inputTank.getTankType() != Fluids.NONE) this.trySubscribe(inputTank.getTankType(), worldObj, pos);
				if(outputTank.getFill() > 0) this.tryProvide(outputTank, worldObj, pos);
			}
			
			this.computerModule.update(1D, 1D, true, slots[1]);
			this.didProcess = this.computerModule.didProcess;
			if(this.computerModule.markDirty) this.markDirty();
			
			this.networkPackNT(100);
		}
	}
	
	public DirPos[] getConPos() {
		return new DirPos[] {
				
		};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		this.inputTank.serialize(buf);
		this.outputTank.serialize(buf);
		buf.writeLong(power);
		buf.writeLong(maxPower);
		buf.writeBoolean(didProcess);
		this.computerModule.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		boolean wasProcessing = this.didProcess;
		this.inputTank.deserialize(buf);
		this.outputTank.deserialize(buf);
		this.power = buf.readLong();
		this.maxPower = buf.readLong();
		this.didProcess = buf.readBoolean();
		this.computerModule.deserialize(buf);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.inputTank.readFromNBT(nbt, "i");
		this.outputTank.readFromNBT(nbt, "o");
		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		this.computerModule.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.inputTank.writeToNBT(nbt, "i");
		this.outputTank.writeToNBT(nbt, "o");
		nbt.setLong("power", power);
		nbt.setLong("maxPower", maxPower);
		this.computerModule.writeToNBT(nbt);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0 && stack.getItem() instanceof IBatteryItem) return true;
		if(slot == 1 && stack.getItem() == ModItems.blueprints) return true;
		if(this.computerModule.isItemValid(slot, stack)) return true; // recipe input crap
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i >= 5 || this.computerModule.isSlotClogged(i);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {2, 3, 4, 5, 6, 7};
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }

	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {inputTank}; }
	@Override public FluidTank[] getSendingTanks() { return new FluidTank[] {outputTank}; }
	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {inputTank, outputTank}; }

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerMachineSuperComputer(player.inventory, this); }
	@Override @SideOnly(Side.CLIENT) public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIMachineSuperComputer(player.inventory, this); }

	@Override public boolean hasPermission(EntityPlayer player) { return this.isUseableByPlayer(player); }

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("index") && data.hasKey("selection")) {
			int index = data.getInteger("index");
			String selection = data.getString("selection");
			if(index == 0) {
				this.computerModule.setRecipe(selection, false);
				this.markChanged();
			}
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) bb = AxisAlignedBB.getBoundingBox(xCoord - 8, yCoord, zCoord - 8, xCoord + 9, yCoord + 9, zCoord + 9);
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
