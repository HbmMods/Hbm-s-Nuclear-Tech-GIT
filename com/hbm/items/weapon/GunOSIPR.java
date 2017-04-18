package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityCombineBall;
import com.hbm.entity.projectile.EntityMiniNuke;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunOSIPR extends Item {

	Random rand = new Random();

    public GunOSIPR()
    {
        this.maxStackSize = 1;
        this.setMaxDamage(2500);
    }

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

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

	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
		World world = player.worldObj;
		
		if (!player.isSneaking()) {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
			if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_osipr_ammo)) && count % 3 == 0) {
					EntityBullet entityarrow = new EntityBullet(world, player, 3.0F, 5, 15, false, "chopper");
				entityarrow.setDamage(5 + rand.nextInt(10));

				//world.playSoundAtEntity(player, "random.explode", 1.0F, 1.5F + (rand.nextFloat() / 4));
				world.playSoundAtEntity(player, "hbm:weapon.osiprShoot", 1.0F, 0.8F + (rand.nextFloat() * 0.4F));

				if (flag) {
					entityarrow.canBePickedUp = 2;
				} else {
					player.inventory.consumeInventoryItem(ModItems.gun_osipr_ammo);
				}
				
				if (!world.isRemote) {
					world.spawnEntityInWorld(entityarrow);
				}
			}
		} else {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
			if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_osipr_ammo2)) && count % 30 == 0 && (this.getMaxItemUseDuration(stack) - count) != 0) {
				EntityCombineBall entityarrow = new EntityCombineBall(player.worldObj, player, 3.0F);
				entityarrow.setDamage(35 + rand.nextInt(45 - 35));

				//world.playSoundAtEntity(player, "tile.piston.in", 1.0F, 0.75F);
				world.playSoundAtEntity(player, "hbm:weapon.singFlyby", 1.0F, 1F);

				if (flag) {
					entityarrow.canBePickedUp = 2;
				} else {
					player.inventory.consumeInventoryItem(ModItems.gun_osipr_ammo2);
				}
				
				if (!world.isRemote) {
					world.spawnEntityInWorld(entityarrow);
				}
			}
			
			if((this.getMaxItemUseDuration(stack) - count) % 30 == 15 && (player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_osipr_ammo2)))
				world.playSoundAtEntity(player, "hbm:weapon.osiprCharging", 1.0F, 1F);
		}
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("Hold right mouse button");
		list.add("to shoot,");
		list.add("sneak to shoot a");
		list.add("dark energy ball!");
		list.add("");
		list.add("Ammo: Dark Energy Plugs");
		list.add("Secondary Ammo: Combine Ball");
		list.add("Damage: 5 - 15");
		list.add("Secondary Damage: 1000");
	}

	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", (double) 5, 0));
		return multimap;
	}

}
