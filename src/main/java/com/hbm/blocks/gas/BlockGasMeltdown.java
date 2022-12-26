package com.hbm.blocks.gas;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorUtil;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGasMeltdown extends BlockGasBase {

	public BlockGasMeltdown() {
		super(0.1F, 0.4F, 0.1F);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entity) {

		if(!(entity instanceof EntityLivingBase))
			return;

		EntityLivingBase entityLiving = (EntityLivingBase) entity;

		ContaminationUtil.contaminate((EntityLivingBase)entity, HazardType.RADIATION, ContaminationType.CREATIVE, 0.5F);
		entityLiving.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 60 * 20, 2));

		if(ArmorRegistry.hasAllProtection(entityLiving, 3, HazardClass.PARTICLE_FINE)) {
			ArmorUtil.damageGasMaskFilter(entityLiving, 1);
		} else {
			HbmLivingProps.incrementAsbestos(entityLiving, 5); // Mesothelioma can be developed as a result of exposure to radiation in the lungs
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);
		world.spawnParticle("townaura", x + rand.nextFloat(), y + rand.nextFloat(), z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
	}

	@Override
	public ForgeDirection getFirstDirection(World world, int x, int y, int z) {

		if(world.rand.nextInt(2) == 0)
			return ForgeDirection.UP;

		return ForgeDirection.DOWN;
	}

	@Override
	public ForgeDirection getSecondDirection(World world, int x, int y, int z) {
		return this.randomHorizontal(world);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(!world.isRemote) {

			ForgeDirection dir = ForgeDirection.getOrientation(rand.nextInt(6));

			if(rand.nextInt(7) == 0 && world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == Blocks.air) {
				world.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, ModBlocks.gas_radon_dense);
			}
			
			if (world.canBlockSeeTheSky(x, y, z)) {
				ChunkRadiationManager.proxy.incrementRad(world, x, y, z, 5);
			}
			
			if(rand.nextInt(350) == 0) {
				world.setBlockToAir(x, y, z);
				return;
			}
		}

		super.updateTick(world, x, y, z, rand);
	}
}