package com.hbm.blocks.generic;

import com.hbm.potion.HbmPotion;
import com.hbm.util.ArmorUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGasRadon extends BlockGasBase {

	@Override
	public void onEntityCollidedWithBlock(World world, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entity) {
		
		if(entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer && ArmorUtil.checkForGasMask((EntityPlayer) entity))) {
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30, 1));
		}
	}

	@Override
	public ForgeDirection getFirstDirection(World world, int x, int y, int z) {
		return ForgeDirection.DOWN;
	}

	@Override
	public ForgeDirection getSecondDirection(World world, int x, int y, int z) {
		return this.randomHorizontal(world);
	}
}
