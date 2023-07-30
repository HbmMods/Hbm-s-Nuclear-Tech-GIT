package com.hbm.blocks.fluid;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.extprop.HbmLivingProps.ContaminationEffect;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

public class MeltedFlesh extends Block {

	public MeltedFlesh(Material mat) {
		super(mat);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.setTickRandomly(true);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		if(this==ModBlocks.flesh_block)
		{
			return ModItems.flesh;	
		}
		if(this==ModBlocks.charred_flesh_block)
		{
			return ModItems.grilled_flesh;	
		}
		return ModItems.powder_coal_tiny;
	}

	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y - 1, z);
		return block != Blocks.ice && block != Blocks.packed_ice ? (block.isLeaves(world, x, y - 1, z) ? true : (block == this && (world.getBlockMetadata(x, y - 1, z) & 7) == 7 ? true : block.isOpaqueCube() && block.getMaterial().blocksMovement())) : false;
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
		this.func_150155_m(world, x, y, z);
	}

	private boolean func_150155_m(World world, int x, int y, int z) {
		if(!this.canPlaceBlockAt(world, x, y, z)) {
			world.setBlockToAir(x, y, z);
			return false;
		} else {
			return true;
		}
	}

	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);

		if(world.provider.dimensionId== SpaceConfig.eveDimension)
		{
			float f;
            float f1;
            float f2;
        	f = (float)x + rand.nextFloat();
            f1 = (float)y + rand.nextFloat() * 0.5F + 0.5F;
            f2 = (float)z + rand.nextFloat();
			if(this==ModBlocks.flesh_block)
			{
	        	world.spawnParticle("smoke", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
	        	world.spawnParticle("cloud", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
			}
			if(this==ModBlocks.charred_flesh_block)
			{
	        	world.spawnParticle("largesmoke", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);	        	
			}
		}		
	}
	
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
    	if(world.provider.dimensionId==SpaceConfig.eveDimension)
		{
			if(this==ModBlocks.flesh_block)
			{
				world.setBlock(x, y, z, ModBlocks.charred_flesh_block);    	
			}
			if(this==ModBlocks.charred_flesh_block)
			{
				world.setBlock(x, y, z, ModBlocks.carbonized_flesh_block);    	
			}  
        }
    }

}
