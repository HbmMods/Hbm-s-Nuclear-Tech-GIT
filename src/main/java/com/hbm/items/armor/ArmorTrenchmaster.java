package com.hbm.items.armor;

import java.util.List;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.render.model.ModelArmorTrenchmaster;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class ArmorTrenchmaster extends ArmorFSB {

	public ArmorTrenchmaster(ArmorMaterial material, int slot, String texture) {
		super(material, slot, texture);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorTrenchmaster[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {

		if(models == null) {
			models = new ModelArmorTrenchmaster[4];

			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorTrenchmaster(i);
		}

		return models[armorSlot];
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		super.addInformation(stack, player, list, ext);

		list.add(EnumChatFormatting.RED + "  " + I18nUtil.resolveKey("armor.fasterReload"));
		list.add(EnumChatFormatting.RED + "  " + I18nUtil.resolveKey("armor.moreAmmo"));
	}
	
	@Override
	public void handleAttack(LivingAttackEvent event) {

		EntityLivingBase e = event.entityLiving;

		if(e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e;

			if(ArmorFSB.hasFSBArmor(player)) {

				if(e.getRNG().nextInt(3) == 0) {
					HbmPlayerProps.plink(player, "random.break", 0.5F, 1.0F + e.getRNG().nextFloat() * 0.5F);
					event.setCanceled(true);
				}
			}
		}
	}
}
