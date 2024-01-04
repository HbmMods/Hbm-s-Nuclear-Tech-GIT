package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerFunnel;
import com.hbm.inventory.gui.GUIFunnel;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.machine.TileEntityMachineAutocrafter.InventoryCraftingAuto;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class TileEntityMachineFunnel extends TileEntityMachineBase implements IGUIProvider {

	public TileEntityMachineFunnel() {
		super(18);
	}

	@Override
	public String getName() {
		return "container.machineFunnel";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			for(int i = 0; i < 9; i++) {
				
				if(slots[i] != null) {
					int stacksize = 9;
					ItemStack compressed = slots[i].stackSize < 9 ? null : this.getFrom9(slots[i]);
					if(compressed == null) {
						compressed = slots[i].stackSize < 4 ? null : this.getFrom4(slots[i]);
						stacksize = 4;
					}
					
					if(compressed != null && slots[i].stackSize >= stacksize) {
						if(slots[i + 9] == null) {
							slots[i + 9] = compressed.copy();
							this.decrStackSize(i, stacksize);
						} else if(slots[i + 9].getItem() == compressed.getItem() && slots[i + 9].getItemDamage() == compressed.getItemDamage() && slots[i + 9].stackSize + compressed.stackSize <= compressed.getMaxStackSize()) {
							slots[i + 9].stackSize += compressed.stackSize;
							this.decrStackSize(i, stacksize);
						}
					}
				}
			}
		}
	}

	public int[] topAccess = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	public int[] bottomAccess = new int[] { 9, 10, 11, 12, 13, 14, 15, 16, 17 };
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? bottomAccess : topAccess;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		if(j == 0) return i > 8;
		return j != 1 && i < 9;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot > 8) return false;
		return this.getFrom9(stack) != null || this.getFrom4(stack) != null;
	}
	
	protected InventoryCraftingAuto craftingInventory = new InventoryCraftingAuto(3, 3);
	
	public ItemStack getFrom4(ItemStack ingredient) {
		this.craftingInventory.clear();
		this.craftingInventory.setInventorySlotContents(0, ingredient.copy());
		this.craftingInventory.setInventorySlotContents(1, ingredient.copy());
		this.craftingInventory.setInventorySlotContents(3, ingredient.copy());
		this.craftingInventory.setInventorySlotContents(4, ingredient.copy());
		return getMatch(this.craftingInventory);
	}
	
	public ItemStack getFrom9(ItemStack ingredient) {
		this.craftingInventory.clear();
		for(int i = 0; i < 9; i++) this.craftingInventory.setInventorySlotContents(i, ingredient.copy());
		return getMatch(this.craftingInventory);
	}
	
	public ItemStack getMatch(InventoryCrafting grid) {
		for(Object o : CraftingManager.getInstance().getRecipeList()) {
			IRecipe recipe = (IRecipe) o;
			
			if(recipe.matches(grid, worldObj)) {
				return recipe.getCraftingResult(grid);
			}
		}
		return null;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFunnel(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFunnel(player.inventory, this);
	}
}
