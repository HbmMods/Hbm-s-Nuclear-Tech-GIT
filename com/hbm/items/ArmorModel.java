package com.hbm.items;

import com.hbm.render.ModelGasMask;
import com.hbm.render.ModelGoggles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmorModel extends ItemArmor {
		  @SideOnly(Side.CLIENT)
		  private ModelGoggles modelGoggles;
		  private ModelGasMask modelGas;
		  
		  public ArmorModel(ArmorMaterial armorMaterial, int renderIndex, int armorType)
		  {
				super(armorMaterial, renderIndex, armorType);
		  }
		  
		  @Override
		public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
		  {
		    return armorType == 0;
		  }
		  
		  @Override
		@SideOnly(Side.CLIENT)
		  public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
		  {
			  if(this == ModItems.goggles)
			  {
				  if (armorSlot == 0)
				  {
					  if (this.modelGoggles == null) {
						  this.modelGoggles = new ModelGoggles();
					  }
					  return this.modelGoggles;
				  }
			  }
			  if(this == ModItems.gas_mask)
			  {
				  if (armorSlot == 0)
				  {
					  if (this.modelGas == null) {
						  this.modelGas = new ModelGasMask();
					  }
					  return this.modelGas;
				  }
			  }
			  return null;
		  }
		  
		  @Override
		public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
		  {
			  if(stack.getItem() == ModItems.goggles)
			  {
				  return "hbm:textures/models/Goggles.png";
			  }
			  if(stack.getItem() == ModItems.gas_mask)
			  {
				  return "hbm:textures/models/GasMask.png";
			  }
			  return null;
		  }
}
