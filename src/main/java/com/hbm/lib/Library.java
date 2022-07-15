package com.hbm.lib;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.CheckForNull;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnegative;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.hbm.blocks.ModBlocks;
import com.hbm.calc.EasyLocation;
import com.hbm.calc.UnionOfTileEntitiesAndBooleansForFluids;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidDuct;
import com.hbm.interfaces.IFluidSource;
import com.hbm.interfaces.ILocationProvider;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.ModItems;
import com.hbm.main.DeserializationException;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyBase;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.conductor.*;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import com.hbm.util.TimeDurationType;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyConnector;
import api.hbm.energy.IEnergyConnectorBlock;
import api.hbm.fluid.IFluidConnector;
import api.hbm.fluid.IFluidConnectorBlock;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Spaghetti("this whole class")
public class Library
{
	private static final int[] primes = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283,	293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223};
	final static Random rand = new Random();

	//this is a list of UUIDs used for various things, primarily for accessories.
	//for a comprehensive list, check RenderAccessoryUtility.java
	public static final String HbMinecraft = "192af5d7-ed0f-48d8-bd89-9d41af8524f8";
	public static final String LPkukin = "937c9804-e11f-4ad2-a5b1-42e62ac73077";
	public static final String Dafnik = "3af1c262-61c0-4b12-a4cb-424cc3a9c8c0";
	public static final String a20 = "4729b498-a81c-42fd-8acd-20d6d9f759e0";
	public static final String LordVertice = "a41df45e-13d8-4677-9398-090d3882b74f";
	public static final String CodeRed_ = "912ec334-e920-4dd7-8338-4d9b2d42e0a1";
	public static final String dxmaster769 = "62c168b2-d11d-4dbf-9168-c6cea3dcb20e";
	public static final String Dr_Nostalgia = "e82684a7-30f1-44d2-ab37-41b342be1bbd";
	public static final String Samino2 = "87c3960a-4332-46a0-a929-ef2a488d1cda";
	public static final String Hoboy03new = "d7f29d9c-5103-4f6f-88e1-2632ff95973f";
	public static final String Dragon59MC = "dc23a304-0f84-4e2d-b47d-84c8d3bfbcdb";
	public static final String Steelcourage = "ac49720b-4a9a-4459-a26f-bee92160287a";
	public static final String ZippySqrl = "03c20435-a229-489a-a1a1-671b803f7017";
	public static final String Schrabby = "3a4a1944-5154-4e67-b80a-b6561e8630b7";
	public static final String SweatySwiggs = "5544aa30-b305-4362-b2c1-67349bb499d5";
	public static final String Drillgon = "41ebd03f-7a12-42f3-b037-0caa4d6f235b";
	public static final String Doctor17 = "e4ab1199-1c22-4f82-a516-c3238bc2d0d1";
	public static final String Doctor17PH = "4d0477d7-58da-41a9-a945-e93df8601c5a";
	public static final String ShimmeringBlaze = "061bc566-ec74-4307-9614-ac3a70d2ef38";
	public static final String FifeMiner = "37e5eb63-b9a2-4735-9007-1c77d703daa3";
	public static final String lag_add = "259785a0-20e9-4c63-9286-ac2f93ff528f";
	public static final String Pu_238 = "c95fdfd3-bea7-4255-a44b-d21bc3df95e3";
	public static final String Tankish = "609268ad-5b34-49c2-abba-a9d83229af03";
	public static final String FrizzleFrazzle = "fc4cc2ee-12e8-4097-b26a-1c6cb1b96531";
	public static final String the_NCR = "28ae585f-4431-4491-9ce8-3def6126e3c6";
	public static final String Barnaby99_x = "711aaf78-a862-4b7e-921a-216349716e9a";
	public static final String Ma118 = "1121cb7a-8773-491f-8e2b-221290c93d81";

	public static final Set<String> contributors = Sets.newHashSet(new String[] {
			"06ab7c03-55ce-43f8-9d3c-2850e3c652de", //mustang_rudolf
			"5bf069bc-5b46-4179-aafe-35c0a07dee8b", //JMF781
			});
	
	public static boolean doesUUIDMatch(EntityPlayer player, String ID)
	{
		return player.getUniqueID().toString().equals(ID);
	}
	/**
	 * Convert a real life time unit to Minecraft ticks
	 * @param time The amount of time
	 * @param type {@link#TimeDurationType}
	 * @param realYears Use 365 days per year instead of 100 days per year
	 * @return The calculated duration
	 */
	public static long getLifespan(float time, TimeDurationType type, boolean realYears)
	{
		final float life;
		switch (type)
		{
		case LONG: life = (48000 * (realYears ? 365 : 100) * 100) * time; break;
		case MEDIUM: life = (48000 * (realYears ? 365 : 100)) * time; break;
		case SHORT: life = 48000 * time; break;
		default: life = 0; break;
		}
		return (long) life;
	}
	
	public static void radiate(World worldIn, EasyLocation loc, float rads, double range, HazardType hazType, ContaminationType contType)
	{
		List<EntityLivingBase> entities = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(loc.posX + 0.5, loc.posY + 0.5, loc.posZ + 0.5, loc.posX + 0.5, loc.posY + 0.5, loc.posZ + 0.5).expand(range, range, range));
//		System.out.println(entities.isEmpty());
		for (EntityLivingBase e : entities)
		{
			
			Vec3 vec = Vec3.createVectorHelper(e.posX - (loc.posX + 0.5), (e.posY + e.getEyeHeight()) - (loc.posY + 0.5), e.posZ - (loc.posZ + 0.5));
			double len = vec.lengthVector();
			vec = vec.normalize();
			
			float res = 0;
			
			for (int i = 1; i < len; i++)
			{

				int ix = (int) Math.floor(loc.posX + 0.5 + vec.xCoord * i);
				int iy = (int) Math.floor(loc.posY + 0.5 + vec.yCoord * i);
				int iz = (int) Math.floor(loc.posZ + 0.5 + vec.zCoord * i);
				
				res += worldIn.getBlock(ix, iy, iz).getExplosionResistance(null);
			}
			
			if (res < 1)
				res = 1;
			
			float eRads = rads;
			eRads /= res;
			eRads /= (float) (len * len);
			
			ContaminationUtil.contaminate(e, hazType, contType, eRads);
		}
	}
	
	public static void easyPrintHook(ScaledResolution resolution, String title, String...text)
	{
		easyPrintHook(resolution, title, Arrays.asList(text));
	}
	
	public static void easyPrintHook(ScaledResolution resolution, String title, List<String> text)
	{
		Minecraft mc = Minecraft.getMinecraft();
		
		GL11.glPushMatrix();
		
		int pX = resolution.getScaledWidth() / 2 + 8;
		int pZ = resolution.getScaledHeight() / 2;
		
		mc.fontRenderer.drawString(title, pX + 1, pZ - 19, 0x006000);
		mc.fontRenderer.drawString(title, pX, pZ - 20, 0x00FF00);
		
		for (String s : text)
		{
			mc.fontRenderer.drawString(s, pX, pZ, 0xffffff);
			pZ += 10;
		}
		
		GL11.glDisable(GL11.GL_BLEND);

		GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
	
	public static byte[] boolToByte(boolean[] bools)
	{
		final byte[] bytes = new byte[bools.length];
		for (int i = 0; i < bools.length; i++)
			bytes[i] = (byte) (bools[i] ? 1 : 0);
		return bytes;
	}
	
	public static boolean[] byteToBool(byte[] bytes, boolean defaultVal)
	{
		final boolean[] bools = new boolean[bytes.length];
		for (int i = 0; i < bytes.length; i++)
			bools[i] = bytes[i] == 1 ? true : defaultVal;
		return bools;
	}
	
	public static byte[] compressStringCollection(Collection<String> strings)
	{
		final ByteBuf buf = Unpooled.buffer();
		buf.writeInt(strings.size());
		
		for (String s : strings)
		{
			buf.writeInt(s.length());
			buf.writeBytes(s.getBytes());
		}
		
		return buf.array();
	}
	
	public static Collection<String> decompressStringBytes(byte[] bytes) throws DeserializationException
	{
		try
		{
			final ByteBuf buf = Unpooled.copiedBuffer(bytes);
			final int size = buf.readInt();
			final ArrayList<String> strings = new ArrayList<String>(size);
			
			for (int i = 0; i < size; i++)
			{
				byte[] stringBytes = new byte[buf.readInt()];
				buf.writeBytes(stringBytes);
				strings.add(new String(stringBytes));
			}
			
			return strings;
		} catch (Exception e)
		{
			throw new DeserializationException(e);
		}
	}
	
	@CheckForNull
	public static NBTBase autoNBT(Object obj)
	{
		if (obj instanceof Long)
			return new NBTTagLong((long) obj);
		else if (obj instanceof Integer)
			return new NBTTagInt((int) obj);
		else if (obj instanceof Short)
			return new NBTTagShort((short) obj);
		else if (obj instanceof Byte)
			return new NBTTagByte((byte) obj);
		else if (obj instanceof Boolean)
			return new NBTTagByte((byte) (((boolean) obj) ? 1 : 0));
		else if (obj instanceof Double)
			return new NBTTagDouble((double) obj);
		else if (obj instanceof Float)
			return new NBTTagFloat((float) obj);
		else if (obj instanceof int[])
			return new NBTTagIntArray((int[]) obj);
		else if (obj instanceof byte[])
			return new NBTTagByteArray((byte[]) obj);
		else if (obj instanceof String)
			return new NBTTagString((String) obj);
		else if (obj instanceof NBTTagList)
			return (NBTTagList) obj;
		else if (obj instanceof NBTTagCompound)
			return (NBTTagCompound) obj;
		else
			return null;
	}
	
	/**
	 * Rounds a number to so many significant digits
	 * @param num The number to round
	 * @param digits Amount of digits
	 * @return The rounded double
	 */
	public static double roundDecimal(double num, @Nonnegative int digits)
	{
		if (digits < 0)
			throw new IllegalArgumentException("Attempted negative number in non-negative field! Attempted value: " + digits);
		
		return new BigDecimal(num).setScale(digits, RoundingMode.HALF_UP).doubleValue();
	}
	/**
	 * Round any number to so many significant figures
	 * @author Drillgon200
	 * @param num The number to round
	 * @param digits Amount of significant figures
	 * @return The rounded form of the number
	 */
	public static double roundNumber(double num, @Nonnegative int digits)
	{
		final double leftDigitCount = Math.ceil(Math.log10(Math.abs(num)));
		final double fac = Math.pow(10, digits - leftDigitCount);
		return Math.round(num * fac) / fac;
	}
	
	public static boolean getBlink()
	{
		return System.currentTimeMillis() % 1000 < 500;
	}
	@Deprecated
	public static String[] ticksToDate(long ticks)
	{
		final String[] dateOut = new String[3];
		long year = Math.floorDiv(ticks, HbmCollection.tickYear);
		byte day = (byte) Math.floorDiv(ticks - HbmCollection.tickYear * year, HbmCollection.tickDay);
		float time = ticks - ((HbmCollection.tickYear * year) + (HbmCollection.tickDay * day));
		time = (float) convertScale(time, 0, HbmCollection.tickDay, 0, 10F);
		dateOut[0] = String.valueOf(year);
		dateOut[1] = String.valueOf(day);
		dateOut[2] = String.valueOf(time);
		return dateOut;
	}
	
	/**
	 * Rescale a number from one range to another
	 * @param toScale - The integer to scale
	 * @param oldMin - The current minimum value
	 * @param oldMax - The current maximum value
	 * @param newMin - The desired minimum value
	 * @param newMax - The desired maximum value
	 * @return The scaled number
	 */
	public static double convertScale(double toScale, double oldMin, double oldMax, double newMin, double newMax)
	{
		double prevRange = oldMax - oldMin;
		double newRange = newMax - newMin;
		return (((toScale - oldMin) * newRange) / prevRange) + newMin;
	}

	public static String toPercentage(float amount, float total)
	{
		return NumberFormat.getPercentInstance().format(amount / total);
	}
	
	public static boolean doesArrayContain(Object[] array, Object objectCheck)
	{
		if (isArrayEmpty(array) && objectCheck == null)
			return true;
		else if (isArrayEmpty(array) && objectCheck != null || !isArrayEmpty(array) && objectCheck == null)
			return false;
		
		for (Object o : array)
			if (objectCheck.equals(o))
				return true;
		return false;
	}
	
	public static <T> T concatArrays(T array1, T array2)
	{
	    if (!array1.getClass().isArray() || !array2.getClass().isArray())
	        throw new IllegalArgumentException("Only arrays are accepted.");

	    final Class<?> compType1 = array1.getClass().getComponentType();
	    final Class<?> compType2 = array2.getClass().getComponentType();

	    if (!compType1.equals(compType2))
	        throw new IllegalArgumentException("Two arrays have different types.");

	    final int len1 = Array.getLength(array1), len2 = Array.getLength(array2);

	    final T result = (T) Array.newInstance(compType1, len1 + len2);

	    System.arraycopy(array1, 0, result, 0, len1);
	    System.arraycopy(array2, 0, result, len1, len2);

	    return result;
	}
	
	public static <T> T randomFromArray(T[] array)
	{
		return array[rand.nextInt(array.length)];
	}
	
	public static <T> T randomFromCollection(Collection<T> collection)
	{
		return Iterables.get(collection, rand.nextInt(collection.size()));
	}
	
	public static int randomPrime()
	{
		return primes[rand.nextInt(primes.length)];
	}
	
	//the old list that allowed superuser mode for the ZOMG
	//currently unused
	public static List<String> superuser = new ArrayList<>();
	
	public static boolean checkForHeld(EntityPlayer player, Item item) {
		
		if(player.getHeldItem() == null)
			return false;
		
		return player.getHeldItem().getItem() == item;
	}

	public static final ForgeDirection POS_X = ForgeDirection.EAST;
	public static final ForgeDirection NEG_X = ForgeDirection.WEST;
	public static final ForgeDirection POS_Y = ForgeDirection.UP;
	public static final ForgeDirection NEG_Y = ForgeDirection.DOWN;
	public static final ForgeDirection POS_Z = ForgeDirection.SOUTH;
	public static final ForgeDirection NEG_Z = ForgeDirection.NORTH;
	
	/*
	 * Is putting this into this trash can a good idea? No. Do I have a better idea? Not currently.
	 */
	public static boolean canConnect(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		
		if(y > 255 || y < 0)
			return false;
		
		Block b = world.getBlock(x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(b instanceof IEnergyConnectorBlock) {
			IEnergyConnectorBlock con = (IEnergyConnectorBlock) b;
			
			if(con.canConnect(world, x, y, z, dir))
				return true;
		}
		
		if(te instanceof IEnergyConnector) {
			IEnergyConnector con = (IEnergyConnector) te;
			
			if(con.canConnect(dir))
				return true;
		}
		
		return false;
	}

	public static boolean canConnectFluid(IBlockAccess world, int x, int y, int z, ForgeDirection dir, FluidType type) {
		
		if(y > 255 || y < 0)
			return false;
		
		Block b = world.getBlock(x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(b instanceof IFluidConnectorBlock) {
			IFluidConnectorBlock con = (IFluidConnectorBlock) b;
			
			if(con.canConnect(type, world, x, y, z, dir))
				return true;
		}
		
		if(te instanceof IFluidConnector) {
			IFluidConnector con = (IFluidConnector) te;
			
			if(con.canConnect(type, dir))
				return true;
		}
		
		return false;
	}
	
	public static boolean checkFluidConnectables(World world, int x, int y, int z, FluidType type)
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(tileentity != null && tileentity instanceof IFluidDuct && ((IFluidDuct)tileentity).getType() == type)
			return true;
		if((tileentity != null && (tileentity instanceof IFluidAcceptor || 
				tileentity instanceof IFluidSource)) || 
				world.getBlock(x, y, z) == ModBlocks.dummy_port_flare ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_fluidtank ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_refinery ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_pumpjack ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_turbofan ||
				world.getBlock(x, y, z) == ModBlocks.reactor_hatch ||
				world.getBlock(x, y, z) == ModBlocks.reactor_conductor ||
				world.getBlock(x, y, z) == ModBlocks.watz_hatch ||
				world.getBlock(x, y, z) == ModBlocks.fwatz_hatch ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_ams_limiter ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_ams_emitter ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_ams_base ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_compact_launcher ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_launch_table ||
				world.getBlock(x, y, z) == ModBlocks.rbmk_loader) {
			return true;
		}
		
		if(world.getBlock(x, y, z) == ModBlocks.machine_mining_laser && tileentity instanceof TileEntityProxyInventory)
			return true;
		
		return false;
	}
	
	public static boolean checkUnionListForFluids(List<UnionOfTileEntitiesAndBooleansForFluids> list, IFluidSource that) {
		
		for(UnionOfTileEntitiesAndBooleansForFluids union : list)
		{
			if(union.source == that)
			{
				return true;
			}
		}
		
		return false;
	}

	public static EntityLivingBase getClosestEntityForChopper(World world, double x, double y, double z, double radius) {
		double d4 = -1.0D;
		EntityLivingBase entityplayer = null;

		for (int i = 0; i < world.loadedEntityList.size(); ++i) {
			if (world.loadedEntityList.get(i) instanceof EntityLivingBase && !(world.loadedEntityList.get(i) instanceof EntityHunterChopper)) {
				EntityLivingBase entityplayer1 = (EntityLivingBase) world.loadedEntityList.get(i);

				if (entityplayer1.isEntityAlive() && !(entityplayer1 instanceof EntityPlayer && ((EntityPlayer)entityplayer1).capabilities.disableDamage)) {
					double d5 = entityplayer1.getDistanceSq(x, y, z);
					double d6 = radius;

					if (entityplayer1.isSneaking()) {
						d6 = radius * 0.800000011920929D;
					}

					if ((radius < 0.0D || d5 < d6 * d6) && (d4 == -1.0D || d5 < d4)) {
						d4 = d5;
						entityplayer = entityplayer1;
					}
				}
			}
		}

		return entityplayer;
	}

	public static EntityPlayer getClosestPlayerForSound(World world, double x, double y, double z, double radius) {
		double d4 = -1.0D;
		EntityPlayer entity = null;

		for (int i = 0; i < world.loadedEntityList.size(); ++i) {
				Entity entityplayer1 = (Entity)world.loadedEntityList.get(i);

				if (entityplayer1.isEntityAlive() && entityplayer1 instanceof EntityPlayer) {
					double d5 = entityplayer1.getDistanceSq(x, y, z);
					double d6 = radius;

					if ((radius < 0.0D || d5 < d6 * d6) && (d4 == -1.0D || d5 < d4)) {
						d4 = d5;
						entity = (EntityPlayer)entityplayer1;
					}
			}
		}

		return entity;
	}

	public static EntityHunterChopper getClosestChopperForSound(World world, double x, double y, double z, double radius) {
		double d4 = -1.0D;
		EntityHunterChopper entity = null;

		for (int i = 0; i < world.loadedEntityList.size(); ++i) {
				Entity entityplayer1 = (Entity)world.loadedEntityList.get(i);

				if (entityplayer1.isEntityAlive() && entityplayer1 instanceof EntityHunterChopper) {
					double d5 = entityplayer1.getDistanceSq(x, y, z);
					double d6 = radius;

					if ((radius < 0.0D || d5 < d6 * d6) && (d4 == -1.0D || d5 < d4)) {
						d4 = d5;
						entity = (EntityHunterChopper)entityplayer1;
					}
			}
		}

		return entity;
	}

	public static EntityChopperMine getClosestMineForSound(World world, double x, double y, double z, double radius) {
		double d4 = -1.0D;
		EntityChopperMine entity = null;

		for (int i = 0; i < world.loadedEntityList.size(); ++i) {
				Entity entityplayer1 = (Entity)world.loadedEntityList.get(i);

				if (entityplayer1.isEntityAlive() && entityplayer1 instanceof EntityChopperMine) {
					double d5 = entityplayer1.getDistanceSq(x, y, z);
					double d6 = radius;

					if ((radius < 0.0D || d5 < d6 * d6) && (d4 == -1.0D || d5 < d4)) {
						d4 = d5;
						entity = (EntityChopperMine)entityplayer1;
					}
			}
		}

		return entity;
	}
	
	public static TargetPoint easyTargetPoint(TileEntity te, int range)
	{
		return new TargetPoint(te.getWorldObj().provider.dimensionId, te.xCoord, te.yCoord, te.zCoord, range);
	}
	/** <u><i>Requires dim to be set</i></u> **/
	public static TargetPoint easyTargetPoint(ILocationProvider loc, int range)
	{
		return new TargetPoint(loc.getWorld().provider.dimensionId, loc.getX(), loc.getY(), loc.getZ(), range);
	}
	/**
	 * Like Arrays.fill(), but better(?)
	 * @param <T> The type of the array
	 * @param type Class of the type 
	 * @param fill Object to fill with
	 * @param size Size of the array
	 * @return The filled array
	 */
	public static <T> T[] filledArray(Class<T> type, T fill, @Nonnegative int size)
	{
		T[] array = (T[]) Array.newInstance(type, size);
		
		for (int i = 0; i < size; i++)
			array[i] = fill;
		
		return array;
	}
	/**
	 * Convert an array to a list with proper type
	 * @param <T> The type the list will be
	 * @param array The array input, will also specify type
	 * @return The array as a list, but with the proper type specified
	 */
	public static <T> List<T> arrayToList(T[] array)
	{
		ArrayList<T> listOut = new ArrayList<>(array.length);
		for (T obj : array)
			listOut.add(obj);
		return listOut;
	}
	
	public enum EnumHashAlgorithm
	{
		MD2("MD2"),
		MD5("MD5"),
		SHA1("SHA-1"),
		SHA224("SHA-224"),
		SHA256("SHA-256"),
		SHA384("SHA-384"),
		SHA512("SHA-512");
		final String name;
		private EnumHashAlgorithm(String name)
		{
			this.name = name;
		}
	}
	
	public static byte[] getHash(String input)
	{
		return getHash(input.getBytes(), EnumHashAlgorithm.SHA256);
	}
	
	public static byte[] getHash(byte[] input)
	{
		return getHash(input, EnumHashAlgorithm.SHA256);
	}
	
	@CheckReturnValue
	public static byte[] getHash(byte[] input, EnumHashAlgorithm algorithm)
	{
		try
		{
			return MessageDigest.getInstance(algorithm.name).digest(input);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			MainRegistry.logger.catching(e);
			return new byte[0];
		}
	}
	
	/** Same as {@code MessageDigest.isEqual()}
	 */
	public static boolean doHashesMatch(byte[] h1, byte[] h2)
	{
		return MessageDigest.isEqual(h1, h2);
	}
	
	public static String toHexString(byte[] bytes)
	{
		final StringBuilder builder = new StringBuilder(bytes.length);
		for (byte b : bytes)
			builder.append((0xFF & b) > 15 ? Integer.toHexString(0xFF & b) : '0' + Integer.toHexString(0xFF & b));
		return builder.toString();
	}
	
	public static String iterableToString(Iterable<Object> iterable)
	{
		final StringBuilder builder = new StringBuilder();
		builder.append('[');
		iterable.forEach(o -> builder.append(", " + String.valueOf(o)));
		builder.append(']');
		return builder.toString();
	}
	
	public static MovingObjectPosition rayTrace(EntityPlayer player, double length, float interpolation) {
        Vec3 vec3 = getPosition(interpolation, player);
        vec3.yCoord += player.eyeHeight;
        Vec3 vec31 = player.getLook(interpolation);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * length, vec31.yCoord * length, vec31.zCoord * length);
        return player.worldObj.func_147447_a(vec3, vec32, false, false, true);
	}
	
	public static MovingObjectPosition rayTrace(EntityPlayer player, double length, float interpolation, boolean liquids, boolean entity, boolean allowZeroLength) {
        Vec3 vec3 = getPosition(interpolation, player);
        vec3.yCoord += player.eyeHeight;
        Vec3 vec31 = player.getLook(interpolation);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * length, vec31.yCoord * length, vec31.zCoord * length);
        return player.worldObj.func_147447_a(vec3, vec32, liquids, entity, allowZeroLength);
	}
	
    public static Vec3 getPosition(float interpolation, EntityPlayer player) {
        if (interpolation == 1.0F)
        {
            return Vec3.createVectorHelper(player.posX, player.posY + (player.getEyeHeight() - player.getDefaultEyeHeight()), player.posZ);
        }
		double d0 = player.prevPosX + (player.posX - player.prevPosX) * interpolation;
		double d1 = player.prevPosY + (player.posY - player.prevPosY) * interpolation + (player.getEyeHeight() - player.getDefaultEyeHeight());
		double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * interpolation;
		return Vec3.createVectorHelper(d0, d1, d2);
    }
	
	public static List<int[]> getBlockPosInPath(int x, int y, int z, int length, Vec3 vec0) {
		List<int[]> list = new ArrayList<>();
		
		for(int i = 0; i <= length; i++) {
			list.add(new int[] { (int)(x + (vec0.xCoord * i)), y, (int)(z + (vec0.zCoord * i)), i });
		}
		
		return list;
	}
	
	//not great either but certainly better
	/**
	 * @param maxPower Unused 
	 */
	public static long chargeItemsFromTE(ItemStack[] slots, int index, long power, long maxPower) {
		
		if(power < 0)
			return 0;
		
		if(power > maxPower)
			return maxPower;

		if(slots[index] != null && slots[index].getItem() instanceof IBatteryItem) {
			
			IBatteryItem battery = (IBatteryItem) slots[index].getItem();

			long batMax = battery.getMaxCharge();
			long batCharge = battery.getCharge(slots[index]);
			long batRate = battery.getChargeRate();
			
			//in hHE
			long toCharge = Math.min(Math.min(power, batRate), batMax - batCharge);
			
			power -= toCharge;
			
			battery.chargeBattery(slots[index], toCharge);

			if(slots[index] != null && slots[index].getItem() == ModItems.dynosphere_desh && battery.getCharge(slots[index]) >= battery.getMaxCharge())
				slots[index] = new ItemStack(ModItems.dynosphere_desh_charged);
			if(slots[index] != null && slots[index].getItem() == ModItems.dynosphere_schrabidium && battery.getCharge(slots[index]) >= battery.getMaxCharge())
				slots[index] = new ItemStack(ModItems.dynosphere_schrabidium_charged);
			if(slots[index] != null && slots[index].getItem() == ModItems.dynosphere_euphemium && battery.getCharge(slots[index]) >= battery.getMaxCharge())
				slots[index] = new ItemStack(ModItems.dynosphere_euphemium_charged);
			if(slots[index] != null && slots[index].getItem() == ModItems.dynosphere_dineutronium && battery.getCharge(slots[index]) >= battery.getMaxCharge())
				slots[index] = new ItemStack(ModItems.dynosphere_dineutronium_charged);
		}
		
		return power;
	}
	public static long chargeTEFromItems(ItemStack[] slots, int index, long power, long maxPower) {
		
		if(slots[index] != null && slots[index].getItem() == ModItems.battery_creative)
		{
			return maxPower;
		}
		
		if(slots[index] != null && slots[index].getItem() == ModItems.fusion_core_infinite)
		{
			return maxPower;
		}
		
		if(slots[index] != null && slots[index].getItem() instanceof IBatteryItem) {
			
			IBatteryItem battery = (IBatteryItem) slots[index].getItem();

			long batCharge = battery.getCharge(slots[index]);
			long batRate = battery.getDischargeRate();
			
			//in hHe
			long toDischarge = Math.min(Math.min((maxPower - power), batRate), batCharge);
			
			battery.dischargeBattery(slots[index], toDischarge);
			power += toDischarge;
		}
		
		return power;
	}
	
	//Flut-Füll gesteuerter Energieübertragungsalgorithmus
	//Flood fill controlled energy transmission algorithm
	public static void ffgeua(int x, int y, int z, boolean newTact, Object that, World worldObj) {
		
		/*
		 * This here smoldering crater is all that remains from the old energy system.
		 * In loving memory, 2016-2021.
		 * You won't be missed.
		 */
	}
	
	public static void transmitFluid(int x, int y, int z, boolean newTact, IFluidSource that, World worldObj, FluidType type) {
		Block block = worldObj.getBlock(x, y, z);
		TileEntity tileentity = worldObj.getTileEntity(x, y, z);
		
		//Fluid Tank
		if(block == ModBlocks.dummy_port_fluidtank)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		//Refinery
		if(block == ModBlocks.dummy_port_refinery)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		//Gas Flare
		if(block == ModBlocks.dummy_port_flare)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		//Turbofan
		if(block == ModBlocks.dummy_port_turbofan)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		//Large Nuclear Reactor
		if(block == ModBlocks.reactor_hatch && worldObj.getBlock(x, y, z + 2) == ModBlocks.reactor_computer)
		{
			tileentity = worldObj.getTileEntity(x, y, z + 2);
		}
		if(block == ModBlocks.reactor_hatch && worldObj.getBlock(x, y, z - 2) == ModBlocks.reactor_computer)
		{
			tileentity = worldObj.getTileEntity(x, y, z - 2);
		}
		if(block == ModBlocks.reactor_hatch && worldObj.getBlock(x + 2, y, z) == ModBlocks.reactor_computer)
		{
			tileentity = worldObj.getTileEntity(x + 2, y, z);
		}
		if(block == ModBlocks.reactor_hatch && worldObj.getBlock(x - 2, y, z) == ModBlocks.reactor_computer)
		{
			tileentity = worldObj.getTileEntity(x - 2, y, z);
		}
		//FWatz Reactor
		if(block == ModBlocks.fwatz_hatch && worldObj.getBlock(x, y + 11, z + 9) == ModBlocks.fwatz_core)
		{
			tileentity = worldObj.getTileEntity(x, y + 11, z + 9);
		}
		if(block == ModBlocks.fwatz_hatch && worldObj.getBlock(x, y + 11, z - 9) == ModBlocks.fwatz_core)
		{
			tileentity = worldObj.getTileEntity(x, y + 11, z - 9);
		}
		if(block == ModBlocks.fwatz_hatch && worldObj.getBlock(x + 9, y + 11, z) == ModBlocks.fwatz_core)
		{
			tileentity = worldObj.getTileEntity(x + 9, y + 11, z);
		}
		if(block == ModBlocks.fwatz_hatch && worldObj.getBlock(x - 9, y + 11, z) == ModBlocks.fwatz_core)
		{
			tileentity = worldObj.getTileEntity(x - 9, y + 11, z);
		}
		//AMS Limiter
		if(block == ModBlocks.dummy_port_ams_limiter)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		//AMS Limiter
		if(block == ModBlocks.dummy_port_ams_emitter)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		//AMS Base
		if(block == ModBlocks.dummy_port_ams_base)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		//Launchers
		if(block == ModBlocks.dummy_port_compact_launcher || block == ModBlocks.dummy_port_launch_table)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		
		if(tileentity == that)
			tileentity = null;
		
		if(tileentity instanceof TileEntityProxyBase) {
			TileEntityProxyBase proxy = (TileEntityProxyBase) tileentity;
			
			if(proxy.getTE() == that)
				tileentity = null;
		}
		
		if(tileentity instanceof IFluidDuct)
		{
			if(tileentity instanceof TileEntityFluidDuctSimple && ((TileEntityFluidDuctSimple)tileentity).getType().name().equals(type.name()))
			{
				if(Library.checkUnionListForFluids(((TileEntityFluidDuctSimple)tileentity).uoteab, that))
				{
					for(int i = 0; i < ((TileEntityFluidDuctSimple)tileentity).uoteab.size(); i++)
					{
						if(((TileEntityFluidDuctSimple)tileentity).uoteab.get(i).source == that)
						{
							if(((TileEntityFluidDuctSimple)tileentity).uoteab.get(i).ticked != newTact)
							{
								((TileEntityFluidDuctSimple)tileentity).uoteab.get(i).ticked = newTact;
								transmitFluid(x, y + 1, z, that.getTact(), that, worldObj, type);
								transmitFluid(x, y - 1, z, that.getTact(), that, worldObj, type);
								transmitFluid(x - 1, y, z, that.getTact(), that, worldObj, type);
								transmitFluid(x + 1, y, z, that.getTact(), that, worldObj, type);
								transmitFluid(x, y, z - 1, that.getTact(), that, worldObj, type);
								transmitFluid(x, y, z + 1, that.getTact(), that, worldObj, type);
							}
						}
					}
				} else {
					((TileEntityFluidDuctSimple)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleansForFluids(that, newTact));
				}
			}
			if(tileentity instanceof TileEntityGasDuct && ((TileEntityGasDuct)tileentity).type.name().equals(type.name()))
			{
				if(Library.checkUnionListForFluids(((TileEntityGasDuct)tileentity).uoteab, that))
				{
					for(int i = 0; i < ((TileEntityGasDuct)tileentity).uoteab.size(); i++)
					{
						if(((TileEntityGasDuct)tileentity).uoteab.get(i).source == that)
						{
							if(((TileEntityGasDuct)tileentity).uoteab.get(i).ticked != newTact)
							{
								((TileEntityGasDuct)tileentity).uoteab.get(i).ticked = newTact;
								transmitFluid(x, y + 1, z, that.getTact(), that, worldObj, type);
								transmitFluid(x, y - 1, z, that.getTact(), that, worldObj, type);
								transmitFluid(x - 1, y, z, that.getTact(), that, worldObj, type);
								transmitFluid(x + 1, y, z, that.getTact(), that, worldObj, type);
								transmitFluid(x, y, z - 1, that.getTact(), that, worldObj, type);
								transmitFluid(x, y, z + 1, that.getTact(), that, worldObj, type);
							}
						}
					}
				} else {
					((TileEntityGasDuct)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleansForFluids(that, newTact));
				}
			}
			if(tileentity instanceof TileEntityOilDuct && ((TileEntityOilDuct)tileentity).type.name().equals(type.name()))
			{
				if(Library.checkUnionListForFluids(((TileEntityOilDuct)tileentity).uoteab, that))
				{
					for(int i = 0; i < ((TileEntityOilDuct)tileentity).uoteab.size(); i++)
					{
						if(((TileEntityOilDuct)tileentity).uoteab.get(i).source == that)
						{
							if(((TileEntityOilDuct)tileentity).uoteab.get(i).ticked != newTact)
							{
								((TileEntityOilDuct)tileentity).uoteab.get(i).ticked = newTact;
								transmitFluid(x, y + 1, z, that.getTact(), that, worldObj, type);
								transmitFluid(x, y - 1, z, that.getTact(), that, worldObj, type);
								transmitFluid(x - 1, y, z, that.getTact(), that, worldObj, type);
								transmitFluid(x + 1, y, z, that.getTact(), that, worldObj, type);
								transmitFluid(x, y, z - 1, that.getTact(), that, worldObj, type);
								transmitFluid(x, y, z + 1, that.getTact(), that, worldObj, type);
							}
						}
					}
				} else {
					((TileEntityOilDuct)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleansForFluids(that, newTact));
				}
			}
			if(tileentity instanceof TileEntityGasDuctSolid && ((TileEntityGasDuctSolid)tileentity).type.name().equals(type.name()))
			{
				if(Library.checkUnionListForFluids(((TileEntityGasDuctSolid)tileentity).uoteab, that))
				{
					for(int i = 0; i < ((TileEntityGasDuctSolid)tileentity).uoteab.size(); i++)
					{
						if(((TileEntityGasDuctSolid)tileentity).uoteab.get(i).source == that)
						{
							if(((TileEntityGasDuctSolid)tileentity).uoteab.get(i).ticked != newTact)
							{
								((TileEntityGasDuctSolid)tileentity).uoteab.get(i).ticked = newTact;
								transmitFluid(x, y + 1, z, that.getTact(), that, worldObj, type);
								transmitFluid(x, y - 1, z, that.getTact(), that, worldObj, type);
								transmitFluid(x - 1, y, z, that.getTact(), that, worldObj, type);
								transmitFluid(x + 1, y, z, that.getTact(), that, worldObj, type);
								transmitFluid(x, y, z - 1, that.getTact(), that, worldObj, type);
								transmitFluid(x, y, z + 1, that.getTact(), that, worldObj, type);
							}
						}
					}
				} else {
					((TileEntityGasDuctSolid)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleansForFluids(that, newTact));
				}
			}
			if(tileentity instanceof TileEntityOilDuctSolid && ((TileEntityOilDuctSolid)tileentity).type.name().equals(type.name()))
			{
				if(Library.checkUnionListForFluids(((TileEntityOilDuctSolid)tileentity).uoteab, that))
				{
					for(int i = 0; i < ((TileEntityOilDuctSolid)tileentity).uoteab.size(); i++)
					{
						if(((TileEntityOilDuctSolid)tileentity).uoteab.get(i).source == that)
						{
							if(((TileEntityOilDuctSolid)tileentity).uoteab.get(i).ticked != newTact)
							{
								((TileEntityOilDuctSolid)tileentity).uoteab.get(i).ticked = newTact;
								transmitFluid(x, y + 1, z, that.getTact(), that, worldObj, type);
								transmitFluid(x, y - 1, z, that.getTact(), that, worldObj, type);
								transmitFluid(x - 1, y, z, that.getTact(), that, worldObj, type);
								transmitFluid(x + 1, y, z, that.getTact(), that, worldObj, type);
								transmitFluid(x, y, z - 1, that.getTact(), that, worldObj, type);
								transmitFluid(x, y, z + 1, that.getTact(), that, worldObj, type);
							}
						}
					}
				} else {
					((TileEntityOilDuctSolid)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleansForFluids(that, newTact));
				}
			}
		}
		
		if(tileentity instanceof IFluidAcceptor && newTact && ((IFluidAcceptor)tileentity).getMaxFluidFillForReceive(type) > 0 &&
				((IFluidAcceptor)tileentity).getMaxFluidFillForReceive(type) - ((IFluidAcceptor)tileentity).getFluidFillForReceive(type) > 0) {
			that.getFluidList(type).add((IFluidAcceptor)tileentity);
		}
		
		if(!newTact) {
			int size = that.getFluidList(type).size();
			
			if(size > 0) {
				int part = that.getFluidFillForTransfer(type) / size;
				
				for(IFluidAcceptor consume : that.getFluidList(type)) {
					
					if(consume.getFluidFillForReceive(type) < consume.getMaxFluidFillForReceive(type)) {
						
						if(consume.getMaxFluidFillForReceive(type) - consume.getFluidFillForReceive(type) >= part) {
							that.transferFluid(part, type);
							consume.receiveFluid(part, type);
							
						} else {
							int transfer = consume.getMaxFluidFillForReceive(type) - consume.getFluidFillForReceive(type);
							that.transferFluid(transfer, type);
							consume.receiveFluid(transfer, type);
						}
					}
				}
			}
			that.clearFluidList(type);
		}
	}
	
	public static boolean isArrayEmpty(Object[] array) {
		if(array == null)
			return true;
		if(array.length == 0)
			return true;
		
		boolean flag = true;
		
		for(int i = 0; i < array.length; i++) {
			if(array[i] != null)
				flag = false;
		}
		
		return flag;
	}
	
	public static boolean isObstructed(World world, ILocationProvider loc1, ILocationProvider loc2)
	{
		return isObstructed(world, loc1.getX(), loc1.getY(), loc1.getZ(), loc2.getX(), loc2.getY(), loc2.getZ());
	}
	
	public static boolean isObstructed(World world, double x, double y, double z, double a, double b, double c) {
		
		MovingObjectPosition pos = world.rayTraceBlocks(Vec3.createVectorHelper(x, y, z), Vec3.createVectorHelper(a, b, c));
		
		return pos != null;
	}
	
	public static int getFirstNullIndex(int start, Object[] array) {
		for(int i = start; i < array.length; i++) {
			if(array[i] == null)
				return i;
		}
		return -1;
	}
	
	public static Block getRandomConcrete() {
		int i = rand.nextInt(20);

		if(i <= 1)
			return ModBlocks.brick_concrete_broken;
		if(i <= 4)
			return ModBlocks.brick_concrete_cracked;
		if(i <= 10)
			return ModBlocks.brick_concrete_mossy;
		
		return ModBlocks.brick_concrete;
	}
}
