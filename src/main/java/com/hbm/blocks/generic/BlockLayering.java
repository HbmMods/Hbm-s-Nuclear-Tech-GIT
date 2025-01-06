package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.ZirnoxDestroyed;
import com.hbm.blocks.machine.rbmk.RBMKDebris;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLayering extends Block {

	public BlockLayering(Material mat) {
		super(mat);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.func_150154_b(0);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
		int l = p_149668_1_.getBlockMetadata(p_149668_2_, p_149668_3_, p_149668_4_) & 7;
		float f = 0.125F;
		return AxisAlignedBB.getBoundingBox((double) p_149668_2_ + this.minX, (double) p_149668_3_ + this.minY, (double) p_149668_4_ + this.minZ, (double) p_149668_2_ + this.maxX, (double) ((float) p_149668_3_ + (float) l * f), (double) p_149668_4_ + this.maxZ);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public void setBlockBoundsForItemRender() {
		this.func_150154_b(0);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
		this.func_150154_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
	}

	protected void func_150154_b(int p_150154_1_) {
		int j = p_150154_1_ & 7;
		float f = (float) (2 * (1 + j)) / 16.0F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
	}

	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y - 1, z);
		
		if(block instanceof RBMKDebris || block instanceof ZirnoxDestroyed)
			return true;
		
		return block != Blocks.ice && block != Blocks.packed_ice ? (block.isLeaves(world, x, y - 1, z) ? true : (block == this && (world.getBlockMetadata(x, y - 1, z) & 7) == 7 ? true : block.isOpaqueCube() && block.getMaterial().blocksMovement())) : false;
	}

	public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
		this.func_150155_m(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
	}

	private boolean func_150155_m(World p_150155_1_, int p_150155_2_, int p_150155_3_, int p_150155_4_) {
		if(!this.canPlaceBlockAt(p_150155_1_, p_150155_2_, p_150155_3_, p_150155_4_)) {
			p_150155_1_.setBlockToAir(p_150155_2_, p_150155_3_, p_150155_4_);
			return false;
		} else {
			return true;
		}
	}

	public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_) {
		super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
		p_149636_1_.setBlockToAir(p_149636_3_, p_149636_4_, p_149636_5_);
	}

	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return null;
	}

	public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
		/*if(p_149674_1_.getSavedLightValue(EnumSkyBlock.Block, p_149674_2_, p_149674_3_, p_149674_4_) > 11) {
			p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
		}*/
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return p_149646_5_ == 1 ? true : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
	}

	public int quantityDropped(int meta, int fortune, Random random) {
		return (meta & 7) + 1;
	}

	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		if(this == ModBlocks.leaves_layer) return true;
		if(this == ModBlocks.oil_spill) return true;
		if(this == ModBlocks.foam_layer) return true;
		int meta = world.getBlockMetadata(x, y, z);
		return meta >= 7 ? false : blockMaterial.isReplaceable();
	}
}
