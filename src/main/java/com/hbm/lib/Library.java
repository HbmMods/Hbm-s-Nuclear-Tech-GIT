package com.hbm.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;
import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidDuct;
import com.hbm.interfaces.IFluidSource;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityProxyInventory;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyConnector;
import api.hbm.energy.IEnergyConnectorBlock;
import api.hbm.fluid.IFluidConnector;
import api.hbm.fluid.IFluidConnectorBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Spaghetti("this whole class")
public class Library {
	
	static Random rand = new Random();

	//this is a list of UUIDs used for various things, primarily for accessories.
	//for a comprehensive list, check RenderAccessoryUtility.java
	public static String HbMinecraft = "192af5d7-ed0f-48d8-bd89-9d41af8524f8";
	public static String LPkukin = "937c9804-e11f-4ad2-a5b1-42e62ac73077";
	public static String Dafnik = "3af1c262-61c0-4b12-a4cb-424cc3a9c8c0";
	public static String a20 = "4729b498-a81c-42fd-8acd-20d6d9f759e0";
	public static String LordVertice = "a41df45e-13d8-4677-9398-090d3882b74f";
	public static String CodeRed_ = "912ec334-e920-4dd7-8338-4d9b2d42e0a1";
	public static String dxmaster769 = "62c168b2-d11d-4dbf-9168-c6cea3dcb20e";
	public static String Dr_Nostalgia = "e82684a7-30f1-44d2-ab37-41b342be1bbd";
	public static String Samino2 = "87c3960a-4332-46a0-a929-ef2a488d1cda";
	public static String Hoboy03new = "d7f29d9c-5103-4f6f-88e1-2632ff95973f";
	public static String Dragon59MC = "dc23a304-0f84-4e2d-b47d-84c8d3bfbcdb";
	public static String Steelcourage = "ac49720b-4a9a-4459-a26f-bee92160287a";
	public static String ZippySqrl = "03c20435-a229-489a-a1a1-671b803f7017";
	public static String Schrabby = "3a4a1944-5154-4e67-b80a-b6561e8630b7";
	public static String SweatySwiggs = "5544aa30-b305-4362-b2c1-67349bb499d5";
	public static String Drillgon = "41ebd03f-7a12-42f3-b037-0caa4d6f235b";
	public static String Doctor17 = "e4ab1199-1c22-4f82-a516-c3238bc2d0d1";
	public static String Doctor17PH = "4d0477d7-58da-41a9-a945-e93df8601c5a";
	public static String ShimmeringBlaze = "061bc566-ec74-4307-9614-ac3a70d2ef38";
	public static String FifeMiner = "37e5eb63-b9a2-4735-9007-1c77d703daa3";
	public static String lag_add = "259785a0-20e9-4c63-9286-ac2f93ff528f";
	public static String Pu_238 = "c95fdfd3-bea7-4255-a44b-d21bc3df95e3";
	public static String Tankish = "609268ad-5b34-49c2-abba-a9d83229af03";
	public static String SolsticeUnlimitd = "f5574fd2-ec28-4927-9d11-3c0c731771f4";
	public static String FrizzleFrazzle = "fc4cc2ee-12e8-4097-b26a-1c6cb1b96531";
	public static String the_NCR = "28ae585f-4431-4491-9ce8-3def6126e3c6";
	public static String Barnaby99_x = "b04cf173-cff0-4acd-aa19-3d835224b43d";
	public static String Ma118 = "1121cb7a-8773-491f-8e2b-221290c93d81";
	public static String Adam29Adam29 = "bbae7bfa-0eba-40ac-a0dd-f3b715e73e61";
	public static String Alcater = "0b399a4a-8545-45a1-be3d-ece70d7d48e9";
	public static String ege444 = "42ee978c-442a-4cd8-95b6-29e469b6df10";

	public static Set<String> contributors = Sets.newHashSet(new String[] {
			"06ab7c03-55ce-43f8-9d3c-2850e3c652de", //mustang_rudolf
			"5bf069bc-5b46-4179-aafe-35c0a07dee8b", //JMF781
			});
	
	//the old list that allowed superuser mode for the ZOMG
	//currently unused
	public static List<String> superuser = new ArrayList<String>();
	
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
	public static boolean canConnect(IBlockAccess world, int x, int y, int z, ForgeDirection dir /* cable's connecting side */) {
		
		if(y > 255 || y < 0)
			return false;
		
		Block b = world.getBlock(x, y, z);
		
		if(b instanceof IEnergyConnectorBlock) {
			IEnergyConnectorBlock con = (IEnergyConnectorBlock) b;
			
			if(con.canConnect(world, x, y, z, dir.getOpposite() /* machine's connecting side */))
				return true;
		}
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof IEnergyConnector) {
			IEnergyConnector con = (IEnergyConnector) te;
			
			if(con.canConnect(dir.getOpposite() /* machine's connecting side */))
				return true;
		}
		
		return false;
	}

	/** dir is the direction along the fluid duct entering the block */
	public static boolean canConnectFluid(IBlockAccess world, int x, int y, int z, ForgeDirection dir /* duct's connecting side */, FluidType type) {
		
		if(y > 255 || y < 0)
			return false;
		
		Block b = world.getBlock(x, y, z);
		
		if(b instanceof IFluidConnectorBlock) {
			IFluidConnectorBlock con = (IFluidConnectorBlock) b;
			
			if(con.canConnect(type, world, x, y, z, dir.getOpposite() /* machine's connecting side */))
				return true;
		}
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof IFluidConnector) {
			IFluidConnector con = (IFluidConnector) te;
			
			if(con.canConnect(type, dir.getOpposite() /* machine's connecting side */))
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
				world.getBlock(x, y, z) == ModBlocks.fusion_hatch ||
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
	
	public static MovingObjectPosition rayTrace(EntityPlayer player, double length, float interpolation) {
		Vec3 vec3 = getPosition(interpolation, player);
		vec3.yCoord += player.eyeHeight;
		Vec3 vec31 = player.getLook(interpolation);
		Vec3 vec32 = vec3.addVector(vec31.xCoord * length, vec31.yCoord * length, vec31.zCoord * length);
		return player.worldObj.func_147447_a(vec3, vec32, false, false, true);
	}

	public static MovingObjectPosition rayTrace(EntityPlayer player, double length, float interpolation, boolean allowLiquids, boolean disallowNonCollidingBlocks, boolean mopOnMiss) {
		Vec3 vec3 = getPosition(interpolation, player);
		vec3.yCoord += player.eyeHeight;
		Vec3 vec31 = player.getLook(interpolation);
		Vec3 vec32 = vec3.addVector(vec31.xCoord * length, vec31.yCoord * length, vec31.zCoord * length);
		return player.worldObj.func_147447_a(vec3, vec32, allowLiquids, disallowNonCollidingBlocks, mopOnMiss);
	}
	
	public static Vec3 getPosition(float interpolation, EntityPlayer player) {
		if(interpolation == 1.0F) {
			return Vec3.createVectorHelper(player.posX, player.posY + (player.getEyeHeight() - player.getDefaultEyeHeight()), player.posZ);
		} else {
			double d0 = player.prevPosX + (player.posX - player.prevPosX) * interpolation;
			double d1 = player.prevPosY + (player.posY - player.prevPosY) * interpolation + (player.getEyeHeight() - player.getDefaultEyeHeight());
			double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * interpolation;
			return Vec3.createVectorHelper(d0, d1, d2);
		}
	}
	
	public static List<int[]> getBlockPosInPath(int x, int y, int z, int length, Vec3 vec0) {
		List<int[]> list = new ArrayList<int[]>();
		
		for(int i = 0; i <= length; i++) {
			list.add(new int[] { (int)(x + (vec0.xCoord * i)), y, (int)(z + (vec0.zCoord * i)), i });
		}
		
		return list;
	}
	
	//not great either but certainly better
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
			long toCharge = Math.min(Math.min(power, batRate), batMax - batCharge);
			
			power -= toCharge;
			
			battery.chargeBattery(slots[index], toCharge);
		}
		
		return power;
	}
	
	public static long chargeTEFromItems(ItemStack[] slots, int index, long power, long maxPower) {

		if(slots[index] != null && slots[index].getItem() == ModItems.battery_creative) {
			return maxPower;
		}

		if(slots[index] != null && slots[index].getItem() == ModItems.fusion_core_infinite) {
			return maxPower;
		}

		if(slots[index] != null && slots[index].getItem() instanceof IBatteryItem) {

			IBatteryItem battery = (IBatteryItem) slots[index].getItem();

			long batCharge = battery.getCharge(slots[index]);
			long batRate = battery.getDischargeRate();
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
	
	public static void transmitFluid(int x, int y, int z, boolean newTact, IFluidSource that, World worldObj, FluidType type) { }
	
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

	// Added for sake of doors
	// Original: Drillgon200: https://thebookofshaders.com/glossary/?search=smoothstep
	public static double smoothstep(double t, double edge0, double edge1){
		t = MathHelper.clamp_double((t - edge0) / (edge1 - edge0), 0.0, 1.0);
		return t * t * (3.0 - 2.0 * t);
	}
	public static float smoothstep(float t, float edge0, float edge1){
		t = MathHelper.clamp_float((t - edge0) / (edge1 - edge0), 0.0F, 1.0F);
		return t * t * (3.0F - 2.0F * t);
	}

	public static boolean isObstructed(World world, double x, double y, double z, double a, double b, double c) {
		MovingObjectPosition pos = world.rayTraceBlocks(Vec3.createVectorHelper(x, y, z), Vec3.createVectorHelper(a, b, c));
		return pos != null;
	}
	
	public static boolean isObstructedOpaque(World world, double x, double y, double z, double a, double b, double c) {
		MovingObjectPosition pos = world.func_147447_a(Vec3.createVectorHelper(x, y, z), Vec3.createVectorHelper(a, b, c), false, true, false);
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
