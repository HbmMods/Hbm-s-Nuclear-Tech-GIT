package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockPedestal.TileEntityPedestal;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockRedBrick extends Block {

	protected IIcon iconFront;
	protected IIcon iconTop;

	public BlockRedBrick(Material material) {
		super(material);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":brick_red");
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":brick_red_top");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":brick_base");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == meta ? (side == 0 || side == 1 ? this.iconTop : this.iconFront) : this.blockIcon;
	}
	
	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}
	
	public static void buildShaft(World world, int x, int y, int z, ForgeDirection dir) {
		
		int vOff = 12;
		x += dir.offsetX * 4;
		y -= vOff;
		z += dir.offsetZ * 4;
		
		int size = 9;
		int height = 15;
		int width = size / 2;
		
		//Outer Edges, top and bottom
		for(int i = -width; i <= width; i++) {
			world.setBlock(x + i, y, z + width, ModBlocks.brick_red, 6, 3);
			world.setBlock(x + i, y, z - width, ModBlocks.brick_red, 6, 3);
			world.setBlock(x + width, y, z + i, ModBlocks.brick_red, 6, 3);
			world.setBlock(x - width, y, z + i, ModBlocks.brick_red, 6, 3);
			world.setBlock(x + i, y + height - 1, z + width, ModBlocks.brick_red, 6, 3);
			world.setBlock(x + i, y + height - 1, z - width, ModBlocks.brick_red, 6, 3);
			world.setBlock(x + width, y + height - 1, z + i, ModBlocks.brick_red, 6, 3);
			world.setBlock(x - width, y + height - 1, z + i, ModBlocks.brick_red, 6, 3);
		}
		
		for(int i = 1; i <= height - 2; i++) {
			//Outer edges, sides
			world.setBlock(x + width, y + i, z + width, ModBlocks.brick_red, 6, 3);
			world.setBlock(x + width, y + i, z - width, ModBlocks.brick_red, 6, 3);
			world.setBlock(x - width, y + i, z + width, ModBlocks.brick_red, 6, 3);
			world.setBlock(x - width, y + i, z - width, ModBlocks.brick_red, 6, 3);
			
			//Walls
			for(int j = -width + 1; j <= width - 1; j++) {
				if(dir != Library.NEG_X || i < height - 5) world.setBlock(x + width, y + i, z + j, ModBlocks.brick_red, 6, 3);
				if(dir != Library.POS_X || i < height - 5) world.setBlock(x - width, y + i, z + j, ModBlocks.brick_red, 6, 3);
				if(dir != Library.NEG_Z || i < height - 5) world.setBlock(x + j, y + i, z + width, ModBlocks.brick_red, 6, 3);
				if(dir != Library.POS_Z || i < height - 5) world.setBlock(x + j, y + i, z - width, ModBlocks.brick_red, 6, 3);
			}
		}
		
		for(int i = -width + 1; i <= width - 1; i++) {
			for(int j = -width + 1; j <= width - 1; j++) {
				//Floor and ceiling
				world.setBlock(x + i, y, z + j, ModBlocks.brick_red, 6, 3);
				world.setBlock(x + i, y + height - 1, z + j, ModBlocks.brick_red, 6, 3);
				
				for(int k = 1; k <= height - 2; k++) {
					world.setBlock(x + i, y + k, z + j, Blocks.air);
				}
			}
		}

		world.func_147480_a(x - dir.offsetX * 4, y + vOff - 1, z - dir.offsetZ * 4, false);
		world.func_147480_a(x - dir.offsetX * 4, y + vOff, z - dir.offsetZ * 4, false);
		
		world.setBlock(x, y + 1, z, ModBlocks.pedestal, 1, 3);
		TileEntityPedestal pedestal = (TileEntityPedestal) world.getTileEntity(x, y + 1, z);
		pedestal.item = new ItemStack(ModItems.nothing);
	}
}
