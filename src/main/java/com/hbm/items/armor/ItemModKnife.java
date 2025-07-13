package com.hbm.items.armor;

import java.util.List;
import java.util.UUID;

import com.hbm.handler.ArmorModHandler;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.main.MainRegistry;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class ItemModKnife extends ItemArmorMod {

	public static final UUID trigamma_UUID = UUID.fromString("86d44ca9-44f1-4ca6-bdbb-d9d33bead251");

	public ItemModKnife() {
		super(ArmorModHandler.extra, false, true, false, false);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.RED + "Pain.");
		list.add("");
		list.add(EnumChatFormatting.RED + "Hurts, doesn't it?");

		list.add("");
		super.addInformation(stack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.RED + "  " + stack.getDisplayName());
	}

	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {

		if(!entity.worldObj.isRemote) {

			if(entity.ticksExisted % 50 == 0 && entity.getMaxHealth() > 2F) {

				entity.worldObj.playSoundAtEntity(entity, "hbm:entity.slicer", 1.0F, 1.0F);

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("type", "bloodvomit");
				nbt.setInteger("entity", entity.getEntityId());
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(nbt, 0, 0, 0),  new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 25));

				IAttributeInstance attributeinstance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);

				float health = entity.getMaxHealth();

				try {
					attributeinstance.removeModifier(attributeinstance.getModifier(trigamma_UUID));
				} catch(Exception ex) { }

				attributeinstance.applyModifier(new AttributeModifier(trigamma_UUID, "digamma", -(entity.getMaxHealth() - health + 2), 0));

				if(entity instanceof EntityPlayerMP) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "properJolt");

					if(entity.getMaxHealth() > 2F) {
						data.setInteger("time", 10000 + entity.getRNG().nextInt(10000));
						data.setInteger("maxTime", 10000);
					} else {
						data.setInteger("time", 0);
						data.setInteger("maxTime", 0);

						((EntityPlayer)entity).triggerAchievement(MainRegistry.achSomeWounds);
					}
					PacketThreading.createSendToThreadedPacket(new AuxParticlePacketNT(data, 0, 0, 0), (EntityPlayerMP)entity);
				}
			}
		}
	}
}
