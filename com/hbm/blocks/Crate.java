package com.hbm.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class Crate extends Block {

	protected Crate(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return null;
    }


    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
    	if(player.getHeldItem() != null && player.getHeldItem().getItem().equals(ModItems.crowbar))
    	{
    		dropItems(world, x, y, z);
    		world.setBlockToAir(x, y, z);
    		return true;
    	} else {
			if(world.isRemote)
			{
				player.addChatMessage(new ChatComponentText("I'll need a crate opening device to get the loot, smashing the whole thing won't work..."));
			}
    	}
    	
    	return false;
    }
    
    public void dropItems(World world, int x, int y, int z) {
    	Random rand = new Random();
    	List<Item> list = new ArrayList<Item>();
    	
    	switch(rand.nextInt(23)) {
    	case 0:
    		list.add(ModItems.clip_revolver_iron);
    		list.add(ModItems.clip_revolver_iron);
    		break;
    	case 1:
    		list.add(ModItems.clip_revolver_iron);
    		list.add(ModItems.syringe_metal_stimpak);
    		list.add(ModItems.syringe_metal_stimpak);
    		break;
    	case 2:
    		list.add(ModItems.clip_revolver);
    		list.add(ModItems.syringe_metal_stimpak);
    		break;
    	case 3:
    		list.add(ModItems.clip_osipr);
    		list.add(ModItems.syringe_metal_stimpak);
    		break;
    	case 4:
    		list.add(ModItems.clip_osipr);
    		list.add(ModItems.clip_osipr);
    		list.add(ModItems.syringe_metal_stimpak);
    		break;
    	case 5:
    		list.add(ModItems.clip_revolver_iron);
    		list.add(ModItems.clip_revolver);
    		break;
    	case 6:
    		list.add(ModItems.clip_revolver_iron);
    		list.add(ModItems.clip_revolver_iron);
    		list.add(ModItems.clip_revolver);
    		break;
    	case 7:
    		list.add(ModItems.clip_revolver_iron);
    		list.add(ModItems.clip_osipr);
    		break;
    	case 8:
    		list.add(ModItems.clip_revolver_gold);
    		list.add(ModItems.syringe_metal_stimpak);
    		break;
    	case 9:
    		list.add(ModItems.clip_revolver_gold);
    		list.add(ModItems.clip_revolver_gold);
    		list.add(ModItems.syringe_metal_stimpak);
    		break;
    	case 10:
    		list.add(ModItems.clip_revolver_schrabidium);
    		break;
    	case 11:
    		list.add(ModItems.clip_xvl1456);
    		break;
    	case 12:
    		list.add(ModItems.clip_xvl1456);
    		list.add(ModItems.clip_xvl1456);
    		break;
    	case 13:
    		list.add(ModItems.clip_xvl1456);
    		list.add(ModItems.syringe_metal_stimpak);
    		break;
    	case 14:
    		list.add(ModItems.clip_osipr);
    		list.add(ModItems.clip_osipr);
    		list.add(ModItems.syringe_metal_stimpak);
    		break;
    	case 15:
    		list.add(ModItems.clip_osipr);
    		list.add(ModItems.syringe_metal_stimpak);
    		list.add(ModItems.syringe_metal_stimpak);
    		break;
    	case 16:
    		list.add(ModItems.clip_revolver_gold);
    		list.add(ModItems.clip_revolver_gold);
    		break;
    	case 17:
    		list.add(ModItems.clip_rpg);
    		list.add(ModItems.clip_rpg);
    		break;
    	case 18:
    		list.add(ModItems.clip_rpg);
    		list.add(ModItems.syringe_metal_stimpak);
    		break;
    	case 19:
    		list.add(ModItems.clip_rpg);
    		list.add(ModItems.clip_revolver_iron);
    		list.add(ModItems.syringe_metal_stimpak);
    	case 20:
    		list.add(ModItems.clip_rpg);
    		list.add(ModItems.clip_revolver);
    		list.add(ModItems.syringe_metal_stimpak);
    		break;
    	case 21:
    		list.add(ModItems.syringe_metal_stimpak);
    		list.add(ModItems.syringe_metal_stimpak);
    		break;
    	case 22:
    		list.add(ModItems.syringe_metal_stimpak);
    		list.add(ModItems.syringe_metal_stimpak);
    		list.add(ModItems.syringe_metal_stimpak);
    		break;
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
}
