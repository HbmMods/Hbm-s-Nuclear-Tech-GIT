package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IBomb;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityHatch;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockSeal extends Block implements IBomb {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BlockSeal(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":seal_frame");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":seal_controller");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		metadata = metadata % 4;
		if(metadata == 0 || metadata == 1)
			metadata += 4;
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : (metadata == 0 && side == 3 ? this.blockIcon : (side == metadata ? this.blockIcon : this.iconTop)));
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
		}
		if(i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			int i = BlockSeal.getFrameSize(world, x, y, z);

			if(i != 0)
				if(BlockSeal.isSealClosed(world, x, y, z, i))
					BlockSeal.openSeal(world, x, y, z, i);
				else
					BlockSeal.closeSeal(world, x, y, z, i);

			return true;
		} else {
			return false;
		}
	}

	public static int getFrameSize(World world, int x, int y, int z) {

		int max = 7;

		for(int size = 1; size < max; size++) {

			boolean valid = true;

			int xOff = 0;
			int zOff = 0;
			if(world.getBlockMetadata(x, y, z) % 4 == 2)
				zOff += size;
			if(world.getBlockMetadata(x, y, z) % 4 == 3)
				zOff -= size;
			if(world.getBlockMetadata(x, y, z) % 4 == 0)
				xOff += size;
			if(world.getBlockMetadata(x, y, z) % 4 == 1)
				xOff -= size;

			for(int X = x - size; X <= x + size; X++) {
				if(world.getBlock(X + xOff, y, z + size + zOff) != ModBlocks.seal_frame && world.getBlock(X + xOff, y, z + size + zOff) != ModBlocks.seal_controller)
					valid = false;
			}
			for(int X = x - size; X <= x + size; X++) {
				if(world.getBlock(X + xOff, y, z - size + zOff) != ModBlocks.seal_frame && world.getBlock(X + xOff, y, z - size + zOff) != ModBlocks.seal_controller)
					valid = false;
			}
			for(int Z = z - size; Z <= z + size; Z++) {
				if(world.getBlock(x - size + xOff, y, Z + zOff) != ModBlocks.seal_frame && world.getBlock(x - size + xOff, y, Z + zOff) != ModBlocks.seal_controller)
					valid = false;
			}
			for(int Z = z - size; Z <= z + size; Z++) {
				if(world.getBlock(x + size + xOff, y, Z + zOff) != ModBlocks.seal_frame && world.getBlock(x + size + xOff, y, Z + zOff) != ModBlocks.seal_controller)
					valid = false;
			}
			/*
			 * for(int X = x - size + 1; X <= x + size - 1; X++) { for(int Z = z
			 * - size + 1; Z <= z + size - 1; Z++) { //if(world.getBlock(X +
			 * size + xOff, y, Z + zOff) != ModBlocks.block_steel && //
			 * world.getBlock(X + size + xOff, y, Z + zOff) != Blocks.air) //
			 * valid = false; world.setBlock(X + xOff, y, Z + zOff,
			 * ModBlocks.block_steel); System.out.println(valid); } }
			 */

			if(valid)
				return size;
		}

		return 0;
	}

	public static void closeSeal(World world, int x, int y, int z, int size) {

		int xOff = 0;
		int zOff = 0;
		if(world.getBlockMetadata(x, y, z) % 4 == 2)
			zOff += size;
		if(world.getBlockMetadata(x, y, z) % 4 == 3)
			zOff -= size;
		if(world.getBlockMetadata(x, y, z) % 4 == 0)
			xOff += size;
		if(world.getBlockMetadata(x, y, z) % 4 == 1)
			xOff -= size;

		for(int X = x - size + 1; X <= x + size - 1; X++) {
			for(int Z = z - size + 1; Z <= z + size - 1; Z++) {
				if(world.getBlock(X + xOff, y, Z + zOff) == Blocks.air && !world.isRemote) {
					world.setBlock(X + xOff, y, Z + zOff, ModBlocks.seal_hatch);
					TileEntity te = world.getTileEntity(X + xOff, y, Z + zOff);
					if(te != null && te instanceof TileEntityHatch)
						((TileEntityHatch) te).setControllerPos(x, y, z);

				}
			}
		}
	}

	public static void openSeal(World world, int x, int y, int z, int size) {

		int xOff = 0;
		int zOff = 0;
		if(world.getBlockMetadata(x, y, z) % 4 == 2)
			zOff += size;
		if(world.getBlockMetadata(x, y, z) % 4 == 3)
			zOff -= size;
		if(world.getBlockMetadata(x, y, z) % 4 == 0)
			xOff += size;
		if(world.getBlockMetadata(x, y, z) % 4 == 1)
			xOff -= size;

		for(int X = x - size + 1; X <= x + size - 1; X++) {
			for(int Z = z - size + 1; Z <= z + size - 1; Z++) {
				if(world.getBlock(X + xOff, y, Z + zOff) == ModBlocks.seal_hatch && !world.isRemote) {
					world.setBlock(X + xOff, y, Z + zOff, Blocks.air);
				}
			}
		}
	}

	public static boolean isSealClosed(World world, int x, int y, int z, int size) {

		int xOff = 0;
		int zOff = 0;
		if(world.getBlockMetadata(x, y, z) % 4 == 2)
			zOff += size;
		if(world.getBlockMetadata(x, y, z) % 4 == 3)
			zOff -= size;
		if(world.getBlockMetadata(x, y, z) % 4 == 0)
			xOff += size;
		if(world.getBlockMetadata(x, y, z) % 4 == 1)
			xOff -= size;

		for(int X = x - size + 1; X <= x + size - 1; X++) {
			for(int Z = z - size + 1; Z <= z + size - 1; Z++) {
				if(world.getBlock(X + xOff, y, Z + zOff) == ModBlocks.seal_hatch) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {

		if(!world.isRemote) {
			int i = BlockSeal.getFrameSize(world, x, y, z);

			if(i != 0) {
				if(BlockSeal.isSealClosed(world, x, y, z, i)) {
					BlockSeal.openSeal(world, x, y, z, i);
				} else {
					BlockSeal.closeSeal(world, x, y, z, i);
				}
				
				return BombReturnCode.TRIGGERED;
			}
			
			return BombReturnCode.ERROR_INCOMPATIBLE;
		}

		return BombReturnCode.UNDEFINED;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
			int meta = world.getBlockMetadata(x, y, z);
			if(meta < 4) {
				world.setBlockMetadataWithNotify(x, y, z, meta + 4, 2);

				int i = BlockSeal.getFrameSize(world, x, y, z);

				if(i != 0)
					if(BlockSeal.isSealClosed(world, x, y, z, i))
						BlockSeal.openSeal(world, x, y, z, i);
					else
						BlockSeal.closeSeal(world, x, y, z, i);
			}
		} else {
			int meta = world.getBlockMetadata(x, y, z);
			if(meta >= 4) {
				world.setBlockMetadataWithNotify(x, y, z, meta % 4, 2);
			}
		}
	}

}
