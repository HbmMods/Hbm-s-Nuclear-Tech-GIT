package com.hbm.blocks;

import com.hbm.blocks.BlockEnums.*;
import com.hbm.blocks.bomb.*;
import com.hbm.blocks.fluid.*;
import com.hbm.blocks.gas.*;
import com.hbm.blocks.generic.*;
import com.hbm.blocks.generic.BlockHazard.ExtDisplayEffect;
import com.hbm.blocks.generic.BlockMotherOfAllOres.ItemRandomOreBlock;
import com.hbm.blocks.machine.*;
import com.hbm.blocks.machine.pile.*;
import com.hbm.blocks.machine.rbmk.*;
import com.hbm.blocks.network.*;
import com.hbm.blocks.rail.*;
import com.hbm.blocks.siege.SiegeCircuit;
import com.hbm.blocks.siege.SiegeHole;
import com.hbm.blocks.siege.SiegeInternal;
import com.hbm.blocks.siege.SiegeShield;
import com.hbm.blocks.test.*;
import com.hbm.blocks.turret.*;
import com.hbm.items.block.*;
import com.hbm.items.bomb.ItemPrototypeBlock;
import com.hbm.items.special.ItemOreBlock;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.DoorDecl;
import com.hbm.tileentity.machine.storage.TileEntityFileCabinet;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModBlocks2 {
	
	public static void mainRegistry()
	{
		initializeBlock();
		registerBlock();
	}
	

	public static Block heater_huge_heatex;
	public static Block machine_stirling_cmb;

	public static Block machine_dineutronium_battery_ex;
	public static Block machine_ep_battery;
	public static Block barrel_antimatter_ex;

	public static Block silo_hatch_ex;
	public static Block ore_manganese;
	public static Block ore_sodium;

	public static Block rbmk_turbine;
	
	private static void initializeBlock() {
		heater_huge_heatex = new HeaterHugeHeatex().setBlockName("heater_huge_heatex").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");

		machine_stirling_cmb = new MachineStirling().setBlockName("machine_stirling_cmb").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_dineutronium_battery_ex = new MachineBattery(Material.iron, 100_000_000_000_000L).setBlockName("machine_dineutronium_battery_ex").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_ep_battery = new MachineBattery(Material.iron, 50_000_000_000_000_000L).setBlockName("machine_ep_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);

		rbmk_turbine = new RBMKTurbine(Material.iron).setBlockName("rbmk_turbine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk_turbine");

		barrel_antimatter_ex = new BlockFluidBarrel(Material.iron, 4096000).setBlockName("barrel_antimatter_ex").setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":barrel_antimatter");
		silo_hatch_ex = new BlockDoorGeneric(Material.iron, DoorDecl.SILO_HATCH).setBlockName("silo_hatch_ex").setHardness(10.0F).setResistance(30000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");

		ore_manganese = new BlockGeneric(Material.rock).setBlockName("ore_manganese").setCreativeTab(null).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ore_manganese");		
		ore_sodium = new BlockGeneric(Material.rock).setBlockName("ore_sodium").setCreativeTab(null).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ore_sodium");		

	}

	private static void registerBlock() {
		register(heater_huge_heatex);
		register(machine_stirling_cmb);
		register(machine_dineutronium_battery_ex);
		register(machine_ep_battery);
		register(rbmk_turbine);

		register(barrel_antimatter_ex);
		register(ore_manganese);
		register(ore_sodium);
		GameRegistry.registerBlock(silo_hatch_ex, silo_hatch_ex.getUnlocalizedName());
	}
	
	private static void register(Block b) {
		GameRegistry.registerBlock(b, ItemBlockBase.class, b.getUnlocalizedName());
	}
	
	private static void register(Block b, Class<? extends ItemBlock> clazz) {
		GameRegistry.registerBlock(b, clazz, b.getUnlocalizedName());
	}
	
	public static void addRemap(String unloc, Block block, int meta) {
		Block remap = new BlockRemap(block, meta).setBlockName(unloc);
		register(remap, ItemBlockRemap.class);
	}
}
