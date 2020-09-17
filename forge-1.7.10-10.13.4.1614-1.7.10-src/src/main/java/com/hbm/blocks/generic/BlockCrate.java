package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
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

	List<Item> crateList;
	List<Item> weaponList;
	List<Item> leadList;
	List<Item> metalList;
	List<Item> redList;

	public BlockCrate(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

    @Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return null;
    }

    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
    	if(player.getHeldItem() != null && player.getHeldItem().getItem().equals(ModItems.crowbar))
    	{
    		dropItems(world, x, y, z);
    		world.setBlockToAir(x, y, z);
    		world.playSoundEffect(x, y, z, "hbm:block.crateBreak", 0.5F, 1.0F);
    		return true;
    	} else {
			if(world.isRemote)
			{
				player.addChatMessage(new ChatComponentText("I'll need a crate opening device to get the loot, smashing the whole thing won't work..."));
			}
    	}
    	
    	return true;
    }
    
    public void setDrops() {

    	crateList = new ArrayList<Item>();
    	weaponList = new ArrayList<Item>();
    	leadList = new ArrayList<Item>();
    	metalList = new ArrayList<Item>();
    	redList = new ArrayList<Item>();

		//Supply Crate
    	BlockCrate.addToListWithWeight(crateList, ModItems.syringe_metal_stimpak, 10);
    	BlockCrate.addToListWithWeight(crateList, ModItems.syringe_antidote, 5);
    	BlockCrate.addToListWithWeight(crateList, ModItems.clip_revolver_iron, 9);
    	BlockCrate.addToListWithWeight(crateList, ModItems.clip_revolver, 7);
    	BlockCrate.addToListWithWeight(crateList, ModItems.clip_revolver_gold, 4);
    	BlockCrate.addToListWithWeight(crateList, ModItems.clip_revolver_lead, 6);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_revolver_schrabidium, 1);
    	BlockCrate.addToListWithWeight(crateList, ModItems.clip_revolver_cursed, 5);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_revolver_nightmare, 3);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_revolver_nightmare2, 2);
    	BlockCrate.addToListWithWeight(crateList, ModItems.clip_rpg, 5);
    	BlockCrate.addToListWithWeight(crateList, ModItems.clip_fatman, 1);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_mirv, 2);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_bf, 1);
    	BlockCrate.addToListWithWeight(crateList, ModItems.clip_mp40, 7);
    	BlockCrate.addToListWithWeight(crateList, ModItems.clip_uzi, 7);
    	BlockCrate.addToListWithWeight(crateList, ModItems.clip_uboinik, 7);
    	BlockCrate.addToListWithWeight(crateList, ModItems.clip_lever_action, 5);
    	BlockCrate.addToListWithWeight(crateList, ModItems.clip_bolt_action, 5);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_osipr, 7);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_immolator, 4);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_cryolator, 4);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_mp, 3);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_xvl1456, 5);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_emp, 3);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_defabricator, 3);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_euthanasia, 2);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_hp, 2);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_jack, 2);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_revolver_pip, 3);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_spark, 2);
    	//BlockCrate.addToListWithWeight(crateList, ModItems.clip_stinger, 5);
    	BlockCrate.addToListWithWeight(crateList, ModItems.grenade_generic, 8);
    	BlockCrate.addToListWithWeight(crateList, ModItems.grenade_strong, 6);
    	BlockCrate.addToListWithWeight(crateList, ModItems.grenade_mk2, 4);
    	BlockCrate.addToListWithWeight(crateList, ModItems.grenade_flare, 4);
    	BlockCrate.addToListWithWeight(crateList, ModItems.ammo_container, 2);
    	
    	//Weapon Crate
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_revolver_iron, 10);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_revolver, 9);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_revolver_gold, 7);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_revolver_lead, 8);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_revolver_schrabidium, 1);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_revolver_cursed, 7);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_revolver_nightmare, 5);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_revolver_nightmare2, 4);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_revolver_pip, 3);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_calamity, 3);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_calamity_dual, 2);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_rpg, 7);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_karl, 4);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_panzerschreck, 6);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_hk69, 8);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_stinger, 7);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_fatman, 5);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_proto, 3);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_mirv, 3);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_bf, 1);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_mp40, 9);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_uzi, 6);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_uzi_silencer, 5);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_uzi_saturnite, 4);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_uzi_saturnite_silencer, 3);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_uboinik, 8);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_lever_action, 7);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_lever_action_dark, 6);
    	BlockCrate.addToListWithWeight(weaponList, ModItems.gun_bolt_action, 7);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_bolt_action_green, 6);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_xvl1456, 4);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_osipr, 6);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_immolator, 5);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_cryolator, 5);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_mp, 2);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_emp, 3);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_jack, 3);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_spark, 3);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_hp, 3);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_euthanasia, 3);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_skystinger, 3);
    	//BlockCrate.addToListWithWeight(weaponList, ModItems.gun_defabricator, 2);
    	
    	//Lead Crate
    	BlockCrate.addToListWithWeight(leadList, ModItems.ingot_uranium, 10);
    	//BlockCrate.addToListWithWeight(leadList, ModItems.ingot_u235, 5);
    	BlockCrate.addToListWithWeight(leadList, ModItems.ingot_u238, 8);
    	BlockCrate.addToListWithWeight(leadList, ModItems.ingot_plutonium, 7);
    	//BlockCrate.addToListWithWeight(leadList, ModItems.ingot_pu238, 5);
    	//BlockCrate.addToListWithWeight(leadList, ModItems.ingot_pu239, 4);
    	BlockCrate.addToListWithWeight(leadList, ModItems.ingot_pu240, 6);
    	BlockCrate.addToListWithWeight(leadList, ModItems.ingot_neptunium, 7);
    	BlockCrate.addToListWithWeight(leadList, ModItems.ingot_uranium_fuel, 8);
    	BlockCrate.addToListWithWeight(leadList, ModItems.ingot_plutonium_fuel, 7);
    	BlockCrate.addToListWithWeight(leadList, ModItems.ingot_mox_fuel, 6);
    	BlockCrate.addToListWithWeight(leadList, ModItems.nugget_uranium, 10);
    	//BlockCrate.addToListWithWeight(leadList, ModItems.nugget_u235, 5);
    	BlockCrate.addToListWithWeight(leadList, ModItems.nugget_u238, 8);
    	BlockCrate.addToListWithWeight(leadList, ModItems.nugget_plutonium, 7);
    	//BlockCrate.addToListWithWeight(leadList, ModItems.nugget_pu238, 5);
    	//BlockCrate.addToListWithWeight(leadList, ModItems.nugget_pu239, 4);
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
    	//BlockCrate.addToListWithWeight(leadList, ModItems.nugget_schrabidium, 1);
    	
    	//Metal Crate
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_press), 10);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 9);
    	//BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_centrifuge), 5);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_reactor), 6);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_nuke_furnace_off), 7);
    	//BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_rtg_furnace_off), 5);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_coal_off), 10);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_diesel), 8);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_selenium), 7);
    	//BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_industrial_generator), 6);
    	//BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_radgen), 5);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_rtg_grey), 4);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.red_pylon), 9);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_battery), 8);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_lithium_battery), 5);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off), 8);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_assembler), 10);
    	//BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_chemplant), 7);
    	//BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_reactor_small), 4);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_fluidtank), 7);
    	//BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_shredder), 8);
    	//BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_well), 6);
    	//BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_pumpjack), 5);
    	//BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_flare), 7);
    	//BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_refinery), 5);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_drill), 4);
    	//BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_turbofan), 4);
    	BlockCrate.addToListWithWeight(metalList, ModItems.centrifuge_element, 6);
    	BlockCrate.addToListWithWeight(metalList, ModItems.motor, 8);
    	BlockCrate.addToListWithWeight(metalList, ModItems.coil_tungsten, 7);
    	//BlockCrate.addToListWithWeight(metalList, ModItems.rtg_unit, 4);
    	BlockCrate.addToListWithWeight(metalList, ModItems.photo_panel, 3);
    	BlockCrate.addToListWithWeight(metalList, ModItems.coil_copper, 10);
    	BlockCrate.addToListWithWeight(metalList, ModItems.tank_steel, 9);
    	BlockCrate.addToListWithWeight(metalList, ModItems.blade_titanium, 3);
    	BlockCrate.addToListWithWeight(metalList, ModItems.bolt_compound, 2);
    	BlockCrate.addToListWithWeight(metalList, ModItems.piston_selenium, 6);
    	
    	//Red Crate
    	BlockCrate.addToListWithWeight(redList, ModItems.mysteryshovel, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.gun_revolver_pip, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.gun_revolver_blackjack, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.clip_revolver_pip, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.gun_calamity_dual, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.gun_b92, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.weaponized_starblaster_cell, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.battery_spark, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.bottle_sparkle, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.bottle_rad, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.missile_taint, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.flame_pony, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.burnt_bark, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.crystal_horn, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.crystal_charred, 1);
    	BlockCrate.addToListWithWeight(redList, Item.getItemFromBlock(ModBlocks.ntm_dirt), 1);
    	BlockCrate.addToListWithWeight(redList, Item.getItemFromBlock(ModBlocks.broadcaster_pc), 1);
    }
    
    public void dropItems(World world, int x, int y, int z) {
    	Random rand = new Random();
    	
    	setDrops();

    	List<Item> list = new ArrayList<Item>();
    	
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
    	
    	for(Item stack : list) {
            float f = rand.nextFloat() * 0.8F + 0.1F;
            float f1 = rand.nextFloat() * 0.8F + 0.1F;
            float f2 = rand.nextFloat() * 0.8F + 0.1F;
        	
            EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(stack));

            float f3 = 0.05F;
            entityitem.motionX = (float)rand.nextGaussian() * f3;
            entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
            entityitem.motionZ = (float)rand.nextGaussian() * f3;
            if(!world.isRemote)
            	world.spawnEntityInWorld(entityitem);
    	}
    }
    
    public static void addToListWithWeight(List<Item> list, Item item, int weight) {
    	for(int i = 0; i < weight; i++)
    		list.add(item);
    }
}
