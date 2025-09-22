package com.hbm.items.armor;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.model.ModelArmorTrenchmaster;
import com.hbm.render.tileentity.IItemRendererProvider;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ArmorTrenchmaster extends ArmorFSB implements IItemRendererProvider {

	public ArmorTrenchmaster(ArmorMaterial material, int slot, String texture) {
		super(material, slot, texture);
		this.setMaxDamage(0);
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

		//list.add(EnumChatFormatting.RED + "  " + I18nUtil.resolveKey("armor.fasterReload"));
		list.add(EnumChatFormatting.RED + "  " + I18nUtil.resolveKey("armor.moreAmmo"));
	}

	@Override
	public void handleHurt(LivingHurtEvent event) {
		super.handleHurt(event);

		EntityLivingBase e = event.entityLiving;

		if(e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e;

			if(ArmorFSB.hasFSBArmor(player)) {
				
				if(event.source.isExplosion() && event.source.getSourceOfDamage() == player) {
					event.ammount = 0;
					return;
				}
			}
		}
	}
	
	@Override
	public void handleAttack(LivingAttackEvent event) {
		super.handleAttack(event);

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

	public static boolean isTrenchMaster(EntityPlayer player) {
		if(player == null) return false;
		return player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() == ModItems.trenchmaster_plate && ArmorFSB.hasFSBArmor(player);
	}

	public static boolean hasAoS(EntityPlayer player) {
		if(player == null) return false;
		if(player.inventory.armorInventory[3] != null) {
			ItemStack[] mods =  ArmorModHandler.pryMods(player.inventory.armorInventory[3]);
			ItemStack helmet = mods[ArmorModHandler.helmet_only];
			return helmet != null && helmet.getItem() == ModItems.card_aos;
		}
		return false;
	}

	@Override public Item getItemForRenderer() { return this; }

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				if(armorType == 0) GL11.glTranslated(0, 1, 0);
				if(armorType == 1) GL11.glTranslated(0, 1.5, 0);
				setupRenderInv();
			}
			public void renderNonInv() { setupRenderNonInv(); }
			public void renderCommon() {
				renderStandard(ResourceManager.armor_trenchmaster, armorType,
						ResourceManager.trenchmaster_helmet, ResourceManager.trenchmaster_chest, ResourceManager.trenchmaster_arm, ResourceManager.trenchmaster_leg,
						"Helmet,Light", "Chest", "LeftArm", "RightArm", "LeftLeg", "RightLeg", "LeftBoot", "RightBoot");
			}};
	}
}
