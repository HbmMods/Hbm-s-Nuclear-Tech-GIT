package com.hbm.items.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemStorageMedium extends Item
{
	EnumRarity rarity;
	boolean hasEffect;
	private Item dropItem = null;
	private EnumStorageItemType type;
	
	private long readRate;
	private long writeRate;
	private long maxCapacity;
	private ArrayList<EnumStorageItemTraits> traits = new ArrayList<EnumStorageItemTraits>();
	private boolean exposed;
	private boolean aux;
	private ArrayList<ProsConsList> prosCons = new ArrayList<>();
	
	// Scaling
	public static final int KB = 1000;
	public static final int MB = 1000000;
	public static final int GB = 1000000000;
	public static final long TB = 100000000000L;
	public static final long PB = 100000000000000L;
	public static final long ZB = 100000000000000000L;
	/**
	 * Construct a new data storage medium
	 * @param maxCapacity - Total capacity of the drive
	 * @param writeRate - Rate of which data is modified from the drive
	 * @param readRate - Rate of which data is read from the drive
	 * @param aux - Whether or not the medium is an auxiliary drive (can it be quickly added and removed from a drive)
	 * @param type - The technology the medium uses, determines some traits
	 */
	public ItemStorageMedium(long maxCapacity, long writeRate, long readRate, boolean aux, EnumStorageItemType type)
	{
		this.maxCapacity = maxCapacity;
		this.writeRate = writeRate;
		this.readRate = readRate;
		this.aux = aux;
		this.maxStackSize = 1;
		this.type = type;
		getProsCons();
	}
	
	public ItemStorageMedium specialProsCons(ProsConsList... traits)
	{
		for (ProsConsList trait : traits)
			prosCons.add(trait);
		Collections.sort(prosCons);
		return this;
	}
	
	private void getProsCons()
	{
		switch (this.type)
		{
		case MAGNETIC_BASIC:
			prosCons.add(ProsConsList.LIFESPAN_GOOD);
			prosCons.add(ProsConsList.CHEAP_VERY);
			prosCons.add(ProsConsList.MAGNETIC);
			prosCons.add(ProsConsList.FRAGILE);
			prosCons.add(ProsConsList.MAGNET);
			break;
		case MAGNETIC_ENHANCED:
			prosCons.add(ProsConsList.LIFESPAN_DECENT);
			prosCons.add(ProsConsList.MAGNETIC);
			prosCons.add(ProsConsList.FRAGILE);
			prosCons.add(ProsConsList.MAGNET);
			prosCons.add(ProsConsList.MAGNETIC);
			prosCons.add(ProsConsList.MECHANICAL);
			prosCons.add(ProsConsList.FRAGILE);
			prosCons.add(ProsConsList.MECHANICAL_BAD);
			break;
		default:
			prosCons.add(ProsConsList.NA);
			break;
		}
		if (this.exposed)
			prosCons.add(ProsConsList.EXPOSED);
		Collections.sort(prosCons);
	}
	
	public static enum EnumStorageItemType
	{
		MAGNETIC_BASIC,// FDDs & tape drives, tells it its very weak to magnets
		MAGNETIC_ENHANCED,// HDDs, somewhat weak to magnets
		OPTICAL,// CDs, DVDs, & BDs, immune to magnets
		FLASH,// SSDs & USBs, immune to magnets
		MAGNETIC_ADVANCED,// Super high capacity tape drives, very weak to magnets
		CRYSTALLINE,// Quite stable, immune to almost everything, overall very good albiet expensive, similar to optical
		SINGULARITY;// Endgame(?), immune to radiation
	}
	public static enum EnumStorageItemTraits
	{
		//DIRTY,// Prone to errors, makes more of a hassle to use, but easy to fix
		DAMAGED,// Physically damaged, prone to further corruption, fixable?
		CORRUPTED_LIGHT,// Data is somewhat corrupted, can be fixed relatively easily
		CORRUPTED_HEAVY,// Data is very corrupted, requires more effort
		CORRUPTED_ABSOLUTE;// Corruption is irreversible, just re-format it
	}
	public static enum ProsConsList
	{
		LIFESPAN_GOOD("pro.lifespan1"),
		LIFESPAN_DECENT("pro.lifespan2"),
		CHEAP_VERY("pro.cheap_very"),
		CAPACITY_GOOD("pro.capacity1"),
		COMPACT("pro.compact"),
		SPEED_GOOD("pro.speed1"),
		MAGNETIC("neut.magnetic"),
		MECHANICAL("neut.mechanical"),
		OPTICAL("neut.optical"),
		FLASH("neut.flash"),
		NA("neut.na"),
		FRAGILE("con.fragile"),
		SPEED_POOR("con.speed1"),
		MAGNET("con.magnet"),
		EXPOSED("con.exposed"),
		MECHANICAL_BAD("con.mechanical"),
		CAPACITY_POOR("con.capacity1"),
		NAME("con.name");
		public String key;
		private ProsConsList(String string)
		{
			key = string;
		}
	}
	
	public void writeData(ItemStack stack, long i)
	{
		if (stack.getItem() instanceof ItemStorageMedium)
		{
			if (stack.hasTagCompound())
			{
				stack.stackTagCompound.setLong("used", stack.stackTagCompound.getLong("used") + i);
			}
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("used", i);
			}
		}
	}
	
	public void setData(ItemStack stack, long i)
	{
		if (stack.getItem() instanceof ItemStorageMedium)
		{
			if (stack.hasTagCompound())
			{
				stack.stackTagCompound.setLong("used", i);
			}
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("used", i);
			}
		}
	}
	
	public void deleteData(ItemStack stack, long i)
	{
		if (stack.getItem() instanceof ItemStorageMedium)
		{
			if (stack.hasTagCompound())
			{
				stack.stackTagCompound.setLong("used", stack.stackTagCompound.getLong("used") - i);
			}
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("used", this.maxCapacity - i);
			}
		}
	}
	
	public long getDataUsed(ItemStack stack)
	{
		if (stack.getItem() instanceof ItemStorageMedium)
		{
			if (stack.hasTagCompound())
			{
				return stack.stackTagCompound.getLong("used");
			}
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("used", ((ItemStorageMedium) stack.getItem()).maxCapacity);
				return stack.stackTagCompound.getLong("used");
			}
		}
		return 0;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool)
	{
		String unloc = this.getUnlocalizedName() + ".desc";
		String loc = I18nUtil.resolveKey(unloc);
		if (!unloc.equals(loc))
		{
			String[] locs = loc.split("\\$");
			
			for (String s : locs)
			{
				list.add(s);
			}
		}
		// Auto-generated
		if (this != ModItems.storage_magnetic_fdd_tainted)
		{
			long written = this.maxCapacity;
			if (itemStack.hasTagCompound())
			{
				written = getDataUsed(itemStack);
			}
			String capacityPercent = Library.getShortNumber((written * 100) / this.maxCapacity);
			list.add("Capacity: " + capacityPercent + "%");
			list.add(String.format("(%s/%sbyte)", Library.getShortNumber(written), Library.getShortNumber(maxCapacity)));
			list.add(String.format("Write rate: %sbyte/s", Library.getShortNumber(writeRate)));
			list.add(String.format("Read rate: %sbyte/s", Library.getShortNumber(readRate)));
		}
			
		// Pros-cons list and lore
		if (this == ModItems.storage_magnetic_fdd_tainted)
		{
			list.add(I18nUtil.resolveKey(this.getUnlocalizedName() + ".desc19"));
			list.add("");
			// The fate of the world is now (literally) in your hands
			//list.add(EnumChatFormatting.RED + specialLore[MainRegistry.polaroidID - 1]);
			list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("item.storage_magnetic_fdd_tainted.desc" + MainRegistry.polaroidID));
			list.add("");
			list.add(I18nUtil.resolveKey(this.getUnlocalizedName() + ".desc20"));
		}
		
//		for (EnumStorageItemTraits trait : EnumStorageItemTraits.values())
//		{
//			if (itemStack.stackTagCompound.getBoolean(trait.toString()))
//			{
//				this.traits.add(trait);
//			}
//		}

		if (this != ModItems.storage_magnetic_fdd_tainted)
		{
			list.add("");
			if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			{
				list.add(String.format("%s%sHold <%sLSHIFT%s%s%s> to view pros/cons list", EnumChatFormatting.DARK_GRAY, EnumChatFormatting.ITALIC, EnumChatFormatting.YELLOW, EnumChatFormatting.RESET, EnumChatFormatting.DARK_GRAY, EnumChatFormatting.ITALIC));
			}
			else
			{
				for (ProsConsList trait : prosCons)
				{
					list.add(I18nUtil.resolveKey("storage.desc." + trait.key));
				}
//				if (this == ModItems.storage_magnetic_r_to_r)
//				{
//					list.add(EnumChatFormatting.BLUE + "+ Excellent lifespan");
//					list.add(EnumChatFormatting.BLUE + "+ Extremely cheap");
//					list.add(EnumChatFormatting.YELLOW + "* Magnetic medium");
//					list.add(EnumChatFormatting.RED + "- Fragile");
//					list.add(EnumChatFormatting.RED + "- Pitiful r/w speed");
//					list.add(EnumChatFormatting.RED + "- Easily wiped by magnets");
//					list.add(EnumChatFormatting.RED + "- Exposed to the elements");
//				}
//				if (this == ModItems.storage_magnetic_cassette)
//				{
//					list.add(EnumChatFormatting.BLUE + "+ Excellent lifespan");
//					list.add(EnumChatFormatting.BLUE + "+ Extremely cheap");
//					list.add(EnumChatFormatting.YELLOW + "* Magnetic medium");
//					list.add(EnumChatFormatting.RED + "- Pitiful r/w speed");
//					list.add(EnumChatFormatting.RED + "- Easily wiped by magnets");
//				}
//				if (this == ModItems.storage_magnetic_fdd)
//				{
//					list.add(EnumChatFormatting.BLUE + "+ Excellent lifespan");
//					list.add(EnumChatFormatting.BLUE + "+ Extremely cheap");
//					list.add(EnumChatFormatting.BLUE + "+ Very compact");
//					list.add(EnumChatFormatting.YELLOW + "* Magnetic medium");
//					list.add(EnumChatFormatting.RED + "- Pitiful storage capacity");
//					list.add(EnumChatFormatting.RED + "- Easily wiped by magnets");
//				}
//				if (this == ModItems.storage_hdd)
//				{
//					list.add(EnumChatFormatting.BLUE + "+ Very good capacity");
//					list.add(EnumChatFormatting.BLUE + "+ Good lifespan");
//					list.add(EnumChatFormatting.BLUE + "+ Decent r/w speed");
//					list.add(EnumChatFormatting.YELLOW + "* Magnetic medium");
//					list.add(EnumChatFormatting.YELLOW + "* Mechanical parts");
//					list.add(EnumChatFormatting.RED + "- Fragile");
//					list.add(EnumChatFormatting.RED + "- Vulnerable to magnets");
//					list.add(EnumChatFormatting.RED + "- Prone to mechanical failure");
//				}
//				if (this == ModItems.storage_hdd_dead)
//				{
//					list.add("The mechanical parts are damaged, data cannot be read or written, it can be repaired however");
//				}
//				if (this == ModItems.storage_optical_bd)
//				{
//					list.add(EnumChatFormatting.RED + "- Dumb name");
//				}
			}
		}
	}
	
	public String getTraitTranslation(EnumStorageItemTraits trait)
	{
		switch (trait)
		{
		/*case DIRTY:
			return "trait.dirty";*/
		case CORRUPTED_LIGHT:
			return "trait.corruptedLight";
		case CORRUPTED_HEAVY:
			return "trait.corruptedHeavy";
		case CORRUPTED_ABSOLUTE:
			return "trait.corruptedAbsolute";
		default:
			return null;
		}
	}
	
	public long getMaxCapacity()
	{
		return maxCapacity;
	}
	public long getWriteSpeed()
	{
		return writeRate;
	}
	public long getReadSpeed()
	{
		return readRate;
	}
	public static ItemStack getBlankMedium(Item item, int count)
	{
		if (item instanceof ItemStorageMedium)
		{
			ItemStack stack = new ItemStack(item);
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("used", 0);
			stack.stackSize = count;
			return stack.copy();
		}
		return null;
	}
	public static ItemStack getFullMedium(Item item, int count)
	{
		if (item instanceof ItemStorageMedium)
		{
			ItemStack stack = new ItemStack(item);
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("used", ((ItemStorageMedium) item).getMaxCapacity());
			stack.stackSize = count;
			return stack.copy();
		}
		return new ItemStack(item);
	}
	public boolean showDurabilityBar(ItemStack stack)
	{
		return true;
	}
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return 1D - (double) getDataUsed(stack) / (double) getMaxCapacity();
	}
	/**
	 * If not null, when dropped, the item will turn into the one specified to simulate "breaking"
	 * @param item - The broken form
	 * @return Itself
	 */
	public ItemStorageMedium setFragile(Item item)
	{
		if (item != null)
		{
			this.dropItem = item;
		}
		return this;
	}
	/**
	 * Sets the item to become "dirty" when in inventory after some time
	 * @return Itself
	 */
	public ItemStorageMedium isExposed()
	{
		exposed = true;
		return this;
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
	{
		if (entityItem != null && !entityItem.worldObj.isRemote && entityItem.onGround && dropItem != null)
		{
			entityItem.setEntityItemStack(new ItemStack(dropItem));
			return true;
		}		
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		if (this == ModItems.storage_magnetic_fdd_tainted)
			return true;
		return false;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
	{
		return this.rarity != null ? rarity : EnumRarity.common;
	}
	
	public ItemStorageMedium setRarity(EnumRarity rarity)
	{
		this.rarity = rarity;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		if (this.writeRate > 0)
		{
			list.add(getBlankMedium(item, 1));
			list.add(getFullMedium(item, 1));
		}
	}
	@Override
	public Item setMaxStackSize(int p_77625_1_)
	{
		return super.setMaxStackSize(1);
	}
}
