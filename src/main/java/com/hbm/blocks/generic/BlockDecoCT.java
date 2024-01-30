package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.render.block.ct.CT;
import com.hbm.render.block.ct.CTStitchReceiver;
import com.hbm.render.block.ct.IBlockCT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockDecoCT extends BlockOre implements IBlockCT{

	public BlockDecoCT(Material mat) {
		super(mat);
	}

	@Override
	public int getRenderType() {
		return CT.renderID;
	}

	@SideOnly(Side.CLIENT)
	public CTStitchReceiver rec;

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(this.getTextureName());
		this.rec = IBlockCT.primeReceiver(reg, this.getTextureName(), this.blockIcon);
	}

	@Override
	public IIcon[] getFragments(IBlockAccess world, int x, int y, int z) {
		return rec.fragCache;
	}
	
	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		if(rand.nextInt(4) != 0) return null;
		if(this == ModBlocks.deco_aluminium) return ModItems.ingot_aluminium;
		if(this == ModBlocks.deco_beryllium) return ModItems.ingot_beryllium;
		if(this == ModBlocks.deco_lead) return ModItems.ingot_lead;
		if(this == ModBlocks.deco_red_copper) return ModItems.ingot_red_copper;
		if(this == ModBlocks.deco_steel) return ModItems.ingot_steel;
		if(this == ModBlocks.deco_tungsten) return ModItems.ingot_tungsten;
		return null;
	}
}
