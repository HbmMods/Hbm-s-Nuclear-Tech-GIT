package com.hbm.blocks.fluid;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class AcidBlock extends BlockFluidClassic {

	@SideOnly(Side.CLIENT)
	public static IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon flowingIcon;
	public Random rand = new Random();

	public static DamageSource damageSource;

	public AcidBlock(Fluid fluid, Material material, DamageSource damage) {
		super(fluid, material);
		damageSource = damage;
		setQuantaPerBlock(4);
		setCreativeTab(null);
		displacements.put(this, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1) ? stillIcon : flowingIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		stillIcon = register.registerIcon(RefStrings.MODID + ":acid_still");
		flowingIcon = register.registerIcon(RefStrings.MODID + ":acid_flowing");
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {

		if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
			return false;
		}
		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {

		if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
			return false;
		}
		return super.displaceIfPossible(world, x, y, z);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		entity.setInWeb();
		entity.attackEntityFrom(ModDamageSource.acid, 10000F);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		super.updateTick(world, x, y, z, rand);
		
		reactToBlocks(world, x + 1, y, z);
		reactToBlocks(world, x - 1, y, z);
		reactToBlocks(world, x, y + 1, z);
		reactToBlocks(world, x, y - 1, z);
		reactToBlocks(world, x, y, z + 1);
		reactToBlocks(world, x, y, z - 1);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
		super.onNeighborBlockChange(world, x, y, z, block);
		
		reactToBlocks(world, x + 1, y, z);
		reactToBlocks(world, x - 1, y, z);
		reactToBlocks(world, x, y + 1, z);
		reactToBlocks(world, x, y - 1, z);
		reactToBlocks(world, x, y, z + 1);
		reactToBlocks(world, x, y, z - 1);
    }
	
	public void reactToBlocks(World world, int x, int y, int z) {
		if(world.getBlock(x, y, z) != ModBlocks.acid_block) {
			world.setBlock(x, y, z, Blocks.air);
		}
	}

	@Override
	public int tickRate(World p_149738_1_) {
		return 5;
	}

}
