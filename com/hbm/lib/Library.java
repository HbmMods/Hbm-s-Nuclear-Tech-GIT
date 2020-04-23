package com.hbm.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.calc.UnionOfTileEntitiesAndBooleansForFluids;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.handler.HazmatRegistry;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidDuct;
import com.hbm.interfaces.IFluidSource;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.potion.HbmPotion;
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
import com.hbm.tileentity.machine.TileEntityMachineFluidTank;
import com.hbm.tileentity.machine.TileEntityMachineTransformer;

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

public class Library {
	
	static Random rand = new Random();

	public static List<String> book1 = new ArrayList<String>();
	public static List<String> book2 = new ArrayList<String>();
	public static List<String> book3 = new ArrayList<String>();
	public static List<String> book4 = new ArrayList<String>();
	public static List<String> book5 = new ArrayList<String>();

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
	
	public static List<String> superuser = new ArrayList<String>();
	
	public static void initBooks() {
		
	}
	
	public static boolean checkArmor(EntityPlayer player, Item helmet, Item plate, Item legs, Item boots) {
		
		if(player.inventory.armorInventory[0] != null && 
				player.inventory.armorInventory[0].getItem() == boots && 
				player.inventory.armorInventory[1] != null && 
				player.inventory.armorInventory[1].getItem() == legs && 
				player.inventory.armorInventory[2] != null && 
				player.inventory.armorInventory[2].getItem() == plate && 
				player.inventory.armorInventory[3] != null && 
				player.inventory.armorInventory[3].getItem() == helmet)
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean checkArmorPiece(EntityPlayer player, Item armor, int slot)
	{
		if(player.inventory.armorInventory[slot] != null &&
				player.inventory.armorInventory[slot].getItem() == armor) 
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean checkArmorNull(EntityPlayer player, int slot)
	{
		if(player.inventory.armorInventory[slot] == null) 
		{
			return true;
		}
		
		return false;
	}
	
	public static void damageSuit(EntityPlayer player, int slot, int amount) {
		
		if(player.inventory.armorInventory[slot] == null)
			return;
		
		int j = player.inventory.armorInventory[slot].getItemDamage();
		player.inventory.armorInventory[slot].setItemDamage(j += amount);

		if(player.inventory.armorInventory[slot].getItemDamage() >= player.inventory.armorInventory[slot].getMaxDamage())
		{
			player.inventory.armorInventory[slot] = null;
		}
	}
	
	//radDura: Radiation duration in seconds
	//radLevel: Radiation level (0 = I)
	//maskDura: Radiation duration when wearing gasmask
	//maskLevel: Radiation level when wearing gasmask
	/*public static void applyRadiation(Entity e, int radDura, int radLevel, int maskDura, int maskLevel) {
		
		if(!(e instanceof EntityLivingBase))
			return;
		
		if(radDura == 0)
			return;
		
		EntityLivingBase entity = (EntityLivingBase)e;
		
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			
			if(checkForHazmat(player))
				return;
			
			if(checkForGasMask(player)) {
				
				if(maskDura == 0)
					return;
				
				entity.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, maskDura * 20, maskLevel));
				return;
			}
		}
		
		entity.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, radDura * 20, radLevel));
	}*/
	
	public static void applyRadData(Entity e, float f) {

		if(!(e instanceof EntityLivingBase))
			return;
		
		EntityLivingBase entity = (EntityLivingBase)e;
		
		if(entity.isPotionActive(HbmPotion.mutation))
			return;
		
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			
			float koeff = 5.0F;
			f *= (float) Math.pow(koeff, -HazmatRegistry.instance.getResistance(player));
		}

		float rad = e.getEntityData().getFloat("hfr_radiation");
		e.getEntityData().setFloat("hfr_radiation", rad + f);
	}
	
	public static void applyRadDirect(Entity e, float f) {

		if(!(e instanceof EntityLivingBase))
			return;
		
		if(((EntityLivingBase)e).isPotionActive(HbmPotion.mutation))
			return;
		
		float rad = e.getEntityData().getFloat("hfr_radiation");
		e.getEntityData().setFloat("hfr_radiation", rad + f);
	}
	
	public static boolean checkForHazmat(EntityPlayer player) {
		
		if(checkArmor(player, ModItems.hazmat_helmet, ModItems.hazmat_plate, ModItems.hazmat_legs, ModItems.hazmat_boots) || 
				checkArmor(player, ModItems.hazmat_helmet_red, ModItems.hazmat_plate_red, ModItems.hazmat_legs_red, ModItems.hazmat_boots_red) || 
				checkArmor(player, ModItems.hazmat_helmet_grey, ModItems.hazmat_plate_grey, ModItems.hazmat_legs_grey, ModItems.hazmat_boots_grey) || 
				checkArmor(player, ModItems.t45_helmet, ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots) || 
				checkArmor(player, ModItems.schrabidium_helmet, ModItems.schrabidium_plate, ModItems.schrabidium_legs, ModItems.schrabidium_boots) || 
				checkForHaz2(player)) {
			
			return true;
		}
		
		if(player.isPotionActive(HbmPotion.mutation))
			return true;
		
		return false;
	}
	
	public static boolean checkForHaz2(EntityPlayer player) {
		
		if(checkArmor(player, ModItems.hazmat_paa_helmet, ModItems.hazmat_paa_plate, ModItems.hazmat_paa_legs, ModItems.hazmat_paa_boots) || 
				checkArmor(player, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots))
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean checkForAsbestos(EntityPlayer player) {
		
		if(checkArmor(player, ModItems.asbestos_helmet, ModItems.asbestos_plate, ModItems.asbestos_legs, ModItems.asbestos_boots))
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean checkForFaraday(EntityPlayer player) {
		
		ItemStack[] armor = player.inventory.armorInventory;
		
		if(armor[0] == null || armor[1] == null || armor[2] == null || armor[3] == null) return false;
		
		if(isFaradayArmor(armor[0].getItem()) &&
				isFaradayArmor(armor[1].getItem()) &&
				isFaradayArmor(armor[2].getItem()) &&
				isFaradayArmor(armor[3].getItem()))
			return true;
		
		return false;
	}
	
	public static final String[] metals = new String[] {
			"chainmail",
			"iron",
			"silver",
			"gold",
			"platinum",
			"tin",
			"lead",
			"schrabidium",
			"euphemium",
			"steel",
			"titanium",
			"alloy",
			"copper",
			"bronze",
			"electrum",
			"t45",
			"hazmat", //also count because rubber is insulating
			"rubber"
	};
	
	public static boolean isFaradayArmor(Item item) {
		
		String name = item.getUnlocalizedName();
		
		for(String metal : metals) {
			
			if(name.toLowerCase().contains(metal))
				return true;
		}
		
		return false;
	}
	
	public static boolean checkForGasMask(EntityPlayer player) {

		if(checkArmorPiece(player, ModItems.hazmat_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.hazmat_helmet_red, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.hazmat_helmet_grey, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.hazmat_paa_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.gas_mask, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.gas_mask_m65, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.t45_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.schrabidium_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.euphemium_helmet, 3))
		{
			return true;
		}
		
		if(player.isPotionActive(HbmPotion.mutation))
			return true;
		
		return false;
	}
	
	public static boolean checkForHeld(EntityPlayer player, Item item) {
		
		if(player.getHeldItem() == null)
			return false;
		
		return player.getHeldItem().getItem() == item;
	}
	
	public static boolean checkForFiend(EntityPlayer player) {
		
		return checkArmorPiece(player, ModItems.jackt, 2) && checkForHeld(player, ModItems.shimmer_sledge);
	}
	
	public static boolean checkForFiend2(EntityPlayer player) {
		
		return checkArmorPiece(player, ModItems.jackt2, 2) && checkForHeld(player, ModItems.shimmer_axe);
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
				world.getBlock(x, y, z) == ModBlocks.dummy_port_launch_table)
		{
			return true;
		}
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
	
	//////  //////  //////  //////  //////  ////        //////  //////  //////
	//      //  //  //        //    //      //  //      //      //      //    
	////    //////  /////     //    ////    ////        ////    //  //  //  //
	//      //  //     //     //    //      //  //      //      //  //  //  //
	//////  //  //  /////     //    //////  //  //      //////  //////  //////

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

	public static Item getItemByCode(int i) {
		
		if(i == 1337)
			return ModItems.schrabidium_hammer;
		if(i == 234)
			return ModItems.euphemium_kit;
		if(i == 69)
			return ModItems.nuke_advanced_kit;
		if(i == 34)
			return ModItems.t45_kit;
		
		return null;
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
	
	public static MovingObjectPosition rayTrace(EntityPlayer player, double d, float f) {
        Vec3 vec3 = getPosition(f, player);
        vec3.yCoord += player.eyeHeight;
        Vec3 vec31 = player.getLook(f);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * d, vec31.yCoord * d, vec31.zCoord * d);
        return player.worldObj.func_147447_a(vec3, vec32, false, false, true);
	}
	
    public static Vec3 getPosition(float par1, EntityPlayer player) {
        if (par1 == 1.0F)
        {
            return Vec3.createVectorHelper(player.posX, player.posY + (player.getEyeHeight() - player.getDefaultEyeHeight()), player.posZ);
        }
        else
        {
            double d0 = player.prevPosX + (player.posX - player.prevPosX) * par1;
            double d1 = player.prevPosY + (player.posY - player.prevPosY) * par1 + (player.getEyeHeight() - player.getDefaultEyeHeight());
            double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * par1;
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
	
	public static long chargeItemsFromTE(ItemStack[] slots, int index, long power, long maxPower) {

		if(slots[index] != null && slots[index].getItem() instanceof ItemBattery) {
			
			long dR = ((ItemBattery)slots[index].getItem()).getChargeRate();

			while(dR >= 1000000000000L) {
				if(power - 100000000000000L >= 0 && ItemBattery.getCharge(slots[index]) < ((ItemBattery)slots[index].getItem()).getMaxCharge())
				{
					power -= 100000000000000L;
					dR -= 1000000000000L;
					((ItemBattery)slots[index].getItem()).chargeBattery(slots[index], 1000000000000L);
				} else break;
			}
			while(dR >= 1000000000) {
				if(power - 100000000000L >= 0 && ItemBattery.getCharge(slots[index]) < ((ItemBattery)slots[index].getItem()).getMaxCharge())
				{
					power -= 100000000000L;
					dR -= 1000000000;
					((ItemBattery)slots[index].getItem()).chargeBattery(slots[index], 1000000000);
				} else break;
			}
			while(dR >= 1000000) {
				if(power - 100000000 >= 0 && ItemBattery.getCharge(slots[index]) < ((ItemBattery)slots[index].getItem()).getMaxCharge())
				{
					power -= 100000000;
					dR -= 1000000;
					((ItemBattery)slots[index].getItem()).chargeBattery(slots[index], 1000000);
				} else break;
			}
			while(dR >= 1000) {
				if(power - 100000 >= 0 && ItemBattery.getCharge(slots[index]) < ((ItemBattery)slots[index].getItem()).getMaxCharge())
				{
					power -= 100000;
					dR -= 1000;
					((ItemBattery)slots[index].getItem()).chargeBattery(slots[index], 1000);
				} else break;
			}
			while(dR >= 1) {
				if(power - 100 >= 0 && ItemBattery.getCharge(slots[index]) < ((ItemBattery)slots[index].getItem()).getMaxCharge())
				{
					power -= 100;
					dR -= 1;
					((ItemBattery)slots[index].getItem()).chargeBattery(slots[index], 1);
				} else break;
			}

			if(slots[index] != null && slots[index].getItem() == ModItems.dynosphere_desh && ItemBattery.getCharge(slots[index]) >= ItemBattery.getMaxChargeStatic(slots[index]))
				slots[index] = new ItemStack(ModItems.dynosphere_desh_charged);
			if(slots[index] != null && slots[index].getItem() == ModItems.dynosphere_schrabidium && ItemBattery.getCharge(slots[index]) >= ItemBattery.getMaxChargeStatic(slots[index]))
				slots[index] = new ItemStack(ModItems.dynosphere_schrabidium_charged);
			if(slots[index] != null && slots[index].getItem() == ModItems.dynosphere_euphemium && ItemBattery.getCharge(slots[index]) >= ItemBattery.getMaxChargeStatic(slots[index]))
				slots[index] = new ItemStack(ModItems.dynosphere_euphemium_charged);
			if(slots[index] != null && slots[index].getItem() == ModItems.dynosphere_dineutronium && ItemBattery.getCharge(slots[index]) >= ItemBattery.getMaxChargeStatic(slots[index]))
				slots[index] = new ItemStack(ModItems.dynosphere_dineutronium_charged);
		}
		
		for(int i = 0; i < 50; i++)
			if(power - 10 >= 0 && slots[index] != null && slots[index].getItem() == ModItems.elec_sword && slots[index].getItemDamage() > 0)
			{
				power -= 10;
				slots[index].setItemDamage(slots[index].getItemDamage() - 1);
			} else break;
	
		for(int i = 0; i < 50; i++)
			if(power - 10 >= 0 && slots[index] != null && slots[index].getItem() == ModItems.elec_pickaxe && slots[index].getItemDamage() > 0)
			{
				power -= 10;
				slots[index].setItemDamage(slots[index].getItemDamage() - 1);
			} else break;
	
		for(int i = 0; i < 50; i++)
			if(power - 10 >= 0 && slots[index] != null && slots[index].getItem() == ModItems.elec_axe && slots[index].getItemDamage() > 0)
			{
				power -= 10;
				slots[index].setItemDamage(slots[index].getItemDamage() - 1);
			} else break;
	
		for(int i = 0; i < 50; i++)
			if(power - 10 >= 0 && slots[index] != null && slots[index].getItem() == ModItems.elec_shovel && slots[index].getItemDamage() > 0)
			{
				power -= 10;
				slots[index].setItemDamage(slots[index].getItemDamage() - 1);
			} else break;
		
		if(slots[index] != null && slots[index].getItem() instanceof ItemBattery) {
			ItemBattery.updateDamage(slots[index]);
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
		
		if(slots[index] != null && slots[index].getItem() instanceof ItemBattery) {
			ItemBattery.updateDamage(slots[index]);
		}
		
		if(slots[index] != null && slots[index].getItem() instanceof ItemBattery) {
			
			long dR = ((ItemBattery)slots[index].getItem()).getDischargeRate();

			while(dR >= 1000000000000L) {
				if(power + 100000000000000L <= maxPower && ItemBattery.getCharge(slots[index]) > 0)
				{
					power += 100000000000000L;
					dR -= 1000000000000L;
					((ItemBattery)slots[index].getItem()).dischargeBattery(slots[index], 1000000000000L);
				} else break;
			}
			while(dR >= 1000000000) {
				if(power + 100000000000L <= maxPower && ItemBattery.getCharge(slots[index]) > 0)
				{
					power += 100000000000L;
					dR -= 1000000000L;
					((ItemBattery)slots[index].getItem()).dischargeBattery(slots[index], 1000000000);
				} else break;
			}
			while(dR >= 1000000) {
				if(power + 100000000L <= maxPower && ItemBattery.getCharge(slots[index]) > 0)
				{
					power += 100000000L;
					dR -= 1000000;
					((ItemBattery)slots[index].getItem()).dischargeBattery(slots[index], 1000000);
				} else break;
			}
			while(dR >= 1000) {
				if(power + 100000L <= maxPower && ItemBattery.getCharge(slots[index]) > 0)
				{
					power += 100000L;
					dR -= 1000;
					((ItemBattery)slots[index].getItem()).dischargeBattery(slots[index], 1000);
				} else break;
			}
			while(dR >= 1) {
				if(power + 100L <= maxPower && ItemBattery.getCharge(slots[index]) > 0)
				{
					power += 100L;
					dR -= 1;
					((ItemBattery)slots[index].getItem()).dischargeBattery(slots[index], 1);
				} else break;
			}
		}
		
		return power;
	}
	
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
		
		if(tileentity instanceof IFluidAcceptor && newTact && !(tileentity instanceof TileEntityMachineFluidTank && ((TileEntityMachineFluidTank)tileentity).dna())
				&& ((IFluidAcceptor)tileentity).getMaxFluidFill(type) > 0 && ((IFluidAcceptor)tileentity).getMaxFluidFill(type) - ((IFluidAcceptor)tileentity).getFluidFill(type) > 0)
		{
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
