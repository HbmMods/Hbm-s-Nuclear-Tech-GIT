package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.deco.TileEntityGeysir;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockGeysir extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		if(this == ModBlocks.geysir_nether) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":geysir_nether");
			this.blockIcon = Blocks.netherrack.getIcon(0, 0);
		} else {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":geysir_stone");
			this.blockIcon = Blocks.stone.getIcon(0, 0);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		return side == 1 ? this.iconTop : this.blockIcon;
	}

	public BlockGeysir(Material p_i45386_1_) {
		super(p_i45386_1_);
	}
	
	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityGeysir();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World p_149734_1_, int x, int y, int z, Random rand) {

		int l = p_149734_1_.getBlockMetadata(x, y, z);
		
		if(this == ModBlocks.geysir_vapor && l == 1) {
			float f = x + 0.5F;
			float f1 = y + 1.0F;
			float f2 = z + 0.5F;
	
			p_149734_1_.spawnParticle("cloud", f, f1, f2, 0.0D, 0.1D, 0.0D);
		}
		
		if(this == ModBlocks.geysir_nether) {
			p_149734_1_.spawnParticle("flame", x + 0.5F, y + 1.0625F, z + 0.5F, 0.0D, 0.0D, 0.0D);
		}
	}

}
