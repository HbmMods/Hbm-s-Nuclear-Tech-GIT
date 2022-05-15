package com.hbm.items.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.ModItems;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.model.ModelArmorDiesel;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ArmorDiesel extends ArmorFSBFueled {

	public ArmorDiesel(ArmorMaterial material, int layer, int slot, String texture, FluidType fuelType, int maxFuel, int fillRate, int consumption, int drain) {
		super(material, layer, slot, texture, fuelType, maxFuel, fillRate, consumption, drain);
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
		
		if(this == ModItems.dieselsuit_legs && this.hasFSBArmor(player) && world.getTotalWorldTime() % 3 == 0) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "bnuuy");
			data.setInteger("player", player.getEntityId());
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, player.posX, player.posY, player.posZ), new TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 100));
		}
	}
}
