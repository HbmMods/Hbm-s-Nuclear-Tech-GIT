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
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

@Spaghetti("wtf is this shit")
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
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.drink;
	}

	@Spaghetti("cover yourself in oil")
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(VersatileConfig.hasPotionSickness(player)) return stack;
		if(this.requiresOpener && !player.inventory.hasItem(ModItems.bottle_opener)) return stack;

		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));

		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

		// Особые случаи с альтернативами
		if(this == ModItems.bottle_sparkle) {
			if(MainRegistry.polaroidID == 11)
				list.add(I18nUtil.resolveKey(this.getUnlocalizedName() + ".alt"));
			else
				list.add(I18nUtil.resolveKey(this.getUnlocalizedName() + ".desc"));
			return;
		}
		if(this == ModItems.bottle_rad) {
			if(MainRegistry.polaroidID == 11)
				list.add(I18nUtil.resolveKey(this.getUnlocalizedName() + ".alt"));
			else
				list.add(I18nUtil.resolveKey(this.getUnlocalizedName() + ".desc"));
			return;
		}

		if(this == ModItems.can_breen || this == ModItems.chocolate_milk) {
			for (String line : I18nUtil.resolveKeyArray(this.getUnlocalizedName() + ".desc")) {
				list.add(line);
			}
			return;
		}

		list.add(I18nUtil.resolveKey(this.getUnlocalizedName() + ".desc"));

		if(this.requiresOpener) {
			list.add(I18nUtil.resolveKey("item.bottle.desc"));
		}
	}
}
