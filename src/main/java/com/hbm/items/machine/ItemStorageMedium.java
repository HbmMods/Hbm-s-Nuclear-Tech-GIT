package com.hbm.items.machine;

import java.util.ArrayList;
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
	
	private long readRate;
	private long writeRate;
	private long maxCapacity;
	private ArrayList<EnumStorageItemTraits> traits = new ArrayList<EnumStorageItemTraits>();
	private boolean exposed;
	private boolean aux;
	
	// Scaling
	public static final int KB = 1000;
	public static final int MB = 1000000;
	public static final int GB = 1000000000;
	public static final long TB = 100000000000L;
	public static final long PB = 100000000000000L;
	public static final long ZB = 100000000000000000L;
	
	private static String[] specialLore = new String[] {"Is silence better than the noise?", "An all penetrating gaze", "How dangerous do you think knowledge can be?", "Once lost, now found again, by " + EnumChatFormatting.BOLD + "you", "They called it the " + EnumChatFormatting.ITALIC + "\"Interloper\"", "The price you have to pay is more than mere computational power", "It's calling us, will we answer, or perish into nothingness?", "More than a simple blueprint for a Machine", "Can you hear It?", "They said it was impossible", "" + EnumChatFormatting.OBFUSCATED + "BackgammonProprietyThousand4444", "The design is mostly human, but with clear signs of some other influence. It's not Lunar either", "GATEWAY TO THE OTHER SIDE", "INTERCONNECTED", "ONE OF MANY", "SOMETHING WAS IN BETWEEN", "It was no accident or coincedence you can across this item on your journey", "" + EnumChatFormatting.BOLD + "31"};
	
	public ItemStorageMedium(long maxCapacity, long writeRate, long readRate, boolean aux)
	{
		this.maxCapacity = maxCapacity;
		this.writeRate = writeRate;
		this.readRate = readRate;
		this.aux = aux;
		this.maxStackSize = 1;
	}
	
	public static enum EnumStorageItemType
	{
		MAGNETIC_BASIC,// FDDs & tape drives
		MAGNETIC_ENHANCED,// HDDs
		OPTICAL,// CDs, DVDs, & BDs
		FLASH,// SSDs & USBs
		MAGNETIC_ADVANCED,// Super high capacity tape drives
		CRYSTALLINE,// Uhh
		SINGULARITY;// Endgame(?)
	}
	public static enum EnumStorageItemTraits
	{
		DIRTY,// Prone to errors, makes more of a hassle to fix, but easy to fix
		CORRUPTED_LIGHT,// Data is somewhat corrupted, can be fixed relatively easily
		CORRUPTED_HEAVY,// Data is very corrupted, requires more effort
		CORRUPTED_ABSOLUTE;// Corruption is irreversible, just re-format it
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
		// Autogenerated
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
			list.add("The disc has a curious label:");
			list.add("");
			// The fate of the world is now (literally) in your hands
			list.add(EnumChatFormatting.RED + specialLore[MainRegistry.polaroidID - 1]);
			list.add("...");
			list.add("It also appears to have a custom housing, it is permanently set to write-protect mode");
		}
		
		for (EnumStorageItemTraits trait : EnumStorageItemTraits.values())
		{
			if (itemStack.stackTagCompound.getBoolean(trait.toString()))
			{
				this.traits.add(trait);
			}
		}

		itemStack.stackTagCompound = new NBTTagCompound();

		if (itemStack.stackTagCompound.getInteger("traitCount") > 0)
		{
			for (EnumStorageItemTraits i : traits)
			{
				list.add(I18nUtil.resolveKey(getTraitTranslation(i)));
			}
		}
		if (this != ModItems.storage_magnetic_fdd_tainted)
		{
			list.add("");
			if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			{
				list.add(String.format("%s%sHold <%sLSHIFT%s%s%s> to view pros/cons list", EnumChatFormatting.DARK_GRAY, EnumChatFormatting.ITALIC, EnumChatFormatting.YELLOW, EnumChatFormatting.RESET, EnumChatFormatting.DARK_GRAY, EnumChatFormatting.ITALIC));
			}
			else
			{
				if (this == ModItems.storage_magnetic_r_to_r)
				{
					list.add(EnumChatFormatting.BLUE + "+ Excellent lifespan");
					list.add(EnumChatFormatting.BLUE + "+ Extremely cheap");
					list.add(EnumChatFormatting.YELLOW + "* Magnetic medium");
					list.add(EnumChatFormatting.RED + "- Fragile");
					list.add(EnumChatFormatting.RED + "- Pitiful r/w speed");
					list.add(EnumChatFormatting.RED + "- Easily wiped by magnets");
					list.add(EnumChatFormatting.RED + "- Exposed to the elements");
				}
				if (this == ModItems.storage_magnetic_cassette)
				{
					list.add(EnumChatFormatting.BLUE + "+ Excellent lifespan");
					list.add(EnumChatFormatting.BLUE + "+ Extremely cheap");
					list.add(EnumChatFormatting.YELLOW + "* Magnetic medium");
					list.add(EnumChatFormatting.RED + "- Pitiful r/w speed");
					list.add(EnumChatFormatting.RED + "- Easily wiped by magnets");
				}
				if (this == ModItems.storage_magnetic_fdd)
				{
					list.add(EnumChatFormatting.BLUE + "+ Excellent lifespan");
					list.add(EnumChatFormatting.BLUE + "+ Extremely cheap");
					list.add(EnumChatFormatting.BLUE + "+ Very compact");
					list.add(EnumChatFormatting.YELLOW + "* Magnetic medium");
					list.add(EnumChatFormatting.RED + "- Pitiful storage capacity");
					list.add(EnumChatFormatting.RED + "- Easily wiped by magnets");
				}
				if (this == ModItems.storage_hdd)
				{
					list.add(EnumChatFormatting.BLUE + "+ Very good capacity");
					list.add(EnumChatFormatting.BLUE + "+ Good lifespan");
					list.add(EnumChatFormatting.BLUE + "+ Decent r/w speed");
					list.add(EnumChatFormatting.YELLOW + "* Magnetic medium");
					list.add(EnumChatFormatting.YELLOW + "* Mechanical parts");
					list.add(EnumChatFormatting.RED + "- Fragile");
					list.add(EnumChatFormatting.RED + "- Vulnerable to magnets");
					list.add(EnumChatFormatting.RED + "- Prone to mechanical failure");
				}
				if (this == ModItems.storage_hdd_dead)
				{
					list.add("The mechanical parts are damaged, data cannot be read or written, it can be repaired however");
				}
			}
		}
	}
	
	public String getTraitTranslation(EnumStorageItemTraits trait)
	{
		switch (trait)
		{
		case DIRTY:
			return "trait.dirty";
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
	
	public ItemStorageMedium setFragile(Item item)
	{
		if (item != null)
		{
			this.dropItem = item;
		}
		return this;
	}
	
	public ItemStorageMedium isExposed(boolean bool)
	{
		this.exposed = bool;
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
	
	// Make dirty
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entity, int i, boolean bool)
	{
		if (this.exposed && !stack.stackTagCompound.getBoolean(EnumStorageItemTraits.DIRTY.name()))
		{
			Random rand = new Random();
			if (rand.nextInt(20000) == 1)
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setBoolean(EnumStorageItemTraits.DIRTY.toString(), true);
				stack.stackTagCompound.setInteger("traitCount", stack.stackTagCompound.getInteger("traitCount") + 1);
			}
		}
	}
	// Make clean
	public static ItemStack getClean(ItemStack stackIn)
	{
		if (stackIn.getItem() instanceof ItemStorageMedium)
		{
			stackIn.stackTagCompound = new NBTTagCompound();
			stackIn.stackTagCompound.setBoolean(EnumStorageItemTraits.DIRTY.toString(), false);
			stackIn.stackTagCompound.setInteger("traitCount", stackIn.stackTagCompound.getInteger("traitCount") - 1);
		}
		return stackIn;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		if (this == ModItems.storage_magnetic_fdd_tainted)
		{
			return true;
		}
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
