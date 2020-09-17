package com.hbm.items.tool;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.items.ModItems;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;

public class ItemMultitoolTool extends ItemTool {


	public ItemMultitoolTool(float f, ToolMaterial mat, Set set) {
		super(f, mat, set);
	}
    
    public static Set getAllBlocks() {
    	
    	Set all = new HashSet();
    	
    	for(Object b : GameData.getBlockRegistry()) {
    		all.add(b);
    	}
    	
    	return all;
    }

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		if(player.isSneaking()) {
			
	        world.playSoundAtEntity(player, "hbm:item.techBoop", 2.0F, 1.0F);
	        
			if(this == ModItems.multitool_dig) {
				ItemStack item = new ItemStack(ModItems.multitool_silk, 1, stack.getItemDamage());
				item.addEnchantment(Enchantment.silkTouch, 3);
				return item;
			} else if (this == ModItems.multitool_silk) {
				ItemStack item = new ItemStack(ModItems.multitool_ext, 1, stack.getItemDamage());
				item.addEnchantment(Enchantment.fireAspect, 3);
				return item;
			}
		}
		
		return stack;
	}
	
    @Override
	public boolean func_150897_b(Block p_150897_1_) {
    	return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return false;
    }
    
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.multitool_dig) {
			list.add("Breaks blocks extremely fast");
			list.add("Extra drops for ores");
		}
		if(this == ModItems.multitool_silk) {
			list.add("Breaks blocks extremely fast");
			list.add("Ores will drop themselves via silk touch");
		}
	}

}
