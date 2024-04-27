package com.hbm.items.food;

import java.util.List;

import com.hbm.config.VersatileConfig;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class ItemEnergy extends Item {
	
	private Item container = null;
	private Item cap = null;
	private boolean requiresOpener = false;
	
	public ItemEnergy() {
		this.setCreativeTab(MainRegistry.consumableTab);
	}
	
	
	public ItemEnergy makeCan() {
		this.container = ModItems.can_empty;
		this.cap = ModItems.ring_pull;
		this.requiresOpener = false;
		this.setContainerItem(this.container);
		return this;
	}
	 
	public ItemEnergy makeBottle(Item bottle, Item cap) {
		this.container = bottle;
		this.cap = cap;
		this.requiresOpener = true;
		this.setContainerItem(this.container);
		this.setCreativeTab(MainRegistry.consumableTab);
		return this;
	}
	
	public ItemEnergy makeGlass() {
		this.requiresOpener = false;
		return this;
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {

		if(!player.capabilities.isCreativeMode) {
			--stack.stackSize;
		}

		if(!world.isRemote) {

			if(player instanceof FakePlayer) {
				world.newExplosion(player, player.posX, player.posY, player.posZ, 5F, true, true);
				return super.onEaten(stack, world, player);
			}
			
			VersatileConfig.applyPotionSickness(player, 5);

			if(this == ModItems.can_smart) {
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 0));
			}
			if(this == ModItems.can_creature) {
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 30 * 20, 1));
			}
			if(this == ModItems.can_redbomb) {
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
				player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 30 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.jump.id, 30 * 20, 1));
			}
			if(this == ModItems.can_mrsugar) {
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 30 * 20, 1));
				player.addPotionEffect(new PotionEffect(Potion.jump.id, 30 * 20, 2));
			}
			if(this == ModItems.can_overcharge) {
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 0));
			}
			if(this == ModItems.can_luna) {
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 1));
				player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 30 * 20, 2));
				System.out.println(this.container);

			}
			if(this == ModItems.can_bepis) {
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 3));
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 3));
			}
			if(this == ModItems.can_breen) {
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, 30 * 20, 0));
			}
			if(this == ModItems.can_mug) {
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 3 * 60 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 60 * 20, 2));
			}
			if(this == ModItems.chocolate_milk) {
				ExplosionLarge.explode(world, player.posX, player.posY, player.posZ, 50, true, false, false);
			}
			if(this == ModItems.bottle_nuka) {
				player.heal(4F);
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 30 * 20, 1));
				ContaminationUtil.contaminate(player, HazardType.RADIATION, ContaminationType.RAD_BYPASS, 5.0F);
			}
			if(this == ModItems.bottle_cherry) {
				player.heal(6F);
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
				player.addPotionEffect(new PotionEffect(Potion.jump.id, 30 * 20, 2));
				ContaminationUtil.contaminate(player, HazardType.RADIATION, ContaminationType.RAD_BYPASS, 5.0F);
			}
			if(this == ModItems.glass_smilk) {
				player.heal(6F); //ideas welcome pls thanks
				this.setContainerItem(ModItems.glass_empty);
				this.container = ModItems.glass_empty;
				//System.out.println(this.container);
			}
			if(this == ModItems.teacup) {
				player.heal(3F); 				
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 4));

				this.setContainerItem(ModItems.teacup_empty);
				this.container = ModItems.teacup_empty;
			}
			if(this == ModItems.bottle_honey) {
				player.heal(9F);  //sweet sorrow
				float digamma = HbmLivingProps.getDigamma(player);
				HbmLivingProps.setDigamma(player, Math.max(digamma - 0.3F, 0F));
				
				this.setContainerItem(Items.glass_bottle);
				this.container = Items.glass_bottle;
			}
			if(this == ModItems.bottle_quantum) {
				player.heal(10F);
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 1));
				ContaminationUtil.contaminate(player, HazardType.RADIATION, ContaminationType.RAD_BYPASS, 15.0F);
			}
			if(this == ModItems.bottle2_korl) {
				player.heal(6);
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 30 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 2));
			}
			if(this == ModItems.bottle2_fritz) {
				player.heal(6);
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.jump.id, 30 * 20, 2));
			}
			if(this == ModItems.bottle2_korl_special) {
				player.heal(16);
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 120 * 20, 1));
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 120 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 120 * 20, 2));
			}
			if(this == ModItems.bottle2_fritz_special) {
				player.heal(16);
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 120 * 20, 1));
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 120 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.jump.id, 120 * 20, 2));
			}
			if(this == ModItems.bottle_sparkle) {
				player.heal(10F);
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 120 * 20, 1));
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 120 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 120 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 120 * 20, 1));
				ContaminationUtil.contaminate(player, HazardType.RADIATION, ContaminationType.RAD_BYPASS, 5.0F);
			}
			if(this == ModItems.bottle_rad) {
				player.heal(10F);
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 120 * 20, 1));
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 120 * 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 120 * 20, 0));
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 120 * 20, 4));
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 120 * 20, 1));
				ContaminationUtil.contaminate(player, HazardType.RADIATION, ContaminationType.RAD_BYPASS, 15.0F);
			}
			if(this == ModItems.coffee) {
				player.heal(10);
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60 * 20, 2));
				this.setContainerItem(ModItems.cmug_empty);
				this.container = ModItems.cmug_empty;
				System.out.println(this.container);
			}
			if(this == ModItems.coffee_radium) {
				player.heal(10);
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60 * 20, 2));
				HbmLivingProps.incrementRadiation(player, 500F);
				player.triggerAchievement(MainRegistry.achRadium);
			}

			if(!player.capabilities.isCreativeMode) {

				if(this.cap != null) {
					player.inventory.addItemStackToInventory(new ItemStack(this.cap));
				}
				if(this.container != null) {
					if(stack.stackSize <= 0) {
						return new ItemStack(this.container);
					}
					player.inventory.addItemStackToInventory(new ItemStack(this.container));
				}
			}
			
			player.inventoryContainer.detectAndSendChanges();
		}

		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.drink;
	}

	@Spaghetti("cover yourself in oil")
	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {

		if(VersatileConfig.hasPotionSickness(p_77659_3_))
			return p_77659_1_;
		
			if(this.requiresOpener && !p_77659_3_.inventory.hasItem(ModItems.bottle_opener))
				return p_77659_1_;

		p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));

		return p_77659_1_;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		if(this == ModItems.can_smart) {
			list.add("Cheap and full of bubbles");
		}
		if(this == ModItems.can_creature) {
			list.add("Basically gasoline in a tin can");
		}
		if(this == ModItems.can_redbomb) {
			list.add("Liquefied explosives");
		}
		if(this == ModItems.can_mrsugar) {
			list.add("An intellectual drink, for the chosen ones!");
		}
		if(this == ModItems.can_overcharge) {
			list.add("Possible side effects include heart attacks, seizures or zombification");
		}
		if(this == ModItems.can_luna) {
			list.add("Contains actual selenium and star metal. Tastes like night.");
		}
		if(this == ModItems.can_bepis) {
			list.add("beppp");
		}
		if(this == ModItems.can_breen) {
			list.add("Don't drink the water. They put something in it, to make you forget.");
			list.add("I don't even know how I got here.");
		}
		if(this == ModItems.chocolate_milk) {
			list.add("Regular chocolate milk. Safe to drink.");
			list.add("Totally not made from nitroglycerine.");
		}
		if(this == ModItems.bottle_nuka) {
			list.add("Contains about 210 kcal and 1500 mSv.");
			list.add("[Requires bottle opener]");
		}
		if(this == ModItems.bottle_cherry) {
			list.add("Now with severe radiation poisoning in every seventh bottle!");
			list.add("[Requires bottle opener]");
		}
		if(this == ModItems.bottle_quantum) {
			list.add("Comes with a colorful mix of over 70 isotopes!");
			list.add("[Requires bottle opener]");
		}
		if(this == ModItems.bottle2_korl) {
			list.add("Contains actual orange juice!");
			list.add("[Requires bottle opener]");
		}
		if(this == ModItems.bottle2_fritz) {
			list.add("moremore caffeine");
			list.add("[Requires bottle opener]");
		}
		if(this == ModItems.bottle2_korl_special) {
			if(MainRegistry.polaroidID == 11)
				list.add("shgehgev u rguer");
			else
				list.add("Contains actual orange juice!");
			list.add("[Requires bottle opener]");
		}
		if(this == ModItems.bottle2_fritz_special) {
			if(MainRegistry.polaroidID == 11)
				list.add("ygrogr fgrof bf");
			else
				list.add("moremore caffeine");
			list.add("[Requires bottle opener]");
		}
		if(this == ModItems.bottle_sparkle) {
			if(MainRegistry.polaroidID == 11)
				list.add("Contains trace amounts of taint.");
			else
				list.add("The most delicious beverage in the wasteland!");
			list.add("[Requires bottle opener]");
		}
		if(this == ModItems.bottle_rad) {
			if(MainRegistry.polaroidID == 11)
				list.add("Now with 400% more radiation!");
			else
				list.add("Tastes like radish and radiation.");
			list.add("[Requires bottle opener]");
		}
	}
}
