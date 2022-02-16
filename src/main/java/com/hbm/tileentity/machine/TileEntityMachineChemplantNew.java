package com.hbm.tileentity.machine;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.ChemplantRecipes;
import com.hbm.inventory.recipes.ChemplantRecipes.ChemRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMachineChemplantNew extends TileEntityMachineBase {

	public long power;
	public static final long maxPower = 100000;
	public int progress;
	public int maxProgress = 100;
	
	public FluidTank[] tanks;
	
	//upgraded stats
	int consumption = 100;
	int speed = 100;

	public TileEntityMachineChemplantNew() {
		super(21);
		/*
		 * 0 Battery
		 * 1-3 Upgrades
		 * 4 Schematic
		 * 5-8 Output
		 * 9-10 FOut In
		 * 11-12 FOut Out
		 * 13-16 Input
		 * 17-18 FIn In
		 * 19-20 FIn Out
		 */
		
		tanks = new FluidTank[4];
		for(int i = 0; i < 4; i++) {
			tanks[i] = new FluidTank(Fluids.NONE, 24_000, i);
		}
	}

	@Override
	public String getName() {
		return "container.chemplant";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			UpgradeManager.eval(slots, 1, 3);

			int speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			int powerLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);
			int overLevel = UpgradeManager.getLevel(UpgradeType.OVERDRIVE);
			
			speed -= speedLevel * 25;
			consumption += speedLevel * 300;
			speed += powerLevel * 5;
			consumption -= powerLevel * 30;
			speed /= (overLevel + 1);
			consumption *= (overLevel + 1);
			
			if(!canProcess()) {
				this.progress = 0;
			} else {
				process();
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
	}
	
	private boolean canProcess() {
		
		if(slots[4] == null || slots[4].getItem() != ModItems.chemistry_template)
			return false;
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(slots[4].getItemDamage());
		
		if(recipe == null)
			return false;
		
		setupTanks(recipe);
		
		if(!hasRequiredFluids(recipe))
			return false;
		
		if(!hasSpaceForFluids(recipe))
			return false;
		
		return true;
	}
	
	private void setupTanks(ChemRecipe recipe) {
		if(recipe.inputFluids.length > 0) tanks[0].setTankType(recipe.inputFluids[0].type);
		if(recipe.inputFluids.length > 1) tanks[1].setTankType(recipe.inputFluids[1].type);
		if(recipe.outputFluids.length > 0) tanks[2].setTankType(recipe.outputFluids[0].type);
		if(recipe.outputFluids.length > 1) tanks[3].setTankType(recipe.outputFluids[1].type);
	}
	
	private boolean hasRequiredFluids(ChemRecipe recipe) {
		if(recipe.inputFluids.length > 0 && tanks[0].getFill() < recipe.inputFluids[0].fill) return false;
		if(recipe.inputFluids.length > 1 && tanks[1].getFill() < recipe.inputFluids[1].fill) return false;
		return true;
	}
	
	private boolean hasSpaceForFluids(ChemRecipe recipe) {
		if(recipe.outputFluids.length > 0 && tanks[2].getFill() + recipe.outputFluids[0].fill > tanks[2].getMaxFill()) return false;
		if(recipe.outputFluids.length > 1 && tanks[3].getFill() + recipe.outputFluids[1].fill > tanks[3].getMaxFill()) return false;
		return true;
	}
	
	private boolean hasRequiredItems(ChemRecipe recipe) {
		return false;
	}
	
	private void process() {
		
	}
}
