package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.ChemplantRecipes;
import com.hbm.inventory.recipes.ChemplantRecipes.ChemRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;

import api.hbm.energy.IEnergyUser;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineChemplantNew extends TileEntityMachineBase implements IEnergyUser, IFluidSource, IFluidAcceptor {

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
			
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);

			if(!tanks[0].loadTank(17, 19, slots)) tanks[0].unloadTank(17, 19, slots);
			if(!tanks[1].loadTank(18, 20, slots)) tanks[1].unloadTank(18, 20, slots);
			
			tanks[2].unloadTank(9, 11, slots);
			tanks[3].unloadTank(10, 12, slots);
			
			if(worldObj.getTotalWorldTime() % 10 == 0) {
				this.fillFluidInit(tanks[2].getTankType());
				this.fillFluidInit(tanks[3].getTankType());
			}
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				this.updateConnections();
			}
			
			UpgradeManager.eval(slots, 1, 3);

			int speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			int powerLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);
			int overLevel = UpgradeManager.getLevel(UpgradeType.OVERDRIVE);
			
			this.speed -= speedLevel * 25;
			this.consumption += speedLevel * 300;
			this.speed += powerLevel * 5;
			this.consumption -= powerLevel * 30;
			this.speed /= (overLevel + 1);
			this.consumption *= (overLevel + 1);
			
			if(!canProcess()) {
				this.progress = 0;
			} else {
				process();
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progress", this.progress);
			data.setInteger("maxProgress", this.maxProgress);
			
			for(int i = 0; i < tanks.length; i++) {
				tanks[i].writeToNBT(data, "t" + i);
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
		this.maxProgress = nbt.getInteger("maxProgress");

		for(int i = 0; i < tanks.length; i++) {
			tanks[i].readFromNBT(nbt, "t" + i);
		}
	}
	
	private void updateConnections() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		this.trySubscribe(worldObj, xCoord + rot.offsetX * 3,				yCoord,	zCoord + rot.offsetZ * 3,				rot);
		this.trySubscribe(worldObj, xCoord - rot.offsetX * 2,				yCoord,	zCoord - rot.offsetZ * 2,				rot.getOpposite());
		this.trySubscribe(worldObj, xCoord + rot.offsetX * 3 + dir.offsetX,	yCoord,	zCoord + rot.offsetZ * 3 + dir.offsetZ, rot);
		this.trySubscribe(worldObj, xCoord - rot.offsetX * 2 + dir.offsetX,	yCoord,	zCoord - rot.offsetZ * 2 + dir.offsetZ, rot.getOpposite());
	}
	
	private boolean canProcess() {
		
		if(slots[4] == null || slots[4].getItem() != ModItems.chemistry_template)
			return false;
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(slots[4].getItemDamage());
		
		if(recipe == null)
			return false;
		
		setupTanks(recipe);
		
		if(this.power < this.consumption) return false;
		if(!hasRequiredFluids(recipe)) return false;
		if(!hasSpaceForFluids(recipe)) return false;
		if(!hasRequiredItems(recipe)) return false;
		if(!hasSpaceForItems(recipe)) return false;
		
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
		return InventoryUtil.doesArrayHaveIngredients(slots, 13, 16, recipe.inputs);
	}
	
	private boolean hasSpaceForItems(ChemRecipe recipe) {
		return InventoryUtil.doesArrayHaveSpace(slots, 5, 8, recipe.outputs);
	}
	
	private void process() {
		
		this.power -= this.consumption;
		this.progress++;
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(slots[4].getItemDamage());
		
		this.maxProgress = recipe.getDuration() * this.speed / 100;
		
		if(this.progress >= this.maxProgress) {
			consumeFluids(recipe);
			produceFluids(recipe);
			consumeItems(recipe);
			produceItems(recipe);
		}
	}
	
	private void consumeFluids(ChemRecipe recipe) {
		if(recipe.inputFluids.length > 0) tanks[0].setFill(tanks[0].getFill() - recipe.inputFluids[0].fill);
		if(recipe.inputFluids.length > 1) tanks[1].setFill(tanks[1].getFill() - recipe.inputFluids[1].fill);
	}
	
	private void produceFluids(ChemRecipe recipe) {
		if(recipe.outputFluids.length > 0) tanks[2].setFill(tanks[2].getFill() + recipe.outputFluids[0].fill);
		if(recipe.outputFluids.length > 1) tanks[3].setFill(tanks[3].getFill() + recipe.outputFluids[1].fill);
	}
	
	private void consumeItems(ChemRecipe recipe) {
		
		for(AStack in : recipe.inputs) {
			InventoryUtil.tryConsumeAStack(slots, 13, 16, in);
		}
	}
	
	private void produceItems(ChemRecipe recipe) {
		
		for(ItemStack out : recipe.outputs) {
			InventoryUtil.tryAddItemToInventory(slots, 5, 8, out);
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
	public long getMaxPower() {
		return this.maxPower;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index >= 0 && index < tanks.length) tanks[index].setFill(fill);
	}

	@Override
	public void setFillForTransfer(int fill, FluidType type) {
		
		for(FluidTank tank : tanks) {
			if(tank.getTankType() == type) {
				tank.setFill(fill);
				return;
			}
		}
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index >= 0 && index < tanks.length) tanks[index].setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		for(FluidTank tank : tanks) {
			if(tank.getTankType() == type) {
				return tank.getFill();
			}
		}
		
		return 0;
	}

	@Override
	public int getMaxFillForReceive(FluidType type) {
		
		for(FluidTank tank : tanks) {
			if(tank.getTankType() == type) {
				return tank.getMaxFill();
			}
		}
		
		return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		/*
		 *  ####
		 * X####X
		 * X##O#X
		 *  ####
		 */
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		fillFluid(xCoord + rot.offsetX * 3,					yCoord,	zCoord + rot.offsetZ * 3,				this.getTact(), type);
		fillFluid(xCoord - rot.offsetX * 2,					yCoord,	zCoord - rot.offsetZ * 2,				this.getTact(), type);
		fillFluid(xCoord + rot.offsetX * 3 + dir.offsetX,	yCoord,	zCoord + rot.offsetZ * 3 + dir.offsetZ,	this.getTact(), type);
		fillFluid(xCoord - rot.offsetX * 2 + dir.offsetX,	yCoord,	zCoord - rot.offsetZ * 2 + dir.offsetZ,	this.getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		return worldObj.getTotalWorldTime() % 20 < 10;
	}
	
	List<IFluidAcceptor>[] lists = new List[] {
		new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList()
	};

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		
		for(int i = 0; i < tanks.length; i++) {
			if(tanks[i].getTankType() == type) {
				return lists[i];
			}
		}
		
		return new ArrayList();
	}

	@Override
	public void clearFluidList(FluidType type) {
		
		for(int i = 0; i < tanks.length; i++) {
			if(tanks[i].getTankType() == type) {
				lists[i].clear();
			}
		}
	}
}
