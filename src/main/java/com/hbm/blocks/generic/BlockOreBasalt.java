package com.hbm.blocks.generic;

import java.util.Locale;
import java.util.Random;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockOreBasalt extends BlockEnumMulti {

	public BlockOreBasalt() {
		super(Material.rock, EnumBasaltOreType.class, true, true);
	}
	
	public static enum EnumBasaltOreType {
		SULFUR,
		FLUORITE,
		ASBESTOS,
		GEM,
		MOLYSITE
	}
	
	public String getTextureMultiName(Enum num) {
		return this.getTextureName() + "_" + num.name().toLowerCase(Locale.US);
	}
	
	public String getUnlocalizedMultiName(Enum num) {
		return super.getUnlocalizedName() + "_" + num.name().toLowerCase(Locale.US);
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		if(meta == EnumBasaltOreType.SULFUR.ordinal())		return ModItems.sulfur;
		if(meta == EnumBasaltOreType.FLUORITE.ordinal())	return ModItems.fluorite;
		if(meta == EnumBasaltOreType.ASBESTOS.ordinal())	return ModItems.ingot_asbestos;
		if(meta == EnumBasaltOreType.GEM.ordinal())			return ModItems.gem_volcanic;
		if(meta == EnumBasaltOreType.MOLYSITE.ordinal())	return ModItems.powder_molysite;
		return super.getItemDropped(meta, rand, fortune);
	}

	@Override
	public int damageDropped(int meta) {
		return 0;
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
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == EnumBasaltOreType.ASBESTOS.ordinal()) for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == Blocks.air) {
				world.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, ModBlocks.gas_asbestos);
			}
		}
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
		if(meta == EnumBasaltOreType.ASBESTOS.ordinal()) world.setBlock(x, y, z, ModBlocks.gas_asbestos);
		super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
	}
}
