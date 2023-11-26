package com.hbm.blocks.fluid;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class CoriumFinite extends GenericFiniteFluid {

	public CoriumFinite(Fluid fluid, Material material) {
		super(fluid, material, "corium_still", "corium_flowing");
		setQuantaPerBlock(5);
		this.tickRate = 30;
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		float res = (float) (Math.sqrt(b.getExplosionResistance(null)) * 3);
		
		if(res < 1)
			return true;
		Random rand = new Random();
		
		return b.getMaterial().isLiquid() || rand.nextInt((int) res) == 0;
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {

		if(world.getBlock(x, y, z).getMaterial().isLiquid()) {
			return false;
		}
		return canDisplace(world, x, y, z);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		entity.setInWeb();
		entity.setFire(3);
		entity.attackEntityFrom(ModDamageSource.radiation, 2F);
		
		if(entity instanceof EntityLivingBase)
			ContaminationUtil.contaminate((EntityLivingBase)entity, HazardType.RADIATION, ContaminationType.CREATIVE, 1F);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		super.updateTick(world, x, y, z, rand);
		
		if(!world.isRemote && rand.nextInt(10) == 0 && world.getBlock(x, y - 1, z) != this) {
			
			if(rand.nextInt(3) == 0)
				world.setBlock(x, y, z, ModBlocks.block_corium);
			else
				world.setBlock(x, y, z, ModBlocks.block_corium_cobble);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 0;
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return false;
	}
}
