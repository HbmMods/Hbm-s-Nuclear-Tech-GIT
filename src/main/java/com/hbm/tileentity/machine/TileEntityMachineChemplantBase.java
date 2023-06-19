package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.ChemplantRecipes;
import com.hbm.inventory.recipes.ChemplantRecipes.ChemRecipe;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidUser;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

/**
 * Base class for single and multi chemplants.
 * Most stuff should be handled by this class automatically, given the slots and indices are defined correctly
 * Does not sync automatically, nor handle upgrades
 * Slot indices are mostly free game, but battery has to be slot 0
 * Tanks follow the order R1(I1, I2, O1, O2), R2(I1, I2, O1, O2) ...
 * @author hbm
 */
public abstract class TileEntityMachineChemplantBase extends TileEntityMachineBase implements IEnergyUser, IFluidUser, IGUIProvider {

	public long power;
	public int[] progress;
	public int[] maxProgress;
	public boolean isProgressing;
	
	public FluidTank[] tanks;
	
	int consumption = 100;
	int speed = 100;

	public TileEntityMachineChemplantBase(int scount) {
		super(scount);
		
		int count = this.getRecipeCount();

		progress = new int[count];
		maxProgress = new int[count];
		
		tanks = new FluidTank[4 * count];
		for(int i = 0; i < 4 * count; i++) {
			tanks[i] = new FluidTank(Fluids.NONE, getTankCapacity(), i);
		}
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			int count = this.getRecipeCount();
			
			this.isProgressing = false;
			this.power = Library.chargeTEFromItems(slots, 0, power, this.getMaxPower());
			
			for(int i = 0; i < count; i++) {
				loadItems(i);
				unloadItems(i);
			}

			
			for(int i = 0; i < count; i++) {
				if(!canProcess(i)) {
					this.progress[i] = 0;
				} else {
					isProgressing = true;
					process(i);
				}
			}
		}
	}
	
	protected boolean canProcess(int index) {
		
		int template = getTemplateIndex(index);
		
		if(slots[template] == null || slots[template].getItem() != ModItems.chemistry_template)
			return false;
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(slots[template].getItemDamage());
		
		if(recipe == null)
			return false;
		
		setupTanks(recipe, index);
		
		if(this.power < this.consumption) return false;
		if(!hasRequiredFluids(recipe, index)) return false;
		if(!hasSpaceForFluids(recipe, index)) return false;
		if(!hasRequiredItems(recipe, index)) return false;
		if(!hasSpaceForItems(recipe, index)) return false;
		
		return true;
	}
	
	private void setupTanks(ChemRecipe recipe, int index) {
		if(recipe.inputFluids[0] != null) tanks[index * 4].setTankType(recipe.inputFluids[0].type);
		if(recipe.inputFluids[1] != null) tanks[index * 4 + 1].setTankType(recipe.inputFluids[1].type);
		if(recipe.outputFluids[0] != null) tanks[index * 4 + 2].setTankType(recipe.outputFluids[0].type);
		if(recipe.outputFluids[1] != null) tanks[index * 4 + 3].setTankType(recipe.outputFluids[1].type);
	}
	
	private boolean hasRequiredFluids(ChemRecipe recipe, int index) {
		if(recipe.inputFluids[0] != null && tanks[index * 4].getFill() < recipe.inputFluids[0].fill) return false;
		if(recipe.inputFluids[1] != null && tanks[index * 4 + 1].getFill() < recipe.inputFluids[1].fill) return false;
		return true;
	}
	
	private boolean hasSpaceForFluids(ChemRecipe recipe, int index) {
		if(recipe.outputFluids[0] != null && tanks[index * 4 + 2].getFill() + recipe.outputFluids[0].fill > tanks[index * 4 + 2].getMaxFill()) return false;
		if(recipe.outputFluids[1] != null && tanks[index * 4 + 3].getFill() + recipe.outputFluids[1].fill > tanks[index * 4 + 3].getMaxFill()) return false;
		return true;
	}
	
	private boolean hasRequiredItems(ChemRecipe recipe, int index) {
		int[] indices = getSlotIndicesFromIndex(index);
		return InventoryUtil.doesArrayHaveIngredients(slots, indices[0], indices[1], recipe.inputs);
	}
	
	private boolean hasSpaceForItems(ChemRecipe recipe, int index) {
		int[] indices = getSlotIndicesFromIndex(index);
		return InventoryUtil.doesArrayHaveSpace(slots, indices[2], indices[3], recipe.outputs);
	}
	
	protected void process(int index) {
		
		this.power -= this.consumption;
		this.progress[index]++;
		
		if(slots[0] != null && slots[0].getItem() == ModItems.meteorite_sword_machined)
			slots[0] = new ItemStack(ModItems.meteorite_sword_treated); //fisfndmoivndlmgindgifgjfdnblfm
		
		int template = getTemplateIndex(index);
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(slots[template].getItemDamage());
		
		this.maxProgress[index] = recipe.getDuration() * this.speed / 100;
		
		if(this.progress[index] >= this.maxProgress[index]) {
			consumeFluids(recipe, index);
			produceFluids(recipe, index);
			consumeItems(recipe, index);
			produceItems(recipe, index);
			this.progress[index] = 0;
			this.markDirty();
		}
	}
	
	private void consumeFluids(ChemRecipe recipe, int index) {
		if(recipe.inputFluids[0] != null) tanks[index * 4].setFill(tanks[index * 4].getFill() - recipe.inputFluids[0].fill);
		if(recipe.inputFluids[1] != null) tanks[index * 4 + 1].setFill(tanks[index * 4 + 1].getFill() - recipe.inputFluids[1].fill);
	}
	
	private void produceFluids(ChemRecipe recipe, int index) {
		if(recipe.outputFluids[0] != null) tanks[index * 4 + 2].setFill(tanks[index * 4 + 2].getFill() + recipe.outputFluids[0].fill);
		if(recipe.outputFluids[1] != null) tanks[index * 4 + 3].setFill(tanks[index * 4 + 3].getFill() + recipe.outputFluids[1].fill);
	}
	
	private void consumeItems(ChemRecipe recipe, int index) {
		
		int[] indices = getSlotIndicesFromIndex(index);
		
		for(AStack in : recipe.inputs) {
			if(in != null)
				InventoryUtil.tryConsumeAStack(slots, indices[0], indices[1], in);
		}
	}
	
	private void produceItems(ChemRecipe recipe, int index) {
		
		int[] indices = getSlotIndicesFromIndex(index);
		
		for(ItemStack out : recipe.outputs) {
			if(out != null)
				InventoryUtil.tryAddItemToInventory(slots, indices[2], indices[3], out.copy());
		}
	}
	
	private void loadItems(int index) {
		
		int template = getTemplateIndex(index);
		if(slots[template] == null || slots[template].getItem() != ModItems.chemistry_template)
			return;
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(slots[template].getItemDamage());
		
		if(recipe != null) {
			
			ChunkCoordinates[] positions = getInputPositions();
			int[] indices = getSlotIndicesFromIndex(index);
			
			for(ChunkCoordinates coord : positions) {
				
				TileEntity te = worldObj.getTileEntity(coord.posX, coord.posY, coord.posZ);
				
				if(te instanceof IInventory) {
					
					IInventory inv = (IInventory) te;
					ISidedInventory sided = inv instanceof ISidedInventory ? (ISidedInventory) inv : null;
					
					for(AStack ingredient : recipe.inputs) {
						
						if(!InventoryUtil.doesArrayHaveIngredients(slots, indices[0], indices[1], ingredient)) {
							
							for(int i = 0; i < inv.getSizeInventory(); i++) {
								
								ItemStack stack = inv.getStackInSlot(i);
								if(ingredient.matchesRecipe(stack, true) && (sided == null || sided.canExtractItem(i, stack, 0))) {
									
									for(int j = indices[0]; j <= indices[1]; j++) {
										
										if(slots[j] != null && slots[j].stackSize < slots[j].getMaxStackSize() & InventoryUtil.doesStackDataMatch(slots[j], stack)) {
											inv.decrStackSize(i, 1);
											slots[j].stackSize++;
											return;
										}
									}
									
									for(int j = indices[0]; j <= indices[1]; j++) {
										
										if(slots[j] == null) {
											slots[j] = stack.copy();
											slots[j].stackSize = 1;
											inv.decrStackSize(i, 1);
											return;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void unloadItems(int index) {

		ChunkCoordinates[] positions = getOutputPositions();
		int[] indices = getSlotIndicesFromIndex(index);
		
		for(ChunkCoordinates coord : positions) {
			
			TileEntity te = worldObj.getTileEntity(coord.posX, coord.posY, coord.posZ);
			
			if(te instanceof IInventory) {
				
				IInventory inv = (IInventory) te;
				//ISidedInventory sided = inv instanceof ISidedInventory ? (ISidedInventory) inv : null;
				
				for(int i = indices[2]; i <= indices[3]; i++) {
					
					ItemStack out = slots[i];
					
					if(out != null) {
						
						for(int j = 0; j < inv.getSizeInventory(); j++) {
							
							if(!inv.isItemValidForSlot(j, out))
								continue;
							
							ItemStack target = inv.getStackInSlot(j);
							
							if(InventoryUtil.doesStackDataMatch(out, target) && target.stackSize < target.getMaxStackSize() && target.stackSize < inv.getInventoryStackLimit()) {
								this.decrStackSize(i, 1);
								target.stackSize++;
								return;
							}
						}
						
						for(int j = 0; j < inv.getSizeInventory(); j++) {
							
							if(!inv.isItemValidForSlot(j, out))
								continue;
							
							if(inv.getStackInSlot(j) == null && inv.isItemValidForSlot(j, out)) {
								ItemStack copy = out.copy();
								copy.stackSize = 1;
								inv.setInventorySlotContents(j, copy);
								this.decrStackSize(i, 1);
								return;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	/*public int getFluidFill(FluidType type) {
		
		int fill = 0;
		
		for(FluidTank tank : inTanks()) {
			if(tank.getTankType() == type) {
				fill += tank.getFill();
			}
		}
		
		for(FluidTank tank : outTanks()) {
			if(tank.getTankType() == type) {
				fill += tank.getFill();
			}
		}
		
		return fill;
	}*/

	/* For input only! */
	public int getMaxFluidFill(FluidType type) {
		
		int maxFill = 0;
		
		for(FluidTank tank : inTanks()) {
			if(tank.getTankType() == type) {
				maxFill += tank.getMaxFill();
			}
		}
		
		return maxFill;
	}
	
	protected List<FluidTank> inTanks() {

		List<FluidTank> inTanks = new ArrayList();
		
		for(int i = 0; i < tanks.length; i++) {
			FluidTank tank = tanks[i];
			if(tank.index % 4 < 2) {
				inTanks.add(tank);
			}
		}
		
		return inTanks;
	}

	/*public void receiveFluid(int amount, FluidType type) {
		
		if(amount <= 0)
			return;
		
		List<FluidTank> rec = new ArrayList();
		
		for(FluidTank tank : inTanks()) {
			if(tank.getTankType() == type) {
				rec.add(tank);
			}
		}
		
		if(rec.size() == 0)
			return;
		
		int demand = 0;
		List<Integer> weight = new ArrayList();
		
		for(FluidTank tank : rec) {
			int fillWeight = tank.getMaxFill() - tank.getFill();
			demand += fillWeight;
			weight.add(fillWeight);
		}
		
		for(int i = 0; i < rec.size(); i++) {
			
			if(demand <= 0)
				break;
			
			FluidTank tank = rec.get(i);
			int fillWeight = weight.get(i);
			int part = (int) (Math.min((long)amount, (long)demand) * (long)fillWeight / (long)demand);
			
			tank.setFill(tank.getFill() + part);
		}
	}*/

	public int getFluidFillForTransfer(FluidType type, int pressure) {
		
		int fill = 0;
		
		for(FluidTank tank : outTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) {
				fill += tank.getFill();
			}
		}
		
		return fill;
	}
	
	public void transferFluid(int amount, FluidType type, int pressure) {
		
		/*
		 * this whole new fluid mumbo jumbo extra abstraction layer might just be a bandaid
		 * on the gushing wound that is the current fluid systemm but i'll be damned if it
		 * didn't at least do what it's supposed to. half a decade and we finally have multi
		 * tank support for tanks with matching fluid types!!
		 */
		if(amount <= 0)
			return;
		
		List<FluidTank> send = new ArrayList();
		
		for(FluidTank tank : outTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) {
				send.add(tank);
			}
		}
		
		if(send.size() == 0)
			return;
		
		int offer = 0;
		List<Integer> weight = new ArrayList();
		
		for(FluidTank tank : send) {
			int fillWeight = tank.getFill();
			offer += fillWeight;
			weight.add(fillWeight);
		}
		
		int tracker = amount;
		
		for(int i = 0; i < send.size(); i++) {
			
			FluidTank tank = send.get(i);
			int fillWeight = weight.get(i);
			int part = amount * fillWeight / offer;
			
			tank.setFill(tank.getFill() - part);
			tracker -= part;
		}
		
		//making sure to properly deduct even the last mB lost by rounding errors
		for(int i = 0; i < 100 && tracker > 0; i++) {
			
			FluidTank tank = send.get(i % send.size());
			
			if(tank.getFill() > 0) {
				int total = Math.min(tank.getFill(), tracker);
				tracker -= total;
				tank.setFill(tank.getFill() - total);
			}
		}
	}
	
	protected List<FluidTank> outTanks() {
		
		List<FluidTank> outTanks = new ArrayList();
		
		for(int i = 0; i < tanks.length; i++) {
			FluidTank tank = tanks[i];
			if(tank.index % 4 > 1) {
				outTanks.add(tank);
			}
		}
		
		return outTanks;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public long transferFluid(FluidType type, int pressure, long fluid) {
		int amount = (int) fluid;
		
		if(amount <= 0)
			return 0;
		
		List<FluidTank> rec = new ArrayList();
		
		for(FluidTank tank : inTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) {
				rec.add(tank);
			}
		}
		
		if(rec.size() == 0)
			return fluid;
		
		int demand = 0;
		List<Integer> weight = new ArrayList();
		
		for(FluidTank tank : rec) {
			int fillWeight = tank.getMaxFill() - tank.getFill();
			demand += fillWeight;
			weight.add(fillWeight);
		}
		
		for(int i = 0; i < rec.size(); i++) {
			
			if(demand <= 0)
				break;
			
			FluidTank tank = rec.get(i);
			int fillWeight = weight.get(i);
			int part = (int) (Math.min((long)amount, (long)demand) * (long)fillWeight / (long)demand);
			
			tank.setFill(tank.getFill() + part);
			fluid -= part;
		}
		
		return fluid;
	}

	@Override
	public long getDemand(FluidType type, int pressure) {
		return getMaxFluidFill(type) - getFluidFillForTransfer(type, pressure);
	}

	@Override
	public long getTotalFluidForSend(FluidType type, int pressure) {
		return getFluidFillForTransfer(type, pressure);
	}

	@Override
	public void removeFluidForTransfer(FluidType type, int pressure, long amount) {
		this.transferFluid((int) amount, type, pressure);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.progress = nbt.getIntArray("progress");
		
		if(progress.length == 0)
			progress = new int[this.getRecipeCount()];
		
		for(int i = 0; i < tanks.length; i++) {
			tanks[i].readFromNBT(nbt, "t" + i);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setIntArray("progress", progress);
		
		for(int i = 0; i < tanks.length; i++) {
			tanks[i].writeToNBT(nbt, "t" + i);
		}
	}

	public abstract int getRecipeCount();
	public abstract int getTankCapacity();
	public abstract int getTemplateIndex(int index);
	
	/**
	 * @param index
	 * @return A size 4 int array containing min input, max input, min output and max output indices in that order.
	 */
	public abstract int[] getSlotIndicesFromIndex(int index);
	public abstract ChunkCoordinates[] getInputPositions();
	public abstract ChunkCoordinates[] getOutputPositions();
}
