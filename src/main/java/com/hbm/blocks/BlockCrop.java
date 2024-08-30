package com.hbm.blocks;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCrop extends BlockBush implements IGrowable {
	
	protected int maxGrowthStage = 7;

	@SideOnly(Side.CLIENT)
	protected IIcon[] blockIcons;

	public BlockCrop() {
		// Basic block setup
		setTickRandomly(true);
		float f = 0.5F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
		setHardness(0.0F);
		setStepSound(soundTypeGrass);
		disableStats();
	}

	/**
	 * is the block grass, dirt or farmland
	 */
	@Override
	protected boolean canPlaceBlockOn(Block block) {
		return block == Blocks.farmland;
	}

	public void incrementGrowStage(World world, Random rand, int x, int y, int z) {
		int growStage = world.getBlockMetadata(x, y, z) + MathHelper.getRandomIntegerInRange(rand, 2, 5);

		if(growStage > maxGrowthStage) {
			growStage = maxGrowthStage;
		}

		world.setBlockMetadataWithNotify(x, y, z, growStage, 2);
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		if(this == ModBlocks.crop_strawberry) {
			return ModItems.strawberry;
		}
		if(this == ModBlocks.crop_coffee) {
			return ModItems.bean_raw;
		}
		if(this == ModBlocks.crop_tea) {
			return meta == 7 ? ModItems.tea_leaf : ModItems.teaseeds;
		}

		return Item.getItemFromBlock(this);
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return 1; // Cross like flowers
	}
	
	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int growthStage) {
		return blockIcons[growthStage];
	}
	
	protected void checkAndDropBlock(World world, int x, int y, int z) {
		if(!this.canBlockStay(world, x, y, z)) {
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlock(x, y, z, getBlockById(0), 0, 2);
		}
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return canPlaceBlockOn(world.getBlock(x, y - 1, z));
	}

	/*
	 * Need to implement the IGrowable interface methods
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		super.updateTick(world, x, y, z, rand);
		int growStage = world.getBlockMetadata(x, y, z) + 1;

		if(growStage > 7) {
			growStage = 7;
		}

		world.setBlockMetadataWithNotify(x, y, z, growStage, 2);
	}
	
	// checks if finished growing (a grow stage of 7 is final stage)
	@Override
	public boolean func_149851_a(World world, int x, int y, int z, boolean p_149851_5_) {
		return world.getBlockMetadata(x, y, z) != 7;
	}

	@Override
	public boolean func_149852_a(World world, Random rand, int x, int y, int z) {
		return true;
	}

	@Override
	public void func_149853_b(World world, Random rand, int x, int y, int z) {
		incrementGrowStage(world, rand, x, y, z);
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random rand) {
		if(meta == 7) { //dividing is probably better, but thats the point?? plus i want players to fully grow their crops
			return(4);
		} else {
			return (meta/2);	
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);

		// Adds seeds to the drops
		if(this == ModBlocks.crop_tea && metadata >= 7) {
			for(int i = 0; i < 3 + fortune; ++i) {
				if(world.rand.nextInt(15) <= metadata) {
					ret.add(new ItemStack(ModItems.teaseeds, 1, 0));
				}
			}
		}

		return ret;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister parIIconRegister) {
		blockIcons = new IIcon[maxGrowthStage+2];
		// seems that crops like to have 8 growth icons, but okay to repeat actual texture if you want
		// to make generic should loop to maxGrowthStage
		blockIcons[0] = parIIconRegister.registerIcon(getTextureName() + "_1");
		blockIcons[1] = parIIconRegister.registerIcon(getTextureName() + "_1");
		blockIcons[2] = parIIconRegister.registerIcon(getTextureName() + "_2");
		blockIcons[3] = parIIconRegister.registerIcon(getTextureName() + "_2");
		blockIcons[4] = parIIconRegister.registerIcon(getTextureName() + "_3");
		blockIcons[5] = parIIconRegister.registerIcon(getTextureName() + "_3");
		blockIcons[6] = parIIconRegister.registerIcon(getTextureName() + "_4");
		blockIcons[7] = parIIconRegister.registerIcon(getTextureName() + "_5");
		blockIcons[8] = parIIconRegister.registerIcon(getTextureName() + "_5");
	}

}
