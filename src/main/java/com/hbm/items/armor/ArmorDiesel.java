package com.hbm.items.armor;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.model.ModelArmorDiesel;
import com.hbm.render.tileentity.IItemRendererProvider;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

public class ArmorDiesel extends ArmorFSBFueled implements IItemRendererProvider {

	public ArmorDiesel(ArmorMaterial material, int slot, String texture, FluidType fuelType, int maxFuel, int fillRate, int consumption, int drain) {
		super(material, slot, texture, fuelType, maxFuel, fillRate, consumption, drain);
	}

	@Override
	public Multimap getItemAttributeModifiers() {

		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), new AttributeModifier(ArmorModHandler.fixedUUIDs[this.armorType], "Armor modifier", 0.25D, 1));
		return multimap;
	}

	@SideOnly(Side.CLIENT)
	ModelArmorDiesel[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {

		if(models == null) {
			models = new ModelArmorDiesel[4];

			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorDiesel(i);
		}

		return models[armorSlot];
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		super.onArmorTick(world, player, stack);

		if(!world.isRemote && this == ModItems.dieselsuit_legs && this.hasFSBArmor(player) && world.getTotalWorldTime() % 3 == 0) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "bnuuy");
			data.setInteger("player", player.getEntityId());
			PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, player.posX, player.posY, player.posZ), new TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 100));
		}
	}

	@Override
	public boolean acceptsFluid(FluidType type, ItemStack stack) {
		return type == Fluids.DIESEL || type == Fluids.DIESEL_CRACK;
	}

	@Override public Item getItemForRenderer() { return this; }

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() { setupRenderInv(); }
			public void renderNonInv() { setupRenderNonInv(); }
			public void renderCommon() {
				if(armorType == 0) {
					GL11.glTranslated(0, 0.5, 0);
					GL11.glScaled(0.875, 0.875, 0.875);
				}
				renderStandard(ResourceManager.armor_dieselsuit, armorType,
						ResourceManager.dieselsuit_helmet, ResourceManager.dieselsuit_chest, ResourceManager.dieselsuit_arm, ResourceManager.dieselsuit_leg,
						"Head", "Body", "LeftArm", "RightArm", "LeftLeg", "RightLeg", "LeftBoot", "RightBoot");
			}};
	}
}
