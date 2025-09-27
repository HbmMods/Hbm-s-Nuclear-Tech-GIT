package com.hbm.items.armor;

import com.hbm.items.ModItems;
import com.hbm.render.model.ModelT45Boots;
import com.hbm.render.model.ModelT45Chest;
import com.hbm.render.model.ModelT45Helmet;
import com.hbm.render.model.ModelT45Legs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ArmorT45 extends ArmorFSBPowered {
	
	@SideOnly(Side.CLIENT)
	private ModelT45Helmet helmet;
	@SideOnly(Side.CLIENT)
	private ModelT45Chest plate;
	@SideOnly(Side.CLIENT)
	private ModelT45Legs legs;
	@SideOnly(Side.CLIENT)
	private ModelT45Boots boots;

	public ArmorT45(ArmorMaterial material, int slot, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, slot, "", maxPower, chargeRate, consumption, drain);
		this.setCreativeTab(null);
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		if (stack.getItem() == ModItems.t45_helmet)
			return armorType == 0;
		if (stack.getItem() == ModItems.t45_plate)
			return armorType == 1;
		if (stack.getItem() == ModItems.t45_legs)
			return armorType == 2;
		if (stack.getItem() == ModItems.t45_boots)
			return armorType == 3;
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		if (this == ModItems.t45_helmet) {
			if (armorSlot == 0) {
				if (this.helmet == null) {
					this.helmet = new ModelT45Helmet();
				}
				return this.helmet;
			}
		}
		if (this == ModItems.t45_plate) {
			if (armorSlot == 1) {
				if (this.plate == null) {
					this.plate = new ModelT45Chest();
				}
				return this.plate;
			}
		}
		if (this == ModItems.t45_legs) {
			if (armorSlot == 2) {
				if (this.legs == null) {
					this.legs = new ModelT45Legs();
				}
				return this.legs;
			}
		}
		if (this == ModItems.t45_boots) {
			if (armorSlot == 3) {
				if (this.boots == null) {
					this.boots = new ModelT45Boots();
				}
				return this.boots;
			}
		}
		return null;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (stack.getItem() == ModItems.t45_helmet) {
			return "hbm:textures/models/T45Helmet.png";
		}
		if (stack.getItem() == ModItems.t45_plate) {
			return "hbm:textures/models/T45Chest.png";
		}
		if (stack.getItem() == ModItems.t45_legs) {
			return "hbm:textures/models/T45Legs.png";
		}
		if (stack.getItem() == ModItems.t45_boots) {
			return "hbm:textures/models/T45Boots.png";
		}
		return null;
	}
}
