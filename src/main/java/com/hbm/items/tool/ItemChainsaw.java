package com.hbm.items.tool;

import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.IHeldSoundProvider;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemChainsaw extends ItemToolAbilityFueled implements IHeldSoundProvider {

	public ItemChainsaw(float damage, double movement, ToolMaterial material, EnumToolType type, int maxFuel, int consumption, int fillRate, FluidType... acceptedFuels) {
		super(damage, movement, material, type, maxFuel, consumption, fillRate, acceptedFuels);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {

		if(!(entityLiving instanceof EntityPlayerMP))
			return false;

		if(stack.getItemDamage() >= stack.getMaxDamage())
			return false;

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("type", "anim");
		nbt.setString("mode", "sSwing");
		PacketThreading.createSendToThreadedPacket(new AuxParticlePacketNT(nbt, 0, 0, 0), (EntityPlayerMP)entityLiving);

		return false;
	}
}
