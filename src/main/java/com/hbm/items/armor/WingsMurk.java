package com.hbm.items.armor;

import com.hbm.items.ModItems;
import com.hbm.render.model.ModelArmorWings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class WingsMurk extends ItemArmor {

	public WingsMurk(ArmorMaterial material) {
		super(material, 7, 1);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorWings model;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		
		if(model == null) {
			model = new ModelArmorWings(this == ModItems.wings_murk ? 0 : 1);
		}
		
		return model;
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		
		if(player.fallDistance > 0)
			player.fallDistance = 0;
		
		if(player.motionY < -0.4D)
			player.motionY = -0.4D;
		
		if(this == ModItems.wings_limp) {
			
			 if(player.isSneaking()) {
					
					if(player.motionY < -0.08) {
						
						double mo = player.motionY * -0.2;
						player.motionY += mo;
						
						Vec3 vec = player.getLookVec();
						vec.xCoord *= mo;
						vec.yCoord *= mo;
						vec.zCoord *= mo;

						player.motionX += vec.xCoord;
						player.motionY += vec.yCoord;
						player.motionZ += vec.zCoord;
					}
				}
		}
	}
}
