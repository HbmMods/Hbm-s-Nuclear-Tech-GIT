package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

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

public class GunDefabricator extends Item {

	Random rand = new Random();

    public GunDefabricator()
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
		if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_defabricator_ammo))
				&& count % 2 == 0) {
			EntityBullet entitybullet = new EntityBullet(world, player, 3.0F, 40, 120, false, "tauDay");
			entitybullet.setDamage(40 + rand.nextInt(120 - 40));

			//world.playSoundAtEntity(player, "random.explode", 1.0F, 1.5F + (rand.nextFloat() / 4));
			world.playSoundAtEntity(player, "hbm:weapon.defabShoot", 1.0F, 0.9F + (rand.nextFloat() * 0.2F));
			if(count == this.getMaxItemUseDuration(stack))
				world.playSoundAtEntity(player, "hbm:weapon.defabSpinup", 1.0F, 1.0F);

			if(count % 20 == 0 && !flag)
				player.inventory.consumeInventoryItem(ModItems.gun_defabricator_ammo);

			if (!world.isRemote) {
				world.spawnEntityInWorld(entitybullet);
			}
		}
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if(MainRegistry.polaroidID == 11)
			list.add("Did you set your alarm for volcano day?");
		else
			list.add("BAD WOLF");
		list.add("");
		list.add("Ammo: Defabricator Energy Cell");
		list.add("Damage: 40 - 120");
		list.add("");
		list.add("[LEGENDARY WEAPON]");
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", 6.5, 0));
		return multimap;
	}
}
