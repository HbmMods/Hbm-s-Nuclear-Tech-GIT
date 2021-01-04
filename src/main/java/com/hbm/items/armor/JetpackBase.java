package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.render.model.ModelJetPack;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class JetpackBase extends ItemArmor {

	private ModelJetPack model;
	public FluidType fuel;
	public int maxFuel;

	public JetpackBase(ArmorMaterial mat, int i, int j, FluidType fuel, int maxFuel) {
		super(mat, i, j);
		this.fuel = fuel;
		this.maxFuel = maxFuel;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(I18nUtil.resolveKey(fuel.getUnlocalizedName()) + ": " + this.getFuel(itemstack) + "mB / " + this.maxFuel + "mB");
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		if (armorSlot == 1) {
			if (model == null) {
				this.model = new ModelJetPack();
			}
			return this.model;
		}
		
		return null;
	}
	
	protected void useUpFuel(EntityPlayer player, ItemStack stack, int rate) {

		if(player.ticksExisted % rate == 0)
			this.setFuel(stack, this.getFuel(stack) - 1);
	}
	
    public static int getFuel(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getInteger("fuel");
		
	}
	
	public static void setFuel(ItemStack stack, int i) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		stack.stackTagCompound.setInteger("fuel", i);
		
	}
}
