package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.deco.TileEntityDecoBlock;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DecoBlock extends BlockContainer {
	
	Random rand = new Random();

	public DecoBlock(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		if(this == ModBlocks.steel_scaffold || this == ModBlocks.steel_beam) return null;
		return new TileEntityDecoBlock();
	}

	public static int renderIDBeam = RenderingRegistry.getNextAvailableRenderId();
	
	@Override
	public int getRenderType(){
		if(this == ModBlocks.steel_beam)
			return renderIDBeam;
		else
			return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		if(i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
		int te = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
		float f = 0.0625F;

		if(this == ModBlocks.steel_wall) {
			switch(te) {
			case 4:
				this.setBlockBounds(14 * f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				break;
			case 2:
				this.setBlockBounds(0.0F, 0.0F, 14 * f, 1.0F, 1.0F, 1.0F);
				break;
			case 5:
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 2 * f, 1.0F, 1.0F);
				break;
			case 3:
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2 * f);
				break;
			}
		}

		if(this == ModBlocks.steel_corner) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}

		if(this == ModBlocks.steel_roof) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1 * f, 1.0F);
		}

		if(this == ModBlocks.steel_beam) {
			this.setBlockBounds(7 * f, 0.0F, 7 * f, 9 * f, 1.0F, 9 * f);
		}

		if(this == ModBlocks.steel_scaffold) {
			this.setBlockBounds(0.0F, 0.0F, 2 * f, 1.0F, 1.0F, 14 * f);
			switch(te) {
			case 4:
				this.setBlockBounds(2 * f, 0.0F, 0.0F, 14 * f, 1.0F, 1.0F);
				break;
			case 2:
				this.setBlockBounds(0.0F, 0.0F, 2 * f, 1.0F, 1.0F, 14 * f);
				break;
			case 5:
				this.setBlockBounds(2 * f, 0.0F, 0.0F, 14 * f, 1.0F, 1.0F);
				break;
			case 3:
				this.setBlockBounds(0.0F, 0.0F, 2 * f, 1.0F, 1.0F, 14 * f);
				break;
			}
		}

		// this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {

		int te = world.getBlockMetadata(x, y, z);
		float f = 0.0625F;

		if(this == ModBlocks.steel_wall) {
			switch(te) {
			case 4:
				this.setBlockBounds(14 * f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				break;
			case 2:
				this.setBlockBounds(0.0F, 0.0F, 14 * f, 1.0F, 1.0F, 1.0F);
				break;
			case 5:
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 2 * f, 1.0F, 1.0F);
				break;
			case 3:
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2 * f);
				break;
			}
		}

		if(this == ModBlocks.steel_corner) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}

		if(this == ModBlocks.steel_roof) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1 * f, 1.0F);
		}

		if(this == ModBlocks.steel_beam) {
			this.setBlockBounds(7 * f, 0.0F, 7 * f, 9 * f, 1.0F, 9 * f);
		}

		if(this == ModBlocks.steel_scaffold) {
			this.setBlockBounds(0.0F, 0.0F, 2 * f, 1.0F, 1.0F, 14 * f);
			switch(te) {
			case 4:
				this.setBlockBounds(2 * f, 0.0F, 0.0F, 14 * f, 1.0F, 1.0F);
				break;
			case 2:
				this.setBlockBounds(0.0F, 0.0F, 2 * f, 1.0F, 1.0F, 14 * f);
				break;
			case 5:
				this.setBlockBounds(2 * f, 0.0F, 0.0F, 14 * f, 1.0F, 1.0F);
				break;
			case 3:
				this.setBlockBounds(0.0F, 0.0F, 2 * f, 1.0F, 1.0F, 14 * f);
				break;
			}
		}

		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}
}
