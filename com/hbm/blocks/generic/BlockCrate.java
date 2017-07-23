package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    		world.playSoundEffect(x, y, z, "hbm:block.crateBreak", 1.0F, 1.0F);
    		return true;
    	} else {
			if(world.isRemote)
			{
				player.addChatMessage(new ChatComponentText("I'll need a crate opening device to get the loot, smashing the whole thing won't work..."));
			}
    	}
    	
    	return true;
    }
    
    public void dropItems(World world, int x, int y, int z) {
    	Random rand = new Random();
    	List<Item> list1 = new ArrayList<Item>();

    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.syringe_metal_stimpak, 10);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.syringe_antidote, 5);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_revolver_iron, 9);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_revolver, 7);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_revolver_gold, 4);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_revolver_lead, 6);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_revolver_schrabidium, 1);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_revolver_cursed, 5);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_revolver_nightmare, 3);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_revolver_nightmare2, 2);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_rpg, 5);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_fatman, 3);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_mirv, 1);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_bf, 0);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_mp40, 7);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_uboinik, 7);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_osipr, 7);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_immolator, 4);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_cryolator, 4);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_mp, 3);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_xvl1456, 5);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.clip_emp, 3);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.grenade_generic, 8);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.grenade_strong, 6);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.grenade_mk2, 4);
    	list1 = BlockCrate.addToListWithWeight(list1, ModItems.grenade_flare, 4);

    	List<Item> list = new ArrayList<Item>();
    	
    	int i = rand.nextInt(3) + 3;
    	for(int j = 0; j < i; j++)
    		list.add(list1.get(rand.nextInt(list1.size())));
    	
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
    
    public static List<Item> addToListWithWeight(List<Item> list, Item item, int weight) {
    	for(int i = 0; i < weight; i++)
    		list.add(item);
    	
    	return list;
    }
}
