package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.config.CustomMachineConfigJSON;
import com.hbm.config.CustomMachineConfigJSON.MachineConfiguration;
import com.hbm.config.CustomMachineConfigJSON.MachineConfiguration.ComponentDefinition;
import com.hbm.inventory.container.ContainerMachineCustom;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineCustom;
import com.hbm.inventory.recipes.CustomMachineRecipes;
import com.hbm.inventory.recipes.CustomMachineRecipes.CustomMachineRecipe;
import com.hbm.lib.Library;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.TileEntityProxyBase;
import com.hbm.util.Compat;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCustomMachine extends TileEntityMachineBase implements IFluidStandardTransceiver, IEnergyUser, IGUIProvider {
	
	public String machineType;
	public MachineConfiguration config;
	
	public long power;
	public int progress;
	public int maxProgress = 1;
	public FluidTank[] inputTanks;
	public FluidTank[] outputTanks;
	public ModulePatternMatcher matcher;
	public int structureCheckDelay;
	public boolean structureOK = false;
	public CustomMachineRecipe cachedRecipe;
	
	public List<DirPos> connectionPos = new ArrayList();

	public TileEntityCustomMachine() {
		/*
		 * 0: Battery
		 * 1-3: Fluid IDs
		 * 4-9: Inputs
		 * 10-15: Template
		 * 16-21: Output
		 */
		super(22);
	}
	
	public void init() {
		MachineConfiguration config = CustomMachineConfigJSON.customMachines.get(this.machineType);
		
		if(config != null) {
			this.config = config;

			inputTanks = new FluidTank[config.fluidInCount];
			for(int i = 0; i < inputTanks.length; i++) inputTanks[i] = new FluidTank(Fluids.NONE, config.fluidInCap);
			outputTanks = new FluidTank[config.fluidOutCount];
			for(int i = 0; i < outputTanks.length; i++) outputTanks[i] = new FluidTank(Fluids.NONE, config.fluidOutCap);
			
			matcher = new ModulePatternMatcher(config.itemInCount);
			
		} else {
			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
		}
	}

	@Override
	public String getName() {
		return config != null ? config.localizedName : "INVALID";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(config == null) {
				worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
				return;
			}

			this.power = Library.chargeTEFromItems(slots, 0, power, this.config.maxPower);
			
			if(this.inputTanks.length > 0) this.inputTanks[0].setType(1, slots);
			if(this.inputTanks.length > 1) this.inputTanks[1].setType(2, slots);
			if(this.inputTanks.length > 2) this.inputTanks[2].setType(3, slots);
			
			this.structureCheckDelay--;
			if(this.structureCheckDelay <= 0) this.checkStructure();
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				for(DirPos pos : this.connectionPos) {
					for(FluidTank tank : this.inputTanks) {
						this.trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					}
					if(!config.generatorMode) this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
			
			for(DirPos pos : this.connectionPos) {
				if(config.generatorMode && power > 0) this.sendPower(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				for(FluidTank tank : this.outputTanks) if(tank.getFill() > 0) this.sendFluid(tank, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			if(this.structureOK) {
				
				if(config.generatorMode) {
					if(this.cachedRecipe == null) {
						CustomMachineRecipe recipe = this.getMatchingRecipe();
						if(recipe != null && this.hasRequiredQuantities(recipe) && this.hasSpace(recipe)) {
							this.cachedRecipe = recipe;
							this.useUpInput(recipe);
						}
					}
					
					if(this.cachedRecipe != null) {
						this.maxProgress = (int) Math.max(cachedRecipe.duration / this.config.recipeSpeedMult, 1);
						int powerReq = (int) Math.max(cachedRecipe.consumptionPerTick * this.config.recipeConsumptionMult, 1);
						
						this.progress++;
						this.power += powerReq;
						if(power > config.maxPower) power = config.maxPower;
						
						if(progress >= this.maxProgress) {
							this.progress = 0;
							this.processRecipe(cachedRecipe);
							this.cachedRecipe = null;
						}
					}
					
				} else {
					CustomMachineRecipe recipe = this.getMatchingRecipe();
					
					if(recipe != null) {
						this.maxProgress = (int) Math.max(recipe.duration / this.config.recipeSpeedMult, 1);
						int powerReq = (int) Math.max(recipe.consumptionPerTick * this.config.recipeConsumptionMult, 1);
						
						if(this.power >= powerReq && this.hasRequiredQuantities(recipe) && this.hasSpace(recipe)) {
							this.progress++;
							this.power -= powerReq;
							
							if(progress >= this.maxProgress) {
								this.progress = 0;
								this.useUpInput(recipe);
								this.processRecipe(recipe);
							}
						}
					} else {
						this.progress = 0;
					}
				}
			} else {
				this.progress = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", this.machineType);
			data.setLong("power", power);
			data.setBoolean("structureOK", structureOK);
			data.setInteger("progress", progress);
			data.setInteger("maxProgress", maxProgress);
			for(int i = 0; i < inputTanks.length; i++) inputTanks[i].writeToNBT(data, "i" + i);
			for(int i = 0; i < outputTanks.length; i++) outputTanks[i].writeToNBT(data, "o" + i);
			this.matcher.writeToNBT(data);
			this.networkPack(data, 50);
		}
	}
	
	/** Only accepts inputs in a fixed order, saves a ton of performance because there's no permutations to check for */
	public CustomMachineRecipe getMatchingRecipe() {
		List<CustomMachineRecipe> recipes = CustomMachineRecipes.recipes.get(this.config.recipeKey);
		if(recipes == null || recipes.isEmpty()) return null;
		
		outer:
		for(CustomMachineRecipe recipe : recipes) {
			for(int i = 0; i < recipe.inputFluids.length; i++) {
				if(this.inputTanks[i].getTankType() != recipe.inputFluids[i].type || this.inputTanks[i].getPressure() != recipe.inputFluids[i].pressure) continue outer;
			}
			
			for(int i = 0; i < recipe.inputItems.length; i++) {
				if(recipe.inputItems[i] != null && slots[i + 4] == null) continue outer;
				if(!recipe.inputItems[i].matchesRecipe(slots[i + 4], true)) continue outer;
			}
			
			return recipe;
		}
		
		return null;
	}
	
	public boolean hasRequiredQuantities(CustomMachineRecipe recipe) {
		
		for(int i = 0; i < recipe.inputFluids.length; i++) {
			if(this.inputTanks[i].getFill() < recipe.inputFluids[i].fill) return false;
		}
		
		for(int i = 0; i < recipe.inputItems.length; i++) {
			if(slots[i + 4] != null && slots[i + 4].stackSize < recipe.inputItems[i].stacksize) return false;
		}
		
		return true;
	}
	
	public boolean hasSpace(CustomMachineRecipe recipe) {
		
		for(int i = 0; i < recipe.outputFluids.length; i++) {
			if(this.outputTanks[i].getTankType() == recipe.outputFluids[i].type && this.outputTanks[i].getFill() + recipe.outputFluids[i].fill > this.outputTanks[i].getMaxFill()) return false;
		}
		
		for(int i = 0; i < recipe.outputItems.length; i++) {
			if(slots[i + 16] != null && (slots[i + 16].getItem() != recipe.outputItems[i].key.getItem() || slots[i + 16].getItemDamage() != recipe.outputItems[i].key.getItemDamage())) return false;
			if(slots[i + 16] != null && slots[16 + i].stackSize + recipe.outputItems[i].key.stackSize > slots[i + 16].getMaxStackSize()) return false;
		}
		
		return true;
	}
	
	public void useUpInput(CustomMachineRecipe recipe) {
		
		for(int i = 0; i < recipe.inputFluids.length; i++) {
			this.inputTanks[i].setFill(this.inputTanks[i].getFill() - recipe.inputFluids[i].fill);
		}
		
		for(int i = 0; i < recipe.inputItems.length; i++) {
			this.decrStackSize(i + 4, recipe.inputItems[i].stacksize);
		}
	}
	
	public void processRecipe(CustomMachineRecipe recipe) {
		
		for(int i = 0; i < recipe.outputFluids.length; i++) {
			if(this.outputTanks[i].getTankType() != recipe.outputFluids[i].type) this.outputTanks[i].setTankType(recipe.outputFluids[i].type);
			this.outputTanks[i].setFill(this.outputTanks[i].getFill() + recipe.outputFluids[i].fill);
		}
		
		for(int i = 0; i < recipe.outputItems.length; i++) {
			
			if(worldObj.rand.nextFloat() < recipe.outputItems[i].value) {
				if(slots[i + 16] == null) {
					slots[i + 16] = recipe.outputItems[i].key.copy();
				} else {
					slots[i + 16].stackSize += recipe.outputItems[i].key.stackSize;
				}
			}
		}
	}
	
	public boolean checkStructure() {
		
		this.connectionPos.clear();
		this.structureCheckDelay = 300;
		this.structureOK = false;
		if(this.config == null) return false;
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		for(ComponentDefinition comp : config.components) {
			
			/* vvv precisely the same method used for defining ports vvv */
			int x = xCoord - dir.offsetX * comp.x + rot.offsetX * comp.x;
			int y = yCoord + comp.y;
			int z = zCoord - dir.offsetZ * comp.z + rot.offsetZ * comp.z;
			/* but for EW directions it just stops working entirely */
			/* there is absolutely zero reason why this should be required */
			if(dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) {
				x = xCoord + dir.offsetZ * comp.z - rot.offsetZ * comp.z;
				z = zCoord + dir.offsetX * comp.x - rot.offsetX * comp.x;
			}
			/* i wholeheartedly believe it is the computer who is wrong here */
			
			Block b = worldObj.getBlock(x, y, z);
			if(b != comp.block) return false;
			
			int meta = worldObj.getBlockMetadata(x, y, z);
			if(!comp.allowedMetas.contains(meta)) return false;
			
			TileEntity tile = Compat.getTileStandard(worldObj, x, y, z);
			if(tile instanceof TileEntityProxyBase) {
				TileEntityProxyBase proxy = (TileEntityProxyBase) tile;
				proxy.cachedPosition = new BlockPos(xCoord, yCoord, zCoord);
				proxy.markDirty();
				
				for(ForgeDirection facing : ForgeDirection.VALID_DIRECTIONS) {
					this.connectionPos.add(new DirPos(x + facing.offsetX, y + facing.offsetY, z + facing.offsetZ, facing));
				}
			}
		}
		
		for(ForgeDirection facing : ForgeDirection.VALID_DIRECTIONS) {
			this.connectionPos.add(new DirPos(xCoord + facing.offsetX, yCoord + facing.offsetY, zCoord + facing.offsetZ, facing));
		}
		
		this.structureOK = true;
		return true;
	}
	
	public void buildStructure() {
		
		if(this.config == null) return;
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		for(ComponentDefinition comp : config.components) {
			
			int x = xCoord - dir.offsetX * comp.x + rot.offsetX * comp.x;
			int y = yCoord + comp.y;
			int z = zCoord - dir.offsetZ * comp.z + rot.offsetZ * comp.z;
			if(dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) {
				x = xCoord + dir.offsetZ * comp.z - rot.offsetZ * comp.z;
				z = zCoord + dir.offsetX * comp.x - rot.offsetX * comp.x;
			}
			
			worldObj.setBlock(x, y, z, comp.block, (int) comp.allowedMetas.toArray()[0], 3);
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(this.config == null) return new int[] { };
		if(this.config.itemInCount > 5) return new int[] { 4, 5, 6, 7, 8, 9, 16, 17, 18, 19, 20, 21 };
		if(this.config.itemInCount > 4) return new int[] { 4, 5, 6, 7, 8, 16, 17, 18, 19, 20, 21 };
		if(this.config.itemInCount > 3) return new int[] { 4, 5, 6, 7, 16, 17, 18, 19, 20, 21 };
		if(this.config.itemInCount > 2) return new int[] { 4, 5, 6, 16, 17, 18, 19, 20, 21 };
		if(this.config.itemInCount > 1) return new int[] { 4, 5, 16, 17, 18, 19, 20, 21 };
		if(this.config.itemInCount > 0) return new int[] { 4, 16, 17, 18, 19, 20, 21 };
		return new int[] { };
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		return i >= 16 && i <= 21;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot < 4 || slot > 9) return false;
		
		int index = slot - 4;
		int filterSlot = slot + 6;
		
		if(slots[filterSlot] == null) return true;
		
		return matcher.isValidForFilter(slots[filterSlot], index, stack);
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.machineType = nbt.getString("type");
		if(this.config == null) this.init();
		
		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
		this.structureOK = nbt.getBoolean("structureOK");
		this.maxProgress = nbt.getInteger("maxProgress");
		for(int i = 0; i < inputTanks.length; i++) inputTanks[i].readFromNBT(nbt, "i" + i);
		for(int i = 0; i < outputTanks.length; i++) outputTanks[i].readFromNBT(nbt, "o" + i);
		
		this.matcher.readFromNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		this.machineType = nbt.getString("machineType");
		this.init();
		
		super.readFromNBT(nbt);
		
		if(this.config != null) {
	
			for(int i = 0; i < inputTanks.length; i++) inputTanks[i].readFromNBT(nbt, "i" + i);
			for(int i = 0; i < outputTanks.length; i++) outputTanks[i].readFromNBT(nbt, "o" + i);
			
			this.matcher.readFromNBT(nbt);
			
			int index = nbt.getInteger("cachedIndex");
			if(index != -1) {
				this.cachedRecipe = CustomMachineRecipes.recipes.get(this.config.recipeKey).get(index);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
		if(machineType == null || this.config == null) {
			super.writeToNBT(nbt);
			return;
		}
		
		nbt.setString("machineType", machineType);
		
		super.writeToNBT(nbt);

		for(int i = 0; i < inputTanks.length; i++) inputTanks[i].writeToNBT(nbt, "i" + i);
		for(int i = 0; i < outputTanks.length; i++) outputTanks[i].writeToNBT(nbt, "o" + i);
		
		this.matcher.writeToNBT(nbt);
		
		if(this.cachedRecipe != null) {
			int index = CustomMachineRecipes.recipes.get(this.config.recipeKey).indexOf(this.cachedRecipe);
			nbt.setInteger("cachedIndex", index);
		} else {
			nbt.setInteger("cachedIndex", -1);
		}
	}

	@Override
	public FluidTank[] getAllTanks() {
		
		FluidTank[] all = new FluidTank[inputTanks.length + outputTanks.length];

		for(int i = 0; i < inputTanks.length; i++) all[i] = inputTanks[i];
		for(int i = 0; i < outputTanks.length; i++) all[inputTanks.length + i] = outputTanks[i];
		
		return all;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return outputTanks != null ? outputTanks : new FluidTank[0];
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return inputTanks != null ? inputTanks : new FluidTank[0];
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(this.config == null) return null;
		return new ContainerMachineCustom(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(this.config == null) return null;
		return new GUIMachineCustom(player.inventory, this);
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return this.config != null ? this.config.maxPower : 1;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}
	
	@Override
	public long transferPower(long power) {
		if(this.config != null && this.config.generatorMode) return power;
		
		this.setPower(this.getPower() + power);
		
		if(this.getPower() > this.getMaxPower()) {
			
			long overshoot = this.getPower() - this.getMaxPower();
			this.setPower(this.getMaxPower());
			return overshoot;
		}
		
		return 0;
	}

	@Override
	public long getTransferWeight() {
		if(this.config != null && this.config.generatorMode) return 0;

		return Math.max(getMaxPower() - getPower(), 0);
	}
}
