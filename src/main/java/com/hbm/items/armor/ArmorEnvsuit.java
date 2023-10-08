package com.hbm.items.armor;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.render.model.ModelArmorEnvsuit;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ArmorEnvsuit extends ArmorFSBOxy {
	

	
	public ArmorEnvsuit(ArmorMaterial material, int slot, String texture, FluidType fuelType, int maxFuel, int fillRate, int consumption, int drain) {
		super(material, slot, texture, fuelType, maxFuel, fillRate, consumption, drain);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorEnvsuit[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {

		if(models == null) {
			models = new ModelArmorEnvsuit[4];

			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorEnvsuit(i);
		}

		return models[armorSlot];
	}
	
	private static final UUID speed = UUID.fromString("6ab858ba-d712-485c-bae9-e5e765fc555a");

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {

		super.onArmorTick(world, player, stack);
		
		if(this != ModItems.envsuit_plate)
			return;

		/// SPEED ///
		Multimap multimap = super.getAttributeModifiers(stack);
		multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(speed, "SQUIRREL SPEED", 0.1, 0));
		player.getAttributeMap().removeAttributeModifiers(multimap);
		
		if(this.hasFSBArmor(player)) {
			
			if(player.isSprinting()) player.getAttributeMap().applyAttributeModifiers(multimap);
			
			if(player.isInWater()) {
				
				if(!world.isRemote) {
					player.setAir(300);
					player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 15 * 20, 0));
				}

				double mo = 0.1 * player.moveForward;
				Vec3 vec = player.getLookVec();
				vec.xCoord *= mo;
				vec.yCoord *= mo;
				vec.zCoord *= mo;

				player.motionX += vec.xCoord;
				player.motionY += vec.yCoord;
				player.motionZ += vec.zCoord;
			} else {
				if(!world.isRemote) {
					player.removePotionEffect(Potion.nightVision.id);
				}
			}
		}
	}
}
