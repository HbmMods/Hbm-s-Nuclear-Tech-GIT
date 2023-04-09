package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockBase;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockUberConcrete extends BlockBase {
	
	public BlockUberConcrete() {
		super();
		this.setTickRandomly(true);
	}

	@SideOnly(Side.CLIENT)
	protected IIcon[] brokenIcons;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":concrete_super");
		brokenIcons = new IIcon[4];
		
		for(int i = 0; i < 4; i++) {
			this.brokenIcons[i] = iconRegister.registerIcon(RefStrings.MODID + ":concrete_super_m" + i);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {

		if(meta == 15) return brokenIcons[3];
		if(meta == 14) return brokenIcons[2];
		if(meta > 11) return brokenIcons[1];
		if(meta > 9) return brokenIcons[0];
		
		return this.blockIcon;
	}
	
	public void updateTick(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(rand.nextInt(meta + 1) > 0)
			return;
		
		if(meta < 15) {
			world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
		} else {
			world.setBlockToAir(x, y, z);
			
			if(world.getBlock(x, y - 1, z) == Blocks.air) {
				world.setBlock(x, y, z, ModBlocks.concrete_super_broken);
				return;
			}
			
			List<Integer> sides = new ArrayList(); //i wish the fucking homunculus that made the standard list implementation the nastiest fucking diarrhea of his life
			Collections.addAll(sides, 2, 3, 4, 5);
			Collections.shuffle(sides);
			
			for(Integer i : sides) {
				ForgeDirection dir = ForgeDirection.getOrientation(i);
				if(world.getBlock(x + dir.offsetX, y, z + dir.offsetZ) == Blocks.air && world.getBlock(x + dir.offsetX, y - 1, z + dir.offsetZ) == Blocks.air) {
					EntityFallingBlock debris = new EntityFallingBlock(world, x + 0.5 + dir.offsetX, y + 0.5, z + 0.5 + dir.offsetZ, ModBlocks.concrete_super_broken);
					debris.field_145812_b = 2;
					debris.field_145813_c = false;
					debris.func_145806_a(true);
					world.spawnEntityInWorld(debris);
					return;
				}
			}
			
			world.setBlock(x, y, z, ModBlocks.concrete_super_broken);
		}
	}
}
