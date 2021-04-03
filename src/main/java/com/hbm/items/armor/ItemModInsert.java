package com.hbm.items.armor;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.interfaces.IItemHazard;
import com.hbm.items.ModItems;
import com.hbm.modules.ItemHazardModule;
import com.hbm.util.ContaminationUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModInsert extends ItemArmorMod implements IItemHazard {
	
	float damageMod;
	float projectileMod;
	float explosionMod;
	float speed;

	public ItemModInsert(int durability, float damageMod, float projectileMod, float explosionMod, float speed) {
		super(ArmorModHandler.kevlar, false, true, false, false);
		this.damageMod = damageMod;
		this.projectileMod = projectileMod;
		this.explosionMod = explosionMod;
		this.speed = speed;
		this.setMaxDamage(durability);
	}
    
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		if(damageMod != 1F)
			list.add(EnumChatFormatting.RED + (damageMod < 1 ? "-" : "+") + Math.abs(Math.round((1F - damageMod) * 100)) + "% damage");
		if(projectileMod != 1F)
			list.add(EnumChatFormatting.YELLOW + "-" + Math.round((1F - projectileMod) * 100) + "% projectile damage");
		if(explosionMod != 1F)
			list.add(EnumChatFormatting.YELLOW + "-" + Math.round((1F - explosionMod) * 100) + "% explosion damage");
		if(speed != 1F)
			list.add(EnumChatFormatting.BLUE + "-" + Math.round((1F - speed) * 100) + "% speed");
		
		if(this == ModItems.insert_polonium)
			list.add(EnumChatFormatting.DARK_RED + "+100 RAD/s");
		
		list.add((stack.getMaxDamage() - stack.getItemDamage()) + "/" + stack.getMaxDamage() + "HP");
		
		list.add("");
		super.addInformation(stack, player, list, bool);
		
		module.addInformation(stack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		
		List<String> desc = new ArrayList();

		if(damageMod != 1F)
			desc.add((damageMod < 1 ? "-" : "+") + Math.abs(Math.round((1F - damageMod) * 100)) + "% dmg");
		if(projectileMod != 1F)
			desc.add("-" + Math.round((1F - projectileMod) * 100) + "% proj");
		if(explosionMod != 1F)
			desc.add("-" + Math.round((1F - explosionMod) * 100) + "% exp");
		if(explosionMod != 1F)
			desc.add("-" + Math.round((1F - speed) * 100) + "% speed");

		if(this == ModItems.insert_polonium)
			desc.add("+100 RAD/s");
		
		String join = String.join(" / ", desc);
		
		list.add(EnumChatFormatting.DARK_PURPLE + "  " + stack.getDisplayName() + " (" + join + " / " + (stack.getMaxDamage() - stack.getItemDamage()) + "HP)");
	}

	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {
		
		event.ammount *= damageMod;
		
		if(event.source.isProjectile())
			event.ammount *= projectileMod;
		
		if(event.source.isExplosion())
			event.ammount *= explosionMod;
		
		ItemStack insert = ArmorModHandler.pryMods(armor)[ArmorModHandler.kevlar];
		
		if(insert == null)
			return;
		
		insert.setItemDamage(insert.getItemDamage() + 1);
		
		if(!event.entity.worldObj.isRemote && this == ModItems.insert_era) {
			event.entity.worldObj.newExplosion(event.entity, event.entity.posX, event.entity.posY - event.entity.yOffset + event.entity.height * 0.5, event.entity.posZ, 0.05F, false, false);
		}
		
		if(insert.getItemDamage() >= insert.getMaxDamage()) {
			ArmorModHandler.removeMod(armor, ArmorModHandler.kevlar);
		} else {
			ArmorModHandler.applyMod(armor, insert);
		}
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		
		if(!entity.worldObj.isRemote && this == ModItems.insert_polonium) {
			ContaminationUtil.applyRadDirect(entity, 5);
		}
	}
	
	@Override
	public Multimap getModifiers(ItemStack armor) {
		
		if(speed == 1)
			return null;
		
		Multimap multimap = super.getItemAttributeModifiers();
		
		multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(),
				new AttributeModifier(ArmorModHandler.UUIDs[((ItemArmor)armor.getItem()).armorType], "NTM Armor Mod Speed", -1F + speed, 2));
		
		return multimap;
	}

	ItemHazardModule module = new ItemHazardModule();
	
	@Override
	public ItemHazardModule getModule() {
		return module;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
		
		if(entity instanceof EntityLivingBase)
			this.module.applyEffects((EntityLivingBase) entity, stack.stackSize, i, b);
	}
}
