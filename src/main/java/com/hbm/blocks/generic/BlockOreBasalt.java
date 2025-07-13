package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockOreBasalt extends BlockEnumMulti {
	
	protected IIcon[] topIcons;

	public BlockOreBasalt() {
		super(Material.rock, EnumBasaltOreType.class, true, true);
	}
	
	public static enum EnumBasaltOreType {
		SULFUR(ModItems.sulfur),
		FLUORITE(ModItems.fluorite),
		ASBESTOS(ModItems.ingot_asbestos),
		GEM(ModItems.gem_volcanic),
		MOLYSITE(ModItems.powder_molysite);
		
		public Item drop;
		
		private EnumBasaltOreType(Item drop) {
			this.drop = drop;
			if(drop == null) throw new IllegalStateException("EnumBasaltOreType initialized before ModItems!");
		}
	}
	
	public String getTextureMultiName(Enum num) {
		return this.getTextureName() + "_" + num.name().toLowerCase(Locale.US);
	}
	
	public String getUnlocalizedMultiName(Enum num) {
		return super.getUnlocalizedName() + "_" + num.name().toLowerCase(Locale.US);
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		EnumBasaltOreType type = EnumUtil.grabEnumSafely(EnumBasaltOreType.class, meta);
		return type.drop;
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == EnumBasaltOreType.ASBESTOS.ordinal() && world.getBlock(x, y + 1, z) == Blocks.air) {
			if(world.rand.nextInt(10) == 0) world.setBlock(x, y + 1, z, ModBlocks.gas_asbestos);
			for(int i = 0; i < 5; i++) world.spawnParticle("townaura", x + world.rand.nextFloat(), y + 1.1, z + world.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) { } //no more BUD outgassing for you, mister

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
		if(meta == EnumBasaltOreType.ASBESTOS.ordinal()) world.setBlock(x, y, z, ModBlocks.gas_asbestos);
		super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return ModBlocks.getDropsWithoutDamage(world, this, metadata, fortune);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);

		Enum[] enums = theEnum.getEnumConstants();
		this.topIcons = new IIcon[enums.length];
		
		for(int i = 0; i < topIcons.length; i++) {
			Enum num = enums[i];
			this.topIcons[i] = reg.registerIcon(this.getTextureMultiName(num) + "_top");
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if(side <= 1) return this.topIcons[meta % this.topIcons.length];
		return super.getIcon(side, meta);
	}

}
