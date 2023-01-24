package com.hbm.items.armor;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.ArmorModHandler;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemModSensor extends ItemArmorMod {

	public ItemModSensor() {
		super(ArmorModHandler.extra, true, true, true, true);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.YELLOW + "Beeps near hazardous gasses");
		list.add(EnumChatFormatting.YELLOW + "Works in the inventory or when applied to armor");
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.YELLOW + "  " + stack.getDisplayName() + " (Detects gasses)");
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean equipped) {
		if(entity instanceof EntityLivingBase) {
			modUpdate((EntityLivingBase) entity, null);
		}
	}

	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		
		if(entity.worldObj.isRemote || entity.worldObj.getTotalWorldTime() % 20 != 0) return;

		int x = (int) Math.floor(entity.posX);
		int y = (int) Math.floor(entity.posY + entity.getEyeHeight() - entity.getYOffset());
		int z = (int) Math.floor(entity.posZ);
		
		boolean poison = false;
		boolean explosive = false;
		
		for(int i = -3; i <= 3; i++) {
			for(int j = -1; j <= 1; j++) {
				for(int k = -3; k <= 3; k++) {
					Block b = entity.worldObj.getBlock(x + i * 2, y + j * 2, z + k * 2);
					if(b == ModBlocks.gas_asbestos || b == ModBlocks.gas_coal || b == ModBlocks.gas_radon || b == ModBlocks.gas_monoxide || b == ModBlocks.gas_radon_dense || b == ModBlocks.chlorine_gas) {
						poison = true;
					}
					if(b == ModBlocks.gas_flammable || b == ModBlocks.gas_explosive) {
						explosive = true;
					}
				}
			}
		}
		
		if(explosive) {
			entity.worldObj.playSoundAtEntity(entity, "hbm:weapon.follyAquired", 0.5F, 1.0F);
		} else if(poison) {
			entity.worldObj.playSoundAtEntity(entity, "hbm:item.techBoop", 2F, 1.5F);
		}
	}
}
