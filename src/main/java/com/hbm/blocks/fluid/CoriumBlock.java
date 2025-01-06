package com.hbm.blocks.fluid;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class CoriumBlock extends BlockFluidClassic {

	@SideOnly(Side.CLIENT)
	public static IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon flowingIcon;
	public Random rand = new Random();

	public CoriumBlock(Fluid fluid, Material material) {
		super(fluid, material);
		setQuantaPerBlock(5);
		setCreativeTab(null);
		displacements.put(this, false);
		this.tickRate = 30;
		
		this.setTickRandomly(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1) ? stillIcon : flowingIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		stillIcon = register.registerIcon(RefStrings.MODID + ":corium_still");
		flowingIcon = register.registerIcon(RefStrings.MODID + ":corium_flowing");
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		float res = (float) (Math.sqrt(b.getExplosionResistance(null)) * 3);
		
		if(res < 1)
			return true;
		Random rand = new Random();
		
		return b.getMaterial().isLiquid() || rand.nextInt((int) (res * res)) == 0;
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
		
		if(!world.isRemote && rand.nextInt(10) == 0) {
			
			if(this.isSourceBlock(world, x, y, z))
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
