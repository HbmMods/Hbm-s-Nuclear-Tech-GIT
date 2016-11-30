package com.hbm.items.food;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTemFlakes extends ItemFood {

	public ItemTemFlakes(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
        this.setHasSubtypes(true);
        this.setAlwaysEdible();
	}

    @Override
	protected void onFoodEaten(ItemStack p_77849_1_, World p_77849_2_, EntityPlayer p_77849_3_)
    {
    	p_77849_3_.heal(2F);
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 1));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 2));
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(itemstack.getItemDamage() == 0)
		{
			list.add("Heals 2HP DISCOUNT FOOD OF TEM!!!");
		}
		if(itemstack.getItemDamage() == 1)
		{
			list.add("Heals 2HP food of tem");
		}
		if(itemstack.getItemDamage() == 2)
		{
			list.add("Heals food of tem (expensiv)");
		}
	}

}
