package com.hbm.tileentity.machine;

import java.util.List;
import java.util.Random;

import com.hbm.config.RadiationConfig;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityDecon extends TileEntity {

	@Override
	public void updateEntity() {
		if(!this.worldObj.isRemote) {
			List<EntityLivingBase> entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(this.xCoord - 0.5, this.yCoord, this.zCoord - 0.5, this.xCoord + 1.5, this.yCoord + 2, this.zCoord + 1.5));
			if(!entities.isEmpty()) {
				for(EntityLivingBase e : entities) {
					HbmLivingProps.incrementRadiation(e, -0.5F);
					e.removePotionEffect(HbmPotion.radiation.id);
					HbmLivingProps.getCont(e).clear();
				}

				deconNeutron(entities);
			}
		} else {
			Random rand = worldObj.rand;

			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("type", "vanillaExt");
			nbt.setString("mode", "townaura");
			nbt.setDouble("posX", xCoord + 0.125 + rand.nextDouble() * 0.75);
			nbt.setDouble("posY", yCoord + 1.1);
			nbt.setDouble("posZ", zCoord + 0.125 + rand.nextDouble() * 0.75);
			nbt.setDouble("mX", 0.0);
			nbt.setDouble("mY", 0.04);
			nbt.setDouble("mZ", 0.0);
			MainRegistry.proxy.effectNT(nbt);
		}
	}

	private void deconNeutron(List<EntityLivingBase> entities) {
		for(EntityLivingBase e : entities) {
			float neut = HbmLivingProps.getNeutronActivation(e);

			if(neut > 0 && !RadiationConfig.disableNeutron) {
				ContaminationUtil.contaminate(e, HazardType.RADIATION, ContaminationType.RAD_BYPASS, neut / 20F);
				HbmLivingProps.setNeutronActivation(e, neut * 0.899916F);// 20 minute half life not  really
			}

			if(e instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) e;
				for(int i2 = 0; i2 < player.inventory.mainInventory.length; i2++) {
					ItemStack stack2 = player.inventory.getStackInSlot(i2);

					if(stack2 != null) {
						if(!stack2.hasTagCompound())
							stack2.stackTagCompound = new NBTTagCompound();
						float activation = stack2.stackTagCompound.getFloat("ntmNeutron");
						stack2.stackTagCompound.setFloat("ntmNeutron", activation * 0.899916f);
						if(activation < 1e-5)
							stack2.stackTagCompound.removeTag("ntmNeutron");
						if(stack2.stackTagCompound.hasNoTags()) {
							stack2.setTagCompound((NBTTagCompound) null);
						}
					}
				}
				for(int i2 = 0; i2 < player.inventory.armorInventory.length; i2++) {
					ItemStack stack2 = player.inventory.armorItemInSlot(i2);

					if(stack2 != null) {
						if(!stack2.hasTagCompound())
							stack2.stackTagCompound = new NBTTagCompound();
						float activation = stack2.stackTagCompound.getFloat("ntmNeutron");
						stack2.stackTagCompound.setFloat("ntmNeutron", activation * 0.899916f);
						if(activation < 1e-5)
							stack2.stackTagCompound.removeTag("ntmNeutron");
						if(stack2.stackTagCompound.hasNoTags()) {
							stack2.setTagCompound((NBTTagCompound) null);
						}
					}
				}
			}
		}
	}
}
