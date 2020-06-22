package com.hbm.items.food;

import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemPill extends ItemFood {

	public ItemPill(int hunger) {
		super(hunger, false);
        this.setAlwaysEdible();
	}
	
	Random rand = new Random();

	@Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
        	if(this == ModItems.pill_iodine) {
        		player.removePotionEffect(Potion.blindness.id);
        		player.removePotionEffect(Potion.confusion.id);
        		player.removePotionEffect(Potion.digSlowdown.id);
        		player.removePotionEffect(Potion.hunger.id);
        		player.removePotionEffect(Potion.moveSlowdown.id);
        		player.removePotionEffect(Potion.poison.id);
        		player.removePotionEffect(Potion.weakness.id);
        		player.removePotionEffect(Potion.wither.id);
        		player.removePotionEffect(HbmPotion.radiation.id);
        	}

        	if(this == ModItems.plan_c) {
        		for(int i = 0; i < 10; i++)
        			player.attackEntityFrom(rand.nextBoolean() ? ModDamageSource.euthanizedSelf : ModDamageSource.euthanizedSelf2, 1000);
        	}

        	if(this == ModItems.radx) {
        		player.addPotionEffect(new PotionEffect(HbmPotion.radx.id, 3 * 60 * 20, 0));
        	}
        }
    }
    
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.pill_iodine) {
			list.add("Removes negative effects");
		}
		if(this == ModItems.plan_c) {
			list.add("Deadly");
		}
		if(this == ModItems.radx) {
			list.add("Increases radiation resistance by 0.4 for 3 minutes");
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 10;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		
		p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));

		return p_77659_1_;
	}

}
