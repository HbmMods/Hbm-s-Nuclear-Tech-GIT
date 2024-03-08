package com.hbm.items;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.VersatileConfig;
import com.hbm.handler.BucketHandler;
import com.hbm.handler.ToolAbility;
import com.hbm.handler.ToolAbility.LuckAbility;
import com.hbm.handler.WeaponAbility;
import com.hbm.handler.guncfg.*;
import com.hbm.interfaces.ICustomWarhead.SaltedFuel.HalfLifeType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ItemAmmoEnums.*;
import com.hbm.items.ItemEnums.*;
import com.hbm.items.armor.*;
import com.hbm.items.armor.IArmorDisableModel.EnumPlayerPart;
import com.hbm.items.bomb.*;
import com.hbm.items.food.*;
import com.hbm.items.machine.*;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.items.machine.ItemPlateFuel.FunctionEnum;
import com.hbm.items.machine.ItemPWRFuel.EnumPWRFuel;
import com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc;
import com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc;
import com.hbm.items.machine.ItemRTGPelletDepleted.DepletedRTGMaterial;
import com.hbm.items.machine.ItemStamp.StampType;
import com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType;
import com.hbm.items.special.*;
import com.hbm.items.special.ItemPlasticScrap.ScrapType;
import com.hbm.items.tool.*;
import com.hbm.items.tool.ItemToolAbility.EnumToolType;
import com.hbm.items.weapon.*;
import com.hbm.items.weapon.ItemMissile.*;
import com.hbm.items.weapon.gununified.ItemEnergyGunBase;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.machine.rbmk.IRBMKFluxReceiver.NType;
import com.hbm.util.EnchantmentUtil;
import com.hbm.util.RTGUtil;

import api.hbm.block.IToolable.ToolType;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ModItems2 {
	
	public static void mainRegistry()
	{
		initializeItem();
		registerItem();
	}
	public static ItemRBMKRod rbmk_fuel_euph;

	public static ItemRBMKPellet rbmk_pellet_euph;
	public static ItemRBMKRod rbmk_fuel_dnt;

	public static ItemRBMKPellet rbmk_pellet_dnt;

	public static Item ingot_manganese;
	public static Item ingot_sodium;

	public static void initializeItem()
	{	
		rbmk_pellet_euph = (ItemRBMKPellet) new ItemRBMKPellet("euphemium").setUnlocalizedName("rbmk_pellet_euph").setTextureName(RefStrings.MODID+ ":rbmk_pellet_euph");
		rbmk_fuel_euph = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_euph)
				.setYield(1000000D)
				.setStats(1250, 10)
				.setFunction(EnumBurnFunc.QUADRATIC)
				.setHeat(0.1D)
				.setMeltingPoint(100000)
				.setUnlocalizedName("rbmk_fuel_euph").setTextureName(RefStrings.MODID + ":rbmk_fuel_euph");
		
		rbmk_pellet_dnt = (ItemRBMKPellet) new ItemRBMKPellet("dnt").setUnlocalizedName("rbmk_pellet_dnt").setTextureName(RefStrings.MODID+ ":rbmk_pellet_dnt");
		rbmk_fuel_dnt = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_dnt)
				.setYield(1000000D)
				.setStats(1500, 10)
				.setFunction(EnumBurnFunc.QUADRATIC)
				.setHeat(0.1D)
				.setMeltingPoint(100000)
				.setUnlocalizedName("rbmk_fuel_dnt").setTextureName(RefStrings.MODID + ":rbmk_fuel_dnt");
		ingot_manganese = new Item().setUnlocalizedName("ingot_manganese").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_manganese");
		ingot_sodium= new Item().setUnlocalizedName("ingot_sodium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_sodium");

		
	}
	
	private static void registerItem() {

		GameRegistry.registerItem(rbmk_fuel_dnt, rbmk_fuel_dnt.getUnlocalizedName());
		GameRegistry.registerItem(rbmk_pellet_dnt, rbmk_pellet_dnt.getUnlocalizedName());
		GameRegistry.registerItem(rbmk_fuel_euph, rbmk_fuel_euph.getUnlocalizedName());
		GameRegistry.registerItem(rbmk_pellet_euph, rbmk_pellet_euph.getUnlocalizedName());
		GameRegistry.registerItem(ingot_manganese, ingot_manganese.getUnlocalizedName());
		GameRegistry.registerItem(ingot_sodium, ingot_sodium.getUnlocalizedName());

	}
	
	public static void addRemap(String unloc, Item item, Enum sub) {
		addRemap(unloc, item, sub.ordinal());
	}

	public static void addRemap(String unloc, Item item, int meta) {
		Item remap = new ItemRemap(item, meta).setUnlocalizedName(unloc).setTextureName(RefStrings.MODID + ":plate_armor_titanium");
		GameRegistry.registerItem(remap, remap.getUnlocalizedName());
	}
}
