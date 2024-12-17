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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineAmmoPress extends TileEntityMachineBase implements IControlReceiver, IGUIProvider {
	
	public int selectedRecipe = -1;
	
	public AnimationState animState = AnimationState.LIFTING;

	public int playAnimation = 0;
	public float prevLift = 0F;
	public float lift = 0F;
	public float prevPress = 0F;
	public float press = 0F;
	
	public static enum AnimationState {
		LIFTING, PRESSING, RETRACTING, LOWERING
	}

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
			if(this.playAnimation > 0) this.playAnimation--;
			this.performRecipe();
			this.networkPackNT(25);
		} else {
			
			this.prevLift = this.lift;
			this.prevPress = this.press;
			
			if(playAnimation > 0 || lift > 0) switch(animState) {
			case LIFTING:
				this.lift += 1F / 40F;
				if(this.lift >= 1F) {
					this.lift = 1F;
					this.animState = AnimationState.PRESSING;
				} break;
			case PRESSING:
				this.press += 1F / 20F;
				if(this.press >= 1F) {
					this.press = 1F;
					this.animState = AnimationState.RETRACTING;
				} break;
			case RETRACTING:
				this.press -= 1F / 20F;
				if(this.press <= 0F) {
					this.press = 0F;
					this.animState = AnimationState.LOWERING;
				} break;
			case LOWERING:
				this.lift -= 1F / 40F;
				if(this.lift <= 0F) {
					this.lift = 0F;
					this.animState = AnimationState.LIFTING;
				} break;
			}
		}
	}
	
	// we want to update the output every time the grid changes, but producing output changes the grid again, so we just put a recursion brake on this fucker
	public static boolean recipeLock = false;

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		super.setInventorySlotContents(slot, stack);
		/*if(this.worldObj.isRemote) return;
		
		//while this allowed one shift click to process absolutely everything, it also caused a fuckton of issues
		if(!recipeLock) {
			recipeLock = true;
			if(slot < 10) this.performRecipe();
			recipeLock = false;
		}*/
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
		
		this.playAnimation = 40;
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
		buf.writeInt(this.playAnimation);
		
	}
	
	@Override public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.selectedRecipe = buf.readInt();
		this.playAnimation = buf.readInt();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.selectedRecipe = nbt.getInteger("recipe");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("recipe", selectedRecipe);
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
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 2,
					zCoord + 2
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerMachineAmmoPress(player.inventory, this); }
	@Override @SideOnly(Side.CLIENT) public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIMachineAmmoPress(player.inventory, this); }
}
