package com.hbm.blocks;

import java.util.Random;

import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class MudBlock extends BlockFluidClassic {

	@SideOnly(Side.CLIENT)
	public static IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon flowingIcon;

	public static DamageSource damageSource;

	public MudBlock(Fluid fluid, Material material, DamageSource damage) {
		super(fluid, material);
		damageSource = damage;
		setQuantaPerBlock(4);
		setCreativeTab(null);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1) ? stillIcon : flowingIcon;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		stillIcon = register.registerIcon(RefStrings.MODID + ":mud_still");
		flowingIcon = register.registerIcon(RefStrings.MODID + ":mud_flowing");
	}

	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		if (world.getBlock(x, y, z).getMaterial().isLiquid())
			return false;
		return super.canDisplace(world, x, y, z);
	}

	public boolean displaceIfPossible(World world, int x, int y, int z) {
		if (world.getBlock(x, y, z).getMaterial().isLiquid())
			return false;
		return super.displaceIfPossible(world, x, y, z);
	}

	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
				entity.motionY *= 0.05D;
				entity.motionZ *= 0.05D;
				entity.motionX *= 0.05D;
		if (!world.isRemote) {
			entity.attackEntityFrom(damageSource, 8.0F);
		}
		if(entity.fallDistance > 2)
			entity.fallDistance = 2;
	}

	public void updateTick(World world, int x, int y, int z, Random rand) {
		super.updateTick(world, x, y, z, rand);
	}
	
    public int tickRate(World p_149738_1_)
    {
        return 15;
    }

}
