package com.hbm.blocks.generic;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockDeadPlant.EnumDeadPlantType;
import com.hbm.blocks.generic.BlockTallPlant.EnumTallFlower;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class BlockNTMFlower extends BlockEnumMulti implements IPlantable, IGrowable, ITooltipProvider {

	public BlockNTMFlower() {
		super(Material.plants, EnumFlowerType.class, true, true);
		this.setTickRandomly(true);
	}
	
	public static enum EnumFlowerType {
		FOXGLOVE(false),
		TOBACCO(false),
		NIGHTSHADE(false),
		WEED(false),
		CD0(true),
		CD1(true);
		
		public boolean needsOil;
		private EnumFlowerType(boolean needsOil) {
			this.needsOil = needsOil;
		}
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
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
	}

	protected boolean canPlaceBlockOn(Block block) {
		return block == Blocks.grass || block == Blocks.dirt || block == Blocks.farmland || block == ModBlocks.dirt_dead || block == ModBlocks.dirt_oily;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
		this.checkAndDropBlock(world, x, y, z);
	}
	
	protected void checkAndDropBlock(World world, int x, int y, int z) {
		if(!this.canBlockStay(world, x, y, z)) {
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlock(x, y, z, Blocks.air, 0, 2);
		}
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return canPlaceBlockOn(world.getBlock(x, y - 1, z));
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
		return 1;
	}

	@Override
	public int damageDropped(int meta) {
		
		if(meta == EnumFlowerType.CD1.ordinal()) {
			return EnumFlowerType.CD0.ordinal();
		}
		
		return meta;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(world.isRemote) return; //not possible i believe, but better safe than sorry
		
		int meta = world.getBlockMetadata(x, y, z);
		EnumFlowerType type = EnumFlowerType.values()[rectify(meta)];
		
		if(!(type == EnumFlowerType.WEED || type == EnumFlowerType.CD0 || type == EnumFlowerType.CD1)) return;
		
		if(func_149851_a(world, x, y, z, false) && func_149852_a(world, rand, x, y, z) && rand.nextInt(3) == 0) {
			func_149853_b(world, rand, x, y, z);
		}
	}

	/* grow condition */
	@Override
	public boolean func_149851_a(World world, int x, int y, int z, boolean b) {
		
		int meta = world.getBlockMetadata(x, y, z);
		
		//cadmium willows can only grow with water
		if(meta == EnumFlowerType.CD0.ordinal() || meta == EnumFlowerType.CD1.ordinal()) {
			
			if(world.getBlock(x + 1, y - 1, z).getMaterial() != Material.water &&
					world.getBlock(x - 1, y - 1, z).getMaterial() != Material.water &&
					world.getBlock(x, y - 1, z + 1).getMaterial() != Material.water &&
					world.getBlock(x, y - 1, z - 1).getMaterial() != Material.water) {
				return false;
			}
		}
		
		if(meta == EnumFlowerType.WEED.ordinal() ||  meta == EnumFlowerType.CD1.ordinal()) {
			return world.isAirBlock(x, y + 1, z);
		}
		return true;
	}

	/* chance */
	@Override
	public boolean func_149852_a(World world, Random rand, int x, int y, int z) {
		
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta == EnumFlowerType.WEED.ordinal() || meta == EnumFlowerType.CD0.ordinal() || meta == EnumFlowerType.CD1.ordinal()) {
			return rand.nextFloat() < 0.33F;
		}
		
		return true;
	}

	/* grow */
	@Override
	public void func_149853_b(World world, Random rand, int x, int y, int z) {

		int meta = world.getBlockMetadata(x, y, z);
		Block onTop = world.getBlock(x, y - 1, z);
		
		if(meta == EnumFlowerType.WEED.ordinal()) {
			if(onTop == ModBlocks.dirt_dead || onTop == ModBlocks.dirt_oily) {
				world.setBlock(x, y, z, ModBlocks.plant_dead, EnumDeadPlantType.GENERIC.ordinal(), 3);
				return;
			}
		}
		
		if(meta == EnumFlowerType.WEED.ordinal()) {
			world.setBlock(x, y, z, ModBlocks.plant_tall, EnumTallFlower.WEED.ordinal(), 3);
			world.setBlock(x, y + 1, z, ModBlocks.plant_tall, EnumTallFlower.WEED.ordinal() + 8, 3);
			return;
		}
		
		if(meta == EnumFlowerType.CD0.ordinal()) {
			world.setBlock(x, y, z, ModBlocks.plant_flower, EnumFlowerType.CD1.ordinal(), 3);
			return;
		}
		
		if(meta == EnumFlowerType.CD1.ordinal()) {
			world.setBlock(x, y, z, ModBlocks.plant_tall, EnumTallFlower.CD2.ordinal(), 3);
			world.setBlock(x, y + 1, z, ModBlocks.plant_tall, EnumTallFlower.CD2.ordinal() + 8, 3);
			return;
		}
		
		this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
	}
	
	
	
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta)
    {
    	if (meta == 1 || meta == 3) {
    		return ColorizerGrass.getGrassColor(0.5D, 1.0D);
        } else return 0xFFFFFF;
    }
    // if you need to make another tinted plant just throw the metadata value
    // into the if statements above and below i really do not want to make this more 
    // complicated than it needs to be

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
    	int l = 0;
        int i1 = 0;
        int j1 = 0;

        for (int k1 = -1; k1 <= 1; ++k1)
        {
            for (int l1 = -1; l1 <= 1; ++l1)
            {
                int i2 = world.getBiomeGenForCoords(x + l1, z + k1).getBiomeFoliageColor(x + l1, y, z + k1);
                l += (i2 & 16711680) >> 16;
                i1 += (i2 & 65280) >> 8;
                j1 += i2 & 255;
            }
        }
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 1 || meta == 3) {
        	return ((l / 9 & 255) << 16 | (i1 / 9 & 255) << 8 | j1 / 9 & 255);
        } else return 0xFFFFFF;
    }
    

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) { }
}
