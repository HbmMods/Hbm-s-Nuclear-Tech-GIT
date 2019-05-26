package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.tileentity.bomb.TileEntityNukeGadget;
import com.hbm.tileentity.deco.TileEntityBomber;
import com.hbm.tileentity.deco.TileEntityDecoBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
		
		if(this == ModBlocks.bomber)
			return new TileEntityBomber();
		
		if(this == ModBlocks.steel_scaffold)
			return null;
		
		return new TileEntityDecoBlock();
	}
	
	@Override
	public int getRenderType(){
		if(this == ModBlocks.steel_scaffold)
			return 334078;
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
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
		if(this == ModBlocks.boxcar || this == ModBlocks.bomber)
			return null;
        return Item.getItemFromBlock(this);
    }
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
		int te = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
        float f = 0.0625F;
        
        if(this == ModBlocks.steel_wall)
        {
			switch(te)
			{
			case 4:
            	this.setBlockBounds(14*f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            	break;
			case 2:
            	this.setBlockBounds(0.0F, 0.0F, 14*f, 1.0F, 1.0F, 1.0F);
            	break;
			case 5:
            	this.setBlockBounds(0.0F, 0.0F, 0.0F, 2*f, 1.0F, 1.0F);
            	break;
			case 3:
            	this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2*f);
            	break;
			}
        }
        
        if(this == ModBlocks.steel_corner)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        
        if(this == ModBlocks.steel_roof)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1*f, 1.0F);
        }
        
        if(this == ModBlocks.steel_beam)
        {
            this.setBlockBounds(7*f, 0.0F, 7*f, 9*f, 1.0F, 9*f);
        }
        
        if(this == ModBlocks.steel_scaffold)
        {
            this.setBlockBounds(0.0F, 0.0F, 2*f, 1.0F, 1.0F, 14*f);
			switch(te)
			{
			case 4:
	            this.setBlockBounds(2*f, 0.0F, 0.0F, 14*f, 1.0F, 1.0F);
            	break;
			case 2:
	            this.setBlockBounds(0.0F, 0.0F, 2*f, 1.0F, 1.0F, 14*f);
            	break;
			case 5:
	            this.setBlockBounds(2*f, 0.0F, 0.0F, 14*f, 1.0F, 1.0F);
            	break;
			case 3:
	            this.setBlockBounds(0.0F, 0.0F, 2*f, 1.0F, 1.0F, 14*f);
            	break;
			}
        }
        
        //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {

		int te = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;
        
        if(this == ModBlocks.steel_wall)
        {
			switch(te)
			{
			case 4:
            	this.setBlockBounds(14*f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            	break;
			case 2:
            	this.setBlockBounds(0.0F, 0.0F, 14*f, 1.0F, 1.0F, 1.0F);
            	break;
			case 5:
            	this.setBlockBounds(0.0F, 0.0F, 0.0F, 2*f, 1.0F, 1.0F);
            	break;
			case 3:
            	this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2*f);
            	break;
			}
        }
        
        if(this == ModBlocks.steel_corner)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        
        if(this == ModBlocks.steel_roof)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1*f, 1.0F);
        }
        
        if(this == ModBlocks.steel_beam)
        {
            this.setBlockBounds(7*f, 0.0F, 7*f, 9*f, 1.0F, 9*f);
        }
        
        if(this == ModBlocks.steel_scaffold)
        {
            this.setBlockBounds(0.0F, 0.0F, 2*f, 1.0F, 1.0F, 14*f);
			switch(te)
			{
			case 4:
	            this.setBlockBounds(2*f, 0.0F, 0.0F, 14*f, 1.0F, 1.0F);
            	break;
			case 2:
	            this.setBlockBounds(0.0F, 0.0F, 2*f, 1.0F, 1.0F, 14*f);
            	break;
			case 5:
	            this.setBlockBounds(2*f, 0.0F, 0.0F, 14*f, 1.0F, 1.0F);
            	break;
			case 3:
	            this.setBlockBounds(0.0F, 0.0F, 2*f, 1.0F, 1.0F, 14*f);
            	break;
			}
        }
        
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i) {
		
		if (b == ModBlocks.boxcar) {
			
			List<ItemStack> list = new ArrayList<ItemStack>();
			list.add(new ItemStack(ModItems.ingot_steel, 5 + rand.nextInt(16)));
			list.add(new ItemStack(ModItems.plate_steel, 15 + rand.nextInt(31)));
			list.add(new ItemStack(Items.iron_ingot, 5 + rand.nextInt(11)));
			list.add(new ItemStack(ModBlocks.block_steel, 1 + rand.nextInt(3)));
			list.add(new ItemStack(ModBlocks.crate, 1 + rand.nextInt(6)));

			for (int i1 = 0; i1 < list.size(); ++i1) {
				ItemStack itemstack = list.get(i1).copy();

				if (itemstack != null) {
					float f = this.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

					while (itemstack.stackSize > 0) {
						int j1 = this.rand.nextInt(21) + 10;

						if (j1 > itemstack.stackSize) {
							j1 = itemstack.stackSize;
						}

						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(world, x + f, y + f1,
								z + f2,
								new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem()
									.setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}

						float f3 = 0.05F;
						entityitem.motionX = (float) this.rand.nextGaussian() * f3;
						entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}

			world.func_147453_f(x, y, z, b);
		}

		super.breakBlock(world, x, y, z, b, i);
	}

}
