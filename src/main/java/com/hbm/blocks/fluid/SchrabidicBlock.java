package com.hbm.blocks.fluid;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class SchrabidicBlock extends BlockFluidClassic {

	@SideOnly(Side.CLIENT)
	public static IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon flowingIcon;
	public Random rand = new Random();

	public static DamageSource damageSource;

	public SchrabidicBlock(Fluid fluid, Material material, DamageSource damage) {
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
		stillIcon = register.registerIcon(RefStrings.MODID + ":schrabidic_acid_still");
		flowingIcon = register.registerIcon(RefStrings.MODID + ":schrabidic_acid_flowing");
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
		
		if(this.getMaterial() == ModBlocks.fluidschrabidic)
			entity.setInWeb();
		
		if(entity instanceof EntityLivingBase)
			ContaminationUtil.contaminate((EntityLivingBase)entity, HazardType.RADIATION, ContaminationType.CREATIVE, 1.0F);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

			if(reactToBlocks(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
				world.setBlock(x, y, z, ModBlocks.sellafield_slaked);
				break;
			}
		}
	}
	
	public boolean reactToBlocks(World world, int x, int y, int z) {
		if(world.getBlock(x, y, z).getMaterial() != this.getMaterial()) {
			if(world.getBlock(x, y, z).getMaterial().isLiquid()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int tickRate(World p_149738_1_) {
		return 15;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 0;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);

		double ix = x + 0.5F + rand.nextDouble() * 2 - 1D;
		double iy = y + 0.5F + rand.nextDouble() * 2 - 1D;
		double iz = z + 0.5F + rand.nextDouble() * 2 - 1D;

		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "schrabfog");
		data.setDouble("posX", ix);
		data.setDouble("posY", iy);
		data.setDouble("posZ", iz);
		MainRegistry.proxy.effectNT(data);
	}
}
