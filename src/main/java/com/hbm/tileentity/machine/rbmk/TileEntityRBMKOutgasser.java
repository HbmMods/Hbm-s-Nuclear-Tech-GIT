package com.hbm.tileentity.machine.rbmk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityRBMKOutgasser extends TileEntityRBMKSlottedBase implements IRBMKFluxReceiver, IFluidSource {

	public List<IFluidAcceptor> list = new ArrayList();
	public FluidTank gas;
	public double progress;
	public static final int duration = 10000;

	public TileEntityRBMKOutgasser() {
		super(2);
		gas = new FluidTank(FluidType.TRITIUM, 64000, 0);
	}

	@Override
	public String getName() {
		return "container.rbmkOutgasser";
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			gas.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			if(worldObj.getTotalWorldTime() % 10 == 0)
				fillFluidInit(gas.getTankType());
			
			if(!canProcess()) {
				this.progress = 0;
			}
		}
		
		super.updateEntity();
	}

	@Override
	public void receiveFlux(NType type, double flux) {
		
		if(canProcess()) {
			
			if(type == NType.FAST)
				flux *= 0.2D;
			
			progress += flux * RBMKDials.getOutgasserMod(worldObj);
			
			if(progress > duration) {
				process();
				this.markDirty();
			}
		}
	}
	
	private static HashMap<Object, ItemStack> recipes = new HashMap();
	
	static {
		recipes.put("blockLithium", ItemFluidIcon.addQuantity(new ItemStack(ModItems.fluid_icon, 1, FluidType.TRITIUM.ordinal()), 10000));
		recipes.put("ingotLithium", ItemFluidIcon.addQuantity(new ItemStack(ModItems.fluid_icon, 1, FluidType.TRITIUM.ordinal()), 1000));
		recipes.put("dustLithium", ItemFluidIcon.addQuantity(new ItemStack(ModItems.fluid_icon, 1, FluidType.TRITIUM.ordinal()), 1000));
		recipes.put(new ComparableStack(ModItems.powder_lithium_tiny), ItemFluidIcon.addQuantity(new ItemStack(ModItems.fluid_icon, 1, FluidType.TRITIUM.ordinal()), 100));
		recipes.put("ingotGold", new ItemStack(ModItems.ingot_au198));
		recipes.put("nuggetGold", new ItemStack(ModItems.nugget_au198));
		recipes.put("dustGold", new ItemStack(ModItems.powder_au198));
	}
	
	private boolean canProcess() {
		
		if(slots[0] == null)
			return false;
		
		ItemStack output = getOutput(slots[0]);
		
		if(output == null)
			return false;
		
		if(output.getItem() == ModItems.fluid_icon) {
			return output.getItemDamage() == gas.getTankType().ordinal() && gas.getFill() + ItemFluidIcon.getQuantity(output) <= gas.getMaxFill();
		}
		
		if(slots[1] == null)
			return true;
		
		return slots[1].getItem() == output.getItem() && slots[1].getItemDamage() == output.getItemDamage() && slots[1].stackSize + output.stackSize <= slots[1].getMaxStackSize();
	}
	
	public static ItemStack getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return null;
		
		ComparableStack comp = new ComparableStack(stack);
		
		if(recipes.containsKey(comp))
			return recipes.get(comp);
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {
			
			if(recipes.containsKey(key))
				return recipes.get(key);
		}
		
		return null;
	}
	
	private void process() {
		
		ItemStack output = getOutput(slots[0]);
		this.decrStackSize(0, 1);
		this.progress = 0;
		
		if(output.getItem() == ModItems.fluid_icon) {
			gas.setFill(gas.getFill() + ItemFluidIcon.getQuantity(output));
			return;
		}
		
		if(slots[1] == null) {
			slots[1] = output.copy();
		} else {
			slots[1].stackSize += output.stackSize;
		}
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(worldObj) + 1, this.zCoord, getTact(), type);
		
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == ModBlocks.rbmk_loader) {

			fillFluid(this.xCoord + 1, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord - 1, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord + 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord - 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord, getTact(), type);
		}
		
		if(worldObj.getBlock(xCoord, yCoord - 2, zCoord) == ModBlocks.rbmk_loader) {

			fillFluid(this.xCoord + 1, this.yCoord - 2, this.zCoord, getTact(), type);
			fillFluid(this.xCoord - 1, this.yCoord - 2, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord + 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord - 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 3, this.zCoord, getTact(), type);
		}
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	@Deprecated //why are we still doing this?
	public boolean getTact() { return worldObj.getTotalWorldTime() % 20 < 10; }

	@Override
	public void setFillstate(int fill, int index) {

		if(index == 0)
			gas.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		
		if(type == gas.getTankType())
			gas.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {

		if(index == 0)
			gas.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		return new ArrayList() {{ add(gas); }};
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		if(type == gas.getTankType())
			return gas.getFill();
		
		return 0;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		list.clear();
	}
	
	@Override
	public void onMelt(int reduce) {
		
		int count = 4 + worldObj.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.BLANK);
		}
		
		super.onMelt(reduce);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.OUTGASSER;
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("gas", this.gas.getFill());
		data.setInteger("maxGas", this.gas.getMaxFill());
		data.setShort("type", (short)this.gas.getTankType().ordinal());
		data.setDouble("progress", this.progress);
		return data;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.progress = nbt.getDouble("progress");
		this.gas.readFromNBT(nbt, "gas");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setDouble("progress", this.progress);
		this.gas.writeToNBT(nbt, "gas");
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return getOutput(itemStack) != null && i == 0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 1;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] {0, 1};
	}
}
