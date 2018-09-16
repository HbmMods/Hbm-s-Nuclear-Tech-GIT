package com.hbm.tileentity.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityNukeCustom extends TileEntity implements ISidedInventory {

	public ItemStack slots[];
	private String customName;
	public float tntStrength;
	public float nukeStrength;
	public float hydroStrength;
	public float amatStrength;
	public float dirtyStrength;
	public float schrabStrength;
	public float euphStrength;
	
	public TileEntityNukeCustom() {
		slots = new ItemStack[27];
		tntStrength = 0;
		nukeStrength = 0;
		hydroStrength = 0;
		amatStrength = 0;
		dirtyStrength = 0;
		schrabStrength = 0;
		euphStrength = 0;
	}
	
	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(slots[i] != null)
		{
			if(slots[i].stackSize <= j)
			{
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0)
			{
				slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(slots[i] != null)
		{
			ItemStack itemStack = slots[i];
			slots[i] = null;
			return itemStack;
		} else {
		return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.nukeCustom";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=64;
		}
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return null;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return j != 0 || i != 1 || itemStack.getItem() == Items.bucket;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length)
			{
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < slots.length; i++)
		{
			if(slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	@Override
	public void updateEntity() {

		this.tntStrength = 0;
		this.nukeStrength = 0;
		this.hydroStrength = 0;
		this.amatStrength = 0;
		this.dirtyStrength = 0;
		this.schrabStrength = 0;
		this.euphStrength = 0;
		
		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null) {
				setValues(slots[i]);
			}
			if(slots[i] != null) {
				setMultipliers(slots[i]);
			}
		}

		if(this.nukeStrength > 0 && this.tntStrength < 16)
			this.nukeStrength = 0;
		if(this.hydroStrength > 0 && this.nukeStrength < 100)
			this.hydroStrength = 0;
		if(this.amatStrength > 0 && this.nukeStrength < 15)
			this.amatStrength = 0;
		if(this.dirtyStrength > 0 && this.nukeStrength == 0)
			this.dirtyStrength = 0;
		if(this.schrabStrength > 0 && this.nukeStrength < 50)
			this.schrabStrength = 0;
		if(this.euphStrength > 0 && this.schrabStrength == 0)
			this.euphStrength = 0;
	}
	
	public void setValues(ItemStack stack) {
		
		Item item = stack.getItem();
		
		for(int i = 0; i < stack.stackSize; i++) {
			if(item == Items.gunpowder) {
				this.tntStrength += 0.8F;
			}
			if(item == Item.getItemFromBlock(Blocks.tnt)) {
				this.tntStrength += 4;
			}
			if(item == Item.getItemFromBlock(ModBlocks.det_cord)) {
				this.tntStrength += 1.5F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.det_charge)) {
				this.tntStrength += 15F;
			}
			if(item == ModItems.canister_fuel) {
				this.tntStrength += 0.3F;
			}
			if(item == ModItems.canister_fuel) {
				this.tntStrength += 0.5F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.red_barrel)) {
				this.tntStrength += 2.5F;
			}
			if(item == ModItems.gun_immolator_ammo) {
				this.tntStrength += 0.055F;
			}
			if(item == ModItems.clip_immolator) {
				this.tntStrength += 3.5F;
			}

			if(item == ModItems.custom_tnt) {
				this.tntStrength += 10F;
			}
			//
			if(item == ModItems.ingot_u235) {
				this.nukeStrength += 15F;
			}
			if(item == ModItems.ingot_pu239) {
				this.nukeStrength += 25F;
			}
			if(item == ModItems.ingot_neptunium) {
				this.nukeStrength += 30F;
			}
			if(item == ModItems.nugget_u235) {
				this.nukeStrength += 1.5F;
			}
			if(item == ModItems.nugget_pu239) {
				this.nukeStrength += 2.5F;
			}
			if(item == ModItems.nugget_neptunium) {
				this.nukeStrength += 3.0F;
			}
			if(item == ModItems.powder_neptunium) {
				this.nukeStrength += 30F;
			}
			
			if(item == ModItems.custom_nuke) {
				this.nukeStrength += 30F;
			}
			//
			if(item == ModItems.cell_deuterium) {
				this.hydroStrength += 20F;
			}
			if(item == ModItems.cell_tritium) {
				this.hydroStrength += 30F;
			}
			if(item == ModItems.lithium) {
				this.hydroStrength += 20F;
			}
			if(item == ModItems.tritium_deuterium_cake) {
				this.hydroStrength += 200F;
			}
			
			if(item == ModItems.custom_hydro) {
				this.hydroStrength += 30F;
			}
			//
			if(item == ModItems.cell_antimatter) {
				this.amatStrength += 5F;
			}
			
			if(item == ModItems.custom_amat) {
				this.amatStrength += 15F;
			}
			//
			if(item == ModItems.ingot_tungsten) {
				this.dirtyStrength += 10F;
			}
			if(item == ModItems.nuclear_waste) {
				this.dirtyStrength += 2.5F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.yellow_barrel)) {
				this.dirtyStrength += 20F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.block_waste)) {
				this.dirtyStrength += 25F;
			}
			
			if(item == ModItems.custom_dirty) {
				this.dirtyStrength += 10F;
			}
			//
			if(item == ModItems.ingot_schrabidium) {
				this.schrabStrength += 5F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.block_schrabidium)) {
				this.schrabStrength += 50F;
			}
			if(item == ModItems.plate_schrabidium) {
				this.schrabStrength += 1.25F;
			}
			if(item == ModItems.nugget_schrabidium) {
				this.schrabStrength += 0.5F;
			}
			if(item == ModItems.cell_sas3) {
				this.schrabStrength += 7.5F;
			}
			if(item == ModItems.cell_anti_schrabidium) {
				this.schrabStrength += 15F;
			}
			
			if(item == ModItems.custom_schrab) {
				this.schrabStrength += 15F;
			}
			//
			if(item == ModItems.nugget_euphemium) {
				this.euphStrength += 1F;
			}
			if(item == ModItems.ingot_euphemium) {
				this.euphStrength += 1F;
			}
		}
	}
	
	public void setMultipliers(ItemStack stack) {
		
		Item item = stack.getItem();
		
		for(int i = 0; i < stack.stackSize; i++) {
			if(item == Items.redstone) {
				this.tntStrength *= 1.005F;
			}
			if(item == Item.getItemFromBlock(Blocks.redstone_block)) {
				this.tntStrength *= 1.05F;
			}
			if(item == ModItems.canister_fuel) {
				this.tntStrength *= 1.025F;
			}
			if(item == ModItems.canister_napalm) {
				this.tntStrength *= 1.035F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.red_barrel)) {
				this.tntStrength *= 1.2F;
			}
			if(item == ModItems.gun_immolator_ammo) {
				this.tntStrength *= 1.0004F;
			}
			if(item == ModItems.clip_immolator) {
				this.tntStrength *= 1.025F;
			}
			//
			if(item == ModItems.ingot_u238) {
				this.nukeStrength *= 1.1F;
				this.hydroStrength *= 1.1F;
				this.dirtyStrength *= 1.1F;
			}
			if(item == ModItems.ingot_pu238) {
				this.nukeStrength *= 1.25F;
			}
			if(item == ModItems.ingot_pu240) {
				this.nukeStrength *= 1.05F;
				this.dirtyStrength *= 1.15F;
			}
			if(item == ModItems.ingot_neptunium) {
				this.nukeStrength *= 1.35F;
				this.dirtyStrength *= 1.15F;
			}
			if(item == ModItems.nugget_u238) {
				this.nukeStrength *= 1.01F;
				this.hydroStrength *= 1.01F;
			}
			if(item == ModItems.nugget_pu238) {
				this.nukeStrength *= 1.025F;
			}
			if(item == ModItems.nugget_pu240) {
				this.nukeStrength *= 1.005F;
				this.dirtyStrength *= 1.015F;
			}
			if(item == ModItems.nugget_neptunium) {
				this.nukeStrength *= 1.035F;
				this.dirtyStrength *= 1.015F;
			}
			if(item == ModItems.powder_neptunium) {
				this.nukeStrength *= 1.35F;
				this.dirtyStrength *= 1.15F;
			}
			if(item == ModItems.ingot_uranium) {
				this.nukeStrength *= 1.085F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.block_uranium)) {
				this.nukeStrength *= 1.85F;
			}
			if(item == ModItems.ingot_plutonium) {
				this.nukeStrength *= 1.075F;
			}
			if(item == ModItems.nugget_uranium) {
				this.nukeStrength *= 1.0085F;
			}
			if(item == ModItems.nugget_plutonium) {
				this.nukeStrength *= 1.0075F;
			}
			if(item == ModItems.powder_uranium) {
				this.nukeStrength *= 1.085F;
				this.dirtyStrength *= 1.15F;
			}
			if(item == ModItems.powder_plutonium) {
				this.nukeStrength *= 1.075F;
				this.dirtyStrength *= 1.15F;
			}
			//
			if(item == ModItems.cell_antimatter) {
				this.amatStrength *= 1.1F;
			}
			//
			if(item == ModItems.nuclear_waste) {
				this.dirtyStrength *= 1.05F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.yellow_barrel)) {
				this.dirtyStrength *= 1.05F;
				this.dirtyStrength *= 1.05F;
				this.dirtyStrength *= 1.05F;
				this.dirtyStrength *= 1.05F;
				this.dirtyStrength *= 1.05F;
				this.dirtyStrength *= 1.05F;
				this.dirtyStrength *= 1.05F;
				this.dirtyStrength *= 1.05F;
			}
		}
	}
	
	public boolean isReady() {
		if(this.tntStrength > 0)
		{
			return true;
		}
		
		return false;
	}
	
	public float[] returnAllValues() {
		return new float[] { this.tntStrength, this.nukeStrength, this.hydroStrength, this.amatStrength, this.dirtyStrength, this.schrabStrength, this.euphStrength};
	}
	
	public void clearSlots() {
		for(int i = 0; i < slots.length; i++)
		{
			slots[i] = null;
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
