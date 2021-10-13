package com.hbm.items.food;

import java.util.List;
import java.util.Random;

import com.hbm.config.VersatileConfig;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.interfaces.IHasLore;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemCustomLore;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
@Spaghetti("if statement chain")
public class ItemPill extends ItemFood implements IHasLore
{

	public ItemPill(int hunger) {
		super(hunger, false);
		this.setAlwaysEdible();
	}

	Random rand = new Random();

	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		if(!world.isRemote) {
			
			VersatileConfig.applyPotionSickness(player, 5);
			
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

			if(this == ModItems.xanax) {
				float digamma = HbmLivingProps.getDigamma(player);
				HbmLivingProps.setDigamma(player, Math.max(digamma - 0.5F, 0F));
			}

			if(this == ModItems.fmn) {
				float digamma = HbmLivingProps.getDigamma(player);
				HbmLivingProps.setDigamma(player, Math.min(digamma, 2F));
				player.addPotionEffect(new PotionEffect(Potion.blindness.id, 60, 0));
			}

			if(this == ModItems.five_htp) {
				HbmLivingProps.setDigamma(player, 0);
				player.addPotionEffect(new PotionEffect(HbmPotion.stability.id, 10 * 60 * 20, 0));
			}
		}
	}

			if(this == ModItems.pirfenidone) {
				float fibrosis = HbmLivingProps.getFibrosis(player);
				HbmLivingProps.setFibrosis(player, (int) Math.min(fibrosis, 37800));
			}
			if(this == ModItems.siox) {
				HbmLivingProps.setAsbestos(player, 0);
				HbmLivingProps.setBlackLung(player, Math.min(HbmLivingProps.getBlackLung(player), HbmLivingProps.maxBlacklung / 5));
			}
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		standardLore(itemstack, list);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 10;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {

		if(!VersatileConfig.hasPotionSickness(p_77659_3_))
			p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));

		return p_77659_1_;
	}

}
