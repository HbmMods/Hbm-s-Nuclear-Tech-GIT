package com.hbm.items.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;

import org.lwjgl.input.Keyboard;

import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableSet;
import com.hbm.items.ItemEnumMultiSimple;
import com.hbm.items.ItemStorageEnums.EnumStorageMagneticNew;
import com.hbm.items.ItemStorageEnums.EnumStorageMagneticOld;
import com.hbm.items.ItemStorageEnums.EnumStorageOptical;
import com.hbm.items.ItemStorageEnums.IDataStorageItemEnum;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.Library;
import com.hbm.lib.Library.EnumHashAlgorithm;
import com.hbm.main.DeserializationException;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import api.hbm.computer.filesystem.FileBase;
import api.hbm.computer.filesystem.FileMetadata;
import api.hbm.computer.filesystem.IDataStorageItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
@Beta
public class ItemStorageMedium extends ItemEnumMultiSimple implements IDataStorageItem
{
	private Optional<Item> dropItem = Optional.empty();
	private static final EnumMap<EnumStorageItemType, Class<? extends Enum<? extends IDataStorageItemEnum>>> ENUM_MAP = new EnumMap<>(EnumStorageItemType.class);
	static
	{
		ENUM_MAP.put(EnumStorageItemType.MAGNETIC_BASIC, EnumStorageMagneticOld.class);
		ENUM_MAP.put(EnumStorageItemType.MAGNETIC_ENHANCED, EnumStorageMagneticNew.class);
		ENUM_MAP.put(EnumStorageItemType.OPTICAL, EnumStorageOptical.class);
	}
	public ItemStorageMedium(Class<? extends Enum<? extends IDataStorageItemEnum>> clazz)
	{
		super(clazz, true, true);
	}
	
	public enum EnumStorageItemType
	{
		MAGNETIC_BASIC,// FDDs & tape drives, tells it its very weak to magnets
		MAGNETIC_ENHANCED,// HDDs, somewhat weak to magnets
		OPTICAL,// CDs, DVDs, & BDs, immune to magnets
		FLASH,// SSDs & USBs, immune to magnets
		MAGNETIC_ADVANCED,// Super high capacity tape drives, very weak to magnets
		CRYSTALLINE,// Quite stable, immune to almost everything, overall very good albeit expensive, similar to optical
		SINGULARITY;// Endgame(?), immune to radiation
	}
	public enum EnumStorageItemTraits
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
	public enum ProsConsList
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
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool)
	{
		super.addInformation(itemStack, player, list, bool);
		final IDataStorageItemEnum storageData = IDataStorageItemEnum.getInstance(theEnum, itemStack);
		// Auto-generated
		final long written = getUsedCapacity(itemStack);
		
		String capacityPercent = BobMathUtil.getShortNumber((written * 100) / storageData.getMaxCapacity());
		list.add("Capacity: " + capacityPercent + '%');
		list.add(String.format("(%s/%sbyte)", BobMathUtil.getShortNumber(written), BobMathUtil.getShortNumber(storageData.getMaxCapacity())));
		list.add(String.format("Write rate: %sbyte/s", BobMathUtil.getShortNumber(storageData.getWriteSpeed())));
		list.add(String.format("Read rate: %sbyte/s", BobMathUtil.getShortNumber(storageData.getReadSpeed())));
		
		list.add("");
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			list.add(I18nUtil.resolveKey(HbmCollection.lshift, I18nUtil.resolveKey("desc.item.storage.proscons")));
		else
		{
			list.clear();
			// There's probably a better way of doing this, I'll figure it out eventually, but until then, this'll do
			// Turns out, you could sort lists of enums the entire time
			final ArrayList<ProsConsList> sortedList = new ArrayList<>(storageData.getProsCons());
			Collections.sort(sortedList);
			if (sortedList.isEmpty())
				sortedList.add(ProsConsList.NA);
			for (ProsConsList trait : sortedList)
				list.add(I18nUtil.resolveKey(trait.key));
		}
		if (!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			list.add("Press <LCTRL> to view stored file list");
		else
		{
			list.clear();
			for (String f : getFileList(itemStack))
				list.add(f);
		}
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return true;
	}
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return BobMathUtil.durabilityBarDisplay(getUsedCapacity(stack), getMaxCapacity(stack));
	}
	/**
	 * If not null, when dropped, the item will turn into the one specified to simulate "breaking"
	 * @param item - The broken form
	 * @return Itself
	 */
	public ItemStorageMedium setFragile(@Nonnull Item item)
	{
		this.dropItem = Optional.of(item);
		return this;
	}
	/**
	 * Sets the item to become "dirty" when in inventory after some time
	 * @return Itself
	 */
	// TODO
	public ItemStorageMedium isExposed()
	{
		return this;
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
	{
		if (entityItem != null && !entityItem.worldObj.isRemote && entityItem.onGround && dropItem.isPresent())
		{
			entityItem.setEntityItemStack(new ItemStack(dropItem.get()));
			return true;
		}		
		return false;
	}
		
	@Override
	public long getMaxCapacity(ItemStack stack)
	{
		return IDataStorageItemEnum.getInstance(theEnum, stack).getMaxCapacity();
	}

	@Override
	public long getReadSpeed(ItemStack stack)
	{
		return IDataStorageItemEnum.getInstance(theEnum, stack).getReadSpeed();
	}

	@Override
	public long getWriteSpeed(ItemStack stack)
	{
		return IDataStorageItemEnum.getInstance(theEnum, stack).getWriteSpeed();
	}

	@Override
	public boolean writeFile(FileBase file, FileMetadata metadata, ItemStack stack)
	{
		final IDataStorageItemEnum instance = IDataStorageItemEnum.getInstance(theEnum, stack);
		if (file.getSize() + getUsedCapacity(stack) > instance.getMaxCapacity())
			return false;
		else
		{
			if (!stack.hasTagCompound())
				stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setByteArray(PREFIX + metadata.filename, file.serialize());
			stack.stackTagCompound.setByteArray(M_PREFIX + metadata.filename, metadata.serialize());
			stack.stackTagCompound.setByteArray(PREFIX + metadata.filename + H_SUFFIX, Library.getHash(file.serialize(), EnumHashAlgorithm.MD5));
			final HashSet<String> list = new HashSet<String>(getFileList(stack));
			list.add(metadata.filename);
			stack.stackTagCompound.setByteArray(KEY_FILE_LIST, Library.compressStringCollection(list));
			IDataStorageItem.setUsedCapacity(stack, getUsedCapacity(stack) + file.getSize());
			return true;
		}
	}

	@Override
	public boolean deleteFile(String filename, ItemStack stack)
	{
		if (getFileList(stack).contains(filename))
		{
			final long size = readFile(filename, stack).getSize();
			IDataStorageItem.setUsedCapacity(stack, getUsedCapacity(stack) - size);
			stack.stackTagCompound.removeTag(PREFIX + filename);
			final HashSet<String> list = new HashSet<>(getFileList(stack));
			list.remove(filename);
			stack.stackTagCompound.setByteArray(KEY_FILE_LIST, Library.compressStringCollection(list));
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean overwriteFile(FileBase file, FileMetadata metadata, ItemStack stack)
	{
		final IDataStorageItemEnum instance = IDataStorageItemEnum.getInstance(theEnum, stack);
		if (getFileList(stack).contains(metadata.filename))
		{
			if (file.getSize() + getUsedCapacity(stack) > instance.getMaxCapacity())
				return false;
			else
			{
				writeFile(file, metadata, stack);
				return true;
			}
		}
		else
			return false;
	}

	@Override
	public FileBase readFile(String filename, ItemStack stack)
	{
		if (getFileList(stack).contains(PREFIX + filename))
			try
			{
				return FileBase.deserializeFromRegistry(getFileMetadata(filename, stack).fileType, stack.stackTagCompound.getByteArray(PREFIX + filename));
			} catch (DeserializationException e)
			{
				e.printStackTrace();
				return null;
			}
		else
			return null;
	}
	
	@Override
	public byte[] getFileHash(String filename, ItemStack stack)
	{
		if (getFileList(stack).contains(PREFIX + filename))
			return stack.stackTagCompound.getByteArray(PREFIX + filename + H_SUFFIX);
		else
			return FileBase.emptyBytes;
	}
	
	@Override
	public FileMetadata getFileMetadata(String filename, ItemStack stack)
	{
		if (getFileList(stack).contains(filename))
		{
			try
			{
				return FileMetadata.deserializer.deserialize(stack.stackTagCompound.getByteArray(M_PREFIX + filename));
			} catch (DeserializationException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		else
			return null;
	}

	@Override
	public Set<String> getFileList(ItemStack stack)
	{
		try
		{
			return ImmutableSet.copyOf(Library.decompressStringBytes(stack.getTagCompound().getByteArray(KEY_FILE_LIST)));
		} catch (DeserializationException e)
		{
			e.printStackTrace();
			return ImmutableSet.of();
		}
	}
}