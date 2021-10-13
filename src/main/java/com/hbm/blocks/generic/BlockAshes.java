package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorUtil;
import com.hbm.util.ArmorRegistry.HazardClass;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockAshes extends BlockFalling {

	public BlockAshes(Material mat) {
		super(mat);
	}
	
	public static int ashes = 0;

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
		super.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);
		
		if(p_149734_5_.nextInt(25) == 0) {
			
			if(ArmorUtil.checkArmorPiece(MainRegistry.proxy.me(), ModItems.ashglasses, 3)) {
				if(ashes < 256 * 0.25) {
					ashes++;
				}
			} else if(ArmorRegistry.hasAnyProtection(MainRegistry.proxy.me(), 3, HazardClass.SAND, HazardClass.LIGHT)) {
				if(ashes < 256 * 0.75) {
					ashes++;
				}
			} else {
				if(ashes < 256 * 0.95) {
					ashes++;
				}
			}
		}
	}
}
