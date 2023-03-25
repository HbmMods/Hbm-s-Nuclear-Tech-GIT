package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.hbm.handler.WeaponAbility;
import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;

public class ItemSwordAbility extends ItemSword implements IItemAbility {

	private EnumRarity rarity = EnumRarity.common;
	// was there a reason for this to be private?
	protected float damage;
	protected double movement;
	private List<WeaponAbility> hitAbility = new ArrayList();

	public ItemSwordAbility(float damage, double movement, ToolMaterial material) {
		super(material);
		this.damage = damage;
		this.movement = movement;
	}

	public ItemSwordAbility addHitAbility(WeaponAbility weaponAbility) {
		this.hitAbility.add(weaponAbility);
		return this;
	}

	// <insert obvious Rarity joke here>
	public ItemSwordAbility setRarity(EnumRarity rarity) {
		this.rarity = rarity;
		return this;
	}

	public EnumRarity getRarity(ItemStack stack) {
		return this.rarity != EnumRarity.common ? this.rarity : super.getRarity(stack);
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker) {

		if(!attacker.worldObj.isRemote && !this.hitAbility.isEmpty() && attacker instanceof EntityPlayer && canOperate(stack)) {

			// hacky hacky hack
			if(this == ModItems.mese_gavel)
				attacker.worldObj.playSoundAtEntity(victim, "hbm:weapon.whack", 3.0F, 1.F);

			for(WeaponAbility ability : this.hitAbility) {
				ability.onHit(attacker.worldObj, (EntityPlayer) attacker, victim, this);
			}
		}

		stack.damageItem(1, attacker);

		return true;
	}

	@Override
	public Multimap getItemAttributeModifiers() {

		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double) this.damage, 0));
		multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", movement, 1));
		return multimap;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {

		if(!this.hitAbility.isEmpty()) {

			list.add("Weapon modifiers: ");

			for(WeaponAbility ability : this.hitAbility) {
				list.add("  " + EnumChatFormatting.RED + ability.getFullName());
			}
		}
	}

	protected boolean canOperate(ItemStack stack) {
		return true;
	}

	@Override
	public boolean isShears(ItemStack stack) {
		return false;
	}
}
