package com.hbm.lib;

import java.util.ArrayList;
import java.util.List;
import com.hbm.blocks.ModBlocks;
import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.calc.UnionOfTileEntitiesAndBooleansForFluids;
import com.hbm.calc.UnionOfTileEntitiesAndBooleansForGas;
import com.hbm.calc.UnionOfTileEntitiesAndBooleansForOil;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidDuct;
import com.hbm.interfaces.IFluidSource;
import com.hbm.interfaces.IGasAcceptor;
import com.hbm.interfaces.IGasDuct;
import com.hbm.interfaces.IGasSource;
import com.hbm.interfaces.IOilDuct;
import com.hbm.interfaces.IOilAcceptor;
import com.hbm.interfaces.IOilSource;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.conductor.TileEntityCable;
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
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Library {

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
	
	public static List<String> superuser = new ArrayList<String>();
	
	public static void initBooks() {

		book1.add("This book contains detailed information about the resources in Hbm's Nuclear Tech Mod. Some can be found underground, some have to be processed in special machines before becoming useful.");
		book1.add("Uranium\nRare metal which spawns below Y:25. Impure uranium which is not useful by itself. Can be crafted into uranium hexafluoride, which can be seperated by the centrifuge.");
		book1.add("Uranium 235\nRare uranium isotope obtained from processing uranium hexafluoride in the centrifuge. This isotope is fissile, it can be used for reactor fuel and bombs. Np237 can be bred from U235.");
		book1.add("Uranium 238\nVery abundant isotope which is not as useful as it's fissile counterpart. Used in reactor fuel and for breeding fissile Pu239.");
		book1.add("Plutonium\nTransuranic element which cannot be found underground. It can be found in the nether (config option required) or bred from impure uranium.");
		book1.add("Plutonium 238\nAlpha emitter that can be bred from Np237, useful for making RTG pellets which is needed for the RTG furnace.");
		book1.add("Plutonium 239\nFissile plutonium isotope that can be crafted into reactor fuel. Implosion-type nukes also need this isotope.");
		book1.add("Plutonium 240\nIsotope with a high spontaneous fission rate. Not very useful.");
		book1.add("Neptunium\nNeptunium 237 can only be obtained by breeding U235. Other than being crucial for Pu238 production, it doesn't have many uses.");
		book1.add("Titanium\nTough metal which can be found underground below Y:35. Titanium is important for crafting machines and stong tools.");
		book1.add("Copper\nMetal found below Y:50. Very important metal for machines, mostly used for alloys.");
		book1.add("Minecraft Grade Copper\nMinecraft Grade Copper (or red copper) is used for all machines that use electricity. Every conductor is made from red copper.");
		book1.add("Advanced Alloy\nAlloy made from red copper and steel. Very tough, used for super conductors, late game machines and heavy duty tools.");
		book1.add("Tungsten\nFound below Y:35 or in the nether, needed for heating elements, neutron reflectors and late game alloys.");
		book1.add("Aluminium\nSoft metal found below Y:45. Can be used to craft shells and canisters.");
		book1.add("Steel\nAlloy made from coal and iron. Almost everything needs steel, shells, frames, machines, tools, etc.");
		book1.add("Lead\n.Found below Y:35. Used for nuclear reactors, hazmat equpiment and bullets.");
		book1.add("Beryllium\n.Brittle metal found below Y:35, used for deco elements and atom bombs.");
		book1.add("Schrabidium\n.Weird. Can be created using the schrabidium transmutation device or by setting off nukes near uranium ore. Can be crafted into extremely powerful equipment and machines.");
		book1.add("Magnetized Tungsten\nTungsten with traces of schrabidium. Strong super conductor which withstands very high temparatures. Used for 4000K magnets and calculation matrices.");
		book1.add("Lithium\nCan be extracted from quartz using the centrifuge, used for breeding tritium.");
		book1.add("Sulfur\nDust found underground below Y:35. Useful for matchsticks, deuterium production and crafting gunpowder.");
		book1.add("Niter\nDust found below Y:35. Can be used to craft gunpowder and det cord.");
		book1.add("Fluorite\nCan be found below Y:40. Only used for crafting uranium and plutonium hexafluoride.");
		book1.add("Uranium Hexafluoride\nCan be seperated in the centrifuge. Storable in the UF6 tank.");
		book1.add("Plutonium Hexafluoride\nCan be seperated in the centrifuge. Storable in the PuF6 tank.");

		book2.add("This book contains detailed information about the machines in Hbm's Nuclear Tech Mod. Most machines use electricity in the form of HE.");
		book2.add("Alloy Furnace\nType: Processor\nUses fuels like redstone, coal and netherrack to combine two items. Accepts RTG pellets.");
		book2.add("Centrifuge\nType: Processor\nUses the same fuels as the alloy furnace to seperate an imput to up to four outputs. Used for uranium enrichment.");
		book2.add("Hexafluoride Tanks\nType: Storage\nComes in two different variations, for uranium and plutonium.");
		book2.add("Breeding Reactor\nType: Processor\nUses nuclear rods as fuel to breed other rods. Used to process isotopes.");
		book2.add("Nuclear Furnace\nType: Processor\nUses nuclear rods as fuel to smelt items like a normal furnace does, but much faster.");
		book2.add("RTG-Furnace\nType: Processor\nUtilizes three plutonium 238 pellets which never run out to smelt items very fast.");
		book2.add("Coal Generator\nType: Generator\nUses coal and water in order to create little amounts of HE.");
		book2.add("Diesel Generator\nType: Generator\nUses diesel fuel to create HE, does not need water in order to run.");
		book2.add("Small Nuclear Reactor\nType: Generator\nUses reactor fuel, water and coolant to generate HE, the reactor explodes when it overheats.");
		book2.add("Red Copper Cable\nType: Conductor\nConnects to other cables and machines, transfers HE.");
		book2.add("Coated Red Copper Cable\nType: Conductor\nFull block conductors which are more resistant than normal cables.");
		book2.add("Energy Storage Block\nType: Storage\nStores energy when connected to a power source, releases it if it's powered with a redstone signal.");
		book2.add("HE to RF Converter\nType: Power Converter\nCollects HE and converts it to RF, which can be used by machines from other mods which are RF compatible.");
		book2.add("RF to HE Converter\nType: Power Converter\nCollects RF and converts it to HE.");
		book2.add("Electric Furnace\nType: Processor\nNeeds HE in order to smelt items much faster than a normal furnace.");
		book2.add("Shredder\nType: Processor\nNeeds HE and two blades in order to run. Ingots and ores are turned into dust, other blocks get processed into scrap. Useful to make fuel out of junk.");
		book2.add("Deuterium Extractor\nType: Processor\nNeeds HE, water and sulfur in order to extract deuterium out of the water. Sulfur is used up slower than water.");
		book2.add("Schrabidium Transmutation Device\nType: Processor\nWith 500 million HE, a special capacitor and uranium, this machine creates schrabidium ingots.");
		book2.add("CMB Steel Factory\nType: Processor\nCombines magnetized tungsten, advanced alloy and poisonous mud into CMB steel.");
		book2.add("Basic Factory\nType: Processor\nLarge furnace with in- and output queue that smelts two items at a time. Needs a specific energy core to run, though it also accepts external energy if it has an electricity port.");
		book2.add("Advanced Factory\nType: Processor\nThis factory smelts four items at a time, twice as fast, making it four times better than the basic factory.");
		book2.add("Large Nuclear Reactor\nType: Generator\nA much larger version of the nuclear reactor which works similarly, but needs a fuse to run. The optional concrete coating will prevent radiation from leaking out.");
		book2.add("Fusion Reactor\nType: Generator\nA very large reactor which fuses deuterium and tritium to create power. It needs startup energy in form of four energy cores (and or fusion cores) and a fuse.");
		book2.add("Watz Power Plant\nType: Generator\nA special fission reactor whcih needs a titanium filter and fuel pellets to run. Pellets have different modifiers changing lifetime and efficiency of all other components. Mind the waste.");
		book2.add("Fusionary Watz Plant\nType: Generator\nThe strongest reactor which fuses antimatter with antischrabidium with the help of a singularity. Different singularities have different effects. Beware the screwdriver.");

		book3.add("This book contains detailed information about the explosives in Hbm's Nuclear Tech Mod. All explosives can also be used with the detonator.");
		book3.add("The Gadget\nType: Nuke\nStrength: " + MainRegistry.gadgetRadius + "\nItems required: 4x bundled propellant, 1x Gadget plutonium core, 1x Wiring");
		book3.add("Little Boy\nType: Nuke\nStrength: " + MainRegistry.boyRadius + "\nItems required: 1x neutron shielding, 1x U235 target, 1x U235 bullet, 1x Little Boy propellant, 1x Little Boy igniter");
		book3.add("Fat Man\nType: Nuke\nStrength: " + MainRegistry.manRadius + "\nItems required: 4x bundled propellant, 1x Fat Man plutonium core, 1x Fatman fuse");
		book3.add("Ivy Mike\nType: H-Bomb\nStrength (nuke): " + MainRegistry.manRadius + "\nStrength (h-bomb): " + MainRegistry.mikeRadius + "\nItems required: 4x bundled propellant, 1x Fat Man plutonium core\nOptional: 1x deuterium supercooler, 1x uranium covered deuterium tank, 1x deuterium tank");
		book3.add("Tsar Bomba\nType: H-Bomb\nStrength (nuke): " + MainRegistry.manRadius + "\nStrength (h-bomb): " + MainRegistry.tsarRadius + "\nItems required: 4x bundled propellant, 1x Fat Man plutonium core\nOptional: Tsar Bomba core");
		book3.add("The Prototype\nType: Schrabidium-Bomb\nStrength:" + MainRegistry.prototypeRadius + "\nItems required: 4x schrabidium-trisulfide, 4x uranium quad rod, 4x lead quad rod, 2x neptunium quad rod");
		book3.add("F.L.E.I.J.A.\nType: Schrabidium-Bomb\nStrength:" + MainRegistry.fleijaRadius + "\nItems required: 2x pulse igniter, 3x schrabidium propellant, 6x F.L.E.I.J.A. uranium charge");
		book3.add("Multi Purpose Bomb\nType: Generic\nStrength: Variable\nNeeds four blocks of TNT in the marked slots, special items can be added in the right two slots. Equal items have their own icon, unequal items show a ?-icon, though they still work together.");
		book3.add("Multi Purpose Bomb (cont.)\nGunpowder: +1 explosion strength, TNT: +4 explosion strength, explosive pellets: +50 bomblets, fire powder: +10 fire radius, poison powder: +15 poison radius, gas cartridge: +15 gas radius");
		book3.add("Dud\nType: ???\nStrength:" + MainRegistry.fatmanRadius + "\nUncraftable, right-click with empty cells to extract antimatter. Can explode via detonator.");
		book3.add("Flame War in a Box\nType: Box\nSpawns random explosions, sets everything on fire.");
		book3.add("Levitation Bomb\nRadius: 15\nHeight: 50\nLifts all blocks and entites caught in it's area of effect.");
		book3.add("Endothermic Bomb\nRadius: 15\nTurns blocks into ice, freezes entities solid.");
		book3.add("Exothermic Bomb\nRadius: 15\nBurns/melts blocks, sets all entities on fire.");
		book3.add("Explosive Barrel\nType: Barrel\nExplodes when shot/set on fire.");
		book3.add("Radioactive Barrel\nType: Barrel\nOnly set off by explosions, contaminates area.");
		
		book4.add("This book contains detailed information about the missiles in Hbm's Nuclear Tech Mod. All missiles are launched from the launch pad, the coordinates are set using the short range target designator.");
		book4.add("HE Missiles\nNormal missile which explodes on impact.\nSmall: 10\nMedium: 25\nLarge: 50 x 5");
		book4.add("Incendiary Missiles\nSpecial missile which explodes and sets the surrounding area on fire.\nSmall: 10 + F\nMedium: 25 + F\nLarge: 35 + F");
		book4.add("Cluster Missiles\nMissile which explodes mid-air and releases small bomblets.\nSmall: 5 + 25B\nMedium: 15 + 50B\nLarge: 25 + 100B");
		book4.add("Bunker Buster\nMissile which creates vertical explosions.\nSmall: 5 x 15\nMedium: 7.5 x 20\nLarge: 10 x 30B");
		book4.add("Thermal Missiles\nEndo or exothermic, area of effect is twice as large as for their stationary counterparts.");
		book4.add("Nuclear Missile\nLarge missile equipped with a nuclear warhead.\nRadius: " + MainRegistry.missileRadius);
		book4.add("Nuclear MIRV\nCrossbreed of a nuclear missile and a cluster missile...splits into eight smaller nukes.\nRadius per MIRV: " + MainRegistry.mirvRadius);

		book5.add("This book contains detailed information about the miscellaneous items in Hbm's Nuclear Tech Mod. All missiles are launched from the launch pad, the coordinates are set using the short range target designator.");
		book5.add("Revolvers\nThese guns come in different variants, each one has it's own ammo. Charge them like a bow for half a second and release to shoot.");
		book5.add("Rocket Launcher\nWorks much like a revolver, but it needs to be completely charged up to fire. Shoots explosive rockets instead of bullets. Duh.");
		book5.add("M42 Nuclear Catapult\nIs basically just an RPG. That shoots nukes. In an arch. Which kill everybody. Full stop.");
		book5.add("Waffle of Mass Destruction\nEat & explode.");
		book5.add("Vegan Schnitzel\nEat & die.");
		book5.add("Radioactive Cotton Candy\nEat & enjoy.");
		book5.add("Schrabidium Apples\nGives you an epic bizeps in 1.2 seconds.");
		book5.add("Tem Flakes\nfood of tem. com in 3 flavurs, on sale, nurmal & PREMIUM!!! (expensiv) heals 2HP. construction paper.");
		book5.add("Oversugared Unhealthy Beverages\nHalf of them taste like urine, all of them are unhealthy.");
		book5.add("Grenades\nMany different types with effects, do not use the schrabidium/drill ones.");
		book5.add("Detonator\nRight click a block to set the position, shift-right click to detonate.");
		book5.add("Supply Drop Requester\nRight click to call in a supply drop, has five uses in survival mode.");
		book5.add("Magazines\nRight click to retrieve ammo for a specific gun.");
		book5.add("Syringes\nRight click to inject the syringe's content, hit somebody else to give them the effects.");
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
	public static void applyRadiation(Entity e, int radDura, int radLevel, int maskDura, int maskLevel) {
		
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
	}
	
	public static boolean checkForHazmat(EntityPlayer player) {
		
		if(checkArmor(player, ModItems.hazmat_helmet, ModItems.hazmat_plate, ModItems.hazmat_legs, ModItems.hazmat_boots) || 
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
	
	public static boolean checkForGasMask(EntityPlayer player) {

		if(checkArmorPiece(player, ModItems.hazmat_helmet, 3))
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
				world.getBlock(x, y, z) == ModBlocks.reactor_conductor ||
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
				world.getBlock(x, y, z) == ModBlocks.dummy_port_reactor_small)
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
				world.getBlock(x, y, z) == ModBlocks.fusion_hatch ||
				world.getBlock(x, y, z) == ModBlocks.watz_hatch ||
				world.getBlock(x, y, z) == ModBlocks.fwatz_hatch ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_ams_limiter ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_ams_emitter ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_ams_base ||
				world.getBlock(x, y, z) == ModBlocks.dummy_port_reactor_small)
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
					((ItemBattery)slots[index].getItem()).chargeBattery(slots[index], 1);
				} else break;
			}
			while(dR >= 1000000000) {
				if(power - 100000000000L >= 0 && ItemBattery.getCharge(slots[index]) < ((ItemBattery)slots[index].getItem()).getMaxCharge())
				{
					power -= 100000000000L;
					dR -= 1000000000;
					((ItemBattery)slots[index].getItem()).chargeBattery(slots[index], 1);
				} else break;
			}
			while(dR >= 1000000) {
				if(power - 100000000 >= 0 && ItemBattery.getCharge(slots[index]) < ((ItemBattery)slots[index].getItem()).getMaxCharge())
				{
					power -= 100000000;
					dR -= 1000000;
					((ItemBattery)slots[index].getItem()).chargeBattery(slots[index], 1);
				} else break;
			}
			while(dR >= 1000) {
				if(power - 100000 >= 0 && ItemBattery.getCharge(slots[index]) < ((ItemBattery)slots[index].getItem()).getMaxCharge())
				{
					power -= 100000;
					dR -= 1000;
					((ItemBattery)slots[index].getItem()).chargeBattery(slots[index], 1);
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

			if(slots[index] != null && slots[index].getItem() == ModItems.dynosphere_desh && ItemBattery.getCharge(slots[index]) == ItemBattery.getMaxChargeStatic(slots[index]))
				slots[index] = new ItemStack(ModItems.dynosphere_desh_charged);
			if(slots[index] != null && slots[index].getItem() == ModItems.dynosphere_schrabidium && ItemBattery.getCharge(slots[index]) == ItemBattery.getMaxChargeStatic(slots[index]))
				slots[index] = new ItemStack(ModItems.dynosphere_schrabidium_charged);
			if(slots[index] != null && slots[index].getItem() == ModItems.dynosphere_euphemium && ItemBattery.getCharge(slots[index]) == ItemBattery.getMaxChargeStatic(slots[index]))
				slots[index] = new ItemStack(ModItems.dynosphere_euphemium_charged);
			if(slots[index] != null && slots[index].getItem() == ModItems.dynosphere_dineutronium && ItemBattery.getCharge(slots[index]) == ItemBattery.getMaxChargeStatic(slots[index]))
				slots[index] = new ItemStack(ModItems.dynosphere_dineutronium_charged);
		}
		
		for(int i = 0; i < 50; i++)
			if(power - 1 >= 0 && slots[index] != null && slots[index].getItem() == ModItems.elec_sword && slots[index].getItemDamage() > 0)
			{
				power -= 1;
				slots[index].setItemDamage(slots[index].getItemDamage() - 1);
			} else break;
	
		for(int i = 0; i < 50; i++)
			if(power - 1 >= 0 && slots[index] != null && slots[index].getItem() == ModItems.elec_pickaxe && slots[index].getItemDamage() > 0)
			{
				power -= 1;
				slots[index].setItemDamage(slots[index].getItemDamage() - 1);
			} else break;
	
		for(int i = 0; i < 50; i++)
			if(power - 1 >= 0 && slots[index] != null && slots[index].getItem() == ModItems.elec_axe && slots[index].getItemDamage() > 0)
			{
				power -= 1;
				slots[index].setItemDamage(slots[index].getItemDamage() - 1);
			} else break;
	
		for(int i = 0; i < 50; i++)
			if(power - 1 >= 0 && slots[index] != null && slots[index].getItem() == ModItems.elec_shovel && slots[index].getItemDamage() > 0)
			{
				power -= 1;
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
					((ItemBattery)slots[index].getItem()).dischargeBattery(slots[index], 1);
				} else break;
			}
			while(dR >= 1000000000) {
				if(power + 100000000000L <= maxPower && ItemBattery.getCharge(slots[index]) > 0)
				{
					power += 100000000000L;
					dR -= 1000000000L;
					((ItemBattery)slots[index].getItem()).dischargeBattery(slots[index], 1);
				} else break;
			}
			while(dR >= 1000000) {
				if(power + 100000000L <= maxPower && ItemBattery.getCharge(slots[index]) > 0)
				{
					power += 100000000L;
					dR -= 1000000;
					((ItemBattery)slots[index].getItem()).dischargeBattery(slots[index], 1);
				} else break;
			}
			while(dR >= 1000) {
				if(power + 100000L <= maxPower && ItemBattery.getCharge(slots[index]) > 0)
				{
					power += 100000L;
					dR -= 1000;
					((ItemBattery)slots[index].getItem()).dischargeBattery(slots[index], 1);
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
		
		if(tileentity instanceof IFluidAcceptor && newTact && !(tileentity instanceof TileEntityMachineFluidTank && ((TileEntityMachineFluidTank)tileentity).dna()))
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
		
		Vec3 vector = Vec3.createVectorHelper(a - x, b - y, c - z);
		double length = vector.lengthVector();
		Vec3 nVec = vector.normalize();
		
		for(float i = 0; i < length; i += 0.25F)
			if(world.getBlock((int) Math.round(x + (nVec.xCoord * i)), (int) Math.round(y + (nVec.yCoord * i)), (int) Math.round(z + (nVec.zCoord * i))) != Blocks.air && 
					world.getBlock((int) Math.round(x + (nVec.xCoord * i)), (int) Math.round(y + (nVec.yCoord * i)), (int) Math.round(z + (nVec.zCoord * i))).isNormalCube())
				return true;
		
		return false;
	}
	
	public static int getFirstNullIndex(int start, Object[] array) {
		for(int i = start; i < array.length; i++) {
			if(array[i] == null)
				return i;
		}
		return -1;
	}
}
