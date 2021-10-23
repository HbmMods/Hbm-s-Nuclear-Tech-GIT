package com.hbm.items.machine;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import org.lwjgl.input.Keyboard;

import com.google.common.annotations.Beta;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.special.ItemCustomLore;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;

import api.hbm.internet.IDataStorageUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
@Beta
@Spaghetti("Could  be a lot better tbh")
public class ItemStorageMedium extends ItemCustomLore implements IDataStorageUser
{
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
		setMaxStackSize(0);
		this.type = type;
		getProsCons();
	}
	
	public ItemStorageMedium specialProsCons(ProsConsList... traits)
	{
		for (ProsConsList trait : traits)
			prosCons.add(trait);
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
		//DIRTY("dirty"),// Prone to errors, makes more of a hassle to use, but easy to fix
		DAMAGED("damaged"),// Physically damaged, prone to further corruption, fixable?
		CORRUPTED_LIGHT("corruptedLight"),// Data is somewhat corrupted, can be fixed relatively easily
		CORRUPTED_HEAVY("corruptedHeavy"),// Data is very corrupted, requires more effort
		CORRUPTED_ABSOLUTE("corruptedAbsolute");// Corruption is irreversible, just re-format it
		public String key;
		private EnumStorageItemTraits(String string)
		{
			key = "trait." + string;
		}
	}
	public static enum ProsConsList
	{
		LIFESPAN_GOOD("pro.lifespan1"),
		LIFESPAN_DECENT("pro.lifespan2"),
		CHEAP_VERY("pro.cheap_very"),
		CAPACITY_GOOD("pro.capacity1"),
		COMPACT("pro.compact"),
		SPEED_GOOD("pro.speed1"),
		MAGNETIC("neu.magnetic"),
		MECHANICAL("neu.mechanical"),
		OPTICAL("neu.optical"),
		FLASH("neu.flash"),
		NA("neu.na"),
		FRAGILE("con.fragile"),
		SPEED_POOR("con.speed1"),
		MAGNET("con.magnet"),
		EXPOSED("con.exposed"),
		MECHANICAL_BAD("con.mechanical"),
		CAPACITY_POOR("con.capacity1"),
		NAME("con.name");
		public String key = "storage.desc.";
		private ProsConsList(String string)
		{
			key += string;
		}
	}
	
	public void writeData(ItemStack stack, long i)
	{
		if (stack.getItem() instanceof ItemStorageMedium)
		{
			if (stack.hasTagCompound())
				stack.stackTagCompound.setLong("used", stack.stackTagCompound.getLong("used") + i);
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
				stack.stackTagCompound.setLong("used", i);
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("used", i);
			}
		}
	}
	
	@Override
	public void deleteData(ItemStack stack, long i)
	{
		if (stack.getItem() instanceof ItemStorageMedium)
		{
			if (stack.hasTagCompound())
				stack.stackTagCompound.setLong("used", stack.stackTagCompound.getLong("used") - i);
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
				return stack.stackTagCompound.getLong("used");
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("used", ((ItemStorageMedium) stack.getItem()).maxCapacity);
				return stack.stackTagCompound.getLong("used");
			}
		}
		return 0;
	}
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool)
	{
		super.addInformation(itemStack, player, list, bool);
		// Auto-generated
		long written = this.maxCapacity;
		if (itemStack.hasTagCompound())
			written = getDataUsed(itemStack);
		
		String capacityPercent = Library.getShortNumber((written * 100) / this.maxCapacity);
		list.add("Capacity: " + capacityPercent + "%");
		list.add(String.format("(%s/%sbyte)", Library.getShortNumber(written), Library.getShortNumber(maxCapacity)));
		list.add(String.format("Write rate: %sbyte/s", Library.getShortNumber(writeRate)));
		list.add(String.format("Read rate: %sbyte/s", Library.getShortNumber(readRate)));
		
		list.add("");
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			list.add(String.format("%s%sHold <%sLSHIFT%s%s%s> to view pros/cons list", EnumChatFormatting.DARK_GRAY, EnumChatFormatting.ITALIC, EnumChatFormatting.YELLOW, EnumChatFormatting.RESET, EnumChatFormatting.DARK_GRAY, EnumChatFormatting.ITALIC));
		else
		{
			// There's probably a better way of doing this, I'll figure it out eventually, but until then, this'll do
			// Get pros
			for (ProsConsList trait : prosCons)
				if (trait.key.contains("pro"))
					list.add(EnumChatFormatting.BLUE + I18nUtil.resolveKey(trait.key));
			// Get neutral
			for (ProsConsList trait : prosCons)
				if (trait.key.contains("neu"))
					list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey(trait.key));
			// Get cons
			for (ProsConsList trait : prosCons)
				if (trait.key.contains("con"))
					list.add(EnumChatFormatting.RED + I18nUtil.resolveKey(trait.key));
		}
		
	}
	
	@Override
	public long getMaxCapacity()
	{
		return maxCapacity;
	}
	@Override
	public long getWriteSpeed()
	{
		return writeRate;
	}
	@Override
	public long getReadSpeed()
	{
		return readRate;
	}
	@Deprecated
	public static ItemStack getBlankMedium(Item item)
	{
		if (item instanceof ItemStorageMedium)
		{
			ItemStack stack = new ItemStack(item);
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("used", 0);
			stack.stackSize = 1;
			return stack.copy();
		}
		return null;
	}
	@Deprecated
	public static ItemStack getFullMedium(Item item)
	{
		if (item instanceof ItemStorageMedium)
		{
			ItemStack stack = new ItemStack(item);
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("used", ((ItemStorageMedium) item).getMaxCapacity());
			stack.stackSize = 1;
			return stack.copy();
		}
		return new ItemStack(item);
	}
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return true;
	}
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return 1D - (double) getDataUsed(stack) / (double) getMaxCapacity();
	}
	/**
	 * If not null, when dropped, the item will turn into the one specified to simulate "breaking"
	 * @param item - The broken form
	 * @return Itself
	 */
	public ItemStorageMedium setFragile(@Nonnull Item item)
	{
		this.dropItem = item;
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
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		if (this.writeRate > 0)
		{
			list.add(IDataStorageUser.getBlankMedium(item));
			list.add(IDataStorageUser.getFullMedium(item));
		}
	}
	// TODO
	@Override
	@CheckForNull
	public NBTTagCompound getStoredData(ItemStack stack)
	{
		if (stack != null && stack.getItem() instanceof ItemStorageMedium)
		{
			if (stack.hasTagCompound() && !stack.stackTagCompound.getCompoundTag(GROUP_KEY).equals(BLANK))
				return stack.stackTagCompound.getCompoundTag(GROUP_KEY);
			else
				return null;
		}
		return null;
	}
}
