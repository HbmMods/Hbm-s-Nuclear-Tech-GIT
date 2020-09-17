package com.hbm.items.food;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemAppleEuphemium extends ItemFood {

	public ItemAppleEuphemium(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
        this.setAlwaysEdible();
        this.setCreativeTab(null);
	}

    @Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack p_77636_1_)
    {
        return true;
    }

    @Override
	protected void onFoodEaten(ItemStack p_77849_1_, World p_77849_2_, EntityPlayer p_77849_3_)
    {
        if (!p_77849_2_.isRemote)
        {
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 2147483647, 120));
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 2147483647, 0));
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 2147483647, 120));
        }
    }
    
    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
    {
		return EnumRarity.epic;
    }

}
