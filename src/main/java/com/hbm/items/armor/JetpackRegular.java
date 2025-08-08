package com.hbm.items.armor;

import java.util.List;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.ArmorUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class JetpackRegular extends JetpackFueledBase {

	public JetpackRegular(FluidType fuel, int maxFuel) {
		super(fuel, maxFuel);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "hbm:textures/models/JetPackRed.png";
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {

		HbmPlayerProps props = HbmPlayerProps.getData(player);

		if(!world.isRemote) {

			if(getFuel(stack) > 0 && props.isJetpackActive()) {

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "jetpack");
				data.setInteger("player", player.getEntityId());
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, player.posX, player.posY, player.posZ), new TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 100));
			}
		}

		if(getFuel(stack) > 0 && props.isJetpackActive()) {
			player.fallDistance = 0;

			if(player.motionY < 0.4D)
				player.motionY += 0.1D;

			world.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:weapon.flamethrowerShoot", 0.25F, 1.5F);
			this.useUpFuel(player, stack, 5);
			ArmorUtil.resetFlightTime(player);
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {

		list.add("Regular jetpack for simple upwards momentum.");

		super.addInformation(stack, player, list, ext);
	}
}
