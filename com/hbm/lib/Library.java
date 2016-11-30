package com.hbm.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.hbm.blocks.ModBlocks;
import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
<<<<<<< HEAD
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.projectile.EntityChopperMine;
=======
import com.hbm.entity.EntityChopperMine;
import com.hbm.entity.EntityHunterChopper;
>>>>>>> 5525318475377d238c79edc90a14ee8fa48397af
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityLaunchPad;
import com.hbm.tileentity.TileEntityMachineBattery;
import com.hbm.tileentity.TileEntityMachineDeuterium;
import com.hbm.tileentity.TileEntityMachineElectricFurnace;
import com.hbm.tileentity.TileEntityWireCoated;

import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Library {
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 5525318475377d238c79edc90a14ee8fa48397af

	public static List<String> book1 = new ArrayList<String>();
	public static List<String> book2 = new ArrayList<String>();
	public static List<String> book3 = new ArrayList<String>();
	public static List<String> book4 = new ArrayList<String>();
	public static List<String> book5 = new ArrayList<String>();
<<<<<<< HEAD
=======
=======
	
	public static String book11 = "Resources\nHbm's Nuclear Tech Mod features eleven different ores and 13 materials which are used to craft machines and bombs. Most of them can be obtained from ores or various machines. This book will provide information about all of them.";
	public static String book12 = "Uranium\nUranium is a rather rare ore found deep underground. Smelting the ore will give you a raw uranium ingot which can later be processed into fissile material that can be used for energy production or nuclear weapons.";
	public static String book13 = "Titanium\nTitanium is a material mainly used for crafting machines and bomb hulls. You can get it from smelting titanium ore.";
	public static String book14 = "Steel\nSteel is a commonly used material for building bombs, machines and decorative elements. It can be obtained by combining a lump of coal and an iron ingot in the alloy furnace.";
	public static String book15 = "Sulfur\nSulfur is a powder which will directly drop from it's ore. Sulfur is used for extracting deuterium with the deuterium extractor, crafting gunpowder or Schrabidiumtrisulfite.";
	public static String book16 = "Niter\nNiter also drops directly from the ore, it's only uses are for crafting det cords and gunpowder.";
	public static String book17 = "Industrial Grade Copper\nIndustrial Grade Copper (mainly just referred to as Copper) is an importand ingot used for crafting electronic components and creating Minecraft Grade Copper. It can be obtained by simply smelting copper ore.";
	public static String book18 = "Minecraft Grade Copper\nMinecraft Grade Copper (aka Red Copper) can be created by combining copper ingots and redstone in the alloy furnace. It is used for crafting advanced electronics and batteries.";
	public static String book19 = "Tungsten\nTungsten can be used to make neutron reflectors by combining it in the alloy furnace with coal. It is also needed for heating coils, which are importand for storing uranium and plutonium hexafluorite.";
	public static String book110 = "Aluminium\nAluminium is a commonly found metal that is used for crafting hulls and machines. Basic circuits are also made out of aluminium wires.";
	public static String book111 = "Fuorite\nFluorite is a powder which can be found in form of ores. It is used to make uranium and plutonium hexafluorite, gasses which can be enriched in a gas centrifuge.";
	public static String book112 = "Beryllium\nBeryllium is an ingot which can be simply obtained from it's ore. Beryllium is used for Schrabidium fuel and plutonium cores.";
	public static String book113 = "Lead\nLead is a very common ore that can be smelted into an ingot. It can be used to craft breeding reactors, RTG-Units and if combined with copper in the alloy furnace, it can be processed into neutron reflectors.";
	public static String book114 = "Schrabidium\nSchrabidium is a very odd element only found near nuclear testing sites. It has violent behaviours if used the right way, making it perfect for bombs. If a big ammount is hit by neutrons, it will create other elements.";
	public static String book115 = "Advanced Alloy\nThe advanced alloy is a very durable and useful material, only used for the best factories which can handle four operations at once. It is composed of Minecraft grade copper and steel, combined by an alloy furnace.";
	public static String book116 = "Fuel\nFuel is obtained by combining an empty canister and a piece of coal in the alloy furnace. It is used for crafting explosive barrels and missiles.";
	
	public static String book21 = "Machines\nAs an essential part of technic mods, Hbm's Nuclear Tech Mod also features many machines. They are either used to smelt or refine items or to store hazardous materials or even generate electricity.";
	public static String book22 = "Alloy Furnace\nThe alloy furnace is the most importand machine as it is used to create alloys like red copper or neutron reflectors. It uses different kinds of fuel such as coal, netherrack, lava or even redstone.";
	public static String book23 = "Gas Centrifuge\nThe gas centrifuge is a requirement for any advanced nuclear tech as it can be used to enrich uranium and plutonium. It uses the same fuel as the alloy furnace. It can process uranium and plutonium hexafluorite.";
	public static String book24 = "Hexafluorite Tanks\nThese tanks can store uranium or plutonium hexafluorite. By placing a filled cell into the upper-left slot, the gas will be transfered into the tank. Placing empty cells into the upper-right slot will drain the tank's content.";
	public static String book25 = "Breeding Reactor\nThe breeding reactor is used to breed uranium into plutonium. By filling the lower slot with a radioactive rod it will supply it with power, the rod which shall be breeded goes into the upper slot.";
	public static String book26 = "Nuclear Powered Furnace\nThis furnace works like the regular furnace, but uses fissile material as fuel instead. Each smelting process only takes arround 1.5 seconds.";
	public static String book27 = "RTG-Furnace\nThe RGT-Furnace also works like a normal furnace, but uses three plutonium 238 pellets as fuel. Those pellets never deplete, making this furnace last forever. Smelting an item takes 2.5 seconds.";
	public static String book28 = "Coal Generator\nThe coal generator can generate electricity by burning coal. It needs a constant water supply for successfully providing energy, it can use water buckets or cells. You can transport it's energy either with wires or batteries.";
	public static String book29 = "Nuclear Reactor\nThe nuclear reactor uses special fuel cells to generate energy. It needs water to do so and coolant to prevent it from overheating.";
	public static String book210 = "Coated Wire\nThis wire is made from red copper and can transfer electricity. It can take energy from various generators and transport it to machines.";
	public static String book211 = "Energy Storage Block\nThis battery block will collect electricity from wires. Applying a redstone signal will prevent it from getting more energy and make it release it's electricity again.";
	public static String book212 = "Electric Furnace\nThe electric furnace works like a normal furnace, but uses electricity instead of fuel items. It can be powered either with wires or with battery items.";
	public static String book213 = "Deuterium Extractor\nThis machine needs electricity, water, sulfur and empty cells to extract deuterium from the water. Water will deplete much faster than sulfur.";
	public static String book214 = "Factories\nFactories are big 3x3x3 furnaces on steroids. Depending on what type you use, it can smelt 2 or 4 items at the same time, while having nine additional in and output slots!";
	public static String book215 = "Large Nuclear Reactor\nThese big machines work like regular nuclear reactors, but can store much more energy, water, coolant and up to 30 slots of fuel! Be sure to protect it with a concrete shell, or else it will emit deadly doses of radiation.";
	public static String book216 = "Fusion Reactor\nHarness the mighty power of the sun with this high-tech machinery! It will not present any danger as it can not explode or radiate, unlike other reactors. It needs deuterium, tritium, a fuse and four energy cores to start the fusion process.";
	public static String book217 = "Schrabidium Transmutator\nThis new innovaion turns regular impure uranium into schrabidium! It needs almost 500MHE and a neutral redcoil capacitor to work. The capacitors can be discharged, but it is very dangerous to do so.";

	public static String book31 = "Bombs\nThis mod also features many different bombs, some of them work like regular TNT, others are nukes and need additional items to ignite.";
	public static String book32 = "The Gadget\nThe Gadget was the first functional nuclear explosive ever built. It needs four special propellants, a plutonium core and a cable drum. It's crater has a radius up to 150 meters.";
	public static String book33 = "Little Boy\nLittle Boy is a gun-type nuke which uses a tungsten-carbide neutron reflector, two different uranium 235 elements, a propellant and it's igniter. It has an explosion radius of 120.";
	public static String book34 = "Fat Man\nFat Man is very similar to the Gadged in terms of it's inner parts, even though it uses alternate versions of these items. It's explosion radius is 175 meters.";
	public static String book35 = "Ivy Mike\nIvy Mike is a hydrogen bomb which needs Fat Man's parts to work. Additionally, you can add an uranium tank, deuterium and a cooler to increase the radius by a lot. Radius with Fat Man parts: 175 meters, radius with additional items: 250 meters.";
	public static String book36 = "Tsar Bomba\nThe Tsar Bomba was the strongest bomb ever built and is a hydrogen bomb much like Ivy Mike. It needs Fat Man's parts to work and with the fusion core, the explosion radius reaches an astounding 500 meters.";
	public static String book37 = "The Prototype\nThe Prototype was the first (and strongest) Schrabidium-powered bomb. It was made from a pimped makeshift nuclear reactor using liquid Schrabidiumtrisulfide. Like any other Schrabidium bomb, the Prototype's crater is 100% spherical,";
	public static String book38 = "leaving no blocks untouched, besides Bedrock on Y: 0. It needs four quad rods of regular uranium, four quad rods filled with lead, two quad rods of neptunium and four Schrabidiumtrisulfide cells in order to explode. It has a crater radius of only";
	public static String book39 = "150 meters, but because it's shape, it can destroy a whopping 14 million blocks, the seven million blocks of the bottom half are ten times more than the ammount of blocks the Gadget will destroy below it's Y axis.";
	public static String book310 = "F.L.E.I.J.A.\nF.L.E.I.J.A. (pronounced �oFre-ja�r) is a high-tech Schrabidium bomb which consists of two special impulse igniters, three Schrabidium propellants and six magnetized F.L.E.I.J.A. uranium 235 cores. It has a range of 50 meters.";
	public static String book311 = "Multi Purpose Bomb\nThe multi purpose bomb is a small customizable explosive which requires four blocks of TNT to work. It has a base explosion value which can be modified with different items. You can either use two of a kind (the small box will show";
	public static String book312 = "you what kind of explosion it will create), only one item (with half the radius/effect of the modifier) or two different items (two different items or one single are part of scambled mode, indicated by a question mark. It will have two different effects.)";
	public static String book313 = "Modifying items:\nBase Value: 8\nGunpowder: +1\nTNT: +4\nExplosive Pellets: +50 bomblets\nFire Powder: +10 fire radius\nPoison Powder: +15 poison radius\nGas Cartridge: +15 gas radius";
	public static String book314 = "Flame War in a Box\nEverything explodes, everything is on fire. Enough desription.";
	public static String book315 = "Levitation Bomb\nThis bomb uses a Schrabidium powered modified thermal distribution unit to lift the surrounding area 50 meters up. It will mess with entities caught in it's radius, making them...weird.";
	public static String book316 = "Endothermic Bomb\nThis bomb contains an ice-filled thermal distribution unit and will freeze blocks and entities close to it. Entities will get a slowness effect and freeze to a big ice cube.";
	public static String book317 = "Exothermic Bomb\nThe exothermic bomb needs a thermal distribution unit filled with lava in order to burn and/or melt nearby blocks. Living creatures will get a slowness and weakness effect.";
	public static String book318 = "Det Cord\nThe det cord is a TNT like block with a much smaller explosion radius. It will explode right after ignition. It can be triggered by redstone or other explosions and is ideal to trigger far away TNT chrages.";
	public static String book319 = "Explosive Barrel\nThis is a red barrel filled with fuel. It will explode if it catches fire, explodes or gets shot with a revolver. It will blow up in a big firey explosion, great for demolishing houses or forests.";
	public static String book320 = "Radioactive Barrel\nA yellow barrel filled to the top with nuclear waste. It has a much bigger explosion radius than it's non-nuclear counterpart, and can only be triggered by explosions. It will give creatures bad effects when they stay to close.";

	public static String book41 = "Missiles\nThere are four different tiers and four major groups of missiles. They need a launch pad to work and will fly to a maked target where they will explode. Most missiles are easily recognizable by their shape and color.";
	public static String book42 = "HE Missiles\nHE missiles (often just referred to as generic missiles) are the simplest type of missiles. They will fly in an arc shape and explode on impact. There are three different HE missiles, one for each of the first three tiers.";
	public static String book43 = "Incendiary Missiles\nIncendiary missiles work just like generic missiles, with the slight difference that the explosion will spawn fire. The higher the tier, the more fire the impact will create. There are three different tiers of incendiary missiles.";
	public static String book44 = "Cluster Missiles\nCluster missiles will fly the same arc shape as all the other missiles do, with the exception that it will explode just before impact, releasing hundrets of small bomblets. They are avalible in three different sizes (tiers)";
	public static String book45 = "Bunker Busters\nA bunker buster behaves much like a normal missile, except that it's explosion goes further down, digging holes and revealing underground structures or destroying bunkers. They also come in three different sizes.";
	public static String book46 = "Endothermic Missile\nA tier 3 missile which works exactly like an endothermic bomb, but has double the effect radius.";
	public static String book47 = "Exothermic Missile\nAnother tier 3 missile with an exothermic warhead and double the radius of it's stationary counterpart.";
	public static String book48 = "Nuclear Missile\nIt's basically a small nuke mounted on a missile. It has an explosion radius of 100 meters, making it the smallest nuke (next to the waffle of mass destruction, the nuclear creeper and the MIRV.)";
	public static String book49 = "Nuclear MIRV Missile\nThe nuclear MIRV is the most destructive intercontinental ballistic missile ever created. It combines a cluster bomb with the destructive force of a nuke, splitting into eight small warheads just before impact.";

	public static String book51 = "Misc\nNext to nuclear technology and weapons of mass destruction, this mod also offers some gimmicks, handguns, different armor sets, randomly generated dungeons and more! (Please, do not touch any objects you don't know. You will probably explode and die.)";
	public static String book52 = "Revolvers\nLike in the good old Western films, you can shoot with your own revolver! There are four different tiers of revolvers, dealing about 5 - infinite damage, depending on the tier. Note: A revolver can not hurt mobs like the ender dragon!";
	public static String book53 = "RPG\nTo use the RPG, charge it like a bow and release the charge when it reached it's maximum. The rocket will cause a small explosion about half the size of a creeper's explosion.";
	public static String book54 = "Nuke Launcher\nThe M42 Nuclear Catapult \"Fat Man\" is the perfect christmas present for every small child! It shoots small tactical nuclear warheads causing chaos and destrucion. (What did you expect?)";
	public static String book55 = "Grenades\nA grenade is a very easy-to-use type of explosive. Simply throw it like a snowball and watch the explosion. Remember: Never, NEVER throw a Schrabidium Grenade.";
	public static String book56 = "Syringes\nDon't do drugs! (except in Minecraft) Need some health? Take a Stimpak. You demand damage resistance? Inject a Med-X. Want to go nuts? Take AWESOME (only one dose per hour will help) Warning: Hitting enemies with syinges will give them the effect!";
	public static String book57 = "Mobile Stealth Device\nThis useful machine will make you invisibile, even without the annoying particles! Just remember that the effect will wear off in 30 seconds, so don't rely too much on it.";
	public static String book58 = "Food\nSurprisingly enough, this mod offers a great variety of different food. Be sure not to eat anything, you may explode/combust/die of radiation poisoning/starve to death/experience a rare case of waffles taking over the world.";

	public static String book61;
>>>>>>> 540fb3d256a0f4ae6a8b1db586f8e9cfd6ed7372
>>>>>>> 5525318475377d238c79edc90a14ee8fa48397af

	public static String HbMinecraft = "192af5d7-ed0f-48d8-bd89-9d41af8524f8";
	public static String LPkukin = "937c9804-e11f-4ad2-a5b1-42e62ac73077";
	public static String Dafnik = "3af1c262-61c0-4b12-a4cb-424cc3a9c8c0";
	public static String a20 = "4729b498-a81c-42fd-8acd-20d6d9f759e0";
	
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
<<<<<<< HEAD
		book5.add("Syringes\nRight click to inject the syringe's content, hit somebody else to give them the effects.");
		book5.add("");
		book5.add("");
		book5.add("");
		book5.add("Coincidences are less likely than most people believe, don't you think? Look at things twice. Try imagining what the creators and inventors were thinking. All things happen for a reason, tough sometimes they are not as clear. \n\nPPPPPPP");
=======
>>>>>>> 5525318475377d238c79edc90a14ee8fa48397af
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
	
	public static void damageSuit(EntityPlayer player, int slot) {
		
		int j = player.inventory.armorInventory[slot].getItemDamage();
		player.inventory.armorInventory[slot].setItemDamage(j += 1);

		if(player.inventory.armorInventory[slot].getItemDamage() == player.inventory.armorInventory[slot].getMaxDamage())
		{
			player.inventory.armorInventory[slot] = null;
		}
	}
	
	public static boolean checkForHazmat(EntityPlayer player) {
		
		if(checkArmor(player, ModItems.hazmat_helmet, ModItems.hazmat_plate, ModItems.hazmat_legs, ModItems.hazmat_boots) || 
				checkArmor(player, ModItems.t45_helmet, ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots) || 
				checkArmor(player, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots) || 
				checkArmor(player, ModItems.hazmat_paa_helmet, ModItems.hazmat_paa_plate, ModItems.hazmat_paa_legs, ModItems.hazmat_paa_boots))
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
				world.getBlock(x, y, z) == ModBlocks.factory_advanced_conductor ||
				world.getBlock(x, y, z) == ModBlocks.watz_conductor ||
				world.getBlock(x, y, z) == ModBlocks.fwatz_hatch)
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
<<<<<<< HEAD
	
	public static MovingObjectPosition rayTrace(EntityPlayer player, double d, float f) {
        Vec3 vec3 = player.getPosition(f);
        vec3.yCoord += player.eyeHeight;
        Vec3 vec31 = player.getLook(f);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * d, vec31.yCoord * d, vec31.zCoord * d);
        return player.worldObj.func_147447_a(vec3, vec32, false, false, true);
	}
=======
>>>>>>> 5525318475377d238c79edc90a14ee8fa48397af
}
