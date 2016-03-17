package com.hbm.lib;

import java.util.List;
import java.util.UUID;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.TileEntityLaunchPad;
import com.hbm.blocks.TileEntityMachineBattery;
import com.hbm.blocks.TileEntityMachineDeuterium;
import com.hbm.blocks.TileEntityMachineElectricFurnace;
import com.hbm.blocks.TileEntityWireCoated;
import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Library {
	
	public static String book11 = "§lResources§r\nHbm's Nuclear Tech Mod features eleven different ores and 13 materials which are used to craft machines and bombs. Most of them can be obtained from ores or various machines. This book will provide information about all of them.";
	public static String book12 = "§lUranium§r\nUranium is a rather rare ore found deep underground. Smelting the ore will give you a raw uranium ingot which can later be processed into fissile material that can be used for energy production or nuclear weapons.";
	public static String book13 = "§lTitanium§r\nTitanium is a material mainly used for crafting machines and bomb hulls. You can get it from smelting titanium ore.";
	public static String book14 = "§lSteel§r\nSteel is a commonly used material for building bombs, machines and decorative elements. It can be obtained by combining a lump of coal and an iron ingot in the alloy furnace.";
	public static String book15 = "§lSulfur§r\nSulfur is a powder which will directly drop from it's ore. Sulfur is used for extracting deuterium with the deuterium extractor, crafting gunpowder or Schrabidiumtrisulfite.";
	public static String book16 = "§lNiter§r\nNiter also drops directly from the ore, it's only uses are for crafting det cords and gunpowder.";
	public static String book17 = "§lIndustrial Grade Copper§r\nIndustrial Grade Copper (mainly just referred to as §oCopper§r) is an importand ingot used for crafting electronic components and creating Minecraft Grade Copper. It can be obtained by simply smelting copper ore.";
	public static String book18 = "§lMinecraft Grade Copper§r\nMinecraft Grade Copper (aka §oRed Copper§r) can be created by combining copper ingots and redstone in the alloy furnace. It is used for crafting advanced electronics and batteries.";
	public static String book19 = "§lTungsten§r\nTungsten can be used to make neutron reflectors by combining it in the alloy furnace with coal. It is also needed for heating coils, which are importand for storing uranium and plutonium hexafluorite.";
	public static String book110 = "§lAluminium§r\nAluminium is a commonly found metal that is used for crafting hulls and machines. Basic circuits are also made out of aluminium wires.";
	public static String book111 = "§lFuorite§r\nFluorite is a powder which can be found in form of ores. It is used to make uranium and plutonium hexafluorite, gasses which can be enriched in a gas centrifuge.";
	public static String book112 = "§lBeryllium§r\nBeryllium is an ingot which can be simply obtained from it's ore. Beryllium is used for Schrabidium fuel and plutonium cores.";
	public static String book113 = "§lLead§r\nLead is a very common ore that can be smelted into an ingot. It can be used to craft breeding reactors, RTG-Units and if combined with copper in the alloy furnace, it can be processed into neutron reflectors.";
	public static String book114 = "§lSchrabidium§r\nSchrabidium is a very odd element only found near nuclear testing sites. It has violent behaviours if used the right way, making it perfect for bombs. If a big ammount is hit by neutrons, it will create other elements.";
	public static String book115 = "§lAdvanced Alloy§r\nThe advanced alloy is a very durable and useful material, only used for the best factories which can handle four operations at once. It is composed of Minecraft grade copper and steel, combined by an alloy furnace.";
	public static String book116 = "§lFuel§r\nFuel is obtained by combining an empty canister and a piece of coal in the alloy furnace. It is used for crafting explosive barrels and missiles.";
	
	public static String book21 = "§lMachines§r\nAs an essential part of technic mods, Hbm's Nuclear Tech Mod also features many machines. They are either used to smelt or refine items or to store hazardous materials or even generate electricity.";
	public static String book22 = "§lAlloy Furnace§r\nThe alloy furnace is the most importand machine as it is used to create alloys like red copper or neutron reflectors. It uses different kinds of fuel such as coal, netherrack, lava or even redstone.";
	public static String book23 = "§lGas Centrifuge§r\nThe gas centrifuge is a requirement for any advanced nuclear tech as it can be used to enrich uranium and plutonium. It uses the same fuel as the alloy furnace. It can process uranium and plutonium hexafluorite.";
	public static String book24 = "§lHexafluorite Tanks§r\nThese tanks can store uranium or plutonium hexafluorite. By placing a filled cell into the upper-left slot, the gas will be transfered into the tank. Placing empty cells into the upper-right slot will drain the tank's content.";
	public static String book25 = "§lBreeding Reactor§r\nThe breeding reactor is used to breed uranium into plutonium. By filling the lower slot with a radioactive rod it will supply it with power, the rod which shall be breeded goes into the upper slot.";
	public static String book26 = "§lNuclear Powered Furnace§r\nThis furnace works like the regular furnace, but uses fissile material as fuel instead. Each smelting process only takes arround 1.5 seconds.";
	public static String book27 = "§lRTG-Furnace§r\nThe RGT-Furnace also works like a normal furnace, but uses three plutonium 238 pellets as fuel. Those pellets never deplete, making this furnace last forever. Smelting an item takes 2.5 seconds.";
	public static String book28 = "§lCoal Generator§r\nThe coal generator can generate electricity by burning coal. It needs a constant water supply for successfully providing energy, it can use water buckets or cells. You can transport it's energy either with wires or batteries.";
	public static String book29 = "§lNuclear Reactor§r\nThe nuclear reactor uses special fuel cells to generate energy. It needs water to do so and coolant to prevent it from overheating.";
	public static String book210 = "§lCoated Wire§r\nThis wire is made from red copper and can transfer electricity. It can take energy from various generators and transport it to machines.";
	public static String book211 = "§lEnergy Storage Block§r\nThis battery block will collect electricity from wires. Applying a redstone signal will prevent it from getting more energy and make it release it's electricity again.";
	public static String book212 = "§lElectric Furnace§r\nThe electric furnace works like a normal furnace, but uses electricity instead of fuel items. It can be powered either with wires or with battery items.";
	public static String book213 = "§lDeuterium Extractor§r\nThis machine needs electricity, water, sulfur and empty cells to extract deuterium from the water. Water will deplete much faster than sulfur.";
	public static String book214 = "§lFactories§r\nFactories are big 3x3x3 furnaces on steroids. Depending on what type you use, it can smelt 2 or 4 items at the same time, while having nine additional in and output slots!";
	public static String book215 = "§lLarge Nuclear Reactor§r\nThese big machines work like regular nuclear reactors, but can store much more energy, water, coolant and up to 30 slots of fuel! Be sure to protect it with a concrete shell, or else it will emit deadly doses of radiation.";
	public static String book216 = "§lFusion Reactor§r\nHarness the mighty power of the sun with this high-tech machinery! It will not present any danger as it can not explode or radiate, unlike other reactors. It needs deuterium, tritium, a fuse and four energy cores to start the fusion process.";

	public static String book31 = "§lBombs§r\nThis mod also features many different bombs, some of them work like regular TNT, others are nukes and need additional items to ignite.";
	public static String book32 = "§lThe Gadget§r\nThe Gadget was the first functional nuclear explosive ever built. It needs four special propellants, a plutonium core and a cable drum. It's crater has a radius up to 150 meters.";
	public static String book33 = "§lLittle Boy§r\nLittle Boy is a gun-type nuke which uses a tungsten-carbide neutron reflector, two different uranium 235 elements, a propellant and it's igniter. It has an explosion radius of 120.";
	public static String book34 = "§lFat Man§r\nFat Man is very similar to the Gadged in terms of it's inner parts, even though it uses alternate versions of these items. It's explosion radius is 175 meters.";
	public static String book35 = "§lIvy Mike§r\nIvy Mike is a hydrogen bomb which needs Fat Man's parts to work. Additionally, you can add an uranium tank, deuterium and a cooler to increase the radius by a lot. Radius with Fat Man parts: 175 meters, radius with additional items: 250 meters.";
	public static String book36 = "§lTsar Bomba§r\nThe Tsar Bomba was the strongest bomb ever built and is a hydrogen bomb much like Ivy Mike. It needs Fat Man's parts to work and with the fusion core, the explosion radius reaches an astounding 500 meters.";
	public static String book37 = "§lThe Prototype§r\nThe Prototype was the first (and strongest) Schrabidium-powered bomb. It was made from a pimped makeshift nuclear reactor using liquid Schrabidiumtrisulfide. Like any other Schrabidium bomb, the Prototype's crater is 100% spherical,";
	public static String book38 = "leaving no blocks untouched, besides Bedrock on Y: 0. It needs four quad rods of regular uranium, four quad rods filled with lead, two quad rods of neptunium and four Schrabidiumtrisulfide cells in order to explode. It has a crater radius of only";
	public static String book39 = "150 meters, but because it's shape, it can destroy a whopping 14 million blocks, the seven million blocks of the bottom half are ten times more than the ammount of blocks the Gadget will destroy below it's Y axis.";
	public static String book310 = "§lF.L.E.I.J.A.§r\nF.L.E.I.J.A. (pronounced §oFre-ja§r) is a high-tech Schrabidium bomb which consists of two special impulse igniters, three Schrabidium propellants and six magnetized F.L.E.I.J.A. uranium 235 cores. It has a range of 50 meters.";
	public static String book311 = "§lMulti Purpose Bomb§r\nThe multi purpose bomb is a small customizable explosive which requires four blocks of TNT to work. It has a base explosion value which can be modified with different items. You can either use two of a kind (the small box will show";
	public static String book312 = "you what kind of explosion it will create), only one item (with half the radius/effect of the modifier) or two different items (two different items or one single are part of scambled mode, indicated by a question mark. It will have two different effects.)";
	public static String book313 = "Modifying items:\nBase Value: 8\nGunpowder: +1\nTNT: +4\nExplosive Pellets: +50 bomblets\nFire Powder: +10 fire radius\nPoison Powder: +15 poison radius\nGas Cartridge: +15 gas radius";
	public static String book314 = "§lFlame War in a Box§r\nEverything explodes, everything is on fire. Enough desription.";
	public static String book315 = "§lLevitation Bomb§r\nThis bomb uses a Schrabidium powered modified thermal distribution unit to lift the surrounding area 50 meters up. It will mess with entities caught in it's radius, making them...weird.";
	public static String book316 = "§lEndothermic Bomb§r\nThis bomb contains an ice-filled thermal distribution unit and will freeze blocks and entities close to it. Entities will get a slowness effect and freeze to a big ice cube.";
	public static String book317 = "§lExothermic Bomb§r\nThe exothermic bomb needs a thermal distribution unit filled with lava in order to burn and/or melt nearby blocks. Living creatures will get a slowness and weakness effect.";
	public static String book318 = "§lDet Cord§r\nThe det cord is a TNT like block with a much smaller explosion radius. It will explode right after ignition. It can be triggered by redstone or other explosions and is ideal to trigger far away TNT chrages.";
	public static String book319 = "§lExplosive Barrel§r\nThis is a red barrel filled with fuel. It will explode if it catches fire, explodes or gets shot with a revolver. It will blow up in a big firey explosion, great for demolishing houses or forests.";
	public static String book320 = "§lRadioactive Barrel§r\nA yellow barrel filled to the top with nuclear waste. It has a much bigger explosion radius than it's non-nuclear counterpart, and can only be triggered by explosions. It will give creatures bad effects when they stay to close.";

	public static String book41 = "§lMissiles§r\nThere are four different tiers and four major groups of missiles. They need a launch pad to work and will fly to a maked target where they will explode. Most missiles are easily recognizable by their shape and color.";
	public static String book42 = "§lHE Missiles§r\nHE missiles (often just referred to as generic missiles) are the simplest type of missiles. They will fly in an arc shape and explode on impact. There are three different HE missiles, one for each of the first three tiers.";
	public static String book43 = "§lIncendiary Missiles§r\nIncendiary missiles work just like generic missiles, with the slight difference that the explosion will spawn fire. The higher the tier, the more fire the impact will create. There are three different tiers of incendiary missiles.";
	public static String book44 = "§lCluster Missiles§r\nCluster missiles will fly the same arc shape as all the other missiles do, with the exception that it will explode just before impact, releasing hundrets of small bomblets. They are avalible in three different sizes (tiers)";
	public static String book45 = "§lBunker Busters§r\nA bunker buster behaves much like a normal missile, except that it's explosion goes further down, digging holes and revealing underground structures or destroying bunkers. They also come in three different sizes.";
	public static String book46 = "§lEndothermic Missile§r\nA tier 3 missile which works exactly like an endothermic bomb, but has double the effect radius.";
	public static String book47 = "§lExothermic Missile§r\nAnother tier 3 missile with an exothermic warhead and double the radius of it's stationary counterpart.";
	public static String book48 = "§lNuclear Missile§r\nIt's basically a small nuke mounted on a missile. It has an explosion radius of 100 meters, making it the smallest nuke (next to the waffle of mass destruction, the nuclear creeper and the MIRV.)";
	public static String book49 = "§lNuclear MIRV Missile§r\nThe nuclear MIRV is the most destructive intercontinental ballistic missile ever created. It combines a cluster bomb with the destructive force of a nuke, splitting into eight small warheads just before impact.";

	public static String book51 = "§lMisc§r\nNext to nuclear technology and weapons of mass destruction, this mod also offers some gimmicks, handguns, different armor sets, randomly generated dungeons and more! (Please, do not touch any objects you don't know. You will probably explode and die.)";
	public static String book52 = "§lRevolvers§r\nLike in the good old Western films, you can shoot with your own revolver! There are four different tiers of revolvers, dealing about 5 - infinite damage, depending on the tier. Note: A revolver can not hurt mobs like the ender dragon!";
	public static String book53 = "§lRPG§r\nTo use the RPG, charge it like a bow and release the charge when it reached it's maximum. The rocket will cause a small explosion about half the size of a creeper's explosion.";
	public static String book54 = "§lNuke Launcher§r\nThe M42 Nuclear Catapult \"Fat Man\" is the perfect christmas present for every small child! It shoots small tactical nuclear warheads causing chaos and destrucion. (What did you expect?)";
	public static String book55 = "§lGrenades§r\nA grenade is a very easy-to-use type of explosive. Simply throw it like a snowball and watch the explosion. Remember: Never, NEVER throw a Schrabidium Grenade.";
	public static String book56 = "§lSyringes§r\nDon't do drugs! (except in Minecraft) Need some health? Take a Stimpak. You demand damage resistance? Inject a Med-X. Want to go nuts? Take AWESOME (only one dose per hour will help) Warning: Hitting enemies with syinges will give them the effect!";
	public static String book57 = "§lMobile Stealth Device§r\nThis useful machine will make you invisibile, even without the annoying particles! Just remember that the effect will wear off in 30 seconds, so don't rely too much on it.";
	public static String book58 = "§lFood§r\nSurprisingly enough, this mod offers a great variety of different food. Be sure not to eat anything, you may explode/combust/die of radiation poisoning/starve to death/experience a rare case of waffles taking over the world.";

	public static String book61;

	public static /*UUID*/String HbMinecraft = /*UUID.fromString(*/"192af5d7-ed0f-48d8-bd89-9d41af8524f8"/*)*/;
	public static UUID LPkukin = UUID.fromString("937c9804-e11f-4ad2-a5b1-42e62ac73077");
	
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
	
	public static void damageSuit(EntityPlayer player, int slot) {
		
		int j = player.inventory.armorInventory[slot].getItemDamage();
		player.inventory.armorInventory[slot].setItemDamage(j += 1);

		if(player.inventory.armorInventory[slot].getItemDamage() == player.inventory.armorInventory[slot].getMaxDamage())
		{
			player.inventory.armorInventory[slot] = null;
		}
	}
	
	public static boolean checkForHazmat(EntityPlayer player) {
		
		if(checkArmor(player, ModItems.hazmat_helmet, ModItems.hazmat_plate, ModItems.hazmat_legs, ModItems.hazmat_boots) || checkArmor(player, ModItems.t45_helmet, ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots) || checkArmor(player, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots))
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
		if(checkArmorPiece(player, ModItems.gas_mask, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.t45_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.euphemium_helmet, 3))
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean checkConnectables(World world, int x, int y, int z)
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if((tileentity != null && (tileentity instanceof IConductor ||
				tileentity instanceof IConsumer ||
				tileentity instanceof ISource)) ||
				world.getBlock(x, y, z) == ModBlocks.fusion_center ||
				world.getBlock(x, y, z) == ModBlocks.reactor_conductor ||
				world.getBlock(x, y, z) == ModBlocks.factory_titanium_conductor ||
				world.getBlock(x, y, z) == ModBlocks.factory_advanced_conductor)
		{
			return true;
		}
		return false;
	}
	
	public static boolean checkUnionList(List<UnionOfTileEntitiesAndBooleans> list, TileEntity that) {
		
		for(UnionOfTileEntitiesAndBooleans union : list)
		{
			if(union.source == that)
			{
				return true;
			}
		}
		
		return false;
	}
}
