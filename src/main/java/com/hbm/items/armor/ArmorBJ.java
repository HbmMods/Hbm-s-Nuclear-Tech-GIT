package com.hbm.items.armor;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.model.ModelArmorBJ;
import com.hbm.render.tileentity.IItemRendererProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

public class ArmorBJ extends ArmorFSBPowered implements IItemRendererProvider {

	public ArmorBJ(ArmorMaterial material, int slot, String texture, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, slot, texture, maxPower, chargeRate, consumption, drain);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorBJ[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {

		if(models == null) {
			models = new ModelArmorBJ[4];

			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorBJ(i);
		}

		return models[armorSlot];
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {

		super.onArmorTick(world, player, itemStack);

		if(this == ModItems.bj_helmet && ArmorFSB.hasFSBArmorIgnoreCharge(player) && !ArmorFSB.hasFSBArmor(player)) {

			ItemStack helmet = player.inventory.armorInventory[3];

			if(!player.inventory.addItemStackToInventory(helmet))
				player.dropPlayerItemWithRandomChoice(helmet, false);

			player.inventory.armorInventory[3] = null;

			player.attackEntityFrom(ModDamageSource.lunar, 1000);
		}
	}

	@Override public Item getItemForRenderer() { return this; }

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() { setupRenderInv(); }
			public void renderNonInv() { setupRenderNonInv(); }
			public void renderCommon() {
				if(armorType == 1) {
					if(ArmorBJ.this == ModItems.bj_plate_jetpack) {
						GL11.glScaled(0.6875, 0.6875, 0.6875);
					} else {
						GL11.glScaled(0.875, 0.875, 0.875);
					}
				}
				renderStandard(ResourceManager.armor_bj, armorType,
						ResourceManager.bj_eyepatch, ResourceManager.bj_chest, ResourceManager.bj_arm, ResourceManager.bj_leg,
						"Head", "Body", "LeftArm", "RightArm", "LeftLeg", "RightLeg", "LeftFoot", "RightFoot");
				if(ArmorBJ.this == ModItems.bj_plate_jetpack) {
					GL11.glTranslated(0, 0, -0.1);
					Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.bj_jetpack);
					ResourceManager.armor_bj.renderPart("Jetpack");
				}
			}};
	}
}
