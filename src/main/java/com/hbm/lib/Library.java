package com.hbm.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;
import com.hbm.blocks.ModBlocks;
import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.calc.UnionOfTileEntitiesAndBooleansForFluids;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidDuct;
import com.hbm.interfaces.IFluidSource;
import com.hbm.interfaces.ISource;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.conductor.TileEntityCable;
import com.hbm.tileentity.conductor.TileEntityCableSwitch;
import com.hbm.tileentity.conductor.TileEntityFluidDuct;
import com.hbm.tileentity.conductor.TileEntityGasDuct;
import com.hbm.tileentity.conductor.TileEntityGasDuctSolid;
import com.hbm.tileentity.conductor.TileEntityOilDuct;
import com.hbm.tileentity.conductor.TileEntityOilDuctSolid;
import com.hbm.tileentity.conductor.TileEntityPylonRedWire;
import com.hbm.tileentity.conductor.TileEntityWireCoated;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityMachineBattery;
import com.hbm.tileentity.machine.TileEntityMachineTransformer;

import api.hbm.energy.IBatteryItem;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

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
	public static String GOD___TM = "57146e3f-16b5-4e9f-b0b8-139bec2ca2cb";
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
	
	public static boolean checkCableConnectables(World world, int x, int y, int z)
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if((tileentity != null && (tileentity instanceof IConductor ||
				tileentity instanceof IConsumer ||
				tileentity instanceof ISource)) ||
				world.getBlock(x, y, z) == ModBlocks.fusion_center ||
				world.getBlock(x, y, z) == ModBlocks.factory_titanium_conductor ||
				world.getBlock(x, y, z) == ModBlocks.factory_advanced_conductor ||
				world.getBlock(x, y, z) == ModBlocks.watz_conductor ||
				world.getBlock(x, y, z) == ModBlocks.fwatz_hatch ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_igenerator ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_cyclotron ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_well ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_flare ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_drill ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_assembler ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_chemplant ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_refinery ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_pumpjack ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_turbofan ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_ams_limiter ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_ams_emitter ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_ams_base ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_radgen ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_compact_launcher ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_launch_table)
		{
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
				world.getBlock(x, y, z) == ModBlocks.dummy_port_well ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_flare ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_chemplant ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_fluidtank ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_refinery ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_pumpjack ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_turbofan ||
				world.getBlock(x, y, z) == ModBlocks.reactor_hatch ||
				world.getBlock(x, y, z) == ModBlocks.reactor_conductor ||
				world.getBlock(x, y, z) == ModBlocks.fusion_hatch ||
				world.getBlock(x, y, z) == ModBlocks.watz_hatch ||
				world.getBlock(x, y, z) == ModBlocks.fwatz_hatch ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_ams_limiter ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_ams_emitter ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_ams_base ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_reactor_small ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_compact_launcher ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_launch_table) {
			return true;
		}
		
		if(world.getBlock(x, y, z) == ModBlocks.machine_mining_laser && tileentity instanceof TileEntityProxyInventory)
			return true;
		
		return false;
	}
	
	public static boolean checkUnionList(List<UnionOfTileEntitiesAndBooleans> list, ISource that) {
		
		for(UnionOfTileEntitiesAndBooleans union : list)
		{
			if(union.source == that)
			{
				return true;
			}
		}
		
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
        else
        {
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
	
	public static String getShortNumber(long l) {

		if(l >= Math.pow(10, 18)) {
			double res = l / Math.pow(10, 18);
			res = Math.round(res * 100.0) / 100.0;
			return res + "E";
		}
		if(l >= Math.pow(10, 15)) {
			double res = l / Math.pow(10, 15);
			res = Math.round(res * 100.0) / 100.0;
			return res + "P";
		}
		if(l >= Math.pow(10, 12)) {
			double res = l / Math.pow(10, 12);
			res = Math.round(res * 100.0) / 100.0;
			return res + "T";
		}
		if(l >= Math.pow(10, 9)) {
			double res = l / Math.pow(10, 9);
			res = Math.round(res * 100.0) / 100.0;
			return res + "G";
		}
		if(l >= Math.pow(10, 6)) {
			double res = l / Math.pow(10, 6);
			res = Math.round(res * 100.0) / 100.0;
			return res + "M";
		}
		if(l >= Math.pow(10, 3)) {
			double res = l / Math.pow(10, 3);
			res = Math.round(res * 100.0) / 100.0;
			return res + "k";
		}
		
		return Long.toString(l);
	}
	
	//not great either but certainly better
	public static long chargeItemsFromTE(ItemStack[] slots, int index, long power, long maxPower) {

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
	
	//TODO: jesus christ kill it
	//Flut-Füll gesteuerter Energieübertragungsalgorithmus
	//Flood fill controlled energy transmission algorithm
	public static void ffgeua(int x, int y, int z, boolean newTact, ISource that, World worldObj) {
		Block block = worldObj.getBlock(x, y, z);
		TileEntity tileentity = worldObj.getTileEntity(x, y, z);

		//Factories
		if(block == ModBlocks.factory_titanium_conductor && worldObj.getBlock(x, y + 1, z) == ModBlocks.factory_titanium_core)
		{
			tileentity = worldObj.getTileEntity(x, y + 1, z);
		}
		if(block == ModBlocks.factory_titanium_conductor && worldObj.getBlock(x, y - 1, z) == ModBlocks.factory_titanium_core)
		{
			tileentity = worldObj.getTileEntity(x, y - 1, z);
		}
		if(block == ModBlocks.factory_advanced_conductor && worldObj.getBlock(x, y + 1, z) == ModBlocks.factory_advanced_core)
		{
			tileentity = worldObj.getTileEntity(x, y + 1, z);
		}
		if(block == ModBlocks.factory_advanced_conductor && worldObj.getBlock(x, y - 1, z) == ModBlocks.factory_advanced_core)
		{
			tileentity = worldObj.getTileEntity(x, y - 1, z);
		}
		//Derrick
		if(block == ModBlocks.dummy_port_well && worldObj.getBlock(x + 1, y, z) == ModBlocks.machine_well)
		{
			tileentity = worldObj.getTileEntity(x + 1, y, z);
		}
		if(block == ModBlocks.dummy_port_well && worldObj.getBlock(x - 1, y, z) == ModBlocks.machine_well)
		{
			tileentity = worldObj.getTileEntity(x - 1, y, z);
		}
		if(block == ModBlocks.dummy_port_well && worldObj.getBlock(x, y, z + 1) == ModBlocks.machine_well)
		{
			tileentity = worldObj.getTileEntity(x, y, z + 1);
		}
		if(block == ModBlocks.dummy_port_well && worldObj.getBlock(x, y, z - 1) == ModBlocks.machine_well)
		{
			tileentity = worldObj.getTileEntity(x, y, z - 1);
		}
		//Mining Drill
		if(block == ModBlocks.dummy_port_drill && worldObj.getBlock(x + 1, y, z) == ModBlocks.machine_drill)
		{
			tileentity = worldObj.getTileEntity(x + 1, y, z);
		}
		if(block == ModBlocks.dummy_port_drill && worldObj.getBlock(x - 1, y, z) == ModBlocks.machine_drill)
		{
			tileentity = worldObj.getTileEntity(x - 1, y, z);
		}
		if(block == ModBlocks.dummy_port_drill && worldObj.getBlock(x, y, z + 1) == ModBlocks.machine_drill)
		{
			tileentity = worldObj.getTileEntity(x, y, z + 1);
		}
		if(block == ModBlocks.dummy_port_drill && worldObj.getBlock(x, y, z - 1) == ModBlocks.machine_drill)
		{
			tileentity = worldObj.getTileEntity(x, y, z - 1);
		}
		//Assembler
		if(block == ModBlocks.dummy_port_assembler)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		//Chemplant
		if(block == ModBlocks.dummy_port_chemplant)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		//Refinery
		if(block == ModBlocks.dummy_port_refinery)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		//Pumpjack
		if(block == ModBlocks.dummy_port_pumpjack)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		//AMS Limiter
		if(block == ModBlocks.dummy_port_ams_limiter)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		//AMS Emitter
		if(block == ModBlocks.dummy_port_ams_emitter)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		//Launchers
		if(block == ModBlocks.dummy_port_compact_launcher || block == ModBlocks.dummy_port_launch_table)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
		
		if(tileentity instanceof IConductor)
		{
			if(tileentity instanceof TileEntityCable)
			{
				if(Library.checkUnionList(((TileEntityCable)tileentity).uoteab, that))
				{
					for(int i = 0; i < ((TileEntityCable)tileentity).uoteab.size(); i++)
					{
						if(((TileEntityCable)tileentity).uoteab.get(i).source == that)
						{
							if(((TileEntityCable)tileentity).uoteab.get(i).ticked != newTact)
							{
								((TileEntityCable)tileentity).uoteab.get(i).ticked = newTact;
								that.ffgeua(x, y + 1, z, that.getTact());
								that.ffgeua(x, y - 1, z, that.getTact());
								that.ffgeua(x - 1, y, z, that.getTact());
								that.ffgeua(x + 1, y, z, that.getTact());
								that.ffgeua(x, y, z - 1, that.getTact());
								that.ffgeua(x, y, z + 1, that.getTact());
							}
						}
					}
				} else {
					((TileEntityCable)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleans(that, newTact));
				}
			}
			if(tileentity instanceof TileEntityWireCoated)
			{
				if(Library.checkUnionList(((TileEntityWireCoated)tileentity).uoteab, that))
				{
					for(int i = 0; i < ((TileEntityWireCoated)tileentity).uoteab.size(); i++)
					{
						if(((TileEntityWireCoated)tileentity).uoteab.get(i).source == that)
						{
							if(((TileEntityWireCoated)tileentity).uoteab.get(i).ticked != newTact)
							{
								((TileEntityWireCoated)tileentity).uoteab.get(i).ticked = newTact;
								that.ffgeua(x, y + 1, z, that.getTact());
								that.ffgeua(x, y - 1, z, that.getTact());
								that.ffgeua(x - 1, y, z, that.getTact());
								that.ffgeua(x + 1, y, z, that.getTact());
								that.ffgeua(x, y, z - 1, that.getTact());
								that.ffgeua(x, y, z + 1, that.getTact());
							}
						}
					}
				} else {
					((TileEntityWireCoated)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleans(that, newTact));
				}
			}
			if(tileentity instanceof TileEntityCableSwitch)
			{
				if(tileentity.getBlockMetadata() == 1) {
					if(Library.checkUnionList(((TileEntityCableSwitch)tileentity).uoteab, that))
					{
						for(int i = 0; i < ((TileEntityCableSwitch)tileentity).uoteab.size(); i++)
						{
							if(((TileEntityCableSwitch)tileentity).uoteab.get(i).source == that)
							{
								if(((TileEntityCableSwitch)tileentity).uoteab.get(i).ticked != newTact)
								{
									((TileEntityCableSwitch)tileentity).uoteab.get(i).ticked = newTact;
									that.ffgeua(x, y + 1, z, that.getTact());
									that.ffgeua(x, y - 1, z, that.getTact());
									that.ffgeua(x - 1, y, z, that.getTact());
									that.ffgeua(x + 1, y, z, that.getTact());
									that.ffgeua(x, y, z - 1, that.getTact());
									that.ffgeua(x, y, z + 1, that.getTact());
								}
							}
						}
					} else {
						((TileEntityCableSwitch)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleans(that, newTact));
					}
				} else {
					((TileEntityCableSwitch)tileentity).uoteab.clear();
				}
			}
			if(tileentity instanceof TileEntityPylonRedWire)
			{
				if(Library.checkUnionList(((TileEntityPylonRedWire)tileentity).uoteab, that))
				{
					for(int i = 0; i < ((TileEntityPylonRedWire)tileentity).uoteab.size(); i++)
					{
						if(((TileEntityPylonRedWire)tileentity).uoteab.get(i).source == that)
						{
							if(((TileEntityPylonRedWire)tileentity).uoteab.get(i).ticked != newTact)
							{
								((TileEntityPylonRedWire)tileentity).uoteab.get(i).ticked = newTact;
								for(int j = 0; j < ((TileEntityPylonRedWire)tileentity).connected.size(); j++) {
									TileEntityPylonRedWire pylon = ((TileEntityPylonRedWire)tileentity).connected.get(j);
									if(pylon != null) {
										that.ffgeua(pylon.xCoord + 1, pylon.yCoord, pylon.zCoord, that.getTact());
										that.ffgeua(pylon.xCoord - 1, pylon.yCoord, pylon.zCoord, that.getTact());
										that.ffgeua(pylon.xCoord, pylon.yCoord + 1, pylon.zCoord, that.getTact());
										that.ffgeua(pylon.xCoord, pylon.yCoord - 1, pylon.zCoord, that.getTact());
										that.ffgeua(pylon.xCoord, pylon.yCoord, pylon.zCoord + 1, that.getTact());
										that.ffgeua(pylon.xCoord, pylon.yCoord, pylon.zCoord - 1, that.getTact());
										
										that.ffgeua(pylon.xCoord, pylon.yCoord, pylon.zCoord, that.getTact());
									}
								}
							}
						}
					}
				} else {
					((TileEntityPylonRedWire)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleans(that, newTact));
				}
			}
		}
		
		//TE will not be added as consumer if:
		// -TE is the source (will not send to itself)
		// -TE is already full
		// -TE is a battery set to output only
		// -TE as well as source are transformers of the same frequency
		if(tileentity instanceof IConsumer && newTact && !(tileentity instanceof TileEntityMachineBattery && ((TileEntityMachineBattery)tileentity).conducts) &&
				tileentity != that && ((IConsumer)tileentity).getPower() < ((IConsumer)tileentity).getMaxPower() &&
				!(tileentity instanceof TileEntityMachineTransformer && that instanceof TileEntityMachineTransformer &&
						((TileEntityMachineTransformer)tileentity).delay == ((TileEntityMachineTransformer)that).delay))
		{
			that.getList().add((IConsumer)tileentity);
		}
		
		if(!newTact)
		{
			int size = that.getList().size();
			if(size > 0)
			{
				long part = that.getSPower() / size;
				for(IConsumer consume : that.getList())
				{
					if(consume.getPower() < consume.getMaxPower())
					{
						if(consume.getMaxPower() - consume.getPower() >= part)
						{
							that.setSPower(that.getSPower()-part);
							consume.setPower(consume.getPower() + part);
						} else {
							that.setSPower(that.getSPower() - (consume.getMaxPower() - consume.getPower()));
							consume.setPower(consume.getMaxPower());
						}
					}
				}
			}
			that.clearList();
		}
	}
	
	public static void transmitFluid(int x, int y, int z, boolean newTact, IFluidSource that, World worldObj, FluidType type) {
		Block block = worldObj.getBlock(x, y, z);
		TileEntity tileentity = worldObj.getTileEntity(x, y, z);
		
		//Chemplant
		if(block == ModBlocks.dummy_port_chemplant)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetX, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetY, ((TileEntityDummy)worldObj.getTileEntity(x, y, z)).targetZ);
		}
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
		//Large Fusion Reactor
		if(block == ModBlocks.fusion_hatch && worldObj.getBlock(x, y, z + 8) == ModBlocks.fusion_core)
		{
			tileentity = worldObj.getTileEntity(x, y, z + 8);
		}
		if(block == ModBlocks.fusion_hatch && worldObj.getBlock(x, y, z - 8) == ModBlocks.fusion_core)
		{
			tileentity = worldObj.getTileEntity(x, y, z - 8);
		}
		if(block == ModBlocks.fusion_hatch && worldObj.getBlock(x + 8, y, z) == ModBlocks.fusion_core)
		{
			tileentity = worldObj.getTileEntity(x + 8, y, z);
		}
		if(block == ModBlocks.fusion_hatch && worldObj.getBlock(x - 8, y, z) == ModBlocks.fusion_core)
		{
			tileentity = worldObj.getTileEntity(x - 8, y, z);
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
		//Small Nuclear Reactor
		if(block == ModBlocks.dummy_port_reactor_small)
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
		
		if(tileentity instanceof IFluidDuct)
		{
			if(tileentity instanceof TileEntityFluidDuct && ((TileEntityFluidDuct)tileentity).type.name().equals(type.name()))
			{
				if(Library.checkUnionListForFluids(((TileEntityFluidDuct)tileentity).uoteab, that))
				{
					for(int i = 0; i < ((TileEntityFluidDuct)tileentity).uoteab.size(); i++)
					{
						if(((TileEntityFluidDuct)tileentity).uoteab.get(i).source == that)
						{
							if(((TileEntityFluidDuct)tileentity).uoteab.get(i).ticked != newTact)
							{
								((TileEntityFluidDuct)tileentity).uoteab.get(i).ticked = newTact;
								that.fillFluid(x, y + 1, z, that.getTact(), type);
								that.fillFluid(x, y - 1, z, that.getTact(), type);
								that.fillFluid(x - 1, y, z, that.getTact(), type);
								that.fillFluid(x + 1, y, z, that.getTact(), type);
								that.fillFluid(x, y, z - 1, that.getTact(), type);
								that.fillFluid(x, y, z + 1, that.getTact(), type);
							}
						}
					}
				} else {
					((TileEntityFluidDuct)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleansForFluids(that, newTact));
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
								that.fillFluid(x, y + 1, z, that.getTact(), type);
								that.fillFluid(x, y - 1, z, that.getTact(), type);
								that.fillFluid(x - 1, y, z, that.getTact(), type);
								that.fillFluid(x + 1, y, z, that.getTact(), type);
								that.fillFluid(x, y, z - 1, that.getTact(), type);
								that.fillFluid(x, y, z + 1, that.getTact(), type);
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
								that.fillFluid(x, y + 1, z, that.getTact(), type);
								that.fillFluid(x, y - 1, z, that.getTact(), type);
								that.fillFluid(x - 1, y, z, that.getTact(), type);
								that.fillFluid(x + 1, y, z, that.getTact(), type);
								that.fillFluid(x, y, z - 1, that.getTact(), type);
								that.fillFluid(x, y, z + 1, that.getTact(), type);
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
								that.fillFluid(x, y + 1, z, that.getTact(), type);
								that.fillFluid(x, y - 1, z, that.getTact(), type);
								that.fillFluid(x - 1, y, z, that.getTact(), type);
								that.fillFluid(x + 1, y, z, that.getTact(), type);
								that.fillFluid(x, y, z - 1, that.getTact(), type);
								that.fillFluid(x, y, z + 1, that.getTact(), type);
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
								that.fillFluid(x, y + 1, z, that.getTact(), type);
								that.fillFluid(x, y - 1, z, that.getTact(), type);
								that.fillFluid(x - 1, y, z, that.getTact(), type);
								that.fillFluid(x + 1, y, z, that.getTact(), type);
								that.fillFluid(x, y, z - 1, that.getTact(), type);
								that.fillFluid(x, y, z + 1, that.getTact(), type);
							}
						}
					}
				} else {
					((TileEntityOilDuctSolid)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleansForFluids(that, newTact));
				}
			}
		}
		
		if(tileentity instanceof IFluidAcceptor && newTact && ((IFluidAcceptor)tileentity).getMaxFluidFill(type) > 0 &&
				((IFluidAcceptor)tileentity).getMaxFluidFill(type) - ((IFluidAcceptor)tileentity).getFluidFill(type) > 0) {
			that.getFluidList(type).add((IFluidAcceptor)tileentity);
		}
		
		if(!newTact)
		{
			int size = that.getFluidList(type).size();
			if(size > 0)
			{
				int part = that.getFluidFill(type) / size;
				for(IFluidAcceptor consume : that.getFluidList(type))
				{
					if(consume.getFluidFill(type) < consume.getMaxFluidFill(type))
					{
						if(consume.getMaxFluidFill(type) - consume.getFluidFill(type) >= part)
						{
							that.setFluidFill(that.getFluidFill(type)-part, type);
							consume.setFluidFill(consume.getFluidFill(type) + part, type);
						} else {
							that.setFluidFill(that.getFluidFill(type) - (consume.getMaxFluidFill(type) - consume.getFluidFill(type)), type);
							consume.setFluidFill(consume.getMaxFluidFill(type), type);
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
	
	public static ItemStack carefulCopy(ItemStack stack) {
		if(stack == null)
			return null;
		else
			return stack.copy();
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
