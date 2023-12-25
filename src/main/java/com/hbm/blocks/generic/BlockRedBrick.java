package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

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
}
