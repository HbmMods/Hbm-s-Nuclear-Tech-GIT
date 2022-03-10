package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.ChemplantRecipes;
import com.hbm.inventory.recipes.ChemplantRecipes.ChemRecipe;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;

import api.hbm.energy.IEnergyUser;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
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
public abstract class TileEntityMachineChemplantBase extends TileEntityMachineBase implements IEnergyUser, IFluidSource, IFluidAcceptor {

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
			
			if(worldObj.getTotalWorldTime() % 1 == 0) {

				for(int i = 0; i < count; i++) {
					this.fillFluidInit(tanks[i * 4 + 2].getTankType());
					this.fillFluidInit(tanks[i * 4 + 3].getTankType());
				}
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
	
	private boolean canProcess(int index) {
		
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
	
	private void process(int index) {
		
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
					
					for(AStack ingredient : recipe.inputs) {
						
						if(!InventoryUtil.doesArrayHaveIngredients(slots, indices[0], indices[1], ingredient)) {
							
							for(int i = 0; i < inv.getSizeInventory(); i++) {
								
								ItemStack stack = inv.getStackInSlot(i);
								if(ingredient.matchesRecipe(stack, true)) {
									
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
				
				for(int i = indices[2]; i <= indices[3]; i++) {
					
					ItemStack out = slots[i];
					
					if(out != null) {
						
						for(int j = 0; j < inv.getSizeInventory(); j++) {
							ItemStack target = inv.getStackInSlot(j);
							
							if(InventoryUtil.doesStackDataMatch(out, target) && target.stackSize < target.getMaxStackSize()) {
								this.decrStackSize(i, 1);
								target.stackSize++;
								return;
							}
						}
						
						for(int j = 0; j < inv.getSizeInventory(); j++) {
							
							if(inv.getStackInSlot(j) == null) {
								inv.setInventorySlotContents(j, out.copy());
								inv.getStackInSlot(j).stackSize = 1;
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

	@Override
	public void setFillForSync(int fill, int index) {
		if(index >= 0 && index < tanks.length) tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		
		//TODO: figure this shit out
		//also this won't work anyway since there's no difference as of now between setting in or output tanks
		//the recent rework tried to implement that difference but we all know how that went
		
		/*for(FluidTank tank : tanks) {
			if(tank.getTankType() == type) {
				tank.setFill(fill);
				return;
			}
		}*/
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index >= 0 && index < tanks.length) tanks[index].setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		int fill = 0;
		
		for(FluidTank tank : tanks) {
			if(tank.getTankType() == type) {
				fill += tank.getFill();
			}
		}
		
		return fill;
	}

	/* For input only! */
	@Override
	public int getMaxFluidFill(FluidType type) {
		
		int maxFill = 0;
		int count = getRecipeCount();
		
		for(int i = 0; i < count; i++) {

			if(tanks[i * 4].getTankType() == type) maxFill += tanks[i * 4].getMaxFill();
			if(tanks[i * 4 + 1].getTankType() == type) maxFill += tanks[i * 4 + 1].getMaxFill();
		}
		
		return maxFill;
	}

	@Override
	public int getFluidFillForReceive(FluidType type) {
		
		int fill = 0;
		
		for(FluidTank tank : tanks) {
			if(tank.index % 4 < 2 && tank.getTankType() == type) {
				fill += tank.getFill();
			}
		}
		
		return fill;
	}

	@Override
	public void receiveFluid(int amount, FluidType type) {
		
		List<FluidTank> rec = new ArrayList();
		
		for(FluidTank tank : tanks) {
			if(tank.index % 4 < 2 && tank.getTankType() == type) {
				rec.add(tank);
			}
		}
		
		if(rec.size() == 0)
			return;
		
		int demand = 0;
		
		for(FluidTank tank : rec) {
			demand += tank.getMaxFill() - tank.getFill();
		}
		
		int part = demand / rec.size(); // dividing ints rounds down anyway
		
		for(FluidTank tank : rec) {
			tank.setFill(tank.getFill() + part);
			demand -= part;
		}
		
		//getting rid of annoying rounding errors
		if(demand > 0) {
			
			while(demand > 0) {
				
				FluidTank tank = rec.get(worldObj.rand.nextInt(rec.size()));
				if(tank.getFill() < tank.getMaxFill()) {
					//we do single mB steps because the distribution is more even this way and honestly the remainder can't possibly be that big
					tank.setFill(tank.getFill() + 1);
					demand--;
				}
			}
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
