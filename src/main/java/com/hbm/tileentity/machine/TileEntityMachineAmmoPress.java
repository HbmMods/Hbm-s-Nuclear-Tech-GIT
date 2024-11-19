package com.hbm.tileentity.machine;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerMachineAmmoPress;
import com.hbm.inventory.gui.GUIMachineAmmoPress;
import com.hbm.inventory.recipes.AmmoPressRecipes;
import com.hbm.inventory.recipes.AmmoPressRecipes.AmmoPressRecipe;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityMachineAmmoPress extends TileEntityMachineBase implements IControlReceiver, IGUIProvider {
	
	public int selectedRecipe = -1;

	public TileEntityMachineAmmoPress() {
		super(10);
	}

	@Override
	public String getName() {
		return "container.machineAmmoPress";
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			this.performRecipe();
			this.networkPackNT(25);
		}
	}
	
	// we want to update the output every time the grid changes, but producing output changes the grid again, so we just put a recursion brake on this fucker
	public static boolean recipeLock = false;

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		super.setInventorySlotContents(slot, stack);
		
		if(!recipeLock) {
			recipeLock = true;
			if(slot < 10) this.performRecipe();
			recipeLock = false;
		}
	}
	
	public void performRecipe() {
		if(selectedRecipe < 0 || selectedRecipe >= AmmoPressRecipes.recipes.size()) return;
		
		AmmoPressRecipe recipe = AmmoPressRecipes.recipes.get(selectedRecipe);
		
		if(slots[9] != null) {
			if(slots[9].getItem() != recipe.output.getItem()) return;
			if(slots[9].getItemDamage() != recipe.output.getItemDamage()) return;
			if(slots[9].stackSize +  recipe.output.stackSize > slots[9].getMaxStackSize()) return;
		}
		
		if(this.hasIngredients(recipe)) {
			this.produceAmmo(recipe);
			performRecipe();
		}
	}
	
	public boolean hasIngredients(AmmoPressRecipe recipe) {
		
		for(int i = 0; i < 9; i++) {
			if(recipe.input[i] == null && slots[i] == null) continue;
			if(recipe.input[i] != null && slots[i] == null) return false;
			if(recipe.input[i] == null && slots[i] != null) return false;
			if(!recipe.input[i].matchesRecipe(slots[i], false)) return false;
		}
		
		return true;
	}
	
	//implies hasIngredients returns true, will violently explode otherwise
	protected void produceAmmo(AmmoPressRecipe recipe) {
		
		for(int i = 0; i < 9; i++) {
			if(recipe.input[i] != null) this.decrStackSize(i, recipe.input[i].stacksize);
		}
		
		if(slots[9] == null) {
			slots[9] = recipe.output.copy();
		} else {
			slots[9].stackSize += recipe.output.stackSize;
		}
	}

	public int[] access = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return access;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		return i == 9;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot > 8) return false;
		if(selectedRecipe < 0 || selectedRecipe >= AmmoPressRecipes.recipes.size()) return false;
		
		AmmoPressRecipe recipe = AmmoPressRecipes.recipes.get(selectedRecipe);
		if(recipe.input[slot] == null) return false;
		return recipe.input[slot].matchesRecipe(stack, true);
	}

	@Override public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(this.selectedRecipe);
		
	}
	
	@Override public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.selectedRecipe = buf.readInt();
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		int newRecipe = data.getInteger("selection");
		if(newRecipe == selectedRecipe) this.selectedRecipe = -1;
		else this.selectedRecipe = newRecipe;
		this.markDirty();
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerMachineAmmoPress(player.inventory, this); }
	@Override @SideOnly(Side.CLIENT) public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIMachineAmmoPress(player.inventory, this); }
}
