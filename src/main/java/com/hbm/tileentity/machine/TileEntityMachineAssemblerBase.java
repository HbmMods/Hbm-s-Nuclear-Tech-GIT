package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.recipes.AssemblerRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;

import api.hbm.energy.IEnergyUser;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;

public abstract class TileEntityMachineAssemblerBase extends TileEntityMachineBase implements IEnergyUser {

	public long power;
	public int[] progress;
	public int[] maxProgress;
	public boolean isProgressing;
	
	int consumption = 100;
	int speed = 100;

	public TileEntityMachineAssemblerBase(int scount) {
		super(scount);
		
		int count = this.getRecipeCount();

		progress = new int[count];
		maxProgress = new int[count];
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			int count = this.getRecipeCount();
			
			this.isProgressing = false;
			this.power = Library.chargeTEFromItems(slots, 0, power, this.getMaxPower());
			
			for(int i = 0; i < count; i++) {
				//loadItems(i);
				//unloadItems(i);
			}
			
			if(worldObj.getTotalWorldTime() % 10 == 0) {

				/*for(FluidTank tank : this.outTanks()) {
					if(tank.getTankType() != Fluids.NONE && tank.getFill() > 0) {
						this.fillFluidInit(tank.getTankType());
					}
				}*/
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
	 * @return A size 4 int array containing min input, max input and output indices in that order.
	 */
	public abstract int[] getSlotIndicesFromIndex(int index);
	public abstract ChunkCoordinates[] getInputPositions();
	public abstract ChunkCoordinates[] getOutputPositions();
}
