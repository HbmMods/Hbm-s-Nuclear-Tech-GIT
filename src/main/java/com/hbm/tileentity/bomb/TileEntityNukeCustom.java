package com.hbm.tileentity.bomb;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.NukeCustom;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerNukeCustom;
import com.hbm.inventory.gui.GUINukeCustom;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityNukeCustom extends TileEntity implements ISidedInventory, IGUIProvider {

	public ItemStack slots[];
	private String customName;
	
	public TileEntityNukeCustom() {
		slots = new ItemStack[27];
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
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
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
	
	public static HashMap<ComparableStack, CustomNukeEntry> entries = new HashMap();
	
	public static void registerBombItems() {

		entries.put(new ComparableStack(Items.gunpowder), new CustomNukeEntry(EnumBombType.TNT, 0.8F));
		entries.put(new ComparableStack(Blocks.tnt), new CustomNukeEntry(EnumBombType.TNT, 4F));
		entries.put(new ComparableStack(ModBlocks.det_cord), new CustomNukeEntry(EnumBombType.TNT, 1.5F));
		entries.put(new ComparableStack(ModItems.ingot_semtex), new CustomNukeEntry(EnumBombType.TNT, 8F));
		entries.put(new ComparableStack(ModBlocks.det_charge), new CustomNukeEntry(EnumBombType.TNT, 15F));
		entries.put(new ComparableStack(ModBlocks.red_barrel), new CustomNukeEntry(EnumBombType.TNT, 2.5F));
		entries.put(new ComparableStack(ModBlocks.pink_barrel), new CustomNukeEntry(EnumBombType.TNT, 4F));
		entries.put(new ComparableStack(ModItems.custom_tnt), new CustomNukeEntry(EnumBombType.TNT, 10F));

		entries.put(new ComparableStack(ModItems.ingot_u235), new CustomNukeEntry(EnumBombType.NUKE, 15F));
		entries.put(new ComparableStack(ModItems.ingot_pu239), new CustomNukeEntry(EnumBombType.NUKE, 25F));
		entries.put(new ComparableStack(ModItems.ingot_neptunium), new CustomNukeEntry(EnumBombType.NUKE, 30F));
		entries.put(new ComparableStack(ModItems.nugget_u235), new CustomNukeEntry(EnumBombType.NUKE, 1.5F));
		entries.put(new ComparableStack(ModItems.nugget_pu239), new CustomNukeEntry(EnumBombType.NUKE, 2.5F));
		entries.put(new ComparableStack(ModItems.nugget_neptunium), new CustomNukeEntry(EnumBombType.NUKE, 3.0F));
		entries.put(new ComparableStack(ModItems.powder_neptunium), new CustomNukeEntry(EnumBombType.NUKE, 30F));
		entries.put(new ComparableStack(ModItems.custom_nuke), new CustomNukeEntry(EnumBombType.NUKE, 30F));

		entries.put(new ComparableStack(ModItems.cell_deuterium), new CustomNukeEntry(EnumBombType.HYDRO, 20F));
		entries.put(new ComparableStack(ModItems.cell_tritium), new CustomNukeEntry(EnumBombType.HYDRO, 30F));
		entries.put(new ComparableStack(ModItems.lithium), new CustomNukeEntry(EnumBombType.HYDRO, 20F));
		entries.put(new ComparableStack(ModItems.tritium_deuterium_cake), new CustomNukeEntry(EnumBombType.HYDRO, 200F));
		entries.put(new ComparableStack(ModItems.custom_hydro), new CustomNukeEntry(EnumBombType.HYDRO, 30F));

		entries.put(new ComparableStack(ModItems.cell_antimatter), new CustomNukeEntry(EnumBombType.AMAT, 5F));
		entries.put(new ComparableStack(ModItems.custom_amat), new CustomNukeEntry(EnumBombType.AMAT, 15F));
		entries.put(new ComparableStack(ModItems.egg_balefire_shard), new CustomNukeEntry(EnumBombType.AMAT, 15F));
		entries.put(new ComparableStack(ModItems.egg_balefire), new CustomNukeEntry(EnumBombType.AMAT, 150F));

		entries.put(new ComparableStack(ModItems.ingot_tungsten), new CustomNukeEntry(EnumBombType.DIRTY, 1F));
		entries.put(new ComparableStack(ModItems.custom_dirty), new CustomNukeEntry(EnumBombType.DIRTY, 10F));

		entries.put(new ComparableStack(ModItems.ingot_schrabidium), new CustomNukeEntry(EnumBombType.SCHRAB, 5F));
		entries.put(new ComparableStack(ModBlocks.block_schrabidium), new CustomNukeEntry(EnumBombType.SCHRAB, 50F));
		entries.put(new ComparableStack(ModItems.nugget_schrabidium), new CustomNukeEntry(EnumBombType.SCHRAB, 0.5F));
		entries.put(new ComparableStack(ModItems.powder_schrabidium), new CustomNukeEntry(EnumBombType.SCHRAB, 5F));
		entries.put(new ComparableStack(ModItems.cell_sas3), new CustomNukeEntry(EnumBombType.SCHRAB, 7.5F));
		entries.put(new ComparableStack(ModItems.cell_anti_schrabidium), new CustomNukeEntry(EnumBombType.SCHRAB, 15F));
		entries.put(new ComparableStack(ModItems.custom_schrab), new CustomNukeEntry(EnumBombType.SCHRAB, 15F));

		entries.put(new ComparableStack(ModItems.nugget_euphemium), new CustomNukeEntry(EnumBombType.EUPH, 1F));
		entries.put(new ComparableStack(ModItems.ingot_euphemium), new CustomNukeEntry(EnumBombType.EUPH, 1F));

		entries.put(new ComparableStack(Items.redstone), new CustomNukeEntry(EnumBombType.TNT, 1.05F, EnumEntryType.MULT));
		entries.put(new ComparableStack(Blocks.redstone_block), new CustomNukeEntry(EnumBombType.TNT, 1.5F, EnumEntryType.MULT));

		entries.put(new ComparableStack(ModItems.ingot_uranium), new CustomNukeEntry(EnumBombType.NUKE, 1.05F, EnumEntryType.MULT));
		entries.put(new ComparableStack(ModItems.ingot_plutonium), new CustomNukeEntry(EnumBombType.NUKE, 1.15F, EnumEntryType.MULT));
		entries.put(new ComparableStack(ModItems.ingot_u238), new CustomNukeEntry(EnumBombType.NUKE, 1.1F, EnumEntryType.MULT));
		entries.put(new ComparableStack(ModItems.ingot_pu238), new CustomNukeEntry(EnumBombType.NUKE, 1.15F, EnumEntryType.MULT));
		entries.put(new ComparableStack(ModItems.nugget_uranium), new CustomNukeEntry(EnumBombType.NUKE, 1.005F, EnumEntryType.MULT));
		entries.put(new ComparableStack(ModItems.nugget_plutonium), new CustomNukeEntry(EnumBombType.NUKE, 1.15F, EnumEntryType.MULT));
		entries.put(new ComparableStack(ModItems.nugget_u238), new CustomNukeEntry(EnumBombType.NUKE, 1.01F, EnumEntryType.MULT));
		entries.put(new ComparableStack(ModItems.nugget_pu238), new CustomNukeEntry(EnumBombType.NUKE, 1.015F, EnumEntryType.MULT));
		entries.put(new ComparableStack(ModItems.powder_uranium), new CustomNukeEntry(EnumBombType.NUKE, 1.05F, EnumEntryType.MULT));
		entries.put(new ComparableStack(ModItems.powder_plutonium), new CustomNukeEntry(EnumBombType.NUKE, 1.15F, EnumEntryType.MULT));

		entries.put(new ComparableStack(ModItems.ingot_pu240), new CustomNukeEntry(EnumBombType.DIRTY, 1.05F, EnumEntryType.MULT));
		entries.put(new ComparableStack(ModItems.nuclear_waste), new CustomNukeEntry(EnumBombType.DIRTY, 1.025F, EnumEntryType.MULT));
		entries.put(new ComparableStack(ModBlocks.block_waste), new CustomNukeEntry(EnumBombType.DIRTY, 1.25F, EnumEntryType.MULT));
		entries.put(new ComparableStack(ModBlocks.yellow_barrel), new CustomNukeEntry(EnumBombType.DIRTY, 1.2F, EnumEntryType.MULT));
	}

	public float tnt;
	public float nuke;
	public float hydro;
	public float amat;
	public float dirty;
	public float schrab;
	public float euph;
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public void updateEntity() {
		
		float tnt = 0F,		tntMod = 1F;
		float nuke = 0F,	nukeMod = 1F;
		float hydro = 0F,	hydroMod = 1F;
		float amat = 0F,	amatMod = 1F;
		float dirty = 0F,	dirtyMod = 1F;
		float schrab = 0F,	schrabMod = 1F;
		float euph = 0F;
		
		for(ItemStack stack : slots) {
			
			if(stack == null)
				continue;
			
			ComparableStack comp = new ComparableStack(stack).makeSingular();
			CustomNukeEntry ent = entries.get(comp);
			
			if(ent == null)
				continue;
			
			if(ent.entry == EnumEntryType.ADD) {
				
				switch(ent.type) {
				case TNT: tnt += ent.value * stack.stackSize; break;
				case NUKE: nuke += ent.value * stack.stackSize; break;
				case HYDRO: hydro += ent.value * stack.stackSize; break;
				case AMAT: amat += ent.value * stack.stackSize; break;
				case DIRTY: dirty += ent.value * stack.stackSize; break;
				case SCHRAB: schrab += ent.value * stack.stackSize; break;
				case EUPH: euph += ent.value * stack.stackSize; break;
				}
				
			} else if(ent.entry == EnumEntryType.MULT) {
				
				switch(ent.type) {
				case TNT: tntMod *= ent.value * stack.stackSize; break;
				case NUKE: nukeMod *= ent.value * stack.stackSize; break;
				case HYDRO: hydroMod *= ent.value * stack.stackSize; break;
				case AMAT: amatMod *= ent.value * stack.stackSize; break;
				case DIRTY: dirtyMod *= ent.value * stack.stackSize; break;
				case SCHRAB: schrabMod *= ent.value * stack.stackSize; break;
				}
			}
		}
		
		tnt *= tntMod;
		nuke *= nukeMod;
		hydro *= hydroMod;
		amat *= amatMod;
		dirty *= dirtyMod;
		schrab *= schrabMod;
		
		if(tnt < 16) nuke = 0;
		if(nuke < 100) hydro = 0;
		if(nuke < 50) amat = 0;
		if(nuke < 50) schrab = 0;
		if(schrab == 0) euph = 0;

		this.tnt = tnt;
		this.nuke = nuke;
		this.hydro = hydro;
		this.amat = amat;
		this.dirty = dirty;
		this.schrab = schrab;
		this.euph = euph;
	}
	
	public float getNukeAdj() {
		
		if(nuke == 0)
			return 0;
		
		return Math.min(nuke + tnt / 2, NukeCustom.maxNuke);
	}
	
	public float getHydroAdj() {
		
		if(hydro == 0)
			return 0;
		
		return Math.min(hydro + nuke / 2 + tnt / 4, NukeCustom.maxHydro);
	}
	
	public float getAmatAdj() {
		
		if(amat == 0)
			return 0;
		
		return Math.min(amat + hydro / 2 + nuke / 4 + tnt / 8, NukeCustom.maxAmat);
	}
	
	public float getSchrabAdj() {
		
		if(schrab == 0)
			return 0;
		
		return Math.min(schrab + amat / 2 + hydro / 4 + nuke / 8 + tnt / 16, NukeCustom.maxSchrab);
	}
	
	public boolean isFalling() {
		
		for(ItemStack stack : slots) {
			if(stack != null && stack.getItem() == ModItems.custom_fall)
				return true;
		}
		
		return false;
	}
	
	public void destruct() {
		
		clearSlots();
		worldObj.func_147480_a(xCoord, yCoord, xCoord, false);
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
	
	public static enum EnumBombType {
		TNT("TNT"),
		NUKE("Nuclear"),
		HYDRO("Hydrogen"),
		AMAT("Antimatter"),
		DIRTY("Salted"),
		SCHRAB("Schrabidium"),
		EUPH("Anti Mass");
		
		String name;
		
		EnumBombType(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	public static enum EnumEntryType {
		ADD,
		MULT
	}
	
	public static class CustomNukeEntry {
		
		public EnumBombType type;
		public EnumEntryType entry;
		public float value;
		
		public CustomNukeEntry(EnumBombType type, float value) {
			this.type = type;
			this.entry = EnumEntryType.ADD;
			this.value = value;
		}
		
		public CustomNukeEntry(EnumBombType type, float value, EnumEntryType entry) {
			this(type, value);
			this.entry = entry;
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerNukeCustom(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUINukeCustom(player.inventory, this);
	}
}
