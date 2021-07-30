package com.hbm.blocks.fluid;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class VolcanicBlock extends BlockFluidClassic {

	@SideOnly(Side.CLIENT)
	public static IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon flowingIcon;

	public VolcanicBlock(Fluid fluid, Material material) {
		super(fluid, material);
		this.setTickRandomly(true);
		this.setQuantaPerBlock(4);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1) ? stillIcon : flowingIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		stillIcon = register.registerIcon(RefStrings.MODID + ":volcanic_lava_still");
		flowingIcon = register.registerIcon(RefStrings.MODID + ":volcanic_lava_flowing");
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			Block b = getReaction(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			
			if(b != null)
				world.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, b);
		}
	}

	public Block getReaction(World world, int x, int y, int z) {
		
		Block b = world.getBlock(x, y, z);
		if(b.getMaterial() == Material.water) {
			return Blocks.stone;
		}
		if(b == Blocks.log || b == Blocks.log2) {
			return ModBlocks.waste_log;
		}
		if(b == Blocks.planks) {
			return ModBlocks.waste_planks;
		}
		if(b == Blocks.leaves || b == Blocks.leaves2) {
			return Blocks.fire;
		}
		return null;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		super.updateTick(world, x, y, z, rand);
		
		int count = 0;
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			
			if(b == this)
				count++;
		}
		
		if(!world.isRemote && ((!this.isSourceBlock(world, x, y, z) && count < 2) || (rand.nextInt(5) == 0) && count < 5) && world.getBlock(x, y - 1, z) != this) {
			
			int r = rand.nextInt(200);
			
			if(r < 2)
				world.setBlock(x, y, z, ModBlocks.basalt_sulfur);
			else if(r == 2)
				world.setBlock(x, y, z, ModBlocks.basalt_asbestos);
			else if(r == 3)
				world.setBlock(x, y, z, ModBlocks.basalt_fluorite);
			else
				world.setBlock(x, y, z, ModBlocks.basalt);
		}
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		
		if(Blocks.fire.getFlammability(b) > 0)
			return true;
		
		if(b.isReplaceable(world, x, y, z))
			return true;
		
		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {
		return super.displaceIfPossible(world, x, y, z) || canDisplace(world, x, y, z);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 0;
	}
}
