package com.hbm.items.weapon;

import java.util.List;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityMiniNuke;
import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunProtoMirv extends Item {

	public GunProtoMirv() {
		this.maxStackSize = 1;
		this.setMaxDamage(2500);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int integer) {
		int j = this.getMaxItemUseDuration(stack) - integer;

		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, j);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return;
		}
		j = event.charge;

		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

		boolean f1 = false;
				
		for(int i = 0; i < 8; i++) {
			if (flag || player.inventory.hasItem(ModItems.gun_fatman_ammo)) {
				
				f1 = true;
				
				float f = j / 20.0F;
				f = (f * f + f * 2.0F) / 3.0F;
	
				if (j < 25.0D) {
					return;
				}
	
				if (j > 25.0F) {
					f = 25.0F;
				}
	
				EntityMiniNuke entityarrow = new EntityMiniNuke(world, player, 3.0F);
	
				entityarrow.setIsCritical(true);
				entityarrow.gravity = 0.3;
				entityarrow.setDamage(1000);
				
				entityarrow.motionX += world.rand.nextGaussian() * 0.4;
				entityarrow.motionY += world.rand.nextGaussian() * 0.4;
				entityarrow.motionZ += world.rand.nextGaussian() * 0.4;
	
				stack.damageItem(1, player);
	
				if (!flag) {
					player.inventory.consumeInventoryItem(ModItems.gun_fatman_ammo);
				}
	
				if (!world.isRemote) {
					world.spawnEntityInWorld(entityarrow);
				}
			}
		}
		
		if(f1)
			world.playSoundAtEntity(player, "hbm:weapon.fatmanShoot", 1.0F, 1F);
	}

	@Override
	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
		return p_77654_1_;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.bow;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		ArrowNockEvent event = new ArrowNockEvent(p_77659_3_, p_77659_1_);
		MinecraftForge.EVENT_BUS.post(event);
		
		p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));

		return p_77659_1_;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", -0.3, 1));
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", 4, 0));
		return multimap;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("Excessive, who's being excessive!?");
		list.add("");
		list.add("Ammo: Mini Nukes");
		list.add("Damage: 1000");
		list.add("Shoots up to eight mini nukes at once!");
	}
}
