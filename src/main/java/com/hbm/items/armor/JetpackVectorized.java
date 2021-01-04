package com.hbm.items.armor;

import java.util.List;

import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.extprop.HbmExtendedProperties;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.KeybindPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.model.ModelJetPack;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class JetpackVectorized extends JetpackBase {

	public JetpackVectorized(ArmorMaterial mat, int i, int j, FluidType fuel, int maxFuel) {
		super(mat, i, j, fuel, maxFuel);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "hbm:textures/models/JetPackGreen.png";
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		
		HbmExtendedProperties props = HbmExtendedProperties.getData(player);
		
		if(world.isRemote) {
			
			if(player == MainRegistry.proxy.me()) {
				
				boolean last = props.getKeyPressed(EnumKeybind.JETPACK);
				boolean current = MainRegistry.proxy.getIsKeyPressed(EnumKeybind.JETPACK);
				
				if(last != current) {
					PacketDispatcher.wrapper.sendToServer(new KeybindPacket(EnumKeybind.JETPACK, current));
					props.setKeyPressed(EnumKeybind.JETPACK, current);
				}
			}
			
		} else {
			
			if(getFuel(stack) > 0 && props.getKeyPressed(EnumKeybind.JETPACK)) {

	    		NBTTagCompound data = new NBTTagCompound();
	    		data.setString("type", "jetpack");
	    		data.setInteger("player", player.getEntityId());
	    		data.setInteger("mode", 1);
	    		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, player.posX, player.posY, player.posZ), new TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 100));
			}
		}

		if(getFuel(stack) > 0 && props.getKeyPressed(EnumKeybind.JETPACK)) {
			
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
		}
    }
}
