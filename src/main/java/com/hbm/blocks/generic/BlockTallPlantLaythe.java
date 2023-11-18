package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockDeadPlant.EnumDeadPlantType;
import com.hbm.blocks.generic.BlockNTMFlower.EnumFlowerType;
import com.hbm.blocks.generic.BlockTallPlant.EnumTallFlower;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class BlockTallPlantLaythe extends BlockEnumMulti implements IPlantable, IGrowable {
	
	public BlockTallPlantLaythe() {
		super(Material.plants, EnumTallFlowerLaythe.class, true, true);
		this.setTickRandomly(true);
	}

	public static enum EnumTallFlowerLaythe {
		LAYTHE;
	}

	
	protected IIcon[] bottomIcons;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		
		Enum[] enums = theEnum.getEnumConstants();
		this.icons = new IIcon[enums.length];
		this.bottomIcons = new IIcon[enums.length];
		
		for(int i = 0; i < icons.length; i++) {
			Enum num = enums[i];
			this.icons[i] = reg.registerIcon(this.getTextureName() + "." + num.name().toLowerCase(Locale.US) + ".upper");
			this.bottomIcons[i] = reg.registerIcon(this.getTextureName() + "." + num.name().toLowerCase(Locale.US) + ".lower");
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return meta > 7 ? this.icons[meta % this.icons.length] : this.bottomIcons[meta % this.icons.length];
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
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
	public int getRenderType() {
		return 6;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z) && world.isAirBlock(x, y + 1, z);
	}

	protected boolean canPlaceBlockOn(Block block) {
		return block == ModBlocks.laythe_silt;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
		this.checkAndDropBlock(world, x, y, z);
	}
	
	public static boolean detectCut = true;
	protected void checkAndDropBlock(World world, int x, int y, int z) {
		if(!this.canBlockStay(world, x, y, z)) {
			if(world.getBlockMetadata(x, y, z) < 8) {
				this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			}
			world.setBlock(x, y, z, Blocks.air, 0, 3);
		}
		
		if(!detectCut) return;
		
		Block block = world.getBlock(x, y + 1, z);
		int meta = world.getBlockMetadata(x, y + 1, z);
		int ownMeta = world.getBlockMetadata(x, y, z);
		
		if(ownMeta < 8 && (meta != ownMeta + 8 || block != this) && ModBlocks.plant_flower.canBlockStay(world, x, y, z)) {

			if(ownMeta == EnumTallFlowerLaythe.LAYTHE.ordinal())
				world.setBlock(x, y, z, Blocks.air);
			else
				world.setBlock(x, y, z, ModBlocks.plant_flower, EnumFlowerType.CD0.ordinal(), 3);
		}
		
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta > 7) {
			return world.getBlock(x, y - 1, z) == this && world.getBlockMetadata(x, y - 1, z) == meta - 8;
		}
		
		return canPlaceBlockOn(world.getBlock(x, y - 1, z));
	}
	
	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z) % 8;
	}
	
	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
	    // Check if the top part of the plant is being broken
	    if (meta > 7) {
	        // Set the bottom part to air
	        if (world.getBlock(x, y - 1, z) == this) {
	            world.setBlockToAir(x, y - 1, z);
	        }
	    } else {
	        // If the bottom part is being broken, check for the top part and set it to air
	        if (world.getBlock(x, y + 1, z) == this) {
	            world.setBlockToAir(x, y + 1, z);
	        }
	        
	    }
		if(player.capabilities.isCreativeMode) {
			world.setBlock(x, y + 1, z, Blocks.air, 0, 2);
		} else {
			this.dropBlockAsItem(world, x, y + 1, z, world.getBlockMetadata(x, y + 1, z), 0);
		}

	    super.onBlockHarvested(world, x, y, z, meta, player);
	}
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		world.setBlock(x, y + 1, z, this, stack.getItemDamage() + 8, 2);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(world.isRemote) return; //not possible i believe, but better safe than sorry
		
		int meta = world.getBlockMetadata(x, y, z);
		if(meta > 7) return;
		
		EnumTallFlower type = EnumTallFlower.values()[rectify(meta)];
		Block onTop = world.getBlock(x, y - 1, z);

	}

	/* grow condition */
	@Override
	public boolean func_149851_a(World world, int x, int y, int z, boolean b) {
		return false;
	/*	
		int meta = world.getBlockMetadata(x, y, z);
		int rec = rectify(meta);
		
		if(rec == EnumTallFlower.CD2.ordinal() || rec == EnumTallFlower.CD3.ordinal()) {
			
			int y0 = rec == meta ? y : y - 1;
			
			if(world.getBlock(x + 1, y0 - 1, z).getMaterial() != Material.water &&
					world.getBlock(x - 1, y0 - 1, z).getMaterial() != Material.water &&
					world.getBlock(x, y0 - 1, z + 1).getMaterial() != Material.water &&
					world.getBlock(x, y0 - 1, z - 1).getMaterial() != Material.water) {
				return false;
			}
		}
		
		if(rec == EnumTallFlower.CD3.ordinal()) {
			Block onTop = world.getBlock(x, y - (rec == meta ? 1 : 2), z);
			return onTop == ModBlocks.dirt_dead || onTop == ModBlocks.dirt_oily;
		}
		
		return rec != EnumTallFlower.CD4.ordinal() && rec != EnumTallFlower.WEED.ordinal();
		*/
	}

	/* chance */
	@Override
	public boolean func_149852_a(World world, Random rand, int x, int y, int z) {
		int meta = rectify(world.getBlockMetadata(x, y, z));
		if(meta == EnumTallFlower.CD3.ordinal()) {
			return true;
		}
		return rand.nextFloat() < 0.33F;
	}

	/* grow */
	@Override
	public void func_149853_b(World world, Random rand, int x, int y, int z) {

		int meta = world.getBlockMetadata(x, y, z);
		int rec = rectify(meta);
		
	//	detectCut = false;
		/*
		if(rec == EnumTallFlower.CD2.ordinal() || rec == EnumTallFlower.CD3.ordinal()) {
			
			if(meta == rec) {
				world.setBlockMetadataWithNotify(x, y + 1, z, meta + 9, 3);
				world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
				if(rec == EnumTallFlower.CD3.ordinal()) {
					world.setBlock(x, y - 1, z, Blocks.dirt);
				}
			} else {
				world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
				world.setBlockMetadataWithNotify(x, y - 1, z, rec + 1, 3);
				if(rec == EnumTallFlower.CD3.ordinal()) {
					world.setBlock(x, y - 2, z, Blocks.dirt);
				}
			}
		}
		*/
		
		//detectCut = true;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return EnumPlantType.Plains;
	}

	@Override
	public Block getPlant(IBlockAccess world, int x, int y, int z) {
		return this;
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}
	

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);
		
		if(metadata == EnumTallFlower.CD4.ordinal() + 8) {
			ret.add(DictFrame.fromOne(ModItems.plant_item, com.hbm.items.ItemEnums.EnumPlantType.MUSTARDWILLOW, 3 + world.rand.nextInt(4)));
		}
		
		return ret;
	}
}
