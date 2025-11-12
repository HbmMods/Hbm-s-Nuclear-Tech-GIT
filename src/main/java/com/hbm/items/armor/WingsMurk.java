package com.hbm.items.armor;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelArmorWings;
import com.hbm.util.ArmorUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class WingsMurk extends JetpackBase {

	public WingsMurk() {
		super();
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return ResourceManager.wings_murk.toString();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		if (armorSlot == 1) {
			if(cachedModel == null) {
				cachedModel = new ModelArmorWings(this == ModItems.wings_murk ? 0 : 1);
			}
			
			return cachedModel;
		}

		return null;
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		
		if(player.onGround)
			return;
		
		ArmorUtil.resetFlightTime(player);
		
		if(player.fallDistance > 0)
			player.fallDistance = 0;
		
		if(player.motionY < -0.4D)
			player.motionY = -0.4D;
		
		if(this == ModItems.wings_limp) {
			
			 if(player.isSneaking()) {
					
				if(player.motionY < -0.08) {

					double mo = player.motionY * -0.2;
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

		HbmPlayerProps props = HbmPlayerProps.getData(player);
		
		if(this == ModItems.wings_murk) {

			if(props.isJetpackActive()) {

				if(player.motionY < 0.6D)
					player.motionY += 0.2D;
				else
					player.motionY = 0.8D;
				
			} else if(props.enableBackpack && !player.isSneaking()) {
				
				if(player.motionY < -1)
					player.motionY += 0.4D;
				else if(player.motionY < -0.1)
					player.motionY += 0.2D;
				else if(player.motionY < 0)
					player.motionY = 0;
			}
			
			if(props.enableBackpack) {
				
				Vec3 orig = player.getLookVec();
				Vec3 look = Vec3.createVectorHelper(orig.xCoord, 0, orig.zCoord).normalize();
				double mod = player.isSneaking() ? 0.25D : 1D;
				
				if(player.moveForward != 0) {
					player.motionX += look.xCoord * 0.35 * player.moveForward * mod;
					player.motionZ += look.zCoord * 0.35 * player.moveForward * mod;
				}
				
				if(player.moveStrafing != 0) {
					look.rotateAroundY((float) Math.PI * 0.5F);
					player.motionX += look.xCoord * 0.15 * player.moveStrafing * mod;
					player.motionZ += look.zCoord * 0.15 * player.moveStrafing * mod;
				}
			}
		}
	}
}
