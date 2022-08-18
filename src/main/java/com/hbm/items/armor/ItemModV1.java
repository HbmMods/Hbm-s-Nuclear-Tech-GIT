package com.hbm.items.armor;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.interfaces.IArmorModDash;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModV1 extends ItemArmorMod implements IArmorModDash {
	
	private static final UUID speed = UUID.fromString("1d11e63e-28c4-4e14-b09f-fe0bd1be708f");
	public static final UUID its_to_prevent_you_from_healing = UUID.fromString("9b0ff87a-6103-482b-b515-19ef2e6721ce");

	public ItemModV1() {
		super(ArmorModHandler.extra, false, true, false, false);
	}
	
	@Override
	public Multimap getModifiers(ItemStack armor) {
		Multimap multimap = super.getAttributeModifiers(armor);
		multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(speed, "V1 SPEED", 0.5, 2));
		return multimap;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		list.add(EnumChatFormatting.RED + "BLOOD IS FUEL");
		list.add("");
		super.addInformation(stack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.RED + "  " + stack.getDisplayName() + " (BLOOD IS FUEL)");
	}
	
	public int getDashes() {
		return 3;
	}

	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {
		
		EntityLivingBase entity = event.entityLiving;
		
		IAttributeInstance attributeinstance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
		
		float health = entity.getMaxHealth();
		
		try {
			attributeinstance.removeModifier(attributeinstance.getModifier(its_to_prevent_you_from_healing));
		} catch(Exception ex) { }
		
		attributeinstance.applyModifier(new AttributeModifier(its_to_prevent_you_from_healing, "hakita what is this bullshit", -(entity.getMaxHealth() - health + event.ammount * 0.35), 0));
	}
}
