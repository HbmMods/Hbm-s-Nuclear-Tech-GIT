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
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class JetpackVectorized extends JetpackFueledBase {

	public JetpackVectorized(FluidType fuel, int maxFuel) {
		super(fuel, maxFuel);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "hbm:textures/models/JetPackGreen.png";
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {

		HbmPlayerProps props = HbmPlayerProps.getData(player);

		if(!world.isRemote) {

			if(getFuel(stack) > 0 && props.isJetpackActive()) {

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "jetpack");
				data.setInteger("player", player.getEntityId());
				data.setInteger("mode", 1);
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, player.posX, player.posY, player.posZ), new TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 100));
			}
		}

		if(getFuel(stack) > 0 && props.isJetpackActive()) {

			if(player.motionY < 0.4D)
				player.motionY += 0.1D;

			Vec3 look = player.getLookVec();

			if(Vec3.createVectorHelper(player.motionX, player.motionY, player.motionZ).lengthVector() < 2) {
				player.motionX += look.xCoord * 0.1;
				player.motionY += look.yCoord * 0.1;
				player.motionZ += look.zCoord * 0.1;

				if(look.yCoord > 0)
					player.fallDistance = 0;
			}

			world.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:weapon.flamethrowerShoot", 0.25F, 1.5F);
			this.useUpFuel(player, stack, 3);
			ArmorUtil.resetFlightTime(player);
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {

		list.add("High-mobility jetpack.");
		list.add("Higher fuel consumption.");

		super.addInformation(stack, player, list, ext);
	}
}
