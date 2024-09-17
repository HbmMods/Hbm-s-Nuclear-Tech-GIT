package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.container.ContainerAutocrafter;
import com.hbm.inventory.gui.GUIAutocrafter;
import com.hbm.lib.Library;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.tileentity.IControlReceiverFilter;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineAutocrafter extends TileEntityMachineBase implements IEnergyReceiverMK2, IGUIProvider, IControlReceiverFilter {

	
	public List<IRecipe> recipes = new ArrayList();
	public int recipeIndex;
	public int recipeCount;

	public ModulePatternMatcher matcher;

	public TileEntityMachineAutocrafter() {
		super(21);
		this.matcher = new ModulePatternMatcher(9);
	}

	@Override
	public void nextMode(int i) {
		this.matcher.nextMode(worldObj, slots[i], i);
	}

	public void nextTemplate() {
		
		if(worldObj.isRemote) return;
		
		this.recipeIndex++;
		
		if(this.recipeIndex >= this.recipes.size())
			this.recipeIndex = 0;
		
		if(!this.recipes.isEmpty()) {
			slots[9] = this.recipes.get(this.recipeIndex).getCraftingResult(getTemplateGrid());
		} else {
			slots[9] = null;
		}
	}

	@Override
	public String getName() {
		return "container.autocrafter";
	}
	
	protected InventoryCraftingAuto craftingInventory = new InventoryCraftingAuto(3, 3);

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(slots, 20, power, maxPower);
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
			
			if(!this.recipes.isEmpty() && this.power >= this.consumption) {
				IRecipe recipe = this.recipes.get(recipeIndex);
				
				if(recipe.matches(this.getRecipeGrid(), this.worldObj)) {
					ItemStack stack = recipe.getCraftingResult(this.getRecipeGrid());
					
					if(stack != null) {
						
						boolean didCraft = false;
						
						if(slots[19] == null) {
							slots[19] = stack.copy();
							didCraft = true;
						} else if(slots[19].isItemEqual(stack) && ItemStack.areItemStackTagsEqual(stack, slots[19]) && slots[19].stackSize + stack.stackSize <= slots[19].getMaxStackSize()) {
							slots[19].stackSize += stack.stackSize;
							didCraft = true;
						}
						
						if(didCraft) {
							for(int i = 10; i < 19; i++) {
								
								ItemStack ingredient = this.getStackInSlot(i);

								if(ingredient != null) {
									this.decrStackSize(i, 1);

									if(slots[i] == null && ingredient.getItem().hasContainerItem(ingredient)) {
										ItemStack container = ingredient.getItem().getContainerItem(ingredient);

										if(container != null && container.isItemStackDamageable() && container.getItemDamage() > container.getMaxDamage()) {
											continue;
										}

										this.setInventorySlotContents(i, container);
									}
								}
							}
							
							this.power -= this.consumption;
						}
					}
				}
			}
			
			this.networkPackNT(15);
		}
	}
	
	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		matcher.serialize(buf);
		buf.writeInt(recipeCount);
		buf.writeInt(recipeIndex);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		power = buf.readLong();
		matcher.deserialize(buf);
		recipeCount = buf.readInt();
		recipeIndex = buf.readInt();
	}
	
	public void updateTemplateGrid() {

		this.recipes = getMatchingRecipes(this.getTemplateGrid());
		this.recipeCount = recipes.size();
		this.recipeIndex = 0;
		
		if(!this.recipes.isEmpty()) {
			slots[9] = this.recipes.get(this.recipeIndex).getCraftingResult(getTemplateGrid());
		} else {
			slots[9] = null;
		}
	}
	
	public List<IRecipe> getMatchingRecipes(InventoryCrafting grid) {
		List<IRecipe> recipes = new ArrayList();
		
		for(Object o : CraftingManager.getInstance().getRecipeList()) {
			IRecipe recipe = (IRecipe) o;
			
			if(recipe.matches(grid, worldObj)) {
				recipes.add(recipe);
			}
		}
		
		return recipes;
	}

	public int[] access = new int[] { 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return access;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		if(i == 19)
			return true;
		
		if(i > 9 && i < 19) {
			ItemStack filter = slots[i - 10];
			if(filter == null || matcher.modes[i - 10] == null || matcher.modes[i - 10].isEmpty()) return true;
			return !matcher.isValidForFilter(filter, i - 10, stack);
		}
		
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {

		//automatically prohibit stacked container items
		if(stack.stackSize > 1 && stack.getItem().hasContainerItem(stack)) return false;
		
		//only allow insertion for the nine recipe slots
		if(slot < 10 || slot > 18)
			return false;
		
		//is the filter at this space null? no input.
		if(slots[slot - 10] == null)
			return false;
		
		//do not permit total stacking beyond 4 items
		if(slots[slot] != null && slots[slot].stackSize + stack.stackSize > 4) return false;
		if(stack.stackSize > 4) return false;
		
		//let's find all slots that this item could potentially go in
		List<Integer> validSlots = new ArrayList();
		for(int i = 0; i < 9; i++) {
			ItemStack filter = slots[i];
			if(filter == null || matcher.modes[i] == null || matcher.modes[i].isEmpty()) continue;

			if(matcher.isValidForFilter(filter, i, stack)) {
				validSlots.add(i + 10);
				
				//if the current slot is valid and has no item in it, shortcut to true [*]
				if(i + 10 == slot && slots[slot] == null) {
					return true;
				}
			}
		}
		
		//if the slot we are looking at isn't valid, skip
		if(!validSlots.contains(slot)) {
			return false;
		}
		
		//assumption from [*]: the slot has to be valid by now, and it cannot be null
		int size = slots[slot].stackSize;
		
		//now we decide based on stacksize, woohoo
		for(Integer i : validSlots) {
			ItemStack valid = slots[i];
			
			if(valid == null) return false; //null? since slots[slot] is not null by now, this other slot needs the item more
			if(!(valid.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(valid, stack))) continue; //different item anyway? out with it
			
			//if there is another slot that actually does need the same item more, cancel
			if(valid.stackSize < size)
				return false;
		}
		
		//prevent items with containers from stacking
		if(stack.getItem().hasContainerItem(stack))
			return false;
		
		//by now, we either already have filled the slot (if valid by filter and null) or weeded out all other options, which means it is good to go
		return true;
	}
	
	public InventoryCrafting getTemplateGrid() {
		this.craftingInventory.loadIventory(slots, 0);
		return this.craftingInventory;
	}
	
	public InventoryCrafting getRecipeGrid() {
		this.craftingInventory.loadIventory(slots, 10);
		return this.craftingInventory;
	}

	public static class InventoryCraftingAuto extends InventoryCrafting {

		public InventoryCraftingAuto(int width, int height) {
			super(new ContainerBlank() /* "can't be null boo hoo" */, width, height);
		}
		
		public void loadIventory(ItemStack[] slots, int start) {
			
			for(int i = 0; i < this.getSizeInventory(); i++) {
				this.setInventorySlotContents(i, slots[start + i]);
			}
		}
		
		public void clear() {
			for(int i = 0; i < this.getSizeInventory(); i++) this.setInventorySlotContents(i, null);
		}
		
		public static class ContainerBlank extends Container {
			@Override public void onCraftMatrixChanged(IInventory inventory) { }
			@Override public boolean canInteractWith(EntityPlayer player) { return false; }
		}
	}
	
	public static int consumption = 100;
	public static long maxPower = consumption * 100;
	public long power;

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		matcher.readFromNBT(nbt);
		this.recipes = getMatchingRecipes(this.getTemplateGrid());
		this.recipeCount = recipes.size();
		this.recipeIndex = nbt.getInteger("rec");
		
		if(!this.recipes.isEmpty()) {
			slots[9] = this.recipes.get(this.recipeIndex).getCraftingResult(getTemplateGrid());
		} else {
			slots[9] = null;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		matcher.writeToNBT(nbt);
		nbt.setInteger("rec", this.recipeIndex);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerAutocrafter(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIAutocrafter(player.inventory, this);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void setFilterContents(NBTTagCompound nbt) {
		TileEntity tile = this;
		IInventory inv = this;
		int slot = nbt.getInteger("slot");
		if(slot > 8) return;
		NBTTagCompound stack = nbt.getCompoundTag("stack");
		ItemStack item = ItemStack.loadItemStackFromNBT(stack);
		inv.setInventorySlotContents(slot, item);
		matcher.initPatternSmart(getWorldObj(), item, slot);
		tile.getWorldObj().markTileEntityChunkModified(tile.xCoord, tile.yCoord, tile.zCoord, tile);
		updateTemplateGrid();
	}
	@Override
	public int[] getFilterSlots() {
		return new int[]{0,9};
	}
}
