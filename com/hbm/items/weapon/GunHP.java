package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityPlasmaBeam;
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

public class GunHP extends Item {

	Random rand = new Random();

	public GunHP() {
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
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		World world = player.worldObj;
		
		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
		if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_hp_ammo))) {

			EntityPlasmaBeam plasma = new EntityPlasmaBeam(world, player, 1F);
			EntityPlasmaBeam plasma1 = new EntityPlasmaBeam(world, player, 1F);
			EntityPlasmaBeam plasma2 = new EntityPlasmaBeam(world, player, 1F);
			EntityPlasmaBeam plasma3 = new EntityPlasmaBeam(world, player, 1F);
			EntityPlasmaBeam plasma4 = new EntityPlasmaBeam(world, player, 1F);
			plasma1.motionX *= (0.75 + (rand.nextDouble() * 0.5));
			plasma1.motionY *= (0.75 + (rand.nextDouble() * 0.5));
			plasma1.motionZ *= (0.75 + (rand.nextDouble() * 0.5));
			plasma2.motionX *= (0.75 + (rand.nextDouble() * 0.5));
			plasma2.motionY *= (0.75 + (rand.nextDouble() * 0.5));
			plasma2.motionZ *= (0.75 + (rand.nextDouble() * 0.5));
			plasma3.motionX *= (0.75 + (rand.nextDouble() * 0.5));
			plasma3.motionY *= (0.75 + (rand.nextDouble() * 0.5));
			plasma3.motionZ *= (0.75 + (rand.nextDouble() * 0.5));
			plasma4.motionX *= (0.75 + (rand.nextDouble() * 0.5));
			plasma4.motionY *= (0.75 + (rand.nextDouble() * 0.5));
			plasma4.motionZ *= (0.75 + (rand.nextDouble() * 0.5));

			if (flag) {
				plasma.canBePickedUp = 2;
			} else {
				if (count % 20 == 0)
					player.inventory.consumeInventoryItem(ModItems.gun_hp_ammo);
			}

			if (count == this.getMaxItemUseDuration(stack))
				world.playSoundAtEntity(player, "hbm:weapon.immolatorIgnite", 1.0F, 1F);
			if (count % 10 == 0)
				world.playSoundAtEntity(player, "hbm:weapon.immolatorShoot", 1.0F, 1F);

			if (!world.isRemote) {
				world.spawnEntityInWorld(plasma);
				world.spawnEntityInWorld(plasma1);
				world.spawnEntityInWorld(plasma2);
				world.spawnEntityInWorld(plasma3);
				world.spawnEntityInWorld(plasma4);
			}
		}
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("Rrrrt - rrrrt - rrrrt, weeee!");
		list.add("");
		list.add("Ammo: Ink Cartridge");
		list.add("Damage: 25 - 45");
		list.add("");
		list.add("[LEGENDARY WEAPON]");
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", 4, 0));
		return multimap;
	}
}
