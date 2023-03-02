package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.items.food.ItemConserve.EnumFoodType;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
    
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
    	ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
    	
    	int count = quantityDropped(metadata, fortune, world.rand);
    	for(int i = 0; i < count; i++) {
    		Item item = getItemDropped(metadata, world.rand, fortune);
    		if(item != null)
    			ret.add(new ItemStack(item, 1, damageDropped(metadata, world.rand, item)));
    	}
    	
    	return ret;
    }
    
    //pain
    public int damageDropped(int meta, Random rand, Item item) {
    	if(item != ModItems.canned_conserve)
    		return damageDropped(meta);
    	else
    		return Math.abs(rand.nextInt() % EnumFoodType.values().length);
    }
    
    @Override
	public Item getItemDropped(int i, Random rand, int j) {
    	
    	List<Item> items = new ArrayList();
    	for(int a = 0; a < EnumFoodType.values().length; a++)
    		items.add(ModItems.canned_conserve);
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