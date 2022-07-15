package com.hbm.items;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.hbm.items.machine.ItemStorageMedium.ProsConsList;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EnumUtil;

import api.hbm.computer.filesystem.IDataStorageBase;
import net.minecraft.item.ItemStack;

public class ItemStorageEnums
{

	public interface IDataStorageItemEnum extends IDataStorageBase
	{
		public List<ProsConsList> getProsCons();

		public static IDataStorageItemEnum getInstance(Class<? extends Enum<?>> theEnum, ItemStack itemStack)
		{
			return EnumUtil.grabEnumSafely(theEnum, itemStack.getItemDamage());
		}
	}

	public enum EnumStorageOptical implements IDataStorageItemEnum
	{
		CD(650 * BobMathUtil.MB, 2 * BobMathUtil.MB, BobMathUtil.MB),
		DVD((long) (4.5 * BobMathUtil.GB), 6 * BobMathUtil.MB, 4 * BobMathUtil.MB),
		DVD_DL(8 * BobMathUtil.GB, 6 * BobMathUtil.MB, 4 * BobMathUtil.MB),
		BD25(25 * BobMathUtil.GB, 36 * BobMathUtil.MB, 45 * BobMathUtil.MB),
		BD50(50 * BobMathUtil.GB, 36 * BobMathUtil.MB, 45 * BobMathUtil.MB),
		BD100(100 * BobMathUtil.GB, 36 * BobMathUtil.MB, 45 * BobMathUtil.MB),
		FIVE_D(360 * BobMathUtil.GB, 15 * BobMathUtil.MB, 15 * BobMathUtil.MB);
		private final long maxCapacity;
		private final int readSpeed, writeSpeed;
		private EnumStorageOptical(long max, int read, int write)
		{
			maxCapacity = max;
			readSpeed = read;
			writeSpeed = write;
		}

		@Override
		public long getMaxCapacity()
		{
			return maxCapacity;
		}

		@Override
		public long getReadSpeed()
		{
			return readSpeed;
		}

		@Override
		public long getWriteSpeed()
		{
			return writeSpeed;
		}

		@Override
		public List<ProsConsList> getProsCons()
		{
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public enum EnumStorageMagneticNew implements IDataStorageItemEnum
	{
		HDD1(),
		HDD2();

		@Override
		public long getMaxCapacity()
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getReadSpeed()
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getWriteSpeed()
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public List<ProsConsList> getProsCons()
		{
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public enum EnumStorageMagneticOld implements IDataStorageItemEnum
	{
		R_TO_R(2 * BobMathUtil.GB, 300 * BobMathUtil.KB, 300 * BobMathUtil.KB),
		CASSETTE(2 * BobMathUtil.GB, 300 * BobMathUtil.KB, 300 * BobMathUtil.KB),
		
		FLOPPY((int) 1.44 * BobMathUtil.MB, 100 * BobMathUtil.KB, 15 * BobMathUtil.KB),
		ZIP100(100 * BobMathUtil.MB, 2 * BobMathUtil.MB, BobMathUtil.MB),
		ZIP250(250 * BobMathUtil.MB, 2 * BobMathUtil.MB, BobMathUtil.MB),
		ZIP750(750 * BobMathUtil.MB, 2 * BobMathUtil.MB, BobMathUtil.MB);
		
//		LTO(45 * BobMathUtil.TB, BobMathUtil.TB, BobMathUtil.TB);
		private final long maxCapacity;
		private final int readSpeed, writeSpeed;
		private EnumStorageMagneticOld(long max, int read, int write)
		{
			maxCapacity = max;
			readSpeed = read;
			writeSpeed = write;
		}
		
		@Override
		public long getMaxCapacity()
		{
			return maxCapacity;
		}

		@Override
		public long getReadSpeed()
		{
			return readSpeed;
		}

		@Override
		public long getWriteSpeed()
		{
			return writeSpeed;
		}

		@Override
		public List<ProsConsList> getProsCons()
		{
			return ImmutableList.of(ProsConsList.CHEAP_VERY, ProsConsList.FRAGILE, ProsConsList.LIFESPAN_GOOD, ProsConsList.SPEED_POOR, ProsConsList.MAGNETIC, ProsConsList.MAGNET);
		}
	}

}
