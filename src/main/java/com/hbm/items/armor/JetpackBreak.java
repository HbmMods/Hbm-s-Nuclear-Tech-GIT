package com.hbm.items.armor;

import com.hbm.extprop.HbmExtendedProperties;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.KeybindPacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class JetpackBreak extends JetpackBase {

	public static int maxFuel = 1200;

	public JetpackBreak(ArmorMaterial mat, int i, int j, FluidType fuel, int maxFuel) {
		super(mat, i, j, fuel, maxFuel);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "hbm:textures/models/JetPackBlue.png";
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
			
			if(getFuel(stack) > 0 && (props.getKeyPressed(EnumKeybind.JETPACK) || (!player.onGround && !player.isSneaking()))) {

	    		NBTTagCompound data = new NBTTagCompound();
	    		data.setString("type", "jetpack");
	    		data.setInteger("player", player.getEntityId());
	    		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, player.posX, player.posY, player.posZ), new TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 100));
			}
		}

		if(getFuel(stack) > 0) {
			
			if(props.getKeyPressed(EnumKeybind.JETPACK)) {
				player.fallDistance = 0;
				
				if(player.motionY < 0.4D)
					player.motionY += 0.1D;
				
				world.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:weapon.flamethrowerShoot", 0.25F, 1.5F);
				this.useUpFuel(player, stack, 5);
				
			} else if(!player.isSneaking() && !player.onGround) {
				player.fallDistance = 0;
				
				if(player.motionY < -1)
					player.motionY += 0.2D;
				else if(player.motionY < -0.1)
					player.motionY += 0.1D;
				else if(player.motionY < 0)
					player.motionY = 0;
				
				world.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:weapon.flamethrowerShoot", 0.25F, 1.5F);
				this.useUpFuel(player, stack, 10);
			}
		}
    }
}
