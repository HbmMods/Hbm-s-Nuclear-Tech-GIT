package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunXVL1456 extends Item {

	Random rand = new Random();

    public GunXVL1456()
    {
        this.maxStackSize = 1;
        this.setMaxDamage(2500);
    }

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int i) {

		int j = this.getMaxItemUseDuration(stack) - i;
		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, j);
		MinecraftForge.EVENT_BUS.post(event);
		// if (event.isCanceled()) {
		// return;
		// }
        j = event.charge * 2;

		if (player.isSneaking() && j >= 20) {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

			if (flag || player.inventory.hasItem(ModItems.gun_xvl1456_ammo)) {
				EntityBullet entitybullet = new EntityBullet(world, player, 3.0F, j, j + 5, false, "tauDay");

				entitybullet.setDamage(j + rand.nextInt(6));

				world.playSoundAtEntity(player, "hbm:weapon.tauShoot", 1.0F, 0.5F);

				if (flag) {
					entitybullet.canBePickedUp = 2;
				} else {
					player.inventory.consumeInventoryItem(ModItems.gun_xvl1456_ammo);
				}

				entitybullet.setIsCritical(true);

				if (!world.isRemote) {
					world.spawnEntityInWorld(entitybullet);
				}
				stack.damageItem((int)(j * 0.05F), player);
				
				player.rotationPitch -= (j * 0.1F);
			}
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		ArrowNockEvent event = new ArrowNockEvent(p_77659_3_, p_77659_1_);
		MinecraftForge.EVENT_BUS.post(event);
		// if (event.isCanceled()) {
		// return event.result;
		// }
		// Made uncancelable to prevent intermod idiocy
		{
			p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
			}

		return p_77659_1_;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
		World world = player.worldObj;
		
		if (!player.isSneaking()) {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
			if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_xvl1456_ammo)) && count % 4 == 0) {

				EntityBullet entityarrow = new EntityBullet(world, player, 3.0F, 25, 65, false, "eyyOk");
				entityarrow.setDamage(25 + rand.nextInt(65 - 25));

				world.playSoundAtEntity(player, "hbm:weapon.tauShoot", 1.0F, 0.8F + (rand.nextFloat() * 0.4F));

				if (flag) {
					entityarrow.canBePickedUp = 2;
				} else {
					player.inventory.consumeInventoryItem(ModItems.gun_xvl1456_ammo);
				}
				
				if (!world.isRemote) {
					world.spawnEntityInWorld(entityarrow);
				}
			}
		} else {
			if (count % 20 == 0 && this.getMaxItemUseDuration(stack) - count != 0) {
				boolean flag = player.capabilities.isCreativeMode
						|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
				if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_xvl1456_ammo))) {
					if (!flag) {
						player.inventory.consumeInventoryItem(ModItems.gun_xvl1456_ammo);
					}
				}
			}
			
			world.playSoundAtEntity(player, "hbm:misc.nullTau", 0.1F, 1.0F);
		}
		
		if(player instanceof EntityPlayer)
		{
			if(count < getMaxItemUseDuration(stack) - 200 && player.isSneaking() && count != 0)
			{
				if(!world.isRemote)
				{
					stack.damageItem(1250, player);
					
					world.createExplosion(player, player.posX, player.posY, player.posZ, 10.0F, true);
					player.attackEntityFrom(ModDamageSource.tauBlast, 1000F);
					player.dropOneItem(false);
				}
			}
		}
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("Hold right mouse button");
		list.add("to shoot tauons,");
		list.add("sneak to charge up for");
		list.add("stronger shots!");
		list.add("");
		list.add("Ammo: Depleted Uranium");
		list.add("Damage: 25 - 65");
		list.add("Charged Damage: 40 - 400");
		list.add("Projectiles penetrate walls.");
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", 6, 0));
		return multimap;
	}

}
