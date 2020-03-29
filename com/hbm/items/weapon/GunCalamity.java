package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunCalamity extends Item {

	Random rand = new Random();

    public GunCalamity()
    {
        this.maxStackSize = 1;
    }

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		new ArrowNockEvent(p_77659_3_, p_77659_1_);
		{
			p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		}

		return p_77659_1_;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
		World world = player.worldObj;

		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

				if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_calamity_ammo)) && count % 6 == 0) {
					EntityBullet entityarrow = new EntityBullet(world, player, 3.0F, 15, 25, false, false);
					entityarrow.setDamage(15 + rand.nextInt(25));

					world.playSoundAtEntity(player, "hbm:weapon.calShoot", 1.0F, 1.0F);

					if (flag) {
						entityarrow.canBePickedUp = 2;
					} else {
						player.inventory.consumeInventoryItem(ModItems.ammo_50bmg);
					}

					if (!world.isRemote) {
						world.spawnEntityInWorld(entityarrow);
					}
				}
				
				if (this == ModItems.gun_calamity_dual && (player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_calamity_ammo)) && count % 6 == 3) {
					EntityBullet entityarrow = new EntityBullet(world, player, 3.0F, 15, 25, false, false);
					entityarrow.setDamage(15 + rand.nextInt(25));

					world.playSoundAtEntity(player, "hbm:weapon.calShoot", 1.0F, 0.7F);

					if (flag) {
						entityarrow.canBePickedUp = 2;
					} else {
						player.inventory.consumeInventoryItem(ModItems.ammo_50bmg);
					}

					if (!world.isRemote) {
						world.spawnEntityInWorld(entityarrow);
					}
				}
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if(this == ModItems.gun_calamity) {
			list.add("Handheld Maxim");
		}
		if(this == ModItems.gun_calamity_dual) {
			list.add("You may be thinking, 'This gun makes no");
			list.add("sense, why is there only one receiver for");
			list.add("two barrels, and how do the bullets even");
			list.add("come out of this thing? The barrels are");
			list.add("just taped onto a plate with no connection");
			list.add("to the rest of the gun!' Well my boy, this");
			list.add("question has a simple, easy to understand");
			list.add("answer, it's because " + EnumChatFormatting.OBFUSCATED + "gkjin soi unsi");
			list.add("and " + EnumChatFormatting.OBFUSCATED + "aslfnu isnfi uo fnafaoin fsj afakjkk abk");
		}
		list.add("");
		list.add("Ammo: .50 BMG Round");
		list.add("Damage: 15 - 25");
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", 3, 0));
		return multimap;
	}
}
