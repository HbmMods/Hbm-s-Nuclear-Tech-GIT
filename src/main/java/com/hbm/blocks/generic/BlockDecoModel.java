package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockDecoModel extends Block {
	
	//Allows between 1-4 differently colored/textured sub-blocks altogether.
	int subTypes;
	
	public BlockDecoModel(Material mat, int types) {
		super(mat);
		subTypes = types;
	}
	
	@SideOnly(Side.CLIENT)
	protected IIcon[] icons;
	
	@Override
	public int damageDropped(int meta) {
		return meta & 12;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
		for(byte i = 0; i < subTypes; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		icons =  new IIcon[subTypes];
		
		for(byte i = 0; i < subTypes; i++)
			icons[i] = iconRegister.registerIcon(this.textureName + "_" + i);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.icons[(meta >> 2) % this.icons.length];
	}
	
	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	//Did somebody say - pain?
			//Alright fuckers, looks like 2/b010 = North, 3/b011 = South, 4/b100 = West, 5/b101 = East for sides.
			//I'll just opt for something similar (0/b00 North, 1/b01 South, 2/b10 West, 3/b11 East)
	
	//Assumes meta is using the third and fourth bits.
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		int meta;
		
		if((i & 1) != 1)
			meta = i >> 1; //For North(b00>b00) and South(b10>b01), shift bits right by one
		else {
			if(i == 3)
				meta = 2; //For West(b11>b10), just set to 2
			else
				meta = 3; //For East(b01>b11), just set to 3
		}
		
		world.setBlockMetadataWithNotify(x, y, z, meta, 2);
	}
}
