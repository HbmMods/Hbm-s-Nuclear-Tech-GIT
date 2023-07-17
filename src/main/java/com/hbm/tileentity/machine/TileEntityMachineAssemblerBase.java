package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.recipes.AssemblerRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.machine.storage.TileEntityCrateTemplate;
import com.hbm.util.InventoryUtil;

import api.hbm.energy.IEnergyUser;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public abstract class TileEntityMachineAssemblerBase extends TileEntityMachineBase implements IEnergyUser, IGUIProvider {

	public long power;
	public int[] progress;
	public int[] maxProgress;
	public boolean isProgressing;
	public boolean[] needsTemplateSwitch;
	
	int consumption = 100;
	int speed = 100;

	public TileEntityMachineAssemblerBase(int scount) {
		super(scount);
		
		int count = this.getRecipeCount();

		progress = new int[count];
		maxProgress = new int[count];
		needsTemplateSwitch = new boolean[count];
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			int count = this.getRecipeCount();
			
			this.isProgressing = false;
			this.power = Library.chargeTEFromItems(slots, getPowerSlot(), power, this.getMaxPower());
			
			for(int i = 0; i < count; i++) {
				unloadItems(i);
				loadItems(i);
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
		
		if(slots[template] == null || slots[template].getItem() != ModItems.assembly_template)
			return false;

		List<AStack> recipe = AssemblerRecipes.getRecipeFromTempate(slots[template]);
		ItemStack output = AssemblerRecipes.getOutputFromTempate(slots[template]);
		
		if(recipe == null)
			return false;
		
		if(this.power < this.consumption) return false;
		if(!hasRequiredItems(recipe, index)) return false;
		if(!hasSpaceForItems(output, index)) return false;
		
		return true;
	}
	
	private boolean hasRequiredItems(List<AStack> recipe, int index) {
		int[] indices = getSlotIndicesFromIndex(index);
		return InventoryUtil.doesArrayHaveIngredients(slots, indices[0], indices[1], recipe.toArray(new AStack[0]));
	}
	
	private boolean hasSpaceForItems(ItemStack recipe, int index) {
		int[] indices = getSlotIndicesFromIndex(index);
		return InventoryUtil.doesArrayHaveSpace(slots, indices[2], indices[2], new ItemStack[] { recipe });
	}
	
	protected void process(int index) {
		
		this.power -= this.consumption;
		this.progress[index]++;
		
		if(slots[0] != null && slots[0].getItem() == ModItems.meteorite_sword_alloyed)
			slots[0] = new ItemStack(ModItems.meteorite_sword_machined); //fisfndmoivndlmgindgifgjfdnblfm
		
		int template = getTemplateIndex(index);

		List<AStack> recipe = AssemblerRecipes.getRecipeFromTempate(slots[template]);
		ItemStack output = AssemblerRecipes.getOutputFromTempate(slots[template]);
		int time = ItemAssemblyTemplate.getProcessTime(slots[template]);
		
		this.maxProgress[index] = time * this.speed / 100;
		
		if(this.progress[index] >= this.maxProgress[index]) {
			consumeItems(recipe, index);
			produceItems(output, index);
			this.progress[index] = 0;
			this.needsTemplateSwitch[index] = true;
			this.markDirty();
		}
	}
	
	private void consumeItems(List<AStack> recipe, int index) {
		
		int[] indices = getSlotIndicesFromIndex(index);
		
		for(AStack in : recipe) {
			if(in != null)
				InventoryUtil.tryConsumeAStack(slots, indices[0], indices[1], in);
		}
	}
	
	private void produceItems(ItemStack out, int index) {
		
		int[] indices = getSlotIndicesFromIndex(index);
		
		if(out != null) {
			InventoryUtil.tryAddItemToInventory(slots, indices[2], indices[2], out.copy());
		}
	}
	
	private void loadItems(int index) {
		
		int template = getTemplateIndex(index);

		ChunkCoordinates[] positions = getInputPositions();
		int[] indices = getSlotIndicesFromIndex(index);

		for(ChunkCoordinates coord : positions) {

			TileEntity te = worldObj.getTileEntity(coord.posX, coord.posY, coord.posZ);
				
			if(te instanceof IInventory) {

				IInventory inv = (IInventory) te;
				ISidedInventory sided = inv instanceof ISidedInventory ? (ISidedInventory) inv : null;
				boolean templateCrate = te instanceof TileEntityCrateTemplate;

				if(templateCrate && slots[template] == null) {

					for(int i = 0; i < inv.getSizeInventory(); i++) {
						ItemStack stack = inv.getStackInSlot(i);

						if(stack != null && stack.getItem() == ModItems.assembly_template && (sided == null || sided.canExtractItem(i, stack, 0))) {
							slots[template] = stack.copy();
							sided.setInventorySlotContents(i, null);
							this.needsTemplateSwitch[index] = false;
							break;
						}
					}
				}
					
				boolean noTemplate = slots[template] == null || slots[template].getItem() != ModItems.assembly_template;

				if(!noTemplate) {

					List<AStack> recipe = AssemblerRecipes.getRecipeFromTempate(slots[template]);

					if(recipe != null) {

						for(AStack ingredient : recipe) {

							outer: while(!InventoryUtil.doesArrayHaveIngredients(slots, indices[0], indices[1], ingredient)) {

								boolean found = false;

								for(int i = 0; i < inv.getSizeInventory(); i++) {

									ItemStack stack = inv.getStackInSlot(i);
									if(ingredient.matchesRecipe(stack, true) && (sided == null || sided.canExtractItem(i, stack, 0))) {
										found = true;

										for(int j = indices[0]; j <= indices[1]; j++) {

											if(slots[j] != null && slots[j].stackSize < slots[j].getMaxStackSize() & InventoryUtil.doesStackDataMatch(slots[j], stack)) {
												inv.decrStackSize(i, 1);
												slots[j].stackSize++;
												continue outer;
											}
										}

										for(int j = indices[0]; j <= indices[1]; j++) {

											if(slots[j] == null) {
												slots[j] = stack.copy();
												slots[j].stackSize = 1;
												inv.decrStackSize(i, 1);
												continue outer;
											}
										}
									}
								}

								if(!found) return;
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
				
				int i = indices[2];
				ItemStack out = slots[i];
				
				int template = getTemplateIndex(index);
				if(this.needsTemplateSwitch[index] && te instanceof TileEntityCrateTemplate && slots[template] != null) {
					out = slots[template];
					i = template;
				}

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

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	public abstract int getRecipeCount();
	public abstract int getTemplateIndex(int index);
	
	/**
	 * @param index
	 * @return A size 3 int array containing min input, max input and output indices in that order.
	 */
	public abstract int[] getSlotIndicesFromIndex(int index);
	public abstract ChunkCoordinates[] getInputPositions();
	public abstract ChunkCoordinates[] getOutputPositions();
	public abstract int getPowerSlot();
}
