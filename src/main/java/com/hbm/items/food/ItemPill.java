package com.hbm.items.food;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.config.VersatileConfig;
import com.hbm.extprop.HbmLivingProps;
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
			
			if(this == ModItems.siox) {
				HbmLivingProps.setAsbestos(player, 0);
				HbmLivingProps.setBlackLung(player, Math.min(HbmLivingProps.getBlackLung(player), HbmLivingProps.maxBlacklung / 5));
			}
			
			if(this == ModItems.pill_herbal) {
				float fibrosis = HbmLivingProps.getFibrosis(player);
				HbmLivingProps.setFibrosis(player, (int) Math.min(fibrosis, 37800));
				HbmLivingProps.setAsbestos(player, 0);
				HbmLivingProps.setBlackLung(player, Math.min(HbmLivingProps.getBlackLung(player), HbmLivingProps.maxBlacklung / 5));
				HbmLivingProps.incrementRadiation(player, -100F);
				
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, 10 * 20, 0));
				player.addPotionEffect(new PotionEffect(Potion.weakness.id, 10 * 60 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 10 * 60 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.poison.id, 5 * 20, 2));
				
				PotionEffect eff = new PotionEffect(HbmPotion.potionsickness.id, 10 * 60 * 20);
				eff.setCurativeItems(new ArrayList());
				player.addPotionEffect(eff);
			}

			if(this == ModItems.xanax) {
				float digamma = HbmLivingProps.getDigamma(player);
				HbmLivingProps.setDigamma(player, Math.max(digamma - 0.5F, 0F));
			}
			
			if(this == ModItems.chocolate) {
				if(rand.nextInt(25) == 0) {
					player.attackEntityFrom(ModDamageSource.overdose, 1000);
				}
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 60 * 20, 3));
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60 * 20, 3));
				player.addPotionEffect(new PotionEffect(Potion.jump.id, 60 * 20, 3));
			}

			if(this == ModItems.fmn) {
				float digamma = HbmLivingProps.getDigamma(player);
				HbmLivingProps.setDigamma(player, Math.min(digamma, 2F));
				player.addPotionEffect(new PotionEffect(Potion.blindness.id, 60, 0));
			}
			
			if(this == ModItems.pirfenidone) {
				float fibrosis = HbmLivingProps.getFibrosis(player);
				HbmLivingProps.setFibrosis(player, (int) Math.min(fibrosis, 37800));
			}

			if(this == ModItems.five_htp) {
				HbmLivingProps.setDigamma(player, 0);
				player.addPotionEffect(new PotionEffect(HbmPotion.stability.id, 10 * 60 * 20, 0));
			}
		}
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if(this == ModItems.pill_iodine) {
			list.add("Removes negative effects");
		}
		if(this == ModItems.plan_c) {
			list.add("Deadly");
		}
		if(this == ModItems.radx) {
			list.add("Increases radiation resistance by 0.2 (37%) for 3 minutes");
		}
		if(this == ModItems.siox) {
			list.add("Reverses mesothelioma with the power of Asbestos!");
		}
		if(this == ModItems.pill_herbal) {
			list.add("Effective treatment against lung disease and mild radiation poisoning");
			list.add("Comes with side effects");
		}
		if(this == ModItems.xanax) {
			list.add("Removes 500mDRX");
		}
		if(this == ModItems.fmn) {
			list.add("Removes all DRX above 2,000mDRX");
		}
		if(this == ModItems.chocolate) {
			list.add("Radium Chocolate? Pretty sure this is just meth.");
		}
		if(this == ModItems.pirfenidone) {
			list.add("Removes all Pulmonary Fibrosis over 35%");
		}
		if(this == ModItems.five_htp) {
			list.add("Removes all DRX, Stability for 10 minutes");
		}
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
