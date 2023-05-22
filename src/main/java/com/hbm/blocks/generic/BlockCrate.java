package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ItemAmmoEnums.Ammo44Magnum;
import com.hbm.items.ModItems;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockCrate extends BlockFalling {

	List<ItemStack> crateList;
	List<ItemStack> weaponList;
	List<ItemStack> leadList;
	List<ItemStack> metalList;
	List<ItemStack> redList;

	public BlockCrate(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if(player.getHeldItem() != null && player.getHeldItem().getItem().equals(ModItems.crowbar)) {
			dropItems(world, x, y, z);
			world.setBlockToAir(x, y, z);
			world.playSoundEffect(x, y, z, "hbm:block.crateBreak", 0.5F, 1.0F);
			return true;
		} else {
			if(world.isRemote) {
				player.addChatMessage(new ChatComponentText("I'll need a crate opening device to get the loot, smashing the whole thing won't work..."));
			}
		}

		return true;
	}

	public void setDrops() {

		crateList = new ArrayList();
		weaponList = new ArrayList();
		leadList = new ArrayList();
		metalList = new ArrayList();
		redList = new ArrayList();

		// Supply Crate
		BlockCrate.addToListWithWeight(crateList, ModItems.syringe_metal_stimpak, 10);
		BlockCrate.addToListWithWeight(crateList, ModItems.syringe_antidote, 5);
		BlockCrate.addToListWithWeight(crateList, ModItems.clip_revolver_iron, 9);
		BlockCrate.addToListWithWeight(crateList, ModItems.clip_revolver, 7);
		BlockCrate.addToListWithWeight(crateList, ModItems.clip_revolver_gold, 4);
		BlockCrate.addToListWithWeight(crateList, ModItems.clip_revolver_lead, 6);
		BlockCrate.addToListWithWeight(crateList, ModItems.clip_revolver_cursed, 5);
		BlockCrate.addToListWithWeight(crateList, ModItems.clip_rpg, 5);
		BlockCrate.addToListWithWeight(crateList, ModItems.clip_fatman, 1);
		BlockCrate.addToListWithWeight(crateList, ModItems.clip_mp40, 7);
		BlockCrate.addToListWithWeight(crateList, ModItems.clip_uzi, 7);
		BlockCrate.addToListWithWeight(crateList, ModItems.clip_uboinik, 7);
		BlockCrate.addToListWithWeight(crateList, ModItems.clip_lever_action, 5);
		BlockCrate.addToListWithWeight(crateList, ModItems.clip_bolt_action, 5);
		BlockCrate.addToListWithWeight(crateList, ModItems.grenade_generic, 8);
		BlockCrate.addToListWithWeight(crateList, ModItems.grenade_strong, 6);
		BlockCrate.addToListWithWeight(crateList, ModItems.grenade_mk2, 4);
		BlockCrate.addToListWithWeight(crateList, ModItems.grenade_flare, 4);
		BlockCrate.addToListWithWeight(crateList, ModItems.ammo_container, 2);

		// Weapon Crate
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_revolver, 9);
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_revolver_gold, 7);
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_revolver_cursed, 7);
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_calamity, 3);
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_rpg, 7);
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_karl, 4);
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_panzerschreck, 6);
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_hk69, 8);
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_stinger, 7);
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_mp40, 9);
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_uzi, 6);
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_uzi_silencer, 5);
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_uboinik, 8);
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_lever_action, 7);
		BlockCrate.addToListWithWeight(weaponList, ModItems.gun_bolt_action, 7);

		// Lead Crate
		BlockCrate.addToListWithWeight(leadList, ModItems.ingot_uranium, 10);
		// BlockCrate.addToListWithWeight(leadList, ModItems.ingot_u235, 5);
		BlockCrate.addToListWithWeight(leadList, ModItems.ingot_u238, 8);
		BlockCrate.addToListWithWeight(leadList, ModItems.ingot_plutonium, 7);
		// BlockCrate.addToListWithWeight(leadList, ModItems.ingot_pu238, 5);
		// BlockCrate.addToListWithWeight(leadList, ModItems.ingot_pu239, 4);
		BlockCrate.addToListWithWeight(leadList, ModItems.ingot_pu240, 6);
		BlockCrate.addToListWithWeight(leadList, ModItems.ingot_neptunium, 7);
		BlockCrate.addToListWithWeight(leadList, ModItems.ingot_uranium_fuel, 8);
		BlockCrate.addToListWithWeight(leadList, ModItems.ingot_plutonium_fuel, 7);
		BlockCrate.addToListWithWeight(leadList, ModItems.ingot_mox_fuel, 6);
		BlockCrate.addToListWithWeight(leadList, ModItems.nugget_uranium, 10);
		// BlockCrate.addToListWithWeight(leadList, ModItems.nugget_u235, 5);
		BlockCrate.addToListWithWeight(leadList, ModItems.nugget_u238, 8);
		BlockCrate.addToListWithWeight(leadList, ModItems.nugget_plutonium, 7);
		// BlockCrate.addToListWithWeight(leadList, ModItems.nugget_pu238, 5);
		// BlockCrate.addToListWithWeight(leadList, ModItems.nugget_pu239, 4);
		BlockCrate.addToListWithWeight(leadList, ModItems.nugget_pu240, 6);
		BlockCrate.addToListWithWeight(leadList, ModItems.nugget_neptunium, 7);
		BlockCrate.addToListWithWeight(leadList, ModItems.nugget_uranium_fuel, 8);
		BlockCrate.addToListWithWeight(leadList, ModItems.nugget_plutonium_fuel, 7);
		BlockCrate.addToListWithWeight(leadList, ModItems.nugget_mox_fuel, 6);
		BlockCrate.addToListWithWeight(leadList, ModItems.cell_deuterium, 8);
		BlockCrate.addToListWithWeight(leadList, ModItems.cell_tritium, 8);
		BlockCrate.addToListWithWeight(leadList, ModItems.cell_uf6, 8);
		BlockCrate.addToListWithWeight(leadList, ModItems.cell_puf6, 8);
		BlockCrate.addToListWithWeight(leadList, ModItems.pellet_rtg, 6);
		BlockCrate.addToListWithWeight(leadList, ModItems.pellet_rtg_weak, 7);
		BlockCrate.addToListWithWeight(leadList, ModItems.tritium_deuterium_cake, 5);
		BlockCrate.addToListWithWeight(leadList, ModItems.powder_yellowcake, 10);

		// Metal Crate
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_press), 10);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 9);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_reactor_breeding), 6);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_nuke_furnace_off), 7);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_coal_off), 10);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_diesel), 8);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_selenium), 7);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_rtg_grey), 4);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.red_pylon), 9);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_battery), 8);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_lithium_battery), 5);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off), 8);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_assembler), 10);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_fluidtank), 7);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_drill), 4);
		BlockCrate.addToListWithWeight(metalList, ModItems.centrifuge_element, 6);
		BlockCrate.addToListWithWeight(metalList, ModItems.motor, 8);
		BlockCrate.addToListWithWeight(metalList, ModItems.coil_tungsten, 7);
		BlockCrate.addToListWithWeight(metalList, ModItems.photo_panel, 3);
		BlockCrate.addToListWithWeight(metalList, ModItems.coil_copper, 10);
		BlockCrate.addToListWithWeight(metalList, ModItems.tank_steel, 9);
		BlockCrate.addToListWithWeight(metalList, ModItems.blade_titanium, 3);
		BlockCrate.addToListWithWeight(metalList, ModItems.bolt_compound, 2);
		BlockCrate.addToListWithWeight(metalList, ModItems.piston_selenium, 6);

		// Red Crate
		BlockCrate.addToListWithWeight(redList, ModItems.mysteryshovel, 1);
		BlockCrate.addToListWithWeight(redList, ModItems.gun_revolver_pip, 1);
		BlockCrate.addToListWithWeight(redList, ModItems.gun_revolver_blackjack, 1);
		BlockCrate.addToListWithWeight(redList, ModItems.gun_revolver_silver, 1);
		BlockCrate.addToListWithWeight(redList, ModItems.ammo_44.stackFromEnum(Ammo44Magnum.PIP), 1);
		BlockCrate.addToListWithWeight(redList, ModItems.ammo_44.stackFromEnum(Ammo44Magnum.BJ), 1);
		BlockCrate.addToListWithWeight(redList, ModItems.ammo_44.stackFromEnum(Ammo44Magnum.SILVER), 1);
		BlockCrate.addToListWithWeight(redList, ModItems.battery_spark, 1);
		BlockCrate.addToListWithWeight(redList, ModItems.bottle_sparkle, 1);
		BlockCrate.addToListWithWeight(redList, ModItems.bottle_rad, 1);
		BlockCrate.addToListWithWeight(redList, ModItems.ring_starmetal, 1);
		BlockCrate.addToListWithWeight(redList, ModItems.flame_pony, 1);
		BlockCrate.addToListWithWeight(redList, Item.getItemFromBlock(ModBlocks.ntm_dirt), 1);
		BlockCrate.addToListWithWeight(redList, Item.getItemFromBlock(ModBlocks.broadcaster_pc), 1);
	}

	public void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();

		setDrops();

		List<ItemStack> list = new ArrayList();

		int i = rand.nextInt(3) + 3;

		if(this == ModBlocks.crate_weapon) {
			i = 1 + rand.nextInt(2);

			if(rand.nextInt(100) == 34)
				i = 25;
		}

		for(int j = 0; j < i; j++) {

			if(this == ModBlocks.crate)
				list.add(crateList.get(rand.nextInt(crateList.size())));
			if(this == ModBlocks.crate_weapon)
				list.add(weaponList.get(rand.nextInt(weaponList.size())));
			if(this == ModBlocks.crate_lead)
				list.add(leadList.get(rand.nextInt(leadList.size())));
			if(this == ModBlocks.crate_metal)
				list.add(metalList.get(rand.nextInt(metalList.size())));
			if(this == ModBlocks.crate_red)
				list.add(redList.get(rand.nextInt(redList.size())));
		}

		if(this == ModBlocks.crate_red) {
			list.clear();

			for(int k = 0; k < redList.size(); k++) {
				list.add(redList.get(k));
			}
		}

		for(ItemStack stack : list) {
			float f = rand.nextFloat() * 0.8F + 0.1F;
			float f1 = rand.nextFloat() * 0.8F + 0.1F;
			float f2 = rand.nextFloat() * 0.8F + 0.1F;

			EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, stack.copy());

			float f3 = 0.05F;
			entityitem.motionX = (float) rand.nextGaussian() * f3;
			entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
			entityitem.motionZ = (float) rand.nextGaussian() * f3;
			if(!world.isRemote)
				world.spawnEntityInWorld(entityitem);
		}
	}

	public static void addToListWithWeight(List<ItemStack> list, Item item, int weight) {
		for(int i = 0; i < weight; i++) list.add(new ItemStack(item));
	}

	public static void addToListWithWeight(List<ItemStack> list, ItemStack item, int weight) {
		for(int i = 0; i < weight; i++) list.add(item);
	}
}
