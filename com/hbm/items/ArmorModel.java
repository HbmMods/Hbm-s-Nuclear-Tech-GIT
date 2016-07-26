package com.hbm.items;

import java.util.List;

import com.hbm.lib.Library;
import com.hbm.render.ModelCloak;
import com.hbm.render.ModelGasMask;
import com.hbm.render.ModelGoggles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmorModel extends ItemArmor {
	@SideOnly(Side.CLIENT)
	private ModelGoggles modelGoggles;
	private ModelGasMask modelGas;
	private ModelCloak modelCloak;

	public ArmorModel(ArmorMaterial armorMaterial, int renderIndex, int armorType) {
		super(armorMaterial, renderIndex, armorType);
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		if (this == ModItems.goggles) {
			return armorType == 0;
		}
		if (this == ModItems.gas_mask) {
			return armorType == 0;
		}
		if (this == ModItems.cape_test) {
			return armorType == 1;
		}
		if (this == ModItems.cape_radiation) {
			return armorType == 1;
		}
		if (this == ModItems.cape_gasmask) {
			return armorType == 1;
		}
		if (this == ModItems.cape_schrabidium) {
			return armorType == 1;
		}
		if (this == ModItems.cape_hbm) {
			return armorType == 1;
		}
		if (this == ModItems.cape_dafnik) {
			return armorType == 1;
		}
		if (this == ModItems.cape_lpkukin) {
			return armorType == 1;
		}
		return armorType == 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		if (this == ModItems.goggles) {
			if (armorSlot == 0) {
				if (this.modelGoggles == null) {
					this.modelGoggles = new ModelGoggles();
				}
				return this.modelGoggles;
			}
		}
		if (this == ModItems.gas_mask) {
			if (armorSlot == 0) {
				if (this.modelGas == null) {
					this.modelGas = new ModelGasMask();
				}
				return this.modelGas;
			}
		}
		if (this == ModItems.cape_test || this == ModItems.cape_radiation || this == ModItems.cape_gasmask || this == ModItems.cape_schrabidium) {
			if (armorSlot == 1) {
				if (this.modelCloak == null) {
					this.modelCloak = new ModelCloak();
				}
				return this.modelCloak;
			}
		}
		if (this == ModItems.cape_hbm || this == ModItems.cape_dafnik || this == ModItems.cape_lpkukin) {
			if (armorSlot == 1) {
				if (this.modelCloak == null) {
					this.modelCloak = new ModelCloak();
				}
				return this.modelCloak;
			}
		}
		return null;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (stack.getItem() == ModItems.goggles) {
			return "hbm:textures/models/Goggles.png";
		}
		if (stack.getItem() == ModItems.gas_mask) {
			return "hbm:textures/models/GasMask.png";
		}
		if (stack.getItem() == ModItems.cape_test) {
			return "hbm:textures/models/TestCape.png";
		}
		if (stack.getItem() == ModItems.cape_radiation) {
			return "hbm:textures/models/CapeRadiation.png";
		}
		if (stack.getItem() == ModItems.cape_gasmask) {
			return "hbm:textures/models/CapeGasMask.png";
		}
		if (stack.getItem() == ModItems.cape_schrabidium) {
			return "hbm:textures/models/CapeSchrabidium.png";
		}
		if (stack.getItem() == ModItems.cape_hbm && entity instanceof EntityPlayer && ((EntityPlayer)entity).getUniqueID().toString().equals(Library.HbMinecraft)) {
			return "hbm:textures/models/CapeHbm.png";
		}
		if (stack.getItem() == ModItems.cape_dafnik && entity instanceof EntityPlayer && ((EntityPlayer)entity).getUniqueID().toString().equals(Library.Dafnik)) {
			return "hbm:textures/models/CapeDafnik.png";
		}
		if (stack.getItem() == ModItems.cape_lpkukin && entity instanceof EntityPlayer && ((EntityPlayer)entity).getUniqueID().toString().equals(Library.LPkukin)) {
			return "hbm:textures/models/CapeShield.png";
		}
		return "hbm:textures/models/CapeUnknown.png";
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if (itemstack.getItem() == ModItems.cape_radiation) {
			list.add("Avalible for everyone");
		}
		if (itemstack.getItem() == ModItems.cape_gasmask) {
			list.add("Avalible for everyone");
		}
		if (itemstack.getItem() == ModItems.cape_schrabidium) {
			list.add("Avalible for everyone");
		}
		if (itemstack.getItem() == ModItems.cape_hbm) {
			list.add("Only works for HbMinecraft");
		}
		if (itemstack.getItem() == ModItems.cape_dafnik) {
			list.add("Only works for Dafnik");
		}
		if (itemstack.getItem() == ModItems.cape_lpkukin) {
			list.add("Only works for LPkukin");
		}
	}
}
