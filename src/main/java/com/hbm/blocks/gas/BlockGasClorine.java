package com.hbm.blocks.gas;


import java.util.Random;

import com.hbm.util.ArmorUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGasClorine extends BlockGasBase {
	
	@Override
	public int getRenderType() {
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entity) {
		if(entity instanceof EntityPlayer && ArmorUtil.checkForGasMask((EntityPlayer) entity)) {

			if(world.rand.nextInt(25) == 0)
				ArmorUtil.damageSuit((EntityPlayer) entity, 3, world.rand.nextInt(2));

		} else if(entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.blindness.getId(), 5 * 20, 0));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.getId(), 20 * 20, 2));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.wither.getId(), 1 * 20, 1));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 30 * 20, 1));
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.digSlowdown.getId(), 30 * 20, 2));
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(!world.isRemote && rand.nextInt(10) == 0) {
			world.setBlockToAir(x, y, z);
			return;
		}
		
		super.updateTick(world, x, y, z, rand);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return p_149646_5_ == 0 && this.minY > 0.0D ? true : (p_149646_5_ == 1 && this.maxY < 1.0D ? true : (p_149646_5_ == 2 && this.minZ > 0.0D ? true : (p_149646_5_ == 3 && this.maxZ < 1.0D ? true : (p_149646_5_ == 4 && this.minX > 0.0D ? true : (p_149646_5_ == 5 && this.maxX < 1.0D ? true : !p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_).isOpaqueCube())))));
	}

	@Override
	public ForgeDirection getFirstDirection(World world, int x, int y, int z) {
		
		if(world.rand.nextInt(5) == 0)
			return ForgeDirection.UP;
		
		return ForgeDirection.DOWN;
	}

	@Override
	public ForgeDirection getSecondDirection(World world, int x, int y, int z) {
		return this.randomHorizontal(world);
	}
}
