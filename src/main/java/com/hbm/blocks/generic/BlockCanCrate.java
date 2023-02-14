package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockCanCrate extends Block {

	public BlockCanCrate(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
    
    public static int renderID = RenderingRegistry.getNextAvailableRenderId();
	
	@Override
	public int getRenderType(){
		return renderID;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
    	
		if(world.isRemote)
		{
			player.addChatMessage(new ChatComponentText("The one crate you are allowed to smash!"));
		}
    	
    	return true;
    }

    @Override
	public Item getItemDropped(int i, Random rand, int j) {
    	
    	List<Item> items = new ArrayList();
    	items.add(ModItems.canned_beef);
    	items.add(ModItems.canned_tuna);
    	items.add(ModItems.canned_mystery);
    	items.add(ModItems.canned_pashtet);
    	items.add(ModItems.canned_cheese);
    	items.add(ModItems.canned_jizz);
    	items.add(ModItems.canned_milk);
    	items.add(ModItems.canned_ass);
    	items.add(ModItems.canned_pizza);
    	items.add(ModItems.canned_tomato);
    	items.add(ModItems.canned_tube);
    	items.add(ModItems.canned_asbestos);
    	items.add(ModItems.canned_bhole);
    	items.add(ModItems.canned_hotdogs);
    	items.add(ModItems.canned_leftovers);
    	items.add(ModItems.canned_yogurt);
    	items.add(ModItems.canned_stew);
    	items.add(ModItems.canned_chinese);
    	items.add(ModItems.canned_oil);
    	items.add(ModItems.canned_fist);
    	items.add(ModItems.canned_spam);
    	items.add(ModItems.canned_fried);
    	items.add(ModItems.canned_napalm);
    	items.add(ModItems.canned_diesel);
    	items.add(ModItems.canned_kerosene);
    	items.add(ModItems.canned_recursion);
    	items.add(ModItems.canned_bark);
    	items.add(ModItems.can_smart);
    	items.add(ModItems.can_creature);
    	items.add(ModItems.can_redbomb);
    	items.add(ModItems.can_mrsugar);
    	items.add(ModItems.can_overcharge);
    	items.add(ModItems.can_luna);
    	items.add(ModItems.can_breen);
    	items.add(ModItems.can_bepis);
    	items.add(ModItems.pudding);
    	
        return items.get(rand.nextInt(items.size()));
    }
    
    @Override
	public int quantityDropped(Random rand) {
    	
    	return 5 + rand.nextInt(4);
    }

}
