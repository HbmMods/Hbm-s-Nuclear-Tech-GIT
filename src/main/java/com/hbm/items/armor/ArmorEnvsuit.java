package com.hbm.items.armor;

import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;
import com.hbm.render.model.ModelArmorEnvsuit;
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
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.UUID;

public class ArmorEnvsuit extends ArmorFSBPowered {

	public ArmorEnvsuit(ArmorMaterial material, int slot, String texture, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, slot, texture, maxPower, chargeRate, consumption, drain);
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
				boolean canRemoveNightVision = true;
				ItemStack helmet = player.inventory.armorInventory[3];
				ItemStack helmetMod = ArmorModHandler.pryMod(helmet, ArmorModHandler.helmet_only); // Get the modification!
				if (helmetMod != null && helmetMod.getItem() instanceof ItemModNightVision) {
					canRemoveNightVision = false;
				}

				if(!world.isRemote && canRemoveNightVision) {
					player.removePotionEffect(Potion.nightVision.id);
				}
			}
		}
	}
}
