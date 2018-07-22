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
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunUZI extends Item {

	Random rand = new Random();

    public GunUZI()
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
		ArrowNockEvent event = new ArrowNockEvent(p_77659_3_, p_77659_1_);
		{
			p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		}

		return p_77659_1_;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
		World world = player.worldObj;

		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
		
		if (player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_uzi_ammo)) {
			
			EntityBullet entityarrow = new EntityBullet(world, player, 3.0F, 2, 4, false, false);
			entityarrow.setDamage(2 + rand.nextInt(3));

			if(this == ModItems.gun_uzi)
				world.playSoundAtEntity(player, "hbm:weapon.uziShoot", 10.0F, 1.0F);
			if(this == ModItems.gun_uzi_silencer)
				world.playSoundAtEntity(player, "hbm:weapon.silencerShoot", 0.15F, 1.0F);

			if (!flag) {
				player.inventory.consumeInventoryItem(ModItems.gun_uzi_ammo);
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

		if(this == ModItems.gun_uzi)
			list.add("[very intense pewpew-ing]");
		if(this == ModItems.gun_uzi_silencer)
			list.add("Mom, where are my mittens?");
		list.add("");
		list.add("Ammo: .22 LR Round");
		list.add("Damage: 2 - 4");
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", 2.5D, 0));
		return multimap;
	}
}
