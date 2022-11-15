package com.hbm.tileentity.machine;

import java.util.HashMap;

import api.hbm.fluid.IFluidStandardReceiver;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.SILEXRecipes;
import com.hbm.inventory.recipes.SILEXRecipes.SILEXRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;
import com.hbm.util.WeightedRandomObject;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySILEX extends TileEntityMachineBase implements IFluidAcceptor, IFluidStandardReceiver {

	public EnumWavelengths mode = EnumWavelengths.NULL;
	public boolean hasLaser;
	public FluidTank tank;
	public ComparableStack current;
	public int currentFill;
	public static final int maxFill = 16000;
	public int progress;
	public final int processTime = 100;

	// 0: Input
	// 1: Fluid ID
	// 2-3: Fluid Containers
	// 4: Output
	// 5-10: Queue

	public TileEntitySILEX() {
		super(11);
		tank = new FluidTank(Fluids.ACID, 16000, 0);
	}

	@Override
	public String getName() {
		return "container.machineSILEX";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			tank.setType(1, 1, slots);
			tank.loadTank(2, 3, slots);
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.UP);
			this.trySubscribe(tank.getTankType(), worldObj, xCoord + dir.offsetX * 2, yCoord + 1, zCoord + dir.offsetZ * 2, dir);
			this.trySubscribe(tank.getTankType(), worldObj, xCoord - dir.offsetX * 2, yCoord + 1, zCoord - dir.offsetZ * 2, dir.getOpposite());

			loadFluid();

			if(!process()) {
				this.progress = 0;
			}

			dequeue();

			if(currentFill <= 0) {
				current = null;
			}

			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("fill", currentFill);
			data.setInteger("progress", progress);
			data.setString("mode", mode.toString());

			if(this.current != null) {
				data.setInteger("item", Item.getIdFromItem(this.current.item));
				data.setInteger("meta", this.current.meta);
			}

			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			this.networkPack(data, 50);

			this.mode = EnumWavelengths.NULL;
		}
	}

	public void networkUnpack(NBTTagCompound nbt) {

		this.currentFill = nbt.getInteger("fill");
		this.progress = nbt.getInteger("progress");
		this.mode = EnumWavelengths.valueOf(nbt.getString("mode"));

		if(this.currentFill > 0) {
			this.current = new ComparableStack(Item.getItemById(nbt.getInteger("item")), 1, nbt.getInteger("meta"));

		} else {
			this.current = null;
		}
	}

	public void handleButtonPacket(int value, int meta) {

		this.currentFill = 0;
		this.current = null;
	}

	public int getProgressScaled(int i) {
		return (progress * i) / processTime;
	}

	public int getFluidScaled(int i) {
		return (tank.getFill() * i) / tank.getMaxFill();
	}

	public int getFillScaled(int i) {
		return (currentFill * i) / maxFill;
	}

	public static final HashMap<FluidType, ComparableStack> fluidConversion = new HashMap();

	static {
		putFluid(Fluids.UF6);
		putFluid(Fluids.PUF6);
		putFluid(Fluids.DEATH);
	}

	private static void putFluid(FluidType fluid) {
		fluidConversion.put(fluid, new ComparableStack(ModItems.fluid_icon, 1, fluid.getID()));
	}

	int loadDelay;

	public void loadFluid() {

		ComparableStack conv = fluidConversion.get(tank.getTankType());

		if(conv != null) {

			if(currentFill == 0) {
				current = (ComparableStack) conv.copy();
			}

			if(current != null && current.equals(conv)) {

				int toFill = Math.min(10, Math.min(maxFill - currentFill, tank.getFill()));
				currentFill += toFill;
				tank.setFill(tank.getFill() - toFill);
			}
		}

		loadDelay++;

		if(loadDelay > 20)
			loadDelay = 0;

		if(loadDelay == 0 && slots[0] != null && tank.getTankType() == Fluids.ACID && (this.current == null || this.current.equals(new ComparableStack(slots[0]).makeSingular()))) {
			SILEXRecipe recipe = SILEXRecipes.getOutput(slots[0]);

			if(recipe == null)
				return;

			int load = recipe.fluidProduced;

			if(load <= this.maxFill - this.currentFill && load <= tank.getFill()) {
				this.currentFill += load;
				this.current = new ComparableStack(slots[0]).makeSingular();
				tank.setFill(tank.getFill() - load);
				this.decrStackSize(0, 1);
			}
		}
	}

	private boolean process() {

		if(current == null || currentFill <= 0)
			return false;

		SILEXRecipe recipe = SILEXRecipes.getOutput(current.toStack());

		if(recipe == null)
			return false;

		if(recipe.laserStrength.ordinal() > this.mode.ordinal())
			return false;

		if(currentFill < recipe.fluidConsumed)
			return false;

		if(slots[4] != null)
			return false;

		int progressSpeed = (int) Math.pow(2, this.mode.ordinal() - recipe.laserStrength.ordinal() + 1) / 2;

		progress += progressSpeed;

		if(progress >= processTime) {

			currentFill -= recipe.fluidConsumed;

			ItemStack out = ((WeightedRandomObject) WeightedRandom.getRandomItem(worldObj.rand, recipe.outputs)).asStack();
			slots[4] = out.copy();
			progress = 0;
			this.markDirty();
		}

		return true;
	}

	private void dequeue() {

		if(slots[4] != null) {

			for(int i = 5; i < 11; i++) {

				if(slots[i] != null && slots[i].stackSize < slots[i].getMaxStackSize() && InventoryUtil.doesStackDataMatch(slots[4], slots[i])) {
					slots[i].stackSize++;
					this.decrStackSize(4, 1);
					return;
				}
			}

			for(int i = 5; i < 11; i++) {

				if(slots[i] == null) {
					slots[i] = slots[4].copy();
					slots[4] = null;
					return;
				}
			}
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] { 0, 5, 6, 7, 8, 9, 10 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {

		if(i == 0)
			return SILEXRecipes.getOutput(itemStack) != null;

		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return slot >= 5;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tank.readFromNBT(nbt, "tank");
		this.currentFill = nbt.getInteger("fill");
		this.mode = EnumWavelengths.valueOf(nbt.getString("mode"));

		if(this.currentFill > 0) {
			this.current = new ComparableStack(Item.getItemById(nbt.getInteger("item")), 1, nbt.getInteger("meta"));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tank.writeToNBT(nbt, "tank");
		nbt.setInteger("fill", this.currentFill);
		nbt.setString("mode", mode.toString());

		if(this.current != null) {
			nbt.setInteger("item", Item.getIdFromItem(this.current.item));
			nbt.setInteger("meta", this.current.meta);
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 3, zCoord + 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
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

		if(type == tank.getTankType())
			return tank.getFill();

		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {

		if(type == tank.getTankType())
			return tank.getMaxFill();

		return 0;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}
}