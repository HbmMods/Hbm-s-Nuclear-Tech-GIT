package com.hbm.items.armor;

import java.util.List;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.render.model.ModelArmorBJ;
import com.hbm.util.ArmorUtil;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ArmorBJJetpack extends ArmorBJ {

	public ArmorBJJetpack(ArmorMaterial material, int slot, String texture, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, slot, texture, maxPower, chargeRate, consumption, drain);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorBJ model;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		
		if(model == null) {
			model = new ModelArmorBJ(5);
		}
		
		return model;
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		
		super.onArmorTick(world, player, stack);
		
		HbmPlayerProps props = HbmPlayerProps.getData(player);
		
		if(!world.isRemote) {
			
			if(this.hasFSBArmor(player) && props.isJetpackActive()) {

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "jetpack_bj");
				data.setInteger("player", player.getEntityId());
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, player.posX, player.posY, player.posZ), new TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 100));
			}
		}

		if(this.hasFSBArmor(player)) {
			
			ArmorUtil.resetFlightTime(player);
			
			if(props.isJetpackActive()) {
				
				if(player.motionY < 0.4D)
					player.motionY += 0.1D;
				
				player.fallDistance = 0;
				
				world.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:weapon.immolatorShoot", 0.125F, 1.5F);
				
			} else if(player.isSneaking()) {
				
				if(player.motionY < -0.08) {
					
					double mo = player.motionY * -0.4;
					player.motionY += mo;
					
					Vec3 vec = player.getLookVec();
					vec.xCoord *= mo;
					vec.yCoord *= mo;
					vec.zCoord *= mo;

					player.motionX += vec.xCoord;
					player.motionY += vec.yCoord;
					player.motionZ += vec.zCoord;
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		super.addInformation(stack, player, list, ext);

		list.add(EnumChatFormatting.RED + "  + " + I18nUtil.resolveKey("armor.electricJetpack"));
		list.add(EnumChatFormatting.GRAY + "  + " + I18nUtil.resolveKey("armor.glider"));
	}
}
