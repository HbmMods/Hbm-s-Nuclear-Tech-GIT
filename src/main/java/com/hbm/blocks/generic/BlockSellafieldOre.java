package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.IBlockMultiPass;
import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.render.block.RenderBlockMultipass;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class BlockSellafieldOre extends BlockSellafieldSlaked implements IBlockMultiPass {

	public BlockSellafieldOre(Material mat) {
		super(mat);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(this.getTextureName());
		super.registerBlockIcons(reg);
	}

	@Override
	public int getRenderType() {
		return IBlockMultiPass.getRenderType();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		if(RenderBlockMultipass.currentPass == 1) return this.blockIcon;
		return super.getIcon(world, x, y, z, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if(RenderBlockMultipass.currentPass == 1) return this.blockIcon;
		return icons[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		if(RenderBlockMultipass.currentPass == 1) return 0xffffff;
		return super.colorMultiplier(world, x, y, z);
	}

	@Override
	public int getPasses() {
		return 2;
	}

	@Override
	public boolean shouldRenderItemMulti() {
		return true;
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		if(this == ModBlocks.ore_sellafield_diamond) return Items.diamond;
		if(this == ModBlocks.ore_sellafield_emerald) return Items.emerald;
		if(this == ModBlocks.ore_sellafield_radgem) return ModItems.gem_rad;
		return Item.getItemFromBlock(this);
	}

	@Override
	public int quantityDropped(Random rand) {
		return 1;
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random rand) {
		if(fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, rand, fortune)) {
			int j = rand.nextInt(fortune + 2) - 1;
			if(j < 0) j = 0;
			return this.quantityDropped(rand) * (j + 1);
		} else {
			return this.quantityDropped(rand);
		}
	}

	private Random rand = new Random();

	@Override
	public int getExpDrop(IBlockAccess world, int meta, int fortune) {
		if(this.getItemDropped(meta, rand, fortune) != Item.getItemFromBlock(this)) {
			int j1 = 0;

			if(this == ModBlocks.ore_sellafield_diamond) j1 = MathHelper.getRandomIntegerInRange(rand, 3, 7);
			if(this == ModBlocks.ore_sellafield_emerald) j1 = MathHelper.getRandomIntegerInRange(rand, 3, 7);
			if(this == ModBlocks.ore_sellafield_radgem) j1 = MathHelper.getRandomIntegerInRange(rand, 3, 7);
			
			return j1;
		}
		return 0;
	}
}
